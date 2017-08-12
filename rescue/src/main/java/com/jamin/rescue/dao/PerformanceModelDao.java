package com.jamin.rescue.dao;


import android.util.Log;

import com.jamin.rescue.Rescue;
import com.jamin.rescue.db.RescueDBFactory;
import com.jamin.rescue.model.KeyPathPerformanceModel;
import com.jamin.simpedb.DBManager;
import com.jamin.simpedb.DBOperateDeleteListener;
import com.jamin.simpedb.DBOperateSelectListener;


/**
 * Created by wangjieming on 2017/8/2.
 */

public class PerformanceModelDao {


    public void insert(KeyPathPerformanceModel keyPathPerformanceModel) {
        DBManager dbManager = RescueDBFactory.getInstance().getDBManager();
        if (null == dbManager) {
            return;
        }
        dbManager.insert(KeyPathPerformanceModel.class, keyPathPerformanceModel);
    }


    public void deleteByTime(long uploadedTime, DBOperateDeleteListener dbOperateDeleteListener) {
        DBManager dbManager = RescueDBFactory.getInstance().getDBManager();
        if (null == dbManager || dbOperateDeleteListener == null) {
            return;
        }
        String selection = KeyPathPerformanceModel.COLUMN_NAME_CREATE_TIME + "<=?";
        String[] selectionArgs = new String[]{"" + uploadedTime};
        if (Rescue.DEBUG) {
            Log.d("Rescue", "LogDaoDelete uploadedTime  = " + uploadedTime);
        }
        dbManager.delete(KeyPathPerformanceModel.class, selection, selectionArgs, dbOperateDeleteListener);
    }


//    public void getPerformanceModelList(DBOperateSelectListener dbOperateSelectListener) {
//        DBManager dbManager = RescueDBFactory.getInstance().getDBManager();
//        if (null == dbManager || dbOperateSelectListener == null) {
//            return;
//        }
//        dbManager.select(PerformanceModel.class , null , null , null ,null , null , null ,null ,dbOperateSelectListener);
//    }


    /**
     * select all log before logTime
     *
     * @param logTime
     * @param dbOperateSelectListener
     */
    public void getPerformanceModelListByTime(long logTime, DBOperateSelectListener dbOperateSelectListener) {
        DBManager dbManager = RescueDBFactory.getInstance().getDBManager();
        if (null == dbManager || dbOperateSelectListener == null) {
            return;
        }
        String selection = KeyPathPerformanceModel.COLUMN_NAME_CREATE_TIME + "<=?";
        String[] selectionArgs = new String[]{String.valueOf(logTime)};
        String orderBy = KeyPathPerformanceModel.COLUMN_NAME_KEY_PATH + " ASC";
        dbManager.select(KeyPathPerformanceModel.class, null, selection, selectionArgs, null, null, orderBy, null, dbOperateSelectListener);
    }

}
