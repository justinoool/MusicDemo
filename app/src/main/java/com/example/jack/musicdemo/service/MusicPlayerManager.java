package com.example.jack.musicdemo.service;

import android.app.IntentService;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.jack.musicdemo.data.Song;
import com.example.jack.musicdemo.music.MusicPlaylist;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.jack.musicdemo.R.drawable.c;


/**
 * Created by ${justin} on 2017/8/2117: 27
 * WeChat:Justin-Tz
 * 音乐播放的公共管理类
 */

public class MusicPlayerManager implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnErrorListener {
    private final static MusicPlayerManager instance =
            new MusicPlayerManager();
    private Context mContext;
    private MusicService mMusicService;
    private MusicPlaylist mMusicPlaylist;
    private long currentMediaId = -1; //默认当前Id

    private MediaPlayer mediaPlayer;
    private int currentProgress;
    private int mCurrentMaxDuration = 3000;


    private ArrayList<OnSongchangeListener> changeListeners
            = new ArrayList<>();

    public static MusicPlayerManager get() {
        return instance;
    }

    /**
     * 是否需要重启服务
     *
     * @param context
     */
    public static void startServiceIfNecessary(Context context) {
        if (get().mMusicService == null) {
            MusicServiceHelper.get(context).initService();
            get().mMusicService = MusicServiceHelper.get(context).getMusicService();
        }
    }

    /**
     * 设置Context上下文和Service服务
     *
     * @return
     */
    public static MusicPlayerManager from(MusicService service) {
        return MusicPlayerManager.get().setContext(service).setServeice(service);
    }

    public MusicPlayerManager setContext(Context context) {
        this.mContext = context;
        return this;
    }

    public MusicPlayerManager setServeice(MusicService service) {
        this.mMusicService = service;
        return this;
    }

    /**
     * 跟音乐播放相关的方法
     */

    public void playQueue(MusicPlaylist musicPlaylist, int position) {
        mMusicPlaylist = musicPlaylist;
        mMusicPlaylist.setCurrentPlay(position);
        play(musicPlaylist.getCurrentPlay());
    }

    /**
     * 根据下标播放已有列表的歌曲
     *
     * @param position
     */
    public void playQueueItem(int position) {
        mMusicPlaylist.setCurrentPlay(position);
        play(mMusicPlaylist.getCurrentPlay());
    }

    /**
     * 直接播放
     */
    public void play() {
        if (mMusicPlaylist != null || mMusicPlaylist.getCurrentPlay() == null) {
            return;
        }
        play(mMusicPlaylist.getCurrentPlay());
    }

    private void play(Song song) {
        if (song == null) {
            return;
        }
        //歌曲Id应当不能于默认Id才对
        boolean musicHasChaned = !(song.getId() == currentMediaId);
        if (musicHasChaned) {//如果不等于默认id
            currentProgress = 0;
            currentMediaId = song.getId();
        }
        if (mMusicService.getState() == PlaybackStateCompat.STATE_PAUSED
                && !musicHasChaned && mediaPlayer != null) {
            configMediaPlayerState();
        } else { //如果不是在暂停那就是播放

            try {
                createMediaPlayerIfNeed();
                mMusicService.setState(PlaybackStateCompat.STATE_PLAYING);

                mediaPlayer.setDataSource(mContext, song.getUri());
                mediaPlayer.prepareAsync();
                    //监听
                for (OnSongchangeListener l : changeListeners) {
                    l.onSongChanged(song);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void createMediaPlayerIfNeed() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setWakeMode(mContext, PowerManager.PARTIAL_WAKE_LOCK);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnSeekCompleteListener(this);
            mediaPlayer.setOnErrorListener(this);
        } else {
            mediaPlayer.reset();
        }
    }

    /**
     * 确定播放状态
     */
    private void configMediaPlayerState() {
        //mediaPlayer不等于空并且没有在播放
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            //当前进度等于当前位置
            if (currentProgress == mediaPlayer.getCurrentPosition()) {
                mediaPlayer.start();
                mMusicService.setState(PlaybackStateCompat.STATE_PLAYING);
            } else { //相当于已经在播放，只需要将进度条滑动到相对应的位置
                mediaPlayer.seekTo(currentProgress);
                mediaPlayer.start();
                mMusicService.setState(PlaybackStateCompat.STATE_PLAYING);
            }
        }
    }

    /**
     * 播放下一首
     */
    public void playNext() {
        currentProgress = 0;
        play(mMusicPlaylist.getNextSong());
    }

    public void playPre() {
        currentProgress = 0;
        play(mMusicPlaylist.getPreSong());
    }

    public void pause() {
        if (mMusicService.getState() == PlaybackStateCompat.STATE_PLAYING) {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                currentProgress = mediaPlayer.getCurrentPosition();
            }
        }
        mMusicService.setState(PlaybackStateCompat.STATE_PAUSED);
    }

    //恢复播放
    public void resume() {
        if (mMusicService.getState() == PlaybackStateCompat.STATE_PAUSED
                && mediaPlayer != null) {
            mediaPlayer.start();
            mMusicService.setState(PlaybackStateCompat.STATE_PLAYING);
        }
    }

    /**
     * 清除播放列表
     */
    public void clearPlayer() {
        if (getMusicPlayList() != null) { //不等于空证明列表存在
            getMusicPlayList().clear();
        }
        mMusicService.setState(PlaybackStateCompat.STATE_STOPPED);//停止
        if (mediaPlayer != null) {
            mediaPlayer.reset();//重置
        }
    }

    /**
     * 获取播放列表
     * @return
     */
    public MusicPlaylist getMusicPlayList() {

        return mMusicPlaylist;
    }

    public void stop() {
        mMusicService.setState(PlaybackStateCompat.STATE_STOPPED);
        currentProgress = getCurrentProgressInSong();//设置当前进度
        mMusicService.stopService();
    }

    /**
     * 获取当前歌曲播放进度
     */
    public int getCurrentProgressInSong() {
        return mediaPlayer != null ? mediaPlayer.getCurrentPosition() : currentProgress;
    }

    /**
     *   进度拖拽
     */

    public void seekTo(int progress) {
        if (mediaPlayer == null) {
            currentProgress = progress;
        } else {
            if (getCurrentProgressInSong() > progress) {
                //如果当前播放进度大于你拖拽的进度则回放，REWINDING是回放的意思
                mMusicService.setState(PlaybackStateCompat.STATE_REWINDING);
            } else {
                //如果大于当前播放进度则快速移动在你拖拽的位置并播放
                mMusicService.setState(PlaybackStateCompat.STATE_FAST_FORWARDING);
            }
            currentProgress = progress;
            mediaPlayer.seekTo(currentProgress);
        }
    }

    //在这和方法去真正的播放歌曲
    @Override
    public void onPrepared(MediaPlayer mp) {
        mCurrentMaxDuration = mediaPlayer.getDuration();
        configMediaPlayerState();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    /**
     * 切换播放模式
     *
     * @return
     */
    public static final int CYCLETYPE = 0; //循环
    public static final int SINGLETYPE = 0; //单曲
    public static final int RANDOMTYPE = 0; //随机
    private int currentPlayType = CYCLETYPE; //默认循环

    public void switchPlayMode() {
        if (currentPlayType == CYCLETYPE) {
            setPlayMode(CYCLETYPE);
        } else if (currentPlayType == SINGLETYPE) {
            setPlayMode(SINGLETYPE);
        } else if (currentPlayType == RANDOMTYPE) {
            setPlayMode(RANDOMTYPE);
        }

    }

    /**
     * 设置播放模式
     *
     * @param type
     */
    public void setPlayMode(int type) {
        if (type < 0 || type > 2) {
            throw new IllegalArgumentException("incorrect type");

        }
        createMediaPlayerIfNeed();
        currentPlayType = type;
        if (type == SINGLETYPE) {
            mediaPlayer.setLooping(true);
        } else {
            mediaPlayer.setLooping(false);
        }
    }

    public int getPlayMode() {
        return currentPlayType;
    }

    /**
     * 获取当前播放的歌曲
     *
     * @return
     */
    public Song getPlayingSong() {
        if (mMusicPlaylist != null) {
            return mMusicPlaylist.getCurrentPlay();
        } else
            return null;
    }

    /**
     * 返回歌曲长度
     *
     * @return
     */
    public int getCurrentMaxDuration() {
        return mCurrentMaxDuration;
    }

    /**
     * 歌曲当前长度
     *
     * @return
     */
    public int getCurrentPosition() {
        if (mediaPlayer != null)
            return mediaPlayer.getCurrentPosition();
        return 0;
    }

    /**
     * 获取播放状态
     *
     * @return
     */
    public int getState() {
        if (mMusicService != null) {
            return mMusicService.getState();
        }
        return PlaybackStateCompat.STATE_STOPPED;
    }

 public void registerListener(OnSongchangeListener listener){
     //注册监听器
     changeListeners.add(listener);
 }
public void unregisterListener(OnSongchangeListener listener){
    changeListeners.remove(listener);
}

    //封装一个播放或暂停，让其他类调用
public void playOrPause() {
    if (getState() == PlaybackStateCompat.STATE_PLAYING) {

        pause();
    } else {
        play();
    }
}

}
