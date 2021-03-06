package com.jamin.rescue.db;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jamin.rescue.Rescue;
import com.jamin.rescue.db.upgrade.UpgradeTools;
import com.jamin.rescue.greendao.DaoMaster;
import com.jamin.rescue.greendao.DaoSession;
import com.jamin.rescue.greendao.LogModelGreenDao;
import com.jamin.rescue.model.LogModelGreen;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.rx.RxDao;

/**
 * Created by wangjieming on 2017/8/3.
 */

public class RescueDBFactoryGreen {


    public LogModelGreenDao logModelDao;
    public RxDao<LogModelGreen, Long> logModelRxDao;
    private Application application;

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
            this.application = application;
            RescueDBHelper rescueDBHelper = new RescueDBHelper(application, DB_NAME);
            Database database = rescueDBHelper.getWritableDb();
            DaoMaster mDaoMaster = new DaoMaster(database);
            DaoSession daoSession = mDaoMaster.newSession();
            initDAOs(daoSession);
        }
    }


    private void initDAOs(DaoSession daoSession) {
        logModelDao = daoSession.getLogModelGreenDao();
        logModelRxDao = new RxDao<>(logModelDao);
    }


    class RescueDBHelper extends DaoMaster.OpenHelper {


        public RescueDBHelper(Context context, String name) {
            super(context, name);
        }


        public RescueDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);

        }


        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            super.onUpgrade(db, oldVersion, newVersion);
            if (Rescue.DEBUG) {
                Log.d("RescueDBFactoryGreen", "onUpgrade Database");
            }
            UpgradeTools.onUpgrade(db, DB_NAME, application, LogModelGreenDao.class);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            super.onUpgrade(db, oldVersion, newVersion);
            if (Rescue.DEBUG) {
                Log.d("RescueDBFactoryGreen", "onUpgrade SQLiteDatabase");
            }
        }
    }
}
