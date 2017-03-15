package com.jamin.android.demo.remote;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;

import com.jamin.framework.util.LogUtil;

/**
 * Created by jamin on 2017/3/15.
 */

public class JaminServiceImpl extends IJaminServiceAIDL.Stub {

    private Context context;

    public JaminServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public void testMethod(RemoteObj remoteObj) throws RemoteException {
        LogUtil.d("Hello testMethod at remote service");
        int pid = android.os.Process.myPid();
        LogUtil.d("testMethod() pid = " + pid + "--" + remoteObj.toString());
        Intent intent = new Intent(context, JaminRemoteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(JaminRemoteActivity.EXTRA_KEY_REMOTE_OBJ, remoteObj);
        context.startActivity(intent);
    }
}
