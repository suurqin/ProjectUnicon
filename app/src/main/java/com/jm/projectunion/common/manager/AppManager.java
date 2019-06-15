package com.jm.projectunion.common.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.jm.projectunion.MainActivity;
import com.jm.projectunion.common.utils.LogUtils;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 描述：应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class AppManager {

    public final static String TAG = AppManager.class.getSimpleName();

    private static AppManager instance;

    private static CopyOnWriteArrayList<Activity> activityStack;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new CopyOnWriteArrayList<Activity>();
        }
        activityStack.add(activity);
        LogUtils.e(TAG, activity.getClass().getSimpleName() + "正在打开，当前活动Activity数：" + activityStack.size());
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack == null) {
            return null;
        }
        Activity activity = activityStack.get(activityStack.size() - 1);
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishCurrentActiviy() {
        if (activityStack == null) {
            return;
        }
        Activity activity = activityStack.get(activityStack.size() - 1);
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activityStack == null) {
            return;
        }

        if (activity != null) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
            activityStack.remove(activity);
//            LogUtils.e(TAG, activity.getClass().getSimpleName() + "正在关闭，当前活动Activity数：" + activityStack.size());
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack == null) {
            return;
        }

        int size = activityStack.size();
        for (int i = size - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                LogUtils.e(TAG, cls.getSimpleName() + "正在关闭，当前活动Activity数：" + activityStack.size());
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack == null) {
            return;
        }

        int size = activityStack.size();
        for (int i = size - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        activityStack.clear();
        LogUtils.e(TAG, "关闭所有Activity，当前活动Activity个数：" + activityStack.size());
    }

    /**
     * 根据ActivityName获取堆中Activity实例
     *
     * @param activityName activity的全路径名
     * @return
     */
    public Activity getActivity(String activityName) {
        if (activityStack != null) {
            int size = activityStack.size();
            for (int i = size - 1; i >= 0; i--) {
                Activity activity = activityStack.get(i);
                if (activity != null && TextUtils.equals(activity.getClass().getName(), activityName)) {
                    return activity;
                }
            }
        }
        return null;
    }

    public Activity getTopActivity() {
        if (activityStack != null) {
            int size = activityStack.size();
            if (size > 0) {
                return activityStack.get(size - 1);
            }
        }
        return null;
    }

    /**
     * 退出应用程序
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(10);
        } catch (Exception e) {
        }
    }

    public void AppExit(Context context, Boolean isBackground) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
        } catch (Exception e) {

        } finally {
            // 如果没有后台结束Activity
            if (!isBackground) {
                System.exit(0);
            }
        }
    }

    public boolean hasActivity() {
        return activityStack == null ? false : activityStack.size() > 0;
    }

    public int getActivityCount() {
        return activityStack == null ? 0 : activityStack.size();
    }


    /**
     * 结束所有Activity，除指定的Activity外
     *
     * @param dontFinishAct 指定的不finish掉的Activity.
     *                      【注意：这个方法不能用于通用CommActiviy的finish，请使用finishAllExcept(Activity)】
     */
    public void finishAllExcept(Class dontFinishAct) {
        if (activityStack == null) {
            return;
        }

        int size = activityStack.size();
        for (int i = size - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (activity.getClass() != dontFinishAct) {
                activity.finish();
                activityStack.remove(activity);
            }
        }
    }

    public void finishAllExceptT(Class dontFinishAct) {
        if (activityStack == null) {
            return;
        }

        int size = activityStack.size();
        for (int i = size - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (activity.getClass() != dontFinishAct && activity.getClass() != MainActivity.class) {
                activity.finish();
                activityStack.remove(activity);
            }
        }
    }
}
