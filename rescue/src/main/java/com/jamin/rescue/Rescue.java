package com.jamin.rescue;

import android.app.Application;

import com.jamin.rescue.model.LogModel;
import com.jamin.rescue.upload.UploadListener;

/**
 * Created by wangjieming on 2017/8/2.
 */

public class Rescue {

    private volatile static boolean hasInit = false;
    public static boolean DEBUG = false;

    private Rescue() {

    }

    /**
     * Init, it must be call before used router.
     */
    public static void init(Application application) {
        _Rescue.getInstance()._init(application);
    }

    public static void setDeviceId(String deviceId) {
        _Rescue.getInstance().setDeviceId(deviceId);
    }


    public static void setUid(String uid) {
        _Rescue.getInstance().setUid(uid);
    }


    public static void setEnable(boolean enable) {
        //TODO:考虑存储到 sp中,不要每次都调用
        _Rescue.getInstance().setEnable(enable);
    }


    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    public static void log(LogModel logModel) {
        _Rescue.getInstance().log(logModel);
    }


    public static void upload(UploadListener uploadListener) {
        _Rescue.getInstance().upload(uploadListener);
    }

    public static void uploaded() {
        _Rescue.getInstance().uploaded();
    }

}
