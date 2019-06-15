package com.jm.projectunion.mine.entity;

import com.jm.projectunion.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Young on 2017/12/15.
 */

public class VipTypeResult extends Result {

    private List<VipTypeBean> data;

    public List<VipTypeBean> getData() {
        return data;
    }

    public void setData(List<VipTypeBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VipTypeResult{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class VipTypeBean implements Serializable {
        /**
         * "ctime": "2017-12-15T09:00:24.279Z",
         * "description": "string",
         * "money": 0,
         * "rmk1": "string",
         * "rmk2": "string",
         * "status": "string",
         * "type": "string",
         * "vipTypeId": 0
         */
        private String ctime;
        private String description;
        private String money;
        private String rmk1;
        private String rmk2;
        private String status;
        private String type;
        private String vipTypeId;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getRmk1() {
            return rmk1;
        }

        public void setRmk1(String rmk1) {
            this.rmk1 = rmk1;
        }

        public String getRmk2() {
            return rmk2;
        }

        public void setRmk2(String rmk2) {
            this.rmk2 = rmk2;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVipTypeId() {
            return vipTypeId;
        }

        public void setVipTypeId(String vipTypeId) {
            this.vipTypeId = vipTypeId;
        }

        @Override
        public String toString() {
            return "VipTypeBean{" +
                    "ctime='" + ctime + '\'' +
                    ", description='" + description + '\'' +
                    ", money='" + money + '\'' +
                    ", rmk1='" + rmk1 + '\'' +
                    ", rmk2='" + rmk2 + '\'' +
                    ", status='" + status + '\'' +
                    ", type='" + type + '\'' +
                    ", vipTypeId='" + vipTypeId + '\'' +
                    '}';
        }
    }
}
