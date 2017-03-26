package com.jamin.android.demo.ui.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.adapter.CustomRecyclerViewAdapter;
import com.jamin.android.demo.ui.base.BaseActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jamin on 2016/12/14.
 */

public class LauncherAnimationActivity extends BaseActivity {


    RecyclerView mRecycleView;
    CustomRecyclerViewAdapter mCustomRecyclerViewAdapter;
    List<BaseItem> list = new LinkedList<>();
    public static final String LAUNCHER_CIRCLE_FLYING = "Circle Flying";
    public static final String LAUNCHER_FLY_BACK = "vertical_fly_back_activity";
    public static final String LAUNCHER_LIKE_HEART = "Like Heart";
    public static final String LAUNCHER_PARALLAX_SCROLL = "ParallaxScroll";
    String[] LAUNCHER = new String[]{LAUNCHER_CIRCLE_FLYING, LAUNCHER_FLY_BACK, LAUNCHER_PARALLAX_SCROLL};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        initData();
        initView();
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
