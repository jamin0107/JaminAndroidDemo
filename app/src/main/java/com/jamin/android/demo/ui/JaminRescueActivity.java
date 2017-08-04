package com.jamin.android.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.rescue.Rescue;
import com.jamin.rescue.model.LogModel;

/**
 * Created by wangjieming on 2017/7/17.
 */

public class JaminRescueActivity extends BaseActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jamin_test);
        LogModel logModel = new LogModel().withTag("uploadFailed").withLogLevel(LogModel.LEVEL_DEBUG)
                .withPageName(JaminRescueActivity.class.getSimpleName()).withMessage("onCreate");
        Rescue.log(logModel);
    }



}
