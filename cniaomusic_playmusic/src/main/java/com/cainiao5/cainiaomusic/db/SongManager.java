package com.cainiao5.cainiaomusic.db;

import android.text.TextUtils;

import com.cainiao5.cainiaomusic.common.utils.FileUtils;
import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.db.dao.SongDao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @desciption: 歌曲管理器，单例
 */
public class SongManager {
    private static SongManager instance;
    private Map<Long, Song> songLibrary;
    private SongDao songDao;

    public static SongManager getInstance() {
        if (instance == null) {
            instance = new SongManager();
        }
        return instance;
    }

    public SongManager() {
        songDao = new SongDao();
        songLibrary = new LinkedHashMap<>();
        updateSongLibrary();
    }

    /**
     * 异步更新歌曲信息
     */
    private void updateSongLibrary() {
        Observable.create(
                new Observable.OnSubscribe<List<Song>>() {
                    @Override
                    public void call(Subscriber<? super List<Song>> subscriber) {
                        subscriber.onNext(songDao.queryAll());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .concatMap(new Func1<List<Song>, Observable<Song>>() {
                    @Override
                    public Observable<Song> call(List<Song> songs) {
                        return Observable.from(songs);
                    }
                })
                .map(new Func1<Song, Song>() {
                    @Override
                    public Song call(Song song) {
                        if (song.getDownload() == Song.DOWNLOAD_COMPLETE && !TextUtils.isEmpty(song.getPath())) {
                            if (!FileUtils.existFile(song.getPath())) {
                                song.setDownload(Song.DOWNLOAD_NONE);
                                insertOrUpdateSong(song);
                            }
                        }
                        return song;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Song>() {
                    @Override
                    public void call(Song song) {
                        songLibrary.put(song.getId(), song);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    public Song querySong(long sid) {
        if (songLibrary.containsKey(sid)) {
            return songLibrary.get(sid);
        }
        return null;
    }

    public void updateSongFromLibrary(Song song) {
        if (songLibrary.containsKey(song.getId())) {
            Song cacheSong = songLibrary.get(song.getId());
            song.setDownload(cacheSong.getDownload());
            song.setPath(cacheSong.getPath());
        }
    }

    public void insertOrUpdateSong(final Song song) {
        Observable.create(
                new Observable.OnSubscribe<Song>() {
                    @Override
                    public void call(Subscriber<? super Song> subscriber) {
                        updateSongFromLibrary(song);
                        songDao.insertOrUpdateSong(song);
                        subscriber.onNext(song);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Song>() {
                    @Override
                    public void call(Song song) {
                        songLibrary.put(song.getId(), song);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    /**
     * 删除数据库的歌曲信息，包括下载中的temp缓存和已经下载完的歌曲
     *
     * @param song 歌曲信息
     */
    public void deleteSong(final Song song) {
        Observable.create(
                new Observable.OnSubscribe<Song>() {
                    @Override
                    public void call(Subscriber<? super Song> subscriber) {
//                        if (taskMap.containsKey(song.getId())) {
//                            BaseDownloadTask task = taskMap.get(song.getId());
//                            FileDownloader.getImpl().clear(task.getId(), task.getPath());
//                        } else {
//                            FileUtils.deleteFile(song.getPath());
//                        }
                        if (songLibrary.containsKey(song.getId())) {
                            songDao.deleteSong(song);
                        }
                        subscriber.onNext(song);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Song>() {
                    @Override
                    public void call(Song song) {
//                        taskMap.remove(song.getId());
                        songLibrary.remove(song.getId());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }



}
