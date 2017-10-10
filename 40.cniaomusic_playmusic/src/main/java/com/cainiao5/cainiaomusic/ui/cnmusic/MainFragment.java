package com.cainiao5.cainiaomusic.ui.cnmusic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.CommonUtils;
import com.cainiao5.cainiaomusic.ui.adapter.MainFragmentAdapter;
import com.cainiao5.cainiaomusic.ui.adapter.MainFragmentItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout mSwiperefresh;
    @Bind(R.id.empty)
    ImageView mEmpty;
    private MainFragmentAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private List<MainFragmentItem> mList = new ArrayList<>();
    //    private PlaylistInfo playlistInfo; //playlist 管理类
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        initUI();


        return view;
    }

    /***
     * 控件初始化
     */
    private void initUI() {
        layoutManager = new LinearLayoutManager(mContext);
        mRecyclerview.setLayoutManager(layoutManager);
//        mSwiperefresh.setColorSchemeColors(ThemeUtils.getColorById(mContext, R.color.theme_color_Primary));
        mSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notifyData();
            }
        });

        //先给adapter设置空数据，异步加载好后更新数据，防止Recyclerview no attach
        mAdapter = new MainFragmentAdapter(mContext);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setHasFixedSize(true);
//        mRecyclerview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        ((SimpleItemAnimator) mRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        notifyData();

        getActivity().getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);
    }

    /***
     * 更新数据,重新加载
     */
    private void notifyData() {


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            notifyData();
        }
    }


    //为info设置数据，并放入mlistInfo
    private void setInfo(String title, int count, int id, int i) {
        MainFragmentItem information = new MainFragmentItem();
        information.title = title;
        information.count = count;
        information.avatar = id;
        if (mList.size() < 4) {
            mList.add(new MainFragmentItem());
        }
        mList.set(i, information); //将新的info对象加入到信息列表中
    }


    //设置音乐overflow条目
    private void setMusicInfo() {

        if (CommonUtils.isLollipop() && ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            loadCount(false);
        } else {
            loadCount(true);
        }
    }


    private void loadCount(boolean has) {
        int localMusicCount = 0, recentMusicCount = 0,downLoadCount = 0 ,artistsCount = 0;
//        if(has){
//            localMusicCount = MusicUtils.queryMusic(mContext, IConstants.START_FROM_LOCAL).size();
//            recentMusicCount = TopTracksLoader.getCursor(mContext,
//                    TopTracksLoader.QueryType.RecentSongs).getCount();
//            downLoadCount = DownFileStore.getInstance(mContext).getDownLoadedListAll().size();
//            artistsCount = MusicUtils.queryArtist(mContext).size();
//        }
        setInfo(mContext.getResources().getString(R.string.local_music), localMusicCount, R.drawable.music_icn_local, 0);
        setInfo(mContext.getResources().getString(R.string.recent_play), recentMusicCount, R.drawable.music_icn_recent, 1);
        setInfo(mContext.getResources().getString(R.string.local_manage), downLoadCount, R.drawable.music_icn_dld, 2);
        setInfo(mContext.getResources().getString(R.string.my_artist), artistsCount, R.drawable.music_icn_artist, 3);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
