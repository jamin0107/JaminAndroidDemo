package com.jamin.android.demo.ui.rxjava;

import android.graphics.Bitmap;

import com.jamin.framework.util.FaceLandMark;
import com.jamin.framework.util.ImageUtil;
import com.jamin.framework.util.LogUtil;



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
     * <p>
     * 再对B做操作，最多100次，失败回调，成功回调
     *
     * @return
     */
    static int i = 0;
    static long startTime = 0;
    static long endTIme = 0;

    public static Observable<FaceLandMark> faceDetectObserve(Bitmap bitmap) {
        i = 0;
        return Observable.just(bitmap)
                .observeOn(Schedulers.io())
                .flatMap(new Func1<Bitmap, Observable<byte[]>>() {
                    @Override
                    public Observable<byte[]> call(Bitmap bitmap) {
                        LogUtil.d("1 map string to byte[]" + Thread.currentThread());
                        return Observable.just(ImageUtil.getNV12FromBitmap(bitmap.getHeight(), bitmap.getHeight(), bitmap));
                    }
                })
                .doOnNext(new Action1<byte[]>() {
                    @Override
                    public void call(byte[] bytes) {
                        startTime = System.currentTimeMillis();
                        LogUtil.d("starttime = " + startTime);
                    }
                })
                .flatMap(new Func1<byte[], Observable<FaceLandMark>>() {
                    @Override
                    public Observable<FaceLandMark> call(byte[] bitmapArray) {
                        return getByteArrayToLandMarkObservable(bitmapArray);
                    }
                })
                .doOnNext(new Action1<FaceLandMark>() {
                    @Override
                    public void call(FaceLandMark faceLandMark) {
                        endTIme = System.currentTimeMillis();
                        LogUtil.d("costTime = " + (endTIme - startTime) + "ms");
                        LogUtil.d("times = " + i + " times ");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 模拟人脸识别次数。可能失败，多次尝试。成功则返回，最多100次
     *
     * @param bitmapArray
     * @return
     */
    private static Observable<FaceLandMark> getByteArrayToLandMarkObservable(byte[] bitmapArray) {
        return Observable.just(bitmapArray)
                .map(new Func1<byte[], FaceLandMark>() {
                    @Override
                    public FaceLandMark call(byte[] bytes) {
                        i++;
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        LogUtil.d("getByteArrayToLandMarkObservable randomNum = " + i + " ,thread = " + Thread.currentThread().getId());
                        if (i < 30) {
                            throw Exceptions.propagate(new Throwable("retry"));
                        }
                        return new FaceLandMark();
                    }
                })
                .retry(99);
    }
}
