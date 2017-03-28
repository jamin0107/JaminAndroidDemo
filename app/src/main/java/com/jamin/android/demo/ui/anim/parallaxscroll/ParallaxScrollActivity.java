package com.jamin.android.demo.ui.anim.parallaxscroll;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.airbnb.lottie.Cancellable;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.adapter.CustomListViewAdapter;
import com.jamin.android.demo.ui.anim.LauncherAnimationActivity;
import com.jamin.android.demo.ui.anim.LauncherAnimationTextItem;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.android.demo.widget.PullToActionListView;
import com.jamin.framework.util.ScreenTool;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jamin on 2017/3/24.
 */

public class ParallaxScrollActivity extends BaseActivity {


    @BindView(R.id.activity_parallax_recycle_view)
    PullToActionListView mListView;


    CustomListViewAdapter mCustomListViewAdapter;
    List<BaseItem> list = new LinkedList<>();
    View mHeaderView;
    private int height;

    private LottieAnimationView animationView;
    private Cancellable compositionCancellable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
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
        mListView.addHeaderView(mHeaderView);
        mListView.setDefaultImageViewHeight(height);
        mListView.setAdapter(mCustomListViewAdapter);
        mListView.setExpandedListener(new PullToActionListView.ExpandCallback() {
            @Override
            public void expanded() {
                mListView.setVisibility(View.GONE);
                findViewById(R.id.activity_parallax_bg).setVisibility(View.VISIBLE);
                findViewById(R.id.activity_parallax_bg).setBackgroundColor(Color.parseColor(mListView.getRadomColor()));

            }

            @Override
            public void statusChange(float status) {

            }
        });
        animationView = (LottieAnimationView) mHeaderView.findViewById(R.id.list_item_cam_arrow);
        compositionCancellable = LottieComposition.Factory.
                fromAssetFileName(getActivity(), "lottieanimation/drop_down_arrow.json", new OnCompositionLoadedListener() {
                    @Override
                    public void onCompositionLoaded(LottieComposition composition) {
                        animationView.loop(true);
                        animationView.setComposition(composition);
                    }
                });
        RelativeLayout rl = (RelativeLayout) mHeaderView.findViewById(R.id.list_item_cover_layout);
        mListView.setParallaxImageView(rl);
    }

    public View initHeaderView() {
        height = ScreenTool.getScreenPix(getActivity()).heightPixels * 2 / 5;
        View coverView = LayoutInflater.from(this).inflate(R.layout.list_item_parallax_header, null);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        coverView.setLayoutParams(lp);
        return coverView;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mListView.setViewsBounds();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositionCancellable != null)
            compositionCancellable.cancel();
        if (animationView != null)
            animationView.cancelAnimation();
    }
}
