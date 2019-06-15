package com.jm.projectunion.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * 时间操作工具类
 *
 * @author Young
 * @date 2015/6/9 11:46
 */
public class DateUtils {
    // 以下为格式化时间的格式
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_EN = "yyyy/MM";
    public static final String YYYY_MM_DD_EN = "yyyy/MM/dd";
    public static final String YYYY_MM_DD_HH_MM_EN = "yyyy/MM/dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS_EN = "yyyy/MM/dd HH:mm:ss";

    public static final String YYYY_MM_CN = "yyyy年MM月";
    public static final String YYYY_MM_DD_CN = "yyyy年MM月dd日";
    public static final String YYYY_MM_DD_HH_MM_CN = "yyyy年MM月dd日 HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS_CN = "yyyy年MM月dd日 HH:mm:ss";

    public static final String HH_MM = "HH:mm";
    public static final String HH_MM_SS = "HH:mm:ss";

    public static final String MM_DD = "MM-dd";
    public static final String MM_DD_HH_MM = "MM-dd HH:mm";
    public static final String MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";

    public static final String MM_DD_EN = "MM/dd";
    public static final String MM_DD_HH_MM_EN = "MM/dd HH:mm";
    public static final String MM_DD_HH_MM_SS_EN = "MM/dd HH:mm:ss";

    public static final String MM_DD_CN = "MM月dd日";
    public static final String MM_DD_HH_MM_CN = "MM月dd日 HH:mm";
    public static final String MM_DD_HH_MM_SS_CN = "MM月dd日 HH:mm:ss";

    /**
     * 描述：String类型的日期时间转化为Date类型.
     *
     * @param strDate String形式的日期时间
     * @param format  格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return Date Date类型日期时间
     * @throws ParseException
     */
    public static Date getDateByFormat(String strDate, String format)
            throws Exception {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format,
                Locale.US);
        return mSimpleDateFormat.parse(strDate);
    }

    /**
     * 描述：获取偏移之后的Date.
     *
     * @param date          日期时间
     * @param calendarField Calendar属性，对应offset的值，
     *                      如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return Date 偏移之后的日期时间
     */
    public Date getDateByOffset(Date date, int calendarField, int offset)
            throws IllegalArgumentException {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(calendarField, offset);
        return c.getTime();
    }

    /**
     * 描述：获取指定日期时间的字符串(可偏移).
     *
     * @param strDate       String形式的日期时间
     * @param format        格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @param calendarField Calendar属性，对应offset的值，
     *                      如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return String String类型的日期时间
     * @throws ParseException
     */
    public static String getStringByOffset(String strDate, String format,
                                           int calendarField, int offset) throws Exception {
        Calendar c = new GregorianCalendar();
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format,
                Locale.US);
        c.setTime(mSimpleDateFormat.parse(strDate));
        c.add(calendarField, offset);
        return mSimpleDateFormat.format(c.getTime());
    }

    /**
     * 描述：Date类型转化为String类型(可偏移).
     *
     * @param date          the date
     * @param format        the format
     * @param calendarField the calendar field
     * @param offset        the offset
     * @return String String类型日期时间
     */
    public static String getStringByOffset(Date date, String format,
                                           int calendarField, int offset) throws Exception {
        Calendar c = new GregorianCalendar();
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format,
                Locale.US);
        c.setTime(date);
        c.add(calendarField, offset);
        return mSimpleDateFormat.format(c.getTime());
    }

    /**
     * 描述：Date类型转化为String类型.
     *
     * @param date   the date
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getStringByFormat(Date date, String format)
            throws Exception {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format,
                Locale.US);
        return mSimpleDateFormat.format(date);
    }

    /**
     * 描述：获取指定日期时间的字符串,用于导出想要的格式.
     *
     * @param strDate String形式的日期时间，必须为yyyy-MM-dd HH:mm:ss格式
     * @param format  输出格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 转换后的String类型的日期时间
     */
    public static String getStringByFormat2(String strDate, String format)
            throws Exception {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = mSimpleDateFormat.parse(strDate);
        SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
        return mSimpleDateFormat2.format(date);
    }

    /**
     * 描述：获取指定日期时间的字符串,用于导出想要的格式.
     *
     * @param strDate String形式的日期时间，必须为yyyy年MM月dd日格式
     * @param format  输出格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 转换后的String类型的日期时间
     */
    public static String getStringByFormat(String strDate, String format)
            throws Exception {
        Calendar c = new GregorianCalendar();
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
                YYYY_MM_DD_HH_MM_SS, Locale.US);
        c.setTime(mSimpleDateFormat.parse(strDate));
        SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format,
                Locale.US);
        return mSimpleDateFormat2.format(c.getTime());
    }

    /**
     * 描述：获取milliseconds表示的日期时间的字符串.
     *
     * @param milliseconds the milliseconds
     * @param format       格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 日期时间字符串
     */
    public static String getStringByFormat(long milliseconds, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format,
                Locale.US);
        return mSimpleDateFormat.format(milliseconds);
    }

    /**
     * Function:将时间毫秒值转化为格式化的日期形式
     *
     * @param longTime  时间毫秒值
     * @param formatStr 格式化字符串 比如："yyyy-MM-dd 'at' HH:mm:ss:ms"
     * @return
     */
    public static String formatTime(long longTime, String formatStr) {
        SimpleDateFormat formater = new SimpleDateFormat(formatStr);
        Date date = new Date(longTime);
        return formater.format(date);
    }

    /**
     * 描述：获取表示当前日期时间的字符串.
     *
     * @param format 格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String String类型的当前日期时间
     */
    public static String getCurrentDate(String format) throws Exception {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format,
                Locale.US);
        Calendar c = new GregorianCalendar();
        return mSimpleDateFormat.format(c.getTime());
    }

    /**
     * 描述：获取表示当前日期时间的毫秒
     *
     * @return String String类型的当前日期时间
     */
    public static Long getCurrentDateTime() {
        Calendar c = new GregorianCalendar();
        long time = c.getTimeInMillis();
        return time;
    }

    /**
     * 描述：获取表示当前日期时间的字符串(可偏移).
     *
     * @param format        格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @param calendarField Calendar属性，对应offset的值，
     *                      如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return String String类型的日期时间
     */
    public static String getCurrentDateByOffset(String format,
                                                int calendarField, int offset) throws Exception {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format,
                Locale.US);
        Calendar c = new GregorianCalendar();
        c.add(calendarField, offset);
        return mSimpleDateFormat.format(c.getTime());
    }

    /**
     * 描述：计算两个日期所差的天数.
     *
     * @param milliseconds1 the milliseconds1
     * @param milliseconds2 the milliseconds2
     * @return int 所差的天数
     */
    public static int getOffectDay(long milliseconds1, long milliseconds2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(milliseconds1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(milliseconds2);
        // 先判断是否同年
        int y1 = calendar1.get(Calendar.YEAR);
        int y2 = calendar2.get(Calendar.YEAR);
        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int maxDays = 0;
        int day = 0;
        if (y1 - y2 > 0) {
            maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 + maxDays;
        } else if (y1 - y2 < 0) {
            maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 - maxDays;
        } else {
            day = d1 - d2;
        }
        return day;
    }

    /**
     * 描述：计算两个日期所差的小时数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的小时数
     */
    public static int getOffectHour(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int h1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int h2 = calendar2.get(Calendar.HOUR_OF_DAY);
        int h = 0;
        int day = getOffectDay(date1, date2);
        h = h1 - h2 + day * 24;
        return h;
    }

    /**
     * 描述：计算两个日期所差的分钟数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的分钟数
     */
    public static int getOffectMinutes(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int m1 = calendar1.get(Calendar.MINUTE);
        int m2 = calendar2.get(Calendar.MINUTE);
        int h = getOffectHour(date1, date2);
        int m = 0;
        m = m1 - m2 + h * 60;
        return m;
    }

    /**
     * 描述：获取本周一.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfWeek(String format) throws Exception {
        return getDayOfWeek(format, Calendar.MONDAY);
    }

    /**
     * 描述：获取本周日.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getLastDayOfWeek(String format) throws Exception {
        return getDayOfWeek(format, Calendar.SUNDAY);
    }

    /**
     * 描述：获取本周的某一天.
     *
     * @param format        the format
     * @param calendarField the calendar field
     * @return String String类型日期时间
     */
    private static String getDayOfWeek(String format, int calendarField) {
        String strDate = null;
        Calendar c = new GregorianCalendar();
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format,
                Locale.US);
        int week = c.get(Calendar.DAY_OF_WEEK);
        if (week == calendarField) {
            strDate = mSimpleDateFormat.format(c.getTime());
        } else {
            int offectDay = calendarField - week;
            if (calendarField == Calendar.SUNDAY) {
                offectDay = 7 - Math.abs(offectDay);
            }
            c.add(Calendar.DATE, offectDay);
            strDate = mSimpleDateFormat.format(c.getTime());
        }
        return strDate;
    }

    /**
     * 描述：获取本月第一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfMonth(String format) throws Exception {
        Calendar c = new GregorianCalendar();
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format,
                Locale.US);
        // 当前月的第一天
        c.set(GregorianCalendar.DAY_OF_MONTH, 1);
        return mSimpleDateFormat.format(c.getTime());
    }

    /**
     * 描述：获取本月最后一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getLastDayOfMonth(String format) throws Exception {
        Calendar c = new GregorianCalendar();
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format,
                Locale.US);
        // 当前月的最后一天
        c.set(Calendar.DATE, 1);
        c.roll(Calendar.DATE, -1);
        return mSimpleDateFormat.format(c.getTime());
    }

    /**
     * 描述：获取表示当前日期的0点时间毫秒数.
     *
     * @return the first time of day
     */
    public static long getFirstTimeOfDay() throws Exception {
        String currentDate = getCurrentDate(YYYY_MM_DD);
        return getDateByFormat(currentDate + " 00:00:00", YYYY_MM_DD_HH_MM_SS)
                .getTime();
    }

    /**
     * 描述：获取表示当前日期24点时间毫秒数.
     *
     * @return the last time of day
     */
    public static long getLastTimeOfDay() throws Exception {
        String currentDate = getCurrentDate(YYYY_MM_DD);
        return getDateByFormat(currentDate + " 24:00:00", YYYY_MM_DD_HH_MM_SS)
                .getTime();
    }

    /**
     * 描述：判断是否是闰年()
     * <p>
     * (year能被4整除 并且 不能被100整除) 或者 year能被400整除,则该年为闰年.
     *
     * @param year 年代（如2012）
     * @return boolean 是否为闰年
     */
    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 400 != 0) || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }


    /*
    * 根据时间戳获取年月日时分秒
    * */
    public static String getYMDHMS(String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        String st = sdf.format(new Date(Long.parseLong(formatStr)));
        return st;
    }

    /*
    * 根据时间戳获取年月日
    * */
    public static String getYMD(String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        String st = sdf.format(new Date(Long.parseLong(formatStr)));
        return st;
    }

    /**
     * 获取某个月份的天数
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 毫秒转化时分秒毫秒
     */

    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
            return sb.toString();
        }
        if (hour > 0) {
            sb.append(hour + "小时");
            return sb.toString();
        }
        if (minute > 0) {
            sb.append(minute + "分");
            return sb.toString();
        }
        if (second > 0) {
            sb.append(second + "秒");
            return sb.toString();
        }
        return sb.toString();
    }
}
