package com.jamin.rescue;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.jamin.rescue.dao.LogModelDao;
import com.jamin.rescue.db.RescueDBFactory;
import com.jamin.rescue.db.RescueDBFactoryGreen;
import com.jamin.rescue.db.RescueSP;
import com.jamin.rescue.io.NetWorkUtil;
import com.jamin.rescue.io.Utils;
import com.jamin.rescue.model.LogModel;
import com.jamin.rescue.model.LogModelGreen;
import com.jamin.rescue.upload.UploadListener;
import com.jamin.rescue.upload.UploadManager;
import com.jamin.simpedb.BaseModel;
import com.jamin.simpedb.DBOperateDeleteListener;

/**
 * sdk proxy
 * Created by wangjieming on 2017/8/2.
 */

public class _Rescue {

    private volatile static _Rescue S_INSTANCE = null;
    private volatile static boolean s_HasInit = false;


    private boolean enable = true;          //开关
    private String deviceId;                //设备ID
    private String uid;                     //用户ID
    private int versionCode;                //版本号
    private String versionName;             //版本名
    private Application application;        //
    private UploadManager uploadManager;    //上传模块


    private _Rescue() {

    }


    static _Rescue getInstance() {
        if (S_INSTANCE == null) {
            synchronized (_Rescue.class) {
                if (S_INSTANCE == null) {
                    S_INSTANCE = new _Rescue();
                }
            }
        }
        return S_INSTANCE;
    }


    private void setContext(Application application) {
        this.application = application;
    }


    boolean _init(Application application) {
        setContext(application);
        RescueSP.init(application);
        RescueDBFactory.getInstance().initDB(application);
        RescueDBFactoryGreen.getInstance().initDB(application);
        versionCode = Utils.getVersionCode(application);
        versionName = Utils.getVersionName(application);
        uploadManager = new UploadManager(application);
        return true;
    }

    /**
     * Logic init
     */
    void afterInit() {
        deleteDataBeforeKeepDays();
    }


    void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    void setUid(String uid) {
        this.uid = uid;
    }


    /**
     * set data keep days . default keep 7 days.
     *
     * @param days
     */
    void setDataKeepDays(int days) {
        RescueSP.setDataKeepDays(days);
    }

    void setEnable(boolean enable) {
        this.enable = enable;
        RescueSP.setEnable(enable);
    }

    boolean isEnable() {
        return RescueSP.isEnable();
    }

    void log(@NonNull LogModel logModel) {
        if (!enable) {
            if (Rescue.DEBUG) {
                Log.d("_Rescue.log", "rescue  disable");
            }
            return;
        }
        logModel.setNewWorkType(NetWorkUtil.getNetworkType(application));
        LogModelDao logModelDao = RescueDBFactory.getInstance().logModelDao;
        if (logModelDao != null) {
            if (Rescue.DEBUG) {
                Log.d("_Rescue.log", "insert LogModel = " + new Gson().toJson(logModel));
            }
            RescueDBFactory.getInstance().logModelDao.insert(logModel);
        }


        LogModelGreen logModelGreen = new LogModelGreen();
        logModelGreen.setNewWorkType(NetWorkUtil.getNetworkType(application));
        logModelGreen.tag = logModel.tag;
        logModelGreen.logLevel = logModel.logLevel;
        logModelGreen.pageName = logModel.pageName;
        logModelGreen.message = logModel.message;
        log(logModelGreen);

    }



    private void log(@NonNull LogModelGreen logModelGreen) {
        RescueDBFactoryGreen.getInstance().logModelDao.insert(logModelGreen);
//        RescueDBFactoryGreen.getInstance().logModelRxDao.insert(logModelGreen);
    }



    void uploadAll(UploadListener uploadListener) {
        if (!enable) {
            if (Rescue.DEBUG) {
                Log.d("_Rescue.uploadAll", "rescue  disable");
            }
            return;
        }
        uploadManager.uploadAll(uploadListener);
    }


    void uploaded() {
        if (!enable) {
            if (Rescue.DEBUG) {
                Log.d("_Rescue.uploaded", "rescue  disable");
            }
            return;
        }
        uploadManager.uploaded();
    }

    /**
     * delete data before keeps days
     */
    private void deleteDataBeforeKeepDays() {
        //删除数据库中对应的数据
        LogModelDao logModelDao = RescueDBFactory.getInstance().logModelDao;
        final int days = RescueSP.getDataKeepDays();
        long beforeKeepDays = System.currentTimeMillis() - days * 24 * 60 * 60 * 1000;
        if (logModelDao != null) {
            logModelDao.deleteByTime(beforeKeepDays, new DBOperateDeleteListener() {
                @Override
                public <T extends BaseModel> void onDeleteCallback(Class<T> claz, int rows) {
                    if (Rescue.DEBUG) {
                        Log.d("Rescue.UploadManager", "days = " + days + ", deleteDataBeforeKeepDays.size  = " + rows);
                    }
                }
            });
        }


    }

}
