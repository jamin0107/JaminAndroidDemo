package com.jamin.http;

import com.jamin.http.api.HttpServiceApi;
import com.jamin.http.model.CloudBeanHistoryOnToday;
import com.jamin.http.model.detail.CloudBeanHistoryDetail;

import rx.Observable;


/**
 * Created by jamin on 2016/12/21.
 */

public class HttpService extends RetrofitUtils {

    private static HttpService httpService = null;

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

    public Observable<CloudBeanHistoryOnToday> getTodayOnHistory(String date) {
        HttpServiceApi.HistoryApi historyApi = getRetrofit().create(HttpServiceApi.HistoryApi.class);
        Observable<CloudBeanHistoryOnToday> observable = historyApi.getTodayOnHistory(date, HttpConfig.AppKey);
        return observable;
    }

    public Observable<CloudBeanHistoryDetail> getTodayOnHistoryDetail(int id) {
        HttpServiceApi.HistoryApi historyApi = getRetrofit().create(HttpServiceApi.HistoryApi.class);
        Observable<CloudBeanHistoryDetail> observable = historyApi.getTodayOnHistoryDetail(id, HttpConfig.AppKey);
        return observable;
    }


}
