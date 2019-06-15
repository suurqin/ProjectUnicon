package com.jm.projectunion.mine.entity;

import com.jm.projectunion.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Young on 2017/12/13.
 */

public class BanksResult extends Result {

    private List<BankBean> data;

    public List<BankBean> getData() {
        return data;
    }

    public void setData(List<BankBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BanksResult{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class BankBean implements Serializable {
        /**
         * "cardId": "string",
         * "ctime": "2017-12-13T10:35:55.529Z",
         * "defalt": "string",
         * "stat": "string",
         * "type": "string",
         * "userCardId": 0,
         * "id": 0
         */
        private String cardId;
        private String ctime;
        private String defalt = "1";
        private String stat;
        private String type;
        private String userCardId;
        private String userId;
        private String userName;

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getDefalt() {
            return defalt;
        }

        public void setDefalt(String defalt) {
            this.defalt = defalt;
        }

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserCardId() {
            return userCardId;
        }

        public void setUserCardId(String userCardId) {
            this.userCardId = userCardId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        @Override
        public String toString() {
            return "BankBean{" +
                    "cardId='" + cardId + '\'' +
                    ", ctime='" + ctime + '\'' +
                    ", defalt='" + defalt + '\'' +
                    ", stat='" + stat + '\'' +
                    ", type='" + type + '\'' +
                    ", userCardId='" + userCardId + '\'' +
                    ", id='" + userId + '\'' +
                    '}';
        }
    }
}
