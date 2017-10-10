package com.cainiao5.cainiaomusic.ui.play;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.RxBus;
import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.model.event.PlayingUpdateEvent;
import com.cainiao5.cainiaomusic.service.MusicPlayerManager;
import com.cainiao5.cainiaomusic.service.OnSongChangedListener;
import com.cainiao5.cainiaomusic.ui.widget.AlbumViewPager;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class PlayingActivity extends AppCompatActivity implements OnSongChangedListener {

    @Bind(R.id.playing_fav)
    ImageView mPlayingFav;//收藏
    @Bind(R.id.albumArt)
    ImageView backAlbum;
    @Bind(R.id.playing_down)
    ImageView mPlayingDown;//下载
    @Bind(R.id.playing_more)
    ImageView mPlayingMore;//更多
    @Bind(R.id.music_duration_played)
    TextView mMusicDurationPlayed;//歌曲进度
    @Bind(R.id.play_seek)
    SeekBar mPlaySeek;//歌曲进度条
    @Bind(R.id.music_duration)
    TextView mMusicDuration;//歌曲长度
    @Bind(R.id.playing_mode)
    ImageView mPlayingMode;//歌曲模式
    @Bind(R.id.playing_pre)
    ImageView mPlayingPre;//上一首
    @Bind(R.id.playing_play)
    ImageView mPlayingPlay;//播放/暂停
    @Bind(R.id.playing_next)
    ImageView mPlayingNext;//下一首
    @Bind(R.id.playing_playlist)
    ImageView mPlayingPlaylist;//播放列表

    @Bind(R.id.view_pager)
    AlbumViewPager mViewPager;
    //    ImageView mSongCover;
    private String TAG = "PlayingActivity";

    private Song song;

    private Subscription progressSub, timerSub;
    private FragmentAdapter fAdapter;

    private PlayingUpdateEvent mEvent;
    public static void open(Context context) {

        Intent intent = new Intent();
        intent.setClass(context, PlayingActivity.class);
        context.startActivity(intent);
    }

    private Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        ButterKnife.bind(this);
        //歌曲变化注册监听
        MusicPlayerManager.get().registerListener(this);
        initData();
        setViewPager();
        updateProgress();
        updateData();
        makeStatusBarTransparent();

    }
    //设置viewpager
    private void setViewPager() {
        fAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(fAdapter);
    }


    class FragmentAdapter extends FragmentStatePagerAdapter {

        private int mChildCount = 0;

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return RoundFragment.newInstance(MusicPlayerManager.get().getPlayingSong().getCoverUrl());
        }

        @Override
        public int getCount() {
            return 1;
        }


        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

    }



    /***
     * 初始化
     */
    private void initData() {
        mEvent = new PlayingUpdateEvent(true);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        song = MusicPlayerManager.get().getPlayingSong();
        if (song == null) {
            Log.e(TAG, "没有找到正在播放的歌曲");
            finish();
        }
        mViewPager.setOffscreenPageLimit(2);

        //seekbar
        mPlaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                /**进度条拖拉**/
                if (fromUser) {
                    MusicPlayerManager.get().seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


    }


    /****
     * 更新进度条
     */
    private void updateProgress() {
        progressSub = Observable.interval(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mPlaySeek.setMax(MusicPlayerManager.get().getCurrentMaxDuration());
                        mPlaySeek.setProgress(MusicPlayerManager.get().getCurrentPosition());
                        mMusicDuration.setText(
                                formatChange(MusicPlayerManager
                                        .get()
                                        .getCurrentMaxDuration()));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("progressSub: ", throwable.toString());
                    }
                });

        timerSub = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mMusicDurationPlayed.setText(
                                formatChange(MusicPlayerManager
                                        .get()
                                        .getCurrentPosition()));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("timerSub: ", throwable.toString());

                    }
                });
    }

    /***
     * 更新数据
     */
    private void updateData() {

        fAdapter.notifyDataSetChanged();

        if (!TextUtils.isEmpty(song.getAlbumName())) {
            String title = song.getAlbumName();
            Spanned t = Html.fromHtml(title);
            getSupportActionBar().setTitle(t);
        }
        toolbar.setTitle(song.getTitle());

        if (MusicPlayerManager.get().isPlaying()) {
            mPlayingPlay.setImageResource(R.drawable.play_btn_pause);
            mEvent.setIsPlaying(true);
            //广播,Rxbus
            RxBus.getDefault().post(mEvent);
        } else{
            mPlayingPlay.setImageResource(R.drawable.play_btn_play);
            mEvent.setIsPlaying(false);
            RxBus.getDefault().post(mEvent);
        }

    }


    @OnClick({R.id.playing_mode, R.id.playing_pre, R.id.playing_play, R.id.playing_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playing_mode:


                break;
            case R.id.playing_pre:
                MusicPlayerManager.get().playPrev();
                updateData();
                break;
            case R.id.playing_play:
                if (MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PLAYING) {
                    MusicPlayerManager.get().pause();
                    updateData();
                    mPlayingPlay.setImageResource(R.drawable.play_btn_play);
                } else if (MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PAUSED) {
                    MusicPlayerManager.get().play();
                    updateData();
                    mPlayingPlay.setImageResource(R.drawable.play_btn_pause);
                }
                break;
            case R.id.playing_next:
                MusicPlayerManager.get().playNext();
                updateData();
                break;
        }
    }

    @Override
    public void onSongChanged(Song song) {
        this.song = song;
        updateData();
    }

    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {
        updatePlayStatus();
    }

    private void updatePlayStatus() {
        if (MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PLAYING) {

            mPlayingPlay.setImageResource(R.drawable.play_btn_pause);
        } else if (MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PAUSED) {
            mPlayingPlay.setImageResource(R.drawable.play_btn_play);

        }
    }


    /***
     * 对歌曲长度的格式进行转换
     *
     * @param millSeconds
     */
    public String formatChange(int millSeconds) {
        int seconds = millSeconds / 1000;
        int second = seconds % 60;
        int munite = (seconds - second) / 60;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        return decimalFormat.format(munite) + ":" + decimalFormat.format(second);
    }


    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MusicPlayerManager.get().unregisterListener(this);
        progressSub.unsubscribe();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
//            moveTaskToBack(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*设置透明状态栏*/
    private void makeStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
    }


}
