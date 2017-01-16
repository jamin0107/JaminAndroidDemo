package com.jamin.android.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.adapter.CustomRecyclerViewAdapter;
import com.jamin.android.demo.remote.JaminService;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.util.HardWareEventListener;
import com.jamin.framework.util.LogUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jamin on 2016/12/14.
 */

public class MainActivity extends BaseActivity {


    RecyclerView mRecycleView;
    CustomRecyclerViewAdapter mCustomRecyclerViewAdapter;
    List<BaseItem> list = new LinkedList<>();
    public static final String LAUNCHER_ANIMATION = "Animation";
    public static final String LAUNCHER_IMAGE = "Image";
    public static final String LAUNCHER_HISTORY_ON_TODAY = "DbHistory on Today";
    String[] LAUNCHER = new String[]{LAUNCHER_ANIMATION, LAUNCHER_IMAGE, LAUNCHER_HISTORY_ON_TODAY};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        JaminService.startService(this);
        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HardWareEventListener.getInstance(this).register(hardwareEventReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        HardWareEventListener.getInstance(this).unregister(hardwareEventReceiver);
    }

    /**
     * 点击硬件back，recent app的callback
     */
    HardWareEventListener.Receiver hardwareEventReceiver = new HardWareEventListener.Receiver() {
        @Override
        public void onReceived(String reason) {
            if (HardWareEventListener.REASON_HOME_KEY.equals(reason)) {
                //HOME KEY EVENT
                //Toast.makeText(MainActivity.this, "HOME KEY EVENT", Toast.LENGTH_SHORT).show();
                LogUtil.d("HOME KEY EVENT");
            } else if (HardWareEventListener.REASON_RECENT_APPS.equals(reason)) {
                //RECENT APP EVENT
                //Toast.makeText(MainActivity.this, "RECENT APP EVENT", Toast.LENGTH_SHORT).show();
                LogUtil.d("RECENT APP EVENT");
            }
        }
    };


    private void initData() {
        for (String str : LAUNCHER) {
            list.add(new LauncherTextItem(getActivity(), str));
        }
    }

    private void initView() {
        mRecycleView = (RecyclerView) findViewById(R.id.launch_activity_list_view);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mCustomRecyclerViewAdapter = new CustomRecyclerViewAdapter(list);
        mRecycleView.setAdapter(mCustomRecyclerViewAdapter);
    }


}
