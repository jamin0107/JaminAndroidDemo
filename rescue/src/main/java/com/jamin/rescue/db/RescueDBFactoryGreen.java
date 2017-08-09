package com.jamin.rescue.db;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jamin.rescue.greendao.DaoMaster;
import com.jamin.rescue.greendao.DaoSession;
import com.jamin.rescue.greendao.LogModelGreenDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by wangjieming on 2017/8/3.
 */

public class RescueDBFactoryGreen {


    public LogModelGreenDao logModelDao;

    private static RescueDBFactoryGreen INSTANCE;
    private static final String DB_NAME = "rescue_green.db";

    public synchronized static RescueDBFactoryGreen getInstance() {
        if (null == INSTANCE) {
            synchronized (RescueDBFactoryGreen.class) {
                if (null == INSTANCE) {
                    INSTANCE = new RescueDBFactoryGreen();

                }
            }
        }
        return INSTANCE;
    }


    public void initDB(Application application) {
        synchronized (this) {
            RescueDBHelper rescueDBHelper = new RescueDBHelper(application, DB_NAME);
            Database database = rescueDBHelper.getWritableDb();
            DaoMaster mDaoMaster = new DaoMaster(database);
            DaoSession daoSession = mDaoMaster.newSession();
            initDAOs(daoSession);
        }
    }


    private void initDAOs(DaoSession daoSession) {
        logModelDao = daoSession.getLogModelGreenDao();
    }


    class RescueDBHelper extends DaoMaster.OpenHelper {


        public RescueDBHelper(Context context, String name) {
            super(context, name);
        }


        public RescueDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);

        }
    }
}
