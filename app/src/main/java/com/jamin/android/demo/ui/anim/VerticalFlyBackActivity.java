package com.jamin.android.demo.ui.anim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.android.demo.widget.BackgroundVerticalFlyingBackLayout;
import com.jamin.android.demo.widget.ReboundImageView;

/**
 * Created by jamin on 2016/12/27.
 */

public class VerticalFlyBackActivity extends BaseActivity {

    BackgroundVerticalFlyingBackLayout backgroundVerticalFlyLayout2 = null;
    ReboundImageView reboundImageView, reboundImageView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_fly_back);
        reboundImageView = (ReboundImageView) findViewById(R.id.fly_back_imageview);
        reboundImageView2 = (ReboundImageView) findViewById(R.id.fly_back_imageview2);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic1);
        reboundImageView.setImageBitmap(bitmap);
        reboundImageView.setSpeed(26);
        reboundImageView.startScroll();

        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.pic2);
        reboundImageView2.setImageBitmap(bitmap2);
        reboundImageView2.setSpeed(26);
        reboundImageView2.startScroll();

//        postDelay(new Runnable() {
//            @Override
//            public void run() {
//                reboundImageView.setAnimating(false);
//            }
//        }, 2000);
        startFlyBackView();
    }


    private void startFlyBackView() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.circle_fly_layout);
        if (relativeLayout != null) {
            backgroundVerticalFlyLayout2 = new BackgroundVerticalFlyingBackLayout(this, R.mipmap.img_login_window);
            relativeLayout.addView(backgroundVerticalFlyLayout2);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        backgroundVerticalFlyLayout2.destory();
        reboundImageView.stop();

    }
}
