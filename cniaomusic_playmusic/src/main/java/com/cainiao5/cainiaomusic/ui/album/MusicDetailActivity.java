package com.cainiao5.cainiaomusic.ui.album;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.net.RetrofitManager;
import com.cainiao5.cainiaomusic.common.net.callbacks.ICallback;
import com.cainiao5.cainiaomusic.common.utils.GlideUtils;
import com.cainiao5.cainiaomusic.data.BaseBean;
import com.cainiao5.cainiaomusic.data.CollectionBean;
import com.cainiao5.cainiaomusic.data.RankingInsideInfo;
import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.data.SongInfo;
import com.cainiao5.cainiaomusic.data.SongListInsideInfo;
import com.cainiao5.cainiaomusic.music.MusicPlaylist;
import com.cainiao5.cainiaomusic.service.MusicPlayerManager;
import com.cainiao5.cainiaomusic.service.OnSongChangedListener;
import com.cainiao5.cainiaomusic.ui.adapter.NewSongInsideAdapter;
import com.cainiao5.cainiaomusic.ui.adapter.RankingInsideAdapter;
import com.cainiao5.cainiaomusic.ui.play.PlayingActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @desciption: 音乐专辑详情页
 */

public abstract class MusicDetailActivity extends AppCompatActivity implements View.OnClickListener, OnSongChangedListener {
    @Bind(R.id.headerdetail)
    RelativeLayout mHeaderdetail;
    @Bind(R.id.layout_avatar)
    LinearLayout layout_avatar;
    @Bind(R.id.fra)
    FrameLayout fra;
    @Bind(R.id.album_art)
    ImageView mAlbumArt;
    @Bind(R.id.time)
    TextView time;
    private Toolbar toolbar;
    protected RecyclerView refreshView;
    private RankingInsideAdapter musicAdapter;
    private NewSongInsideAdapter mSongInsideAdapter;

    private List<Song> mSongList = new ArrayList<>();//在线歌曲列表

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getEnterTransition().setDuration(500);
        }
        MusicPlayerManager.get().registerListener(this);

        initToolBar();
        initRefreshView();
    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void initRefreshView() {

        musicAdapter = new RankingInsideAdapter(this);
        mSongInsideAdapter = new NewSongInsideAdapter(this);
        refreshView = (RecyclerView) findViewById(R.id.refresh_view);
        refreshView.setLayoutManager(new LinearLayoutManager(this));

    }

    //Ranking的详情页设置
    public void setData(BaseBean data) {
        if (data instanceof RankingInsideInfo) {
            toolbar.setTitle(((RankingInsideInfo) data).getBillboard().getName());
            GlideUtils.with(this, ((RankingInsideInfo) data).getBillboard().getPic_s192(), R.drawable.a8p, mAlbumArt);
            fra.setVisibility(View.INVISIBLE);
            musicAdapter.setData(((RankingInsideInfo) data).getSong_list());

            musicAdapter.setOnItemClickLitener(new RankingInsideAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, final int position, String songid) {
                    final MusicPlaylist songList = new MusicPlaylist();
//                //点击播放,在线播放
                    RetrofitManager.getInstance().buildRecommend(songid, new ICallback() {
                        @Override
                        public void getSongInfoSucess(SongInfo s) {
                            SongInfo.SonginfoBean songinfo = s.getSonginfo();
                            Song song = new Song(Long.parseLong(songinfo.getSong_id())
                                    , songinfo.getTitle()
                                    , Long.parseLong(songinfo.getAlbum_id())
                                    , songinfo.getAlbum_title()
                                    , Long.parseLong(songinfo.getArtist_id())
                                    , songinfo.getAuthor(), s.getBitrate().getFile_link()
                                    , s.getBitrate().getFile_size()
                                    , s.getBitrate().getFile_duration()
                                    , 1213142, "100", position, songinfo.getAlbum_title()
                                    , songinfo.getPic_premium()
                                    , 0
                                    , s.getBitrate().getFile_link(), false);

                            mSongList.add(song);
                            songList.addQueue(mSongList,true);
                            MusicPlayerManager.get().playQueue(songList, position);

                            //下载歌曲歌词文件
                            OkGo.<File>get(songinfo.getLrclink())
                                    .tag(MusicDetailActivity.this)
                                    .execute(new FileCallback(songinfo.getSong_id()+".lrc") {
                                        @Override
                                        public void onSuccess(Response<File> response) {
                                        }
                                    });

                        }

                        @Override
                        public void getSongInfoFailed(Throwable e) {
                        }
                    });

                    PlayingActivity.open(MusicDetailActivity.this);

                }
            });
        }

        refreshView.setAdapter(musicAdapter);
    }

    //歌单的详情页设置
    public void setNewSongDetailData(SongListInsideInfo data) {
        GlideUtils.with(this, data.getPic_300(), R.drawable.a8y, mAlbumArt);
        toolbar.setTitle(data.getTitle());
        layout_avatar.setVisibility(View.GONE);
        mSongInsideAdapter.setData(data);
        refreshView.setAdapter(mSongInsideAdapter);

        //歌曲点击
        mSongInsideAdapter.setOnItemClickLitener(new NewSongInsideAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, final int position, String songid) {
                final MusicPlaylist songList = new MusicPlaylist();
//                //点击播放,在线播放
                RetrofitManager.getInstance().buildRecommend(songid, new ICallback() {
                    @Override
                    public void getSongInfoSucess(SongInfo s) {
                        SongInfo.SonginfoBean songinfo = s.getSonginfo();
                        Song song = new Song(Long.parseLong(songinfo.getSong_id())
                                , songinfo.getTitle()
                                , Long.parseLong(songinfo.getAlbum_id())
                                , songinfo.getAlbum_title()
                                , Long.parseLong(songinfo.getArtist_id())
                                , songinfo.getAuthor(), s.getBitrate().getFile_link()
                                , s.getBitrate().getFile_size()
                                , s.getBitrate().getFile_duration()
                                , 1213142, "100", position, songinfo.getAlbum_title()
                                , songinfo.getPic_premium()
                                , 0
                                , s.getBitrate().getFile_link(), false);

                        mSongList.add(song);
                        songList.addQueue(mSongList,true);
                        MusicPlayerManager.get().playQueue(songList, position);

                        //下载歌曲歌词文件
                        OkGo.<File>get(songinfo.getLrclink())
                                .tag(MusicDetailActivity.this)
                                .execute(new FileCallback(songinfo.getSong_id()+".lrc") {
                                    @Override
                                    public void onSuccess(Response<File> response) {
                                    }
                                });

                    }

                    @Override
                    public void getSongInfoFailed(Throwable e) {
                    }
                });

                PlayingActivity.open(MusicDetailActivity.this);

            }
        });
    }

    public void setCollectionData(CollectionBean collection){
        GlideUtils.with(this, collection.getCoverUrl(), R.drawable.a8y, mAlbumArt);
        toolbar.setTitle(collection.getTitle());
        layout_avatar.setVisibility(View.GONE);
//        mSongInsideAdapter.setData(data);
//        refreshView.setAdapter(mSongInsideAdapter);
    }





    @Override
    public void onSongChanged(Song song) {

    }

    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {

    }
}