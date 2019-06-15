package com.jm.projectunion.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jm.projectunion.entity.LoginOrRegistResult;
import com.jm.projectunion.entity.VersionResult;


/**
 * SharedPreferred 配置文件工具类
 *
 * @author Young
 * @date 2015-5-4 下午5:53:58
 */
public class PrefUtils {
    /**
     * 清单文件名称
     */
    public static final String SP_NAME = "SP_APP";

    //位置信息
    private static final String KEY_LOCATION_PROVINCE = "location_province";//省
    private static final String KEY_LOCATION_CITY = "location_city";//城市
    private static final String KEY_LOCATION_ADDRESS = "location_address";//地址
    private static final String KEY_LOCATION_LAT = "latitude";//纬度
    private static final String KEY_LOCATION_LONG = "longitude";//经度

    //用户信息
    private static final String USERINFO = "userinfo";
    //环信---用户头像
    private static final String USER_PIC = "userpic";
    //环信--用户昵称
    private static final String USER_NICKNAME = "nickname";
    //版本检查
    private static final String VERSION = "version";
    //是否第一次启动
    private static final String FIRST = "first";

    private SharedPreferences.Editor editor;
    private SharedPreferences sp;
    private static PrefUtils sInstance;
    private Context mContext;

    private PrefUtils(Context mContext) {
        this.mContext = mContext;
        this.sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        this.editor = this.sp.edit();
    }

    public static PrefUtils getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PrefUtils(context);
        }
        return sInstance;
    }

    public void setKeyLocationCity(String city) {
        editor.putString(KEY_LOCATION_PROVINCE, city).commit();
    }

    public String getKeyLocationCity() {
        String city = sp.getString(KEY_LOCATION_PROVINCE, "");
        return city;
    }

    public void setKeyLocationProvince(String province) {
        editor.putString(KEY_LOCATION_CITY, province).commit();
    }

    public String getKeyLocationProvince() {
        String province = sp.getString(KEY_LOCATION_CITY, "");
        return province;
    }

    public void setKeyLocationAddress(String address) {
        editor.putString(KEY_LOCATION_ADDRESS, address).commit();
    }

    public String getKeyLocationAddress() {
        return sp.getString(KEY_LOCATION_ADDRESS, "");
    }

    /**
     * 设置位置纬度
     *
     * @param lat
     */
    public void setKeyLocationLat(double lat) {
        String str_lat = String.valueOf(lat);
        editor.putString(KEY_LOCATION_LAT, str_lat).commit();
    }

    /**
     * 获取位置纬度
     */
    public double getKeyLocationLat() {
        String str_lat = sp.getString(KEY_LOCATION_LAT, "");
        if (TextUtils.isEmpty(str_lat)) {
            return 0;
        }
        return Double.valueOf(str_lat);
    }

    /**
     * 设置位置经度
     *
     * @param lon
     */
    public void setKeyLocationLong(double lon) {
        String str_lon = String.valueOf(lon);
        editor.putString(KEY_LOCATION_LONG, str_lon).commit();
    }

    /**
     * 获取位置经度
     */
    public double getKeyLocationLong() {
        String str_lon = sp.getString(KEY_LOCATION_LONG, "");
        if (TextUtils.isEmpty(str_lon)) {
            return 0;
        }
        return Double.valueOf(str_lon);
    }

    /**
     * 保存用户信息
     *
     * @param userinfo
     */
    public void setUserinfo(LoginOrRegistResult.RegistBean userinfo) {
        Gson gson = new Gson();
        String str_userinfo = gson.toJson(userinfo);
        editor.putString(USERINFO, str_userinfo).commit();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public LoginOrRegistResult.RegistBean getUserInfo() {
        Gson gson = new Gson();
        String json_userinfo = sp.getString(USERINFO, "");
        if (TextUtils.isEmpty(json_userinfo)) {
            return null;
        } else {
            return gson.fromJson(json_userinfo, LoginOrRegistResult.RegistBean.class);
        }
    }

    /**
     * 环信---获取用户头像
     *
     * @return
     */
    public String getUserPic() {
        return sp.getString(USER_PIC, "");
    }

    /**
     * 环信---保存用户头像
     *
     * @param userPic
     */
    public void setUserPic(String userPic) {
        editor.putString(USER_PIC, userPic).commit();
    }

    /**
     * 环信---获取用户昵称
     *
     * @return
     */
    public String getUserNickname() {
        return sp.getString(USER_NICKNAME, "");
    }

    public void setUserNickname(String nickname) {
        editor.putString(USER_NICKNAME, nickname).commit();
    }

    /**
     * 设置版本信息
     *
     * @param bean
     */
    public void setVersion(VersionResult.VersionBean bean) {
        Gson gson = new Gson();
        String str_userinfo = gson.toJson(bean);
        editor.putString(VERSION, str_userinfo).commit();
    }

    /**
     * 获取版本信息
     *
     * @return
     */
    public VersionResult.VersionBean getVersion() {
        Gson gson = new Gson();
        String json_userinfo = sp.getString(VERSION, "");
        if (TextUtils.isEmpty(json_userinfo)) {
            return null;
        } else {
            return gson.fromJson(json_userinfo, VersionResult.VersionBean.class);
        }
    }

    public void setFirst(boolean isFirst) {
        editor.putBoolean(FIRST, isFirst).commit();
    }

    public boolean isFirst() {
        return sp.getBoolean(FIRST, true);
    }

    /**
     * 清除登录信息
     */
    public void clear() {
        this.editor.putString(USERINFO, "");
        this.editor.commit();
    }
}
