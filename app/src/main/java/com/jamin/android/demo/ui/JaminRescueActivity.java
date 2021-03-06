package com.jamin.android.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.jamin.framework.base.BaseApplicationHelper;
import com.jamin.android.demo.ProcessManager;
import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.util.LogEventSender;
import com.jamin.framework.util.LogUtil;
import com.jamin.logger.Logger;
import com.jamin.rescue.Rescue;
import com.jamin.rescue.hugo.RescueTimeLog;
import com.jamin.rescue.log.manager.PrepareDataListener;
import com.jamin.rescue.model.KeyPathPerformanceModel;
import com.jamin.rescue.model.LogModel;

import java.util.HashMap;

/**
 * Created by wangjieming on 2017/7/17.
 */
@RescueTimeLog
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

        CheckBox checkBox = (CheckBox) findViewById(R.id.btn_log_enable);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Logger.init(true, "JaminDebug");
                    if (ProcessManager.isUIProcess()) {
                        Logger.showLogWindow(BaseApplicationHelper.getApplication());
                    }
                    Logger.registerLogReceiver(BaseApplicationHelper.getApplication());
                } else {
                    Logger.hideWindow();
                    Logger.unRegisterReceiver(BaseApplicationHelper.getApplication());
                }

            }
        });

        findViewById(R.id.add_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("bac", "345");
                hashMap.put("qwe", "zxc");
                hashMap.put("asd", "2345");
                hashMap.put("asd", "123");
                LogEventSender.sendEvent(BaseApplicationHelper.getApplication(), LogEventSender.DEBUG, "Event : " + "abc" + ",\n propMap = " + new Gson().toJson(hashMap) + "\n");
//                LogEventSender.sendEvent(JaminApplicationHelper.getApplication(), LogEventSender.VERBOSE, "LogMsg-------------");
//                LogEventSender.sendEvent(JaminApplicationHelper.getApplication(), LogEventSender.DEBUG, "LogMsg-------------");
//                LogEventSender.sendEvent(JaminApplicationHelper.getApplication(), LogEventSender.DEBUG, "LogMsg-------------");
                LogEventSender.sendEvent(BaseApplicationHelper.getApplication(), LogEventSender.DEBUG, "Event : " + "abc" + ", propMap = " + new Gson().toJson(hashMap));
//                LogEventSender.sendEvent(JaminApplicationHelper.getApplication(), LogEventSender.DEBUG, "LogMsg-------------");
//                LogEventSender.sendEvent(JaminApplicationHelper.getApplication(), LogEventSender.ERROR, "LogMsg-------------");
                LogEventSender.sendEvent(BaseApplicationHelper.getApplication(), LogEventSender.DEBUG, "Event : " + "abc" + ", propMap = " + new Gson().toJson(hashMap));
//                LogEventSender.sendEvent(JaminApplicationHelper.getApplication(), LogEventSender.DEBUG, "LogMsg-------------");
//                LogEventSender.sendEvent(JaminApplicationHelper.getApplication(), LogEventSender.DEBUG, "LogMsg-------------");
//                LogEventSender.sendEvent(JaminApplicationHelper.getApplication(), LogEventSender.WARN, "LogMsg-------------");
//                LogEventSender.sendEvent(JaminApplicationHelper.getApplication(), LogEventSender.INFO, "LogMsg-------------");
//                LogEventSender.sendEvent(JaminApplicationHelper.getApplication(), LogEventSender.DEBUG, "LogMsg-------------");
//                Logger.e("Logggggggggggggggggggg");
//                Logger.i("Logggggggggggggggggggg");
//                Logger.e("Logggggggggggggggggggg");
//                Logger.e("Logggggggggggggggggggg");
//                Logger.d("Logggggggggggggggggggg");
//                Logger.i("Logggggggggggggggggggg");
//                Logger.v("Logggggggggggggggggggg");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @RescueTimeLog
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
