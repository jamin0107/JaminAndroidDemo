package com.jamin.http.api;

import com.jamin.http.model.CloudBeanHistoryOnToday;
import com.jamin.http.model.detail.CloudBeanHistoryDetail;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpServiceApi {


    String TODAY_ON_HISTORY = "todayOnhistory/queryEvent.php";

    String TODAY_ON_HISTORY_DETAIL = "todayOnhistory/queryDetail.php";

    interface HistoryApi {
        /**
         * @param date
         * @param key
         * @return
         */
        @FormUrlEncoded
        @POST(TODAY_ON_HISTORY)
        Flowable<CloudBeanHistoryOnToday> getTodayOnHistory(@Field("date") String date, @Field("key") String key);


        /**
         * @param e_id
         * @param key
         * @return
         */
        @FormUrlEncoded
        @POST(TODAY_ON_HISTORY_DETAIL)
        Flowable<CloudBeanHistoryDetail> getTodayOnHistoryDetail(@Field("e_id") int e_id, @Field("key") String key);
    }


}
