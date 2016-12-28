package com.jamin.android.demo.ui.anim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
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
    ReboundImageView reboundImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_fly_back_activity);
        reboundImageView = (ReboundImageView) findViewById(R.id.fly_back_imageview);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_login_window);
        reboundImageView.setImageBitmap(bitmap);
        reboundImageView.startScroll();
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

    }
}
