package com.cainiao5.cainiaomusic.common.net;

import android.util.Log;

import com.cainiao5.cainiaomusic.data.SongInfo;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/***
 * 添加网络访问方法
 */
public class ApiLoader extends ObjectLoader {
    private ApiStore mService;

    public ApiLoader() {
        //实例
        mService = RetrofitServiceManager.getInstance().create(ApiStore.class);
    }

    /**
     * 获取MV列表
     *
     * @return
     */
//    public Observable<List<MVListInfo.ResultBean.MvListBean>> getMVFromKG() {
//        return observe(mMovieService.getMVFromKGInfo("1120"))
//                .map(new Func1<MVListInfo, List<MVListInfo.ResultBean.MvListBean>>() {
//                    @Override
//                    public List<MVListInfo.ResultBean.MvListBean> call(MVListInfo mvListInfo) {
//                        return mvListInfo.getResult().getMv_list();
//                    }
//                });
//    }

//    public Observable<List<SongListInfo.DataBean>> getMVFromSingInfo() {
//        return observe(mMovieService.getMVFromSingInfo("1"))
//                .map(new Func1<SongListInfo, List<SongListInfo.DataBean>>() {
//                    @Override
//                    public List<SongListInfo.DataBean> call(SongListInfo songListInfo) {
//                        return songListInfo.getData();
//                    }
//                });
//    }
//    public Observable<List<NewSongDetailInfo.DataBean>> getNewSongDetail(String id) {
//        return observe(mMovieService.getNewSongDetaiInfo(id)).map(new Func1<NewSongDetailInfo, List<NewSongDetailInfo.DataBean>>() {
//            @Override
//            public List<NewSongDetailInfo.DataBean> call(NewSongDetailInfo newSongDetailInfo) {
//                return newSongDetailInfo.getData();
//            }
//        });
//    }

}