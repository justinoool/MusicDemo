package com.example.jack.musicdemo.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ${justin} on 2017/9/1517: 30
 * WeChat:Justin-Tz
 * MVListInfo
 */

public  class MVListInfo {

    /**
     * error_code : 22000
     * result : {"total":720000,"havemore":"1","mv_list":[{"aliastitle":"无情的雨无情的你","all_artist_id":"163","artist":"齐秦","artist_id":"163","del_status":"0","mv_id":"63370002","play_nums":"640137","provider":"12","publishtime":"2013-06-25 05:30:05","subtitle":"字幕版","title":"无情的雨无情的你","thumbnail":"http://musicdata.baidu.com/data2/music/c302fce3d801924cfd7ec2ff53972891/63370021/63370021.jpg","thumbnail2":"http://musicdata.baidu.com/data2/music/c302fce3d801924cfd7ec2ff53972891/63370021/63370021.jpg"},{"aliastitle":"无情的雨无情的你","all_artist_id":"163","artist":"齐秦","artist_id":"163","del_status":"0","mv_id":"62735861","play_nums":"246475","provider":"12","publishtime":"2013-06-24 23:27:15","subtitle":"","title":"无情的雨无情的你","thumbnail":"http://musicdata.baidu.com/data2/music/d138958317ca4fbe12e88257cc546347/62735867/62735867.jpg","thumbnail2":"http://musicdata.baidu.com/data2/music/d138958317ca4fbe12e88257cc546347/62735867/62735867.jpg"},{"aliastitle":"你还要我怎样","all_artist_id":"88","artist":"薛之谦","artist_id":"88","del_status":"0","mv_id":"107056505","play_nums":"704017","provider":"12","publishtime":"2013-12-16 09:06:13","subtitle":"官方版","title":"你还要我怎样","thumbnail":"http://musicdata.baidu.com/data2/music/b559ec89fb89166f3992fdbabd8e5d01/107056519/107056519.jpeg","thumbnail2":"http://musicdata.baidu.com/data2/music/b559ec89fb89166f3992fdbabd8e5d01/107056519/107056519.jpeg"},{"aliastitle":"安和桥","all_artist_id":"44805274","artist":"宋冬野","artist_id":"44805274","del_status":"0","mv_id":"112633517","play_nums":"131213","provider":"12","publishtime":"2014-01-29 14:38:25","subtitle":"","title":"安和桥","thumbnail":"http://musicdata.baidu.com/data2/pic/c24b061fb60f3e208b3c294afe655d1a/261599462/261599462.jpg","thumbnail2":"http://musicdata.baidu.com/data2/pic/c24b061fb60f3e208b3c294afe655d1a/261599462/261599462.jpg"},{"aliastitle":"北国之春","all_artist_id":"116","artist":"邓丽君","artist_id":"116","del_status":"0","mv_id":"61917326","play_nums":"970519","provider":"12","publishtime":"2013-06-24 14:03:47","subtitle":"现场版","title":"北国之春","thumbnail":"http://musicdata.baidu.com/data2/music/bb0be659f41875be74eaa9d907ed4ecc/93147308/93147308.jpeg","thumbnail2":"http://musicdata.baidu.com/data2/music/bb0be659f41875be74eaa9d907ed4ecc/93147308/93147308.jpeg"},{"aliastitle":"他一定很爱你","all_artist_id":"160","artist":"阿杜","artist_id":"160","del_status":"0","mv_id":"61215060","play_nums":"126049","provider":"12","publishtime":"2013-06-24 08:20:17","subtitle":"<男才女貌>主题曲 电视剧版","title":"他一定很爱你","thumbnail":"http://musicdata.baidu.com/data2/pic/38f7ca49ee2241a171be6a8555ecee36/333143353/333143353.jpg","thumbnail2":"http://musicdata.baidu.com/data2/pic/38f7ca49ee2241a171be6a8555ecee36/333143353/333143353.jpg"},{"aliastitle":"大中国","all_artist_id":"356","artist":"高枫","artist_id":"356","del_status":"0","mv_id":"62729142","play_nums":"98805","provider":"12","publishtime":"2013-06-24 23:23:30","subtitle":"","title":"大中国","thumbnail":"http://musicdata.baidu.com/data2/music/3802d822e6f4e98ff9407af2fbf4c3b0/62729168/62729168.jpg","thumbnail2":"http://musicdata.baidu.com/data2/music/3802d822e6f4e98ff9407af2fbf4c3b0/62729168/62729168.jpg"},{"aliastitle":"轻轻的告诉你","all_artist_id":"999","artist":"杨钰莹","artist_id":"999","del_status":"0","mv_id":"125427043","play_nums":"78845","provider":"12","publishtime":"2014-12-05 16:01:08","subtitle":"","title":"轻轻的告诉你","thumbnail":"http://musicdata.baidu.com/data2/pic/c5cda929921bfdbad3c5c9380ac50fd2/327697249/327697249.jpg","thumbnail2":"http://musicdata.baidu.com/data2/pic/c5cda929921bfdbad3c5c9380ac50fd2/327697249/327697249.jpg"},{"aliastitle":"后来","all_artist_id":"74","artist":"刘若英","artist_id":"74","del_status":"0","mv_id":"98630196","play_nums":"103486","provider":"12","publishtime":"2013-10-30 19:43:59","subtitle":"猪哥会社 现场版","title":"后来","thumbnail":"http://musicdata.baidu.com/data2/music/968eac365b500d29cca4b5cc6e4aa35c/98630216/98630216.jpeg","thumbnail2":"http://musicdata.baidu.com/data2/music/968eac365b500d29cca4b5cc6e4aa35c/98630216/98630216.jpeg"},{"aliastitle":"甜蜜蜜","all_artist_id":"116","artist":"邓丽君","artist_id":"116","del_status":"0","mv_id":"63165884","play_nums":"397579","provider":"12","publishtime":"2013-06-25 03:35:46","subtitle":"<但愿人长久>15周年纪念集 现场版","title":"甜蜜蜜","thumbnail":"http://musicdata.baidu.com/data2/music/4bfc97ca21975f267d98720e065023de/63165905/63165905.jpg","thumbnail2":"http://musicdata.baidu.com/data2/music/4bfc97ca21975f267d98720e065023de/63165905/63165905.jpg"},{"aliastitle":"万水千山总是情","all_artist_id":"326","artist":"汪明荃","artist_id":"326","del_status":"0","mv_id":"60732136","play_nums":"114552","provider":"12","publishtime":"2013-06-24 04:33:08","subtitle":"","title":"万水千山总是情","thumbnail":"http://musicdata.baidu.com/data2/pic/92882878/92882878.jpg","thumbnail2":"http://musicdata.baidu.com/data2/pic/92882878/92882878.jpg"},{"aliastitle":"","all_artist_id":"1839","artist":"阿宝","artist_id":"1839","del_status":"0","mv_id":"261080701","play_nums":"26606","provider":"12","publishtime":"2016-01-06 03:20:29","subtitle":"广东跨年 现场版 15/12/31","title":"爱你在心口难开","thumbnail":"http://musicdata.baidu.com/data2/pic/b9cf9f9fc305acfac61631ef2c6bdc7b/261080877/261080877.jpg","thumbnail2":"http://musicdata.baidu.com/data2/pic/b9cf9f9fc305acfac61631ef2c6bdc7b/261080877/261080877.jpg"},{"aliastitle":"甜蜜蜜","all_artist_id":"116","artist":"邓丽君","artist_id":"116","del_status":"0","mv_id":"60382209","play_nums":"408957","provider":"12","publishtime":"2013-06-24 01:48:07","subtitle":"电视剧版","title":"甜蜜蜜","thumbnail":"http://musicdata.baidu.com/data2/music/03414884cc30baebf445ab25f9dc2ea6/60382232/60382232.jpg","thumbnail2":"http://musicdata.baidu.com/data2/music/03414884cc30baebf445ab25f9dc2ea6/60382232/60382232.jpg"},{"aliastitle":"Panama","all_artist_id":"60425915","artist":"Matteo","artist_id":"60425915","del_status":"0","mv_id":"99426552","play_nums":"3891","provider":"12","publishtime":"2013-11-02 10:13:05","subtitle":"","title":"Panama","thumbnail":"http://musicdata.baidu.com/data2/music/7e7abb15e4157de2c65498214a75728c/99426598/99426598.jpeg","thumbnail2":"http://musicdata.baidu.com/data2/music/7e7abb15e4157de2c65498214a75728c/99426598/99426598.jpeg"},{"aliastitle":"女儿情","all_artist_id":"5686850,51991","artist":"影视","artist_id":"5686850","del_status":"0","mv_id":"62320423","play_nums":"29416","provider":"12","publishtime":"2013-06-24 17:28:19","subtitle":"电视剧<西游记>插曲","title":"女儿情","thumbnail":"http://musicdata.baidu.com/data2/pic/b2fc67eb32ca4c6a52927022ef91aac6/263862575/263862575.jpg","thumbnail2":"http://musicdata.baidu.com/data2/pic/b2fc67eb32ca4c6a52927022ef91aac6/263862575/263862575.jpg"},{"aliastitle":"我只在乎你","all_artist_id":"116","artist":"邓丽君","artist_id":"116","del_status":"0","mv_id":"60293722","play_nums":"371894","provider":"12","publishtime":"2013-06-24 01:05:46","subtitle":"","title":"我只在乎你","thumbnail":"http://musicdata.baidu.com/data2/music/c6d9872aa3dddda410eb9a90e65bb988/60293737/60293737.jpg","thumbnail2":"http://musicdata.baidu.com/data2/music/c6d9872aa3dddda410eb9a90e65bb988/60293737/60293737.jpg"},{"aliastitle":"无情的雨无情的你","all_artist_id":"163","artist":"齐秦","artist_id":"163","del_status":"0","mv_id":"61350056","play_nums":"145271","provider":"12","publishtime":"2013-06-24 09:25:28","subtitle":"现场版","title":"无情的雨无情的你","thumbnail":"http://musicdata.baidu.com/data2/music/0185e1296692339cf1ca020fbfd67148/61350083/61350083.jpg","thumbnail2":"http://musicdata.baidu.com/data2/music/0185e1296692339cf1ca020fbfd67148/61350083/61350083.jpg"},{"aliastitle":"真的爱你","all_artist_id":"130","artist":"Beyond","artist_id":"130","del_status":"0","mv_id":"116279020","play_nums":"196275","provider":"12","publishtime":"2014-03-11 12:16:16","subtitle":"十大中文金曲 现场版","title":"真的爱你","thumbnail":"http://musicdata.baidu.com/data2/music/695c359e67145e12e493b5b5a2317515/116279031/116279031.jpeg","thumbnail2":"http://musicdata.baidu.com/data2/music/695c359e67145e12e493b5b5a2317515/116279031/116279031.jpeg"},{"aliastitle":"心会跟爱一起走","all_artist_id":"1449","artist":"郭峰","artist_id":"1449","del_status":"0","mv_id":"84754335","play_nums":"47047","provider":"12","publishtime":"2013-09-09 10:05:07","subtitle":"安徽卫视 黄金年代 现场版","title":"心会跟爱一起走","thumbnail":"http://musicdata.baidu.com/data2/pic/5b277d748ed974515d57541b9100b65e/324811823/324811823.jpeg","thumbnail2":"http://musicdata.baidu.com/data2/pic/5b277d748ed974515d57541b9100b65e/324811823/324811823.jpeg"}]}
     */

    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("result")
    private ResultBean result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * total : 720000
         * havemore : 1
         * mv_list : [{"aliastitle":"无情的雨无情的你","all_artist_id":"163","artist":"齐秦","artist_id":"163","del_status":"0","mv_id":"63370002","play_nums":"640137","provider":"12","publishtime":"2013-06-25 05:30:05","subtitle":"字幕版","title":"无情的雨无情的你","thumbnail":"http://musicdata.baidu.com/data2/music/c302fce3d801924cfd7ec2ff53972891/63370021/63370021.jpg","thumbnail2":"http://musicdata.baidu.com/data2/music/c302fce3d801924cfd7ec2ff53972891/63370021/63370021.jpg"},{"aliastitle":"无情的雨无情的你","all_artist_id":"163","artist":"齐秦","artist_id":"163","del_status":"0","mv_id":"62735861","play_nums":"246475","provider":"12","publishtime":"2013-06-24 23:27:15","subtitle":"","title":"无情的雨无情的你","thumbnail":"http://musicdata.baidu.com/data2/music/d138958317ca4fbe12e88257cc546347/62735867/62735867.jpg","thumbnail2":"http://musicdata.baidu.com/data2/music/d138958317ca4fbe12e88257cc546347/62735867/62735867.jpg"},{"aliastitle":"你还要我怎样","all_artist_id":"88","artist":"薛之谦","artist_id":"88","del_status":"0","mv_id":"107056505","play_nums":"704017","provider":"12","publishtime":"2013-12-16 09:06:13","subtitle":"官方版","title":"你还要我怎样","thumbnail":"http://musicdata.baidu.com/data2/music/b559ec89fb89166f3992fdbabd8e5d01/107056519/107056519.jpeg","thumbnail2":"http://musicdata.baidu.com/data2/music/b559ec89fb89166f3992fdbabd8e5d01/107056519/107056519.jpeg"},{"aliastitle":"安和桥","all_artist_id":"44805274","artist":"宋冬野","artist_id":"44805274","del_status":"0","mv_id":"112633517","play_nums":"131213","provider":"12","publishtime":"2014-01-29 14:38:25","subtitle":"","title":"安和桥","thumbnail":"http://musicdata.baidu.com/data2/pic/c24b061fb60f3e208b3c294afe655d1a/261599462/261599462.jpg","thumbnail2":"http://musicdata.baidu.com/data2/pic/c24b061fb60f3e208b3c294afe655d1a/261599462/261599462.jpg"},{"aliastitle":"北国之春","all_artist_id":"116","artist":"邓丽君","artist_id":"116","del_status":"0","mv_id":"61917326","play_nums":"970519","provider":"12","publishtime":"2013-06-24 14:03:47","subtitle":"现场版","title":"北国之春","thumbnail":"http://musicdata.baidu.com/data2/music/bb0be659f41875be74eaa9d907ed4ecc/93147308/93147308.jpeg","thumbnail2":"http://musicdata.baidu.com/data2/music/bb0be659f41875be74eaa9d907ed4ecc/93147308/93147308.jpeg"},{"aliastitle":"他一定很爱你","all_artist_id":"160","artist":"阿杜","artist_id":"160","del_status":"0","mv_id":"61215060","play_nums":"126049","provider":"12","publishtime":"2013-06-24 08:20:17","subtitle":"<男才女貌>主题曲 电视剧版","title":"他一定很爱你","thumbnail":"http://musicdata.baidu.com/data2/pic/38f7ca49ee2241a171be6a8555ecee36/333143353/333143353.jpg","thumbnail2":"http://musicdata.baidu.com/data2/pic/38f7ca49ee2241a171be6a8555ecee36/333143353/333143353.jpg"},{"aliastitle":"大中国","all_artist_id":"356","artist":"高枫","artist_id":"356","del_status":"0","mv_id":"62729142","play_nums":"98805","provider":"12","publishtime":"2013-06-24 23:23:30","subtitle":"","title":"大中国","thumbnail":"http://musicdata.baidu.com/data2/music/3802d822e6f4e98ff9407af2fbf4c3b0/62729168/62729168.jpg","thumbnail2":"http://musicdata.baidu.com/data2/music/3802d822e6f4e98ff9407af2fbf4c3b0/62729168/62729168.jpg"},{"aliastitle":"轻轻的告诉你","all_artist_id":"999","artist":"杨钰莹","artist_id":"999","del_status":"0","mv_id":"125427043","play_nums":"78845","provider":"12","publishtime":"2014-12-05 16:01:08","subtitle":"","title":"轻轻的告诉你","thumbnail":"http://musicdata.baidu.com/data2/pic/c5cda929921bfdbad3c5c9380ac50fd2/327697249/327697249.jpg","thumbnail2":"http://musicdata.baidu.com/data2/pic/c5cda929921bfdbad3c5c9380ac50fd2/327697249/327697249.jpg"},{"aliastitle":"后来","all_artist_id":"74","artist":"刘若英","artist_id":"74","del_status":"0","mv_id":"98630196","play_nums":"103486","provider":"12","publishtime":"2013-10-30 19:43:59","subtitle":"猪哥会社 现场版","title":"后来","thumbnail":"http://musicdata.baidu.com/data2/music/968eac365b500d29cca4b5cc6e4aa35c/98630216/98630216.jpeg","thumbnail2":"http://musicdata.baidu.com/data2/music/968eac365b500d29cca4b5cc6e4aa35c/98630216/98630216.jpeg"},{"aliastitle":"甜蜜蜜","all_artist_id":"116","artist":"邓丽君","artist_id":"116","del_status":"0","mv_id":"63165884","play_nums":"397579","provider":"12","publishtime":"2013-06-25 03:35:46","subtitle":"<但愿人长久>15周年纪念集 现场版","title":"甜蜜蜜","thumbnail":"http://musicdata.baidu.com/data2/music/4bfc97ca21975f267d98720e065023de/63165905/63165905.jpg","thumbnail2":"http://musicdata.baidu.com/data2/music/4bfc97ca21975f267d98720e065023de/63165905/63165905.jpg"},{"aliastitle":"万水千山总是情","all_artist_id":"326","artist":"汪明荃","artist_id":"326","del_status":"0","mv_id":"60732136","play_nums":"114552","provider":"12","publishtime":"2013-06-24 04:33:08","subtitle":"","title":"万水千山总是情","thumbnail":"http://musicdata.baidu.com/data2/pic/92882878/92882878.jpg","thumbnail2":"http://musicdata.baidu.com/data2/pic/92882878/92882878.jpg"},{"aliastitle":"","all_artist_id":"1839","artist":"阿宝","artist_id":"1839","del_status":"0","mv_id":"261080701","play_nums":"26606","provider":"12","publishtime":"2016-01-06 03:20:29","subtitle":"广东跨年 现场版 15/12/31","title":"爱你在心口难开","thumbnail":"http://musicdata.baidu.com/data2/pic/b9cf9f9fc305acfac61631ef2c6bdc7b/261080877/261080877.jpg","thumbnail2":"http://musicdata.baidu.com/data2/pic/b9cf9f9fc305acfac61631ef2c6bdc7b/261080877/261080877.jpg"},{"aliastitle":"甜蜜蜜","all_artist_id":"116","artist":"邓丽君","artist_id":"116","del_status":"0","mv_id":"60382209","play_nums":"408957","provider":"12","publishtime":"2013-06-24 01:48:07","subtitle":"电视剧版","title":"甜蜜蜜","thumbnail":"http://musicdata.baidu.com/data2/music/03414884cc30baebf445ab25f9dc2ea6/60382232/60382232.jpg","thumbnail2":"http://musicdata.baidu.com/data2/music/03414884cc30baebf445ab25f9dc2ea6/60382232/60382232.jpg"},{"aliastitle":"Panama","all_artist_id":"60425915","artist":"Matteo","artist_id":"60425915","del_status":"0","mv_id":"99426552","play_nums":"3891","provider":"12","publishtime":"2013-11-02 10:13:05","subtitle":"","title":"Panama","thumbnail":"http://musicdata.baidu.com/data2/music/7e7abb15e4157de2c65498214a75728c/99426598/99426598.jpeg","thumbnail2":"http://musicdata.baidu.com/data2/music/7e7abb15e4157de2c65498214a75728c/99426598/99426598.jpeg"},{"aliastitle":"女儿情","all_artist_id":"5686850,51991","artist":"影视","artist_id":"5686850","del_status":"0","mv_id":"62320423","play_nums":"29416","provider":"12","publishtime":"2013-06-24 17:28:19","subtitle":"电视剧<西游记>插曲","title":"女儿情","thumbnail":"http://musicdata.baidu.com/data2/pic/b2fc67eb32ca4c6a52927022ef91aac6/263862575/263862575.jpg","thumbnail2":"http://musicdata.baidu.com/data2/pic/b2fc67eb32ca4c6a52927022ef91aac6/263862575/263862575.jpg"},{"aliastitle":"我只在乎你","all_artist_id":"116","artist":"邓丽君","artist_id":"116","del_status":"0","mv_id":"60293722","play_nums":"371894","provider":"12","publishtime":"2013-06-24 01:05:46","subtitle":"","title":"我只在乎你","thumbnail":"http://musicdata.baidu.com/data2/music/c6d9872aa3dddda410eb9a90e65bb988/60293737/60293737.jpg","thumbnail2":"http://musicdata.baidu.com/data2/music/c6d9872aa3dddda410eb9a90e65bb988/60293737/60293737.jpg"},{"aliastitle":"无情的雨无情的你","all_artist_id":"163","artist":"齐秦","artist_id":"163","del_status":"0","mv_id":"61350056","play_nums":"145271","provider":"12","publishtime":"2013-06-24 09:25:28","subtitle":"现场版","title":"无情的雨无情的你","thumbnail":"http://musicdata.baidu.com/data2/music/0185e1296692339cf1ca020fbfd67148/61350083/61350083.jpg","thumbnail2":"http://musicdata.baidu.com/data2/music/0185e1296692339cf1ca020fbfd67148/61350083/61350083.jpg"},{"aliastitle":"真的爱你","all_artist_id":"130","artist":"Beyond","artist_id":"130","del_status":"0","mv_id":"116279020","play_nums":"196275","provider":"12","publishtime":"2014-03-11 12:16:16","subtitle":"十大中文金曲 现场版","title":"真的爱你","thumbnail":"http://musicdata.baidu.com/data2/music/695c359e67145e12e493b5b5a2317515/116279031/116279031.jpeg","thumbnail2":"http://musicdata.baidu.com/data2/music/695c359e67145e12e493b5b5a2317515/116279031/116279031.jpeg"},{"aliastitle":"心会跟爱一起走","all_artist_id":"1449","artist":"郭峰","artist_id":"1449","del_status":"0","mv_id":"84754335","play_nums":"47047","provider":"12","publishtime":"2013-09-09 10:05:07","subtitle":"安徽卫视 黄金年代 现场版","title":"心会跟爱一起走","thumbnail":"http://musicdata.baidu.com/data2/pic/5b277d748ed974515d57541b9100b65e/324811823/324811823.jpeg","thumbnail2":"http://musicdata.baidu.com/data2/pic/5b277d748ed974515d57541b9100b65e/324811823/324811823.jpeg"}]
         */

        @SerializedName("total")
        private int total;
        @SerializedName("havemore")
        private String havemore;
        @SerializedName("mv_list")
        private List<MvListBean> mvList;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getHavemore() {
            return havemore;
        }

        public void setHavemore(String havemore) {
            this.havemore = havemore;
        }

        public List<MvListBean> getMvList() {
            return mvList;
        }

        public void setMvList(List<MvListBean> mvList) {
            this.mvList = mvList;
        }

        public static class MvListBean {
            /**
             * aliastitle : 无情的雨无情的你
             * all_artist_id : 163
             * artist : 齐秦
             * artist_id : 163
             * del_status : 0
             * mv_id : 63370002
             * play_nums : 640137
             * provider : 12
             * publishtime : 2013-06-25 05:30:05
             * subtitle : 字幕版
             * title : 无情的雨无情的你
             * thumbnail : http://musicdata.baidu.com/data2/music/c302fce3d801924cfd7ec2ff53972891/63370021/63370021.jpg
             * thumbnail2 : http://musicdata.baidu.com/data2/music/c302fce3d801924cfd7ec2ff53972891/63370021/63370021.jpg
             */

            @SerializedName("aliastitle")
            private String aliastitle;
            @SerializedName("all_artist_id")
            private String allArtistId;
            @SerializedName("artist")
            private String artist;
            @SerializedName("artist_id")
            private String artistId;
            @SerializedName("del_status")
            private String delStatus;
            @SerializedName("mv_id")
            private String mvId;
            @SerializedName("play_nums")
            private String playNums;
            @SerializedName("provider")
            private String provider;
            @SerializedName("publishtime")
            private String publishtime;
            @SerializedName("subtitle")
            private String subtitle;
            @SerializedName("title")
            private String title;
            @SerializedName("thumbnail")
            private String thumbnail;
            @SerializedName("thumbnail2")
            private String thumbnail2;

            public String getAliastitle() {
                return aliastitle;
            }

            public void setAliastitle(String aliastitle) {
                this.aliastitle = aliastitle;
            }

            public String getAllArtistId() {
                return allArtistId;
            }

            public void setAllArtistId(String allArtistId) {
                this.allArtistId = allArtistId;
            }

            public String getArtist() {
                return artist;
            }

            public void setArtist(String artist) {
                this.artist = artist;
            }

            public String getArtistId() {
                return artistId;
            }

            public void setArtistId(String artistId) {
                this.artistId = artistId;
            }

            public String getDelStatus() {
                return delStatus;
            }

            public void setDelStatus(String delStatus) {
                this.delStatus = delStatus;
            }

            public String getMvId() {
                return mvId;
            }

            public void setMvId(String mvId) {
                this.mvId = mvId;
            }

            public String getPlayNums() {
                return playNums;
            }

            public void setPlayNums(String playNums) {
                this.playNums = playNums;
            }

            public String getProvider() {
                return provider;
            }

            public void setProvider(String provider) {
                this.provider = provider;
            }

            public String getPublishtime() {
                return publishtime;
            }

            public void setPublishtime(String publishtime) {
                this.publishtime = publishtime;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getThumbnail2() {
                return thumbnail2;
            }

            public void setThumbnail2(String thumbnail2) {
                this.thumbnail2 = thumbnail2;
            }
        }
    }
}
