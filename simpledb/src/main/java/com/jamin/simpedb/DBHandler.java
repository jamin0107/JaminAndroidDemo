package com.jamin.simpedb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.jamin.simpedb.DBMsgObj.ContentCondition;

import java.util.ArrayList;
import java.util.List;


public class DBHandler extends Handler {
    private SQLiteDbHelper appAppDatabase;
    private Handler uiHandler;

    DBHandler(SQLiteDbHelper apAppDatabase, Looper looper) {
        super(looper);
        this.appAppDatabase = apAppDatabase;
        uiHandler = new Handler(Looper.getMainLooper());
        try {
            apAppDatabase.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg != null) {
            try {
                switch (msg.what) {
                    case DBManager.OPERATE_INSERT:
                        insert(msg);
                        break;
                    case DBManager.OPERATE_UPDATE:
                        update(msg);
                        break;
                    case DBManager.OPERATE_REPLACE:
                        replace(msg);
                        break;
                    case DBManager.OPERATE_DELETE:
                        delete(msg);
                        break;
                    case DBManager.OPERATE_SELECT:
                        select(msg);
                        break;
                    default:
                        break;
                }
            } catch (Throwable t) {
//                Log.w("DBHandler", t);
            }
        }
    }

    private <T extends BaseModel> void select(Message msg) {
        if (msg.obj == null) {
            return;
        }
        @SuppressWarnings("unchecked")
        DBMsgObj<T> msgObj = (DBMsgObj<T>) msg.obj;
        DBOperateSelectListener selectListener = msgObj.selectListener;
        if (selectListener == null) {
            return;
        }
        String table = DatabaseTools.getTableName(msgObj.claz);
        if (TextUtils.isEmpty(table)) {
            postToMainLoopSelect(selectListener, null);
            return;
        }
//		SQLiteDatabase database = appAppDatabase.getReadableDatabase();
        SQLiteDatabase database = appAppDatabase.getWritableDatabase();
        if (database != null) {
            DBMsgObj.SelectCondition selectCondition = msgObj.selectCondition;
            Cursor cursor = database.query(table, selectCondition.columns, selectCondition.selection, selectCondition.selectionArgs,
                    selectCondition.groupBy, selectCondition.having, selectCondition.orderBy, selectCondition.limit);
            BaseModel baseModel = null;
            try {
                baseModel = msgObj.claz.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            List<T> list = null;
            if (baseModel != null) {
                try {
                    list = baseModel.getModels(cursor);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            postToMainLoopSelect(selectListener, list);
            return;
        }
        postToMainLoopSelect(selectListener, null);

    }


    private <T extends BaseModel> void insert(Message msg) {
        if (msg.obj == null) {
            return;
        }
        @SuppressWarnings("unchecked")
        DBMsgObj<T> msgObj = (DBMsgObj<T>) msg.obj;
        String tableName = DatabaseTools.getTableName(msgObj.claz);
        if (!TextUtils.isEmpty(tableName) && msgObj.contentConditionList != null && msgObj.contentConditionList.size() > 0) {
            SQLiteDatabase database = appAppDatabase
                    .getWritableDatabase();
            if (database != null) {
                List<T> successModels = new ArrayList<>();
                List<T> failModels = new ArrayList<>();
                database.beginTransaction();
                try {
                    for (ContentCondition<T> condition : msgObj.contentConditionList) {
                        if (condition != null && condition.contentValues != null) {
                            long id = database.insert(tableName, null, condition.contentValues);
                            if (id != -1) {
                                successModels.add(condition.model);
                            } else {
                                failModels.add(condition.model);
                            }
                        }
                    }
                    database.setTransactionSuccessful();// 设置事务处理成功，不设置会自动回滚不提交
                } finally {
                    database.endTransaction();
                    postToMainLoop(msgObj.listener, DBManager.OPERATE_INSERT, msgObj.claz, successModels, failModels);//失败回调到主线程
                }
            }
        }
    }

    private <T extends BaseModel> void update(Message msg) {
        if (msg.obj == null) {
            return;
        }
        @SuppressWarnings("unchecked")
        DBMsgObj<T> msgObj = (DBMsgObj<T>) msg.obj;
        String tableName = DatabaseTools.getTableName(msgObj.claz);
        if (!TextUtils.isEmpty(tableName) && msgObj.contentConditionList != null && msgObj.contentConditionList.size() > 0) {
            SQLiteDatabase database = appAppDatabase
                    .getWritableDatabase();
            if (database != null) {
                List<T> successModels = new ArrayList<>();
                List<T> failModels = new ArrayList<>();
                database.beginTransaction();
                try {
                    for (ContentCondition<T> condition : msgObj.contentConditionList) {
                        if (condition != null && condition.contentValues != null) {
                            int count = database.update(tableName, condition.contentValues, condition.whereClause, condition.whereArgs);
                            if (count > 0) {
                                successModels.add(condition.model);
                            } else {
                                failModels.add(condition.model);
                            }
                        }
                    }
                    database.setTransactionSuccessful();// 设置事务处理成功，不设置会自动回滚不提交
                } finally {
                    database.endTransaction();
                    postToMainLoop(msgObj.listener, DBManager.OPERATE_UPDATE, msgObj.claz, successModels, failModels);
                }
            }
        }
    }

    private <T extends BaseModel> void replace(Message msg) {
        if (msg.obj == null) {
            return;
        }
        @SuppressWarnings("unchecked")
        DBMsgObj<T> msgObj = (DBMsgObj<T>) msg.obj;
        String tableName = DatabaseTools.getTableName(msgObj.claz);
        if (!TextUtils.isEmpty(tableName) && msgObj.contentConditionList != null && msgObj.contentConditionList.size() > 0) {
            SQLiteDatabase database = appAppDatabase
                    .getWritableDatabase();
            if (database != null) {
                List<T> successModels = new ArrayList<>();
                List<T> failModels = new ArrayList<>();
                database.beginTransaction();
                try {
                    for (ContentCondition<T> condition : msgObj.contentConditionList) {
                        if (condition != null && condition.contentValues != null) {
                            long id = database.replace(tableName, null, condition.contentValues);
                            if (id != -1) {
                                successModels.add(condition.model);
                            } else {
                                failModels.add(condition.model);
                            }
                        }
                    }
                    database.setTransactionSuccessful();// 设置事务处理成功，不设置会自动回滚不提交
                } finally {
                    database.endTransaction();
                    postToMainLoop(msgObj.listener, DBManager.OPERATE_REPLACE, msgObj.claz, successModels, failModels);
                }
            }
        }
    }

    private <T extends BaseModel> void delete(Message msg) {
        if (msg.obj == null) {
            return;
        }
        @SuppressWarnings("unchecked")        final DBMsgObj<T> msgObj = (DBMsgObj<T>) msg.obj;
        String tableName = DatabaseTools.getTableName(msgObj.claz);
        if (!TextUtils.isEmpty(tableName) && msgObj.contentConditionList != null && msgObj.contentConditionList.size() > 0) {
            SQLiteDatabase database = appAppDatabase
                    .getWritableDatabase();
            if (database != null) {
                int rows = 0;
                database.beginTransaction();
                try {
                    for (ContentCondition<T> condition : msgObj.contentConditionList) {
                        if (condition != null) {
                            rows += database.delete(tableName, condition.whereClause, condition.whereArgs);
                        }
                    }
                    database.setTransactionSuccessful();// 设置事务处理成功，不设置会自动回滚不提交
                } finally {
                    database.endTransaction();
                    if (msgObj.deleteListener != null) {
                        final int tempRows = rows;
                        uiHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                msgObj.deleteListener.onDeleteCallback(msgObj.claz, tempRows);
                            }
                        });
                    }
                }
            }
        }
    }

    private <T extends BaseModel> void postToMainLoop(final DBOperateAsyncListener listener, @DBOperateType final int type, final Class<T> claz, final List<T> successModels, final List<T> failModels) {
        if (listener != null) {
            uiHandler.post(new Runnable() {

                @Override
                public void run() {
                    try {
                        listener.onPostExecute(type, claz, successModels, failModels);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            });
        }
    }

    private <T extends BaseModel> void postToMainLoopSelect(final DBOperateSelectListener listener, final List<T> dataList) {
        if (listener != null) {
            uiHandler.post(new Runnable() {

                @Override
                public void run() {
                    try {
                        listener.onSelectCallBack(dataList);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            });
        }
    }
}
