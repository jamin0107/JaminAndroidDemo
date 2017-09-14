package com.jamin.android.demo.ui.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.adapter.CustomRecyclerViewAdapter;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.util.LogUtil;
import com.jamin.http.cache.FileCache;
import com.jamin.http.model.CloudBeanHistoryOnToday;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by jamin on 2016/12/23.
 */

public class ActivityHistoryOnToday extends BaseActivity {


    @BindView(R.id.recycler_history_list)
    RecyclerView mRecyclerHistoryList;
    @BindView(R.id.layout_swipeRefresh)
    SwipeRefreshLayout mLayoutSwipeRefresh;


    private CustomRecyclerViewAdapter mAdapter;
    private BaseActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_on_today);
        ButterKnife.bind(this);
        activity = this;

        refresh();
        saveHashMap();
        mLayoutSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

    }


    private void saveHashMap() {
        FileCache<HashMap> fileCache = new FileCache<>(HashMap.class);
        HashMap<String, Object> hashMap = new HashMap<>();
        CloudBeanHistoryOnToday cloudBeanHistoryOnToday = new CloudBeanHistoryOnToday();
        cloudBeanHistoryOnToday.errorCode = 1;
        cloudBeanHistoryOnToday.list = null;
        cloudBeanHistoryOnToday.reason = "test";
        hashMap.put("aaa", "bbb");
        hashMap.put("aaaa", 111);
        hashMap.put("aaaaa", 111);
        hashMap.put("aaaaaa", cloudBeanHistoryOnToday);
        fileCache.saveCache(hashMap);
        fileCache.getCache().subscribe(new Observer<HashMap>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(HashMap hashMap) {
                LogUtil.d(new Gson().toJson(hashMap));
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });





        FileCache<ArrayList> fileCache1 = new FileCache<>(ArrayList.class);
        ArrayList<Object> list = new ArrayList<>();
        CloudBeanHistoryOnToday cloudBeanHistoryOnToday1 = new CloudBeanHistoryOnToday();
        cloudBeanHistoryOnToday1.errorCode = 1;
        cloudBeanHistoryOnToday1.list = null;
        cloudBeanHistoryOnToday1.reason = "test";
        list.add(111);
        list.add("aaa");
        list.add(cloudBeanHistoryOnToday1);
        fileCache1.saveCache(list);
        fileCache1.getCache().subscribe(new Observer<ArrayList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ArrayList list) {
                LogUtil.d(new Gson().toJson(list));
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private void refresh() {
        request();
    }

    private void request() {
        FileCache<CloudBeanHistoryOnToday> fileCache = new FileCache<>(CloudBeanHistoryOnToday.class);

        CloudBeanHistoryOnToday cloudBeanHistoryOnToday = new CloudBeanHistoryOnToday();
        cloudBeanHistoryOnToday.errorCode = 1;
        cloudBeanHistoryOnToday.list = null;
        cloudBeanHistoryOnToday.reason = "test";

        fileCache.saveCache(cloudBeanHistoryOnToday);

        fileCache.getCache().subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CloudBeanHistoryOnToday>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CloudBeanHistoryOnToday cloudBeanHistoryOnToday) {
                        LogUtil.d(new Gson().toJson(cloudBeanHistoryOnToday));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
