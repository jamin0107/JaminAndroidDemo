package com.jamin.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jamin on 2016/12/21.
 */

abstract class RetrofitUtils {


    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;


    Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(HttpConfig.API_BASE + "/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
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
                    .addNetworkInterceptor(new StethoInterceptor())
                    .connectTimeout(HttpConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(HttpConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(HttpConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                    //.cache(cache);
                    .build();
        }
        return mOkHttpClient;
    }

}
