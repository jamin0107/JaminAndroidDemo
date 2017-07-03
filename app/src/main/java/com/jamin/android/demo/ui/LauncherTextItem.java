package com.jamin.android.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseHolder;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.remote.TestRemoteServiceActivity;
import com.jamin.android.demo.ui.gl.GLLaunchActivity;
import com.jamin.android.demo.ui.gl.GLStudyActivity;
import com.jamin.android.demo.ui.anim.LauncherAnimationActivity;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.android.demo.ui.image.LauncherImageActivity;
import com.jamin.android.demo.ui.rxjava.ActivityHistoryOnToday;
import com.jamin.android.demo.ui.rxjava.RxStudyActivity;


/**
 * Created by Administrator on 2016/5/19.
 */
public class LauncherTextItem extends BaseItem<String> {


    public LauncherTextItem(BaseActivity activity, String s) {
        super(activity, s);
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_launch;
    }

    @Override
    public void onBindView(BaseHolder holder, int position) {
        TextView textView = holder.getView(R.id.list_item_launch_tv);
        final String desc = getItemData();
        textView.setText(Html.fromHtml(desc));
        if (MainActivity.LAUNCHER_ANIMATION.equals(desc)) {
            textView.append("(start by ARouter)");
        }
        final Bundle bundle = new Bundle();
        bundle.putString("name", "Jamin Bundle");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.LAUNCHER_ANIMATION.equals(desc)) {
                    ARouter.getInstance().build("/jamin/anim/LauncherAnimationActivity")
                            .withLong("id", 666)
                            .withString("name", "Jamin")
                            .withBundle("jamin", bundle)
                            .navigation();
                } else if (MainActivity.LAUNCHER_IMAGE.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), LauncherImageActivity.class));
                } else if (MainActivity.LAUNCHER_HISTORY_ON_TODAY.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), RxStudyActivity.class));
                } else if (MainActivity.LAUNCHER_GL_VIEW.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), GLLaunchActivity.class));
                } else if (MainActivity.LAUNCHER_SERVICE.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), TestRemoteServiceActivity.class));
                }
            }
        });
    }


}
