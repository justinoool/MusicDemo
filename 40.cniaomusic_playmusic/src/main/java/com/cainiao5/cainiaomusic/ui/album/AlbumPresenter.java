package com.cainiao5.cainiaomusic.ui.album;

///**
// * @desciption: 专辑界面的逻辑处理
// */
//public class AlbumPresenter implements MusicIPresenter, BannerIPresenter {
//
//    private AlbumIView albumView;
//    private BannerAction bannerAction;
//    public AlbumPresenter(AlbumIView albumView) {
//        this.albumView = albumView;
//        bannerAction = new BannerAction(this);
//    }
//
//    public void requestBanner() {
//        bannerAction.getBanners();
//    }
//
//    @Override
//    public void getMusics(List<WikiBean> newMusics, List<WikiBean> hotMusics) {
//        albumView.getMusics(newMusics, hotMusics);
//    }
//
//    @Override
//    public void loadMusicFail(String msg) {
//        albumView.loadFail(msg);
//    }
//
//    @Override
//    public void getBanners(List<BannerBean> been) {
//        if (been == null || been.size() == 0) {
//            been = new ArrayList<>();
//            BannerBean bean = new BannerBean();
//            bean.setName("key");
//            bean.setBanner("http://ofrf20oms.bkt.clouddn.com/Key%20Official.jpg");
//            bean.setKeyword("key");
//            been.add(bean);
//        }
//        albumView.getBanner(been);
//    }
//
//    @Override
//    public void fail(String msg) {
//        getBanners(null);
//    }
//}
