package com.jamin.http;

import com.jamin.http.api.HttpServiceApi;
import com.jamin.http.model.CloudBeanHistoryOnToday;
import com.jamin.http.model.detail.CloudBeanHistoryDetail;

import io.reactivex.Flowable;


/**
 * Created by jamin on 2016/12/21.
 */

public class HttpService extends RetrofitUtils {

    private static HttpService httpService = null;
    public static final int READ_CACHE = 1;
    public static final int NETWORK = 2;

    public static HttpService getInstance() {
        if (httpService == null) {
            synchronized (HttpService.class) {
                if (httpService == null) {
                    httpService = new HttpService();
                }
            }
        }
        return httpService;
    }


    public Flowable<CloudBeanHistoryOnToday> getTodayOnHistory(final String date) {
        HttpServiceApi.HistoryApi historyApi = getRetrofit().create(HttpServiceApi.HistoryApi.class);
        Flowable<CloudBeanHistoryOnToday> observable = historyApi.getTodayOnHistory(date, HttpConfig.AppKey);
        return observable;
//        return Flowable.create(new FlowableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(READ_CACHE);
//                emitter.onNext(NETWORK);
//            }
//        }, BackpressureStrategy.BUFFER)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .flatMap(new Function<Integer, Flowable<CloudBeanHistoryOnToday>>() {
//                    @Override
//                    public Flowable<CloudBeanHistoryOnToday> apply(Integer integer) throws Exception {
//                        if (READ_CACHE == integer) {
//                            HttpFileCache<CloudBeanHistoryOnToday> httpFileCache = new HttpFileCache<>(CloudBeanHistoryOnToday.class);
//                            CloudBeanHistoryOnToday cloudBeanHistoryOnToday = new CloudBeanHistoryOnToday();
//                            cloudBeanHistoryOnToday.errorCode = 1;
//                            cloudBeanHistoryOnToday.list = null;
//                            cloudBeanHistoryOnToday.reason = "test";
//                            httpFileCache.saveCache(cloudBeanHistoryOnToday);
//                            Thread.sleep(2000);
//                            return new HttpFileCache<>(CloudBeanHistoryOnToday.class).getCache();
//                        } else {
//                            HttpFileCache<CloudBeanHistoryOnToday> httpFileCache = new HttpFileCache<>(CloudBeanHistoryOnToday.class);
//                            CloudBeanHistoryOnToday cloudBeanHistoryOnToday = new CloudBeanHistoryOnToday();
//                            cloudBeanHistoryOnToday.errorCode = 0;
//                            cloudBeanHistoryOnToday.list = new ArrayList<>();
//                            CloudBeanHistory history = new CloudBeanHistory();
//                            history.title = "Hello World!";
//                            history.e_id = 15;
//                            cloudBeanHistoryOnToday.list.add(history);
//                            cloudBeanHistoryOnToday.reason = "test0";
//                            httpFileCache.saveCache(cloudBeanHistoryOnToday);
//                            Thread.sleep(1000);
//                            return new HttpFileCache<>(CloudBeanHistoryOnToday.class).getCache();
////                            return getRetrofit().create(HttpServiceApi.HistoryApi.class).getTodayOnHistory(date, HttpConfig.AppKey);
//                        }
//                    }
//                });
    }


    public Flowable<CloudBeanHistoryDetail> getTodayOnHistoryDetail(int id) {
        HttpServiceApi.HistoryApi historyApi = getRetrofit().create(HttpServiceApi.HistoryApi.class);
        Flowable<CloudBeanHistoryDetail> observable = historyApi.getTodayOnHistoryDetail(id, HttpConfig.AppKey);
        return observable;
    }


}
