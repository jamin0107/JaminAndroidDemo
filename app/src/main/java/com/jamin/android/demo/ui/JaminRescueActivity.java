package com.jamin.android.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

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


    Button uploadedBtn , uploadBtn , logBtn;
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
                    public void upload(String filePath, String tag, long uploadedFlag) {
                        LogUtil.d("uploadfilepath = " + filePath + ", tag = " + tag  + ", uploadFlaog = " + uploadedFlag);
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

    }

    private void log() {
        LogModel logModel = new LogModel()
                .withTag("upload22222")
                .withLogLevel(LogModel.LEVEL_DEBUG)
                .withPageName(JaminRescueActivity.class.getSimpleName())
                .withMessage("onCreate");
        Rescue.log(logModel);
    }


}
