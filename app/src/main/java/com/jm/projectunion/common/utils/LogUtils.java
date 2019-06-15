package com.jm.projectunion.common.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.jm.projectunion.BuildConfig;
import com.jm.projectunion.common.manager.Config;


/**
 * Log调试工具
 *
 * @author Young
 * @date 2015-5-4 下午5:26:15
 */
public class LogUtils {
    public static final String TAG = "base";

    private static String buildMsg(String msg) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(msg);

        return buffer.toString();
    }

    public static void i(String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void v(String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.v(TAG, msg);
        }
    }

    public static void json(Object msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.v(TAG, new Gson().toJson(msg));
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg, Exception e) {
        if (BuildConfig.LOG_DEBUG) {
            Log.e(TAG, buildMsg(msg));
        }
    }

    public static void v(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void json(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void z(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void a(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.d(tag, msg);
        }
    }

}
