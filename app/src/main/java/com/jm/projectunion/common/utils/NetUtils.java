package com.jm.projectunion.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class NetUtils {
	public static final int NET_WIFI = 0;
	public static final int NET_MOBILE = 1;
	public static final int NET_NONE = 2;

	public static int getNetStatus(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
		if (activeInfo != null && activeInfo.isConnected()) {
			int type = activeInfo.getType();
			if (type == ConnectivityManager.TYPE_WIFI) {
				return NET_WIFI;
			}
			if (type == ConnectivityManager.TYPE_MOBILE) {
				return NET_MOBILE;
			}
		}
		return NET_NONE;
	}

	/*
     * 判断网络连接是否已开
     * 2012-08-20
     *true 已打开  false 未打开
     * */
	public static boolean isConn(Context context){
		boolean bisConnFlag=false;
		ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = conManager.getActiveNetworkInfo();
		if(network!=null){
			bisConnFlag=conManager.getActiveNetworkInfo().isAvailable();
		}
		return bisConnFlag;
	}
	/**
	 * 当前网络状态是否已链接上WIFI
	 *
	 * @param context
	 * @return
	 */
	public final static boolean isWifi(Context context) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo aliveInfo = connectivityManager.getActiveNetworkInfo();
			if (aliveInfo != null && aliveInfo.isConnected()) {
				if (aliveInfo.getType() == ConnectivityManager.TYPE_WIFI) {
					return true;
				}
			}
		} catch (Exception e) {
			LogUtils.e("get newwork type Err:" + e);
		}
		return false;
	}
	/**
	 * 获取设备当前连接网络的IP地址
	 *
	 * @return
	 */
	public final static String getIP(Context context) {
		if (isWifi(context)) {
			return getWifiIp(context);
		} else {
			try {
				for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
						.hasMoreElements(); ) {
					NetworkInterface intf = en.nextElement();
					for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
							.hasMoreElements(); ) {
						InetAddress inetAddress = enumIpAddr.nextElement();
						if (!inetAddress.isLoopbackAddress()) {
							return inetAddress.getHostAddress().toString();
						}
					}
				}
			} catch (Exception e) {
				LogUtils.e("Get IP Err:" + e);
			}
		}
		return null;
	}

	public final static String getWifiIp(Context context) {
		//获取wifi服务
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		//判断wifi是否开启
//        if (!wifiManager.isWifiEnabled()) {
//            wifiManager.setWifiEnabled(true);
//        }
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		return intToIp(ipAddress);
	}

	private final static String intToIp(int i) {

		return (i & 0xFF) + "." +
				((i >> 8) & 0xFF) + "." +
				((i >> 16) & 0xFF) + "." +
				(i >> 24 & 0xFF);
	}
	/**
	 * 获取设备网络MAC地址
	 *
	 * @param context
	 * @return
	 */
	public final static String getMAC(Context context) {
		try {
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			return wifiInfo.getMacAddress();
		} catch (Exception e) {
			LogUtils.e("get MAC Err:" + e);
		}
		return null;
	}

}
