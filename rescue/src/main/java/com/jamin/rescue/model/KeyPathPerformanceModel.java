package com.jamin.rescue.model;


import com.jamin.simpedb.DatabaseField;
import com.jamin.simpedb.RowIdBaseModel;

/**
 * Created by wangjieming on 2017/8/11.
 */

public class KeyPathPerformanceModel extends RowIdBaseModel {


    public static final String COLUMN_NAME_CREATE_TIME = "create_time";
    public static final String COLUMN_NAME_KEY_PATH = "key_path";

    @DatabaseField(columnName = "from_page")
    public String fromPage;     //来自页面
    @DatabaseField(columnName = "to_page")
    public String toPage;       //去往页面
    @DatabaseField(columnName = COLUMN_NAME_KEY_PATH)
    public int key_path;        //关键路径ID
    @DatabaseField(columnName = COLUMN_NAME_CREATE_TIME)
    public long create_time;    //时间
    @DatabaseField(columnName = "cost_time")
    public long cost_time;      //耗时


    public int times;
    public long minCost;
    public long maxCost;
    public long totalTime;      //所有次数的总耗时  totalTime /times = 平均耗时.


}
