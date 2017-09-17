package com.example.jack.musicdemo.data;

import java.util.List;

public class DynamicInfo {

    private HeaderBean header;
    private BodyBean body;

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class HeaderBean {
        /**
         * result : 1
         */

        private String result;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    public static class BodyBean {

        private PagerBean pager;
        private String sid;
        private List<DetailBean> detail;

        public PagerBean getPager() {
            return pager;
        }

        public void setPager(PagerBean pager) {
            this.pager = pager;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public static class PagerBean {
            /**
             * pageNext : 1
             * pagesize : 10
             * totalRows : 3570
             * currentPage : 1
             */

            private int pageNext;
            private String pagesize;
            private String totalRows;
            private String currentPage;

            public int getPageNext() {
                return pageNext;
            }

            public void setPageNext(int pageNext) {
                this.pageNext = pageNext;
            }

            public String getPagesize() {
                return pagesize;
            }

            public void setPagesize(String pagesize) {
                this.pagesize = pagesize;
            }

            public String getTotalRows() {
                return totalRows;
            }

            public void setTotalRows(String totalRows) {
                this.totalRows = totalRows;
            }

            public String getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(String currentPage) {
                this.currentPage = currentPage;
            }
        }

        public static class DetailBean {

            /**
             * sharetitle : %E3%80%90%E7%9C%8B%E4%BA%86%E5%90%97%E8%A7%86%E9%A2%91%E3%80%91%E4%BC%98%E8%B4%A8%E7%9F%AD%E8%A7%86%E9%A2%91%E8%81%9A%E5%90%88%E5%B9%B3%E5%8F%B0
             * photo : http%3A%2F%2Fimg.klm123.com%2F1504061201_d15ce78a4f0dfb91a28da1d1b6e29550.jpeg
             * videoId : 68520
             * opt1 : 496
             * title : %E6%A1%8C%E5%AD%90%E5%BD%93%E8%88%9E%E5%8F%B0%EF%BC%8C%E6%89%8B%E6%8C%87%E8%88%9E%E8%80%85%E4%B8%BA%E4%BD%A0%E5%B8%A6%E6%9D%A5%E7%82%AB%E9%85%B7%E8%88%9E%E8%B9%88
             * userid : 110592
             * url : http%3A%2F%2Fimg.klm123.com%2Fphoto%2F150236470182286424.jpg
             * opt5 : 0
             * nickname : %E8%81%86%E5%90%AC%E5%85%A8%E4%B8%96%E7%95%8C
             * isopt5 : 0
             * id : 68520
             * shareurl : http%3A%2F%2Fwww.klm123.com%2Fshare%2F68520
             * isopt1 : 0
             * mp4Url : http%3A%2F%2Fvideoi.klm123.com%2Fklm_1502363562000-57f5f727f3c1c88d1e30e0049423209d.mp4
             */

            private String sharetitle;
            private String photo;
            private String videoId;
            private String opt1;
            private String title;
            private String userid;
            private String url;
            private String opt5;
            private String nickname;
            private String isopt5;
            private String id;
            private String shareurl;
            private String isopt1;
            private String mp4Url;

            public String getSharetitle() {
                return sharetitle;
            }

            public void setSharetitle(String sharetitle) {
                this.sharetitle = sharetitle;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getVideoId() {
                return videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }

            public String getOpt1() {
                return opt1;
            }

            public void setOpt1(String opt1) {
                this.opt1 = opt1;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getOpt5() {
                return opt5;
            }

            public void setOpt5(String opt5) {
                this.opt5 = opt5;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getIsopt5() {
                return isopt5;
            }

            public void setIsopt5(String isopt5) {
                this.isopt5 = isopt5;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getShareurl() {
                return shareurl;
            }

            public void setShareurl(String shareurl) {
                this.shareurl = shareurl;
            }

            public String getIsopt1() {
                return isopt1;
            }

            public void setIsopt1(String isopt1) {
                this.isopt1 = isopt1;
            }

            public String getMp4Url() {
                return mp4Url;
            }

            public void setMp4Url(String mp4Url) {
                this.mp4Url = mp4Url;
            }
        }
    }
}
