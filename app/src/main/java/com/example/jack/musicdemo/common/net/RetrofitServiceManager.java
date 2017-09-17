package com.example.jack.musicdemo.common.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by ${justin} on 2017/9/1516: 37
 * WeChat:Justin-Tz
 * 将初始化retrofit的代码抽取出来和okhttp一起封装，让其他类调用
 */

public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private Retrofit mRetrofit;
    private static RetrofitServiceManager INSTANCE;
    public RetrofitServiceManager(String baseurl) {

        //创建okhttpclient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);

       mRetrofit = new Retrofit.Builder()
               .client(builder.build())
               .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
               .addConverterFactory(GsonConverterFactory.create())
               .baseUrl(baseurl)
               .build();
    }

    /**
     * 获取RetrofitServiceManager
     * @param baseurl
     * @return
     */
    public static RetrofitServiceManager getInstance(String baseurl){
        if(INSTANCE == null){
            INSTANCE = new RetrofitServiceManager(baseurl);
        }
        return INSTANCE;
    }

    /**
     * 获取相对应的Service
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T  create(Class<T> service){
        return mRetrofit.create(service);
    }
}
