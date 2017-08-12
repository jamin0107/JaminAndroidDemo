package com.jamin.rescue.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.jamin.rescue.model.LogModelGreen;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOG_MODEL_GREEN".
*/
public class LogModelGreenDao extends AbstractDao<LogModelGreen, Long> {

    public static final String TABLENAME = "LOG_MODEL_GREEN";

    /**
     * Properties of entity LogModelGreen.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Tag = new Property(1, String.class, "tag", false, "TAG");
        public final static Property Create_time = new Property(2, long.class, "create_time", false, "CREATE_TIME");
        public final static Property Message = new Property(3, String.class, "message", false, "MESSAGE");
        public final static Property PageName = new Property(4, String.class, "pageName", false, "PAGE_NAME");
        public final static Property LogLevel = new Property(5, String.class, "logLevel", false, "LOG_LEVEL");
        public final static Property SdcardSize = new Property(6, String.class, "sdcardSize", false, "SDCARD_SIZE");
        public final static Property NetType = new Property(7, String.class, "netType", false, "NET_TYPE");
        public final static Property Aaaaa = new Property(8, String.class, "aaaaa", false, "AAAAA");
    }


    public LogModelGreenDao(DaoConfig config) {
        super(config);
    }
    
    public LogModelGreenDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOG_MODEL_GREEN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TAG\" TEXT," + // 1: tag
                "\"CREATE_TIME\" INTEGER NOT NULL ," + // 2: create_time
                "\"MESSAGE\" TEXT," + // 3: message
                "\"PAGE_NAME\" TEXT," + // 4: pageName
                "\"LOG_LEVEL\" TEXT," + // 5: logLevel
                "\"SDCARD_SIZE\" TEXT," + // 6: sdcardSize
                "\"NET_TYPE\" TEXT," + // 7: netType
                "\"AAAAA\" TEXT);"); // 8: aaaaa
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOG_MODEL_GREEN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LogModelGreen entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(2, tag);
        }
        stmt.bindLong(3, entity.getCreate_time());
 
        String message = entity.getMessage();
        if (message != null) {
            stmt.bindString(4, message);
        }
 
        String pageName = entity.getPageName();
        if (pageName != null) {
            stmt.bindString(5, pageName);
        }
 
        String logLevel = entity.getLogLevel();
        if (logLevel != null) {
            stmt.bindString(6, logLevel);
        }
 
        String sdcardSize = entity.getSdcardSize();
        if (sdcardSize != null) {
            stmt.bindString(7, sdcardSize);
        }
 
        String netType = entity.getNetType();
        if (netType != null) {
            stmt.bindString(8, netType);
        }
 
        String aaaaa = entity.getAaaaa();
        if (aaaaa != null) {
            stmt.bindString(9, aaaaa);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LogModelGreen entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(2, tag);
        }
        stmt.bindLong(3, entity.getCreate_time());
 
        String message = entity.getMessage();
        if (message != null) {
            stmt.bindString(4, message);
        }
 
        String pageName = entity.getPageName();
        if (pageName != null) {
            stmt.bindString(5, pageName);
        }
 
        String logLevel = entity.getLogLevel();
        if (logLevel != null) {
            stmt.bindString(6, logLevel);
        }
 
        String sdcardSize = entity.getSdcardSize();
        if (sdcardSize != null) {
            stmt.bindString(7, sdcardSize);
        }
 
        String netType = entity.getNetType();
        if (netType != null) {
            stmt.bindString(8, netType);
        }
 
        String aaaaa = entity.getAaaaa();
        if (aaaaa != null) {
            stmt.bindString(9, aaaaa);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LogModelGreen readEntity(Cursor cursor, int offset) {
        LogModelGreen entity = new LogModelGreen( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // tag
            cursor.getLong(offset + 2), // create_time
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // message
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // pageName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // logLevel
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // sdcardSize
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // netType
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // aaaaa
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LogModelGreen entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTag(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreate_time(cursor.getLong(offset + 2));
        entity.setMessage(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPageName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLogLevel(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSdcardSize(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setNetType(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setAaaaa(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LogModelGreen entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LogModelGreen entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LogModelGreen entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
