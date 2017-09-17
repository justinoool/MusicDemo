package com.cainiao5.cainiaomusic.ui.album;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cainiao5.cainiaomusic.MyApplication;
import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.net.ApiLoader;
import com.cainiao5.cainiaomusic.common.net.ApiStore;
import com.cainiao5.cainiaomusic.data.SongListInfo;
import com.cainiao5.cainiaomusic.ui.adapter.NewSongAdapter;
import com.cainiao5.cainiaomusic.ui.cnmusic.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * A simple {@link Fragment} subclass.
 * 新曲
 */
public class NewSongFragment extends BaseFragment{
    public static final String TITLE = MyApplication.getInstance().getString(R.string.album);
    private RecyclerView mRecyclerView;
    private NewSongAdapter mAdapter;
    private ApiLoader mMovieLoader;

    public NewSongFragment() {
        // Required empty public constructor
    }

    public static NewSongFragment newInstance() {
        NewSongFragment fragment = new NewSongFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View parentView = inflater.inflate(R.layout.fragment_new_song, container, false);
        initData(parentView);
        getData();
        return parentView;
    }

    private void initData(View parentView) {
        mRecyclerView = (RecyclerView) parentView.findViewById(R.id.recycleView_newsong);
        mAdapter = new NewSongAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter.setOnItemClickLitener(new NewSongAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position,String listid) {
                //进入歌单详情页
                NewSongDetailActivity.open(getActivity(),1,listid);
            }
        });
    }

    private void getData() {

//        Observable<SongListInfo> observable = RetrofitManager.getInstance()
//                .getAPIService(ApiStore.BASE_PARAMETERS_URL)
//                .getSongListInfo("3");
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<SongListInfo>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(SongListInfo songListInfo) {
//                        mAdapter.setData(songListInfo.getContent());
//                    }
//                });

        OkGo.<String>get(ApiStore.GEDAN_URL)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new GsonBuilder().create();
                        SongListInfo songListInfo = gson.fromJson(response.body(), SongListInfo.class);
                        mAdapter.setData(songListInfo.getContent());
                    }
                });
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            Log.e("NewSongFragment: ", "setUserVisibleHint: ");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
