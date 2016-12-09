package com.jamin.android.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.jamin.android.demo.R;
import com.jamin.android.demo.widget.BackgroundFlyingLayout;
import com.jamin.android.demo.widget.BackgroundVerticalFlyingLayout;
import com.jamin.android.demo.widget.CornerLayout;

/**
 * Created by jamin on 2016/11/29.
 */

public class CircleFlyActivity extends AppCompatActivity {

    BackgroundFlyingLayout backgroundFlyLayout = null;
    BackgroundVerticalFlyingLayout backgroundFlyLayout2 = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_fly_activity);
        startNasaView();
        startVerticalFlyView();
    }

    private void startVerticalFlyView() {
        CornerLayout relativeLayoutVertical = (CornerLayout) findViewById(R.id.circle_vertical_fly_layout);
        if (relativeLayoutVertical != null) {
            relativeLayoutVertical.bringToFront();
            backgroundFlyLayout2 = new BackgroundVerticalFlyingLayout(this, R.mipmap.img_login_window);
            relativeLayoutVertical.addView(backgroundFlyLayout2);
        }
    }


    private void startNasaView() {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.circle_fly_layout);
        if (rl != null) {
            backgroundFlyLayout = new BackgroundFlyingLayout(this, R.mipmap.nasa);
            rl.addView(backgroundFlyLayout);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        backgroundFlyLayout.destory();
        backgroundFlyLayout2.destory();
    }
}
