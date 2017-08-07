package com.jamin.rescue.dao;

import com.jamin.rescue.db.RescueDBFactory;
import com.jamin.rescue.model.LogModel;
import com.jamin.simpedb.DBManager;
import com.jamin.simpedb.DBOperateDeleteListener;
import com.jamin.simpedb.DBOperateSelectListener;

/**
 * Created by wangjieming on 2017/8/2.
 */

public class LogModelDao {




    public void insert(LogModel logModel) {
        DBManager dbManager = RescueDBFactory.getInstance().getDBManager();
        if (null == dbManager) {
            return;
        }
        dbManager.insert(LogModel.class, logModel);
    }


    public void deleteByTime(long uploadedTime, DBOperateDeleteListener dbOperateDeleteListener) {
        DBManager dbManager = RescueDBFactory.getInstance().getDBManager();
        if (null == dbManager || dbOperateDeleteListener == null) {
            return;
        }
        String selection = LogModel.COLUMN_NAME_CREATE_TIME + "<=?";
        String[] selectionArgs = new String[]{"" + uploadedTime};
        dbManager.delete(LogModel.class,selection , selectionArgs , dbOperateDeleteListener);
    }



    public void getLogModelList(DBOperateSelectListener dbOperateSelectListener) {
        DBManager dbManager = RescueDBFactory.getInstance().getDBManager();
        if (null == dbManager || dbOperateSelectListener == null) {
            return;
        }
        dbManager.select(LogModel.class , null , null , null ,null , null , null ,null ,dbOperateSelectListener);
    }


    /**
     * select all log before logTime
     * @param logTime
     * @param dbOperateSelectListener
     */
    public void getLogModelListByTime(long logTime, DBOperateSelectListener dbOperateSelectListener) {
        DBManager dbManager = RescueDBFactory.getInstance().getDBManager();
        if (null == dbManager || dbOperateSelectListener == null) {
            return;
        }
        String selection =  LogModel.COLUMN_NAME_CREATE_TIME  + "<=?";
        String[] selectionArgs = new String[]{"" + logTime};
        dbManager.select(LogModel.class, null, selection, selectionArgs, null, null, null, null, dbOperateSelectListener);
    }

}
