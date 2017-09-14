package com.example.jack.myapplication.model;

import java.util.List;

/**
 * Created by ${justin} on 2017/9/1315: 46
 * WeChat:Justin-Tz
 */

public class HeFengWeather {

    private List<HeWeather5Bean> HeWeather5;

    public List<HeWeather5Bean> getHeWeather5() {
        return HeWeather5;
    }

    public void setHeWeather5(List<HeWeather5Bean> HeWeather5) {
        this.HeWeather5 = HeWeather5;
    }

    public static class HeWeather5Bean {
        /**
         * basic : {"city":"北京","cnty":"中国","id":"CN101010100","lat":"39.90498734","lon":"116.40528870","prov":"北京"}
         * status : ok
         */

        private BasicBean basic;
        private String status;

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public static class BasicBean {
            /**
             * city : 北京
             * cnty : 中国
             * id : CN101010100
             * lat : 39.90498734
             * lon : 116.40528870
             * prov : 北京
             */

            private String city;
            private String cnty;
            private String id;
            private String lat;
            private String lon;
            private String prov;

            @Override
            public String toString() {
                return "BasicBean{" +
                        "city='" + city + '\'' +
                        ", cnty='" + cnty + '\'' +
                        ", id='" + id + '\'' +
                        ", lat='" + lat + '\'' +
                        ", lon='" + lon + '\'' +
                        ", prov='" + prov + '\'' +
                        '}';
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getProv() {
                return prov;
            }

            public void setProv(String prov) {
                this.prov = prov;
            }
        }
    }
}
