package com.obsidian.octopus.vulcan.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.obsidian.octopus.utils.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author alex
 */
@Singleton
public class Memory implements CacheOperater {

    private static final Logger LOGGER = Logger.getInstance(Memory.class);
    private final int DEFAULT_EXPIRED_SECOND = 600;
    private final LoadingCache<String, Object> caches;
    private final LoadingCache<String, Object> hashCaches;

    public Memory() {
        this.caches = CacheBuilder.newBuilder().expireAfterWrite(DEFAULT_EXPIRED_SECOND, TimeUnit.SECONDS).build(new CacheLoader<String, Object>() {

            @Override
            public Object load(String key) throws Exception {
                return null;
            }
        });
        this.hashCaches = CacheBuilder.newBuilder().build(new CacheLoader<String, Object>() {

            @Override
            public Object load(String key) throws Exception {
                return null;
            }
        });
    }

    @Override
    public boolean cacheAdd(Object data, Object... keys) {
        return cacheAddWithExpired(data, DEFAULT_EXPIRED_SECOND, keys);
    }

    @Override
    public boolean cacheAddWithExpired(Object data, Integer expiredSeconds, Object... keys) {
        String key = _getKey(keys);
        caches.put(key, data);
        return true;
    }

    @Override
    public boolean cacheReplace(Object data, Object... keys) {
        return cacheAdd(data, keys);
    }

    @Override
    public boolean cacheReplaceWithExpired(Object data, Integer expiredSeconds, Object... keys) {
        return cacheAddWithExpired(data, expiredSeconds, keys);
    }

    @Override
    public boolean cacheDelete(Object... keys) {
        String key = _getKey(keys);
        caches.invalidate(key);
        return true;
    }

    @Override
    public int cacheDeletes(Object[] objects, Object... keys) {
        Object[] newKeys = new Object[keys.length + 1];
        System.arraycopy(keys, 0, newKeys, 0, keys.length);
        ArrayList<String> list = new ArrayList<>();
        for (Object object : objects) {
            newKeys[keys.length] = object;
            String key = _getKey(newKeys);
            list.add(key);
        }
        caches.invalidateAll(list);
        return objects.length;
    }

    @Override
    public <T> T cacheGet(Object... keys) {
        String key = _getKey(keys);
        try {
            return (T) caches.get(key);
        } catch (ExecutionException ex) {
            LOGGER.debug("cacheGet", ex);
            return null;
        }
    }

    @Override
    public synchronized boolean hashAdd(String hashKey, Object data, Object... keys) {
        String key = _getKey(keys);
        Object ret = cacheGet(hashKey);
        Map<String, Object> map;
        if (ret != null && ret instanceof Map) {
            map = (Map<String, Object>) ret;
        } else {
            map = new ConcurrentHashMap<>();
        }

        map.put(key, data);
        hashCaches.put(hashKey, map);
        return true;
    }

    @Override
    public synchronized boolean hashDelete(String hashKey, Object... keys) {
        Object ret = cacheGet(hashKey);
        boolean retB = false;
        if (ret != null && ret instanceof Map) {
            String key = _getKey(keys);
            Map<String, Object> map = (Map<String, Object>) ret;
            Object removeObj = map.remove(key);
            if (removeObj == null) {
                retB = false;
            } else {
                hashCaches.put(hashKey, map);
                retB = true;
            }
        }
        return retB;
    }

    private <T> T _hashCacheGet(Object... keys) {
        String key = _getKey(keys);
        try {
            return (T) hashCaches.get(key);
        } catch (ExecutionException ex) {
            LOGGER.debug("_hashCacheGet", ex);
            return null;
        }
    }

    @Override
    public <T> T hashGet(String hashKey, Object... keys) {
        Object o = null;
        Object ret = _hashCacheGet(hashKey);
        if (ret != null && ret instanceof Map) {
            String key = _getKey(keys);
            Map<String, Object> map = (Map<String, Object>) ret;
            o = map.get(key);
        }
        return (T) o;
    }

    @Override
    public <T> List<T> hashValues(Object... keys) {
        List<T> o = null;
        Object ret = _hashCacheGet(keys);
        if (ret != null && ret instanceof Map) {
            Map<String, T> map = (Map<String, T>) ret;
            o = new ArrayList<>(map.values());
        }
        return o;
    }

    @Override
    public long hashSize(Object... keys) {
        int size = 0;
        Object ret = _hashCacheGet(keys);
        if (ret != null && ret instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) ret;
            size = map.size();
        }
        return size;
    }

    private String _getKey(Object... keys) {
        String ret = StringUtils.join(keys, "_");
        Object primary = getPrimary();
        if (primary != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getPrimary()).append("_")
                    .append(ret);
            ret = stringBuilder.toString();
        }
        return ret;
    }

    protected Object getPrimary() {
        return null;
    }

    @Override
    public boolean flushAll() {
        caches.invalidateAll();
        return true;
    }

}
