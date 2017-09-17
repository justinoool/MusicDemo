package com.cainiao5.cainiaomusic.model;

import com.cainiao5.cainiaomusic.data.SongListInsideInfo;

/**
 * Created by hongkl on 17/6/30.
 */
public interface NewSongDetaiIView extends BaseIView{

    void loadMusicDetailData(SongListInsideInfo baseBean);
    void loadFail(Throwable e);


}
