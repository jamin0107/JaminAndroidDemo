package com.jamin.android.demo.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;




/**
 * Created by jamin on 2016/12/14.
 */

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void hideTitleBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar actionBar = getSupportActionBar(); //得到ActionBar
        if (actionBar != null) {
            actionBar.hide(); //隐藏ActionBar
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public BaseActivity getActivity() {
        return this;
    }


}
