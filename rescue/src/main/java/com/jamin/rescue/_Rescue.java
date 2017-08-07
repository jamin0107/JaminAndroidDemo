package com.jamin.rescue;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.jamin.rescue.dao.LogModelDao;
import com.jamin.rescue.db.RescueDBFactory;
import com.jamin.rescue.io.NetWorkUtil;
import com.jamin.rescue.io.Utils;
import com.jamin.rescue.model.LogModel;
import com.jamin.rescue.upload.UploadListener;
import com.jamin.rescue.upload.UploadManager;

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


    void _init(Application application) {
        setContext(application);
        versionCode = Utils.getVersionCode(application);
        versionName = Utils.getVersionName(application);
        uploadManager = new UploadManager(application);
        RescueDBFactory.getInstance().initDB(application);
    }


    void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    void setUid(String uid) {
        this.uid = uid;
    }


    void setEnable(boolean enable) {
        this.enable = enable;
    }


    void log(@NonNull LogModel logModel) {
        logModel.setNewWorkType(NetWorkUtil.getNetworkType(application));
        LogModelDao logModelDao = RescueDBFactory.getInstance().logModelDao;
        if (logModelDao != null) {
            if (Rescue.DEBUG) {
                Log.d("_Rescue.log", "insert LogModel = " + new Gson().toJson(logModel));
            }
            RescueDBFactory.getInstance().logModelDao.insert(logModel);
        }

    }


    public void uploadAll(UploadListener uploadListener) {
        uploadManager.uploadAll(uploadListener);
    }



    public void uploaded() {
        uploadManager.uploaded();
    }

}
