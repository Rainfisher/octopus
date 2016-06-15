package com.obsidian.octopus.vulcan.cache;

import com.obsidian.octopus.utils.ArrayUtil;
import com.obsidian.octopus.utils.Logger;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class Redis implements CacheOperater {

    private static final Logger LOGGER = Logger.getInstance(Redis.class);
    private static int DEFAULT_EXPIRED_SECOND = 600;
    private static final String OK = "OK";

    private JedisPoolConfig jedisPoolConfig;
    private JedisPool jedisPool;

    public void init(Properties properties) {
        jedisPoolConfig = new JedisPoolConfig();
        for (String name : properties.stringPropertyNames()) {
            try {
                BeanUtils.setProperty(jedisPoolConfig, name, properties.getProperty(name));
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                LOGGER.debug("init", e);
            }
        }
        int database = Protocol.DEFAULT_DATABASE;
        if (properties.containsKey("database")) {
            database = Integer.valueOf(properties.getProperty("database"));
        }
        if (properties.containsKey("expired")) {
            DEFAULT_EXPIRED_SECOND = Integer.valueOf(properties.getProperty("expired"));
        }

        jedisPool = new JedisPool(jedisPoolConfig, properties.getProperty("host"),
                Integer.valueOf(properties.getProperty("port")),
                Protocol.DEFAULT_TIMEOUT,
                null, database);
    }

    public JedisPoolConfig getJedisPoolConfig() {
        return jedisPoolConfig;
    }

    public Jedis getJedis() {
        if (jedisPool == null) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        }
        catch (Exception e) {
            LOGGER.debug("getJedis", e);
        }
        return jedis;
    }

    public void returnResource(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        }
        catch (Exception e) {
            LOGGER.debug("returnResource", e);
        }
    }

    private byte[] _serialize(Object data) {
        byte[] bytes;
        if (data == null) {
            bytes = new byte[0];
        } else if (data instanceof Serializable) {
            bytes = SerializationUtils.serialize((Serializable) data);
        } else {
            throw new SerializationException();
        }
        return bytes;
    }

    private Object _deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return SerializationUtils.deserialize(bytes);
    }

    @Override
    public boolean cacheAdd(Object data, Object... keys) {
        return cacheAddWithExpired(data, DEFAULT_EXPIRED_SECOND, keys);
    }

    @Override
    public boolean cacheAddWithExpired(Object data, Integer expiredSeconds, Object... keys) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return false;
        }
        try {
            byte[] key = _getKey(keys);
            String ret = jedis.set(key, _serialize(data));
            if (OK.equals(ret) && expiredSeconds != null) {
                jedis.expire(key, expiredSeconds);
                return true;
            }
            return false;
        }
        catch (Exception e) {
            LOGGER.debug("cacheAddWithExpired", e);
            return false;
        }
        finally {
            returnResource(jedis);
        }
    }

    @Override
    public boolean cacheReplace(Object data, Object... keys) {
        return cacheAddWithExpired(data, DEFAULT_EXPIRED_SECOND, keys);
    }

    @Override
    public boolean cacheReplaceWithExpired(Object data, Integer expiredSeconds, Object... keys) {
        return cacheAddWithExpired(data, expiredSeconds, keys);
    }

    @Override
    public boolean cacheDelete(Object... keys) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return false;
        }
        try {
            return jedis.del(_getKey(keys)) > 0;
        }
        catch (Exception e) {
            LOGGER.debug("cacheDelete", e);
            return false;
        }
        finally {
            returnResource(jedis);
        }
    }

    @Override
    public int cacheDeletes(Object[] objects, Object... keys) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return 0;
        }
        Object[] newKeys = new Object[keys.length + 1];
        System.arraycopy(keys, 0, newKeys, 0, keys.length);
        String[] strings = new String[objects.length];
        for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];
            newKeys[keys.length] = object;
            byte[] byteKeys = _getKey(newKeys);
            strings[i] = new String(byteKeys);
        }
        int number = 0;
        try {
            String[][] splitArray = ArrayUtil.splitArray(strings, 500);
            for (String[] array : splitArray) {
                number += jedis.del(array);
            }
        }
        catch (Exception e) {
            LOGGER.debug("cacheDeletes", e);
        }
        finally {
            returnResource(jedis);
        }
        return number;
    }

    @Override
    public <T> T cacheGet(Object... keys) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        try {
            byte[] bytes = jedis.get(_getKey(keys));
            return (T) _deserialize(bytes);
        }
        catch (Exception e) {
            LOGGER.debug("cacheGet", e);
            return null;
        }
        finally {
            returnResource(jedis);
        }
    }

    @Override
    public boolean hashAdd(String hashKey, Object data, Object... keys) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return false;
        }
        try {
            jedis.hset(_getKey(keys), hashKey.getBytes(), _serialize(data));
            return true;
        }
        catch (Exception e) {
            LOGGER.debug("hashAdd", e);
            return false;
        }
        finally {
            returnResource(jedis);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean hashDelete(String hashKey, Object... keys) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return false;
        }
        try {
            return jedis.hdel(_getKey(keys), hashKey.getBytes()) > 0;
        }
        catch (Exception e) {
            LOGGER.debug("hashDelete", e);
            return false;
        }
        finally {
            returnResource(jedis);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T hashGet(String hashKey, Object... keys) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        try {
            byte[] value = jedis.hget(_getKey(keys), hashKey.getBytes());
            return (T) _deserialize(value);
        }
        catch (Exception e) {
            LOGGER.debug("hashGet", e);
            return null;
        }
        finally {
            returnResource(jedis);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> hashValues(Object... keys) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        try {
            List<byte[]> values = jedis.hvals(_getKey(keys));
            List<T> list = new ArrayList<>();
            for (byte[] value : values) {
                T t = (T) _deserialize(value);
                list.add(t);
            }
            return list;
        }
        catch (Exception e) {
            LOGGER.debug("hashValues", e);
            return null;
        }
        finally {
            returnResource(jedis);
        }
    }

    @Override
    public long hashSize(Object... keys) {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return 0;
        }
        try {
            return jedis.hlen(_getKey(keys));
        }
        catch (Exception e) {
            LOGGER.debug("hashSize", e);
            return 0;
        }
        finally {
            returnResource(jedis);
        }
    }

    @Override
    public boolean flushAll() {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return false;
        }
        try {
            jedis.flushDB();
        }
        catch (Exception e) {
            LOGGER.debug("flushAll", e);
            return false;
        }
        finally {
            returnResource(jedis);
        }
        return true;
    }

    private byte[] _getKey(Object... keys) {
        String ret = StringUtils.join(keys, "_");
        Object primary = getPrimary();
        if (primary != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getPrimary()).append("_")
                    .append(ret);
            ret = stringBuilder.toString();
        }
        return ret.getBytes();
    }

    protected Object getPrimary() {
        return null;
    }

}
