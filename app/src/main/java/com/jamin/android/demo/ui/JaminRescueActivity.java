package com.jamin.android.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.util.LogUtil;
import com.jamin.rescue.Rescue;
import com.jamin.rescue.model.LogModel;
import com.jamin.rescue.upload.UploadListener;

/**
 * Created by wangjieming on 2017/7/17.
 */

public class JaminRescueActivity extends BaseActivity {


    Button uploadedBtn, uploadBtn, logBtn;
    CheckBox enableCB;
    long uploadFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jamin_test);
        uploadedBtn = (Button) findViewById(R.id.btn_uploaded);
        uploadedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rescue.uploaded();
            }
        });

        uploadBtn = (Button) findViewById(R.id.btn_upload);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rescue.uploadAll(new UploadListener() {
                    @Override
                    public void upload(String filePath, String tag) {
                        LogUtil.d("uploadFilePath = " + filePath + ", tag = " + tag );
                    }

                });
            }
        });

        logBtn = (Button) findViewById(R.id.btn_log);
        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log();
            }
        });

        enableCB = (CheckBox) findViewById(R.id.btn_enable);
        enableCB.setChecked(Rescue.isEnable());
        enableCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setChecked(isChecked);
                Rescue.setEnable(isChecked);
            }
        });

    }

    private void log() {
        LogModel logModel = new LogModel()
                .withTag("uploadaaacc")
                .withLogLevel(LogModel.LEVEL_DEBUG)
                .withPageName(JaminRescueActivity.class.getSimpleName())
                .withMessage("onCreate");
        Rescue.log(logModel);
    }


}
