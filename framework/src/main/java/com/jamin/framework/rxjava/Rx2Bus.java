package com.jamin.framework.rxjava;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 * Created by jamin on 2017/5/10.
 * <p>
 * PublishSubject只会给在订阅者订阅的时间点之后的数据发送给观察者。
 * <p>
 * BehaviorSubject在订阅者订阅时，会发送其最近发送的数据（如果此时还没有收到任何数据，它会发送一个默认值）。
 * <p>
 * ReplaySubject在订阅者订阅时，会发送所有的数据给订阅者，无论它们是何时订阅的。
 * <p>
 * AsyncSubject只在原Observable事件序列完成后，发送最后一个数据，后续如果还有订阅者继续订阅该Subject, 则可以直接接收到最后一个值。
 */

public class Rx2Bus {


    private static volatile Rx2Bus defaultInstance;

    private final Subject<Object> mBus;

    private final Map<Class<?>, Object> mStickyEventMap;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    public Rx2Bus() {
        mBus = PublishSubject.create().toSerialized();
        mStickyEventMap = new ConcurrentHashMap<>();
    }


    // 单例RxBus
    public static Rx2Bus getDefault() {
        if (defaultInstance == null) {
            synchronized (Rx2Bus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new Rx2Bus();
                }
            }
        }
        return defaultInstance;
    }

    // 发送一个新的事件
    public void post(Object o) {
        mBus.onNext(o);
    }


    public void postSticky(Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.getClass(), event);
        }
        post(event);
    }



    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }

    public  <T> Observable<T> toObservableThrottleFirst(Class<T> eventType) {
        return mBus.ofType(eventType).throttleFirst(50, TimeUnit.MILLISECONDS);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservableSticky(final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            Observable<T> observable = mBus.ofType(eventType);
            final Object event = mStickyEventMap.get(eventType);

            if (event != null) {
                return observable.mergeWith(Observable.just(eventType.cast(event)));
            } else {
                return observable;
            }
        }
    }



    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }


}
