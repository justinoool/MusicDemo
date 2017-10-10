package com.cainiao5.cainiaomusic.ui.collection;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.service.OnSongChangedListener;
import com.cainiao5.cainiaomusic.ui.widget.RefreshRecyclerView;

/**
 * @author: cpacm
 * @date: 2016/10/18
 * @desciption: 音乐专辑详情页
 */

public abstract class MusicDetailActivity extends AppCompatActivity implements RefreshRecyclerView.RefreshListener, View.OnClickListener, OnSongChangedListener {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getEnterTransition().setDuration(500);
        }

    }

}