package com.cainiao5.cainiaomusic.model;

import android.graphics.Bitmap;
import android.text.Spanned;

import com.cainiao5.cainiaomusic.data.Song;

import java.util.List;

/**
 * @desciption: 收藏夹歌曲列表的view接口
 */

public interface CollectionPlayIView {

    void collectionDetail(int collectionId, Spanned title, Spanned description);

    void collectionCover(Bitmap cover);

    void getSongs(List<Song> songs);

    void fail(Throwable throwable);
}
