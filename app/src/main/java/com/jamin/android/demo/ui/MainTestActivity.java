package com.jamin.android.demo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.framework.util.LogUtil;

/**
 * Created by Jamin on 2016/4/20.
 */
public class MainTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_dialog);
        initView();

    }

    LinearLayout otherWayLoginLayout = null;
    TextView textView = null;

    private void initView() {
        otherWayLoginLayout = (LinearLayout) findViewById(R.id.explore_login_other_way_layout);
        textView = (TextView) findViewById(R.id.policy);
        Button showOtherWayButton = (Button) findViewById(R.id.explore_show_login_way);
        showOtherWayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });

    }


    public void startAnimation() {
        float density = getResources().getDisplayMetrics().density;
        final int moveHeight = (int) (density * 67 + 0.5);
        LogUtil.d("moveHeight = " + moveHeight);
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, moveHeight);
        animation.setFillAfter(true);
        animation.setDuration(300);
//        textView.setAnimation(animation);
        textView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(textView.getLayoutParams());
                params.addRule(RelativeLayout.BELOW , R.id.explore_login_other_way_layout);
                params.setMargins( 0 , (int) (getResources().getDisplayMetrics().density * 17 + 0.5), 0, 0);
                textView.setLayoutParams(params);
//                params.setMargins(0, -moveHeight, 0, 0);
//                LogUtil.d("params.height = " + params.height);
//                textView.setLayoutParams(params);
                textView.clearAnimation();
                otherWayAnimFadeIn();
                // useForSpecialPhone(m_MoveLayout);
            }

            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


    public void otherWayAnimFadeIn(){
        otherWayLoginLayout.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnim = new AlphaAnimation(0, 1);
        alphaAnim.setDuration(300);
        alphaAnim.setFillAfter(true);
        otherWayLoginLayout.startAnimation(alphaAnim);
        alphaAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

//    private void initView() {
//        TextView tv = (TextView) findViewById(R.id.text_view);
//        String text = "123456";
//
//
//        //String MD5 = MD5Util.getMd5ByString(text);
//        //tv.setText(MD5);
//
//        UserInfo userInfo = new UserInfo();
//        userInfo.userName = "Jamin";
//        userInfo.password = "123456";
//        Gson gson =  new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
////        Gson gson = new Gson();
//        String jsonStr = gson.toJson(userInfo);
//        tv.setText(jsonStr);
//
//        Button button = (Button) findViewById(R.id.click);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LoginDialogFragment loginDialogFragment = LoginDialogFragment.newInstance();
//                loginDialogFragment.show(getSupportFragmentManager() , "LoginDialogFragment");
//            }
//        });
//    }


}
