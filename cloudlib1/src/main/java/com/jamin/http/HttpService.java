package com.jamin.http;

import com.jamin.http.api.HttpServiceApi;

import retrofit2.http.HTTP;
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

    public Observable<String> getTodayOnHistory(String date) {
        HttpServiceApi.HistoryApi historyApi = getRetrofit().create(HttpServiceApi.HistoryApi.class);
        Observable<String> observable = historyApi.getTodayOnHistory(date, HttpServiceApi.AppKey);
        return observable;
    }


}
