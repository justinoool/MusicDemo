package com.cainiao5.cainiaomusic.ui.dynamic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.net.ApiStore;
import com.cainiao5.cainiaomusic.data.DynamicInfo;
import com.cainiao5.cainiaomusic.ui.adapter.RecyclerViewVideoAdapter;

import java.util.List;

import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 */
public class DynamicFragment extends Fragment {

    RecyclerView recyclerView;

    private RecyclerViewVideoAdapter adapterVideoList;
    private Retrofit mRetrofit;
    private ApiStore mRestService;
    public DynamicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_dynamic, container, false);
        ButterKnife.bind(this, inflate);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerview);
        initData();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterVideoList = new RecyclerViewVideoAdapter(getActivity());
        recyclerView.setAdapter(adapterVideoList);

        return inflate;
    }

    /****
     * 数据的初始化
     */
    private void initData() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(mRestService.BASE_PARAMETERS_DYNAMIC)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                //必须加
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        //获取api接口的实现类的对象
        mRestService = mRetrofit.create(ApiStore.class);

        Observable<DynamicInfo> observable = mRestService.getDynamicInfo("6314");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DynamicInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DynamicInfo dynamicInfo) {
                        List<DynamicInfo.ResultBean> result = dynamicInfo.getResult();
                        adapterVideoList.setData(result);
                    }
                });

    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(!isVisibleToUser){
            JCVideoPlayer.releaseAllVideos();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
