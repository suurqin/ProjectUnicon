package com.jm.projectunion.dao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Young on 2017/12/19.
 */
@DatabaseTable(tableName = "tb_area")
public class AreaBean implements Serializable {
    /**
     * "areaId": 0,
     * "level": 0,
     * "name": "string",
     * "pid": 0
     */
    @DatabaseField(columnName = "areaId", id = true)
    private int areaId;
    @DatabaseField(columnName = "level")
    private String level;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "pid")
    private String pid;
    private boolean ifmy=false;

    public AreaBean() {
    }

    public AreaBean(int areaId, String level, String name, String pid) {
        this.areaId = areaId;
        this.level = level;
        this.name = name;
        this.pid = pid;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
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
        return "AreaBean{" +
                ", areaId=" + areaId +
                ", level='" + level + '\'' +
                ", name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                '}';
    }

    public boolean isIfmy() {
        return ifmy;
    }

    public void setIfmy(boolean ifmy) {
        this.ifmy = ifmy;
    }
}
