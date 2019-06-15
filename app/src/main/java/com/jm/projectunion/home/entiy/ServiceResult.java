package com.jm.projectunion.home.entiy;

import com.jm.projectunion.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Young on 2017/12/12.
 */

public class ServiceResult extends Result {

    private List<ServiceBean> data;

    public List<ServiceBean> getData() {
        return data;
    }

    public void setData(List<ServiceBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ServiceResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class ServiceBean implements Serializable {
        /**
         * "categoryId": 0,
         * "level": "string",
         * "name": "string",
         * "pid": 0
         */
        private String categoryId;
        private String level;
        private String name;
        private String pid;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        @Override
        public String toString() {
            return "ServiceBean{" +
                    "categoryId='" + categoryId + '\'' +
                    ", level='" + level + '\'' +
                    ", name='" + name + '\'' +
                    ", pid='" + pid + '\'' +
                    '}';
        }
    }
}
