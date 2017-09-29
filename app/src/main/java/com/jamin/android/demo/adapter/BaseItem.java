package com.jamin.android.demo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.jamin.android.demo.ui.base.BaseActivity;

/**
 * Created by Administrator on 2016/5/19.
 */
public abstract class BaseItem<Data> {

    private Data mData;
    private BaseActivity activity;
    private Fragment fragment;


    public BaseItem(BaseActivity activity) {
        this.activity = activity;
    }


    public BaseItem(BaseActivity activity, Data data) {
        this.activity = activity;
        this.mData = data;
    }

    public BaseItem(Fragment fragment) {
        this.fragment = fragment;
    }


    public BaseItem(Fragment fragment, Data data) {
        this.fragment = fragment;
        this.mData = data;
    }


    public abstract int getLayoutId();


    public abstract void onBindView(BaseHolder holder, int position);


    protected final FragmentActivity getActivity() {
        if (activity != null) {
            return activity;
        }
        if (fragment != null) {
            return fragment.getActivity();
        }
        return null;
    }


    protected final Fragment getFragment() {
        return fragment;
    }


    /**
     * 返回Item绑定的数据
     *
     * @return
     */
    public Data getItemData() {
        return mData;
    }

}
