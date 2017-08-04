package com.jamin.rescue.db;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wangjieming on 2017/8/2.
 *
 */

public class RescueSP {


    static SharedPreferences sharedPreferences;
    static long b = 0;

    public static void a(Context context, long j) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences("crash_sp", Activity.MODE_PRIVATE);
        if (b == 0) {
            b = j;
        }
    }

    public static long getLastUpdateTime() {
        return sharedPreferences.getLong("LastUpdateDbTime", 0);
    }


    public static void setLastUpdateTime(long lastUpdateTime) {
        sharedPreferences.edit().putLong("LastUpdateDbTime", lastUpdateTime).apply();
    }

}
