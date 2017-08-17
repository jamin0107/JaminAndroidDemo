package com.jamin.logger.window;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
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

import com.jamin.logger.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjieming on 2017/8/16.
 */

public class LogView {

    private List<LogInfo> logList = new ArrayList<>();
    private LogAdapter mLogAdapter;
    private ListView mLogListView;
    private View mLogWindowView;

    private WindowManager mWindowManager;
    private LayoutInflater mInflate;

    public int flag = FLAG_NO_TOUCHABLE;
    public static final int FLAG_NO_TOUCHABLE = 1;
    public static final int FLAG_TOUCHABLE = 2;
    public static final int FLAG_HIDE = 3;


    public LogView(Application application) {
        mWindowManager = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
        mInflate = (LayoutInflater) application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void show() {
        if (mLogWindowView == null) {
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
            mLogWindowView = mInflate.inflate(R.layout.window_log_view, null);
            mLogListView = (ListView) mLogWindowView.findViewById(R.id.log_list_view);
            mLogAdapter = new LogAdapter();
            mLogListView.setAdapter(mLogAdapter);
            mWindowManager.addView(mLogWindowView, params);
        } else {
            mLogWindowView.setVisibility(View.VISIBLE);
        }

    }


    public void addItem(LogInfo logInfo) {
        if (logInfo == null || logList == null) {
            return;
        }
        logList.add(logInfo);
        if (logList.size() >= 200) {
            logList.remove(0);
        }
        mLogAdapter.notifyDataSetChanged();
        mLogListView.smoothScrollToPosition(logList.size() - 1);
    }

    public void hide() {
        if (mLogWindowView != null) {
            mLogWindowView.setVisibility(View.GONE);
        }

    }


    public int changeFlag() {
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) mLogWindowView.getLayoutParams();
        //三种模式切换
        if (flag == FLAG_NO_TOUCHABLE) {
            this.flag = FLAG_TOUCHABLE;
            Log.d("LOG_VIEW", "FLAG_TOUCHABLE");
            lp.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            mLogWindowView.setVisibility(View.VISIBLE);
        } else if (flag == FLAG_TOUCHABLE) {
            this.flag = FLAG_HIDE;
            Log.d("LOG_VIEW", "FLAG_HIDE");
            lp.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            mLogWindowView.setVisibility(View.GONE);
        } else if (flag == FLAG_HIDE) {
            this.flag = FLAG_NO_TOUCHABLE;
            Log.d("LOG_VIEW", "FLAG_NO_TOUCHABLE");
            lp.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            mLogWindowView.setVisibility(View.VISIBLE);
        }
        mWindowManager.updateViewLayout(mLogWindowView, lp);
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
            LogListHolder holder;
            if (convertView == null) {
                convertView = mInflate.inflate(R.layout.window_log_item, null, false);
                holder = new LogListHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.log_text_view);
                convertView.setTag(holder);
            } else {
                holder = (LogListHolder) convertView.getTag();
            }
            LogInfo logInfo = logList.get(position);
            if (logInfo == null || holder.textView == null) {
                return convertView;
            }
            Log.d("Logger", "getView Thread =  " + Thread.currentThread().getId() + ", msg = " + logInfo.msg + ", level = " + logInfo.level);
            holder.textView.setText(logInfo.msg);
            switch (logInfo.level) {
                case LogInfo.VERBOSE:
                case LogInfo.INFO:
                    holder.textView.setTextColor(Color.WHITE);
                case LogInfo.DEBUG:
                    holder.textView.setTextColor(Color.GREEN);
                    break;
                case LogInfo.WARN:
                case LogInfo.ERROR:
                case LogInfo.WTF:
                    holder.textView.setTextColor(Color.RED);
                    break;
                default:
                    holder.textView.setTextColor(Color.BLUE);
                    break;
            }
            return convertView;
        }

        private class LogListHolder {
            public TextView textView;
        }
    }
}
