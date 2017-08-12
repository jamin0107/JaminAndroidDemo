package com.jamin.rescue.log.manager;

/**
 * Created by wangjieming on 2017/8/2.
 */

public interface PrepareDataListener {


    void prepared(String filePath, String tag);


    void uploading();
}
