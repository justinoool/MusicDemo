package com.cainiao5.cainiaomusic.model;


import com.cainiao5.cainiaomusic.data.Album;
import com.cainiao5.cainiaomusic.data.Artist;
import com.cainiao5.cainiaomusic.data.Song;

import java.util.List;

/**
 * 本地视图的接口
 */
public interface LocalIView {



    interface LocalMusic{
        void getLocalMusic(List<Song> songs);
    }

    interface LocalAlbum{
        void getLocalAlbum(List<Album> alba);
    }

    interface LocalArtist{
        void getLocalArtist(List<Artist> artists);
    }

}
