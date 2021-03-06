package com.jamin.rescue.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.jamin.rescue.model.CrashModelGreen;
import com.jamin.rescue.model.LogModelGreen;

import com.jamin.rescue.greendao.CrashModelGreenDao;
import com.jamin.rescue.greendao.LogModelGreenDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig crashModelGreenDaoConfig;
    private final DaoConfig logModelGreenDaoConfig;

    private final CrashModelGreenDao crashModelGreenDao;
    private final LogModelGreenDao logModelGreenDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        crashModelGreenDaoConfig = daoConfigMap.get(CrashModelGreenDao.class).clone();
        crashModelGreenDaoConfig.initIdentityScope(type);

        logModelGreenDaoConfig = daoConfigMap.get(LogModelGreenDao.class).clone();
        logModelGreenDaoConfig.initIdentityScope(type);

        crashModelGreenDao = new CrashModelGreenDao(crashModelGreenDaoConfig, this);
        logModelGreenDao = new LogModelGreenDao(logModelGreenDaoConfig, this);

        registerDao(CrashModelGreen.class, crashModelGreenDao);
        registerDao(LogModelGreen.class, logModelGreenDao);
    }
    
    public void clear() {
        crashModelGreenDaoConfig.clearIdentityScope();
        logModelGreenDaoConfig.clearIdentityScope();
    }

    public CrashModelGreenDao getCrashModelGreenDao() {
        return crashModelGreenDao;
    }

    public LogModelGreenDao getLogModelGreenDao() {
        return logModelGreenDao;
    }

}
