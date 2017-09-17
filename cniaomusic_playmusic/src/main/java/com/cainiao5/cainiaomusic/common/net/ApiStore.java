package com.cainiao5.cainiaomusic.common.net;

import com.cainiao5.cainiaomusic.data.AlbumInfo;
import com.cainiao5.cainiaomusic.data.ArtistInfo;
import com.cainiao5.cainiaomusic.data.DynamicInfo;
import com.cainiao5.cainiaomusic.data.MvListInfo;
import com.cainiao5.cainiaomusic.data.RankingInfo;
import com.cainiao5.cainiaomusic.data.RankingInsideInfo;
import com.cainiao5.cainiaomusic.data.SongInfo;
import com.cainiao5.cainiaomusic.data.SongListInfo;
import com.cainiao5.cainiaomusic.data.SongListInsideInfo;

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

    String BASE_PARAMETERS_URL = "http://orp6z38cm.bkt.clouddn.com/";
    String GEDAN_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.diy.gedan&page_size=60&page_no=1";
    String GEDANINFO_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.diy.gedanInfo&listid=";
    String MV_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.mv.searchMV&order=0&page_num=1&page_size=20";
    String MVINFO_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.mv.playMV&mv_id=";
    String DYNAMIC_URL = "http://api.klm123.net/video/queryLabelVideoList.do?src=2000&labelid=46&refreshcount=3&pagesize=10&currentpage=";

    @Headers("Cache-Control: public")
    @GET(BASE_PARAMETERS_ALBUM)
    void getAlbumInfo(@Query("artist") String artist, @Query("album") String album, Callback<AlbumInfo> callback);

    @Headers("Cache-Control: public")
    @GET(BASE_PARAMETERS_ARTIST)
    void getArtistInfo(@Query("artist") String artist, Callback<ArtistInfo> callback);

    @GET("dynamic/vedio/{pageID}")
    Observable<DynamicInfo> getDynamicInfo(@retrofit2.http.Path("pageID") String pageID);

    @GET("cainiaomusic/ranking_last")
    Observable<RankingInfo> getRankingListInfo();

    @GET("rankinginside/{rankingID}")
    Observable<RankingInsideInfo> getRankingInsideInfo(@Path("rankingID") String rankingID);

    @GET("cainiaomusic/mv/{mvid}")
    Observable<MvListInfo> getMVListInfo(@Path("mvid") String mvid);

    @GET("cainiaomusic/gedan/{page}")
    Observable<SongListInfo> getSongListInfo(@Path("page") String page);

    @GET("cainiaomusic/gedaninfo/{listid}")
    Observable<SongListInsideInfo> getSongListInsideInfo(@Path("listid") String listid);

    @GET("cainiaomusic/play/{songid}")
    Observable<SongInfo> getPlaySongInfo(@Path("songid") String songid);

    @GET("http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.song.play&songid={songid}")
    Observable<SongInfo> getPlaySongInfoTest(@Path("songid") String songid);




}
