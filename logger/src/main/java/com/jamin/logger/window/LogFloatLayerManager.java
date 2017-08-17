package com.jamin.logger.window;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.UiThread;
import android.support.v4.content.LocalBroadcastManager;


import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by wangjieming on 2017/8/16.
 */

public class LogFloatLayerManager {


    Application application;
    private LogView logView;
    private ControlView controlView;
    public static final String ACTION_VIVA_LOG = "ACTION_VIVA_LOG";
    public static final String INTENT_KEY_MSG = "INTENT_KEY_MSG";
    //    public static final String INTENT_KEY_TAG = "INTENT_KEY_TAG";
    public static final String INTENT_KEY_LEVEL = "INTENT_KEY_LEVEL";
    Handler handler = new Handler(Looper.getMainLooper());

    public LogFloatLayerManager(Application application) {
        this.application = application;
        this.logView = new LogView(application);
        this.controlView = new ControlView(application, this);
    }

    public void registerLogReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_VIVA_LOG);
        LocalBroadcastManager.getInstance(this.application).registerReceiver(broadcastReceiver, intentFilter);
    }

    public void unregisterLogReceiver() {
        LocalBroadcastManager.getInstance(this.application).unregisterReceiver(broadcastReceiver);
    }


    public void showLogFloatLayer() {
        logView.show();
        controlView.show();
    }

    public static String dateToStamp(long timeStamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        Date date = new Date(timeStamp);
        return simpleDateFormat.format(date);
    }

    @UiThread
    public void addItem(@LogInfo.Level final int level, final String itemText) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                LogInfo logInfo = new LogInfo(level, dateToStamp(System.currentTimeMillis()) + " --> " + itemText);
                logView.addItem(logInfo);
            }
        });

    }


    public void hide() {
        logView.hide();
        controlView.hide();
    }


    public int changeLogViewFlag() {
        return logView.changeFlag();
    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_VIVA_LOG.equals(action)) {
                String info = intent.getStringExtra(INTENT_KEY_MSG);
                int level = intent.getIntExtra(INTENT_KEY_LEVEL, LogInfo.DEBUG);
                addItem(level, info);
            }
        }
    };

}
