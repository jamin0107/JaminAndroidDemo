package com.jamin.android.demo.window;

import android.app.Application;

/**
 * Created by wangjieming on 2017/8/16.
 */

public class LogFloatLayerControl {


    Application application;
    private LogView logView;
    private ControlView controlView;

    public LogFloatLayerControl(Application application) {
        this.application = application;
        this.logView = new LogView(application);
        this.controlView = new ControlView(application, this);
    }

    public void showLogFloatLayer() {
        logView.show();
        controlView.show();
    }

    public void addItem(String itemText) {
        if (logView != null) {
            logView.addItem(itemText);
        }
    }


    public void hide() {
        logView.hide();
        controlView.hide();
    }


    public int changeLogViewFlag() {
        return logView.changeFlag();
    }
}
