package com.jm.projectunion.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 精确算数-保留后两位
 */
public class AccurateArithUtils {

    /**
     * 提供精确加法计算的add方法
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static String add(double value1, double value2) {
        DecimalFormat df = new DecimalFormat("#.00");
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        double value = b1.add(b2).doubleValue();
        String formatValue = df.format(value);
        return formatValue;
    }

    /**
     * 提供精确加法计算的add方法
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static String add(String value1, String value2) {
        DecimalFormat df = new DecimalFormat("#.00");
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        double value = b1.add(b2).doubleValue();
        String formatValue = df.format(value);
        return formatValue;
    }

    /**
     * 提供精确减法运算的sub方法
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static String sub(double value1, double value2) {
        DecimalFormat df = new DecimalFormat("#.00");
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        double value = b1.subtract(b2).doubleValue();
        String formatValue = df.format(value);
        return formatValue;
    }

    /**
     * 提供精确减法运算的sub方法
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static String sub(String value1, String value2) {
        DecimalFormat df = new DecimalFormat("#.00");
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        double value = b1.subtract(b2).doubleValue();
        String formatValue = df.format(value);
        return formatValue;
    }

    public static int mult(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        int value = b1.multiply(b2).intValue();
        return value;
    }

}
