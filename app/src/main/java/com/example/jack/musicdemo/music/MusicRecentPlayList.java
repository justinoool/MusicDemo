package com.example.jack.musicdemo.music;

import com.example.jack.musicdemo.MyApplication;

import com.example.jack.musicdemo.common.utils.ACache;
import com.example.jack.musicdemo.data.Song;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by ${justin} on 2017/8/2420: 45
 * WeChat:Justin-Tz
 * 最近播放列表
 */

public class MusicRecentPlayList {

    private int RECENT_COUNT = 20; //20首
    private String PLAY_QUEUE = "song_queue";
    //用来存放歌曲
    private ArrayList<Song> queue;
    //单例模式
    private static MusicRecentPlayList instance;
    public static MusicRecentPlayList getInstance() {
        if (instance == null) {
            instance = new MusicRecentPlayList();
        }
        return instance;
    }

    /**
     * 获取歌曲队列，可用于最近播放列表
     * @return
     */
    public ArrayList<Song> getQueue(){
        return queue;
    }
    // 清空歌曲列表
   public void clearRecentPlayList(){
       queue.clear();
   }

    /**
     * 读取缓存中的歌曲
     */
    private MusicRecentPlayList(){
        queue= readQueueFromFileCache();
    }

    /**
     *    歌曲的添加
     */
    public void addPlaySong(Song song) {
        queue.add(song);
        for (int i = queue.size() - 1; i > 0; i--) {//最近播放列表显示有限制，添加多少就移除之前多少
            //最近播放的歌曲有数量的限制
            if (i > RECENT_COUNT) {
                queue.remove(i);
                continue;
            }
            //判断歌曲不能重复
            if (song.getId() == queue.get(i).getId()) {//添加歌曲的id跟之前id一样则移除一个
                queue.remove(i);
                break;
            }
        }
        //添加完歌曲可以将歌曲添加到缓存里面，这个退出应用后在进入，最近播放列表数据还会显示
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                addQueueToFileCache();
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    /**
     * 将播放列表缓存到文件中以便下次读取
     */
    private void addQueueToFileCache() {
        //参数1：上下文 ,参数2 ：自定义的缓存名称
        ACache.get(MyApplication.getInstance(), PLAY_QUEUE).put(PLAY_QUEUE, queue);
    }

    /**
     * 歌曲的读取
     */
    private ArrayList<Song> readQueueFromFileCache() {
        Object object = ACache.get(MyApplication.getInstance(), PLAY_QUEUE).getAsObject(PLAY_QUEUE);

        if (object != null && object instanceof ArrayList) {
            return (ArrayList<Song>) object;
        }
        return new ArrayList<>();
    }

}
