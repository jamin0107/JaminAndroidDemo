package com.jamin.greendao.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.jamin.greendao.model.DbHistory;

import com.jamin.greendao.dao.DbHistoryDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig dbHistoryDaoConfig;

    private final DbHistoryDao dbHistoryDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        dbHistoryDaoConfig = daoConfigMap.get(DbHistoryDao.class).clone();
        dbHistoryDaoConfig.initIdentityScope(type);

        dbHistoryDao = new DbHistoryDao(dbHistoryDaoConfig, this);

        registerDao(DbHistory.class, dbHistoryDao);
    }
    
    public void clear() {
        dbHistoryDaoConfig.clearIdentityScope();
    }

    public DbHistoryDao getDbHistoryDao() {
        return dbHistoryDao;
    }

}
