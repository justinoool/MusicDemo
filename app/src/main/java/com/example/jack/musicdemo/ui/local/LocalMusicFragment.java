package com.example.jack.musicdemo.ui.local;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.data.Song;
import com.example.jack.musicdemo.model.ILocalView;
import com.example.jack.musicdemo.music.MusicPlaylist;
import com.example.jack.musicdemo.presenter.LocalLibraryPresenter;
import com.example.jack.musicdemo.service.MusicPlayerManager;
import com.example.jack.musicdemo.ui.adapter.LocalRecyclerAdapter;
import com.example.jack.musicdemo.ui.adapter.OnItemClickListener;
import com.example.jack.musicdemo.ui.play.PlayingActivity;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * A simple {@link Fragment} subclass.
 * 本地音乐中的 单曲列表碎片
 *
 * 配置：1：歌曲的实体bean类，//由于教程里面没讲解实体类，我直接拿它的来用
 *       2：歌曲的获取然后设置到recyclerview  //获取的过程，数据的处里过程，逻辑部分
 *       3.recyclerview的适配器
 *
 * 权限获取，6.0以上
 *       4.歌曲播放
 *       5.歌曲存取
 */
public class LocalMusicFragment extends BaseFragment implements ILocalView.LocalMusic {


    private RecyclerView mRecyclerView;
    private LocalRecyclerAdapter mAdapter;
    private LocalLibraryPresenter mLibraryPresenter;
    private MusicPlaylist mMusicPlaylist;
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
        mLibraryPresenter = new LocalLibraryPresenter(getActivity(), this);
        mMusicPlaylist = new MusicPlaylist();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view =  inflater.inflate(R.layout.fragment_local_music, container, false);
        initRecyclerView(view);
        return view ;
    }

    /**
     * 初始化recyclerview
     * @param view
     */
    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.local_recycler_view);
        mAdapter = new LocalRecyclerAdapter(getActivity());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Object item, int position) {
                MusicPlayerManager.get().playQueue(mMusicPlaylist,position);
              //   startActivity(new Intent(getActivity(), PlayingActivity.class));
                gotoSongPlayerActivity();
            }

            @Override
            public void onItemSettingClick(View v, Object item, int position) {

            }
        });

        mLibraryPresenter.requestMusic();
    }


    @Override
    public void getLocalMusicSuccess(List<Song> songs) {
        //获取到本地音乐的数据
        mMusicPlaylist.setQueue(songs);
        mMusicPlaylist.setTitle("本地歌曲");
        mAdapter.setData(songs);
    }

    @Override
    public void getLocalMusicFaild(Throwable throwable) {
        Toast.makeText(getContext(), "获取本地音乐失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
      mAdapter.notifyDataSetChanged();
    }
}
