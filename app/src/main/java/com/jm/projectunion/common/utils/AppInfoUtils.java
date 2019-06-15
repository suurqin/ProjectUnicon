package com.jm.projectunion.common.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * 描述：获取跟APP相关的信息工具类
 * @作者：JQ.Hu
 * @创建时间：2016/5/24 11:00
 */
public class AppInfoUtils {

	/**
	 * 是否是主线程
	 * @return
	 */
	public static boolean isMainProcess(Context context) {
		ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
		List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		String mainProcessName = context.getPackageName();
		int myPid = android.os.Process.myPid();
		for (RunningAppProcessInfo info : processInfos) {
			if (info.pid == myPid && mainProcessName.equals(info.processName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取程序 图标
	 */
	public static Drawable getAppIcon(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			ApplicationInfo info = pm.getApplicationInfo(getAppPackageName(context), 0);
			return info.loadIcon(pm);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取程序的版本号
	 */
	public static String getAppVersion(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo packinfo = pm.getPackageInfo(getAppPackageName(context), 0);
			return packinfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取程序的版本号
	 */
	public static int getAppVersionCode(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo packinfo = pm.getPackageInfo(getAppPackageName(context), 0);
			return packinfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取程序的名字
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			ApplicationInfo info = pm.getApplicationInfo(getAppPackageName(context), 0);
			return info.loadLabel(pm).toString();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取程序的权限
	 */
	public static String[] getAppPremission(Context context, String packname) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo packinfo = pm.getPackageInfo(packname, PackageManager.GET_PERMISSIONS);
			// 获取到所有的权限
			return packinfo.requestedPermissions;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取程序的签名
	 */
	public static String getAppSignature(Context context, String packname) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo packinfo = pm.getPackageInfo(packname, PackageManager.GET_SIGNATURES);
			return packinfo.signatures[0].toCharsString();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取程序的包名
	 */
	public static String getAppPackageName(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo packinfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
			return packinfo.packageName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 判断应用是否在前台
	 * @param context
	 * @return
	 */
	public static boolean isMyAppOnTop(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(50);
		String pkgName=getAppPackageName(context);
		for (RunningTaskInfo info : list) {
			if (info.topActivity.getPackageName().equals(pkgName)
					&& info.baseActivity.getPackageName().equals(pkgName) && info.numActivities > 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断应用是否在前台
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isAppOnTop(Context context, String packageName) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(50);
		for (RunningTaskInfo info : list) {
			if (info.topActivity.getPackageName().equals(packageName)
					&& info.baseActivity.getPackageName().equals(packageName) && info.numActivities > 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取应用活动的页面个数
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static int getActivitiesCount(Context context, String packageName) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(80);
		for (RunningTaskInfo info : list) {
			if (info.baseActivity.getPackageName().equals(getAppPackageName(context))) {
				return info.numActivities;
			}
		}
		return 0;
	}

	/**
	 * 获取应用的进程名称，默认是APP的包名
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getAppProcessName(Context ctx) {
		int pid = android.os.Process.myPid();
		String processName = null;
		ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = ctx.getPackageManager();
		while (i.hasNext()) {
			RunningAppProcessInfo info = (RunningAppProcessInfo) (i.next());
			try {
				if (info.pid == pid) {
					CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName,
							PackageManager.GET_META_DATA));
					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
			}
		}
		return processName;
	}

	/**
	 * 适用：从不同的进程（通的应用、桌面widget等等）启动应用时使用此方法。
	 *
	 * @param context
	 */
	public static void startApp(Context context, Class mainActivity) {
		// 这个方式可以防止重复启动多个APP实例
		Intent intent = new Intent("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		intent.setComponent(new ComponentName(AppInfoUtils.getAppPackageName(context), mainActivity.getName()));
		context.startActivity(intent);
	}
	/**
	 * 适用：用于已经有一个Activity，但是这个Activity是在单独的Task当中（一般用Intent.
	 * FLAG_ACTIVITY_NEW_TASK模式启动的） ，在此Activity启动应用时，则使用此方法。
	 *
	 * @param context
	 * @param mainActivity
	 */
	public static void startAppFromOut(Context context, Class mainActivity) {
		// 这个方式不能防止重复启动多个APP实例
//		Intent mainIntent = new Intent(context, mainActivity);
		Intent mainIntent = new Intent();
		mainIntent.setComponent(new ComponentName(AppInfoUtils.getAppPackageName(context), mainActivity.getName()));
		mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(mainIntent);
	}

	/**
	 * 安装apk
	 *
	 * @param context 上下文
	 * @param file    APK文件
	 */
	public static void installApk(Context context, File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 安装apk
	 *
	 * @param context 上下文
	 * @param file    APK文件uri
	 */
	public static void installApk(Context context, Uri file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(file, "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 卸载apk
	 *
	 * @param context     上下文
	 * @param packageName 包名
	 */
	public static void uninstallApk(Context context, String packageName) {
		Intent intent = new Intent(Intent.ACTION_DELETE);
		Uri packageURI = Uri.parse("package:" + packageName);
		intent.setData(packageURI);
		context.startActivity(intent);
	}

	/**
	 * 检测服务是否运行
	 *
	 * @param context   上下文
	 * @param className 类名
	 * @return 是否运行的状态
	 */
	public static boolean isServiceRunning(Context context, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> servicesList = activityManager
				.getRunningServices(Integer.MAX_VALUE);
		for (ActivityManager.RunningServiceInfo si : servicesList) {
			if (className.equals(si.service.getClassName())) {
				isRunning = true;
			}
		}
		return isRunning;
	}

	/**
	 * 停止运行服务
	 *
	 * @param context   上下文
	 * @param className 类名
	 * @return 是否执行成功
	 */
	public static boolean stopRunningService(Context context, String className) {
		Intent intent_service = null;
		boolean ret = false;
		try {
			intent_service = new Intent(context, Class.forName(className));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (intent_service != null) {
			ret = context.stopService(intent_service);
		}
		return ret;
	}

	/**
	 * 获取应用运行的最大内存
	 *
	 * @return 最大内存
	 */
	public static long getMaxMemory() {

		return Runtime.getRuntime().maxMemory() / 1024;
	}
}
