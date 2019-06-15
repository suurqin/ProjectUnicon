package com.jm.projectunion;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.jm.projectunion.common.crash.CrashHandler;
import com.jm.projectunion.common.manager.AppManager;
import com.jm.projectunion.common.manager.Config;
import com.jm.projectunion.common.utils.AppInfoUtils;
import com.jm.projectunion.common.utils.AppUtils;
import com.jm.projectunion.location.LocationService;
import com.jm.projectunion.message.chat.EaseIMHelper;
import com.jm.projectunion.utils.GlobalVar;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.autolayout.config.AutoLayoutConifg;

import cn.jpush.android.api.JPushInterface;


/**
 * 描述：通用自定义Application，应用通用的一些数据或者方法再次定义，以及应用需要启动时初始化的逻辑再次初始化
 */
public class MyApplication extends Application {

    {
        PlatformConfig.setWeixin(GlobalVar.WX_APPID, GlobalVar.WX_SCERET);
        PlatformConfig.setQQZone(GlobalVar.QQ_ID, GlobalVar.QQ_KEY);
        PlatformConfig.setSinaWeibo(GlobalVar.SINA_KEY, GlobalVar.SINA_SCERET, GlobalVar.SINA_REDIRECTURL);
    }

    public final static String TAG = MyApplication.class.getSimpleName();
    private static Context context;
    private static MyApplication mInstance;


    private Activity curActivity;
    public LocationService locationService;

    public static MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        mInstance = this;
        context = getApplicationContext();
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        AppUtils.init(this);

//        LitePalApplication.initialize(this);
        /*
         * 如果app启用了远程的service，此application:onCreate会被调用2次
		 * 为了防止EmaintainApp被初始化2次，加此判断会保证EmaintainApp被初始化1次
		 * 默认的app会在以包名为默认的process name下运行（即process name=packageName），
		 * 如果查到的process name不是app的processname就立即返回
		 * isFristStart()==true,则此application::onCreate 是被service 调用的，直接返回
		 */
        if (isFristStart(AppInfoUtils.getAppProcessName(this))) {
            return;
        }
        super.onCreate();
        if (!BuildConfig.LOG_DEBUG) {
            //在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext());
        }
        AutoLayoutConifg.getInstance().useDeviceSize();
        EaseIMHelper.getInstance().init(this);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        UMShareAPI.get(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private boolean isFristStart(String processAppName) {
        return processAppName == null || !processAppName.equalsIgnoreCase(AppInfoUtils.getAppPackageName(this));
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {// 遍历存放在list中的Activity并退出
        AppManager.getInstance().finishAllActivity();
        super.onTerminate();
    }

    public void quitAll(boolean isNormExit) {
        killAppProcess(this);
    }

    public void killAppProcess(final Context ctx) {
        AppManager.getInstance().finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    /**
     * 适用：从不同的进程（通的应用、桌面widget等等）启动应用时使用此方法。
     */
    public static void startApp(Context context, Class mainActivity) {
        // 这个方式可以防止重复启动多个APP实例
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.setComponent(new ComponentName(AppInfoUtils.getAppPackageName(context), mainActivity.getName()));
        context.startActivity(intent);
    }

    public static Context getContext() {
        return context;
    }
}