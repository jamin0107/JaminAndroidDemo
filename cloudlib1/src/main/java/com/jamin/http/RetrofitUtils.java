package com.jamin.http;

import com.jamin.http.api.HttpServiceApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by jamin on 2016/12/21.
 */

abstract class RetrofitUtils {


    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;


    Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(HttpServiceApi.API_BASE + "/")
                    .client(getOkHttpClient())
                    .build();
        }
        return mRetrofit;
    }


    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    //.cookieJar(n)
                    //.addInterceptor(new MyIntercepter())
                    //.addNetworkInterceptor(new CookiesInterceptor(MyApplication.getInstance().getApplicationContext()))
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    //.cache(cache);
                    .build();
        }
        return mOkHttpClient;
    }

}
