package com.jm.projectunion.common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by Young on 2016/12/26.
 */

public class TextViewUtils {

    public static final String COL_898989 = "#898989";
    public static final String COL_F1625C = "#F1625C";

    private static Drawable getDrawable(Context context, int drawableId) {
        Drawable nav_up = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            nav_up = context.getDrawable(drawableId);
        } else {
            nav_up = context.getResources().getDrawable(drawableId);
        }
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        return nav_up;
    }

    /**
     * textview 设置左边的drawable
     *
     * @param context
     * @param textView
     * @param drawableId
     */
    public static void setTextViewDrawableLeft(Context context, TextView textView, int drawableId) {
        Drawable nav_up = getDrawable(context, drawableId);
        textView.setCompoundDrawables(nav_up, null, null, null);
    }

    /**
     * textview 设置上面的drawable
     *
     * @param context
     * @param textView
     * @param drawableId
     */
    public static void setTextViewDrawableTop(Context context, TextView textView, int drawableId) {
        Drawable nav_up = getDrawable(context, drawableId);
        textView.setCompoundDrawables(null, nav_up, null, null);
    }

    /**
     * textview设置右边的drawable
     *
     * @param context
     * @param textView
     * @param drawableId
     */
    public static void setTextViewDrawableRight(Context context, TextView textView, int drawableId) {
        Drawable nav_up = getDrawable(context, drawableId);
        textView.setCompoundDrawables(null, null, nav_up, null);
    }

    /**
     * textview 设置下面的drawable
     *
     * @param context
     * @param textView
     * @param drawableId
     */
    public static void setTextViewDrawableBottom(Context context, TextView textView, int drawableId) {
        Drawable nav_up = getDrawable(context, drawableId);
        textView.setCompoundDrawables(null, null, null, nav_up);
    }

    public static int getRealTextSize(Context context, int uiSize) {
        int resSize = 0;
        int screenWidth = DensityUtils.getScreenWidth(context);
        float precent = (float) uiSize / (float) getUIWidth(context);
        resSize = (int) (precent * screenWidth);
        return resSize;
    }

    public static int getRealHeight(Context context, int uiSize) {
        int resSize = 0;
        int screenHeight = DensityUtils.getScreenHeight(context);
        float precent = (float) uiSize / (float) getUIHeight(context);
        resSize = (int) (precent * screenHeight);
        return resSize;
    }

    private static int getUIWidth(Context context) {
        int resWidth = 0;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            resWidth = appInfo.metaData.getInt("design_width");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return resWidth;
    }

    private static int getUIHeight(Context context) {
        int resWidth = 0;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            resWidth = appInfo.metaData.getInt("design_height");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return resWidth;
    }
}
