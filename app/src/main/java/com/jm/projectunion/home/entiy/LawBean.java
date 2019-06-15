package com.jm.projectunion.home.entiy;

import java.io.Serializable;

/**
 * Created by Young on 2017/10/27.
 */

public class LawBean implements Serializable {
    private String imgUrl;
    private String title;
    private String date;
    private String distance;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
}
