package com.jamin.android.demo.adapter;

import android.support.v4.app.FragmentActivity;

import com.jamin.android.demo.ui.base.BaseActivity;

/**
 * Created by Administrator on 2016/5/19.
 */
public abstract class BaseItem {

    //    private Activity ac;
    private BaseActivity activity;

//    public BaseItem(ShopActivity ctx) {
//        this.ac = ctx;
//    }

    public BaseItem(BaseActivity activity) {
        this.activity = activity;
    }

    public abstract int getLayoutId();

    public abstract void onBindView(BaseHolder holder, int position);


    public final FragmentActivity getActivity() {
        return activity;
    }


}
