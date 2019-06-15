package com.jm.projectunion.entity;

import java.io.Serializable;

/**
 * Created by Young on 2017/11/28.
 */

public class Result implements Serializable {
    /**
     * "code": 0,
     * "msg": "string",
     */
    public String code;
    public String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
