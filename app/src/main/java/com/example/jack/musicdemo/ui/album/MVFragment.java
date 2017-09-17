package com.example.jack.musicdemo.ui.album;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.common.net.ApiLoader;
import com.example.jack.musicdemo.common.net.ApiStore;
import com.example.jack.musicdemo.common.net.RetrofitManager;
import com.example.jack.musicdemo.data.MVFromKG;
import com.example.jack.musicdemo.data.MVInfo;
import com.example.jack.musicdemo.data.MVListInfo;
import com.example.jack.musicdemo.ui.adapter.MVAdapter;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.os.Build.VERSION_CODES.M;

/**
 * A simple {@link Fragment} subclass.
 *  mv碎片
 */
public class MVFragment extends Fragment {

    private RecyclerView mvRecycleView;

    private MVAdapter adapterVideoList;
    private Retrofit mRetrofit,mRetrofit2;
    private ApiStore mRestService,mRestService2;

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
        initData();

        mvRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapterVideoList = new MVAdapter(getActivity());
        mvRecycleView.setAdapter(adapterVideoList);

        adapterVideoList.setOnItemClickListener(new MVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String mvID) {
                loadMvData();
            }
        });
        return mvView;
    }

    private void loadMvData() {
        mRetrofit2 = new Retrofit.Builder()
                .baseUrl(mRestService.BASE_PARAMETERS_MVINFO)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                //必须加
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        //获取api接口的实现类的对象
        mRestService2 = mRetrofit2.create(ApiStore.class);


        Observable<MVInfo> mvInfo = mRestService2.getMVInfo("baidu.ting.mv.playMV", "107056505");

        mvInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MVInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError: ", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(MVInfo mvInfo) {
                        Log.e("onNext: ", "onNext: " + mvInfo.toString());
                    }
                });


    }


    /****
     * 数据的初始化
     */
    private void initData() {


    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (!isVisibleToUser) {
//            JCVideoPlayer.releaseAllVideos();
//        }
//    }

}
