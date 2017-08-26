package com.example.jack.musicdemo.ui.MainMusic;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.data.Album;
import com.example.jack.musicdemo.data.Song;
import com.example.jack.musicdemo.model.PlayListIView;
import com.example.jack.musicdemo.music.MusicPlaylist;
import com.example.jack.musicdemo.service.MusicPlayerManager;
import com.example.jack.musicdemo.service.OnSongchangeListener;
import com.example.jack.musicdemo.ui.adapter.OnItemClickListener;
import com.example.jack.musicdemo.ui.adapter.PlayListAdapter;
import com.lb.materialdesigndialog.base.DialogBase;
import com.lb.materialdesigndialog.base.DialogWithTitle;
import com.lb.materialdesigndialog.impl.MaterialDialogNormal;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * 播放列表片段
 */
public class PlayQueueFragment extends DialogFragment implements OnSongchangeListener,PlayListIView {

    @Bind(R.id.playlist_addto)
    TextView mPlaylistAddto;
    @Bind(R.id.play_list_number)
    TextView mPlayListNumber;
    @Bind(R.id.playlist_clear_all)
    TextView mPlaylistClearAll;
    @Bind(R.id.play_list)
    RecyclerView recyclerView;

    private Context mContext;
    private LinearLayoutManager layoutManager;
    private PlayListAdapter playListAdapter;

    private MusicPlayerManager mPlayerManager;
    private Handler mHandler;
    private MusicPlaylist mPlaylist;
    private ArrayList<Song> playlist;
    private DividerItemDecoration itemDecoration;
    private PlayListPresenter playListPresenter;

    public PlayQueueFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置对话框片段样式，参数1 DialogFragment.STYLE_NO_FRAME 表示我们可以自定义对话框的样式
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDatePickerDialog);
        mContext = getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //设置无标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置从底部弹出
        WindowManager.LayoutParams params = getDialog().getWindow()
                .getAttributes();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);//设置对话框没有标题
        getDialog().getWindow().setAttributes(params);//设置属性

        View view = inflater.inflate(R.layout.fragment_queue, container);
        ButterKnife.bind(this, view);

        //只要我们需要对歌曲信息进行监听就必须注册监听和实现接口
        MusicPlayerManager.get().registerListener(this);

        playListPresenter = new PlayListPresenter(this);

        initRecyclerView(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置fragment高度 、宽度
        int dialogHeight = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.6);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, dialogHeight);
        getDialog().setCanceledOnTouchOutside(true);
    }

    private void initRecyclerView(View view) {
        playListAdapter = new PlayListAdapter(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.play_list);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);//固定大小
        recyclerView.setAdapter(playListAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());//设置动画

        if (MusicPlayerManager.get().getMusicPlayList() != null) {//如果歌曲列表不为空就设置 数据
            playListAdapter.setData(MusicPlayerManager.get().getMusicPlayList().getQueue());
            Log.e("initRecyclerView: ", "initRecyclerView: " + MusicPlayerManager.get().getMusicPlayList().getQueue().size());
        }

        playListAdapter.setSongClickListener(new OnItemClickListener<Song>() {
            @Override
            public void onItemClick(Song song, int position) {
                MusicPlayerManager.get().playQueueItem(position);
            }

            @Override
            public void onItemSettingClick(View v, Song song, int position) {
//                showPopupMenu(v, song, position);
            }
        });

    }







    @OnClick({R.id.playlist_addto, R.id.playlist_clear_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playlist_addto://收藏

                break;
            case R.id.playlist_clear_all://清空
                showBasicDialog();
                break;
        }
    }

  //歌曲改变在此设置数据进入 播放列表
    @Override
    public void onSongChanged(Song song) {
        if (MusicPlayerManager.get().getMusicPlayList() != null) {
            playListAdapter.setData(MusicPlayerManager.get().getMusicPlayList().getQueue());
        }
    }

    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {

    }

    @Override
    public void getAlbum(boolean moeAlbum,  Album album) {

    }

    @Override
    public void fail(String msg) {

    }

//显示是否清空列表的对话框
    private void showBasicDialog() {
        MaterialDialogNormal materialDialogNormal = new MaterialDialogNormal(getActivity());
        materialDialogNormal.setMessage("确定要清空列表吗?");
        materialDialogNormal.setNegativeButton("取消", new DialogWithTitle.OnClickListener() {
            @Override
            public void click(DialogBase dialog, View view) {
                dialog.dismiss();
            }
        });
        materialDialogNormal.setPositiveButton("确定", new DialogWithTitle.OnClickListener() {
            @Override
            public void click(DialogBase dialog, View view) {
                playListAdapter.clearAll();
                MusicPlayerManager.get().clearPlayer();
                dismiss();
            }
        });
        materialDialogNormal.setTitle("清空列表");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        MusicPlayerManager.get().unregisterListener(this);

    }
}
