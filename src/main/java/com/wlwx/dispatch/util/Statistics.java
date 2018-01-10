package com.wlwx.dispatch.util;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Statistics {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String dispatchStatrtTime;
    public static String dispatchStopTime;
    public static volatile int taskTolNum;
    public static volatile int ExceptionNum;
    public static volatile int runningNum;
    public static volatile int tolMobileNum;
    public static volatile long sqlRedisLength;

    public static void setDispatchStatrtTime(Date date) {
        Statistics.dispatchStatrtTime = sdf.format(date);
    }

    public static void setDispatchStopTime(Date date) {
        Statistics.dispatchStopTime = sdf.format(date);
    }
}
