package com.cainiao5.cainiaomusic.ui.album;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;

import com.cainiao5.cainiaomusic.data.BaseBean;
import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.model.RankingDetaiIView;

/**
 * @desciption: 信息详情页
 */
public class RankingDetailActivity extends MusicDetailActivity implements RankingDetaiIView {
    private static int rankingID = -1;
    private MusicDetailPresenter mPresenter;

    public static void open(Context context, int pos) {
        rankingID = pos;
        Intent intent = new Intent();
        intent.setClass(context, RankingDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        parseDataWithType();
        mPresenter = new MusicDetailPresenter(this, this);
        mPresenter.initListData(0,"" +rankingID);
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
    public void loadMusicDetailData(BaseBean baseBean) {
        setData(baseBean);
    }

    @Override
    public void loadFail() {

    }
}