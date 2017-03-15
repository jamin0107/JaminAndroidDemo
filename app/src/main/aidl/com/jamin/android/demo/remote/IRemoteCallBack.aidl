// RemoteCallBack.aidl
package com.jamin.android.demo.remote;

// Declare any non-default types here with import statements
import com.jamin.android.demo.remote.RemoteObj;

interface IRemoteCallBack {
    void onSuccess(in RemoteObj remoteObj);
    void onFailed(int responseCode,in RemoteObj remoteobj);
}
