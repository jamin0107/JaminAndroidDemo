package com.jamin.android.demo.remote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.Toast;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jamin on 2017/3/15.
 */

public class TestRemoteServiceActivity extends BaseActivity {


    @BindView(R.id.service_bind)
    Button mBindBtn;
    @BindView(R.id.service_unbind)
    Button mUnbindBtn;
    @BindView(R.id.service_connect)
    Button mConnectBtn;
    @BindView(R.id.btn_remote_activity)
    Button mStartActivityBtn;

    JaminServiceConnect mServiceConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_remote_service);
        ButterKnife.bind(this);
        mServiceConnection = JaminServiceConnect.getInstance();
    }


    @OnClick(R.id.service_bind)
    public void onBind() {
        mServiceConnection.onBind();
        mUnbindBtn.setEnabled(true);
        mConnectBtn.setEnabled(true);

    }

    @OnClick(R.id.service_unbind)
    public void onUnBind() {
        mServiceConnection.onUnBind();
        mUnbindBtn.setEnabled(false);
        mConnectBtn.setEnabled(false);
    }


    @OnClick(R.id.service_connect)
    public void onConnect() {
        mServiceConnection.onConnectTestMethod();
    }

    @OnClick(R.id.btn_remote_activity)
    public void startRemoteActivity() {
        Intent intent = new Intent(this, JaminRemoteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServiceConnection.onUnBind();
    }
}
