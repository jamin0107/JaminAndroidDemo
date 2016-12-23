package com.jamin.android.demo.ui.rxjava;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.util.LogUtil;
import com.jamin.http.HttpService;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jamin on 2016/12/23.
 */

public class ActivityHistoryOnToday extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpService.getInstance().getTodayOnHistory("11/11")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.d("Thread id = " + Looper.myLooper().getThread().getId());
                        LogUtil.d("s = " + s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.d("Thread id = " + Looper.myLooper().getThread().getId());
                        throwable.printStackTrace();

                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        LogUtil.d("Thread Complete id = " + Looper.myLooper().getThread().getId());

                    }
                });


    }
}
