package com.jamin.rescue.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wangjieming on 2017/8/9.
 */
@Entity
public class CrashModelGreen {

    @Id
    public long id;
    public String tag;          //错误类型
    public long create_time;    //时间
    public String message;      //信息
    @Generated(hash = 1706797823)
    public CrashModelGreen(long id, String tag, long create_time, String message) {
        this.id = id;
        this.tag = tag;
        this.create_time = create_time;
        this.message = message;
    }
    @Generated(hash = 1413481323)
    public CrashModelGreen() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
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




}
