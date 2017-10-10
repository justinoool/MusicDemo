package com.cainiao5.cainiaomusic.ui.dynamic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.net.ApiStore;
import com.cainiao5.cainiaomusic.data.DynamicInfo;
import com.cainiao5.cainiaomusic.ui.adapter.RecyclerViewVideoAdapter;
import com.cainiao5.cainiaomusic.ui.widget.EndLessOnScrollListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import retrofit2.Retrofit;

import static android.R.attr.path;
import static com.cainiao5.cainiaomusic.R.id.body;

/**
 */
public class DynamicFragment extends Fragment {

    RecyclerView recyclerView;

    private RecyclerViewVideoAdapter adapterVideoList;
    private Retrofit mRetrofit;
    private ApiStore mRestService;
    private LinearLayoutManager mLinearLayoutManager;
    private List<DynamicInfo.BodyBean.DetailBean> resultData = new ArrayList<>();
    private int num = 1;
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
        initData(num);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        adapterVideoList = new RecyclerViewVideoAdapter(getActivity());
        recyclerView.setAdapter(adapterVideoList);


        recyclerView.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                initData(currentPage++);
                Log.e("onLoadMore: ", "onLoadMore: ");
            }
        });
        return inflate;
    }

    /****
     * 数据的初始化
     */
    private void initData(int num) {

        OkGo.<String>get(ApiStore.DYNAMIC_URL + num)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        String decodeStr = null;
                        try {
                            decodeStr = URLDecoder.decode(body, "utf-8");
                            Gson gson = new Gson();
                            DynamicInfo dynamicInfo = gson.fromJson(decodeStr, DynamicInfo.class);
                            List<DynamicInfo.BodyBean.DetailBean> result = dynamicInfo.getBody().getDetail();
                            resultData.addAll(result);
                            adapterVideoList.setData(resultData);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
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
