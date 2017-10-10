package com.cainiao5.cainiaomusic.ui.local;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.model.LocalIView;
import com.cainiao5.cainiaomusic.music.MusicPlaylist;
import com.cainiao5.cainiaomusic.service.MusicPlayerManager;
import com.cainiao5.cainiaomusic.ui.adapter.LocalRecyclerAdapter;
import com.cainiao5.cainiaomusic.ui.adapter.OnItemClickListener;
import com.cainiao5.cainiaomusic.ui.cnmusic.BaseFragment;
import com.cainiao5.cainiaomusic.ui.presenter.LocalLibraryPresenter;

import java.util.List;

import magicasakura.widgets.TintImageView;

/**
 * A simple {@link Fragment} subclass.
 * 本地音乐歌曲片段
 * TODO:
 * 1.Song实体类
 * 2.RecyclerView的适配器
 * 3.歌曲的存放
 * 4.歌曲如何获取(rxjava)
 * 5.添加权限
 */
public class LocalMusicFragment extends BaseFragment implements LocalIView.LocalMusic{


    private LocalLibraryPresenter mLibraryPresenter;
    private RecyclerView mRecyclerView;
    private LocalRecyclerAdapter mRecyclerAdapter;
    private MusicPlaylist musicPlayList;
    private int prePosition = -1;
    private TintImageView playState;

    public LocalMusicFragment() {
        // Required empty public constructor
    }

    public static LocalMusicFragment newInstance() {
        LocalMusicFragment fragment = new LocalMusicFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLibraryPresenter = new LocalLibraryPresenter(this, getActivity());
        musicPlayList = new MusicPlaylist();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_local_music, container, false);
        initRecycleView(inflate);
        return inflate;
    }

    private void initRecycleView(View inflate) {
        Log.e("initRecycleView: ", "initRecycleView: ");
        mRecyclerAdapter = new LocalRecyclerAdapter(getActivity());
        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Object item, int position) {
                if(prePosition != position){
                    //播放音乐
                    MusicPlayerManager.get().playQueue(musicPlayList, position);
                    prePosition = position;
                }
                gotoSongPlayerActivity();
            }

            @Override
            public void onItemSettingClick(View v, Object item, int position) {

            }
        });
        mLibraryPresenter.requestMusic();
    }

    @Override
    public void getLocalMusic(List<Song> songs) {
        musicPlayList.setQueue(songs);
        musicPlayList.setTitle("本地歌曲");
        mRecyclerAdapter.setData(songs);
    }


    @Override
    public void onResume() {
        super.onResume();
        mRecyclerAdapter.notifyDataSetChanged();
    }
}
