package com.jm.projectunion.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据验证工具类
 * 
 * @author Young
 * @date 2015-5-4 下午8:25:16
 */
public class ValidateUtils {
	/**
	 * 手否为手机号码
	 * 
	 * @param mobile
	 *            手机号
	 * @return true 为手机号
	 */
	public static boolean isMobile(String mobile) {
		return match("^(1[3|4|5|7|8][0-9])\\d{8}$", mobile);
	}

	/**
	 * 是否为身份证号
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean isIdCard(String idcard) {
		return match("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])", idcard);
	}

	/**
	 * 匹配验证
	 * 
	 * @param regex
	 *            正则表达式
	 * @param str
	 *            需要验证的字符串
	 * @return true 匹配 ，false 不匹配
	 */
	public static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	/**
     * 校验银行卡卡号
     * @author syf
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
             char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
             if(bit == 'N'){
                 return false;
             }
             return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     * @author syf
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId){
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;           
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static int getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

