package com.jm.projectunion.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;


import com.jm.projectunion.MyApplication;

import java.util.UUID;

public class DeviceUtil {
    /**
     * 获得设备IMEI Device ID，需要权限READ_PHONE_STATE
     *
     * @param context
     * @return
     */
    public static String getIMEIDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public final static int getScreenWidth() {
        DisplayMetrics displayMetrics = MyApplication.getInstance().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获得PseudoUniqueId，此ID在任何Android手机中都有效，
     * 但如果两个手机应用了同样的硬件以及Rom镜像（ROM版本、制造商、CPU型号、以及其他硬件信息），那计算的ID就不是唯一的，出现此类情况一般可以忽略。
     *
     * @return
     */
    public static String getPseudoUniqueId() {
//Build.CPU_ABI过时用Build.SUPPORTED_ABIS代替，不过需要API21
        return "35" +
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10;
    }

    /**
     * 获得Android ID
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获得系统版本
     *
     * @return
     */
    public static String getAndroidVersion() {
        return android.os.Build.MODEL + android.os.Build.VERSION.SDK + android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获得Sim Serial Number
     *
     * @param context
     * @return
     */
    public static String getSimSerialNumber(Context context) {
//装有SIM卡的设备的获取方式，但对于CDMA设备返回空值
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber = telephonyManager.getSimSerialNumber();
        if (simSerialNumber == null) {
//Android 2.3设备的获取方式
            String serialNumber = Build.SERIAL;
            if (serialNumber != null) {
                simSerialNumber = serialNumber;
            }
        }
        return simSerialNumber;
    }

    /**
     * 获得Wlan中的MAC地址，需要权限ACCESS_WIFI_STATE
     *
     * @param context
     * @return
     */
    public static String getWlanMACAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo().getMacAddress();
    }

    /**
     * 获得用UUID加密的唯一设备识别号ID
     *
     * @param context
     * @return
     */
    public static String toUUIDUniqueId(Context context) {
        UUID deviceUuid = new UUID(
                getAndroidId(context).hashCode(),
                ((long) getIMEIDeviceId(context).hashCode() << 32) | getSimSerialNumber(context).hashCode()
        );
        return deviceUuid.toString();
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    //版本名
    public static String getVersionName(Context context) {
        String mName = "v " + getPackageInfo(context).versionName.split("-")[0];
        return mName;
    }

    //版本
    public static String getVersion(Context context) {
        String mName = getPackageInfo(context).versionName.split("-")[0];
        return mName;
    }

    //版本号
    public static String getVersionCode(Context context) {
        return getPackageInfo(context).versionCode + "";
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
}