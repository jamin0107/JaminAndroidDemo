package com.jamin.android.demo.window;

import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jamin.android.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjieming on 2017/8/16.
 */

public class LogView {

    public static final String TAG = LogView.class.getSimpleName();
    private List<String> logList = new ArrayList<>();
    private LogAdapter logAdapter;
    private ListView listView;
    private View view;

    private WindowManager mWindowManager;
    private LayoutInflater inflate;
    private Application application;

    public int flag = FLAG_NO_TOUCHABLE;
    public static final int FLAG_NO_TOUCHABLE = 1;
    public static final int FLAG_TOUCHABLE = 2;
    public static final int FLAG_HIDE = 3;


    public LogView(Application application) {
        mWindowManager = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
        inflate = (LayoutInflater) application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.application = application;
    }

    public void show() {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 类型
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.x = 0;
        params.y = 0;
        params.gravity = Gravity.LEFT;
        params.format = PixelFormat.TRANSLUCENT;
        params.width = 600;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        view = inflate.inflate(R.layout.window_log_float, null);
        listView = (ListView) view.findViewById(R.id.log_list_view);
        logAdapter = new LogAdapter();
        listView.setAdapter(logAdapter);
        mWindowManager.addView(view, params);
    }


    public void addItem(String itemText) {
        logList.add(itemText);
        logAdapter.notifyDataSetChanged();
        listView.smoothScrollToPosition(logList.size() - 1);
    }

    public void hide() {

    }


    public int changeFlag() {
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        //三种模式切换
        if (flag == FLAG_NO_TOUCHABLE) {
            this.flag = FLAG_TOUCHABLE;
            Log.d("LOG_VIEW", "FLAG_TOUCHABLE");
            lp.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            view.setVisibility(View.VISIBLE);
        } else if (flag == FLAG_TOUCHABLE) {
            this.flag = FLAG_HIDE;
            Log.d("LOG_VIEW", "FLAG_HIDE");
            lp.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            view.setVisibility(View.GONE);
        } else if (flag == FLAG_HIDE) {
            this.flag = FLAG_NO_TOUCHABLE;
            Log.d("LOG_VIEW", "FLAG_NO_TOUCHABLE");
            lp.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            view.setVisibility(View.VISIBLE);
        }
        mWindowManager.updateViewLayout(view, lp);
        return flag;
    }


    class LogAdapter extends BaseAdapter {


        public LogAdapter() {

        }

        @Override
        public int getCount() {
            return logList.size();
        }

        @Override
        public Object getItem(int position) {
            return logList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflate.inflate(R.layout.window_log_item, null, false);
            TextView textView = (TextView) view.findViewById(R.id.log_text_view);
            textView.setText("position = " + position + " --- " + logList.get(position));
            return view;
        }
    }
}
