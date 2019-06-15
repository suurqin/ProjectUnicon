package com.jm.projectunion.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.jm.projectunion.dao.bean.AreaBean;

import java.sql.SQLException;

/**
 * Created by Young on 2017/12/19.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "project-area.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper instance;
    /**
     * userDao ，每张表对于一个
     */
    private Dao<AreaBean, Integer> areaDao;

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, AreaBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, AreaBean.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得userDao
     *
     * @return
     * @throws SQLException
     */
    public Dao<AreaBean, Integer> getAreaDao() throws SQLException {
        if (areaDao == null) {
            areaDao = getDao(AreaBean.class);
        }
        return areaDao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        areaDao = null;
    }
}
