package com.obsidian.octopus.utils;

import com.alibaba.fastjson.JSONArray;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author gongjun
 */
public class ArrayUtil {

    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final Integer[] EMPTY_INTEGER_ARRAY = new Integer[0];
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * 字符串数组String转int数组
     *
     * @param str
     * @param reg 分隔符
     * @return
     */
    public static int[] changeStrsToInts(String str, String reg) {
        if (str == null) {
            return EMPTY_INT_ARRAY;
        }
        String[] strs = str.split(reg);
        int[] retInts = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            retInts[i] = Integer.parseInt(strs[i]);
        }
        return retInts;
    }

    /**
     * 字符串数组String转float数组
     *
     * @param str
     * @param reg 分隔符
     * @return
     */
    public static float[] changeStrsToFloats(String str, String reg) {
        if (str == null) {
            return EMPTY_FLOAT_ARRAY;
        }
        String[] strs = str.split(reg);
        float[] retFloats = new float[strs.length];
        for (int i = 0; i < strs.length; i++) {
            retFloats[i] = Float.parseFloat(strs[i]);
        }
        return retFloats;
    }

    /**
     * 将JSONArray转为int[]
     *
     * @param ja
     * @return
     */
    public static int[] changeJsonArrayToInts(JSONArray ja) {
        if (ja == null) {
            return EMPTY_INT_ARRAY;
        }
        int[] retInts = new int[ja.size()];
        for (int i = 0; i < ja.size(); i++) {
            retInts[i] = ja.getIntValue(i);
        }
        return retInts;
    }

    /**
     * 将JSONArray转为float[]
     *
     * @param ja
     * @return
     */
    public static double[] changeJsonArrayToDoubles(JSONArray ja) {
        if (ja == null) {
            return EMPTY_DOUBLE_ARRAY;
        }
        double[] retFloats = new double[ja.size()];
        for (int i = 0; i < ja.size(); i++) {
            retFloats[i] = ja.getDouble(i);
        }
        return retFloats;
    }

    /**
     * 将JSONArray转为String[]
     *
     * @param ja
     * @return
     */
    public static String[] changeJsonArrayToStrings(JSONArray ja) {
        if (ja == null) {
            return EMPTY_STRING_ARRAY;
        }
        String[] strings = new String[ja.size()];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = ja.getString(i);
        }
        return strings;
    }

    /**
     * 将JSONArray转为Integer[]
     *
     * @param ja
     * @return
     */
    public static Integer[] changeJsonArrayToIntegers(JSONArray ja) {
        if (ja == null) {
            return EMPTY_INTEGER_ARRAY;
        }
        Integer[] integers = new Integer[ja.size()];
        for (int i = 0; i < integers.length; i++) {
            integers[i] = ja.getIntValue(i);
        }
        return integers;
    }

    /**
     * 检查String是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * int是否在List中
     *
     * @param i
     * @param vars
     * @return
     */
    public static boolean intInArray(int i, List<Integer> vars) {
        return vars == null ? false : vars.contains(i);
    }

    public static String[][] splitArray(String[] strings, int size) {
        if (strings == null) {
            return new String[0][0];
        }
        String[][] rets;
        if (strings.length <= size) {
            rets = new String[][]{
                strings
            };
        } else {
            int count = (int) Math.ceil(strings.length * 1.0 / size);
            rets = new String[count][];
            for (int i = 0; i < count; i++) {
                int indexStart = i * size;
                int length = i == count - 1 ? (strings.length - indexStart) : size;
                rets[i] = new String[length];
                System.arraycopy(strings, indexStart, rets[i], 0, length);
            }
        }
        return rets;
    }

}
