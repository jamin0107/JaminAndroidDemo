package com.jamin.simpedb;

import android.content.ContentValues;

import java.util.List;


/**
 * async event
 *
 * @param <T>
 */
class DBMsgObj<T extends BaseModel> {
    Class<T> claz;
    List<ContentCondition<T>> contentConditionList;
    SelectCondition selectCondition;
    DBOperateAsyncListener listener;
    DBOperateDeleteListener deleteListener;
    DBOperateSelectListener selectListener;


    /**
     * insert , update, replace , delete
     *
     * @param <T>
     */
    static class ContentCondition<T extends BaseModel> {
        String whereClause;
        String[] whereArgs;
        ContentValues contentValues;
        T model;
    }


    /**
     * select
     */
    static class SelectCondition {

        String[] columns;
        String selection;
        String[] selectionArgs;
        String groupBy;
        String having;
        String orderBy;
        String limit;
    }

}
