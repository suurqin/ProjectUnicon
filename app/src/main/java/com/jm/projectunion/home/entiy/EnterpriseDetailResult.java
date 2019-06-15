package com.jm.projectunion.home.entiy;

import com.jm.projectunion.entity.Result;
import com.jm.projectunion.information.dto.EnterpriseInfoDto;

import java.util.List;

/**
 * Created by Young on 2017/10/30.
 */

public class EnterpriseDetailResult extends Result {

    private EnterpriseInfoDto data;

    public EnterpriseInfoDto getData() {
        return data;
    }

    public void setData(EnterpriseInfoDto data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EnterpriseDetailResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
