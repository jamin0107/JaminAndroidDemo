package com.jamin.android.demo.ui.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.adapter.CustomRecyclerViewAdapter;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.android.demo.ui.rxjava.detail.DetailContentItem;
import com.jamin.android.demo.ui.rxjava.detail.DetailPictureItem;
import com.jamin.android.demo.ui.rxjava.detail.DetailTitleItem;
import com.jamin.http.HttpService;
import com.jamin.http.model.detail.CloudBeanHistoryDetail;
import com.jamin.http.model.detail.DetailBean;
import com.jamin.http.model.detail.DetailPicUrlBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ActivityHistoryDetail extends BaseActivity {
    @BindView(R.id.recycler_history_detail)
    RecyclerView mRecyclerHistoryDetail;
    CustomRecyclerViewAdapter mAdapter;
    private BaseActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        ButterKnife.bind(this);
        activity = this;
        int id = analyzeIntent(getIntent());
        requestDetail(id);
    }

    private void requestDetail(int id) {

        HttpService.getInstance().getTodayOnHistoryDetail(id)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<CloudBeanHistoryDetail>() {
                    @Override
                    public void call(CloudBeanHistoryDetail cloudBeanHistoryDetail) {

                    }
                })
                .map(new Func1<CloudBeanHistoryDetail, List<BaseItem>>() {
                    @Override
                    public List<BaseItem> call(CloudBeanHistoryDetail cloudBeanHistoryDetail) {

                        List<BaseItem> items = new ArrayList<>();

                        List<DetailBean> details = cloudBeanHistoryDetail.getResult();
                        for (DetailBean detail : details) {
                            items.add(new DetailTitleItem(activity, detail.getTitle()));
                            items.add(new DetailContentItem(activity, detail.getContent()));
                            List<DetailPicUrlBean> pictures = detail.getPicUrlBeans();
                            for (DetailPicUrlBean picture : pictures) {
                                items.add(new DetailPictureItem(activity, picture));
                            }

                        }
                        return items;
                    }

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<BaseItem>>() {
                    @Override
                    public void call(List<BaseItem> baseItems) {
                        refreshRecyclerView(baseItems);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    private void refreshRecyclerView(List<BaseItem> baseItems) {

        if (mAdapter == null) {
            mAdapter = new CustomRecyclerViewAdapter(baseItems);
            mRecyclerHistoryDetail.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerHistoryDetail.setAdapter(mAdapter);
        } else {
            mAdapter.setData(baseItems);
        }


    }

    private int analyzeIntent(Intent intent) {
        return intent.getIntExtra("e_id", -1);
    }
}
