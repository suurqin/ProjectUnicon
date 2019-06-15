package com.jm.projectunion.common.entity;

import java.io.Serializable;

public class Header implements Serializable {

    /**
     * token string 令牌
     * clientId string 设备编号
     * clientTye string 终端类型：1 IOS,2 Android,3 H5,4 其他
     * clientVersion string 终端系统版本
     * version string  应用版本
     * secret string  加密方式
     * ts string  时间戳
     * sign string 签名
     */
    private String token;
    private String clientId;
    private String clientTye;
    private String clientVersion;
    private String version;
    private String secret;
    private String ts;
    private String sign;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientTye() {
        return clientTye;
    }

    public void setClientTye(String clientTye) {
        this.clientTye = clientTye;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
