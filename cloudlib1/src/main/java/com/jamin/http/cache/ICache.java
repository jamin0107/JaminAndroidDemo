package com.jamin.http.cache;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by wangjieming on 2017/8/22.
 * 这个cache只cache一页数据，只可以添加删除，不可修改
 */

public interface ICache<T> {


    void saveCache(T data);

    void saveCache(T data, Observer<Boolean> observable);

    Observable<T> getCache();

    void clearCache();

    void clearCache(Observer<Boolean> observable);

}
