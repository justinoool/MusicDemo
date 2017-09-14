package com.example.jack.myapplication.api;

import com.example.jack.myapplication.model.HeFengWeather;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by ${justin} on 2017/9/1319: 33
 * WeChat:Justin-Tz
 */

public  interface  RequestPost {
    /**
     * Post请求需要把请求参数放置在请求体中，而非拼接在url后面
     * <p>
     * FormUrlEncode将会自动将请求参数的类型调整为application/x-www-form-urlencoded
     * FormUrlEncode不能用于get请求
     * <p>
     * field注解将每一个请求参数都存放至请求体中，还可以添加encoded参数，该参数为boolean型
     * 例如：@Field(value = "book", encoded = true) String book
     * encoded参数为true的话，key-value-pair将会被编码，即将中文和特殊字符进行编码转换
     */
    @FormUrlEncoded // @FormUrlEncoded的默认编码方式为UTF-8
    @POST("v5/search")
    Observable<HeFengWeather> PostData(@Field("city") String city,
                                       @Field("key") String key);

}
