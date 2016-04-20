package com.jamin.android.demo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.framework.util.MD5Util;

/**
 * Created by Jamin on 2016/4/20.
 */
public class MainTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        initView();

    }

    private void initView() {
        TextView tv = (TextView) findViewById(R.id.text_view);
        String text = "123456";
        String MD5 = MD5Util.getMd5ByString(text);
        tv.setText(MD5);
    }


}
