package com.jm.projectunion.common.entity;

import android.text.TextUtils;

public class Paging {

    private String pageNum;
    private String offset;
    private String pageNo;

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }
    public String getSNum() {

        if(!TextUtils.isEmpty(pageNum)){
            return pageNum;
        }
        if(!TextUtils.isEmpty(offset)){
            return offset;
        }
        if(!TextUtils.isEmpty(pageNo)){
            return pageNo;
        }
        return "1";
    }

}
