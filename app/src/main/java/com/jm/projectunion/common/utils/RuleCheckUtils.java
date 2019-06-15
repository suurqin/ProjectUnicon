package com.jm.projectunion.common.utils;

import java.util.regex.Pattern;

/**
 * Created by yunzhao.liu on 2017/2/17
 */

public class RuleCheckUtils {
    private static final String PASSWORD_REGEX = "[a-zA-Z0-9]{6,20}";
    private static final String PASSWORD_REGEX1 = "(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{6,8}";
    private static final String PASSWORD_REGEX2 = "[0-9a-zA-Z]{6,8}";

    public static boolean passwordRegex(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    public static boolean pwdRegex(String password) {
        return Pattern.matches(PASSWORD_REGEX2, password);
    }
}
