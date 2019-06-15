package com.jm.projectunion.common.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result implements Serializable{
    @SerializedName("header")
    private RespHeader header;

    public class RespHeader  implements Serializable {
       private String icode;
        private String iserial;
        private String istatus;
        private String imsg;

        public String getIcode() {
            return icode;
        }

        public void setIcode(String icode) {
            this.icode = icode;
        }

        public String getIserial() {
            return iserial;
        }

        public void setIserial(String iserial) {
            this.iserial = iserial;
        }

        public String getIstatus() {
            return istatus;
        }

        public void setIstatus(String istatus) {
            this.istatus = istatus;
        }

        public String getImsg() {
            return imsg;
        }

        public void setImsg(String imsg) {
            this.imsg = imsg;
        }

        @Override
        public String toString() {
            return "RespHeader{" +
                    "icode='" + icode + '\'' +
                    ", iserial='" + iserial + '\'' +
                    ", istatus='" + istatus + '\'' +
                    ", imsg='" + imsg + '\'' +
                    '}';
        }
    }

    public RespHeader getHeader() {
        return header;
    }

    public void setHeader(RespHeader header) {
        this.header = header;
    }
}
