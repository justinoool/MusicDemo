package com.cainiao5.cainiaomusic.model;

import com.cainiao5.cainiaomusic.data.Album;

public interface PlayListIView {
    void getAlbum(boolean moeAlbum, Album album);

    void fail(String msg);
}