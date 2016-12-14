package com.jamin.android.demo.ui.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.jamin.android.demo.R;
import com.jamin.android.demo.widget.BackgroundFlyingLayout;
import com.jamin.android.demo.widget.BackgroundVerticalFlyingLayout;
import com.jamin.android.demo.widget.CornerLayout;
import com.jamin.android.demo.widget.CornerLayout2;

/**
 * Created by jamin on 2016/11/29.
 */

public class CircleFlyActivity extends AppCompatActivity {

    BackgroundFlyingLayout backgroundFlyLayout = null;
    BackgroundVerticalFlyingLayout backgroundVerticalFlyLayout = null;
    BackgroundVerticalFlyingLayout backgroundVerticalFlyLayout2 = null;

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
            backgroundVerticalFlyLayout = new BackgroundVerticalFlyingLayout(this, R.mipmap.img_login_window);
            relativeLayoutVertical.addView(backgroundVerticalFlyLayout);
        }

        CornerLayout2 relativeLayoutVertical2 = (CornerLayout2) findViewById(R.id.circle_vertical_fly_layout2);
        if (relativeLayoutVertical2 != null) {
            relativeLayoutVertical2.bringToFront();
            backgroundVerticalFlyLayout2 = new BackgroundVerticalFlyingLayout(this, R.mipmap.img_login_window);
            relativeLayoutVertical2.addView(backgroundVerticalFlyLayout2);
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
        backgroundVerticalFlyLayout.destory();
        backgroundVerticalFlyLayout2.destory();

    }
}
