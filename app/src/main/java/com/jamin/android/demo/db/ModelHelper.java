package com.jamin.android.demo.db;

import com.jamin.greendao.model.DbHistory;
import com.jamin.http.model.CloudBeanHistory;

/**
 * Created by jamin on 2017/1/12.
 */

public class ModelHelper {


    public static DbHistory cloudHistoryToDBHistory(CloudBeanHistory cloudBeanHistory) {
        if (cloudBeanHistory == null) {
            return null;
        }
        DbHistory dbHistory = new DbHistory();
        dbHistory.setE_id(cloudBeanHistory.e_id);
        dbHistory.setDay(cloudBeanHistory.day);
        dbHistory.setDate(cloudBeanHistory.date);
        dbHistory.setTitle(cloudBeanHistory.title);
        return dbHistory;

    }
}
