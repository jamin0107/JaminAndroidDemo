package com.jamin.http.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jamin on 2016/12/23.
 */

public class CloudBeanHistory {


    //日期
    @SerializedName("day")
    @Expose
    public String day;

    //事件日期
    @SerializedName("date")
    @Expose
    public String date;

    //事件id,即下一接口中所用的e_id
    @SerializedName("e_id")
    @Expose
    public Integer e_id;

    //事件标题
    @SerializedName("title")
    @Expose
    public String title;
}
