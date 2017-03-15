package com.jamin.android.demo.ui;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseHolder;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.remote.TestRemoteServiceActivity;
import com.jamin.android.demo.ui.gl.GLStudyActivity;
import com.jamin.android.demo.ui.anim.LauncherAnimationActivity;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.android.demo.ui.image.LauncherImageActivity;
import com.jamin.android.demo.ui.rxjava.ActivityHistoryOnToday;


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
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.LAUNCHER_ANIMATION.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), LauncherAnimationActivity.class));
                } else if (MainActivity.LAUNCHER_IMAGE.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), LauncherImageActivity.class));
                } else if (MainActivity.LAUNCHER_HISTORY_ON_TODAY.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), ActivityHistoryOnToday.class));
                }else if (MainActivity.LAUNCHER_GL_VIEW.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), GLStudyActivity.class));
                }else if (MainActivity.LAUNCHER_SERVICE.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), TestRemoteServiceActivity.class));
                }
            }
        });
    }


}
