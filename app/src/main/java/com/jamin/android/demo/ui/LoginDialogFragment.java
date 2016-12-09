package com.jamin.android.demo.ui;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.widget.BaseDialogFragment;
import com.jamin.framework.util.LogUtil;

/**
 * Created by jamin on 2016/11/24.
 */

public class LoginDialogFragment extends BaseDialogFragment {


    public static LoginDialogFragment newInstance(){
        LoginDialogFragment ldf = new LoginDialogFragment();
        return ldf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login_dialog, null);
        initView(view);
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
//                .setTitle("Hello World")
//                .setMessage("Hello Msg !!!")
                .setPositiveButton("OK" , null)
                .setNeutralButton("OK NEUTRAL ", null)
                .setNegativeButton("CANCEL" , null)
                .create();
        return dialog;
    }


    LinearLayout otherWayLoginLayout = null;
    TextView textView = null;

    private void initView(final View view) {
        otherWayLoginLayout = (LinearLayout) view.findViewById(R.id.explore_login_other_way_layout);
        textView = (TextView) view.findViewById(R.id.policy);
        Button showOtherWayButton = (Button) view.findViewById(R.id.explore_show_login_way);
        showOtherWayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });

    }

    public void startAnimation(){
        float density = getResources().getDisplayMetrics().density;
        int moveHeight = (int) (density * 67 + 0.5);
        LogUtil.d("moveHeight = " + moveHeight);
        ValueAnimator animator = ValueAnimator.ofInt(0 , moveHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                LogUtil.d("moveHeight = " + value);
                ViewGroup.LayoutParams lp = textView.getLayoutParams();
                lp.height = value;
                textView.setLayoutParams(lp);
                otherWayLoginLayout.setVisibility(View.VISIBLE);

            }
        });
        animator.start();
    }


}
