package com.cainiao5.cainiaomusic.ui.album;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.net.ApiLoader;
import com.cainiao5.cainiaomusic.common.net.ApiStore;
import com.cainiao5.cainiaomusic.data.MvInfo;
import com.cainiao5.cainiaomusic.data.MvListInfo;
import com.cainiao5.cainiaomusic.ui.adapter.MVAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * A simple {@link Fragment} subclass.
 * 歌单
 */
public class MVFragment extends Fragment {


    private RecyclerView mvRecycleView;

    private MVAdapter adapterVideoList;
    private ApiLoader mMovieLoader;

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
            public void onItemClick(View view, int position, String mvID, String mvName) {
                OkGo.<String>get(ApiStore.MVINFO_URL + mvID)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Gson gson = new Gson();
                                MvInfo mvInfo = gson.fromJson(response.body(), MvInfo.class);
                                JCVideoPlayerStandard.startFullscreen(getActivity(), JCVideoPlayerStandard.class,
                                        mvInfo.getResult().getFiles().getValue31().getFile_link(),
                                        mvInfo.getResult().getMv_info().getTitle());
                            }
                        });


            }


        });
        return mvView;
    }


    /****
     * 数据的初始化
     */
    private void initData() {
        //使用方式
        OkGo.<String>get(ApiStore.MV_URL)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new GsonBuilder().create();
                        MvListInfo mvListInfo = gson.fromJson(response.body(), MvListInfo.class);
                        adapterVideoList.setData(mvListInfo.getResult().getMv_list());
                    }
                });

    }

}
