package com.jm.projectunion.home.dto;

import java.io.Serializable;

/**
 * Created by YangPan on 2017/12/15.
 */

public class ListDto implements Serializable {
    /**
     * types:类型：1区域，2分类，3发布时间，4距离最近，5同城，6全国(大分类选择多个已#分割)
     * values:类型值（0表示全部）(选择多个已#分割)
     * "name": "string",
     * "articleType":   1：发布置顶广告 ，2：发布法律服务，3：发布房屋租赁，4：发布招聘信息，
     * 5：发布商业服务，6：发布二手建材，7：发布保险服务，8：发布二手设备，9：发布零活信息，
     * 10：发布建筑证件，11：农民工之家，12：发布便民商家,
     * 21:找工人，22：找人才，23：找资料，24：找机械，31：企业专区，88：红包，89项目
     * * "lastId": 0,
     * "coordinateType": "0",
     * "num": 20,
     * "order": 0：时间倒序 ，1:时间自然排序
     * "longitude": 0,
     * "latitude": 0
     */
    private String userId;
    private String types = "1";
    private String values = "0";
    private String name;
    private String articleType;
    private String lastId;
    private String coordinateType = "0";
    private String num;
    private String order;
    private String longitude;
    private String latitude;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getCoordinateType() {
        return coordinateType;
    }

    public void setCoordinateType(String coordinateType) {
        this.coordinateType = coordinateType;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
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

    @Override
    public String toString() {
        return "ListDto{" +
                "userId='" + userId + '\'' +
                ", types='" + types + '\'' +
                ", values='" + values + '\'' +
                ", name='" + name + '\'' +
                ", articleType='" + articleType + '\'' +
                ", lastId='" + lastId + '\'' +
                ", coordinateType='" + coordinateType + '\'' +
                ", num='" + num + '\'' +
                ", order='" + order + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
