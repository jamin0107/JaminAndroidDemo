package com.jamin.android.demo.db;

import android.app.Application;

import com.jamin.greendao.dao.DaoMaster;
import com.jamin.greendao.dao.DaoSession;
import com.jamin.greendao.dao.DbHistoryDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by jamin on 2017/1/12.
 */

public class DBFactory {


    private static DBFactory dbFactory;


    public static DBFactory getInstance() {
        if (dbFactory == null) {
            synchronized (DBFactory.class) {
                if (dbFactory == null) {
                    dbFactory = new DBFactory();
                }
            }
        }
        return dbFactory;
    }

    /**
     * 数据库是否加密的标识
     */
    public static final boolean ENCRYPTED = false;
    private DaoSession daoSession;



    public void register(Application application) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(application,
                ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }


    public DbHistoryDao getHistoryDao() {
        return daoSession.getDbHistoryDao();
    }

}
