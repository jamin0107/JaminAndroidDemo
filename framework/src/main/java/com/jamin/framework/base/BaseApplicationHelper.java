package com.jamin.framework.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by jamin on 2017/3/15.
 */

public class BaseApplicationHelper {


    private final BaseApplication THE_APP;
    private static BaseApplicationHelper mInstance;


    private BaseApplicationHelper(BaseApplication application) {
        THE_APP = application;
    }

    public static void init(BaseApplication application) {
        if (mInstance == null) {
            mInstance = new BaseApplicationHelper(application);
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
