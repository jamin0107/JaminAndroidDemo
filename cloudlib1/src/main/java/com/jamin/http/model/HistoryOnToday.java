package com.jamin.http.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jamin on 2016/12/23.
 */

public class HistoryOnToday {

    //返回码
    @SerializedName("error_code")
    @Expose
    private Integer errorCode;


    //提示信息
    @SerializedName("reason")
    @Expose
    private String reason;

    @SerializedName("result")
    @Expose
    List<History> list;


}
