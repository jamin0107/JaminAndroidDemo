package com.jamin.android.demo.remote;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import com.jamin.framework.base.BaseApplicationHelper;
import com.jamin.framework.util.LogUtil;

/**
 * Created by jamin on 2017/3/15.
 */

public class JaminServiceConnectMessenger implements IJaminConnect {

    private boolean isBind;
    Messenger mService;

    private static class LazyHolder {
        static final JaminServiceConnectMessenger INSTANCE = new JaminServiceConnectMessenger();
    }

    public static JaminServiceConnectMessenger getInstance() {
        return LazyHolder.INSTANCE;
    }

    private JaminServiceConnectMessenger() {
    }

    public void onBind() {
        Context context = BaseApplicationHelper.getAppContext();
        Intent intent = new Intent(context, JaminService.class);
        context.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        int pid = android.os.Process.myPid();
        LogUtil.d("onBind pid = " + pid);
        Toast.makeText(context, "onBind pid = " + pid, Toast.LENGTH_SHORT).show();
    }

    public void onUnBind() {
        if (!isBind) {
            return;
        }
        isBind = false;
        Context context = BaseApplicationHelper.getAppContext();
        context.unbindService(mServiceConnection);
        int pid = android.os.Process.myPid();
        LogUtil.d("onUnBind pid = " + pid);
        Toast.makeText(context, "onUnBind pid = " + pid, Toast.LENGTH_SHORT).show();
    }


    public void onConnectTestMethod() {
        Toast.makeText(BaseApplicationHelper.getAppContext(), "let remote service calculate 5 + 10 , maybe coast 5s", Toast.LENGTH_SHORT).show();
        Message message = Message.obtain(null, JaminService.MESSAGER_ADD, 5, 10);
        message.replyTo = mMessenger;
        try {
            mService.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.d("onServiceConnected");
            isBind = true;
            mService = new Messenger(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.d("onServiceDisconnected");
            mService = null;
        }
    };


    private Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case JaminService.MESSAGER_ADD:
                    Bundle bundle = msg.getData();
                    bundle.setClassLoader(RemoteObj.class.getClassLoader());
                    RemoteObj remote = bundle.getParcelable(JaminService.BUNDLE_REMOTE_OBJ);
                    Toast.makeText(BaseApplicationHelper.getAppContext(), remote.getName() + ",sum = " + msg.arg1, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(JaminApplicationHelper.getAppContext(), "sum = " + msg.arg1, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    });
}
