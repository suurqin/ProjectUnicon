package com.jm.projectunion.common.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 描述：时间字符串转换格式化工具类
 * @作者：JQ.Hu
 * @创建时间：2016/5/23 16:50
 */
public class DateTimeUtil {

	public final static String FORMATER_STR_DEFAULT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Function:将时间毫秒值转化为格式化的日期形式
	 * 
	 * @param longTime 时间毫秒值
	 * @return
	 */
	public static String formatTime(long longTime) {
		SimpleDateFormat formater = new SimpleDateFormat(FORMATER_STR_DEFAULT);
		Date date = new Date(longTime);
		return formater.format(date);
	}

	/**
	 * Function:将时间毫秒值转化为格式化的日期形式
	 * 
	 * @param longTime 时间毫秒值
	 * @param formatStr 格式化字符串 比如："yyyy-MM-dd 'at' HH:mm:ss:ms"
	 * @return
	 */
	public static String formatTime(long longTime, String formatStr) {
		SimpleDateFormat formater = new SimpleDateFormat(formatStr);
		Date date = new Date(longTime);
		return formater.format(date);
	}

	/**
	 * 提升模块用到的时间转换工具
	 * @param longTime	时间戳
	 * @return
     */
	public static String formatTimePromote(long longTime) {
		return formatTime(longTime,"MM月dd日 HH:mm:ss");
	}

	/**
	 * 说明：<font color=red>注意：formatTime和formatPattern的格式必须是相同的，比如：如果formatTime为2008
	 *            .08.08，则formatPattern必须是"yyyy.MM.dd"</font>
	 * @param formatTime
	 *            格式化过的日期字符串，比如："yyyy-MM-dd 'at' HH:mm:ss"、"yyyy-MM-dd"、“yyMMddHHmmss”、
	 *            “yyyy/MM/dd HH:mm:ss”、“yyyy/MM/dd”、“yyyy.MM.dd
	 *            HH:mm:ss”、“yyyy.MM.dd”等等。<br>
	 *            
	 * @param formatPattern
	 *            格式化格式，如："yyyy-MM-dd 'at' HH:mm:ss"、“yyMMddHHmmss”、“yyyy/MM/dd
	 *            HH:mm:ss”、“yyyy.MM.dd HH:mm:ss”等等
	 * @return
	 */
	public static long dateTimeStr2LongValue(String formatTime, String formatPattern) {
		SimpleDateFormat formater = new SimpleDateFormat(formatPattern);
		try {
			return formater.parse(formatTime).getTime();
		} catch (ParseException e) {
			LogUtils.e("DataTimeUtil", e);
		}
		return 0;
	}

	/**
	 * Function:使用默认的（"yyyy-MM-dd"、“yyMMdd”）格式化形式将格式化过的[日期和时间]字符串转化为时间毫秒值
	 * 
	 * @param formatTime
	 *            格式化过的日期字符串，比如："yyyy-MM-dd 'at' HH:mm:ss"、“yyMMddHHmmss”、
	 *            “yyyy/MM/ddHH:mm:ss”、“yyyy.MM.dd HH:mm:ss”等等
	 * @return
	 */
	public static long formatTime2LongValue(String formatTime) {
		formatTime = formatTime.replace(".", "-").replace("/", "-");
		SimpleDateFormat formater = (SimpleDateFormat) SimpleDateFormat
				.getDateTimeInstance();
		try {
			return formater.parse(formatTime).getTime();
		} catch (ParseException e) {
			LogUtils.e("DataTimeUtil", e);
		}
		return 0;
	}

	/**
	 * Function:使用默认的（"yyyy-MM-dd"、“yyMMdd”）格式化形式将格式化过的[日期]字符串转化为时间毫秒值<br/>
	 * 
	 * @param formatTime 格式化过的日期字符串，比如："yyyy-MM-dd"、“yyMMdd”、“yyyy/MM/dd”、“yyyy.MM.dd”等等
	 * @return
	 */
	public static long formatDate2LongValue(String formatTime) {
		formatTime = formatTime.replace(".", "-").replace("/", "-");
		SimpleDateFormat formater = (SimpleDateFormat) SimpleDateFormat
				.getDateInstance();
		try {
			return formater.parse(formatTime).getTime();
		} catch (ParseException e) {
			LogUtils.e("DataTimeUtil", e);
		}
		return 0;
	}

	/**
	 * 获取今天的日期格式化字符串
	 * 
	 * @param formatePattern 格式化格式，如：“yyyy/MM/dd HH:mm:ss”等等
	 * @return
	 */
	public static String getCurrentDateStr(String formatePattern) {
		Date date = new Date();
		String str = null;
		SimpleDateFormat df = new SimpleDateFormat(formatePattern);
		str = df.format(date);
		return str;
	}

	/**
	 * 比较两个时间是否在同一天
	 * 
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	public static boolean isSameDay(long dt1, long dt2) {
		Date d1 = new Date(dt1);
		Date d2 = new Date(dt2);
		if (d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth()
				&& d1.getDate() == d2.getDate()) {
			return true;
		}
		return false;
	}

	/**
	 * 日期类型 *
	 */
	public static final String yyyyMMDD = "yyyy-MM-dd";
	public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	public static final String HHmmss = "HH:mm:ss";
	public static final String hhmmss = "HH:mm:ss";
	public static final String LOCALE_DATE_FORMAT = "yyyy年M月d日 HH:mm:ss";
	public static final String DB_DATA_FORMAT = "yyyy-MM-DD HH:mm:ss";
	public static final String NEWS_ITEM_DATE_FORMAT = "hh:mm M月d日 yyyy";


	public static String dateToString(Date date, String pattern)
			throws Exception {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static Date stringToDate(String dateStr, String pattern)
			throws Exception {
		return new SimpleDateFormat(pattern).parse(dateStr);
	}

	/**
	 * 将Date类型转换为日期字符串
	 *
	 * @param date Date对象
	 * @param type 需要的日期格式
	 * @return 按照需求格式的日期字符串
	 */
	public static String formatDate(Date date, String type) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(type);
			return df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将日期字符串转换为Date类型
	 *
	 * @param dateStr 日期字符串
	 * @param type    日期字符串格式
	 * @return Date对象
	 */
	public static Date parseDate(String dateStr, String type) {
		SimpleDateFormat df = new SimpleDateFormat(type);
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;

	}

	/**
	 * 得到年
	 *
	 * @param date Date对象
	 * @return 年
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 得到月
	 *
	 * @param date Date对象
	 * @return 月
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;

	}

	/**
	 * 得到日
	 *
	 * @param date Date对象
	 * @return 日
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 转换日期 将日期转为今天, 昨天, 前天, XXXX-XX-XX, ...
	 *
	 * @param time 时间
	 * @return 当前日期转换为更容易理解的方式
	 */
	public static String translateDate(Long time) {
		long oneDay = 24 * 60 * 60 * 1000;
		Calendar current = Calendar.getInstance();
		Calendar today = Calendar.getInstance();    //今天

		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
		//  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);

		long todayStartTime = today.getTimeInMillis();

		if (time >= todayStartTime && time < todayStartTime + oneDay) { // today
			return "今天";
		} else if (time >= todayStartTime - oneDay && time < todayStartTime) { // yesterday
			return "昨天";
		} else if (time >= todayStartTime - oneDay * 2 && time < todayStartTime - oneDay) { // the day before yesterday
			return "前天";
		} else if (time > todayStartTime + oneDay) { // future
			return "将来某一天";
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date(time);
			return dateFormat.format(date);
		}
	}

	/**
	 * 转换日期 转换为更为人性化的时间
	 *
	 * @param time 时间
	 * @return
	 */
	private String translateDate(long time, long curTime) {
		long oneDay = 24 * 60 * 60;
		Calendar today = Calendar.getInstance();    //今天
		today.setTimeInMillis(curTime * 1000);
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		long todayStartTime = today.getTimeInMillis() / 1000;
		if (time >= todayStartTime) {
			long d = curTime - time;
			if (d <= 60) {
				return "1分钟前";
			} else if (d <= 60 * 60) {
				long m = d / 60;
				if (m <= 0) {
					m = 1;
				}
				return m + "分钟前";
			} else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("今天 HH:mm");
				Date date = new Date(time * 1000);
				String dateStr = dateFormat.format(date);
				if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
					dateStr = dateStr.replace(" 0", " ");
				}
				return dateStr;
			}
		} else {
			if (time < todayStartTime && time > todayStartTime - oneDay) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("昨天 HH:mm");
				Date date = new Date(time * 1000);
				String dateStr = dateFormat.format(date);
				if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {

					dateStr = dateStr.replace(" 0", " ");
				}
				return dateStr;
			} else if (time < todayStartTime - oneDay && time > todayStartTime - 2 * oneDay) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("前天 HH:mm");
				Date date = new Date(time * 1000);
				String dateStr = dateFormat.format(date);
				if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
					dateStr = dateStr.replace(" 0", " ");
				}
				return dateStr;
			} else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date date = new Date(time * 1000);
				String dateStr = dateFormat.format(date);
				if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
					dateStr = dateStr.replace(" 0", " ");
				}
				return dateStr;
			}
		}
	}
	public static  String getTime(Date date,String type) {
		SimpleDateFormat format = new SimpleDateFormat(type);//HH:mm
		return format.format(date);
	}

	public static  boolean compare(String time1,String time2)
	{
		//如果想比较日期则写成"yyyy-MM-dd"就可以了
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//将字符串形式的时间转化为Date类型的时间
		try {
			Date a = sdf.parse(time1);
			Date b=sdf.parse(time2);
			//Date类的一个方法，如果a早于b返回true，否则返回false
			if(a.before(b))
				return true;
			else
				return false;

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return false;

	}


	public static long dataToLong(String time,String type){
		SimpleDateFormat format = new SimpleDateFormat(type);
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  date.getTime();
	}
}
