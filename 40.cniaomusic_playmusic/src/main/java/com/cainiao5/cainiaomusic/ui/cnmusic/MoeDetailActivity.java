package com.cainiao5.cainiaomusic.ui.cnmusic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;

import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.data.WikiBean;
import com.cainiao5.cainiaomusic.ui.collection.MusicDetailActivity;

/**
 * @desciption: 信息详情页
 */
public class MoeDetailActivity extends MusicDetailActivity {

    public static void open(Context context, WikiBean wiki) {
        context.startActivity(getIntent(context, wiki));
    }

    public static Intent getIntent(Context context, WikiBean wiki) {
        Intent intent = new Intent();
        intent.setClass(context, MoeDetailActivity.class);
        intent.putExtra("wiki", wiki);
        return intent;
    }

    private WikiBean wikiBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
    public void onSwipeRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}