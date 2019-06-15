package com.jm.projectunion.common.manager;

import android.os.Environment;

import java.util.HashMap;

/**
 * 全局配置文件以及常量
 */
public class Config {

    //基础URL
//    public static final String BASE_URL = "http://39.106.138.102:8088/";

    public static final String BASE_URL = "http://211.159.155.242:81/";
//    public static final String BASE_URL = "http://192.168.1.51:81/";

    //接口URL
    public static final String BASE_HOST_URL = BASE_URL + "api/";

    public static final String IMG_HOST = "http://image.gongchenglm.com/";
    /**
     * SD卡根目录
     */
    public static final String DIR_SD_ROOT = Environment.getExternalStorageDirectory()
            .getAbsolutePath();

    /**
     * 应用根目录
     */
    public static final String DIR_APP_ROOT = DIR_SD_ROOT + "/ProjectUnion";


    /**
     * 分页数量
     */
    public static final int PAGE_SIZE = 10;
    //通知刷新类
    public static String NOTICE_CLASS = "";
    public static final String OK = "1";
    public static final String HTTP_PARSER_ERROR = "10001";
    public static final String HTTP_IO_ERROR = "10002";
    public static final String HTTP_NET_ERROR = "10003";
    public static HashMap<String, String> HTTP_ERROR_LIST = initErrorCodes();

    public static HashMap<String, String> initErrorCodes() {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("10", "成功");
        errors.put(HTTP_IO_ERROR, "服务器繁忙，确保网络通畅后重试");
        errors.put(HTTP_PARSER_ERROR, "数据解析失败，工程师努力解决中");
        errors.put(HTTP_NET_ERROR, "连接似乎有问题，请检查网络");
        return errors;
    }

}
