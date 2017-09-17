package com.example.jack.musicdemo.common.net;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * @desciption: Retrofit初始化
 */
public class RetrofitManager {

    private static RetrofitManager ourInstance;
    private Retrofit mRetrofit;

    public static RetrofitManager getInstance() {
        if (ourInstance == null)
            ourInstance = new RetrofitManager();

        return ourInstance;
    }

    private RetrofitManager() {
    }
    /**
     * 清除登录的token
     */
    public void clear() {
        mRetrofit = null;
    }
    private ApiStore mRestService;

    private void build(String baseUrl) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                //必须加
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mRestService = mRetrofit.create(ApiStore.class);
    }

    public ApiStore getAPIService(String baseUrl) {
        if (mRetrofit == null)
            build(baseUrl);
        return mRestService;
    }


}
