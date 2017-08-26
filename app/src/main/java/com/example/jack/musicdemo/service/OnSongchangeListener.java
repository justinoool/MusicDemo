package com.example.jack.musicdemo.service;

import android.support.v4.media.session.PlaybackStateCompat;

import com.example.jack.musicdemo.data.Song;

/**
 * Created by ${justin} on 2017/8/2117: 26
 * WeChat:Justin-Tz
 * 歌曲改变的接口监听
 */

public interface OnSongchangeListener {
        //歌曲改变回调
     void onSongChanged(Song song);

    //歌曲后台改变的回调//比如再通知上边 进行歌曲切换
    void onPlayBackStateChanged(PlaybackStateCompat state);
}
