package com.jamin.android.demo.ui.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseFragment;
import com.jamin.android.demo.ui.rxjava.event.NormalEvent;
import com.jamin.android.demo.ui.rxjava.event.StickyEvent;
import com.jamin.framework.rxjava.Rx2Bus;
import com.jamin.framework.rxjava.RxBus;
import com.jamin.framework.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jamin on 2017/5/10.
 */

public class RxJavaObservableFragment extends BaseFragment {


    public static RxJavaObservableFragment newInstance() {
        return new RxJavaObservableFragment();
    }

    private int mCountNum, mCountStickyNum;


    @BindView(R.id.frag_observe_btn_post)
    Button mPostEvent;
    @BindView(R.id.frag_observe_btn_postSticky)
    Button mPostStickyBtn;
    @BindView(R.id.frag_observe_content)
    TextView mNormalEventTV;
    @BindView(R.id.frag_observe_sticky_content)
    TextView mStickyEventTV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxjava_observable, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.frag_observe_btn_postSticky)
    public void postStickyEvent() {
        Rx2Bus.getDefault().postSticky(new StickyEvent(--mCountStickyNum));
        String str = mStickyEventTV.getText().toString();
        mStickyEventTV.setText(TextUtils.isEmpty(str) ? String.valueOf(mCountStickyNum) : str + ", " + mCountStickyNum);
    }


    @OnClick(R.id.frag_observe_btn_post)
    public void postEvent() {
        for (int i = 0; i < 10000; i++) {
            Rx2Bus.getDefault().post(new NormalEvent(++mCountNum));
//            String str = mNormalEventTV.getText().toString();
            mNormalEventTV.setText("-" + mCountNum);
            LogUtil.d("Jamin111 postEvent = " + mNormalEventTV.getText().toString());
        }

    }


}
