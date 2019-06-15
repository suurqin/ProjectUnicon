package com.jm.projectunion.information.entity;

import com.jm.projectunion.entity.Result;
import com.jm.projectunion.information.dto.EnterpriseInfoDto;

/**
 * Created by Young on 2017/12/13.
 */

public class EnterpriseInfoDetailResult extends Result {

    private EnterpriseInfoDto data;

    public EnterpriseInfoDto getData() {
        return data;
    }

    public void setData(EnterpriseInfoDto data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EnterpriseInfoDetailResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
