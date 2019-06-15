package com.jm.projectunion.entity;

import java.io.Serializable;

/**
 * Created by Young on 2017/11/28.
 */

public class ResultData extends Result {
    /**
     * "code": 0,
     * "msg": "string",
     */
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
