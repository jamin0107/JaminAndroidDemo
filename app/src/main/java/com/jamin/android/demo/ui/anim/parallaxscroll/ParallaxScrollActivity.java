package com.jamin.android.demo.ui.anim.parallaxscroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.adapter.CustomListViewAdapter;
import com.jamin.android.demo.adapter.CustomRecyclerViewAdapter;
import com.jamin.android.demo.ui.anim.LauncherAnimationActivity;
import com.jamin.android.demo.ui.anim.LauncherAnimationTextItem;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.android.demo.widget.ParallaxScrollListView;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jamin on 2017/3/24.
 */

public class ParallaxScrollActivity extends BaseActivity {


    @BindView(R.id.activity_parallax_recycle_view)
    ParallaxScrollListView mRecycleView;


    CustomListViewAdapter mCustomListViewAdapter;
    List<BaseItem> list = new LinkedList<>();
    View mHeaderView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax_scroll);
        ButterKnife.bind(this);
        initData();
        initView();
    }


    private void initData() {
        mHeaderView = initHeaderView();
        for (int i = 0; i < 20; i++) {
            list.add(new LauncherAnimationTextItem(getActivity(), LauncherAnimationActivity.LAUNCHER_CIRCLE_FLYING));
        }
    }

    private void initView() {
        mCustomListViewAdapter = new CustomListViewAdapter(list);
        mRecycleView.addHeaderView(mHeaderView);
        mRecycleView.setAdapter(mCustomListViewAdapter);
        RelativeLayout rl = (RelativeLayout) mHeaderView.findViewById(R.id.list_item_cover_layout);
        mRecycleView.setParallaxImageView(rl);
    }

    public View initHeaderView() {
        View coverView = LayoutInflater.from(this).inflate(R.layout.list_item_parallax_header, null);
        return coverView;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mRecycleView.setViewsBounds();
        }
    }

}
