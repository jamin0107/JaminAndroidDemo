package com.jamin.framework.util.time;

/**
 * Created by jamin on 15-4-2.
 */
public interface TimeUnit {

    /**
     * MILLISECOND
     */
    long MILLISECOND = 1;

    /**
     * SECOND
     */
    long SECOND = 1000;

    /**
     * MINUTE
     */
    long MINUTE = SECOND * 60;

    /**
     * HOUR
     */
    long HOUR = MINUTE * 60;

    /**
     * DAY
     */
    long DAY = HOUR * 24;

    /**
     * WEEK
     */
    long WEEK = DAY * 7;

    /**
     * month
     */
    long MONTH = DAY * 30;

    /**
     * year
     */
    long YEAR = MONTH * 12;


}
