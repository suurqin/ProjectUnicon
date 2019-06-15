package com.jm.projectunion.information.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Young on 2017/11/30.
 */

public class EnterpriseInfoDto implements Serializable {
    /**
     * mobile:	联系人手机
     * master:法人姓名
     * userId:用户d
     * registerPic:营业执照
     * masterPic1:法人正面身份证
     * masterPic2:法人反面身份证
     * name: 公司名称
     * longitude:经度
     * latitude:纬度
     * flag:发布状态：1发布，0未发布
     * area:服务区域,多个以“，”隔开，注意前后都要有“，”
     * phone:	联系人座机
     * imgs:	[...]
     * addrDetails:详细地址
     * lastId:分页时使用
     * addr:公司地址
     * coordinateType:坐标类型：0百度，1高德，2GPS
     * content:资质内容,多个以“，”隔开，注意前后都要有“，”
     * publishTime:发布时间，不用填写
     * registerMoney:	注册资金
     * orgId:	i企业Id,更新时必填
     */
    private String addr;
    private String addrDetails;
    private String area;
    private String content;
    private int flag;
    private String master;
    private String masterPic1;
    private String masterPic2;
    private String mobile;
    private String name;
    private String orgId;
    private String phone;
    private String registerMoney;
    private String registerPic;
    private String userId;
    private String longitude;
    private String latitude;
    private String coordinateType = "0";
    private List<String> imgs;
    private String publishTime;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getMasterPic1() {
        return masterPic1;
    }

    public void setMasterPic1(String masterPic1) {
        this.masterPic1 = masterPic1;
    }

    public String getMasterPic2() {
        return masterPic2;
    }

    public void setMasterPic2(String masterPic2) {
        this.masterPic2 = masterPic2;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegisterMoney() {
        return registerMoney;
    }

    public void setRegisterMoney(String registerMoney) {
        this.registerMoney = registerMoney;
    }

    public String getRegisterPic() {
        return registerPic;
    }

    public void setRegisterPic(String registerPic) {
        this.registerPic = registerPic;
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

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "EnterpriseInfoDto{" +
                "addr='" + addr + '\'' +
                ", addrDetails='" + addrDetails + '\'' +
                ", area='" + area + '\'' +
                ", content='" + content + '\'' +
                ", flag=" + flag +
                ", master='" + master + '\'' +
                ", masterPic1='" + masterPic1 + '\'' +
                ", masterPic2='" + masterPic2 + '\'' +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", orgId='" + orgId + '\'' +
                ", phone='" + phone + '\'' +
                ", registerMoney='" + registerMoney + '\'' +
                ", registerPic='" + registerPic + '\'' +
                ", userId='" + userId + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", coordinateType='" + coordinateType + '\'' +
                ", imgs=" + imgs +
                ", publishTime='" + publishTime + '\'' +
                '}';
    }
}
