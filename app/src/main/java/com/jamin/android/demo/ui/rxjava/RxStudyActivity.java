package com.jamin.android.demo.ui.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jamin on 2017/5/10.
 */

public class RxStudyActivity extends BaseActivity {


    String[] repeatText = new String[]{"Jamin", "Hello", "World", "!"};


    @BindView(R.id.repeat_number)
    TextView mRepeatNumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            RxJavaSubscriberFragment subscriberFragment = RxJavaSubscriberFragment.newInstance();
            RxJavaObservableFragment observableFragment = RxJavaObservableFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_subscriber, subscriberFragment)
                    .add(R.id.fl_observable, observableFragment)
                    .commit();
        }

        startCirclePlay();
        faceDetect();

    }


    public void faceDetect() {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.nasa);
//        RxJavaHelper.faceDetectObserve(bitmap).subscribe(new Subscriber<FaceLandMark>() {
//            @Override
//            public void onCompleted() {
//                LogUtil.d("rx subscribe onCompleted");
//                LogUtil.d("rx thread id = " + Thread.currentThread().getId());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LogUtil.d("rx subscribe onError e = " + e.getMessage());
//                LogUtil.d("rx thread id = " + Thread.currentThread().getId());
//            }
//
//            @Override
//            public void onNext(FaceLandMark faceLandMark) {
//                LogUtil.d("rx subscribe onNext faceLandMark = " + faceLandMark.pointX);
//                LogUtil.d("rx thread id = " + Thread.currentThread().getId());
//            }
//        });
    }


    public void startCirclePlay() {
//        RxJavaHelper.circleRepeat(repeatText)
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onCompleted() {
//                        LogUtil.d("subscribe onCompleted");
//                        LogUtil.d("thread id = " + Thread.currentThread().getId());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.d("subscribe onError e = " + e.getMessage());
//                        LogUtil.d("thread id = " + Thread.currentThread().getId());
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        LogUtil.d("subscribe onNext s = " + s);
//                        LogUtil.d("thread id = " + Thread.currentThread().getId());
//                        mRepeatNumber.setText(s);
//                    }
//                });


    }


}
