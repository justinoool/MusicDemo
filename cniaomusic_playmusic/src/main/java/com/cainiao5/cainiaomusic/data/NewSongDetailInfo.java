package com.cainiao5.cainiaomusic.data;

import java.util.List;

/**
 */
public class NewSongDetailInfo extends BaseBean {


    /**
     * message : 成功
     * success : true
     * data : [{"ID":15537533,"SN":"声音恋人 VOL.1 睡不着吗？我陪你吧~","SK":"fc","UID":15537533,"ST":1,"DD":0,"CT":1477574100,"GD":0,"KM5":"51056a3421a67c97c7ced6f5991da623","CK":140232,"user":{"ID":37938839,"NN":"5sing音乐人","I":"http://wsing.bssdl.kugou.com/400314ea6513af6463d8ee43656398cd.jpg"}},{"ID":15585559,"SN":"声音恋人 VOL.2 写给你的一封信","SK":"fc","UID":15585559,"ST":1,"DD":0,"CT":1479709740,"GD":0,"KM5":"947e97bd6020c3594b0c6ab9f66bf550","CK":98568,"user":{"ID":37938839,"NN":"5sing音乐人","I":"http://wsing.bssdl.kugou.com/400314ea6513af6463d8ee43656398cd.jpg"}},{"ID":15611833,"SN":"声音恋人 VOL.3 早安，世界。","SK":"fc","UID":15611833,"ST":1,"DD":0,"CT":1480903620,"GD":0,"KM5":"9086d4d3de42f14d7f4434877e70d088","CK":80520,"user":{"ID":37938839,"NN":"5sing音乐人","I":"http://wsing.bssdl.kugou.com/400314ea6513af6463d8ee43656398cd.jpg"}},{"ID":15652178,"SN":"声音恋人 VOL.4 我喜欢你。","SK":"fc","UID":15652178,"ST":1,"DD":0,"CT":1482717180,"GD":0,"KM5":"60125c611f29cf06ca0f9b36b9fb5f71","CK":86043,"user":{"ID":37938839,"NN":"5sing音乐人","I":"http://wsing.bssdl.kugou.com/400314ea6513af6463d8ee43656398cd.jpg"}},{"ID":15697005,"SN":"声音恋人 VOL.5 您有一只学霸男神，请查收！","SK":"fc","UID":15697005,"ST":1,"DD":0,"CT":1484710260,"GD":0,"KM5":"1a11cdb6e0914283bbea7377604bb11b","CK":124410,"user":{"ID":37938839,"NN":"5sing音乐人","I":"http://wsing.bssdl.kugou.com/400314ea6513af6463d8ee43656398cd.jpg"}},{"ID":15728750,"SN":"声音恋人 VOL.6 你的撒娇，有点犯规。","SK":"fc","UID":15728750,"ST":1,"DD":0,"CT":1486197780,"GD":0,"KM5":"74620adb2c2886335a924e16254da3ba","CK":59622,"user":{"ID":37938839,"NN":"5sing音乐人","I":"http://wsing.bssdl.kugou.com/400314ea6513af6463d8ee43656398cd.jpg"}},{"ID":15747083,"SN":"声音恋人 VOL.7 霸道总裁爱上我","SK":"fc","UID":15747083,"ST":1,"DD":0,"CT":1486952220,"GD":0,"KM5":"86411d0afb2236a86167f460f8ca808f","CK":109362,"user":{"ID":37938839,"NN":"5sing音乐人","I":"http://wsing.bssdl.kugou.com/400314ea6513af6463d8ee43656398cd.jpg"}},{"ID":15888726,"SN":"声音恋人 VOL.8 您的咖啡，请慢用","SK":"fc","UID":15888726,"ST":1,"DD":0,"CT":1492502294,"GD":0,"KM5":"3398419583c19841b692e4ee029ff005","CK":230835,"user":{"ID":37938839,"NN":"5sing音乐人","I":"http://wsing.bssdl.kugou.com/400314ea6513af6463d8ee43656398cd.jpg"}},{"ID":15969604,"SN":"声音恋人 VOL.9 加油，我在未来等你","SK":"fc","UID":15969604,"ST":1,"DD":0,"CT":1496196755,"GD":0,"KM5":"c91a3ee2f4e0746b11d8309e1540c049","CK":47154,"user":{"ID":37938839,"NN":"5sing音乐人","I":"http://wsing.bssdl.kugou.com/400314ea6513af6463d8ee43656398cd.jpg"}},{"ID":16033454,"SN":"声音恋人 VOL.10 跟可爱学弟来场甜蜜约会吧！","SK":"fc","UID":16033454,"ST":1,"DD":0,"CT":1499172217,"GD":0,"KM5":"5f63d6a6e453bdcc813b1c67b4eadb0c","CK":9747,"user":{"ID":37938839,"NN":"5sing音乐人","I":"http://wsing.bssdl.kugou.com/400314ea6513af6463d8ee43656398cd.jpg"}}]
     * code : 0
     */

    private String message;
    private boolean success;
    private int code;
    /**
     * ID : 15537533
     * SN : 声音恋人 VOL.1 睡不着吗？我陪你吧~
     * SK : fc
     * UID : 15537533
     * ST : 1
     * DD : 0
     * CT : 1477574100
     * GD : 0
     * KM5 : 51056a3421a67c97c7ced6f5991da623
     * CK : 140232
     * user : {"ID":37938839,"NN":"5sing音乐人","I":"http://wsing.bssdl.kugou.com/400314ea6513af6463d8ee43656398cd.jpg"}
     */

    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int ID;
        private String SN;
        private String SK;
        private int UID;
        private int ST;
        private int DD;
        private int CT;
        private int GD;
        private String KM5;
        private int CK;
        /**
         * ID : 37938839
         * NN : 5sing音乐人
         * I : http://wsing.bssdl.kugou.com/400314ea6513af6463d8ee43656398cd.jpg
         */

        private UserBean user;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getSN() {
            return SN;
        }

        public void setSN(String SN) {
            this.SN = SN;
        }

        public String getSK() {
            return SK;
        }

        public void setSK(String SK) {
            this.SK = SK;
        }

        public int getUID() {
            return UID;
        }

        public void setUID(int UID) {
            this.UID = UID;
        }

        public int getST() {
            return ST;
        }

        public void setST(int ST) {
            this.ST = ST;
        }

        public int getDD() {
            return DD;
        }

        public void setDD(int DD) {
            this.DD = DD;
        }

        public int getCT() {
            return CT;
        }

        public void setCT(int CT) {
            this.CT = CT;
        }

        public int getGD() {
            return GD;
        }

        public void setGD(int GD) {
            this.GD = GD;
        }

        public String getKM5() {
            return KM5;
        }

        public void setKM5(String KM5) {
            this.KM5 = KM5;
        }

        public int getCK() {
            return CK;
        }

        public void setCK(int CK) {
            this.CK = CK;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            private int ID;
            private String NN;
            private String I;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getNN() {
                return NN;
            }

            public void setNN(String NN) {
                this.NN = NN;
            }

            public String getI() {
                return I;
            }

            public void setI(String I) {
                this.I = I;
            }
        }
    }
}
