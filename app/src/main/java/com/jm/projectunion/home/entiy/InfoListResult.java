package com.jm.projectunion.home.entiy;

import com.jm.projectunion.entity.Result;
import com.jm.projectunion.home.dto.ReleaseInfoDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YangPan on 2017/12/15.
 */

public class InfoListResult extends Result {

    private List<InfoListBean> data;

    public List<InfoListBean> getData() {
        return data;
    }

    public void setData(List<InfoListBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "InfoListResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class InfoListBean implements Serializable {
        /**
         * entityId (string, optional): 信息id（发布的信息，用户，企业，红包，项目等）
         * image (string, optional): 封面图地址
         * title (string, optional): 标题
         * date (string, optional): 日期
         * distance (string, optional): 距离
         * articleType (integer, optional): 发布类型 1：发布置顶广告 ，2：发布法律服务，3：发布房屋租赁，4：发布招聘信息，5：发布商业服务，6：发布二手建材，7：发布保险服务，8：发布二手设备，9：发布零活信息，10：发布建筑证件，11：农民工之家，12：发布便民商家,21:找工人，22：找人才，23：找资料，24：找机械，31：企业专区，88：红包，89项目
         * lastId (integer, optional): 最后一个lastId，分页时使用
         * redMoney (number, optional): 红包金额
         * redNum (integer, optional): 红包总数
         * redRecNum (integer, optional): 红包已领数
         * receiveStatus  领用状态：1已领，0未领
         */

        private String entityId;
        private String image;
        private String title;
        private String date;
        private String distance;
        private String articleType;
        private String lastId;
        private String redMoney;
        private String redNum;
        private String redRecNum;
        private String receiveStatus;

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getArticleType() {
            return articleType;
        }

        public void setArticleType(String articleType) {
            this.articleType = articleType;
        }

        public String getLastId() {
            return lastId;
        }

        public void setLastId(String lastId) {
            this.lastId = lastId;
        }

        public String getRedMoney() {
            return redMoney;
        }

        public void setRedMoney(String redMoney) {
            this.redMoney = redMoney;
        }

        public String getRedNum() {
            return redNum;
        }

        public void setRedNum(String redNum) {
            this.redNum = redNum;
        }

        public String getRedRecNum() {
            return redRecNum;
        }

        public void setRedRecNum(String redRecNum) {
            this.redRecNum = redRecNum;
        }

        public String getReceiveStatus() {
            return receiveStatus;
        }

        public void setReceiveStatus(String receiveStatus) {
            this.receiveStatus = receiveStatus;
        }

        @Override
        public String toString() {
            return "InfoListBean{" +
                    "entityId='" + entityId + '\'' +
                    ", image='" + image + '\'' +
                    ", title='" + title + '\'' +
                    ", date='" + date + '\'' +
                    ", distance='" + distance + '\'' +
                    ", articleType='" + articleType + '\'' +
                    ", lastId='" + lastId + '\'' +
                    ", redMoney='" + redMoney + '\'' +
                    ", redNum='" + redNum + '\'' +
                    ", redRecNum='" + redRecNum + '\'' +
                    ", receiveStatus='" + receiveStatus + '\'' +
                    '}';
        }
    }
}
