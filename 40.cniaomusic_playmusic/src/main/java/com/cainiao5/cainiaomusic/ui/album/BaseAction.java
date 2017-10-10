//package com.cainiao5.cainiaomusic.ui.album;
//
//
//import com.cainiao5.cainiaomusic.common.net.Constant;
//import com.cainiao5.cainiaomusic.common.net.RetrofitManager;
//
//import retrofit2.Retrofit;
//
///**
// * @desciption: 基础action
// */
//public abstract class BaseAction {
//
//    protected Retrofit retrofit;
//    protected String accessToken, accessTokenSecret;
//    private String baseUrl;
//    protected String url;
//    protected String authorization;
//
//    public BaseAction(String shortUrl) {
//        this.retrofit = RetrofitManager.getInstance().getRetrofit();
//        this.accessToken = RetrofitManager.getInstance().getAccessToken();
//        this.accessTokenSecret = RetrofitManager.getInstance().getAccessTokenSecret();
//        this.baseUrl = Constant.BASE_URL;
//        this.url = baseUrl + shortUrl;
//    }
//
//
//    public String parseThrowable(Throwable e) {
//        if (e != null && e.getMessage() != null && e.getMessage().equals("HTTP 401 Unauthorized")) {
//            return Constant.UNAUTHORIZED;
//        }
//        return Constant.NETWORK_ERROR;
//    }
//}
