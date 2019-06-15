package com.jm.projectunion.dao.entity;

import com.jm.projectunion.dao.bean.AreaBean;
import com.jm.projectunion.entity.Result;

import java.util.List;

/**
 * Created by bobo on 2017/12/19.
 */

public class AreaResult extends Result {

    private List<AreaBean> data;

    public List<AreaBean> getData() {
        return data;
    }

    public void setData(List<AreaBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AreaResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
