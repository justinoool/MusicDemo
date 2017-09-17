package com.cainiao5.cainiaomusic.ui.album;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.widget.Toast;

import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.data.SongListInsideInfo;
import com.cainiao5.cainiaomusic.model.NewSongDetaiIView;

/***
 * 详情界面
 */
public class NewSongDetailActivity extends MusicDetailActivity implements NewSongDetaiIView{
    private static int type = -1;//判断类型
    private static String listID;//歌单id
    private MusicDetailPresenter mPresenter;

    public static void open(Context context, int pos,String listid) {
        type = pos;
        listID=listid;
        Intent intent = new Intent();
        intent.setClass(context, NewSongDetailActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MusicDetailPresenter(this, this);
        mPresenter.initListData(type,listID);
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onSongChanged(Song song) {

    }

    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {

    }

    @Override
    public void loadMusicDetailData(SongListInsideInfo songListInsideInfo) {
        setNewSongDetailData(songListInsideInfo);
    }

    @Override
    public void loadFail(Throwable e) {
        Toast.makeText(NewSongDetailActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
