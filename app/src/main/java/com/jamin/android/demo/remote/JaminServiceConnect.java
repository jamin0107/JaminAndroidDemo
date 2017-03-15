package com.jamin.android.demo.remote;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.jamin.android.demo.JaminApplicationHelper;
import com.jamin.framework.util.LogUtil;

/**
 * Created by jamin on 2017/3/15.
 */

public class JaminServiceConnect {

    private IJaminServiceAIDL mIJaminServiceAIDL;
    private boolean isBind;

    private static class LazyHolder {
        static final JaminServiceConnect INSTANCE = new JaminServiceConnect();
    }

    public static JaminServiceConnect getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void onBind() {
        Context context = JaminApplicationHelper.getAppContext();
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
        Context context = JaminApplicationHelper.getAppContext();
        context.unbindService(mServiceConnection);
        int pid = android.os.Process.myPid();
        LogUtil.d("onUnBind pid = " + pid);
        Toast.makeText(context, "onUnBind pid = " + pid, Toast.LENGTH_SHORT).show();
    }


    public void onConnectTestMethod() {
        RemoteObj remoteObj = new RemoteObj();
        remoteObj.setName("Jamin");
        remoteObj.setPassword("123456");
        remoteObj.setAge(99);
        if (mIJaminServiceAIDL != null) {
            try {
                int pid = android.os.Process.myPid();
                LogUtil.d("onConnectTestMethod from pid = " + pid);
                mIJaminServiceAIDL.testMethod(remoteObj);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    ServiceConnection mServiceConnection = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.d("onServiceConnected");
            isBind = true;
            mIJaminServiceAIDL = IJaminServiceAIDL.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.d("onServiceDisconnected");
            mIJaminServiceAIDL = null;
        }
    };


}
