package com.jamin.android.demo;

import android.app.Application;
import android.text.TextUtils;

import com.jamin.framework.util.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by jamin on 2017/1/5.
 */

public class ProcessManager {

    public static final int UI_PROCESS = 1;
    public static final int REMOTE_PROCESS = 2;
    public static int s_processType = 0;

    public static final String JAMIN_REMOTE_PROCESS = ":remote";

    public static void init(Application application) {
        String procName = getProcessName(application);
        if (TextUtils.isEmpty(procName)) {
            throw new IllegalStateException("Unknown process name: " + procName);
        }
        if (!procName.contains(":")) {
            s_processType = UI_PROCESS;
        } else if (procName.contains(JAMIN_REMOTE_PROCESS)) {
            s_processType = REMOTE_PROCESS;
        }
    }


    public static boolean isUIProcess() {
        return s_processType == UI_PROCESS;
    }


    public static String getProcessName(Application application) {
        File cmdFile = new File("/proc/self/cmdline");
        if (cmdFile.exists() && !cmdFile.isDirectory()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(cmdFile)));
                String procName = reader.readLine();
                LogUtil.d("/proc/self/cmdline procName:" + procName);
                if (!TextUtils.isEmpty(procName)) {
                    return procName.trim();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return application.getApplicationInfo().processName;
    }
}
