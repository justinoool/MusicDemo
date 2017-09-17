package com.cainiao5.cainiaomusic.ui.play;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.RxBus;
import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.model.event.PlayingUpdateEvent;
import com.cainiao5.cainiaomusic.service.MusicPlayerManager;
import com.cainiao5.cainiaomusic.service.OnSongChangedListener;
import com.cainiao5.cainiaomusic.ui.cnmusic.PlayQueueFragment;
import com.cainiao5.cainiaomusic.ui.collection.SongInfoPopup;
import com.cainiao5.cainiaomusic.ui.widget.AlbumViewPager;
import com.cainiao5.cainiaomusic.ui.widget.lrcview.LrcView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
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
    ImageView bgImage;
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

    @Bind(R.id.lrc_small)
    LrcView mLrcView;
    @Bind(R.id.headerView)
    FrameLayout mHeaderView;
    @Bind(R.id.lrcviewContainer)
    RelativeLayout mLrcviewContainer;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.view_line)
    View mViewLine;
    @Bind(R.id.volume_seek)
    SeekBar mVolumeSeek;
    @Bind(R.id.volume_layout)
    LinearLayout mVolumeLayout;
    @Bind(R.id.playing_cmt)
    ImageView mPlayingCmt;
    @Bind(R.id.music_tool)
    LinearLayout mMusicTool;


    //    ImageView mSongCover;
    private String TAG = "PlayingActivity";

    private Song song;

    private Subscription progressSub, timerSub;
    private FragmentAdapter fAdapter;

    private PlayingUpdateEvent mEvent;

    private int[] resID = new int[]{R.mipmap.bg01,
            R.mipmap.bg02,
            R.mipmap.bg03,
            R.mipmap.bg04,R.mipmap.bg05};

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
        initLrc();
        getLrcData();
        setViewPager();
        updateProgress();
        updateData();
        makeStatusBarTransparent();

    }

    private void initLrc() {
        mLrcView.setZOrderOnTop(true);
        mLrcView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        //viewpager和歌词控件的切换
        mViewPager.setOnSingleTouchListener(new AlbumViewPager.OnSingleTouchListener() {
            @Override
            public void onSingleTouch(View v) {
                if (mHeaderView.getVisibility() == View.VISIBLE) {
                    mHeaderView.setVisibility(View.INVISIBLE);
                    mLrcviewContainer.setVisibility(View.VISIBLE);
                    mMusicTool.setVisibility(View.INVISIBLE);
                }
            }
        });
        mLrcviewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLrcviewContainer.getVisibility() == View.VISIBLE) {
                    mLrcviewContainer.setVisibility(View.INVISIBLE);
                    mHeaderView.setVisibility(View.VISIBLE);
                    mMusicTool.setVisibility(View.VISIBLE);
                }
            }
        });

        Random random = new Random();
        int i = random.nextInt(4);
        bgImage.setImageResource(resID[i]);

        //音量的大小的调节
        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int v = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int mMaxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mVolumeSeek.setMax(mMaxVol);
        mVolumeSeek.setProgress(v);
        mVolumeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.ADJUST_SAME);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    /***
     * 获取歌词数据
     */
    private void getLrcData() {
        mLrcView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLrcView.loadLrc(getLrcText(MusicPlayerManager.get().getPlayingSong().getId() + ".lrc"));
            }
        }, 3000);
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

                        mLrcView.updateTime(MusicPlayerManager.get().getCurrentPosition());

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
        if (song != null) {
        }

        fAdapter.notifyDataSetChanged();
        if (song == null) {
            Toast.makeText(PlayingActivity.this, "song ==null", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(song.getAlbumName())) {
            String title = song.getAlbumName();
            Spanned t = Html.fromHtml(title);
            getSupportActionBar().setTitle(t);
        }
        toolbar.setTitle(song.getTitle());

        if (MusicPlayerManager.get().isPlaying()) {
            mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_pause);
            mEvent.setIsPlaying(true);
            //广播,Rxbus
            RxBus.getDefault().post(mEvent);
        } else {
            mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_play);
            mEvent.setIsPlaying(false);
            RxBus.getDefault().post(mEvent);
        }

    }


    @OnClick({R.id.playing_more,R.id.playing_playlist,R.id.lrcviewContainer, R.id.playing_mode, R.id.playing_pre, R.id.playing_play, R.id.playing_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playing_mode:
                break;
            case R.id.playing_pre:
                MusicPlayerManager.get().playPrev();
                updateData();
                break;
            case R.id.playing_play:
                if (MusicPlayerManager.get().isPlaying()) {
                    MusicPlayerManager.get().pause();
                    mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_play);
                    mEvent.setIsPlaying(true);
                    RxBus.getDefault().post(mEvent);
                } else {
                    MusicPlayerManager.get().play();
                    mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_pause);
                    mEvent.setIsPlaying(false);
                    RxBus.getDefault().post(mEvent);
                }
                break;
            case R.id.playing_next:
                MusicPlayerManager.get().playNext();
                updateData();
                break;
            case R.id.playing_playlist:
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PlayQueueFragment playQueueFragment = new PlayQueueFragment();
                        playQueueFragment.show(getSupportFragmentManager(), "playqueueframent");
                    }
                }, 60);
                break;
            case R.id.playing_more:
                SongInfoPopup popupFragment = SongInfoPopup.newInstance(song,this);
                popupFragment.show(getSupportFragmentManager(), "");
                break;
        }
    }

    /***
     * 获取歌词文本
     *
     * @param fileName
     * @return
     */
    private String getLrcText(String fileName) {
        String lrcText = null;
        try {
            File file = new File("sdcard/download/" + fileName);
            FileInputStream fis = new FileInputStream(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            lrcText = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lrcText;
    }

    @Override
    public void onSongChanged(Song song) {
        this.song = song;
        updateData();
        getLrcData();
    }

    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {
        updatePlayStatus();
    }

    private void updatePlayStatus() {
        if (MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PLAYING) {

            mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_pause);
        } else if (MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PAUSED) {
            mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_play);

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
