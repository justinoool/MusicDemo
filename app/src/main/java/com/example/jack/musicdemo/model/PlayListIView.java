package com.example.jack.musicdemo.model;


import com.example.jack.musicdemo.data.Album;

public interface PlayListIView {
    void getAlbum(boolean moeAlbum, Album album);

    void fail(String msg);
}