package com.obsidian.octopus.vulcan.cache;

import java.util.List;

/**
 *
 * @author Alex Chou
 */
public interface CacheOperater {

    boolean cacheAdd(Object data, Object... keys);

    boolean cacheAddWithExpired(Object data, Integer expiredSeconds, Object... keys);

    boolean cacheReplace(Object data, Object... keys);

    boolean cacheReplaceWithExpired(Object data, Integer expiredSeconds, Object... keys);

    boolean cacheDelete(Object... keys);
    
    int cacheDeletes(Object[] objects, Object... keys);

    <T> T cacheGet(Object... keys);

    boolean hashAdd(String hashKey, Object data, Object... keys);

    boolean hashDelete(String hashKey, Object... keys);

    <T> T hashGet(String hashKey, Object... keys);

    <T> List<T> hashValues(Object... keys);

    long hashSize(Object... keys);
    
    boolean flushAll();

}
