package com.jm.projectunion.home.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Young on 2017/12/5.
 */

public class ReleaseInfoDto implements Serializable {
    /**
     * image (string):封面图地址 ,
     * title (string):标题 ,
     * phone (string):联系电话 ,
     * categoryIds (integer):分类Id ,
     * userId (integer):发布人Id ,
     * longitude (string):经度 ,
     * latitude (string):纬度 ,
     * addr (string):地址 ,
     * addrDetails (string):详细地址 ,
     * articleType (integer):发布类型 1：法律，2：房屋租赁，3：招聘，4：设备租赁，5：保险服务，6：二手建材，7：二手设备，8：零活发布，9：建筑证件，10：图文设计，11：便民商家 ,
     * coordinateType (string):坐标类型：0百度，1高德，2GPS ,
     * status (string, optional): 发布状态：1已发布，0未发布
     * articleId (integer, optional): 发布的信息ID ,
     * author (string, optional):称呼 ,
     * type (integer, optional):房屋租赁类型：1出租，2出售 ,
     * areaId (integer, optional):区域（建筑证件） ,
     * price (number, optional):价钱（房屋租赁|招聘|设备租赁）
     * desp (string, optional):详细描述 ,
     * imgs (Array[string], optional):辅助图片地址数组 ,
     * ctime (string, optional):创建时间，不用填写 ,
     * publishTime (string, optional):发布时间，不用填写
     */
    private String articleId;
    private String status = "1";
    private String image;
    private String title;
    private String author;
    private String phone;
    private String type;
    private String areaIds;
    private String price;
    private String categoryIds;
    private String userId;
    private String longitude;
    private String latitude;
    private String coordinateType = "0";
    private String addr;
    private String addrDetails;
    private String desp;
    private List<String> imgs;
    private String articleType;
    private String ctime;
    private String publishTime;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddrDetails() {
        return addrDetails;
    }

    public void setAddrDetails(String addrDetails) {
        this.addrDetails = addrDetails;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ReleaseInfoDto{" +
                "articleId='" + articleId + '\'' +
                ", status='" + status + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", phone='" + phone + '\'' +
                ", type='" + type + '\'' +
                ", areaId='" + areaIds + '\'' +
                ", price='" + price + '\'' +
                ", categoryId='" + categoryIds + '\'' +
                ", userId='" + userId + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", coordinateType='" + coordinateType + '\'' +
                ", addr='" + addr + '\'' +
                ", addrDetails='" + addrDetails + '\'' +
                ", desp='" + desp + '\'' +
                ", imgs=" + imgs +
                ", articleType='" + articleType + '\'' +
                ", ctime='" + ctime + '\'' +
                ", publishTime='" + publishTime + '\'' +
                '}';
    }
}
