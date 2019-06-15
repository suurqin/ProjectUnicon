package com.jm.projectunion.home.entiy;

import com.jm.projectunion.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Young on 2017/12/20.
 */

public class BannerResult extends Result {

    private List<Banner> data;

    public List<Banner> getData() {
        return data;
    }

    public void setData(List<Banner> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BannerResult{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class Banner implements Serializable {
        /**
         * "articleId:	integer ($int32) 发布消息ID
         * "picId": 0,
         * "url": "string"
         * articleType:	string 类型：0公共，1好友置顶图片
         */
        private String articleType;
        private String picId;
        private String url;
        private String articleId;

        public String getArticleType() {
            return articleType;
        }

        public void setArticleType(String articleType) {
            this.articleType = articleType;
        }

        public String getPicId() {
            return picId;
        }

        public void setPicId(String picId) {
            this.picId = picId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }

        @Override
        public String toString() {
            return "Banner{" +
                    "articleType='" + articleType + '\'' +
                    ", picId='" + picId + '\'' +
                    ", url='" + url + '\'' +
                    ", articleId='" + articleId + '\'' +
                    '}';
        }
    }
}
