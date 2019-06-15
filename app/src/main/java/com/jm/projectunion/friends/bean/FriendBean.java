package com.jm.projectunion.friends.bean;

import java.io.Serializable;

/**
 * Created by YangPan on 2017/12/24.
 */

public class FriendBean implements Serializable {
    /**
     * avatar (string, optional):头像 ,
     * friendNum (integer, optional):好友数 ,
     * phone (string, optional):手机号 ,
     * realname (string, optional):姓名 ,
     * recNum1 (integer, optional):一度用户数 ,
     * recNum2 (integer, optional):一度和二度用户数 ,
     * userId (integer):用户ID
     * level (string, optional):0:好友，1:一度用户，2：表示二度用户 ,
     * friendList (Array[好友实体类], optional):好友列表 ,
     * recList (Array[好友实体类], optional):推荐用户（一度和二度用户）列表 ,
     * stat string 好友状态：1是，0否
     */

    private String avatar;
    private String friendNum;
    private String phone;
    private String realname;
    private String recNum1;
    private String recNum2;
    private String userId;
    private String level;
    private String stat;

    //wfx 修改
    private String vipType;

    public String getVipType() {
        return vipType;
    }

    public void setVipType(String vipType) {
        this.vipType = vipType;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFriendNum() {
        return friendNum;
    }

    public void setFriendNum(String friendNum) {
        this.friendNum = friendNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRecNum1() {
        return recNum1;
    }

    public void setRecNum1(String recNum1) {
        this.recNum1 = recNum1;
    }

    public String getRecNum2() {
        return recNum2;
    }

    public void setRecNum2(String recNum2) {
        this.recNum2 = recNum2;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @Override
    public String toString() {
        return "FriendBean{" +
                "avatar='" + avatar + '\'' +
                ", friendNum='" + friendNum + '\'' +
                ", phone='" + phone + '\'' +
                ", realname='" + realname + '\'' +
                ", recNum1='" + recNum1 + '\'' +
                ", recNum2='" + recNum2 + '\'' +
                ", userId='" + userId + '\'' +
                ", level='" + level + '\'' +
                ", stat='" + stat + '\'' +
                ", vipType='" + vipType + '\'' +
                '}';
    }
}
