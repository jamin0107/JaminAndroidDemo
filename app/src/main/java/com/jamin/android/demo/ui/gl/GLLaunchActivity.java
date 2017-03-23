package com.jamin.android.demo.ui.gl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.gl.googledemo.OpenGLES20Activity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jamin on 2017/3/23.
 */

public class GLLaunchActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl_launch);
        ButterKnife.bind(this);
    }





    @OnClick(R.id.gl_triangle)
    public void study1() {
        startActivity(new Intent(this, GLStudyActivity.class));
    }



    @OnClick(R.id.gl_study2)
    public void study2() {
        startActivity(new Intent(this, GLStudy2Activity.class));
    }


    @OnClick(R.id.gl_google_demo)
    public void studyGoogleDemo() {
        startActivity(new Intent(this, OpenGLES20Activity.class));
    }


}
