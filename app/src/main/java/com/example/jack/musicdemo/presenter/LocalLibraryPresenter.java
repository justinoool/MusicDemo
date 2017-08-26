package com.example.jack.musicdemo.presenter;


import android.content.Context;

import com.example.jack.musicdemo.common.util.LocalMusicLibrary;
import com.example.jack.musicdemo.data.Song;
import com.example.jack.musicdemo.model.ILocalView;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.example.jack.musicdemo.R.drawable.c;

/**
 * Created by ${justin} on 2017/8/1916: 22
 * WeChat:Justin-Tz
 * 歌曲获取的逻辑处理
 */

public class LocalLibraryPresenter {

    private Context mContext;
    private ILocalView.LocalMusic mILocalMusic;

    public LocalLibraryPresenter(Context context,ILocalView.LocalMusic iLocalMusic) {
        this.mContext = context;
        this.mILocalMusic=iLocalMusic;
    }


    /**
     * 获取本地音乐
     */
    public void requestMusic() {
        Observable.create(new Observable.OnSubscribe<List<Song>>() {

            @Override
            public void call(Subscriber<? super List<Song>> subscriber) {
                //从底层获取音乐
                List<Song> songs = LocalMusicLibrary.getAllSongs(mContext);
                subscriber.onNext(songs);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())//切换到主线程
                .subscribe(new Action1<List<Song>>() {
                    @Override
                    public void call(List<Song> songs) {
                        //歌曲获取成功
                        if(mILocalMusic != null){
                            mILocalMusic.getLocalMusicSuccess(songs);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //歌曲获取失败
                        if(mILocalMusic != null){
                            mILocalMusic.getLocalMusicFaild(throwable);
                        }


                    }
                });
    }
}
