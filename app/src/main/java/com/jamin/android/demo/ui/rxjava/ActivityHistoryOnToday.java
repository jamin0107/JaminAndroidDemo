package com.jamin.android.demo.ui.rxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.adapter.CustomRecyclerViewAdapter;
import com.jamin.android.demo.db.DBFactory;
import com.jamin.android.demo.db.ModelHelper;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.util.FaceLandMark;
import com.jamin.framework.util.LogUtil;
import com.jamin.greendao.model.DbHistory;
import com.jamin.http.HttpService;
import com.jamin.http.model.CloudBeanHistory;
import com.jamin.http.model.CloudBeanHistoryOnToday;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jamin on 2016/12/23.
 */

public class ActivityHistoryOnToday extends BaseActivity {


    @BindView(R.id.recycler_history_list)
    RecyclerView mRecyclerHistoryList;
    @BindView(R.id.layout_swipeRefresh)
    SwipeRefreshLayout mLayoutSwipeRefresh;

    @BindView(R.id.repeat_number)
    TextView mRepeatNumber;
    String[] repeatText = new String[]{"Jamin", "Hello", "World", "!"};


    private CustomRecyclerViewAdapter mAdapter;
    private BaseActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_on_today);
        ButterKnife.bind(this);
        activity = this;

        mLayoutSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
//        mLayoutSwipeRefresh.setRefreshing(true);
//        refresh();
        startCirclePlay();
//        faceDetect();

    }

    public void faceDetect() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.nasa);
        RxJavaHelper.faceDetectObserve(bitmap).subscribe(new Subscriber<FaceLandMark>() {
            @Override
            public void onCompleted() {
                LogUtil.d("rx subscribe onCompleted");
                LogUtil.d("rx thread id = " + Thread.currentThread().getId());
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d("rx subscribe onError e = " + e.getMessage());
                LogUtil.d("rx thread id = " + Thread.currentThread().getId());
            }

            @Override
            public void onNext(FaceLandMark faceLandMark) {
                LogUtil.d("rx subscribe onNext faceLandMark = " + faceLandMark.pointX);
                LogUtil.d("rx thread id = " + Thread.currentThread().getId());
            }
        });
    }


    public void startCirclePlay() {
        RxJavaHelper.circleRepeat(repeatText)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d("subscribe onCompleted");
                        LogUtil.d("thread id = " + Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("subscribe onError e = " + e.getMessage());
                        LogUtil.d("thread id = " + Thread.currentThread().getId());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("subscribe onNext s = " + s);
                        LogUtil.d("thread id = " + Thread.currentThread().getId());
                        mRepeatNumber.setText(s);
                    }
                });


    }


    private void refresh() {
        request();
    }

    private void request() {
        Observable<CloudBeanHistoryOnToday> observer = HttpService.getInstance().getTodayOnHistory("1/17");
        observer.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<CloudBeanHistoryOnToday>() {
                    @Override
                    public void call(CloudBeanHistoryOnToday cloudBeanHistoryOnToday) {
                        if (cloudBeanHistoryOnToday.list == null) {
                            throw Exceptions.propagate(new Throwable("" + cloudBeanHistoryOnToday.errorCode));
                        }
                        if (cloudBeanHistoryOnToday.errorCode != 0) {
                            LogUtil.d("reason = " + cloudBeanHistoryOnToday.reason);
                            throw Exceptions.propagate(new Throwable("" + cloudBeanHistoryOnToday.errorCode));
                        }
                        //存入数据库
                        List<DbHistory> list = new ArrayList<>();
                        for (CloudBeanHistory history : cloudBeanHistoryOnToday.list) {
                            list.add(ModelHelper.cloudHistoryToDBHistory(history));
                        }
                        DBFactory.getInstance().getHistoryDao().insertInTx(list);
                        LogUtil.d("Thread id = " + Thread.currentThread().getId() + ", Thread name = " + Thread.currentThread().getName());
                        LogUtil.d("DbHistory OnToday Success do first event on bg");
                    }

                })
                .map(new Func1<CloudBeanHistoryOnToday, List<BaseItem>>() {
                    @Override
                    public List<BaseItem> call(CloudBeanHistoryOnToday cloudBeanHistoryOnToday) {

                        List<CloudBeanHistory> list = cloudBeanHistoryOnToday.list;
                        List<BaseItem> items = new ArrayList<BaseItem>();
                        for (CloudBeanHistory cloudBeanHistory : list) {
                            items.add(new HistoryItem(activity, cloudBeanHistory));
                        }
                        return items;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BaseItem>>() {
                    @Override
                    public void onCompleted() {
                        if (mLayoutSwipeRefresh != null) {
                            mLayoutSwipeRefresh.setRefreshing(false);
                        }
                        LogUtil.d("Thread id = " + Thread.currentThread().getId() + ", Thread name = " + Thread.currentThread().getName());
                        LogUtil.d("notify UI onCompleted on MainThread");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mLayoutSwipeRefresh != null) {
                            mLayoutSwipeRefresh.setRefreshing(false);
                        }
                        LogUtil.d("Thread id = " + Thread.currentThread().getId() + ", Thread name = " + Thread.currentThread().getName());
                        LogUtil.d("notify UI onError on MainThread");
                        LogUtil.d("Throwable.msg = " + e.getMessage());
                    }

                    @Override
                    public void onNext(List<BaseItem> baseItems) {
                        refreshRecyclerView(baseItems);
                    }
                });

    }

    private void refreshRecyclerView(List<BaseItem> baseItems) {

        if (baseItems == null || mRecyclerHistoryList == null) {
            return;
        }
        if (mAdapter == null) {
            mAdapter = new CustomRecyclerViewAdapter(baseItems);
            mRecyclerHistoryList.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerHistoryList.setAdapter(mAdapter);
        } else {
            mAdapter.setData(baseItems);
        }

    }
}
