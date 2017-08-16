package com.jamin.android.demo.window;

import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jamin.android.demo.R;

/**
 * Created by wangjieming on 2017/8/16.
 */

public class ControlView {


    LogFloatLayerControl logFloatLayerControl;
    private View view;
    private TextView textView;
    private WindowManager mWindowManager;
    private LayoutInflater inflate;
    private Application application;
    int flag;

    public ControlView(Application application, LogFloatLayerControl logFloatLayerControl) {
        this.logFloatLayerControl = logFloatLayerControl;
        mWindowManager = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
        inflate = (LayoutInflater) application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void show() {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 类型
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.x = 300;
        params.y = 300;
        params.format = PixelFormat.TRANSLUCENT;
        params.width = 200;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view = inflate.inflate(R.layout.window_log_control, null);
        textView = (TextView) view.findViewById(R.id.log_control_btn);
        textView.setText("Mode:浮层不可点");
        GestureDetector gestureDetector = new GestureDetector(view.getContext(), this.simpleOnGestureListener);
        view.setOnTouchListener(new ControlTouchListener(params, mWindowManager, gestureDetector));
        view.setHapticFeedbackEnabled(false);
        mWindowManager.addView(view, params);
    }

    public void hide() {

    }

    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("ControlView", "onDoubleTap");
            int flag = logFloatLayerControl.changeLogViewFlag();
            setModeText(flag);
            return super.onDoubleTap(e);
        }
    };


    private void setModeText(int flag) {
        switch (flag) {
            case LogView.FLAG_NO_TOUCHABLE:
                textView.setText("Mode:浮层不可点");
                break;
            case LogView.FLAG_TOUCHABLE:
                textView.setText("Mode:浮层可滑动");
                break;
            case LogView.FLAG_HIDE:
                textView.setText("Hide");
                break;

        }
    }


    class ControlTouchListener implements View.OnTouchListener {

        private int initialX;
        private int initialY;
        private float initialTouchX;
        private float initialTouchY;
        private WindowManager.LayoutParams paramsF;
        private WindowManager windowManager;
        private GestureDetector gestureDetector;

        public ControlTouchListener(WindowManager.LayoutParams paramsF, WindowManager windowManager, GestureDetector gestureDetector) {
            this.windowManager = windowManager;
            this.paramsF = paramsF;
            this.gestureDetector = gestureDetector;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            this.gestureDetector.onTouchEvent(event);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d("ControlView", "ACTION_DOWN");
                    initialX = paramsF.x;
                    initialY = paramsF.y;
                    initialTouchX = event.getRawX();
                    initialTouchY = event.getRawY();
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
                    paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
                    Log.d("ControlView", "ACTION_MOVE paramsF.x = " + paramsF.x + ", paramsF.y = " + paramsF.y);
                    windowManager.updateViewLayout(v, paramsF);
                default:
                    break;
            }

            return false;
        }
    }

}
