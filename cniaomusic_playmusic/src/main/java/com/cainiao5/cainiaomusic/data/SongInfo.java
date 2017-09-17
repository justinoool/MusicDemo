package com.cainiao5.cainiaomusic.data;

/**
 */
public class SongInfo {

    /**
     * special_type : 0
     * pic_huge :
     * resource_type : 0
     * pic_premium :
     * havehigh : 0
     * author : 阿肆
     * toneid : 0
     * has_mv : 0
     * song_id : 74195778
     * piao_id : 0
     * artist_id : 73329005
     * lrclink :
     * relate_status : 0
     * learn : 0
     * pic_big : http://musicdata.baidu.com/data2/pic/271712c433abf6638768c627935c1b32/533523817/533523817.jpg@s_0,w_150
     * play_type : 0
     * album_id : 0
     * album_title :
     * bitrate_fee : {"0":"0|0","1":"0|0"}
     * song_source : web
     * all_artist_id : 73329005
     * all_artist_ting_uid : 110934445
     * all_rate : 64,128
     * charge : 0
     * copy_type : 0
     * is_first_publish : 0
     * korean_bb_song : 0
     * pic_radio :
     * has_mv_mobile : 0
     * title : 我在人民广场吃炸鸡
     * pic_small : http://musicdata.baidu.com/data2/pic/271712c433abf6638768c627935c1b32/533523817/533523817.jpg@s_0,w_90
     * album_no : 0
     * resource_type_ext : 0
     * ting_uid : 110934445
     */

    private SonginfoBean songinfo;
    /**
     * songinfo : {"special_type":0,"pic_huge":"","resource_type":"0","pic_premium":"","havehigh":0,"author":"阿肆","toneid":"0","has_mv":0,"song_id":"74195778","piao_id":"0","artist_id":"73329005","lrclink":"","relate_status":"0","learn":0,"pic_big":"http://musicdata.baidu.com/data2/pic/271712c433abf6638768c627935c1b32/533523817/533523817.jpg@s_0,w_150","play_type":0,"album_id":"0","album_title":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","song_source":"web","all_artist_id":"73329005","all_artist_ting_uid":"110934445","all_rate":"64,128","charge":0,"copy_type":"0","is_first_publish":0,"korean_bb_song":"0","pic_radio":"","has_mv_mobile":0,"title":"我在人民广场吃炸鸡","pic_small":"http://musicdata.baidu.com/data2/pic/271712c433abf6638768c627935c1b32/533523817/533523817.jpg@s_0,w_90","album_no":"0","resource_type_ext":"0","ting_uid":"110934445"}
     * error_code : 22000
     * bitrate : {"show_link":"http://zhangmenshiting.baidu.com/data2/music/7a186d10f02ecda7e4238c8bc6ccad27/533523591/533523591.mp3?xcode=e5840397d83d196326bdb4a8ac0cec0f","free":1,"song_file_id":533523591,"file_size":1714064,"file_extension":"mp3","file_duration":214,"file_bitrate":64,"file_link":"http://yinyueshiting.baidu.com/data2/music/7a186d10f02ecda7e4238c8bc6ccad27/533523591/533523591.mp3?xcode=e5840397d83d196326bdb4a8ac0cec0f","hash":"d4b14f228f468f2c66e1e27e238384213fef4895"}
     */

    private int error_code;
    /**
     * show_link : http://zhangmenshiting.baidu.com/data2/music/7a186d10f02ecda7e4238c8bc6ccad27/533523591/533523591.mp3?xcode=e5840397d83d196326bdb4a8ac0cec0f
     * free : 1
     * song_file_id : 533523591
     * file_size : 1714064
     * file_extension : mp3
     * file_duration : 214
     * file_bitrate : 64
     * file_link : http://yinyueshiting.baidu.com/data2/music/7a186d10f02ecda7e4238c8bc6ccad27/533523591/533523591.mp3?xcode=e5840397d83d196326bdb4a8ac0cec0f
     * hash : d4b14f228f468f2c66e1e27e238384213fef4895
     */

    private BitrateBean bitrate;

    public SonginfoBean getSonginfo() {
        return songinfo;
    }

    public void setSonginfo(SonginfoBean songinfo) {
        this.songinfo = songinfo;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public BitrateBean getBitrate() {
        return bitrate;
    }

    public void setBitrate(BitrateBean bitrate) {
        this.bitrate = bitrate;
    }

    public static class SonginfoBean {
        private int special_type;
        private String pic_huge;
        private String resource_type;
        private String pic_premium;
        private int havehigh;
        private String author;
        private String toneid;
        private int has_mv;
        private String song_id;
        private String piao_id;
        private String artist_id;
        private String lrclink;
        private String relate_status;
        private int learn;
        private String pic_big;
        private int play_type;
        private String album_id;
        private String album_title;
        private String bitrate_fee;
        private String song_source;
        private String all_artist_id;
        private String all_artist_ting_uid;
        private String all_rate;
        private int charge;
        private String copy_type;
        private int is_first_publish;
        private String korean_bb_song;
        private String pic_radio;
        private int has_mv_mobile;
        private String title;
        private String pic_small;
        private String album_no;
        private String resource_type_ext;
        private String ting_uid;

        public int getSpecial_type() {
            return special_type;
        }

        public void setSpecial_type(int special_type) {
            this.special_type = special_type;
        }

        public String getPic_huge() {
            return pic_huge;
        }

        public void setPic_huge(String pic_huge) {
            this.pic_huge = pic_huge;
        }

        public String getResource_type() {
            return resource_type;
        }

        public void setResource_type(String resource_type) {
            this.resource_type = resource_type;
        }

        public String getPic_premium() {
            return pic_premium;
        }

        public void setPic_premium(String pic_premium) {
            this.pic_premium = pic_premium;
        }

        public int getHavehigh() {
            return havehigh;
        }

        public void setHavehigh(int havehigh) {
            this.havehigh = havehigh;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getToneid() {
            return toneid;
        }

        public void setToneid(String toneid) {
            this.toneid = toneid;
        }

        public int getHas_mv() {
            return has_mv;
        }

        public void setHas_mv(int has_mv) {
            this.has_mv = has_mv;
        }

        public String getSong_id() {
            return song_id;
        }

        public void setSong_id(String song_id) {
            this.song_id = song_id;
        }

        public String getPiao_id() {
            return piao_id;
        }

        public void setPiao_id(String piao_id) {
            this.piao_id = piao_id;
        }

        public String getArtist_id() {
            return artist_id;
        }

        public void setArtist_id(String artist_id) {
            this.artist_id = artist_id;
        }

        public String getLrclink() {
            return lrclink;
        }

        public void setLrclink(String lrclink) {
            this.lrclink = lrclink;
        }

        public String getRelate_status() {
            return relate_status;
        }

        public void setRelate_status(String relate_status) {
            this.relate_status = relate_status;
        }

        public int getLearn() {
            return learn;
        }

        public void setLearn(int learn) {
            this.learn = learn;
        }

        public String getPic_big() {
            return pic_big;
        }

        public void setPic_big(String pic_big) {
            this.pic_big = pic_big;
        }

        public int getPlay_type() {
            return play_type;
        }

        public void setPlay_type(int play_type) {
            this.play_type = play_type;
        }

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public String getAlbum_title() {
            return album_title;
        }

        public void setAlbum_title(String album_title) {
            this.album_title = album_title;
        }

        public String getBitrate_fee() {
            return bitrate_fee;
        }

        public void setBitrate_fee(String bitrate_fee) {
            this.bitrate_fee = bitrate_fee;
        }

        public String getSong_source() {
            return song_source;
        }

        public void setSong_source(String song_source) {
            this.song_source = song_source;
        }

        public String getAll_artist_id() {
            return all_artist_id;
        }

        public void setAll_artist_id(String all_artist_id) {
            this.all_artist_id = all_artist_id;
        }

        public String getAll_artist_ting_uid() {
            return all_artist_ting_uid;
        }

        public void setAll_artist_ting_uid(String all_artist_ting_uid) {
            this.all_artist_ting_uid = all_artist_ting_uid;
        }

        public String getAll_rate() {
            return all_rate;
        }

        public void setAll_rate(String all_rate) {
            this.all_rate = all_rate;
        }

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }

        public String getCopy_type() {
            return copy_type;
        }

        public void setCopy_type(String copy_type) {
            this.copy_type = copy_type;
        }

        public int getIs_first_publish() {
            return is_first_publish;
        }

        public void setIs_first_publish(int is_first_publish) {
            this.is_first_publish = is_first_publish;
        }

        public String getKorean_bb_song() {
            return korean_bb_song;
        }

        public void setKorean_bb_song(String korean_bb_song) {
            this.korean_bb_song = korean_bb_song;
        }

        public String getPic_radio() {
            return pic_radio;
        }

        public void setPic_radio(String pic_radio) {
            this.pic_radio = pic_radio;
        }

        public int getHas_mv_mobile() {
            return has_mv_mobile;
        }

        public void setHas_mv_mobile(int has_mv_mobile) {
            this.has_mv_mobile = has_mv_mobile;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic_small() {
            return pic_small;
        }

        public void setPic_small(String pic_small) {
            this.pic_small = pic_small;
        }

        public String getAlbum_no() {
            return album_no;
        }

        public void setAlbum_no(String album_no) {
            this.album_no = album_no;
        }

        public String getResource_type_ext() {
            return resource_type_ext;
        }

        public void setResource_type_ext(String resource_type_ext) {
            this.resource_type_ext = resource_type_ext;
        }

        public String getTing_uid() {
            return ting_uid;
        }

        public void setTing_uid(String ting_uid) {
            this.ting_uid = ting_uid;
        }

        @Override
        public String toString() {
            return "SonginfoBean{" +
                    "special_type=" + special_type +
                    ", pic_huge='" + pic_huge + '\'' +
                    ", resource_type='" + resource_type + '\'' +
                    ", pic_premium='" + pic_premium + '\'' +
                    ", havehigh=" + havehigh +
                    ", author='" + author + '\'' +
                    ", toneid='" + toneid + '\'' +
                    ", has_mv=" + has_mv +
                    ", song_id='" + song_id + '\'' +
                    ", piao_id='" + piao_id + '\'' +
                    ", artist_id='" + artist_id + '\'' +
                    ", lrclink='" + lrclink + '\'' +
                    ", relate_status='" + relate_status + '\'' +
                    ", learn=" + learn +
                    ", pic_big='" + pic_big + '\'' +
                    ", play_type=" + play_type +
                    ", album_id='" + album_id + '\'' +
                    ", album_title='" + album_title + '\'' +
                    ", bitrate_fee='" + bitrate_fee + '\'' +
                    ", song_source='" + song_source + '\'' +
                    ", all_artist_id='" + all_artist_id + '\'' +
                    ", all_artist_ting_uid='" + all_artist_ting_uid + '\'' +
                    ", all_rate='" + all_rate + '\'' +
                    ", charge=" + charge +
                    ", copy_type='" + copy_type + '\'' +
                    ", is_first_publish=" + is_first_publish +
                    ", korean_bb_song='" + korean_bb_song + '\'' +
                    ", pic_radio='" + pic_radio + '\'' +
                    ", has_mv_mobile=" + has_mv_mobile +
                    ", title='" + title + '\'' +
                    ", pic_small='" + pic_small + '\'' +
                    ", album_no='" + album_no + '\'' +
                    ", resource_type_ext='" + resource_type_ext + '\'' +
                    ", ting_uid='" + ting_uid + '\'' +
                    '}';
        }
    }

    public static class BitrateBean {
        private String show_link;
        private int free;
        private int song_file_id;
        private int file_size;
        private String file_extension;
        private int file_duration;
        private int file_bitrate;
        private String file_link;
        private String hash;

        public String getShow_link() {
            return show_link;
        }

        public void setShow_link(String show_link) {
            this.show_link = show_link;
        }

        public int getFree() {
            return free;
        }

        public void setFree(int free) {
            this.free = free;
        }

        public int getSong_file_id() {
            return song_file_id;
        }

        public void setSong_file_id(int song_file_id) {
            this.song_file_id = song_file_id;
        }

        public int getFile_size() {
            return file_size;
        }

        public void setFile_size(int file_size) {
            this.file_size = file_size;
        }

        public String getFile_extension() {
            return file_extension;
        }

        public void setFile_extension(String file_extension) {
            this.file_extension = file_extension;
        }

        public int getFile_duration() {
            return file_duration;
        }

        public void setFile_duration(int file_duration) {
            this.file_duration = file_duration;
        }

        public int getFile_bitrate() {
            return file_bitrate;
        }

        public void setFile_bitrate(int file_bitrate) {
            this.file_bitrate = file_bitrate;
        }

        public String getFile_link() {
            return file_link;
        }

        public void setFile_link(String file_link) {
            this.file_link = file_link;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        @Override
        public String toString() {
            return "BitrateBean{" +
                    "show_link='" + show_link + '\'' +
                    ", free=" + free +
                    ", song_file_id=" + song_file_id +
                    ", file_size=" + file_size +
                    ", file_extension='" + file_extension + '\'' +
                    ", file_duration=" + file_duration +
                    ", file_bitrate=" + file_bitrate +
                    ", file_link='" + file_link + '\'' +
                    ", hash='" + hash + '\'' +
                    '}';
        }
    }
}
