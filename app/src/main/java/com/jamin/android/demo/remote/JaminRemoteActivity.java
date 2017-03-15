package com.jamin.android.demo.remote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jamin on 2017/3/15.
 */

public class JaminRemoteActivity extends BaseActivity {

    public static final String EXTRA_KEY_REMOTE_OBJ = "extra_key_remote_obj";

    @BindView(R.id.activity_remote_textview)
    TextView textView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_main);
        ButterKnife.bind(this);
        int pid = android.os.Process.myPid();
        RemoteObj remoteObj = getIntent().getExtras().getParcelable(EXTRA_KEY_REMOTE_OBJ);
        if (remoteObj != null) {
            textView.setText("pid = " + pid + "----" + remoteObj.toString());
        }

    }
}
