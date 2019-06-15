package com.jm.projectunion.mine.dto;

import java.io.Serializable;

/**
 * Created by YangPan on 2017/12/23.
 */

public class OrderDto implements Serializable {
    /**
     * amount:	number金额
     * body:	string
     * clientType:	string客户端类型：1 Android端，2IOS
     * ctime:	string ($date-time)创建日期，不用填写
     * orderType:	string订单类型：1充值，2提现，3高级，4 VIP
     * payType:	string支付类型：1支付宝，2微信，3银联
     * proType:	string商品类型:1高级会员，2企业会员
     * subject:	string
     * userId:	integer ($int32)用户id
     */

    private String amount;
    private String body;
    private String clientType = "1";
    private String ctime;
    private String orderType;
    private String payType;
    private String proType;
    private String userId;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "amount='" + amount + '\'' +
                ", body='" + body + '\'' +
                ", clientType='" + clientType + '\'' +
                ", ctime='" + ctime + '\'' +
                ", orderType='" + orderType + '\'' +
                ", payType='" + payType + '\'' +
                ", proType='" + proType + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
