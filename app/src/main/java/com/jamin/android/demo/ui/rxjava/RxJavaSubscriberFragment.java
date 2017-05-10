package com.jamin.android.demo.ui.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseFragment;
import com.jamin.android.demo.ui.rxjava.event.NormalEvent;
import com.jamin.android.demo.ui.rxjava.event.RxSubscriptions;
import com.jamin.android.demo.ui.rxjava.event.StickyEvent;
import com.jamin.framework.rxjava.RxBus;
import com.jamin.framework.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by jamin on 2017/5/10.
 */

public class RxJavaSubscriberFragment extends BaseFragment {


    public static RxJavaSubscriberFragment newInstance() {
        return new RxJavaSubscriberFragment();
    }


    @BindView(R.id.frag_subscriber_btn_register)
    Button mRegisterBtn;
    @BindView(R.id.frag_subscriber_content)
    TextView mNormalEventTV;
    @BindView(R.id.frag_subscriber_sticky_content)
    TextView mStickyEventTV;

    private Subscription mRxSubscriber, mRxSubSticky;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxjava_subscriber, container, false);
        ButterKnife.bind(this, view);
        // 订阅普通RxBus事件
        subscribeEvent();
        return view;
    }


    private void subscribeEvent() {
        mRxSubscriber = RxBus.getDefault()
                .toObservable(NormalEvent.class)
                .subscribe(new Subscriber<NormalEvent>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("onError");
                    }

                    @Override
                    public void onNext(NormalEvent normalEvent) {
                        LogUtil.d("onNext + normalEvent.number = " + normalEvent.number);
                        String str = mNormalEventTV.getText().toString();
                        Toast.makeText(context, "RxSubscriptions.hasSubscriptions() = " + RxSubscriptions.hasSubscriptions(), Toast.LENGTH_SHORT).show();
                        mNormalEventTV.setText(TextUtils.isEmpty(str) ? String.valueOf(normalEvent.number) : str + ", " + normalEvent.number);
                    }
                });

        RxSubscriptions.add(mRxSubscriber);

    }

    @OnClick(R.id.frag_subscriber_btn_register)
    public void subscribeStickyEvent() {
        if (mRxSubSticky != null && !mRxSubSticky.isUnsubscribed()) {
            mRegisterBtn.setText("Register");
            RxSubscriptions.remove(mRxSubSticky);
        } else {
            StickyEvent s = RxBus.getDefault().getStickyEvent(StickyEvent.class);
            LogUtil.d("获取到StickyEvent--->" + s.number);
            mRxSubSticky = RxBus.getDefault().toObservableSticky(StickyEvent.class)
                    .subscribe(new Subscriber<StickyEvent>() {
                        @Override
                        public void onCompleted() {
                            LogUtil.d("onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.d("onError");
                        }

                        @Override
                        public void onNext(StickyEvent normalEvent) {
                            LogUtil.d("onNext + normalEvent.number = " + normalEvent.number);
                            String str = mStickyEventTV.getText().toString();
                            Toast.makeText(context, "RxSubscriptions.hasSubscriptions() = " + RxSubscriptions.hasSubscriptions(), Toast.LENGTH_SHORT).show();
                            mStickyEventTV.setText(TextUtils.isEmpty(str) ? String.valueOf(normalEvent.number) : str + ", " + normalEvent.number);
                        }
                    });
            mRegisterBtn.setText("Cancel");
            RxSubscriptions.add(mRxSubSticky);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxSubscriptions.remove(mRxSubscriber);
        RxSubscriptions.remove(mRxSubSticky);
        LogUtil.d("RxSubscriptions.hasSubscriptions() = " + RxSubscriptions.hasSubscriptions());

    }
}
