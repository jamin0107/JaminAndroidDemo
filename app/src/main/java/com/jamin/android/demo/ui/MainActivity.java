package com.jamin.android.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.airbnb.deeplinkdispatch.DeepLink;
import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.adapter.CustomRecyclerViewAdapter;
import com.jamin.android.demo.remote.JaminService;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.deeplink.JaminDeepLink;
import com.jamin.framework.deeplink.JaminDeepLinkDispatcher;
import com.jamin.framework.util.AESUtil;
import com.jamin.framework.util.HardWareEventListener;
import com.jamin.framework.util.LogUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jamin on 2016/12/14.
 */

@JaminDeepLink(JaminDeepLinkDispatcher.PATH_MAIN_PAGE_FOR_DP)
public class MainActivity extends BaseActivity {


    RecyclerView mRecycleView;
    CustomRecyclerViewAdapter mCustomRecyclerViewAdapter;
    List<BaseItem> list = new LinkedList<>();
    public static final String LAUNCHER_ANIMATION = "Animation";
    public static final String LAUNCHER_IMAGE = "Image";
    public static final String LAUNCHER_HISTORY_ON_TODAY = "DbHistory on Today";
    public static final String LAUNCHER_RXJAVA_TEST = "RxBus";
    public static final String LAUNCHER_GL_VIEW = "GLView";
    public static final String LAUNCHER_SERVICE = "Remote Service";
    public static final String LAUNCHER_RESCUE_TEST = "RESCUE";

    String[] LAUNCHER = new String[]{LAUNCHER_ANIMATION, LAUNCHER_IMAGE, LAUNCHER_HISTORY_ON_TODAY, LAUNCHER_RXJAVA_TEST, LAUNCHER_GL_VIEW, LAUNCHER_SERVICE, LAUNCHER_RESCUE_TEST};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        JaminService.startService(this);
        initData();
        initView();
        Intent intent = getIntent();
        LogUtil.d(intent.getDataString() + "," + intent.getData());
        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
            JaminDeepLinkDispatcher.dispatch(intent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        HardWareEventListener.getInstance(this).register(hardwareEventReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        HardWareEventListener.getInstance(this).unregister(hardwareEventReceiver);
    }

    /**
     * 点击硬件back，recent app的callback
     */
    HardWareEventListener.Receiver hardwareEventReceiver = new HardWareEventListener.Receiver() {
        @Override
        public void onReceived(String reason) {
            if (HardWareEventListener.REASON_HOME_KEY.equals(reason)) {
                //HOME KEY EVENT
                //Toast.makeText(MainActivity.this, "HOME KEY EVENT", Toast.LENGTH_SHORT).show();
                LogUtil.d("HOME KEY EVENT");
            } else if (HardWareEventListener.REASON_RECENT_APPS.equals(reason)) {
                //RECENT APP EVENT
                //Toast.makeText(MainActivity.this, "RECENT APP EVENT", Toast.LENGTH_SHORT).show();
                LogUtil.d("RECENT APP EVENT");
            }
        }
    };


    private void initData() {
        for (String str : LAUNCHER) {
            list.add(new LauncherTextItem(getActivity(), str));
        }
    }

    private void initView() {
        mRecycleView = (RecyclerView) findViewById(R.id.launch_activity_list_view);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mCustomRecyclerViewAdapter = new CustomRecyclerViewAdapter(list);
        mRecycleView.setAdapter(mCustomRecyclerViewAdapter);
        TextView textView = (TextView) findViewById(R.id.text_show);
        try {
//            AESUtil aesUtil = new AESUtil();
//            String encryptStr = aesUtil.encrypt("pid");
//            LogUtil.d("encryptStr = " + encryptStr);
            String decryptStr = AESUtil.decrypt("s4wi9WIk97Anx0+1ipvAXw==");
            LogUtil.d("decryptStr = " + decryptStr);
            LogUtil.d("encrypt = " + AESUtil.encrypt("18752375"));
            LogUtil.d("decryptStr = " + AESUtil.decrypt(AESUtil.encrypt("18752375")));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
