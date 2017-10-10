package com.cainiao5.cainiaomusic.service;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.music.MusicPlaylist;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.media.session.PlaybackStateCompat.STATE_FAST_FORWARDING;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_PAUSED;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_PLAYING;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_REWINDING;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_STOPPED;

/**
 *
 *
 */
public class MusicPlayerManager implements AudioManager.OnAudioFocusChangeListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener {

    private static final String TAG = "MusicPlayer";

    private final static MusicPlayerManager instance = new MusicPlayerManager();

    private Context mContext;

    private MusicService musicService;

    private MediaPlayer mediaPlayer;
    private MusicPlaylist musicPlaylist;
    private boolean playFocusGain;
    private long currentMediaId = -1;
    private int currentProgress;


    /**
     * This is the value you would use for tis situation:
     * The user presses "Previous". if the current position in the song is greater then this value,
     * pressing "Previous" will restart the song, if not it will play the previous song
     */
    public static final int MAX_DURATION_FOR_REPEAT = 3000;
    private int currentMaxDuration = MAX_DURATION_FOR_REPEAT;
    private ArrayList<OnSongChangedListener> changedListeners = new ArrayList<>();


    /***
     * 单例实现
     *
     * @return
     */
    public static MusicPlayerManager get() {
        return instance;
    }


    /***
     * 设置Context和Service,返回MusicPlayerManager对象
     *
     * @param service
     * @return
     */
    public static MusicPlayerManager from(MusicService service) {
        return MusicPlayerManager.get().setContext(service).setService(service);
    }

    /***
     * 是否有必要重新启动服务
     *
     * @param c
     */
    public static void startServiceIfNecessary(Context c) {
        if (get().musicService == null) {
            MusicServiceHelper.get(c).initService();
            get().musicService = MusicServiceHelper.get(c).getService();
        }
    }

    public MusicPlayerManager setContext(Context c) {
        this.mContext = c;
        return this;
    }

    public MusicPlayerManager setService(MusicService service) {
        this.musicService = service;
        return this;
    }




    /**
     * 创建一个新的播放列表
     *
     * @param musicPlaylist
     * @param position      first play position
     */
    public void playQueue(MusicPlaylist musicPlaylist, int position) {
        this.musicPlaylist = musicPlaylist;
        musicPlaylist.setCurrentPlay(position);
        play(musicPlaylist.getCurrentPlay());
    }


    /**
     * 通过下标播放音乐
     *
     * @param position
     */
    public void playQueueItem(int position) {
        musicPlaylist.setCurrentPlay(position);
        play(musicPlaylist.getCurrentPlay());
    }

    public boolean isPlaying(){
        if(musicService.getState() == PlaybackStateCompat.STATE_PLAYING){
            return true;
        }
        return false;
    }


    /**
     * 播放歌曲
     *
     * @param song
     */
    public void play(Song song) {
        if (song == null) return;
//        playFocusGain = true;
//        tryToGetAudioFocus();
        boolean mediaHasChanged = !(song.getId() == currentMediaId);
        if (mediaHasChanged) {
            currentProgress = 0;
            currentMediaId = song.getId();
        }
        if (musicService.getState() == STATE_PAUSED && !mediaHasChanged && mediaPlayer != null) {
            configMediaPlayerState();
        } else {
//            musicService.setState(STATE_STOPPED);
//            relaxResources(false);

            try {
                createMediaPlayerIfNeeded();
                musicService.setState(STATE_PLAYING);
                Log.e("uri: ", "uri: " + song.getUri());
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(mContext, song.getUri());
                mediaPlayer.prepareAsync();

                for (OnSongChangedListener l : changedListeners) {
                    l.onSongChanged(song);
                }

                musicService.setAsForeground();
            } catch (Exception e) {
                Log.e(TAG, "playing song:", e);
            }
        }
    }

    /**
     * 重新配置媒体播放器根据音频集中设置和启动/重新启动它。
     * 此方法启动/重新启动媒体播放器尊重当前音频焦点状态。
     * 如果我们有焦点,它将发挥正常,如果我们没有焦点,
     * 它将离开媒体播放器暂停或者将其设置为较低的体积,根据当前的焦点所允许的设置。
     * 该方法假设媒体播放器!=零,所以如果你调用它,你必须这样做。
     */
    private void configMediaPlayerState() {
//        Log.d(TAG, "configMediaPlayerState. mAudioFocus=" + audioFocus);
//        if (audioFocus == AUDIO_NO_FOCUS_NO_DUCK) {
        // If we don't have audio focus and can't duck, we have to pause,
//            if (musicService.getState() == STATE_PLAYING) {
//                pause();
//            }
//        } else {  // we have audio focus:
//            if (audioFocus == AUDIO_NO_FOCUS_CAN_DUCK) {
//                mediaPlayer.setVolume(VOLUME_DUCK, VOLUME_DUCK); // we'll be relatively quiet
//            } else {
//                if (mediaPlayer != null) {
//                    mediaPlayer.setVolume(VOLUME_NORMAL, VOLUME_NORMAL); // we can be loud again
//                } // else do something for remote client.
//            }
        // If we were playing when we lost focus, we need to resume playing.
//            if (playFocusGain) {
        Log.e(TAG, "configMediaPlayerState: ");
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            Log.d(TAG, "configMediaPlayerState startMediaPlayer. seeking to " + currentProgress);
            if (currentProgress == mediaPlayer.getCurrentPosition()) {
                mediaPlayer.start();
                musicService.setState(STATE_PLAYING);
            } else {
                mediaPlayer.seekTo(currentProgress);
                mediaPlayer.start();
                musicService.setState(STATE_PLAYING);
            }
        }
        playFocusGain = false;
//            }
//        }
    }


    /**
     * 释放资源服务用于回放。这包括“前台服务”的地位和可能的媒体播放器。
     *
     * @param releaseMediaPlayer Indicates whether the Media Player should also
     *                           be released or not
     */
    private void relaxResources(boolean releaseMediaPlayer) {

        musicService.removeForeground(false);

        // stop and release the Media Player, if it's available
        if (releaseMediaPlayer && mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    /**
     * Makes sure the media player exists and has been reset. This will create
     * the media player if needed, or reset the existing media player if one
     * already exists.
     */
    private void createMediaPlayerIfNeeded() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();

            // Make sure the media player will acquire a wake-lock while
            // playing. If we don't do that, the CPU might go to sleep while the
            // song is playing, causing playback to stop.
            mediaPlayer.setWakeMode(mContext, PowerManager.PARTIAL_WAKE_LOCK);

            // we want the media player to notify us when it's ready preparing,
            // and when it's done playing:
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnSeekCompleteListener(this);
        } else {
            mediaPlayer.reset();
        }
    }

    /***
     * 直接播放
     */
    public void play() {
        if (musicPlaylist == null || musicPlaylist.getCurrentPlay() == null) return;
        play(musicPlaylist.getCurrentPlay());
    }

    /**
     * 下一首
     */
    public void playNext() {
        currentProgress = 0;
        play(musicPlaylist.getNextSong());
    }

    /**
     * 上一首
     */
    public void playPrev() {
        currentProgress = 0;
        play(musicPlaylist.getPreSong());
    }

    /**
     * 暂停
     */
    public void pause() {
        if (musicService.getState() == STATE_PLAYING) {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                currentProgress = mediaPlayer.getCurrentPosition();
            }
            relaxResources(false);
            giveUpAudioFocus();
            musicService.removeForeground(false);
        }
        musicService.setState(STATE_PAUSED);
    }


    /**
     * 恢复播放
     */
    public void resume() {
        if (musicService.getState() == STATE_PAUSED && mediaPlayer != null) {
            mediaPlayer.start();
            musicService.setState(STATE_PLAYING);
            tryToGetAudioFocus();
            musicService.setAsForeground();
        } else {
            Log.d(TAG, "Not paused or MediaPlayer is null. Player is null: " + (mediaPlayer == null));
        }
    }

    /***
     * 清除播放列表
     */
    public void clearPlayer() {
        // stop and release the Media Player, if it's available
        if (getMusicPlaylist() != null) {
            getMusicPlaylist().clear();
        }
        musicService.setState(STATE_STOPPED);
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
        giveUpAudioFocus();
        musicService.removeForeground(false);
    }


    /**
     * 停止服务
     */
    public void stop() {
        musicService.setState(STATE_STOPPED);
        currentProgress = getCurrentProgressInSong();
        giveUpAudioFocus();
        relaxResources(true);
        musicService.removeForeground(true);
        musicService.stopService();
    }

    /***
     * 进度拖拽
     *
     * @param progress
     */
    public void seekTo(int progress) {
        if (mediaPlayer == null)
            currentProgress = progress;
        else {
            if (getCurrentProgressInSong() > progress) {
                musicService.setState(STATE_REWINDING);
            } else {
                musicService.setState(STATE_FAST_FORWARDING);
            }
            currentProgress = progress;
            mediaPlayer.seekTo(currentProgress);

        }
    }


    public void playOrpause(){
        if(isPlaying()){
            pause();
        }else
            play();
    }




    /***
     * 设置音量
     *
     * @param mediaVolume
     */
    public void setVolume(int mediaVolume) {
//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mediaVolume, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
    }

    /**
     * set the play mode
     */
    public final static int SINGLETYPE = 0;//单曲循环
    public final static int CYCLETYPE = 1;//列表循环
    public final static int RANDOMTYPE = 2;//随机播放
    private int currentPlayType = CYCLETYPE;

    /**
     * 切换模式:单曲  循环  列表  随机
     */
    public int switchPlayMode() {
        if (currentPlayType == CYCLETYPE) {
            setPlayMode(SINGLETYPE);
        } else if (currentPlayType == SINGLETYPE) {
            setPlayMode(RANDOMTYPE);
        } else if (currentPlayType == RANDOMTYPE) {
            setPlayMode(CYCLETYPE);
        }
        return currentPlayType;
    }

    /***
     * 设置模式
     *
     * @param type
     */
    public void setPlayMode(int type) {
        if (type < 0 || type > 2)
            throw new IllegalArgumentException("incorrect type");
        createMediaPlayerIfNeeded();
        currentPlayType = type;
        if (type == SINGLETYPE)
            mediaPlayer.setLooping(true);
        else
            mediaPlayer.setLooping(false);
    }

    /***
     * 获取当前播放模式
     *
     * @return
     */
    public int getPlayMode() {
        return currentPlayType;
    }


    /**
     * 释放焦点
     */
    private void giveUpAudioFocus() {
        Log.d(TAG, "giveUpAudioFocus");
//        if (audioFocus == AUDIO_FOCUSED) {
//            if (audioManager.abandonAudioFocus(this) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
//                audioFocus = AUDIO_NO_FOCUS_NO_DUCK;
//            }
//        }
    }


    /**
     * 尝试获取系统播放焦点
     */
    private void tryToGetAudioFocus() {
        Log.d(TAG, "tryToGetAudioFocus");
//        if (audioFocus != AUDIO_FOCUSED) {
//            int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
//                    AudioManager.AUDIOFOCUS_GAIN);
//            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
//                audioFocus = AUDIO_FOCUSED;
//            }
//        }
    }


    /***
     * 获取播放列表
     *
     * @return
     */
    public MusicPlaylist getMusicPlaylist() {
        return musicPlaylist;
    }

    public ArrayList<OnSongChangedListener> getChangedListeners() {
        return changedListeners;
    }

    /***
     * 设置播放列表
     *
     * @param musicPlaylist
     */
    public void setMusicPlaylist(MusicPlaylist musicPlaylist) {
        this.musicPlaylist = musicPlaylist;
    }

    /***
     * 获取当前播放进度
     *
     * @return
     */
    public int getCurrentProgressInSong() {
        return mediaPlayer != null ? mediaPlayer.getCurrentPosition() : currentProgress;
    }

    /***
     * 获取当前播放歌曲长度
     *
     * @return
     */
    public int getCurrentMaxDuration() {
        return currentMaxDuration;
    }

    /***
     * 获取当前播放进度
     *
     * @return
     */
    public int getCurrentPosition() {
        if (mediaPlayer != null)
            return mediaPlayer.getCurrentPosition();
        return 0;
    }




    /****
     * 获取当前播放歌曲
     *
     * @return
     */
    public Song getPlayingSong() {
        if (musicPlaylist != null)
            return musicPlaylist.getCurrentPlay();
        else return null;
    }

    /***
     * 获取当前状态
     *
     * @return
     */
    public int getState() {
        if (musicService != null)
            return musicService.getState();
        return PlaybackStateCompat.STATE_STOPPED;
    }


    /***
     * 获取当前播放列表
     */
    public List<Song> getQueue(){

        return MusicPlayerManager.get().getMusicPlaylist().getQueue();
    }


    public void registerListener(OnSongChangedListener l) {
        changedListeners.add(l);
    }

    public void unregisterListener(OnSongChangedListener l) {
        changedListeners.remove(l);
    }


    @Override
    public void onAudioFocusChange(int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onCompletion from MediaPlayer");
        if (!mediaPlayer.isLooping()) {
            // The media player finished playing the current song, so we go ahead and start the next.
            currentProgress = 0;
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        currentMaxDuration = mediaPlayer.getDuration();
        configMediaPlayerState();
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {

    }
}
