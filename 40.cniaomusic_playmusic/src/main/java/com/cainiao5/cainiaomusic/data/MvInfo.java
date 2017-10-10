/**
 * Copyright 2017 bejson.com
 */
package com.cainiao5.cainiaomusic.data;

import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2017-06-23 2:22:54
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class MvInfo {
    @Override
    public String toString() {
        return "MvInfo{" +
                "error_code=" + error_code +
                ", result=" + result +
                '}';
    }

    private int error_code;
    private Result result;
    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
    public int getError_code() {
        return error_code;
    }

    public void setResult(Result result) {
        this.result = result;
    }
    public Result getResult() {
        return result;
    }


    public class Video_info {

        private String video_id;
        private String mv_id;
        private String provider;
        private String sourcepath;
        private String thumbnail;
        private String thumbnail2;
        private String del_status;
        private String distribution;
        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }
        public String getVideo_id() {
            return video_id;
        }

        public void setMv_id(String mv_id) {
            this.mv_id = mv_id;
        }
        public String getMv_id() {
            return mv_id;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }
        public String getProvider() {
            return provider;
        }

        public void setSourcepath(String sourcepath) {
            this.sourcepath = sourcepath;
        }
        public String getSourcepath() {
            return sourcepath;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail2(String thumbnail2) {
            this.thumbnail2 = thumbnail2;
        }
        public String getThumbnail2() {
            return thumbnail2;
        }

        public void setDel_status(String del_status) {
            this.del_status = del_status;
        }
        public String getDel_status() {
            return del_status;
        }

        public void setDistribution(String distribution) {
            this.distribution = distribution;
        }
        public String getDistribution() {
            return distribution;
        }

    }


    public class Files {

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;


    }


    public class Artist_list {

        private String artist_id;
        private String ting_uid;
        private String artist_name;
        private String artist_480_800;
        private String artist_640_1136;
        private String avatar_small;
        private String avatar_mini;
        private String avatar_s180;
        private String avatar_s300;
        private String avatar_s500;
        private String del_status;
        public void setArtist_id(String artist_id) {
            this.artist_id = artist_id;
        }
        public String getArtist_id() {
            return artist_id;
        }

        public void setTing_uid(String ting_uid) {
            this.ting_uid = ting_uid;
        }
        public String getTing_uid() {
            return ting_uid;
        }

        public void setArtist_name(String artist_name) {
            this.artist_name = artist_name;
        }
        public String getArtist_name() {
            return artist_name;
        }

        public void setArtist_480_800(String artist_480_800) {
            this.artist_480_800 = artist_480_800;
        }
        public String getArtist_480_800() {
            return artist_480_800;
        }

        public void setArtist_640_1136(String artist_640_1136) {
            this.artist_640_1136 = artist_640_1136;
        }
        public String getArtist_640_1136() {
            return artist_640_1136;
        }

        public void setAvatar_small(String avatar_small) {
            this.avatar_small = avatar_small;
        }
        public String getAvatar_small() {
            return avatar_small;
        }

        public void setAvatar_mini(String avatar_mini) {
            this.avatar_mini = avatar_mini;
        }
        public String getAvatar_mini() {
            return avatar_mini;
        }

        public void setAvatar_s180(String avatar_s180) {
            this.avatar_s180 = avatar_s180;
        }
        public String getAvatar_s180() {
            return avatar_s180;
        }

        public void setAvatar_s300(String avatar_s300) {
            this.avatar_s300 = avatar_s300;
        }
        public String getAvatar_s300() {
            return avatar_s300;
        }

        public void setAvatar_s500(String avatar_s500) {
            this.avatar_s500 = avatar_s500;
        }
        public String getAvatar_s500() {
            return avatar_s500;
        }

        public void setDel_status(String del_status) {
            this.del_status = del_status;
        }
        public String getDel_status() {
            return del_status;
        }

    }

    public class Mv_info {

        private String mv_id;
        private String all_artist_id;
        private String title;
        private String aliastitle;
        private String subtitle;
        private String play_nums;
        private Date publishtime;
        private String del_status;
        private List<Artist_list> artist_list;
        private String artist_id;
        private String thumbnail;
        private String thumbnail2;
        private String artist;
        private String provider;
        public void setMv_id(String mv_id) {
            this.mv_id = mv_id;
        }
        public String getMv_id() {
            return mv_id;
        }

        public void setAll_artist_id(String all_artist_id) {
            this.all_artist_id = all_artist_id;
        }
        public String getAll_artist_id() {
            return all_artist_id;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setAliastitle(String aliastitle) {
            this.aliastitle = aliastitle;
        }
        public String getAliastitle() {
            return aliastitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }
        public String getSubtitle() {
            return subtitle;
        }

        public void setPlay_nums(String play_nums) {
            this.play_nums = play_nums;
        }
        public String getPlay_nums() {
            return play_nums;
        }

        public void setPublishtime(Date publishtime) {
            this.publishtime = publishtime;
        }
        public Date getPublishtime() {
            return publishtime;
        }

        public void setDel_status(String del_status) {
            this.del_status = del_status;
        }
        public String getDel_status() {
            return del_status;
        }

        public void setArtist_list(List<Artist_list> artist_list) {
            this.artist_list = artist_list;
        }
        public List<Artist_list> getArtist_list() {
            return artist_list;
        }

        public void setArtist_id(String artist_id) {
            this.artist_id = artist_id;
        }
        public String getArtist_id() {
            return artist_id;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail2(String thumbnail2) {
            this.thumbnail2 = thumbnail2;
        }
        public String getThumbnail2() {
            return thumbnail2;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }
        public String getArtist() {
            return artist;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }
        public String getProvider() {
            return provider;
        }

    }

    public class Result {

        private Video_info video_info;
        private Files files;
        private String min_definition;
        private String max_definition;
        private Mv_info mv_info;
        private String share_url;
        public void setVideo_info(Video_info video_info) {
            this.video_info = video_info;
        }
        public Video_info getVideo_info() {
            return video_info;
        }

        public void setFiles(Files files) {
            this.files = files;
        }
        public Files getFiles() {
            return files;
        }

        public void setMin_definition(String min_definition) {
            this.min_definition = min_definition;
        }
        public String getMin_definition() {
            return min_definition;
        }

        public void setMax_definition(String max_definition) {
            this.max_definition = max_definition;
        }
        public String getMax_definition() {
            return max_definition;
        }

        public void setMv_info(Mv_info mv_info) {
            this.mv_info = mv_info;
        }
        public Mv_info getMv_info() {
            return mv_info;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }
        public String getShare_url() {
            return share_url;
        }

    }

}