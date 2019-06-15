package com.jm.projectunion.information.entity;

import com.jm.projectunion.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Young on 2017/11/29.
 */

public class UserInfoDetailResult extends Result {

    private UserInfoDetailBean data;


    public UserInfoDetailBean getData() {
        return data;
    }

    public void setData(UserInfoDetailBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserInfoDetailResult{" +
                "data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class UserInfoDetailBean implements Serializable {
        /**
         * "address": "string",
         * "addressDetails": "string",
         * "age": 0,
         * "avatar": "string",
         * "desc": "string",
         * "email": "string",
         * "imgs": [
         * "string"
         * ],
         * "industryType": "string",
         * "mobile": "string",
         * "phone": "string",
         * "realname": "string",
         * "service": "string",
         * "sex": 0,
         * "id": 0,
         * "username": "string"
         * "longitude": "string",
         * "latitude": "string",
         * "coordinateType": "string",
         * "publishTime": "2017-12-06T01:21:55.581Z"
         * cityId
         */
        private String address;
        private String addressDetails;
        private String age;
        private String avatar;
        private String desc;
        private String email;
        private String industryType;
        private String industryName;
        private String mobile;
        private String phone;
        private String publish;
        private String realname;
        private String service;
        private String serviceName;
        private String sex;
        private String userId;
        private String username;
        private String longitude;
        private String latitude;
        private String coordinateType = "0";
        private String publishTime;
        private List<String> imgs;
        private String workYear;
        private String cityId;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddressDetails() {
            return addressDetails;
        }

        public void setAddressDetails(String addressDetails) {
            this.addressDetails = addressDetails;
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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getIndustryType() {
            return industryType;
        }

        public void setIndustryType(String industryType) {
            this.industryType = industryType;
        }

        public String getIndustryName() {
            return industryName;
        }

        public void setIndustryName(String industryName) {
            this.industryName = industryName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPublish() {
            return publish;
        }

        public void setPublish(String publish) {
            this.publish = publish;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getCoordinateType() {
            return coordinateType;
        }

        public void setCoordinateType(String coordinateType) {
            this.coordinateType = coordinateType;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getWorkYear() {
            return workYear;
        }

        public void setWorkYear(String workYear) {
            this.workYear = workYear;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        @Override
        public String toString() {
            return "UserInfoDetailBean{" +
                    "address='" + address + '\'' +
                    ", addressDetails='" + addressDetails + '\'' +
                    ", age='" + age + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", desc='" + desc + '\'' +
                    ", email='" + email + '\'' +
                    ", industryType='" + industryType + '\'' +
                    ", industryName='" + industryName + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", phone='" + phone + '\'' +
                    ", publish='" + publish + '\'' +
                    ", realname='" + realname + '\'' +
                    ", service='" + service + '\'' +
                    ", serviceName='" + serviceName + '\'' +
                    ", sex='" + sex + '\'' +
                    ", userId='" + userId + '\'' +
                    ", username='" + username + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", coordinateType='" + coordinateType + '\'' +
                    ", publishTime='" + publishTime + '\'' +
                    ", imgs=" + imgs +
                    ", workYear='" + workYear + '\'' +
                    ", cityId='" + cityId + '\'' +
                    '}';
        }
    }
}
