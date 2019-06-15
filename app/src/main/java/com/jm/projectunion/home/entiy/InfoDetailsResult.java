package com.jm.projectunion.home.entiy;

import com.jm.projectunion.entity.Result;
import com.jm.projectunion.home.dto.ReleaseInfoDto;

/**
 * Created by YangPan on 2017/12/15.
 */

public class InfoDetailsResult extends Result {

    private ReleaseInfoDto data;

    public ReleaseInfoDto getData() {
        return data;
    }

    public void setData(ReleaseInfoDto data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "InfoDetailsResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
