package com.jamin.http.api;

import com.jamin.http.model.CloudBeanHistoryOnToday;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface HttpServiceApi {


    String API_BASE = "http://v.juhe.cn/";

    String TODAY_ON_HISTORY = "todayOnhistory/queryEvent.php";

    String TODAY_ON_HISTORY_DETAIL = "todayOnhistory/queryDetail.php";

    String AppKey = "02868bcf47295999fb7475285872d408";

    interface HistoryApi {
        /**
         * @param date
         * @param key
         * @return
         */
        @FormUrlEncoded
        @POST(TODAY_ON_HISTORY)
        Observable<CloudBeanHistoryOnToday> getTodayOnHistory(@Field("date") String date, @Field("key") String key);


        /**
         * @param date
         * @param e_id
         * @return
         */
        @FormUrlEncoded
        @POST(TODAY_ON_HISTORY_DETAIL)
        Observable<String> getTodayOnHistoryDetail(@Field("date") String date, @Field("e_id") String e_id);
    }


}
