package com.jamin.android.demo;

import android.app.Application;
import android.content.Context;

/**
 * Created by jamin on 2017/3/15.
 */

public class JaminApplicationHelper {


    private final JaminApplication THE_APP;
    private static JaminApplicationHelper mInstance;


    private JaminApplicationHelper(JaminApplication application) {
        THE_APP = application;
    }

    public static void init(JaminApplication application) {
        if (mInstance == null) {
            mInstance = new JaminApplicationHelper(application);
        }
    }

    /**
     * If not init in Application will throw a NPE.
     *
     * @return the application's instance
     */
    public static Application getApplication() {
        if (mInstance == null) {
            throw new NullPointerException("TheApplication not init yet.");
        }
        return mInstance.THE_APP;
    }


    public static Context getAppContext() {
        if (mInstance == null) {
            throw new NullPointerException("TheApplication not init yet.");
        }
        return mInstance.THE_APP.getApplicationContext();
    }










}
