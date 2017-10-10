package com.cainiao5.cainiaomusic.model;

import com.cainiao5.cainiaomusic.data.BannerBean;
import com.cainiao5.cainiaomusic.data.WikiBean;

import java.util.List;

public interface AlbumIView {

    void getMusics(List<WikiBean> newMusics, List<WikiBean> hotMusics);

    void getBanner(List<BannerBean> been);

    void loadFail(String msg);
}
