package com.jamin.framework.util;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.v4.content.LocalBroadcastManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wangjieming on 2017/8/16.
 */

public class LogEventSender {

    public static final String ACTION_VIVA_LOG = "ACTION_VIVA_LOG";
    public static final String INTENT_KEY_MSG = "INTENT_KEY_MSG";
    public static final String INTENT_KEY_LEVEL = "INTENT_KEY_LEVEL";


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


    public static void sendEvent(Application application, @Level int level, String msg) {
        Intent intent = new Intent(ACTION_VIVA_LOG);
        intent.putExtra(INTENT_KEY_LEVEL, level);
        intent.putExtra(INTENT_KEY_MSG, msg);
        LocalBroadcastManager.getInstance(application).sendBroadcast(intent);

    }

}
