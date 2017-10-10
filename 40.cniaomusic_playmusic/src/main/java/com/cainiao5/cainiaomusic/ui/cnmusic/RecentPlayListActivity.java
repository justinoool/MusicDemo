package com.cainiao5.cainiaomusic.ui.cnmusic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cainiao5.cainiaomusic.BaseActivity;
import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.music.MusicPlaylist;
import com.cainiao5.cainiaomusic.music.MusicRecentPlaylist;
import com.cainiao5.cainiaomusic.service.MusicPlayerManager;
import com.cainiao5.cainiaomusic.service.OnSongChangedListener;
import com.cainiao5.cainiaomusic.ui.adapter.OnItemClickListener;
import com.cainiao5.cainiaomusic.ui.adapter.RecentPlayAdapter;
import com.cainiao5.cainiaomusic.ui.play.PlayingActivity;
import com.lb.materialdesigndialog.base.DialogBase;
import com.lb.materialdesigndialog.base.DialogWithTitle;
import com.lb.materialdesigndialog.impl.MaterialDialogNormal;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @desciption: 最近播放界面
 */

public class RecentPlayListActivity extends AppCompatActivity implements OnSongChangedListener {

    @Bind(R.id.btnRight)
    Button mBtnRight;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    public static void open(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RecentPlayListActivity.class);
        context.startActivity(intent);
    }
    private RecentPlayAdapter recentAdapter;

    private MusicPlaylist musicPlaylist;
    private ActionBar ab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recent_play_list);
        ButterKnife.bind(this);

        setToolBar();

        MusicPlayerManager.get().registerListener(this);

        initRecyclerView();

    }

    /***
     * 设置toolbar
     */
    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.actionbar_back);
        ab.setTitle("最近播放");
    }

    private void initRecyclerView() {
        mBtnRight.setText("清空");

        recentAdapter = new RecentPlayAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recentAdapter);
        recentAdapter.setData(MusicRecentPlaylist.getInstance().getQueue());
        musicPlaylist = new MusicPlaylist(MusicRecentPlaylist.getInstance().getQueue());
        recentAdapter.setSongClickListener(new OnItemClickListener<Song>() {
            @Override
            public void onItemClick(Song song, int position) {
                MusicPlayerManager.get().playQueue(musicPlaylist, position);
                gotoSongPlayerActivity();
            }

            @Override
            public void onItemSettingClick(View v, Song song, int position) {
                showPopupMenu(v, song, position);
            }
        });
    }

    private void showPopupMenu(final View v, final Song song, final int position) {

        final PopupMenu menu = new PopupMenu(this, v);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popup_song_play:
                        MusicPlayerManager.get().playQueue(musicPlaylist, position);
                        gotoSongPlayerActivity();
                        break;
                    case R.id.popup_song_addto_playlist:
                        MusicPlaylist mp = MusicPlayerManager.get().getMusicPlaylist();
                        if (mp == null) {
                            mp = new MusicPlaylist();
                            MusicPlayerManager.get().setMusicPlaylist(mp);
                        }
                        mp.addSong(song);
                        break;
                    case R.id.popup_song_fav:
                        showCollectionDialog(song);
                        break;
                    case R.id.popup_song_download:
                        break;
                }
                return false;
            }
        });
        menu.inflate(R.menu.popup_recently_playlist_setting);
        menu.show();
    }

    /**
     * 显示选择收藏夹列表的弹窗
     *
     * @param song
     */
    public void showCollectionDialog(final Song song) {

    }

    @Override
    public void onSongChanged(Song song) {
        recentAdapter.setData(MusicRecentPlaylist.getInstance().getQueue());
        musicPlaylist = new MusicPlaylist(MusicRecentPlaylist.getInstance().getQueue());
    }

    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicPlayerManager.get().unregisterListener(this);
    }


    @OnClick(R.id.btnRight)
    public void onClick() {
        MaterialDialogNormal d = new MaterialDialogNormal(this);
        d.setTitle("");
        d.setMessage("清空全部所有最近播放记录?");
        d.setNegativeButton("清空", new DialogWithTitle.OnClickListener() {
            @Override
            public void click(DialogBase dialog, View view) {
                MusicRecentPlaylist.getInstance().clearRecentPlayList();
                recentAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        d.setPositiveButton("取消", new DialogWithTitle.OnClickListener() {
            @Override
            public void click(DialogBase dialog, View view) {
                dialog.dismiss();
            }
        });

    }


    /**
     * 跳转到音乐播放界面
     * @return
     */
    public boolean gotoSongPlayerActivity() {
        if (MusicPlayerManager.get().getPlayingSong() == null) {
            Toast.makeText(RecentPlayListActivity.this, R.string.music_playing_none, Toast.LENGTH_SHORT).show();
            return false;
        }
        PlayingActivity.open(this);
        return true;
    }
}
