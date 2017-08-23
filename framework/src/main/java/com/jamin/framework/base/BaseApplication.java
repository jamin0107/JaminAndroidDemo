package com.jamin.framework.base;

import android.support.multidex.MultiDexApplication;

/**
 * Created by wangjieming on 2017/8/22.
 */

public class BaseApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplicationHelper.init(this);
    }
}
