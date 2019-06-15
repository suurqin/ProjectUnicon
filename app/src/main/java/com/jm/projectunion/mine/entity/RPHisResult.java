package com.jm.projectunion.mine.entity;

import com.jm.projectunion.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Young on 2017/12/15.
 */

public class RPHisResult extends Result {

    private List<RPHisBean> data;

    public List<RPHisBean> getData() {
        return data;
    }

    public void setData(List<RPHisBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RPHisResult{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class RPHisBean implements Serializable {
        /**
         * redPacketId:	integer ($int64)
         * status:	string
         * type:	string
         * redLogId:	integer *($int64)红包日志ID
         * userId:	integer *($int32)用户ID
         * ctime:	string *($date-time)日期
         * money:	number *($double)金额
         * title:	string *     标题
         */
        private String redPacketId;
        private String status;
        private String type;
        private String redLogId;
        private String userId;
        private String ctime;
        private String money;
        private String title;

        public String getRedPacketId() {
            return redPacketId;
        }

        public void setRedPacketId(String redPacketId) {
            this.redPacketId = redPacketId;
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

        public String getRedLogId() {
            return redLogId;
        }

        public void setRedLogId(String redLogId) {
            this.redLogId = redLogId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "RPHisBean{" +
                    "redPacketId='" + redPacketId + '\'' +
                    ", status='" + status + '\'' +
                    ", type='" + type + '\'' +
                    ", redLogId='" + redLogId + '\'' +
                    ", userId='" + userId + '\'' +
                    ", ctime='" + ctime + '\'' +
                    ", money='" + money + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
