package com.jm.projectunion.entity;

import java.io.Serializable;

/**
 * Created by Young on 2017/11/28.
 */

public class LoginOrRegistResult extends Result {

    private RegistBean data;

    public RegistBean getData() {
        return data;
    }

    public void setData(RegistBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginOrRegistResult{" +
                "data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class RegistBean implements Serializable {
        /**
         * "expiresIn": 0,
         * "id": 0,
         * "pk": "string",
         * "token": "string",
         * "tokenType": "string",
         * "ts": 0
         * vipType	string会员类型:0会员，1高级会员，2企业类型
         */
        private String expiresIn;
        private String userId;
        private String pk;
        private String token;
        private String tokenType;
        private String ts;
        private String vipType = "0";
        private String phone;

        public String getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(String expiresIn) {
            this.expiresIn = expiresIn;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPk() {
            return pk;
        }

        public void setPk(String pk) {
            this.pk = pk;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }

        public String getVipType() {
            return vipType;
        }

        public void setVipType(String vipType) {
            this.vipType = vipType;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "RegistBean{" +
                    "expiresIn='" + expiresIn + '\'' +
                    ", userId='" + userId + '\'' +
                    ", pk='" + pk + '\'' +
                    ", token='" + token + '\'' +
                    ", tokenType='" + tokenType + '\'' +
                    ", ts='" + ts + '\'' +
                    ", vipType='" + vipType + '\'' +
                    '}';
        }
    }
}
