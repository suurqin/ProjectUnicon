package com.jm.projectunion.mine.entity;

import com.jm.projectunion.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Young on 2017/12/15.
 */

public class CollectResult extends Result {

    private List<CollectBean> data;

    public List<CollectBean> getData() {
        return data;
    }

    public void setData(List<CollectBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CollectResult{" +
                "data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class CollectBean implements Serializable {
        /**
         * "collectId": 0,
         * "userId": 0,
         * "name": "string",
         * "type":  1：发布置顶广告 ，2：发布法律服务，3：发布房屋租赁，4：发布招聘信息，5：发布商业服务，6：发布二手建材，
         *          7：发布保险服务，8：发布二手设备，9：发布零活信息，10：发布建筑证件，11：农民工之家，12：发布便民商家,
         *          21:找工人，22：找人才，23：找资料，24：找机械，31：企业专区，88：红包，89项目
         * "content": "string",
         * "ctime": "2017-12-15T09:19:51.700Z"
         */

        private String collectId;
        private String userId;
        private String name;
        private String type;
        private String content;
        private String ctime;

        public String getCollectId() {
            return collectId;
        }

        public void setCollectId(String collectId) {
            this.collectId = collectId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        @Override
        public String toString() {
            return "CollectBean{" +
                    "collectId='" + collectId + '\'' +
                    ", userId='" + userId + '\'' +
                    ", name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    ", content='" + content + '\'' +
                    ", ctime='" + ctime + '\'' +
                    '}';
        }
    }
}
