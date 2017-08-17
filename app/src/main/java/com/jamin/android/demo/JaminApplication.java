package com.jamin.android.demo;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;
import com.jamin.android.demo.db.DBFactory;
import com.jamin.framework.util.LogUtil;
import com.jamin.rescue.Rescue;

/**
 * Created by jamin on 2016/11/25.
 */

public class JaminApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        LogUtil.init(true, "JaminDebug");
        JaminApplicationHelper.init(this);
        ProcessManager.init(this);
        LogUtil.d("isUIProcess = " + ProcessManager.isUIProcess());
        //注册数据库
        DBFactory.getInstance().register(this);
        Rescue.init(this);
        Rescue.setEnable(true);
        Rescue.setHugoEnable(false);
        Rescue.setPerformanceEnable(false);

        if (BuildConfig.DEBUG) {
            //Stetho Init chrome://inspect/
//        Stetho.newInitializerBuilder(this)
//                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                .build();
            //// TODO: 2017/1/12 要区分正式环境和测试环境对Stetho进行编译
            Stetho.initializeWithDefaults(this);
            ARouter.openLog();
            ARouter.openDebug();
            Rescue.setDebug(true);
            Rescue.setHugoEnable(true);
            Rescue.setPerformanceEnable(true);
        }
        ARouter.init(this);
        //

    }


}
