package com.example.jack.musicdemo.ui.play;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jack.musicdemo.BaseActivity;
import com.example.jack.musicdemo.R;
import com.example.jack.musicdemo.common.util.ImageUtils;
import com.example.jack.musicdemo.data.Song;
import com.example.jack.musicdemo.service.MusicPlayerManager;
import com.example.jack.musicdemo.service.OnSongchangeListener;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static android.os.Build.VERSION_CODES.M;

/**
 * 歌曲播放界面
 *
 * 有bug
 * 1.歌曲的图片没有切换
 * 2.标题也不对
 * 3.拖动没效果
 * 4.返回键没作用
 * 5.开启播放页面是play按钮初始化图片不对
 */
public class PlayingActivity extends AppCompatActivity implements OnSongchangeListener{
    //进度条
    @Bind(R.id.play_seek)
    SeekBar mPlaySeek;
    //歌曲时间长度
    @Bind(R.id.music_duration)
    TextView mMusicDuration;
    //点点点
    @Bind(R.id.playing_mode)
    ImageView mPlayingMode;
    //上一首
    @Bind(R.id.playing_pre)
    ImageView mPlayingPre;
    //播放
    @Bind(R.id.playing_play)
    ImageView mPlayingPlay;
    //下一首
    @Bind(R.id.playing_next)
    ImageView mPlayingNext;
    //播放列表
    @Bind(R.id.playing_playlist)
    ImageView mPlayingPlaylist;

    @Bind(R.id.music_duration_played)
    TextView mMusicDurationPlayed;

    @Bind(R.id.coverImage)
    ImageView mCoverImage;

    private Toolbar toolbar;
    private Song song;


/*    private List<Song> mAllSongs;
    private MusicService musicService;
    private MyServiceConn conn;
    private int index;
    private MediaPlayer mMediaPlayer;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //设置一下当前进度
            mPlaySeek.setProgress(mMediaPlayer.getCurrentPosition());
            //设置歌曲时间
            mMusicDuration.setText(formatChange(mMediaPlayer.getDuration()));

            mHandler.postDelayed(mRunnable, 100);//使用PostDelayed方法想当于定时器没1/10秒调用此runnable

        }
    };*/

    /*private class MyServiceConn implements ServiceConnection {
        //服务连接
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MyBingder myBingder = (MusicService.MyBingder) service;
            musicService = myBingder.getMusicService();
            mMediaPlayer = musicService.mediaplayer;
            if (mMediaPlayer != null) {
                //默认播放音乐
                playMusic(0);
                //改变图标，pause
                mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_pause);
                //完成的监听
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        playMusic(1);
                    }
                });
            }else {
                //改变图标
                mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_play);
            }
           //设置音乐时长
            mPlaySeek.setMax(mMediaPlayer.getDuration());
            mHandler.removeCallbacks(mRunnable);
            //removeCallbacks方法是删除指定的Runnable对象，使线程对象停止运行。
            mHandler.post(mRunnable);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
       ButterKnife.bind(this);

        //注册监听
        MusicPlayerManager.get().registerListener(this);

        initData();
        // initMusic();
        updateProgress();
        updateData();
    }


    /**
     * 更新进度条和进度显示和歌曲长度
     */
    private void updateProgress() {
        //MILLISECONDS毫秒，指400毫秒的间隔
        Observable.interval(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mPlaySeek.setMax(MusicPlayerManager.get().getCurrentMaxDuration());
                        mPlaySeek.setProgress(MusicPlayerManager.get().getCurrentPosition());
                        //设置歌曲长度
                        mMusicDuration.setText(
                                formatChange(MusicPlayerManager
                                .get()
                                .getCurrentMaxDuration()));

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        //歌曲进度的加载或者说是改变
        Observable.interval(1000,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mMusicDurationPlayed.setText(formatChange(MusicPlayerManager
                                .get()
                                .getCurrentPosition()));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    /**
     * 更新数据包括封面，标题，图标
     */
    private void updateData() {
      //歌曲的封面
        String coverUrl = song.getCoverUrl();
        ImageUtils.GlideWith(this,coverUrl,R.drawable.ah1,mCoverImage);
        //标题
        if(!TextUtils.isEmpty(song.getAlbumName())){
            String title = song.getAlbumName();//获取专辑名称
            Spanned s = Html.fromHtml(title);
            /*
              由于数据是html格式，要将html格式设置到textview要用fromhtml(),或者说想使用html格式设置显示到textview上
             */
          getSupportActionBar().setTitle(s);
        }

        toolbar.setTitle(song.getTitle());
        //图标
        if(MusicPlayerManager.get().getPlayingSong() != null){ //意味当前界面是有歌曲播放,就是一点歌曲进入界面是播放按钮是两杠
            mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_pause);
        }
    }

    /**
     * 歌曲改变的回调
     * @param song
     */
    @Override
    public void onSongChanged(Song song) {
        this.song =song;
        updateData();  //每当歌曲切换就调用歌曲信息更新
    }

    /**
     * 歌曲后台状态改变的回调
     * @param state
     */
    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {

    }
  /*  private void initMusic() {
        //获取歌曲的数据
        mAllSongs = LocalMusicLibrary.getAllSongs(this);
        mPlaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //seekbar有两种进度改变方式，一种是人为一种是代码
                if (fromUser) {//判断是否是人为
                    if (mMediaPlayer != null) {
                        mMediaPlayer.seekTo(progress);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }*/

    private void initData() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        song = MusicPlayerManager.get().getPlayingSong();
        if (song == null) {
            finish();
        }

        mPlaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           //进度条改变
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                     if(fromUser){
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

    @OnClick({R.id.playing_pre, R.id.playing_play, R.id.playing_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.playing_pre:
                //   palyMusicByStatu(2);
                MusicPlayerManager.get().playPre();
                break;
            case R.id.playing_play:
            /*    if (mMediaPlayer != null) {
                    if (mMediaPlayer.isPlaying()) {
                        musicService.pause();
                        //改变图标play
                      mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_play);
                    } else {
                        musicService.continueMusic();
                        //改变图标pause
                        mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_pause);
                    }
                }*/
                if(MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PLAYING){
                    //若为播放状态
                    MusicPlayerManager.get().pause();
                    mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_play);
                }else if(MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PAUSED){
                    //若为暂停状态
                    MusicPlayerManager.get().play();
                    mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_pause);
                }
                break;
            case R.id.playing_next:
                MusicPlayerManager.get().playNext();
                //   palyMusicByStatu(1);
                break;
        }
    }
/*
    public void palyMusicByStatu(int status) {
        switch (status) {
            case 0:
                break;
            case 1: //下一首
                index++;
                if (index == mAllSongs.size()) {
                    index = 0;
                }
                break;
            case 2:  //上一首
                index--;
                if (index == -1) {
                    index = mAllSongs.size() - 1;
                }
                break;
        }
        playMusic(index);
    }*/

  /*  *//**
     * 根据歌曲下标播放歌曲
     *
     * @param index
     *//*
    private void playMusic(int index) {
        if (mAllSongs.size() > 0) {
            musicService.play(mAllSongs.get(index).getPath());
            mMusicDuration.setText(
                    formatChange(mAllSongs.get(index).getDuration()));//设置时间
        }
        //设置进度条
        mPlaySeek.setMax(mMediaPlayer.getDuration());
    }
*/

    /**
     * 对歌曲长度的格式进行切换
     *
     * @param millSeconds
     */
    public String formatChange(int millSeconds) {
        int seconds = millSeconds / 1000;  //1000等于1秒
        int second = seconds % 60;
        int mintue = (seconds - second) / 60;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        return decimalFormat.format(mintue) + " : " + decimalFormat.format(second);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // unbindService(conn); //如果不解绑，退出应用，音乐会关闭
  MusicPlayerManager.get().unregisterListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void open(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, PlayingActivity.class);
        context.startActivity(intent);
    }


}
