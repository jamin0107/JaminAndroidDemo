package com.jamin.rescue.db;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wangjieming on 2017/8/2.
 *
 */

public class RescueSP {


    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences("rescue_sp", Activity.MODE_PRIVATE);
    }

    public static long getLastUpdateTime() {
        return sharedPreferences.getLong("LastUpdateDbTime", 0);
    }


    public static void setDataKeepDays(int days) {
        sharedPreferences.edit().putInt("rescue_data_keep_days", days).apply();
    }

    public static int getDataKeepDays() {
        return sharedPreferences.getInt("rescue_data_keep_days", 7);
    }


    public static void setLastUpdateTime(long lastUpdateTime) {
        sharedPreferences.edit().putLong("LastUpdateDbTime", lastUpdateTime).apply();
    }

    public static void setEnable(boolean enable) {
        sharedPreferences.edit().putBoolean("rescueEnable", enable).apply();
    }


    public static boolean isEnable() {
        return sharedPreferences.getBoolean("rescueEnable", false);
    }
}
