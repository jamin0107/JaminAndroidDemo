package com.jamin.android.demo.remote;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.jamin.framework.util.LogUtil;

/**
 * Created by jamin on 2017/1/5.
 */

public class JaminService extends Service {

    public static final int MESSAGER_ADD = 1;
    public static final String BUNDLE_REMOTE_OBJ = "REMOTEOBJ";

    public static void startService(Context context) {
        Intent intent = new Intent(context, JaminService.class);
        try {
            context.startService(intent);
        } catch (Exception e) {
            //may catch Security Exception
            e.printStackTrace();
        }

    }

    IJaminServiceAIDL.Stub mBinder = null;


    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new JaminServiceImpl(this);
        LogUtil.d("onCreate");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("onStartCommand() called with: intent = [" + intent + "]");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("onBind() called with: intent = [" + intent + "]");
//        return mBinder;
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.d("onUnbind() called with: intent = [" + intent + "]");
        return super.onUnbind(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("onDestroy");
    }


    Messenger mMessenger = new Messenger(new Handler() {

        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGER_ADD:
                    try {
                        final int sum = msg.arg1 + msg.arg2;
                        LogUtil.d("get message in remote service");
                        RemoteObj remoteObj = new RemoteObj();
                        remoteObj.setName("from remote service by messager");
                        Message message = Message.obtain(null, MESSAGER_ADD, sum, 0);
                        Bundle bundle = new Bundle();
                        bundle.setClassLoader(RemoteObj.class.getClassLoader());
                        bundle.putParcelable(BUNDLE_REMOTE_OBJ, remoteObj);
                        message.setData(bundle);
                        msg.replyTo.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }

    });
}
