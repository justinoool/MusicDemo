package com.cainiao5.cainiaomusic.model;

import com.cainiao5.cainiaomusic.data.Album;
import com.cainiao5.cainiaomusic.data.WikiBean;

public interface PlayListIView {
    void getAlbum(boolean moeAlbum, WikiBean wiki, Album album);

    void fail(String msg);
}