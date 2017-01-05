package com.jamin.android.demo.remote;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jamin.framework.util.LogUtil;

/**
 * Created by jamin on 2017/1/5.
 */

public class JaminService extends Service {




    public static void startService(Context context) {
        Intent intent = new Intent(context, JaminService.class);
        try {
            context.startService(intent);
        } catch (Exception e) {
            //may catch Security Exception
            e.printStackTrace();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("onBind() called with: intent = [" + intent + "]");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.d("onUnbind() called with: intent = [" + intent + "]");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("onCreate");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("onDestroy");
    }
}
