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
import com.jamin.http.cache.HttpFileCache;
import com.jamin.http.model.CloudBeanHistoryOnToday;

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
        mLayoutSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });


    }


    private void refresh() {
        request();
    }

    private void request() {
        HttpFileCache<CloudBeanHistoryOnToday> httpFileCache = new HttpFileCache<>(CloudBeanHistoryOnToday.class);
        CloudBeanHistoryOnToday cloudBeanHistoryOnToday = new CloudBeanHistoryOnToday();
        cloudBeanHistoryOnToday.errorCode = 1;
        cloudBeanHistoryOnToday.list = null;
        cloudBeanHistoryOnToday.reason = "test";
        httpFileCache.saveCache(cloudBeanHistoryOnToday).subscribe();

        httpFileCache.getCache().subscribeOn(AndroidSchedulers.mainThread())
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
