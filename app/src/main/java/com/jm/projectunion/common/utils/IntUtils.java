package com.jm.projectunion.common.utils;

/**
 * Created by yunzhao.liu on 2017/1/11
 */

public class IntUtils {

    /**
     * 比较两个数的大小 value1大于等于value2返回true,否则返回false;
     * @param value1
     * @param value2
     * @return
     */
    public static boolean ComparativeSize(int value1, int value2) {
        if (value1 >= value2) {
            return true;
        }
        return false;
    }
}
