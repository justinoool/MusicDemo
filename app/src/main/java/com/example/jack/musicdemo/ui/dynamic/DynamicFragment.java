package com.example.jack.musicdemo.ui.dynamic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.common.net.ApiStore;
import com.example.jack.musicdemo.common.net.RetrofitManager;
import com.example.jack.musicdemo.data.DynamicInfo;
import com.example.jack.musicdemo.ui.adapter.RecyclerViewVideoAdapter;
import com.example.jack.musicdemo.ui.widget.EndLessOnScrollListener;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
 * 动态
 */
public class DynamicFragment extends Fragment {
    RecyclerView recyclerView;

    private RecyclerViewVideoAdapter adapterVideoList;
    private Retrofit mRetrofit;
    private ApiStore mRestService;
    private LinearLayoutManager mLinearLayoutManager;
    private int num = 1;
    private List<DynamicInfo.BodyBean.DetailBean> resultData = new ArrayList<>();
    public DynamicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_dynamic, container, false);
        ButterKnife.bind(this, inflate);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.dynamic_recyclerView);

        initData(num);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        adapterVideoList = new RecyclerViewVideoAdapter(getActivity());
        recyclerView.setAdapter(adapterVideoList);

        recyclerView.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                initData(currentPage++);
                Toast.makeText(getActivity(), "滑动了", Toast.LENGTH_SHORT).show();
            }
        });

        return inflate;
    }

    /****
     * 数据的初始化
     */
    private void initData(int num) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(mRestService.BASE_PARAMETERS_DYNAMIC)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                //必须加
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        //获取api接口的实现类的对象
        mRestService = mRetrofit.create(ApiStore.class);

        Observable<DynamicInfo> observable = mRestService.getDynamicInfo(2000,46,3,10,num);
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
                        List<DynamicInfo.BodyBean.DetailBean> detail = dynamicInfo.getBody().getDetail();
                        adapterVideoList.setData(detail);
                        adapterVideoList.notify();

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
