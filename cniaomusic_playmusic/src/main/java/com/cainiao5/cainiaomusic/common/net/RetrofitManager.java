package com.cainiao5.cainiaomusic.common.net;


import com.cainiao5.cainiaomusic.common.net.callbacks.ICallback;
import com.cainiao5.cainiaomusic.data.SongInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * @desciption: Retrofit初始化
 */
public class RetrofitManager {

    private static RetrofitManager ourInstance;
    private Retrofit mRetrofit,RecRetrofit;

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


    //解析歌曲信息
    public void buildRecommend(String songid, final ICallback callback) {
        OkGo.<String>get("http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.song.play&songid="+songid)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Gson gson = new GsonBuilder().create();
                            SongInfo songInfo = gson.fromJson(response.body(), SongInfo.class);
                            callback.getSongInfoSucess(songInfo);
                        }
                    });

    }


}
