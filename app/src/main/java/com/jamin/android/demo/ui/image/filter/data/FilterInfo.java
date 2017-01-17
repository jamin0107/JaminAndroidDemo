package com.jamin.android.demo.ui.image.filter.data;

/**
 * Created by jamin on 2017/1/16.
 */

public class FilterInfo {

    public int type;
    public String filterName;
    public int id;

    public FilterInfo(int type, String filterName, int id){
        this.id = id;
        this.type = type;
        this.filterName = filterName;
    }


}
