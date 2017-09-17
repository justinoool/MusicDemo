package com.example.jack.musicdemo.common.net;

import com.example.jack.musicdemo.data.AlbumInfo;
import com.example.jack.musicdemo.data.ArtistInfo;
import com.example.jack.musicdemo.data.DynamicInfo;
import com.example.jack.musicdemo.data.MVFromKG;
import com.example.jack.musicdemo.data.MVInfo;
import com.example.jack.musicdemo.data.MVListInfo;

import junit.framework.Test;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ${justin} on 2017/9/1414: 01
 * WeChat:Justin-Tz
 */


public interface ApiStore {

    String BASE_PARAMETERS_MVINFO = "http://tingapi.ting.baidu.com/v1/restserver/";
    String BASE_PARAMETERS_URL = "http://orp6z38cm.bkt.clouddn.com/";
    String BASE_PARAMETERS_DYNAMIC = "http://api.klm123.net/video/";
    //http://mobilecdngz.kugou.com/api/v5/video/list?page=1&pagesize=20&version=8800&plat=0&id=0&short=0&sort=4
    String BASE_PARAMETERS_MVKG = "http://mobilecdngz.kugou.com/api/v5/video/";

    @GET("queryLabelVideoList.do")
    Observable<DynamicInfo> getDynamicInfo(
            @Query("src") int src,
            @Query("labelid") int labelid,
            @Query("refreshcount") int refreshcount,
            @Query("pagesize") int pagesize,
            @Query("currentpage") int currentpage
            );

    @GET("cainiaomusic/mv/{mvID}.txt")
    Observable<MVListInfo> getMVListInfo(@Path("mvID") String mvId);

    @GET("ting")
    Observable<MVInfo> getMVInfo(@Query("method") String method, @Query("mv_id") String MV_ID);

    @GET("list?page=1&pagesize=20&version=8800&plat=0&id=0&short=0&sort=4")
    Observable<MVFromKG> getMVFromKGInfo();


}
