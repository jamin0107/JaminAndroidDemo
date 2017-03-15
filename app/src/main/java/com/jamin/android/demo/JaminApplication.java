package com.jamin.android.demo;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.jamin.android.demo.db.DBFactory;
import com.jamin.framework.util.LogUtil;

/**
 * Created by jamin on 2016/11/25.
 */

public class JaminApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(true, "JaminDebug");
        JaminApplicationHelper.init(this);
        ProcessManager.init(this);
        LogUtil.d("isUIProcess = " + ProcessManager.isUIProcess());
        //注册数据库
        DBFactory.getInstance().register(this);
        if (BuildConfig.DEBUG) {
            //Stetho Init chrome://inspect/
//        Stetho.newInitializerBuilder(this)
//                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                .build();
            //// TODO: 2017/1/12 要区分正式环境和测试环境对Stetho进行编译
            Stetho.initializeWithDefaults(this);
        }
    }


}
