package com.jamin.android.demo.remote;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.jamin.android.demo.JaminApplication;
import com.jamin.android.demo.JaminApplicationHelper;
import com.jamin.framework.util.LogUtil;

/**
 * Created by jamin on 2017/3/15.
 */

public class JaminServiceConnect implements IJaminConnect {

    private IJaminServiceAIDL mIJaminServiceAIDL;
    private IRemoteCallBack mRemoteCallback;
    private boolean isBind;

    private static class LazyHolder {
        static final JaminServiceConnect INSTANCE = new JaminServiceConnect();
    }

    public static JaminServiceConnect getInstance() {
        return LazyHolder.INSTANCE;
    }

    private JaminServiceConnect() {
        mRemoteCallback = new JaminRemoteCallBack();
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
                LogUtil.d("onConnectTestMethod from pid = " + pid + ", mRemoteCallback = " + mRemoteCallback);
                RemoteObj remoteObjReturn = mIJaminServiceAIDL.testMethod(remoteObj, mRemoteCallback);
                LogUtil.d("RemoteObj remoteObjReturn = " + remoteObjReturn);
//                Toast.makeText(JaminApplicationHelper.getAppContext(), "RemoteObj remoteObjReturn = " + remoteObjReturn, Toast.LENGTH_SHORT).show();
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


    private class JaminRemoteCallBack extends IRemoteCallBack.Stub {

        @Override
        public void onSuccess(final RemoteObj remoteObj) throws RemoteException {
            LogUtil.d("onSuccess remoteObj = " + remoteObj);
            new Handler(JaminApplicationHelper.getAppContext().getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(JaminApplicationHelper.getAppContext(), "remoteObj = " + remoteObj, Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void onFailed(int responseCode, RemoteObj remoteobj) throws RemoteException {

        }

    }

}
