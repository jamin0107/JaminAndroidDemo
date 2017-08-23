package com.jamin.http.cache;

import io.reactivex.Observable;

/**
 * Created by wangjieming on 2017/8/22.
 * 这个cache只cache一页数据，只可以添加删除，不可修改
 */

public interface ICache<T> {


    Observable<Boolean> saveCache(T data);

    Observable<T> getCache();

    Observable<Boolean> clearCache();


}
