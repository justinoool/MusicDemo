package com.example.jack.musicdemo.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.example.jack.musicdemo.data.Song;
import com.example.jack.musicdemo.music.MusicRecentPlayList;

import java.io.IOException;

import static android.os.Build.VERSION_CODES.M;

/**
 * 音乐播放的服务
 * <p>
 * 1.开启服务 -- 开启服务的帮助类
 * 2.MediaSession框架，专门解决媒体播放时界面和服务通讯的问题，谷歌提供
 * 3.监听的接口 -- 歌曲的切换
 * 4.音乐播放的管理
 */
public class MusicService extends Service implements OnSongchangeListener{
    //在服务中实现歌曲改变是，将歌曲添加到最近播放列表中
    @Override
    public void onSongChanged(Song song) {
        //添加播放过的歌曲
        MusicRecentPlayList.getInstance().addPlaySong(song);
    }

    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {

    }

    public final Binder mBinder = new MyBingder();
    private MusicPlayerManager mPlayerManager;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat mState;

    /**
     * 用于获取服务
     * 由于Android 中的Service使用了onBind 的方法去绑定服务，
     * 返回一个Ibinder对象进行操作，
     * 而我们要获取具体的Service方法的内容的时候，
     * 我们需要Ibinder对象返回具体的Service对象才能操作
     */
    public class MyBingder extends Binder {
        public MusicService getMusicService() {
            return MusicService.this;
        }
    }

    /**
     * 停止服务
     */
    public void stopService() {
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //服务的入口方法
        MediaButtonReceiver.handleIntent(mediaSession, intent);//为了捕捉到用户按了什么键
        return super.onStartCommand(intent, flags, startId);
    }

    public static MediaPlayer mediaplayer = new MediaPlayer();

    public void setUp() {
        //在这个管理类中去写初始化的内容
        mPlayerManager = MusicPlayerManager.from(this);
        setUpMediaSession();
    }

    /**
     * 使用MediaButtonReceiver来兼容api21之前的版本
     */
    private void setUpMediaSession() {
        ComponentName componentName = new ComponentName(getPackageName(), MediaButtonReceiver.class.getName());
        mediaSession = new MediaSessionCompat(this, "fd", componentName, null);
        //设置flag处理mediabutton
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        //设置回调，我们重写
        mediaSession.setCallback(new MediaSessionCallback());
        setState(PlaybackStateCompat.STATE_NONE);
    }

    /**
     * 设置播放状态
     * 每次操作前都要判断当前状态
     */
    public void setState(int state) {
        mState = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT|
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE |
                        PlaybackStateCompat.ACTION_STOP |
                        PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID |
                        PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH | //搜索
                        PlaybackStateCompat.ACTION_SEEK_TO |
                        PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM //作用：播放到哪首歌时直接跳过
                )
                .setState(state,PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN
        ,1.0f, SystemClock.elapsedRealtime()) //1.0是播放速度，elapsedealtime运行实时更新
                .build();
        //设置播放状态
        mediaSession.setPlaybackState(mState);
        mediaSession.setActive(state != PlaybackStateCompat.STATE_NONE
                && state != PlaybackStateCompat.STATE_STOPPED);

    }

    /**
     * 获取播放状态
     * @return
     */
  public int getState(){
      return mState.getState();
  }

    @Override
    public IBinder onBind(Intent intent) {// 返回的是 IBinder 对象
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    class MediaSessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            super.onPlay();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public void onStop() {
            super.onStop();
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
        }

        /**
         * 拖拉
         *
         * @param pos
         */
        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
        }
    }
    //mediasession框架已经帮我们做好了这些功能
/*

    public void  play(String path){
        mediaplayer.reset();//重置

        try {
            mediaplayer.setDataSource(path);
            mediaplayer.prepare();//缓冲
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
/**
 * 当缓冲完成的时候
 *//*

        mediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                     mediaplayer.start();
            }
        });

    }

    */
/**
 * 暂停
 *//*

        public void pause(){
            if(mediaplayer != null){
                mediaplayer.pause();
            }
        }

    */
/**
 * 停止
 *//*

    public void stop(){
        if(mediaplayer != null){
            mediaplayer.stop();
        }
    }

      public void continueMusic(){
          mediaplayer.start();
      }
*/


}
