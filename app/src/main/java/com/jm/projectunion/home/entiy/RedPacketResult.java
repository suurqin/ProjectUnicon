package com.jm.projectunion.home.entiy;

import com.jm.projectunion.entity.Result;

import java.io.Serializable;

/**
 * Created by YangPan on 2017/11/5.
 */

public class RedPacketResult extends Result {

    private RedPacketBean data;

    public RedPacketBean getData() {
        return data;
    }

    public void setData(RedPacketBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RedPacketResult{" +
                "data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class RedPacketBean implements Serializable {
        /**
         * redPacketId:	integer ($int64)
         * image:	string *封面图地址
         * title:	string *标题
         * phone:	string *联系电话
         * money:	number *($double)红包金额
         * userId:	integer *($int32)发布人Id
         * type:	string *类型:1同城，2全国
         * age:	string *年龄:0不限，其他年龄段0-100，中间以“-”分开
         * sex:	string *性别:0不限，1男，2女
         * price:	number *($double)单个红包金额
         * cityId 城市ID
         * num:	integer *($int32)数量
         * img1:	string图片地址1
         * img1Desc:	string图片描述1
         * img2:	string图片地址2
         * img2Desc:	string图片描述2
         * img3:	string图片地址3
         * img4:	string图片地址4
         * img3Desc:	string图片描述3
         * img4Desc:	string图片描述4
         */
        private String redPacketId;
        private String image;
        private String title;
        private String phone;
        private String money;
        private String userId;
        private String type;
        private String age;
        private String sex;
        private String price;
        private String addr;
        private String cityId;
        private String num;
        private String img1;
        private String img1Desc;
        private String img2;
        private String img2Desc;
        private String img3;
        private String img3Desc;
        private String img4;
        private String img4Desc;

        public String getRedPacketId() {
            return redPacketId;
        }

        public void setRedPacketId(String redPacketId) {
            this.redPacketId = redPacketId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getImg1() {
            return img1;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }

        public String getImg1Desc() {
            return img1Desc;
        }

        public void setImg1Desc(String img1Desc) {
            this.img1Desc = img1Desc;
        }

        public String getImg2() {
            return img2;
        }

        public void setImg2(String img2) {
            this.img2 = img2;
        }

        public String getImg2Desc() {
            return img2Desc;
        }

        public void setImg2Desc(String img2Desc) {
            this.img2Desc = img2Desc;
        }

        public String getImg3() {
            return img3;
        }

        public void setImg3(String img3) {
            this.img3 = img3;
        }

        public String getImg3Desc() {
            return img3Desc;
        }

        public void setImg3Desc(String img3Desc) {
            this.img3Desc = img3Desc;
        }

        public String getImg4() {
            return img4;
        }

        public void setImg4(String img4) {
            this.img4 = img4;
        }

        public String getImg4Desc() {
            return img4Desc;
        }

        public void setImg4Desc(String img4Desc) {
            this.img4Desc = img4Desc;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        @Override
        public String toString() {
            return "RedPacketBean{" +
                    "redPacketId='" + redPacketId + '\'' +
                    ", image='" + image + '\'' +
                    ", title='" + title + '\'' +
                    ", phone='" + phone + '\'' +
                    ", money='" + money + '\'' +
                    ", userId='" + userId + '\'' +
                    ", type='" + type + '\'' +
                    ", age='" + age + '\'' +
                    ", sex='" + sex + '\'' +
                    ", price='" + price + '\'' +
                    ", addr='" + addr + '\'' +
                    ", cityId='" + cityId + '\'' +
                    ", num='" + num + '\'' +
                    ", img1='" + img1 + '\'' +
                    ", img1Desc='" + img1Desc + '\'' +
                    ", img2='" + img2 + '\'' +
                    ", img2Desc='" + img2Desc + '\'' +
                    ", img3='" + img3 + '\'' +
                    ", img3Desc='" + img3Desc + '\'' +
                    ", img4='" + img4 + '\'' +
                    ", img4Desc='" + img4Desc + '\'' +
                    '}';
        }
    }
}
