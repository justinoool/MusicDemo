package com.example.jack.musicdemo.music;




import com.example.jack.musicdemo.data.Song;
import com.example.jack.musicdemo.service.MusicPlayerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @desciption: 播放列表
 * 对歌曲对存放,查找
 */
public class MusicPlaylist {

    private List<Song> queue;
    private Song curSong;
    private long albumID;
    private String title;

    public MusicPlaylist(List<Song> queue){
        setQueue(queue);

    }

    public MusicPlaylist(){
        queue = new ArrayList<>();
    }

    /****
     * 设置队列
     * @param queue
     */
    public void setQueue(List<Song> queue) {
        this.queue = queue;
        setCurrentPlay(0);
    }

    /***
     * 设置当前播放歌曲
     * @param position
     * @return
     */
    public long setCurrentPlay(int position) {
        if(queue.size() > position && position >= 0){
            curSong = queue.get(position);
            return curSong.getId();
        }
        return -1;
    }

    /***
     * 获取当前播放歌曲
     * @return
     */
    public Song getCurrentPlay() {
        if (curSong == null) {
            setCurrentPlay(0);
        }
        return curSong;
    }

    public List<Song> getQueue(){
        return queue;
    }

    /***
     * 添加歌曲队列
     * @param songs
     * @param head
     */
    public void addQueue(List<Song> songs,boolean head){
        if(!head){
            queue.addAll(songs);
        }else
            queue.addAll(0,songs);
    }

    /***
     * 添加歌曲
     * @param song
     */
    public void addSong(Song song){
        queue.add(song);
    }

    /***
     * 添加歌曲,带位置
     * @param song
     * @param position
     */
    public void addSong(Song song,int position){
        queue.add(position,song);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getAlbumID() {
        return albumID;
    }

    public void setAlbumID(long albumID) {
        this.albumID = albumID;
    }

    /**
     * 获取队列的上一首歌
     * @return
     */
    public Song getPreSong() {
        int currentPos = queue.indexOf(curSong);
        int mode = MusicPlayerManager.get().getPlayMode();
        if(mode == MusicPlayerManager.SINGLETYPE || mode == MusicPlayerManager.CYCLETYPE){
            if (--currentPos < 0)
                currentPos = 0;
        }else{
            currentPos = new Random().nextInt(queue.size());
        }
        curSong = queue.get(currentPos);
        return curSong;
    }


    /**
     * 获取队列的下一首歌
     * @return
     */

    public Song getNextSong() {
      int currentPos = queue.indexOf(curSong);
        //播放模式，比如循环，单曲等
        int mode = MusicPlayerManager.get().getPlayMode();
        if(mode == MusicPlayerManager.SINGLETYPE || mode == MusicPlayerManager.CYCLETYPE){
            if (++currentPos >= queue.size())//这里应该是循环的判断方法获取下一首哥
                currentPos = 0;
        }else{
            currentPos = new Random().nextInt(queue.size());
        }
        curSong = queue.get(currentPos);//获取当前下标的歌曲
        return curSong;
    }



    /***
     * 清除列表当前歌曲
     */
    public void clear(){
        queue.clear();
        curSong = null;
    }

}
