package com.cainiao5.cainiaomusic.ui.album;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.net.ApiStore;
import com.cainiao5.cainiaomusic.data.MVListInfo;
import com.cainiao5.cainiaomusic.data.MvInfo;
import com.cainiao5.cainiaomusic.ui.adapter.MVAdapter;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * 歌单
 */
public class MVFragment extends Fragment {


    private RecyclerView mvRecycleView;

    private MVAdapter adapterVideoList;
    private Retrofit mRetrofit,mRetrofit2;
    private ApiStore mRestService,mRestService2;

    public MVFragment() {
        // Required empty public constructor
    }

    public static MVFragment newInstance() {
        MVFragment fragment = new MVFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mvView = inflater.inflate(R.layout.fragment_song_menu, container, false);

        mvRecycleView = (RecyclerView) mvView.findViewById(R.id.recycleView_mv);
        initData();

        mvRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapterVideoList = new MVAdapter(getActivity());
        mvRecycleView.setAdapter(adapterVideoList);

        adapterVideoList.setOnItemClickLitener(new MVAdapter.OnItemClickLitener() {
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


        Observable<MvInfo> mvInfo = mRestService2.getMVInfo("baidu.ting.mv.playMV", "107056505");

        mvInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MvInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError: ", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(MvInfo mvInfo) {
//                        MvInfo.ResultBean result = mvInfo.getResult();
//                        List<MVListInfo.ResultBean.MvListBean> mv_list = result.getMv_list();
//                        adapterVideoList.setData(mv_list);
                        Log.e("onNext: ", "onNext: " + mvInfo.toString());
//                        JCVideoPlayerStandard.startFullscreen(this, JCVideoPlayerStandard.class, "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4", "嫂子辛苦了");


                    }
                });

    }


    /****
     * 数据的初始化
     */
    private void initData() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(mRestService.BASE_PARAMETERS_MV)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                //必须加
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        //获取api接口的实现类的对象
        mRestService = mRetrofit.create(ApiStore.class);


        Observable<MVListInfo> observable = mRestService.getMVListInfo("1120");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MVListInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(MVListInfo mvInfo) {
                        MVListInfo.ResultBean result = mvInfo.getResult();
                        List<MVListInfo.ResultBean.MvListBean> mv_list = result.getMv_list();
                        adapterVideoList.setData(mv_list);
                    }
                });

    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (!isVisibleToUser) {
//            JCVideoPlayer.releaseAllVideos();
//        }
//    }

}
