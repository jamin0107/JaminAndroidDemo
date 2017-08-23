package com.jamin.android.demo.ui.anim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jamin.framework.base.BaseApplicationHelper;
import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.adapter.CustomRecyclerViewAdapter;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.util.LogUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jamin on 2016/12/14.
 */
@Route(path = "/jamin/anim/LauncherAnimationActivity")
public class LauncherAnimationActivity extends BaseActivity {


    RecyclerView mRecycleView;
    CustomRecyclerViewAdapter mCustomRecyclerViewAdapter;
    List<BaseItem> list = new LinkedList<>();
    public static final String LAUNCHER_CIRCLE_FLYING = "Circle Flying";
    public static final String LAUNCHER_FLY_BACK = "vertical_fly_back_activity";
    public static final String LAUNCHER_LIKE_HEART = "Like Heart";
    public static final String LAUNCHER_RESIZE_LAYOUT = "Login Layout";
    public static final String LAUNCHER_PARALLAX_SCROLL = "ParallaxScroll";
    String[] LAUNCHER = new String[]{LAUNCHER_CIRCLE_FLYING, LAUNCHER_FLY_BACK, LAUNCHER_LIKE_HEART,
            LAUNCHER_RESIZE_LAYOUT, LAUNCHER_PARALLAX_SCROLL};


    @Autowired
    public String name;

    @Autowired
    public long id;

    @Autowired(name = "jamin")
    public Bundle bundle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        // ARouter会自动对字段进行赋值，无需主动获取
        LogUtil.d("name = " + name + ",id = " + id + ", bundle = " + (bundle != null ? bundle.getString("name") : null));
        setContentView(R.layout.activity_launch);
        initData();
        initView();

        //泄露测试
        LauncherAnimMemoryLeakTest launcherAnimMemoryLeakTest = new LauncherAnimMemoryLeakTest();
        launcherAnimMemoryLeakTest.sayHollo();

        BroadcastReceiver innerBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        IntentFilter intentFilter = new IntentFilter("a");
        LocalBroadcastManager.getInstance(BaseApplicationHelper.getAppContext()).registerReceiver(innerBroadcastReceiver, intentFilter);
    }

    private void initData() {
        for (String str : LAUNCHER) {
            list.add(new LauncherAnimationTextItem(getActivity(), str));
        }
    }

    private void initView() {
        mRecycleView = (RecyclerView) findViewById(R.id.launch_activity_list_view);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mCustomRecyclerViewAdapter = new CustomRecyclerViewAdapter(list);
        mRecycleView.setAdapter(mCustomRecyclerViewAdapter);
    }

}
