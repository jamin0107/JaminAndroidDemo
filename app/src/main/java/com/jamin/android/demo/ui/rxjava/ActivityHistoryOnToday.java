package com.jamin.android.demo.ui.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.adapter.CustomRecyclerViewAdapter;
import com.jamin.android.demo.db.DBFactory;
import com.jamin.android.demo.db.ModelHelper;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.util.LogUtil;
import com.jamin.greendao.model.DbHistory;
import com.jamin.http.HttpService;
import com.jamin.http.model.CloudBeanHistory;
import com.jamin.http.model.CloudBeanHistoryOnToday;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jamin on 2016/12/23.
 */

public class ActivityHistoryOnToday extends BaseActivity {


    @BindView(R.id.history_on_today)
    Button mHistoryOnToday;
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
        activity=this;
        Button button = (Button) findViewById(R.id.history_on_today);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });
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
        HttpService.getInstance().getTodayOnHistory("1/17")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<CloudBeanHistoryOnToday>() {
                    @Override
                    public void call(CloudBeanHistoryOnToday cloudBeanHistoryOnToday) {
                        if (cloudBeanHistoryOnToday.list == null) {
                            return;
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
                         List<CloudBeanHistory> list=cloudBeanHistoryOnToday.list;
                        List<BaseItem> items=new ArrayList<BaseItem>();
                        for (CloudBeanHistory cloudBeanHistory : list) {
                            items.add(new HistoryItem(activity,cloudBeanHistory));
                        }
                        return items;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<BaseItem>>() {
                    @Override
                    public void call(List<BaseItem> baseItems) {
                        refreshRecyclerView(baseItems);
                        mLayoutSwipeRefresh.setRefreshing(false);
                        LogUtil.d("Thread id = " + Thread.currentThread().getId() + ", Thread name = " + Thread.currentThread().getName());
                        LogUtil.d("notify UI on MainThread");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.d("Thread id = " + Thread.currentThread().getId() + ", Thread name = " + Thread.currentThread().getName());
                        throwable.printStackTrace();

                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        LogUtil.d("Thread id = " + Thread.currentThread().getId() + ", Thread name = " + Thread.currentThread().getName());
                        LogUtil.d("event complete");
                    }
                })
               ;

    }

    private void refreshRecyclerView(List<BaseItem> baseItems) {

        if (mAdapter==null){
            mAdapter=new CustomRecyclerViewAdapter(baseItems);
            mRecyclerHistoryList.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerHistoryList.setAdapter(mAdapter);
        }else {
            mAdapter.setData(baseItems);
        }


    }
}
