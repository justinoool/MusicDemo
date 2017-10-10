package com.cainiao5.cainiaomusic.ui.album;

import com.cainiao5.cainiaomusic.data.WikiBean;

import java.util.List;

public interface MusicIPresenter {

    void getMusics(List<WikiBean> newMusics, List<WikiBean> hotMusics);

    void loadMusicFail(String msg);

}