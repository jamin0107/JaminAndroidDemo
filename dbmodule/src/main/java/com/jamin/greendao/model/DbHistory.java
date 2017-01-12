package com.jamin.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
/**
 * Created by jamin on 2016/12/23.
 */
@Entity
public class DbHistory {


    @Id
    private Long id;
    //日期
    private String day;

    //事件日期
    private String date;

    //事件id,即下一接口中所用的e_id
    private Integer e_id;

    //事件标题
    private String title;

    @Generated(hash = 2066431302)
    public DbHistory(Long id, String day, String date, Integer e_id, String title) {
        this.id = id;
        this.day = day;
        this.date = date;
        this.e_id = e_id;
        this.title = title;
    }

    @Generated(hash = 302639566)
    public DbHistory() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getE_id() {
        return this.e_id;
    }

    public void setE_id(Integer e_id) {
        this.e_id = e_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
