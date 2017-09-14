package com.example.jack.myapplication.api;

import com.example.jack.myapplication.model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ${justin} on 2017/9/1223: 47
 * WeChat:Justin-Tz
 */
// @Path可以用于任何请求，拼接url地址
// @GET表示get请求
public interface ApiStores {
    //baseUrl
    String API_SERVER_URL = "http://www.weather.com.cn/";

    //加载天气
    @GET("data/sk/{cityId}.html")
    Call<WeatherModel> loadDataByJson(@Path("cityId") String cityIdJson);
    //返回字符串
    @GET("data/sk/{cityId}.html")
    Call<String> loadDataByString(@Path("cityId") String cityIdString);
}
