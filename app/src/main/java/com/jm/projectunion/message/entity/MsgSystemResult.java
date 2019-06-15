package com.jm.projectunion.message.entity;

import com.jm.projectunion.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Young on 2017/11/8.
 */

public class MsgSystemResult extends Result {

    private List<MsgSystemBean> data;

    public List<MsgSystemBean> getData() {
        return data;
    }

    public void setData(List<MsgSystemBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MsgSystemResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class MsgSystemBean implements Serializable {
        /**
         * content (string):内容 ,
         * ctime (string): 推送时间 ,
         * msgId (integer):消息Id ,
         * name (string):标题 ,
         * type (string):类型：1节日祝福，2系统消息，3行业动态，4常用数据
         */

        private String content;
        private String ctime;
        private String msgId;
        private String name;
        private String type;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "MsgSystemBean{" +
                    "content='" + content + '\'' +
                    ", ctime='" + ctime + '\'' +
                    ", msgId='" + msgId + '\'' +
                    ", name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
