package com.jamin.simpedb;

import android.content.ContentValues;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.Field;

public class RowIdBaseModel extends BaseModel {

    @DatabaseField(columnName = "_id", generatedId = true)
    public long rowId;

    @Override
    public final ContentValues getContentValues() throws IllegalArgumentException, IllegalAccessException {
        ContentValues values = new ContentValues();
        for (Class<?> clazz = getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    if (field != null) {
                        DatabaseField dbField = field.getAnnotation(DatabaseField.class);
                        if (dbField != null) {
                            String columnName = dbField.columnName();
                            if (TextUtils.isEmpty(columnName) || "_id".equals(columnName)) {
                                continue;
                            }
                            field.setAccessible(true);
                            Class<?> type = field.getType();
                            if (type == long.class) {
                                values.put(columnName, (Long) field.get(this));
                            } else if (type == int.class) {
                                values.put(columnName, (Integer) field.get(this));
                            } else if (type == short.class) {
                                values.put(columnName, (Short) field.get(this));
                            } else if (type == byte.class) {
                                values.put(columnName, (Byte) field.get(this));
                            } else if (type == float.class) {
                                values.put(columnName, (Float) field.get(this));
                            } else if (type == double.class) {
                                values.put(columnName, (Double) field.get(this));
                            } else if (type == String.class) {
                                values.put(columnName, (String) field.get(this));
                            } else if (type == char.class) {
                                char charValue = (Character) field.get(this);
                                int intValue = (int) charValue;
                                values.put(columnName, intValue);
                            } else if (type == boolean.class) {
                                values.put(columnName, (Boolean) field.get(this));
                            } else {
                                Gson gson = new Gson();
                                String gsonStr = gson.toJson(field.get(this));
                                values.put(columnName, gsonStr);
                            }
                        }
                    }
                }
            }
        }

        return values;
    }

}
