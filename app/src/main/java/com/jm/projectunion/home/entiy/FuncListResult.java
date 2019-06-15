package com.jm.projectunion.home.entiy;

import com.jm.projectunion.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bobo on 2017/12/18.
 */

public class FuncListResult extends Result {

    private List<FuncListBean> data;

    public List<FuncListBean> getData() {
        return data;
    }

    public void setData(List<FuncListBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FuncListResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class FuncListBean implements Serializable {
        /**
         * "fid": "1",
         * "title": "开发程序"
         */
        private String fid;
        private String title;

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "FuncListBean{" +
                    "fid='" + fid + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
