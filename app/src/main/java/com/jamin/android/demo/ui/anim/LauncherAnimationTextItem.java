package com.jamin.android.demo.ui.anim;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseHolder;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.ui.anim.parallaxscroll.ParallaxScrollActivity;
import com.jamin.android.demo.ui.base.BaseActivity;


/**
 * Created by Administrator on 2016/5/19.
 */
public class LauncherAnimationTextItem extends BaseItem<String> {


    public LauncherAnimationTextItem(BaseActivity activity, String s) {
        super(activity, s);
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_launch;
    }

    @Override
    public void onBindView(BaseHolder holder, int position) {
        final String desc = getItemData();
        TextView textView = holder.findViewById(R.id.list_item_launch_tv);
        textView.setText(Html.fromHtml(desc));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LauncherAnimationActivity.LAUNCHER_CIRCLE_FLYING.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), CircleFlyActivity.class));
                } else if (LauncherAnimationActivity.LAUNCHER_FLY_BACK.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), VerticalFlyBackActivity.class));
                } else if (LauncherAnimationActivity.LAUNCHER_LIKE_HEART.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), LikeActivity.class));
                } else if (LauncherAnimationActivity.LAUNCHER_PARALLAX_SCROLL.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), ParallaxScrollActivity.class));
                } else if (LauncherAnimationActivity.LAUNCHER_RESIZE_LAYOUT.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
    }


}
