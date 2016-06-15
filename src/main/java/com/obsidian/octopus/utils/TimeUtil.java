package com.obsidian.octopus.utils;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author gongjun
 */
public class TimeUtil {

    /**
     * 获取long型系统时间
     *
     * @return
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static long time() {
        return getCurrentTime();
    }

    public static long timestamp() {
        return timestamp(getCurrentTime());
    }

    public static long timestamp(long time) {
        return time / 1000;
    }

    public static long timestamp(Date data) {
        return timestamp(data.getTime());
    }

    public static long timestamp(Calendar calendar) {
        return timestamp(calendar.getTimeInMillis());
    }

    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 获取与当前时间的分钟差
     *
     * @param time
     * @return
     */
    public static long getCompareMinuteWithNow(Long time) {
        long delta = Math.abs((time - getCurrentTime())) / (1000 * 60);
        return delta;
    }

    /**
     * 获取与当前时间秒差
     *
     * @param time
     * @return
     */
    public static long getCompareSeconds(long time) {
        long delta = time - getCurrentTime();
        delta = delta / 1000;
        return Math.abs(delta);
    }

    /**
     * 检查两个时间是否在指定时间的两端
     *
     * @param time1
     * @param time2
     * @param appointTime
     * @return
     */
    public static boolean isBothSideWithAppointTime(long time1, long time2, long appointTime) {
        boolean retB = false;
        if (time1 <= appointTime && time2 > appointTime) {
            retB = true;
        } else if (time1 < appointTime && time2 >= appointTime) {
            retB = true;
        } else if (time1 >= appointTime && time2 < appointTime) {
            retB = true;
        } else if (time1 > appointTime && time2 <= appointTime) {
            retB = true;
        }
        return retB;
    }

    /**
     * 是否同一天（0点为准）
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean sameDay(Long startTime, Long endTime) {
        if (null == startTime || null == endTime) {
            return false;
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(startTime);
        int y1 = c1.get(Calendar.YEAR);
        int m1 = c1.get(Calendar.MONTH);
        int d1 = c1.get(Calendar.DATE);

        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(endTime);
        int y2 = c2.get(Calendar.YEAR);
        int m2 = c2.get(Calendar.MONTH);
        int d2 = c2.get(Calendar.DATE);

        return y1 == y2 && m1 == m2 && d1 == d2;
    }

    /**
     * 比较当前时间是否在某两个时间点内
     *
     * @param startTime yyyy-MM-dd HH:mm:ss
     * @param endTime yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static boolean compareTime(String startTime, String endTime) {
        java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Calendar c1 = java.util.Calendar.getInstance();
        java.util.Calendar c2 = java.util.Calendar.getInstance();
        java.util.Calendar c3 = java.util.Calendar.getInstance();
        try {
            c1.setTime(df.parse(startTime));
            c2.setTime(df.parse(endTime));
            return !c3.before(c1) && !c3.after(c2);
        } catch (java.text.ParseException e) {
            return false;
        }
    }

}
