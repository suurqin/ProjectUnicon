package com.jm.projectunion.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.jm.projectunion.dao.bean.AreaBean;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Young on 2017/12/19.
 */

public class AreaDaoUtil {

    private Context context;

    public AreaDaoUtil(Context context) {
        this.context = context;
    }

    public void addItem(AreaBean bean) {
        try {
            Dao dao = DatabaseHelper.getHelper(context).getAreaDao();
            dao.createIfNotExists(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AreaBean> queryByPid(String pid) {
        try {
            Dao dao = DatabaseHelper.getHelper(context).getAreaDao();
            QueryBuilder<AreaBean, Integer> queryBuilder = dao.queryBuilder();
            queryBuilder.where().eq("pid", pid);
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<AreaBean> queryByLevel(String level) {
        try {
            Dao dao = DatabaseHelper.getHelper(context).getAreaDao();
            QueryBuilder<AreaBean, Integer> queryBuilder = dao.queryBuilder();
            queryBuilder.where().eq("level", level);
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AreaBean queryById(String id) {
        try {
            Dao dao = DatabaseHelper.getHelper(context).getAreaDao();
            QueryBuilder<AreaBean, Integer> queryBuilder = dao.queryBuilder();
            queryBuilder.where().eq("areaId", id);
            return queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AreaBean queryByName(String name) {
        try {
            Dao dao = DatabaseHelper.getHelper(context).getAreaDao();
            QueryBuilder<AreaBean, Integer> queryBuilder = dao.queryBuilder();
            queryBuilder.where().eq("name", name);
            return queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<AreaBean> queryAll() {
        try {
            Dao dao = DatabaseHelper.getHelper(context).getAreaDao();
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
