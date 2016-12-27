package com.jamin.android.demo.ui.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.android.demo.widget.BackgroundVerticalFlyingBackLayout;

/**
 * Created by jamin on 2016/12/27.
 */

public class VerticalFlyBackActivity extends BaseActivity {

    BackgroundVerticalFlyingBackLayout backgroundVerticalFlyLayout2 = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_fly_back_activity);
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
