package com.example.jack.musicdemo.model;

import com.example.jack.musicdemo.data.Song;

import java.util.List;

/**
 * Created by ${justin} on 2017/8/2010: 01
 * WeChat:Justin-Tz
 *
 * 本地音乐下歌曲、专辑、歌手的接口
 */

public interface ILocalView {

    //歌曲
    interface LocalMusic{
        //获取歌曲成功与失败
        void  getLocalMusicSuccess(List<Song> songs);
        void  getLocalMusicFaild(Throwable throwable);
    }

    //专辑
    interface LocalAlbum{

    }
    //歌手
    interface LocalArtist{

    }
}
