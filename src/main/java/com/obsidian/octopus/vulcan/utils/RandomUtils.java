package com.obsidian.octopus.vulcan.utils;

import com.alibaba.fastjson.JSONArray;
import java.util.Random;
import org.apache.commons.lang.math.JVMRandom;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class RandomUtils {

    public static final Random RANDOM = new JVMRandom();

    public static int random(int max) {
        return RANDOM.nextInt(max);
    }

    public static int random(int min, int max) {
        if (min == max) {
            return min;
        }
        int range = Math.abs(max - min) + 1;
        return min + RANDOM.nextInt(range);
    }

    public static int getRateIndex(JSONArray rates) {
        int index;
        if (rates.size() > 1) {
            int radix = 0;
            for (int i = 0; i < rates.size(); i++) {
                radix += rates.getIntValue(i);
            }
            index = getRateIndex(rates, radix);
        } else {
            index = 0;
        }
        return index;
    }

    public static int getRateIndex(JSONArray rates, int radix) {
        if (rates.size() > 1) {
            int now = 0;
            int random = RANDOM.nextInt(radix);
            for (int i = 0; i < rates.size(); i++) {
                now += rates.getIntValue(i);
                if (random < now) {
                    return i;
                }
            }
        }
        return 0;
    }

    public static int getRateIndex(int[] rates) {
        int radix = 0;
        for (int i : rates) {
            radix += i;
        }
        return getRateIndex(rates, radix);
    }

    public static int getRateIndex(int[] rates, int radix) {
        if (rates.length == 1) {
            return 0;
        }
        int now = 0;
        int random = RANDOM.nextInt(radix);
        for (int i = 0; i < rates.length; i++) {
            now += rates[i];
            if (random < now) {
                return i;
            }
        }
        return 0;
    }

}
