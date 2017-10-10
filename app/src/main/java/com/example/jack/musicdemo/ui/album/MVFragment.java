package com.example.jack.musicdemo.ui.album;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.common.net.ApiLoader;
import com.example.jack.musicdemo.common.net.ApiStore;
import com.example.jack.musicdemo.common.net.RetrofitManager;
import com.example.jack.musicdemo.data.MVFromKG;
import com.example.jack.musicdemo.data.MVInfo;
import com.example.jack.musicdemo.data.MVListInfo;
import com.example.jack.musicdemo.data.MvList;
import com.example.jack.musicdemo.ui.adapter.MVAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cn.jzvd.JZVideoPlayerStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.R.string.ok;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.VERSION_CODES.M;
import static com.example.jack.musicdemo.R.drawable.c;

/**
 * A simple {@link Fragment} subclass.
 *  mv碎片
 * 小问题：下面获取到的视频有部分被遮住
 */
public class MVFragment extends Fragment {
  private final String TAG="MVFragment";
    private RecyclerView mvRecycleView;

    private MVAdapter adapterVideoList;
    private Retrofit mRetrofit,mRetrofit2;
    private ApiStore mRestService,mRestService2;
    private ApiLoader mMovieLoader;
    private Map<String,String> maps;

    public MVFragment() {
        // Required empty public constructor
    }

    public   static  MVFragment getInstance(){
       MVFragment mvFragment = new MVFragment();
        return  mvFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mvView = inflater.inflate(R.layout.fragment_mv, container, false);

        mvRecycleView = (RecyclerView) mvView.findViewById(R.id.recyclerView_mv);

        maps = new HashMap<>();
        maps.put("method","baidu.ting.mv.searchMV");
        maps.put("page_num","1");
        maps.put("page_size","20");
                initData(maps);



        mvRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapterVideoList = new MVAdapter(getActivity());

        mvRecycleView.setAdapter(adapterVideoList);
//用okgo实现
        adapterVideoList.setOnItemClickListener(new MVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String mvID) {
        /*        //加载mv
                Map<String ,String > map1 = new HashMap<String, String>();
                map1.put("method","baidu.ting.mv.playMV");
                map1.put("mv_id",mvID);
              loadmvData(map1);*/
                Log.d(TAG,position+""+mvID);
                OkGo.<String>get(ApiStore.MVINFO_URL + mvID)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Gson gson = new Gson();
                                MVInfo mvInfo = gson.fromJson(response.body(), MVInfo.class);
                                MVInfo.ResultBean result = mvInfo.getResult();
                                String file_link = result.getFiles().getValue31().getFile_link();
                                Toast.makeText(getActivity(), file_link, Toast.LENGTH_SHORT).show();
                                JZVideoPlayerStandard.startFullscreen(getActivity(),JZVideoPlayerStandard.class,
                                        file_link,result.getMv_info().getTitle());
                            }
                        });

            }
        });
        return mvView;
    }
//用retrofit2实现
   private void loadmvData( Map<String ,String > map2 ) {

        mRetrofit = new Retrofit.Builder()
                .client(getClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(mRestService.MV_TEST)
                .build();
        mRestService = mRetrofit.create(ApiStore.class);

        Observable<MVInfo> mvInfo = mRestService.getMVInfo(map2);
     mvInfo.subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(new Observer<MVInfo>() {
                 @Override
                 public void onCompleted() {

                 }

                 @Override
                 public void onError(Throwable e) {
                 Log.e(TAG,e.getMessage());
                 }

                 @Override
                 public void onNext(MVInfo mvInfo) {
                     MVInfo.ResultBean result = mvInfo.getResult();
                     Log.d(TAG,mvInfo.getResult().getFiles().getValue31().getFile_link());
                  /*  JCVideoPlayerStandard.startFullscreen(getActivity(),JCVideoPlayerStandard.class,
                            mvInfo.getResult().getFiles().getValue31().getFile_link(),
                            mvInfo.getResult().getMv_info().getAliastitle()
                    );*/

                 }
             });

    }


    /****
     * 数据的初始化
     * 显示数据
     * //"baidu.ting.mv.searchMV", "0", "1", "20"
     */
    private void initData(Map<String,String> mapss) {

         mRetrofit2 = new Retrofit.Builder()
                 //BASE_PARAMETERS_MV = "http://tinapi.ting.baidu.com/v1/restserver/";
                 .client(getClient())  //这里一定一定要加入user-agent，原因 该api服务端设置了手机端okhttp不能访问，要通过浏览器，添加了头部可以模拟手机浏览器去访问
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                 .baseUrl(mRestService2.MV_TEST)
                 .build();
        mRestService2 = mRetrofit2.create(ApiStore.class);
        Observable<MVListInfo> mvListInfo = mRestService2.getMVListInfo(mapss);
        mvListInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MVListInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MVListInfo mvListInfo) {
                        List<MVListInfo.ResultBean.MvListBean> mvList = mvListInfo.getResult().getMvList();
                        adapterVideoList.setData(mvList);
                    }
                });


        //使用方式 MV_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.mv.searchMV&order=0&page_num=1&page_size=20";
       /* OkGo.<String>get(ApiStore.MV_URL)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new GsonBuilder().create();
                        MVListInfo mvListInfo = gson.fromJson(response.body(), MVListInfo.class);
                        adapterVideoList.setData(mvListInfo.getResult().getMvList());
                    }
                });*/



    }

//这个是retrofit添加头部的方法，很重要，有个加注解的方法试过但是不行
    private OkHttpClient getClient() {
            OkHttpClient httpclient  =new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {

                            Request request =null;
                            request = chain.request()
                                    .newBuilder()
                                    .removeHeader("User-Agent")
                                    .addHeader("User-Agent","MusicDemo-justin")
                                    .build();
                           return chain.proceed(request);
                        }

                    }).build();
        return httpclient;
    }

}
