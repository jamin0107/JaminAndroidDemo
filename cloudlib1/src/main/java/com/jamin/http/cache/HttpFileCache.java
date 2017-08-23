package com.jamin.http.cache;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jamin.framework.base.BaseApplicationHelper;
import com.jamin.framework.util.LogUtil;
import com.jamin.framework.util.MD5Util;

import java.io.File;
import java.io.IOException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wangjieming on 2017/8/22.
 * 简单的HTTP 请求的Cache
 */
public class HttpFileCache<T extends HttpBaseResponse> implements ICache<T> {


    private File cacheFile;

    private Class<T> classOfT;

    private T snsDataResponse;

    private final String HTTP_FILE_DIR = "HTTP_CACHE_";

    /**
     * 默认一个response对应一份cache的话，用类名做文件名
     *
     * @param classOfT
     */
    public HttpFileCache(@NonNull Class<T> classOfT) {
        //用name，防止混淆过后导致SimpleName重复
        this(classOfT.getName(), classOfT);
    }


    /**
     * 一个response对应多个chache的情况，key允许自定义
     *
     * @param cacheKey
     * @param classOfT
     */
    public HttpFileCache(@NonNull String cacheKey, Class<T> classOfT) {
        //缓存文件cache file name md5,对应多个cache file，所以允许自定义
        String fileName = MD5Util.getMd5ByString(cacheKey);
        this.classOfT = classOfT;
        cacheFile = BaseApplicationHelper.getApplication().getFileStreamPath(HTTP_FILE_DIR + fileName);
        if (cacheFile == null) {
            return;
        }
        if (!cacheFile.exists()) {
            try {
                cacheFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 异步存储
     *
     * @param data
     * @return 返回boolean 存储是否成功
     */
    @Override
    public Observable<Boolean> saveCache(final T data) {
        return Observable.just(data).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<T, Boolean>() {
                    @Override
                    public Boolean apply(T t) throws Exception {
                        return saveCacheAsync(t);
                    }
                });
//        return Observable.create(new ObservableOnSubscribe<T>() {
//            @Override
//            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
//                emitter.onNext(data);
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .map(new Function<T, Boolean>() {
//                    @Override
//                    public Boolean apply(T t) throws Exception {
//                        return saveCacheAsync(t);
//                    }
//                });
    }


    @Override
    public Flowable<T> getCache() {
        LogUtil.d("getCache");
        return Flowable.just(cacheFile)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<File, T>() {
                    @Override
                    public T apply(File t) throws Exception {
                        return readCacheAsync();
                    }
                });

    }


    @Override
    public Flowable<Boolean> clearCache() {

//        return Single.create(new SingleOnSubscribe<File>() {
//            @Override
//            public void subscribe(SingleEmitter<File> e) throws Exception {
//                e.onSuccess(cacheFile);
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .map(new Function<File, Boolean>() {
//                    @Override
//                    public Boolean apply(File t) throws Exception {
//                        return deleteCacheAsync();
//                    }
//                }).toFlowable();

        return Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                    }
                }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<Integer, Boolean>() {
                    @Override
                    public Boolean apply(Integer t) throws Exception {
                        return deleteCacheAsync();
                    }
                });
    }


    @WorkerThread
    private Boolean saveCacheAsync(T data) {
        LogUtil.d("saveCacheAsync Thread ID = " + Thread.currentThread().getId());
        if (cacheFile == null) {
            return false;
        }
        synchronized (HttpFileCache.class) {
            snsDataResponse = data;
            String jsonStr = new Gson().toJson(data);
            LogUtil.d("HttpFileCache save Cache - " + jsonStr + "");
            IOUtils.saveStringToFile(jsonStr, cacheFile, "UTF-8");
        }
        return true;
    }


    @WorkerThread
    private T readCacheAsync() {
        LogUtil.d("readCacheAsync Thread ID = " + Thread.currentThread().getId());
        if (snsDataResponse != null) {
            return snsDataResponse;
        }
        if (cacheFile == null) {
            return null;
        }
        synchronized (HttpFileCache.class) {
            String str = IOUtils.readFileToString(cacheFile, "UTF-8");
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            try {
                snsDataResponse = new Gson().fromJson(str, classOfT);
            } catch (Exception e) {

            }
            return snsDataResponse;
        }
    }


    @WorkerThread
    private boolean deleteCacheAsync() {
        LogUtil.d("deleteCacheAsync");
        snsDataResponse = null;
        if (cacheFile != null && cacheFile.exists()) {
            return cacheFile.delete();
        }
        return false;
    }


}
