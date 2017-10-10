package com.example.jack.musicdemo.common.net;

import com.example.jack.musicdemo.data.AlbumInfo;
import com.example.jack.musicdemo.data.ArtistInfo;
import com.example.jack.musicdemo.data.DynamicInfo;
import com.example.jack.musicdemo.data.MVFromKG;
import com.example.jack.musicdemo.data.MVInfo;
import com.example.jack.musicdemo.data.MVListInfo;

import junit.framework.Test;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;


/**
 * Created by ${justin} on 2017/9/1414: 01
 * WeChat:Justin-Tz
 */


public interface ApiStore {
 //"http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.mv.searchMV&order=0&page_num=1&page_size=20"
    String BASE_PARAMETERS_MV = "http://tingapi.ting.baidu.com/v1/restserver/";
    String MV_TEST="http://tingapi.ting.baidu.com/";
    String MV_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.mv.searchMV&order=0&page_num=1&page_size=20";
    String BASE_PARAMETERS_DYNAMIC = "http://api.klm123.net/video/";
    String MVINFO_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.mv.playMV&mv_id=";
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
   //"http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.mv.searchMV&order=0&page_num=1&page_size=20"
   @GET("v1/restserver/ting")
    Observable<MVListInfo> getMVListInfo(@QueryMap Map<String ,String> maps);



    @GET("v1/restserver/ting")
    Observable<MVInfo> getMVInfo(@QueryMap Map<String ,String > maps1);

    @GET("list?page=1&pagesize=20&version=8800&plat=0&id=0&short=0&sort=4")
    Observable<MVFromKG> getMVFromKGInfo();


}
