package com.jamin.android.demo.ui.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.android.demo.widget.LikeButton;

/**
 * Created by jamin on 2016/12/27.
 */

public class LikeActivity extends BaseActivity {


    boolean likeState = false;
    LikeButton likeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_button);
        likeButton = (LikeButton) findViewById(R.id.like_button);

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (likeButton.isAnimating()) {
                    return;
                }
                if (likeState) {
                    likeState = false;
                    likeButton.setUnLike(true);
                } else {
                    likeState = true;
                    likeButton.setLike(true);
                }
            }
        });
    }


}
