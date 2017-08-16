package com.jamin.logger.window;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wangjieming on 2017/8/16.
 */

public class LogInfo {

    int level;
    String msg;

    public LogInfo(int level, String itemText) {
        this.level = level;
        this.msg = itemText;
    }


    public static final int VERBOSE = 1;
    public static final int INFO = 2;
    public static final int DEBUG = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int WTF = 6;


    @IntDef({
            VERBOSE,
            INFO,
            DEBUG,
            WARN,
            ERROR,
            WTF
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Level {

    }

}
