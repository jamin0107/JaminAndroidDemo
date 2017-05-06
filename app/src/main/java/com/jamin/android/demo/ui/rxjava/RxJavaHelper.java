package com.jamin.android.demo.ui.rxjava;

import android.graphics.Bitmap;

import com.jamin.framework.util.FaceLandMark;
import com.jamin.framework.util.ImageUtil;
import com.jamin.framework.util.LogUtil;


import java.util.Random;

import rx.Notification;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jamin on 2017/5/6.
 */

public class RxJavaHelper {


    /**
     * 周期性无旋循环，轮播
     */
    public static <T> Observable<T> circleRepeat(T[] repeatText) {
        return Observable.from(repeatText)
                .observeOn(Schedulers.io())
                .repeat()
                //每次observe发射数据的时候都产生调用，以及回调
                .doOnEach(new Action1<Notification<? super T>>() {
                    @Override
                    public void call(Notification<? super T> notification) {
                        LogUtil.d("sleep at doOnEach notification = " + notification.getValue());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 耗时操作 A 转 B
     *
     * 再对B做操作，最多100次，失败回调，成功回调
     *
     * @return
     */
    static int i = 0;

    public static Observable<FaceLandMark> faceDetectObserve(Bitmap bitmap) {
        i = 0;
        int radomTimes = new Random(100).nextInt();
        return Observable.just(bitmap)
                .observeOn(Schedulers.io())
//                .flatMap(new Func1<String, Observable<Integer>>() {
//                    @Override
//                    public Observable<Integer> call(String s) {
//                        LogUtil.d("1 flatMap String to int" + Thread.currentThread());
//                        return Observable.just(Integer.valueOf(s));
//                    }
//                })
                .flatMap(new Func1<Bitmap, Observable<byte[]>>() {
                    @Override
                    public Observable<byte[]> call(Bitmap bitmap) {
                        LogUtil.d("1 map string to byte[]" + Thread.currentThread());
                        return Observable.just(ImageUtil.getNV12FromBitmap(bitmap.getHeight(), bitmap.getHeight(), bitmap));
                    }
                })
                .observeOn(Schedulers.newThread())
                .flatMap(new Func1<byte[], Observable<FaceLandMark>>() {
                    @Override
                    public Observable<FaceLandMark> call(byte[] bitmapArray) {
                        return getByteArrayToLandMarkObservable(bitmapArray);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 模拟人脸识别次数。可能失败，多次尝试。成功则返回，最多100次
     * @param bitmapArray
     * @return
     */
    private static Observable<FaceLandMark> getByteArrayToLandMarkObservable(byte[] bitmapArray) {
        return Observable.just(bitmapArray)
                .map(new Func1<byte[], FaceLandMark>() {
                    @Override
                    public FaceLandMark call(byte[] bytes) {
//                        Random random = new Random(100);
//                        int randomNum = random.nextInt();
                        i++;
                        LogUtil.d("getByteArrayToLandMarkObservable randomNum = " + i + " ,thread = " + Thread.currentThread().getId());
                        if (i < 80) {
                            throw Exceptions.propagate(new Throwable("retry"));
                        }
                        return new FaceLandMark();
                    }
                })
                .retry(99);
    }
}
