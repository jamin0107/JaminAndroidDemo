package com.jamin.rescue.model;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.jamin.rescue.io.NetWorkUtil;
import com.jamin.rescue.io.SDCardUtil;
import com.jamin.simpedb.DatabaseField;
import com.jamin.simpedb.RowIdBaseModel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wangjieming on 2017/8/2.
 */

public class LogModel extends RowIdBaseModel {


    public static final String LEVEL_ERROR = "ERROR";
    public static final String LEVEL_WARING = "WARING";
    public static final String LEVEL_DEBUG = "DEBUG";
    public static final String LEVEL_WTF = "WTF";


    public static final String COLUMN_NAME_CREATE_TIME = "time";

    @DatabaseField(columnName = "tag")
    public String tag;          //错误类型
    @DatabaseField(columnName = COLUMN_NAME_CREATE_TIME)
    public long create_time;    //时间
    @DatabaseField(columnName = "message")
    public String message;      //信息
    @DatabaseField(columnName = "pageName")
    public String pageName;     //页面名称
    @DatabaseField(columnName = "logLevel")
    public String logLevel;     //日志等级
    @DatabaseField(columnName = "sdcardSize")
    public String sdcardSize;   //SDCardSize
    @DatabaseField(columnName = "net_type")
    public String netType;      //SDCardSize
    @DatabaseField(columnName = "version")
    public String version;      //Version


    public LogModel() {
        this.create_time = System.currentTimeMillis();
        this.sdcardSize = SDCardUtil.getSdCardSize().toString();
    }


    public LogModel withTag(@NonNull String tag) {
        this.tag = tag;
        return this;
    }


    public LogModel withLogLevel(@LogLevel String logLevel) {
        this.logLevel = logLevel;
        return this;
    }


    public LogModel withPageName(@NonNull String pageName) {
        this.pageName = pageName;
        return this;
    }


    public LogModel withMessage(@NonNull String message) {
        this.message = message;
        return this;
    }


    public LogModel withMessage(@NonNull String format, Object... args) {
        this.message = String.format(format, args);
        return this;
    }


    public void setNewWorkType(int type) {
        switch (type) {
            case NetWorkUtil.NET_OFF:
                netType = "NET_OFF";
                break;
            case NetWorkUtil.NET_UNKNOWN:
                netType = "NET_UNKNOWN";
                break;
            case NetWorkUtil.NET_WIFI:
                netType = "NET_WIFI";
                break;
            case NetWorkUtil.NET_2G:
                netType = "NET_2G";
                break;
            case NetWorkUtil.NET_3G:
                netType = "NET_3G";
                break;
            case NetWorkUtil.NET_4G:
                netType = "NET_4G";
                break;
            case NetWorkUtil.NET_EXCEPTION:
                netType = "NET_EXCEPTION";
                break;
        }
    }


    @StringDef({
            LEVEL_ERROR,
            LEVEL_WARING,
            LEVEL_DEBUG,
            LEVEL_WTF
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LogLevel {

    }


}
