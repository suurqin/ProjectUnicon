package com.jm.projectunion.mine.entity;

import com.jm.projectunion.entity.Result;

import java.io.Serializable;

/**
 * Created by Young on 2017/11/29.
 */

public class UserInfoResult extends Result {

    private UserInfoBean data;

    public UserInfoBean getData() {
        return data;
    }

    public void setData(UserInfoBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserInfoResult{" +
                "data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class UserInfoBean implements Serializable {
        /**
         * "account": 0,
         * "age": 0,
         * "avatar": "string",
         * "collectNum": 0,
         * "packageNum": 0,
         * "publishNum": 0,
         * "realname": "string",
         * "sex": "string",
         * "totime": "2017-11-29T10:18:20.397Z",
         * "id": 0,
         * "vipType": "string :0会员，1高级会员，2企业会员
         */

        private String account;
        private String age;
        private String avatar;
        private String collectNum;
        private String packageNum;
        private String publishNum;
        private String realname;
        private String sex;
        private String totime;
        private String id;
        private String vipType;
        private String phone;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCollectNum() {
            return collectNum;
        }

        public void setCollectNum(String collectNum) {
            this.collectNum = collectNum;
        }

        public String getPackageNum() {
            return packageNum;
        }

        public void setPackageNum(String packageNum) {
            this.packageNum = packageNum;
        }

        public String getPublishNum() {
            return publishNum;
        }

        public void setPublishNum(String publishNum) {
            this.publishNum = publishNum;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getTotime() {
            return totime;
        }

        public void setTotime(String totime) {
            this.totime = totime;
        }

        public String getUserId() {
            return id;
        }

        public void setUserId(String id) {
            this.id = id;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "UserInfoBean{" +
                    "account='" + account + '\'' +
                    ", age='" + age + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", collectNum='" + collectNum + '\'' +
                    ", packageNum='" + packageNum + '\'' +
                    ", publishNum='" + publishNum + '\'' +
                    ", realname='" + realname + '\'' +
                    ", sex='" + sex + '\'' +
                    ", totime='" + totime + '\'' +
                    ", id='" + id + '\'' +
                    ", vipType='" + vipType + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }
}
