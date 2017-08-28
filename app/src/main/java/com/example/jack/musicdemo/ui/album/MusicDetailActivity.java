package com.example.jack.musicdemo.ui.album;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.common.util.GlideUtils;
import com.example.jack.musicdemo.data.CollectionBean;
import com.example.jack.musicdemo.data.Song;
import com.example.jack.musicdemo.service.MusicPlayerManager;
import com.example.jack.musicdemo.service.OnSongchangeListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 音乐专辑详情页面 与 收藏夹歌单是同一个布局
 */
public class MusicDetailActivity extends AppCompatActivity implements OnSongchangeListener {

    @Bind(R.id.album_art)
    ImageView mAlbumArt;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.fra)
    FrameLayout fra;
    @Bind(R.id.layout_avatar)
    LinearLayout layout_avatar;
    @Bind(R.id.headerdetail)
    RelativeLayout mHeaderdetail;

    private Toolbar mToolbar;
    protected RecyclerView refreshView;
    //private RankingInsideAdapter musicAdapter;
   // private NewSongInsideAdapter mSongInsideAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
        ButterKnife.bind(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().getEnterTransition().setDuration(500);
        }
        MusicPlayerManager.get().registerListener(this);

        initToolBar();
        initRefreshView();
    }


    private void initRefreshView() {

    }


    private void initToolBar() {
       mToolbar = (Toolbar) findViewById(R.id.toolbar);
     setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
public void setCollectionData(CollectionBean collection){
    GlideUtils.with(this,collection.getCoverUrl(),R.drawable.a8y,mAlbumArt);
    mToolbar.setTitle(collection.getTitle());
    layout_avatar.setVisibility(View.GONE);
}

    @Override
    public void onSongChanged(Song song) {

    }

    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {

    }
}
