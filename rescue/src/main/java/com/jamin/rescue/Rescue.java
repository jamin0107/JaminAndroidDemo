package com.jamin.rescue;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jamin.rescue.log.manager.PrepareDataListener;
import com.jamin.rescue.model.KeyPathPerformanceModel;
import com.jamin.rescue.model.LogModel;

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
     * Use DebugLog test Rescue init Cost
     * Rescue: ⇠ init [9ms]
     */
    public static void init(Application application) {
        hasInit = _Rescue.getInstance()._init(application);
        if (hasInit) {
            _Rescue.getInstance().afterInit();
        }
    }


    public static void setDeviceId(String deviceId) {
        _Rescue.getInstance().setDeviceId(deviceId);
    }


    public static void setUid(String uid) {
        _Rescue.getInstance().setUid(uid);
    }


    public static void setDataKeepDays(int days) {
        _Rescue.getInstance().setDataKeepDays(days);
    }

    /**
     * config by cloud whether running Rescue or not.
     *
     * @param enable
     */
    public static void setEnable(boolean enable) {
        _Rescue.getInstance().setEnable(enable);
    }

    public static boolean isEnable() {
        return _Rescue.getInstance().isEnable();
    }

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    /**
     * log event and save to db
     *
     * @param logModel
     */
    public static void log(LogModel logModel) {
        _Rescue.getInstance().log(logModel);
    }


    /**
     * prepared all log from now
     *
     * @param prepareDataListener
     */
    public static void prepareLogData(PrepareDataListener prepareDataListener) {
        _Rescue.getInstance().prepareLogData(prepareDataListener);
    }


    /**
     * when app prepared the log file . tell Rescue uploaded ,
     * Rescue will delete the log file and remove the uploaded log from db.
     */
    public static void uploaded() {
        _Rescue.getInstance().uploaded();
    }


    public static void performanceWriter(@NonNull KeyPathPerformanceModel keyPathPerformanceModel) {
        _Rescue.getInstance().performanceWriter(keyPathPerformanceModel);
    }


    public static void preparePerformanceData(PrepareDataListener prepareDataListener){
        _Rescue.getInstance().preparePerformanceData(prepareDataListener);
    }

}
