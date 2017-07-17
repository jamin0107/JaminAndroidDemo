package com.jamin.android.demo.ui.anim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.jamin.android.demo.JaminApplicationHelper;

/**
 * Created by wangjieming on 2017/7/13.
 * 泄露测试.内部类会隐式持有该类的引用.
 *
 */

public class LauncherAnimMemoryLeakTest {


    public LauncherAnimMemoryLeakTest() {

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };

        IntentFilter intentFilter = new IntentFilter("a");
        LocalBroadcastManager.getInstance(JaminApplicationHelper.getAppContext()).registerReceiver(receiver, intentFilter);

    }


    public void sayHollo() {
        Toast.makeText(JaminApplicationHelper.getAppContext(), "Hello Memory LeakTest", Toast.LENGTH_SHORT).show();
    }

}
