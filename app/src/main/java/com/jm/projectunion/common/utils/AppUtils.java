package com.jm.projectunion.common.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by Young on 2016/12/15.
 */

public class AppUtils {

    private static PrefUtils prefUtils;
    private static Context mContext;

    private AppUtils() {
    }

    public static void init(Context context) {
        mContext = context;
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static boolean isFirstStart(Context context) {
        prefUtils = PrefUtils.getInstance(context);
        String currentVerCode = getVersionCode(context);
//        String previousVerCode = prefUtils.getAppVerCode();
//        if (currentVerCode.equals(previousVerCode)) {
//            return false;
//        } else {
//            return true;
//        }
        return true;
    }

    //版本名
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    //版本号
    public static String getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 判断某个activity是否运行
     *
     * @param mContext
     * @param activityClassName
     * @return
     */
    public static boolean isActivityRunning(Context mContext, String activityClassName) {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> info = activityManager.getRunningTasks(1);
        if (info != null && info.size() > 0) {
            ComponentName component = info.get(0).topActivity;
            if (activityClassName.equals(component.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
