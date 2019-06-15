package com.jm.projectunion.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Young on 2017/10/30.
 */

public class UiGoto {

    //Bundle参数
    public static final String BUNDLE = "params";
    public static final String HOME_TYPE = "type";

    /**
     * 开启Activity
     * @param context
     * @param cls
     */
    public static void startAty(Context context, java.lang.Class cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    /**
     * 开启Activity
     */
    public static void startAtyWithBundle(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(BUNDLE, bundle);
        context.startActivity(intent);
    }

    /**
     * 开启Activity
     */
    public static void startAtyWithType(Context context, Class<?> cls, String type) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(HOME_TYPE, type);
        context.startActivity(intent);
    }
}
