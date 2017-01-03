package com.jamin.android.demo;

import android.app.Application;
import android.text.TextUtils;

import com.jamin.framework.util.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by jamin on 2016/11/25.
 */

public class JaminApplication extends Application {

    String mProcName;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(true, "Jamin");
        mProcName = getProcessName();
        LogUtil.d("PROCESS : ----" + mProcName);
    }


    public String getProcessName() {
//        File cmdFile = new File("/proc/self/cmdline");
//
//        if (cmdFile.exists() && !cmdFile.isDirectory()) {
//            BufferedReader reader = null;
//            try {
//                reader = new BufferedReader(new InputStreamReader(new FileInputStream(cmdFile)));
//                String procName = reader.readLine();
//                LogUtil.d("/proc/self/cmdline procName:" + procName);
//                if (!TextUtils.isEmpty(procName)) {
//                    return procName.trim();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
        return getApplicationInfo().processName;
    }
}
