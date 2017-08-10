package com.jamin.rescue.model;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.jamin.rescue.io.NetWorkUtil;
import com.jamin.rescue.io.SDCardUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wangjieming on 2017/8/2.
 */
@Entity
public class LogModelGreen {


    public static final String LEVEL_ERROR = "ERROR";
    public static final String LEVEL_WARING = "WARING";
    public static final String LEVEL_DEBUG = "DEBUG";
    public static final String COLUMN_NAME_CREATE_TIME = "time";

    @Id(autoincrement = true)
    public Long id;
    public String tag;          //错误类型
    public long create_time;    //时间
    public String message;      //信息
    public String pageName;     //页面名称
    public String logLevel;     //日志等级
    public String sdcardSize;   //SDCardSize
    public String netType;   //SDCardSize

    public LogModelGreen() {
        this.create_time = System.currentTimeMillis();
        this.sdcardSize = SDCardUtil.getSdCardSize().toString();
    }






    @Generated(hash = 354397725)
    public LogModelGreen(Long id, String tag, long create_time, String message,
            String pageName, String logLevel, String sdcardSize, String netType) {
        this.id = id;
        this.tag = tag;
        this.create_time = create_time;
        this.message = message;
        this.pageName = pageName;
        this.logLevel = logLevel;
        this.sdcardSize = sdcardSize;
        this.netType = netType;
    }






    public LogModelGreen withTag(@NonNull String tag) {
        this.tag = tag;
        return this;
    }


    public LogModelGreen withLogLevel(@LogLevel String logLevel) {
        this.logLevel = logLevel;
        return this;
    }


    public LogModelGreen withPageName(@NonNull String pageName) {
        this.pageName = pageName;
        return this;
    }


    public LogModelGreen withMessage(@NonNull String message) {
        this.message = message;
        return this;
    }


    public LogModelGreen withMessage(@NonNull String format, Object... args) {
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






    public Long getId() {
        return this.id;
    }






    public void setId(Long id) {
        this.id = id;
    }






    public String getTag() {
        return this.tag;
    }






    public void setTag(String tag) {
        this.tag = tag;
    }






    public long getCreate_time() {
        return this.create_time;
    }






    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }






    public String getMessage() {
        return this.message;
    }






    public void setMessage(String message) {
        this.message = message;
    }






    public String getPageName() {
        return this.pageName;
    }






    public void setPageName(String pageName) {
        this.pageName = pageName;
    }






    public String getLogLevel() {
        return this.logLevel;
    }






    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }






    public String getSdcardSize() {
        return this.sdcardSize;
    }






    public void setSdcardSize(String sdcardSize) {
        this.sdcardSize = sdcardSize;
    }






    public String getNetType() {
        return this.netType;
    }






    public void setNetType(String netType) {
        this.netType = netType;
    }




    @StringDef({
            LEVEL_ERROR,
            LEVEL_WARING,
            LEVEL_DEBUG
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LogLevel {

    }


}
