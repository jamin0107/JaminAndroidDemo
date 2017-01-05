package com.jamin.android.demo;

import android.app.Application;

import com.jamin.framework.util.LogUtil;

/**
 * Created by jamin on 2016/11/25.
 */

public class JaminApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(true, "Jamin");
        ProcessManager.init(this);
        LogUtil.d("isUIProcess = " + ProcessManager.isUIProcess());

    }


}
