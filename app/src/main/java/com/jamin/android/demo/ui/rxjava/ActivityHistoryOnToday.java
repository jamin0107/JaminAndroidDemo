package com.jamin.android.demo.ui.rxjava;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.util.LogUtil;
import com.jamin.http.HttpService;
import com.jamin.http.model.HistoryOnToday;

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
        setContentView(R.layout.activity_history_on_today);
        Button button = (Button) findViewById(R.id.history_on_today);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });


    }

    private void request() {
        HttpService.getInstance().getTodayOnHistory("11/11")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<HistoryOnToday>() {
                    @Override
                    public void call(HistoryOnToday historyOnToday) {
                        LogUtil.d("Thread id = " + Thread.currentThread().getId() +", Thread name = " + Thread.currentThread().getName());
                        LogUtil.d("s = " + historyOnToday);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HistoryOnToday>() {
                    @Override
                    public void call(HistoryOnToday historyOnToday) {
                        LogUtil.d("Thread id = " + Thread.currentThread().getId() +", Thread name = " + Thread.currentThread().getName());
                        LogUtil.d("s = " + historyOnToday);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.d("Thread id = " + Thread.currentThread().getId() +", Thread name = " + Thread.currentThread().getName());
                        throwable.printStackTrace();

                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        LogUtil.d("Thread id = " + Thread.currentThread().getId() +", Thread name = " + Thread.currentThread().getName());

                    }
                });

    }
}
