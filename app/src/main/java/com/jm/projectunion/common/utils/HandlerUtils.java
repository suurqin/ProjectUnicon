package com.jm.projectunion.common.utils;

import android.os.Handler;

import com.jm.projectunion.MyApplication;


/**
 * 描述：提供全局Handler的post功能
 *
 * @作者：JQ.Hu
 * @创建时间：2016/5/27 16:59
 */
public class HandlerUtils {

    private final static Handler mHandler;

    static {
        mHandler = new Handler(MyApplication.getInstance().getMainLooper());
    }

    public static Handler getGlobalHandler() {
        return mHandler;
    }

    public static boolean isUIMainThread(long threadId) {
        return mHandler.getLooper().getThread().getId() == threadId;
    }

    public static void runOnUiThread(Runnable runnable) {
        mHandler.post(runnable);
    }

    public static void runOnUiThreadDelay(Runnable runnable, long delayMillis) {
        mHandler.postDelayed(runnable, delayMillis);
    }

    public static void removeRunable(Runnable runnable) {
        mHandler.removeCallbacks(runnable);
    }
}
