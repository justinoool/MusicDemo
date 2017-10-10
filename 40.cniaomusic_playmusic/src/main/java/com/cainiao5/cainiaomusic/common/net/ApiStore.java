package com.cainiao5.cainiaomusic.common.net;

import com.cainiao5.cainiaomusic.data.AlbumInfo;
import com.cainiao5.cainiaomusic.data.ArtistInfo;
import com.cainiao5.cainiaomusic.data.DynamicInfo;
import com.cainiao5.cainiaomusic.data.MVListInfo;
import com.cainiao5.cainiaomusic.data.MvInfo;

import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 */
public interface ApiStore {

    String BASE_PARAMETERS_ALBUM = "/?method=album.getinfo&api_key=fdb3a51437d4281d4d64964d333531d4&format=json";
    String BASE_PARAMETERS_ARTIST = "/?method=artist.getinfo&api_key=fdb3a51437d4281d4d64964d333531d4&format=json";
    String BASE_PARAMETERS_DYNAMIC = "http://orp6z38cm.bkt.clouddn.com";

    String BASE_PARAMETERS_MV = "http://orp6z38cm.bkt.clouddn.com/";
//    http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.mv.playMV&mv_id=107056505
    String BASE_PARAMETERS_MVINFO = "http://tingapi.ting.baidu.com/v1/restserver/";

    @Headers("Cache-Control: public")
    @GET(BASE_PARAMETERS_ALBUM)
    void getAlbumInfo(@Query("artist") String artist, @Query("album") String album, Callback<AlbumInfo> callback);

    @Headers("Cache-Control: public")
    @GET(BASE_PARAMETERS_ARTIST)
    void getArtistInfo(@Query("artist") String artist, Callback<ArtistInfo> callback);


    //http://orp6z38cm.bkt.clouddn.com/dynamic_new/6314.txt
    @GET("/dynamic_new/{pageID}.txt")
    Observable<DynamicInfo> getDynamicInfo(@retrofit2.http.Path("pageID") String pageID);

    @GET("cainiaomusic/mv/{mvID}.txt")
    Observable<MVListInfo> getMVListInfo(@Path("mvID") String mvId);

    @GET("ting")
    Observable<MvInfo> getMVInfo(@Query("method") String method, @Query("mv_id")String MV_ID);



}
