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
import com.jamin.rescue.log.manager.PrepareDataListener;
import com.jamin.rescue.model.KeyPathPerformanceModel;
import com.jamin.rescue.model.LogModel;

/**
 * Created by wangjieming on 2017/7/17.
 */

public class JaminRescueActivity extends BaseActivity {


    Button uploadedBtn, uploadBtn, logBtn;
    Button uploadedPerformanceBtn, uploadPerformanceBtn, logPerformanceBtn;
    CheckBox enableCB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jamin_test);
        initView();
    }

    private void initView() {
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
                Rescue.prepareLogData(new PrepareDataListener() {
                    @Override
                    public void prepared(String filePath, String tag) {
                        LogUtil.d("uploadFilePath = " + filePath + ", tag = " + tag);
                    }

                    @Override
                    public void uploading() {

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


        //-----------------------------------------------------------------

        uploadedPerformanceBtn = (Button) findViewById(R.id.btn_perfromance_uploaded);
        uploadPerformanceBtn = (Button) findViewById(R.id.btn_perfromance_upload);
        logPerformanceBtn = (Button) findViewById(R.id.btn_perfromance_log);

        logPerformanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logPerformance();
            }
        });

        uploadPerformanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rescue.preparePerformanceData(new PrepareDataListener() {
                    @Override
                    public void prepared(String filePath, String tag) {

                    }

                    @Override
                    public void uploading() {

                    }
                });
            }
        });
    }

    private void logPerformance() {
        KeyPathPerformanceModel performanceModel = new KeyPathPerformanceModel();
        performanceModel.fromPage = "A";
        performanceModel.toPage = "B";
        performanceModel.key_path = (int) (Math.random() * 10 + 1);
        performanceModel.cost_time = 100 + (int) (Math.random() * 50);
        Rescue.performanceWriter(performanceModel);

    }

    private void log() {
        LogModel logModel = new LogModel()
                .withTag("uploadaaacc")
                .withLogLevel(LogModel.LEVEL_DEBUG)
                .withPageName(JaminRescueActivity.class.getSimpleName())
                .withMessage("onCreate");
        Rescue.log(logModel);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
