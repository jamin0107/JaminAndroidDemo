package com.jamin.framework.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import java.util.HashMap;

/**
 * Created by jamin on 2017/1/16.
 */

public class HardWareEventListener {

    private final Context mAppContext;
    private HashMap<Integer, Receiver> mReceiverMaps = new HashMap<>();

    private static final String REASON_KEY = "reason";
    public static final String REASON_RECENT_APPS = "recentapps";//长按Home键 或者 activity切换键
    public static final String REASON_HOME_KEY = "homekey";//短按Home键
    public static final String REASON_LOCK = "lock";// 锁屏
    public static final String REASON_ASSIST = "assist";// samsung 长按Home键

    public interface Receiver {
        void onReceived(String reason);
    }

    private static HardWareEventListener sDispatcher = null;

    
    private HardWareEventListener(@NonNull Context context) {
        mAppContext = context.getApplicationContext();
    }

    public static HardWareEventListener getInstance(@NonNull Context context) {
        if (sDispatcher == null) {
            synchronized (HardWareEventListener.class) {
                if (sDispatcher == null) {
                    sDispatcher = new HardWareEventListener(context);
                }
            }
        }
        return sDispatcher;
    }

    @UiThread
    public void register(Receiver receiver) {
        if (receiver != null) {
            if (mReceiverMaps.isEmpty()) {
                mAppContext.registerReceiver(mSysDialogReceiver,
                        new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
            }
            mReceiverMaps.put(receiver.hashCode(), receiver);
        }
    }

    @UiThread
    public void unregister(Receiver receiver) {
        if (receiver != null && mReceiverMaps.containsKey(receiver.hashCode())) {
            mReceiverMaps.remove(receiver.hashCode());
            if (mReceiverMaps.isEmpty()) {
                mAppContext.unregisterReceiver(mSysDialogReceiver);
            }
        }
    }

    private BroadcastReceiver mSysDialogReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String reason = intent.getStringExtra("reason");
            //support home key & recent apps key only for now
            if (reason.equals(REASON_KEY)
                    || REASON_RECENT_APPS.equals(reason)
                    || REASON_HOME_KEY.equals(reason)
                    || REASON_LOCK.equals(reason)
                    || REASON_ASSIST.equals(reason)) {
                for (Integer key : mReceiverMaps.keySet()) {
                    if (mReceiverMaps.get(key) != null) {
                        mReceiverMaps.get(key).onReceived(reason);
                    }
                }
            }
        }
    };


}
