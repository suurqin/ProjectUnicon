package com.jm.projectunion.common.cache;


import com.jm.projectunion.MyApplication;
import com.jm.projectunion.common.manager.DataCleanManager;
import com.jm.projectunion.common.utils.EncryptUtil;
import com.jm.projectunion.common.utils.FileUtils;

import java.io.File;

/**
 * 描述：网络请求数据缓存
 *
 * @作者：hugo
 * @创建时间：2016/7/26 15:44
 */
public class HttpCache {

    public final static String CACHE_DIR = "httpcache";

    private static ACache mCacheClient;
    private static ACache mUserCacheClient;

    private static Short synObj = 66;

//    public String cachekey_4_mainmenu_items;

    public static ACache getCacheClient() {
        if (mCacheClient == null) {
            synchronized (synObj) {
                if (mCacheClient == null) {
                    mCacheClient = ACache.get(MyApplication.getInstance(), CACHE_DIR);
                }
            }
        }
        return mCacheClient;
    }

    public static ACache getUserCacheClient(String id) {
        if (mUserCacheClient == null) {
            synchronized (synObj) {
                if (mUserCacheClient == null) {
                    String cacheDir = DataCleanManager.getAppDataDir(MyApplication.getInstance()) + "/userData/" + id;
                    FileUtils.makeDirs(cacheDir);
                    File userCacheDir = new File(cacheDir);
                    mUserCacheClient = ACache.get(userCacheDir);
                }
            }
        }
        return mUserCacheClient;
    }

    public static void clearCache() {
        if (mCacheClient != null) {
            mCacheClient.clear();
        }
    }

    public static String generateReqId(String... reqParmas) {
        String md5Str = "";
        for (int i = 0; i < reqParmas.length; i++) {
            md5Str += reqParmas[i];
        }
        md5Str = EncryptUtil.getMD5(md5Str);
        return md5Str;
    }
}
