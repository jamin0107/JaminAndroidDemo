package com.jamin.android.demo.ui.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.android.demo.widget.ResizeRelativeLayout;

/**
 * Created by jamin on 2017/3/30.
 */

public class LoginActivity extends BaseActivity {


    Button m_LoginBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LinearLayout m_MoveLayout = (LinearLayout) findViewById(R.id.login_by_phone_move_layout);
        ((ResizeRelativeLayout) findViewById(R.id.login_layout)).setCallback(new MyResizeLayoutCallback(
                m_MoveLayout));
        m_LoginBtn = (Button) findViewById(R.id.btn_login);

    }


    class MyResizeLayoutCallback implements ResizeRelativeLayout.ResizeLayoutCallback {

        LinearLayout m_MoveLayout;

        public MyResizeLayoutCallback(LinearLayout m_MoveLayout) {
            this.m_MoveLayout = m_MoveLayout;
        }

        private void moveUp(int w, int h, int oldw, int oldh) {
            int offset = 5;
            moveh = (m_LoginBtn.getBottom() - h) + offset;
            if (moveh <= 0 || Math.abs(moveh) <= 10) {
                moveh = 0;
                return;
            }
            TranslateAnimation animation = new TranslateAnimation(0, 0, m_MoveLayout.getTop(), m_MoveLayout.getTop()
                    - moveh);
            animation.setFillAfter(true);
            animation.setDuration(100);
            m_MoveLayout.setAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(m_MoveLayout.getLayoutParams());
                    params.setMargins(0, -moveh, 0, 0);
                    m_MoveLayout.setLayoutParams(params);
                    m_MoveLayout.clearAnimation();
                    useForSpecialPhone(m_MoveLayout);
                }

                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        int moveh = 0;

        private void moveDown(int w, int h, int oldw, int oldh) {
            if (Math.abs(moveh) <= 10 || moveh == 0) {
                return;
            }
            TranslateAnimation animation = new TranslateAnimation(0, 0, m_MoveLayout.getTop() + moveh,
                    m_MoveLayout.getTop() + 2 * moveh);
            animation.setDuration(100);
            m_MoveLayout.setAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(m_MoveLayout.getLayoutParams());
                    params.setMargins(0, 0, 0, 0);
                    m_MoveLayout.setLayoutParams(params);
                    m_MoveLayout.clearAnimation();
                    useForSpecialPhone(m_MoveLayout);
                }

                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        @Override
        public void onSizeChanged(int w, int h, int oldw, int oldh) {
            if (oldh > h) {
                // layout move up
                moveUp(w, h, oldw, oldh);
            } else {
                // layout move down
                moveDown(w, h, oldw, oldh);
            }

        }
    }

    /**
     * SHW-M110(sumsung i9000) 执行动画完毕会导致ui绘制出现问题。做特殊处理。
     *
     * @param m_MoveLayout
     */
    private void useForSpecialPhone(LinearLayout m_MoveLayout) {
        // for( int i = 0 ; i < specialPhone.length ; i++ ){
        // String model = android.os.Build.MODEL;
        // if(model != null && specialPhone[i].equals(model)){
        m_MoveLayout.setVisibility(View.GONE);
        m_MoveLayout.setVisibility(View.VISIBLE);
        // }
        // }
    }
}
