package com.jamin.rescue.db;

import android.app.Application;

import com.jamin.rescue.dao.LogModelDao;
import com.jamin.rescue.model.LogModel;
import com.jamin.simpedb.DBManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjieming on 2017/8/3.
 */

public class RescueDBFactory {


    public LogModelDao logModelDao;

    private static RescueDBFactory INSTANCE;
    private DBManager dbManager = null;
    private static final String DB_NAME = "rescue.db";


    public synchronized static RescueDBFactory getInstance() {
        if (null == INSTANCE) {
            synchronized (RescueDBFactory.class) {
                if (null == INSTANCE) {
                    INSTANCE = new RescueDBFactory();

                }
            }
        }
        return INSTANCE;
    }


    public void initDB(Application application) {
        synchronized (this) {
            dbManager = DBManager.newInstance();
            List<Class<?>> models = new ArrayList<>();
            models.add(LogModel.class);
            dbManager.initDataBase(application.getBaseContext(), DB_NAME, models);
            initDAOs();
        }
    }


    public DBManager getDBManager() {
        return dbManager;
    }


    private void initDAOs() {
        logModelDao = new LogModelDao();
    }


}
