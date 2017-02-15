package com.jamin.android.demo.adapter;

import android.support.v4.app.FragmentActivity;

import com.jamin.android.demo.ui.base.BaseActivity;

/**
 * Created by Administrator on 2016/5/19.
 */
public abstract class BaseItem<Data> {

    private Data mData;
    private BaseActivity activity;


    public BaseItem(BaseActivity activity) {
        this.activity = activity;
    }


    public BaseItem(BaseActivity activity, Data data) {
        this.activity = activity;
        this.mData = data;
    }

    public abstract int getLayoutId();

    public abstract void onBindView(BaseHolder holder, int position);


    public final FragmentActivity getActivity() {
        return activity;
    }


    /**
     * 返回Item绑定的数据
     * @return
     */
    public Data getItemData() {
        return mData;
    }

}
