package com.jm.projectunion.home.entiy;

import com.jm.projectunion.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Young on 2017/12/14.
 */

public class ProjectDetailResult extends Result {

    private ProjectBean data;

    public ProjectBean getData() {
        return data;
    }

    public void setData(ProjectBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ProjectDetailResult{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class ProjectBean implements Serializable {
        /**
         * projectId:工程，不用填写
         * projectName:	 项目名称
         * linkman:	 联系人
         * linkphone: 联系电话
         * categoryId: 分类Id,多个以，分开
         * userId: 发布人Id
         * projectType: 类型：0 在建、1新建、2扩建
         * price: 项目造价
         * addr:  地址
         * type: 业主类型：1政府/2ppp项目/3地产商/4企业主
         * area: 建筑面积
         * sdate: 开工日期 yyyy-MM-dd
         * edate: 竣工日期 yyyy-MM-dd
         * ptype:  发包方式：1寻求合作，2大包，3人工费
         * desp: 详细描述
         * rmk1: 发布状态：1已发布，0未发布
         * ctime:	string ($date-time) * 创建日期，不用填写
         * areaIds  区域ID
         */

        private String projectId;
        private String projectName;
        private String linkman;
        private String linkphone;
        private String categoryId;
        private String userId;
        private String projectType;
        private String price;
        private String addr;
        private String type;
        private String area;
        private String sdate;
        private String edate;
        private String ptype;
        private String desp;
        private String rmk1 = "1";
        private String ctime;
        private String areaIds;
        private String longitude;
        private String latitude;

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getLinkphone() {
            return linkphone;
        }

        public void setLinkphone(String linkphone) {
            this.linkphone = linkphone;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getProjectType() {
            return projectType;
        }

        public void setProjectType(String projectType) {
            this.projectType = projectType;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getSdate() {
            return sdate;
        }

        public void setSdate(String sdate) {
            this.sdate = sdate;
        }

        public String getEdate() {
            return edate;
        }

        public void setEdate(String edate) {
            this.edate = edate;
        }

        public String getPtype() {
            return ptype;
        }

        public void setPtype(String ptype) {
            this.ptype = ptype;
        }

        public String getDesp() {
            return desp;
        }

        public void setDesp(String desp) {
            this.desp = desp;
        }

        public String getRmk1() {
            return rmk1;
        }

        public void setRmk1(String rmk1) {
            this.rmk1 = rmk1;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getAreaIds() {
            return areaIds;
        }

        public void setAreaIds(String areaIds) {
            this.areaIds = areaIds;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        @Override
        public String toString() {
            return "ProjectBean{" +
                    "projectId='" + projectId + '\'' +
                    ", projectName='" + projectName + '\'' +
                    ", linkman='" + linkman + '\'' +
                    ", linkphone='" + linkphone + '\'' +
                    ", categoryId='" + categoryId + '\'' +
                    ", userId='" + userId + '\'' +
                    ", projectType='" + projectType + '\'' +
                    ", price='" + price + '\'' +
                    ", addr='" + addr + '\'' +
                    ", type='" + type + '\'' +
                    ", area='" + area + '\'' +
                    ", sdate='" + sdate + '\'' +
                    ", edate='" + edate + '\'' +
                    ", ptype='" + ptype + '\'' +
                    ", desp='" + desp + '\'' +
                    ", rmk1='" + rmk1 + '\'' +
                    ", ctime='" + ctime + '\'' +
                    ", areaIds='" + areaIds + '\'' +
                    '}';
        }
    }
}
