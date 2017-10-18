package com.jamin.rescue.db.upgrade;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Log;

import com.jamin.rescue.Rescue;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;

/**
 * Created by wangjieming on 2017/8/10.
 */

public class UpgradeTools {


    public static void onUpgrade(Database db, String dbName, Context context, Class<? extends AbstractDao<?, ?>> daoClass) {
        DaoConfig daoConfig = new DaoConfig(db, daoClass);
        String tableName = daoConfig.tablename;
        if (Rescue.DEBUG) {
            Log.d("RescueDBFactoryGreen", "tableName = " + tableName);
        }
        if (TextUtils.isEmpty(tableName) || TextUtils.isEmpty(dbName) || context == null) {
            return;
        }
        String desc = "select * from " + tableName + " limit 1";
        if (Rescue.DEBUG) {
            Log.d("RescueDBFactoryGreen", "select sql = " + desc);
        }

        Cursor cursor = null;
        if (db != null) {
            try {
                cursor = db.rawQuery(desc, null);
            } catch (SQLiteException e) {
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
            }
        }

        if (cursor == null) {
            return;
        }

        String[] oldColumns = cursor.getColumnNames();

        Property[] newColumns = daoConfig.properties;
        if (oldColumns == null || oldColumns.length <= 0) {
            cursor.close();
            return;
        }

        if (Rescue.DEBUG) {
            StringBuilder oldCStr = new StringBuilder();
            for (String oldC : oldColumns) {
                oldCStr.append(oldC);
                oldCStr.append(" ");
            }

            StringBuilder newCStr = new StringBuilder();
            for (Property newC : newColumns) {
                newCStr.append(newC.columnName);
                newCStr.append(" ");
            }
            Log.d("RescueDBFactoryGreen", "oldColumns = " + oldColumns.length + ",oldCStr = " + oldCStr);
            Log.d("RescueDBFactoryGreen", "newColumns = " + newColumns.length + ",newCStr = " + newCStr);
        }

        for (Property tempProperty : newColumns) {
            String newColumnName = tempProperty.columnName;
            if (TextUtils.isEmpty(newColumnName)) {
                continue;
            }
            boolean isMatch = false;
            for (String old : oldColumns) {
                if (newColumnName.equals(old)) {
                    isMatch = true;
                    break;
                }
            }

            if (isMatch) {
                continue;
            }
            String temp = tempProperty.columnName;
            temp += " ";
            temp += getTypeSQL(tempProperty.type);
//            temp += " ";
//            temp += getDatabaseFieldSQL(key);
            temp += ",";
//            String temp = tempProperty.columnName + " " + ;
            String sql = "ALTER TABLE " + tableName + " ADD " + temp;
            if (sql.contains(",")) {
                sql = sql.replace(",", ";");
            }
            Log.d("RescueDBFactoryGreen", "sql = " + sql);
//            db.execSQL(sql);
        }

        cursor.close();
    }

    public static String getTypeSQL(Class clazz) {
        String sql = "";
        if (clazz != null) {
            if (clazz == long.class
                    || clazz == Long.class
                    || clazz == int.class
                    || clazz == short.class
                    || clazz == byte.class
                    || clazz == boolean.class
                    || clazz == char.class) {
                sql = "integer";
            } else if (clazz == float.class
                    || clazz == double.class) {
                sql = "real";
            } else if (clazz == String.class) {
                sql = "text";
            } else {//全部用gson转换成字符串
                sql = "text";
            }
        }
        return sql;
    }
}
