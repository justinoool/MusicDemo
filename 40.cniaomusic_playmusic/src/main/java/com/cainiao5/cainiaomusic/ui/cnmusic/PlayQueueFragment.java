package com.cainiao5.cainiaomusic.ui.cnmusic;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.DefaultItemAnimator;
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

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.data.Album;
import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.data.WikiBean;
import com.cainiao5.cainiaomusic.model.PlayListIView;
import com.cainiao5.cainiaomusic.music.MusicPlaylist;
import com.cainiao5.cainiaomusic.service.MusicPlayerManager;
import com.cainiao5.cainiaomusic.service.OnSongChangedListener;
import com.cainiao5.cainiaomusic.ui.adapter.OnItemClickListener;
import com.cainiao5.cainiaomusic.ui.adapter.PlayListAdapter;
import com.cainiao5.cainiaomusic.ui.widget.DividerItemDecoration;
import com.lb.materialdesigndialog.base.DialogBase;
import com.lb.materialdesigndialog.base.DialogWithTitle;
import com.lb.materialdesigndialog.impl.MaterialDialogNormal;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayQueueFragment extends DialogFragment implements OnSongChangedListener,PlayListIView {

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
        //设置样式
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
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setAttributes(params);


        View view = inflater.inflate(R.layout.fragment_queue, container);
        ButterKnife.bind(this, view);

        MusicPlayerManager.get().registerListener(this);



        playListPresenter = new PlayListPresenter(this);

        initRecyclerView(view);


        return view;

    }

    private void initRecyclerView(View view) {
        playListAdapter = new PlayListAdapter(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.play_list);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(playListAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (MusicPlayerManager.get().getMusicPlaylist() != null) {
            playListAdapter.setData(MusicPlayerManager.get().getMusicPlaylist().getQueue());
            Log.e("initRecyclerView: ", "initRecyclerView: " + MusicPlayerManager.get().getMusicPlaylist().getQueue().size());
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


    @Override
    public void onStart() {
        super.onStart();
        //设置fragment高度 、宽度
        int dialogHeight = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.6);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, dialogHeight);
        getDialog().setCanceledOnTouchOutside(true);

    }




    @OnClick({R.id.playlist_addto, R.id.playlist_clear_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playlist_addto:

                break;
            case R.id.playlist_clear_all:
                showBasicDialog();
                break;
        }
    }


    @Override
    public void onSongChanged(Song song) {
        if (MusicPlayerManager.get().getMusicPlaylist() != null) {
            playListAdapter.setData(MusicPlayerManager.get().getMusicPlaylist().getQueue());
        }
    }

    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {

    }

    @Override
    public void getAlbum(boolean moeAlbum, WikiBean wiki, Album album) {

    }

    @Override
    public void fail(String msg) {

    }


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
