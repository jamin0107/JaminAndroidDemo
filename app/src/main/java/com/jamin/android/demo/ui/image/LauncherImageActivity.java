package com.jamin.android.demo.ui.image;

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

public class LauncherImageActivity extends BaseActivity {


    RecyclerView mRecycleView;
    CustomRecyclerViewAdapter mCustomRecyclerViewAdapter;
    List<BaseItem> list = new LinkedList<>();
    public static final String LAUNCHER_FILTER = "Filter";
    public static final String LAUNCHER_ICON_FONT = "IconFont";
    public static final String LAUNCHER_BITMAP_TEST = "BitmapTest";
    String[] LAUNCHER = new String[]{LAUNCHER_FILTER, LAUNCHER_ICON_FONT, LAUNCHER_BITMAP_TEST};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        initData();
        initView();
    }

    private void initData() {
        for (String str : LAUNCHER) {
            list.add(new LauncherFilterTextItem(getActivity(), str));
        }
    }

    private void initView() {
        mRecycleView = (RecyclerView) findViewById(R.id.launch_activity_list_view);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mCustomRecyclerViewAdapter = new CustomRecyclerViewAdapter(list);
        mRecycleView.setAdapter(mCustomRecyclerViewAdapter);
    }

}
