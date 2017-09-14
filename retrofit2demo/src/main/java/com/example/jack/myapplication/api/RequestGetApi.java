package com.example.jack.myapplication.api;

import com.example.jack.myapplication.model.HeFengWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ${justin} on 2017/9/1315: 50
 * WeChat:Justin-Tz
 */

public interface RequestGetApi {
    // @get注解就表示get请求，@query表示请求参数，将会以key=value的方式拼接在url后面
    // Query非必填可以用null填充，例如：("小王子", null, 0, 3)
    @GET("v5/search")
    Call<HeFengWeather> getData(@Query("city") String city,
                                @Query("key") String key);

}
