package com.jm.projectunion.entity;

import java.io.Serializable;

/**
 * Created by Young on 2017/12/27.
 */

public class VersionResult extends Result {

    private VersionBean data;

    public VersionBean getData() {
        return data;
    }

    public void setData(VersionBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VersionResult{" +
                "data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class VersionBean implements Serializable {
        /**
         * version  版本号
         * url  下载地址
         * isForce 1强制，0非强制
         */
        private String version;
        private String url;
        private String isForce;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIsForce() {
            return isForce;
        }

        public void setIsForce(String isForce) {
            this.isForce = isForce;
        }

        @Override
        public String toString() {
            return "VersionBean{" +
                    "version='" + version + '\'' +
                    ", url='" + url + '\'' +
                    ", isForce='" + isForce + '\'' +
                    '}';
        }
    }
}
