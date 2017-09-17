package com.cainiao5.cainiaomusic.model;

import com.cainiao5.cainiaomusic.data.BaseBean;

/**
 * Created by hongkl on 17/6/30.
 */
public interface RankingDetaiIView extends BaseIView{

    void loadMusicDetailData(BaseBean baseBean);
    void loadFail();


}
