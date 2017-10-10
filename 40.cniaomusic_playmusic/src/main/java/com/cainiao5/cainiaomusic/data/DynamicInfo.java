package com.cainiao5.cainiaomusic.data;

import java.util.List;

/**
 * Created by hongkl on 17/6/18.
 */
public class DynamicInfo {

    /**
     * videoid : 134410
     * title : KKBOX华语单曲周榜排行榜TOP50
     * thumburl : http://r2.ykimg.com/05420408521C652D6A0A4710C9C87E30
     * userid : 526
     * username : 小钢炮94
     * useravtar : 小钢炮94
     * decs : KKBOX华语单曲周榜排行榜TOP50—林俊杰，五月天，周杰伦，薛之谦，邓紫棋！
     * total_fav : 3
     * total_up : 1989
     * total_down : 61
     * isoriginal : 0
     * sharestate : OPEN
     * state : normal
     * category : 音乐
     */

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String videoid;
        private String title;
        private String thumburl;
        private String userid;
        private String username;
        private String useravtar;
        private String decs;
        private String total_fav;
        private String total_up;
        private String total_down;
        private String isoriginal;
        private String sharestate;
        private String state;
        private String category;

        public String getVideoid() {
            return videoid;
        }

        public void setVideoid(String videoid) {
            this.videoid = videoid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumburl() {
            return thumburl;
        }

        public void setThumburl(String thumburl) {
            this.thumburl = thumburl;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUseravtar() {
            return useravtar;
        }

        public void setUseravtar(String useravtar) {
            this.useravtar = useravtar;
        }

        public String getDecs() {
            return decs;
        }

        public void setDecs(String decs) {
            this.decs = decs;
        }

        public String getTotal_fav() {
            return total_fav;
        }

        public void setTotal_fav(String total_fav) {
            this.total_fav = total_fav;
        }

        public String getTotal_up() {
            return total_up;
        }

        public void setTotal_up(String total_up) {
            this.total_up = total_up;
        }

        public String getTotal_down() {
            return total_down;
        }

        public void setTotal_down(String total_down) {
            this.total_down = total_down;
        }

        public String getIsoriginal() {
            return isoriginal;
        }

        public void setIsoriginal(String isoriginal) {
            this.isoriginal = isoriginal;
        }

        public String getSharestate() {
            return sharestate;
        }

        public void setSharestate(String sharestate) {
            this.sharestate = sharestate;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
}
