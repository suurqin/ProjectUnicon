package com.jm.projectunion.common.crash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import com.jm.projectunion.common.manager.AppManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CrashHandler implements Thread.UncaughtExceptionHandler {
	private static final String TAG = "CrashHandler";
	private static final boolean DEBUG = true;

	private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/adb_test/log/";
	private static final String FILE_NAME = "crash";

	//log文件的后缀名
	private static final String FILE_NAME_SUFFIX = ".trace";

	private static CrashHandler sInstance = new CrashHandler();

	//系统默认的异常处理（默认情况下，系统会终止当前的异常程序）
	private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

	private Context mContext;

	//构造方法私有，防止外部构造多个实例，即采用单例模式
	private CrashHandler() {
	}

	public static CrashHandler getInstance() {
		return sInstance;
	}

	//这里主要完成初始化工作
	public void init(Context context) {
		//获取系统默认的异常处理器
		mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
		//将当前实例设为系统默认的异常处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
		//获取Context，方便内部使用
		mContext = context.getApplicationContext();
	}

	/**
	 * 这个是最关键的函数，当程序中有未被捕获的异常，系统将会自动调用#uncaughtException方法
	 * thread为出现未捕获异常的线程，ex为未捕获的异常，有了这个ex，我们就可以得到异常信息。
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
			//导出异常信息到SD卡中
			//dumpExceptionToSDCard(ex);
			//这里可以通过网络上传异常信息到服务器，便于开发人员分析日志从而解决bug
			uploadExceptionToServer();
			if(!handleException(ex)){
				//如果系统提供了默认的异常处理器，则交给系统去结束我们的程序，否则就由我们自己结束自己
				if (mDefaultCrashHandler != null) {
					mDefaultCrashHandler.uncaughtException(thread, ex);
				} else {
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			}
		//打印出当前调用栈信息
		ex.printStackTrace();



	}

	private void dumpExceptionToSDCard(Throwable ex) throws IOException {
		//如果SD卡不存在或无法使用，则无法把异常信息写入SD卡
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			if (DEBUG) {
				Log.w(TAG, "sdcard unmounted,skip dump exception");
				return;
			}
		}

		File dir = new File(PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		long current = System.currentTimeMillis();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
		//以当前时间创建log文件
		File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);

		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			//导出发生异常的时间
			pw.println(time);

			//导出手机信息
			dumpPhoneInfo(pw);

			pw.println();
			//导出异常的调用栈信息
			ex.printStackTrace(pw);

			pw.close();
		} catch (Exception e) {
			Log.e(TAG, "dump crash info failed");
		}
	}

	private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
		//应用的版本名称和版本号
		PackageManager pm = mContext.getPackageManager();
		PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
		pw.print("App Version: ");
		pw.print(pi.versionName);
		pw.print('_');
		pw.println(pi.versionCode);

		//android版本号
		pw.print("OS Version: ");
		pw.print(Build.VERSION.RELEASE);
		pw.print("_");
		pw.println(Build.VERSION.SDK_INT);

		//手机制造商
		pw.print("Vendor: ");
		pw.println(Build.MANUFACTURER);

		//手机型号
		pw.print("Model: ");
		pw.println(Build.MODEL);

		//cpu架构
		pw.print("CPU ABI: ");
		pw.println(Build.CPU_ABI);
	}

	private void uploadExceptionToServer() {
		//TODO Upload Exception HisMessage To Your Web Server
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
	 *
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}

		final Activity activity = AppManager.getInstance().currentActivity();

		if (activity == null) {
			return false;
		}

		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				new AlertDialog.Builder(activity).setTitle("提示")
//						.setCancelable(false).setMessage("\n软件出现异常\n工程师正在努力解决\n请稍后重试...\n")
						.setCancelable(false).setMessage("\n由于您的设备网络不稳定\n导致请求失败\n请稍后重试...\n")
						.setNeutralButton("知道了", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								AppManager.getInstance().appExit(activity);
							}
						}).create().show();
				Looper.loop();
			}
		}.start();
		return true;
	}
}