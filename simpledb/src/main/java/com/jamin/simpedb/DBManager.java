package com.jamin.simpedb;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class DBManager {


    static final int OPERATE_INSERT = 1;
    static final int OPERATE_UPDATE = 2;
    static final int OPERATE_REPLACE = 3;
    static final int OPERATE_DELETE = 4;
    static final int OPERATE_SELECT = 5;

    //    private static DatabaseDao databaseDao;
    private SQLiteDbHelper appAppDatabase;
    private DBHandler handler;
    private HashMap<Class<?>, String> uniqueMap;
    private boolean isInited = false;

    private DBManager() {
        uniqueMap = new HashMap<>();
    }

    public synchronized boolean isInited() {
        return isInited;
    }

    public synchronized void setInited() {
        this.isInited = true;
    }


    /**
     * 初始化数据化
     *
     * @param context
     * @param dbName
     */
    public void initDataBase(Context context, String dbName, List<Class<?>> models) {
        if (!isInited()) {
            if (appAppDatabase == null) {
                int version = 0;
                try {
                    version = context.getPackageManager().getPackageInfo(
                            context.getPackageName(), 0).versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                appAppDatabase = new SQLiteDbHelper(context, dbName, null,
                        version, models);
            }

            HandlerThread ht = new HandlerThread("dbOption");
            ht.start();
            handler = new DBHandler(appAppDatabase, ht.getLooper());
            setInited();
        }

    }

//    public synchronized static DatabaseDao getInstance() {
//        if (databaseDao == null) {
//            databaseDao = new DatabaseDao();
//        }
//        return databaseDao;
//    }

    public static DBManager newInstance() {
        return new DBManager();
    }


    private void closeDB() {
        if (appAppDatabase != null) {
            appAppDatabase.close();
            appAppDatabase = null;
        }
    }


    public <T extends BaseModel> void select(Class<T> claz, String[] columns, String selection, String[] selectionArgs, String groupBy,
                                             String having, String orderBy, String limit, DBOperateSelectListener listener) {
        if (!isInited()) {
            return;
        }
        DBMsgObj.SelectCondition selectCondition = new DBMsgObj.SelectCondition();
        selectCondition.columns = columns;
        selectCondition.selection = selection;
        selectCondition.selectionArgs = selectionArgs;
        selectCondition.groupBy = groupBy;
        selectCondition.having = having;
        selectCondition.orderBy = orderBy;
        selectCondition.limit = limit;

        DBMsgObj<T> msgObj = new DBMsgObj<T>();
        msgObj.claz = claz;
        msgObj.selectListener = listener;
        msgObj.selectCondition = selectCondition;
        Message msg = handler.obtainMessage();
        msg.what = OPERATE_SELECT;
        msg.obj = msgObj;
        handler.sendMessage(msg);
    }


    public <T extends BaseModel> void insert(Class<T> claz, T t, DBOperateAsyncListener listener) {
        if (claz != null && t != null) {
            DBMsgObj<T> msgObj = new DBMsgObj<T>();
            msgObj.claz = claz;
            ContentValues contentValues = null;
            try {
                contentValues = t.getContentValues();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (contentValues != null) {
                List<DBMsgObj.ContentCondition<T>> contentConditionList = new ArrayList<DBMsgObj.ContentCondition<T>>();
                DBMsgObj.ContentCondition<T> condition = new DBMsgObj.ContentCondition<T>();
                condition.model = t;
                condition.contentValues = contentValues;
                contentConditionList.add(condition);
                msgObj.contentConditionList = contentConditionList;
                msgObj.listener = listener;
                Message msg = new Message();
                msg.what = OPERATE_INSERT;
                msg.obj = msgObj;
                handler.sendMessage(msg);
            }
        }
    }

    public <T extends BaseModel> void insert(Class<T> claz, T t) {
        insert(claz, t, null);
    }


    public <T extends BaseModel> void insert(Class<T> claz, List<T> models, DBOperateAsyncListener listener) {
        if (claz != null && models != null && models.size() > 0) {
            DBMsgObj<T> msgObj = new DBMsgObj<T>();
            msgObj.claz = claz;
            List<DBMsgObj.ContentCondition<T>> list = new ArrayList<DBMsgObj.ContentCondition<T>>();
            for (T model : models) {
                ContentValues contentValues = null;
                try {
                    contentValues = model.getContentValues();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (contentValues != null) {
                    DBMsgObj.ContentCondition<T> condition = new DBMsgObj.ContentCondition<T>();
                    condition.contentValues = contentValues;
                    condition.model = model;
                    list.add(condition);
                }
            }
            if (list.size() > 0) {
                msgObj.contentConditionList = list;
                msgObj.listener = listener;
                Message msg = new Message();
                msg.what = OPERATE_INSERT;
                msg.obj = msgObj;
                handler.sendMessage(msg);
            }
        }
    }

    public <T extends BaseModel> void syncInsert(Class<T> claz, List<T> models) {
        if (appAppDatabase == null) {
            return;
        }
        if (claz != null && models != null && models.size() > 0) {
            String table = DatabaseTools.getTableName(claz);
            SQLiteDatabase database = appAppDatabase
                    .getWritableDatabase();
            if (database == null) {
                return;
            }
            database.beginTransaction();
            try {

                for (T model : models) {
                    ContentValues contentValues = null;
                    try {
                        contentValues = model.getContentValues();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (contentValues == null) {
                        continue;

                    }
                    // retValue=DatabaseUtil.insertNoTx(msg, database);
                    database.insert(table, null, contentValues);
                    // dataChange(table);
                }

                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
            }
        }
    }

    public <T extends BaseModel> long syncInsert(Class<T> claz, T value) {
        return syncInsert(claz, value, true);
    }

    public <T extends BaseModel> long syncInsert(Class<T> claz, T value, boolean bNeedNotify) {
        if (appAppDatabase == null) {
            return -1;
        }
        long retValue = -1;
        try {
            if (value != null) {
                Message msg = new Message();
                String table = DatabaseTools.getTableName(claz);
                ContentValues contentValues = null;
                try {
                    contentValues = value.getContentValues();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


//				if (false == isWithinTransaction()) {
                SQLiteDatabase database = appAppDatabase
                        .getWritableDatabase();
                if (database == null) {
                    return -1;
                }
                database.beginTransaction();
                try {
//						retValue=DatabaseUtil.insertNoTx(msg, database);
                    database.insert(table, null, contentValues);
                    //dataChange(table);
                    database.setTransactionSuccessful();
                } finally {
                    database.endTransaction();
                }
//					if(bNeedNotify){
//					    dataChange(table);
//					}
//				} else {
//					TLSTransactionObject tlsObj = (TLSTransactionObject) mTls
//							.get();
//					tlsObj.setSyncOpDB(true);
//					tlsObj.getMsgList().add(msg);
//				}
            }
            return retValue;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return retValue;
    }

    public <T extends BaseModel> void insert(Class<T> claz, List<T> models) {
        insert(claz, models, null);
    }

    public <T extends BaseModel> void update(Class<T> claz, T t,
                                             DBOperateAsyncListener listener) {
        if (claz != null && t != null) {
            DBMsgObj<T> msgObj = new DBMsgObj<T>();
            msgObj.claz = claz;
            ContentValues contentValues = null;
            try {
                contentValues = t.getContentValues();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (contentValues != null) {
                String unique = getUniqueColumn(claz);
                if (!TextUtils.isEmpty(unique)) {
                    List<DBMsgObj.ContentCondition<T>> contentConditionList = new ArrayList<DBMsgObj.ContentCondition<T>>();
                    DBMsgObj.ContentCondition<T> condition = new DBMsgObj.ContentCondition<T>();
                    condition.contentValues = contentValues;
                    condition.model = t;
                    condition.whereClause = unique + " = ?";
                    condition.whereArgs = new String[]{contentValues
                            .getAsString(unique)};
                    contentConditionList.add(condition);
                    msgObj.contentConditionList = contentConditionList;
                    msgObj.listener = listener;
                    Message msg = new Message();
                    msg.what = OPERATE_UPDATE;
                    msg.obj = msgObj;
                    handler.sendMessage(msg);
                }
            }
        }
    }

    public <T extends BaseModel> void update(Class<T> claz, T t) {
        update(claz, t, null);
    }

    public <T extends BaseModel> void update(Class<T> claz, List<T> models,
                                             DBOperateAsyncListener listener) {
        if (claz != null && models != null && models.size() > 0) {
            DBMsgObj<T> msgObj = new DBMsgObj<T>();
            msgObj.claz = claz;
            List<DBMsgObj.ContentCondition<T>> contentConditionList = new ArrayList<DBMsgObj.ContentCondition<T>>();
            String unique = getUniqueColumn(claz);
            if (!TextUtils.isEmpty(unique)) {
                for (T model : models) {
                    ContentValues contentValues = null;
                    try {
                        contentValues = model.getContentValues();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (contentValues != null) {
                        if (!TextUtils.isEmpty(unique)) {
                            String whereClause = unique + " = ?";
                            String whereArgs = contentValues
                                    .getAsString(unique);
                            DBMsgObj.ContentCondition<T> condition = new DBMsgObj.ContentCondition<T>();
                            condition.whereClause = whereClause;
                            condition.whereArgs = new String[]{whereArgs};
                            condition.contentValues = contentValues;
                            condition.model = model;
                            contentConditionList.add(condition);
                        }
                    }
                }
                msgObj.contentConditionList = contentConditionList;
                msgObj.listener = listener;
                Message msg = new Message();
                msg.what = OPERATE_UPDATE;
                msg.obj = msgObj;
                handler.sendMessage(msg);
            }
        }
    }

    public <T extends BaseModel> void update(Class<T> claz, List<T> models) {
        update(claz, models, null);
    }


    public <T extends BaseModel> void replace(Class<T> claz, T t,
                                              DBOperateAsyncListener listener) {
        if (claz != null && t != null) {
            DBMsgObj<T> msgObj = new DBMsgObj<T>();
            msgObj.claz = claz;
            ContentValues contentValues = null;
            try {
                contentValues = t.getContentValues();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (contentValues != null) {
                List<DBMsgObj.ContentCondition<T>> contentConditionList = new ArrayList<DBMsgObj.ContentCondition<T>>();
                DBMsgObj.ContentCondition<T> condition = new DBMsgObj.ContentCondition<T>();
                condition.contentValues = contentValues;
                condition.model = t;
                contentConditionList.add(condition);
                msgObj.contentConditionList = contentConditionList;
                msgObj.listener = listener;
                Message msg = new Message();
                msg.what = OPERATE_REPLACE;
                msg.obj = msgObj;
                handler.sendMessage(msg);
            }
        }
    }

    public <T extends BaseModel> void replace(Class<T> claz, T t) {
        replace(claz, t, null);
    }

    public <T extends BaseModel> void replace(Class<T> claz, List<T> models,
                                              DBOperateAsyncListener listener) {
        if (claz != null && models != null && models.size() > 0) {
            DBMsgObj<T> msgObj = new DBMsgObj<T>();
            msgObj.claz = claz;
            List<DBMsgObj.ContentCondition<T>> contentConditionList = new ArrayList<DBMsgObj.ContentCondition<T>>();
            for (T model : models) {
                ContentValues contentValues = null;
                try {
                    contentValues = model.getContentValues();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (contentValues != null) {
                    DBMsgObj.ContentCondition<T> condition = new DBMsgObj.ContentCondition<T>();
                    condition.contentValues = contentValues;
                    condition.model = model;
                    contentConditionList.add(condition);
                }
            }
            if (contentConditionList.size() > 0) {
                msgObj.contentConditionList = contentConditionList;
                msgObj.listener = listener;
                Message msg = new Message();
                msg.what = OPERATE_REPLACE;
                msg.obj = msgObj;
                handler.sendMessage(msg);
            }
        }
    }

    public <T extends BaseModel> void replace(Class<T> claz, List<T> models) {
        replace(claz, models, null);
    }

    public <T extends BaseModel> void delete(Class<T> claz, String whereClause,
                                             String[] whereArgs, DBOperateDeleteListener listener) {
        if (claz != null) {
            DBMsgObj<T> msgObj = new DBMsgObj<T>();
            msgObj.claz = claz;
            List<DBMsgObj.ContentCondition<T>> contentConditionList = new ArrayList<DBMsgObj.ContentCondition<T>>();
            DBMsgObj.ContentCondition<T> condition = new DBMsgObj.ContentCondition<T>();
            contentConditionList.add(condition);
            if (!TextUtils.isEmpty(whereClause)) {
                condition.whereClause = whereClause;
            }
            if (whereArgs != null && whereArgs.length > 0) {
                condition.whereArgs = whereArgs;
            }
            msgObj.contentConditionList = contentConditionList;
            msgObj.deleteListener = listener;
            Message msg = new Message();
            msg.what = OPERATE_DELETE;
            msg.obj = msgObj;
            handler.sendMessage(msg);
        }
    }

    public <T extends BaseModel> void delete(Class<T> claz, String whereClause,
                                             String[] whereArgs) {
        delete(claz, whereClause, whereArgs, null);
    }

    private <T extends BaseModel> String getUniqueColumn(Class<T> claz) {
        String unique = uniqueMap.get(claz);
        if (TextUtils.isEmpty(unique)) {
            HashMap<DatabaseField, String> map = DatabaseTools
                    .getDatabaseFields(claz);
            if (map != null && map.size() > 0) {
                Iterator<Entry<DatabaseField, String>> iterator = map
                        .entrySet().iterator();
                String tempUnique = "";
                if (iterator != null) {
                    while (iterator.hasNext()) {
                        Entry<DatabaseField, String> entry = iterator.next();
                        DatabaseField field = entry.getKey();
                        if (field != null) {
                            if (TextUtils.isEmpty(tempUnique)) {
                                tempUnique = field.columnName();
                            }
                            if (field.unique()) {
                                unique = field.columnName();
                                uniqueMap.put(claz, unique);
                                break;
                            }
                        }
                    }
                }
                // 如果没有unique的列,就默认第一列作为unique列
                if (TextUtils.isEmpty(unique) && !TextUtils.isEmpty(tempUnique)) {
                    unique = tempUnique;
                    uniqueMap.put(claz, unique);
                }
            }
        }
        return unique;
    }

    public void recycle() {
        closeDB();
        if (handler != null) {
            handler.getLooper().quit();
            handler = null;
        }
        if (uniqueMap != null) {
            uniqueMap.clear();
            uniqueMap = null;
        }
//        if (databaseDao != null) {
//            databaseDao = null;
//        }
    }
}
