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


    public void deleteAll(DBOperateDeleteListener dbOperateDeleteListener) {
        DBManager dbManager = RescueDBFactory.getInstance().getDBManager();
        if (null == dbManager || dbOperateDeleteListener == null) {
            return;
        }
    }



    public void getLogModelList(DBOperateSelectListener dbOperateSelectListener) {
        DBManager dbManager = RescueDBFactory.getInstance().getDBManager();
        if (null == dbManager || dbOperateSelectListener == null) {
            return;
        }
        dbManager.select(LogModel.class , null , null , null ,null , null , null ,null ,dbOperateSelectListener);
    }




}
