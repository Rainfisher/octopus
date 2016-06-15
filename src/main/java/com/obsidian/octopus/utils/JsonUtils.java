package com.obsidian.octopus.utils;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author alex
 */
public class JsonUtils {

    public static boolean optBoolean(JSONObject json, Object key, boolean defaultValue) {
        Boolean bool = json.getBoolean(key.toString());
        if (bool == null) {
            bool = defaultValue;
        }
        return bool;
    }

    public static int optInt(JSONObject json, Object key, int defaultValue) {
        Integer integer = json.getInteger(key.toString());
        if (integer == null) {
            integer = defaultValue;
        }
        return integer;
    }

    public static long optLong(JSONObject json, Object key, long defaultValue) {
        Long aLong = json.getLong(key.toString());
        if (aLong == null) {
            aLong = defaultValue;
        }
        return aLong;
    }

    public static String optString(JSONObject json, Object key, String defaultValue) {
        String string = json.getString(key.toString());
        if (string == null) {
            string = defaultValue;
        }
        return string;
    }

}
