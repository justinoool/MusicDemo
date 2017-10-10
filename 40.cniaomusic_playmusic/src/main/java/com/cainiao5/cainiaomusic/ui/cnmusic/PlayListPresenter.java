package com.cainiao5.cainiaomusic.ui.cnmusic;


import com.cainiao5.cainiaomusic.MyApplication;
import com.cainiao5.cainiaomusic.common.utils.LocalMusicLibrary;
import com.cainiao5.cainiaomusic.data.Album;
import com.cainiao5.cainiaomusic.data.Song;
import com.cainiao5.cainiaomusic.model.PlayListIView;

/**
 * @Author: cpacm
 * @Date: 2016/10/28.
 * @description:
 */

public class PlayListPresenter {

    private PlayListIView playListView;

    public PlayListPresenter(PlayListIView playListView) {
        this.playListView = playListView;
    }

    public void requestAlbum(Song song) {
            Album album = LocalMusicLibrary.getAlbum(MyApplication.getInstance(), song.getAlbumId());
            playListView.getAlbum(false, null, album);
    }

}
