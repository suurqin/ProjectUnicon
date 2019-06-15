package com.jm.projectunion.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ScreenUtils {
    private static int SCREEN_WIDTH = 0;
    private static int SCREEN_HEIGHT = 0;

    private ScreenUtils() {
    }

    public static float dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static float pxToDp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static int dpToPxInt(Context context, float dp) {
        return (int) (dpToPx(context, dp) + 0.5f);
    }

    public static int pxToDpInt(Context context, float px) {
        return (int) (pxToDp(context, px) + 0.5f);
    }
    public static int pxToDpInt(float px) {
        return (int) (pxToDp(px) + 0.5f);
    }

    public static int dpToPxInt(float dp) {
        return (int) (dpToPx(dp) + 0.5f);
    }

    /**
     * 将dp转换成px
     *
     * @param dp
     * @return
     */
    public static float dpToPx(float dp) {
        return dp * AppUtils.getAppContext().getResources().getDisplayMetrics().density;
    }

    /**
     * 将px转换成dp
     *
     * @param px
     * @return
     */
    public static float pxToDp(float px) {
        return px / AppUtils.getAppContext().getResources().getDisplayMetrics().density;
    }

    public static int getScreenWidth(Context context) {
        return getScreenWidth(context, false);
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        return AppUtils.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenWidth(Context context, boolean cached) {
        if (!cached && SCREEN_WIDTH > 0) return SCREEN_WIDTH;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        if (cached) {
            return dm.widthPixels;
        }
        SCREEN_WIDTH = dm.widthPixels;
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight(Context context) {
        return getScreenHeight(context, false);
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        return AppUtils.getAppContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenHeight(Context context, boolean cached) {
        if (!cached && SCREEN_HEIGHT > 0) return SCREEN_HEIGHT;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        if (cached) {
            return dm.heightPixels;
        }
        SCREEN_HEIGHT = dm.heightPixels;
        return SCREEN_HEIGHT;
    }

    /**
     * 获取通知栏高度
     *
     * @param context
     * @return
     */
    public static int getNotificationbarHeigth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) Math.ceil(25 * metrics.density);
    }

    /**
     * 截屏
     *
     * @param context
     * @return
     */
    public static Bitmap captureScreen(Activity context) {
        View cv = context.getWindow().getDecorView();
        Bitmap bmp = Bitmap.createBitmap(cv.getWidth(), cv.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        cv.draw(canvas);
        return bmp;
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static float sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (spValue * fontScale + 0.5f);
    }

    /**
     * 是否横屏
     *
     * @param context
     * @return
     */
    public static boolean isLandscapeOrientation(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }


    private static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }

        return hasNavigationBar;

    }

    //获取NavigationBar的高度
    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }


    public static void showSoftInput(Context context, View view) {
        if (view == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, 0);
    }

    public static void hideSoftInput(Context context, View view) {
        if (view == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void setTopbarTranslucent(Activity activity) {
        if (activity == null) {
            return;
        }
        //4.4 以上沉浸statusbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Window window = activity.getWindow();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    }
                    return;
                }
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                Field drawsSysBackgroundsField = WindowManager.LayoutParams.class.getField(
                        "FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS");
                activity.getWindow().addFlags(drawsSysBackgroundsField.getInt(null));
                Method setStatusBarColorMethod =
                        Window.class.getDeclaredMethod("setStatusBarColor", int.class);
            /*Method setNavigationBarColorMethod =
                    Window.class.getDeclaredMethod("setNavigationBarColor", int.class);*/
                setStatusBarColorMethod.invoke(window, Color.TRANSPARENT);
                // setNavigationBarColorMethod.invoke(getWindow(), Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
    }

    /**
     * 判断是否开启了自动亮度调节
     *
     * @param activity
     * @return
     */
    public static boolean isAutoBrightness(Activity activity) {
        boolean isAutoAdjustBright = false;
        try {
            isAutoAdjustBright = Settings.System.getInt(
                    activity.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return isAutoAdjustBright;
    }


    /**
     * 设置Activity的亮度
     *
     * @param paramInt
     * @param mActivity
     */
    public static void setScreenBrightness(int paramInt, Activity mActivity) {
        if (paramInt <= 5) {
            paramInt = 5;
        }
        Window localWindow = mActivity.getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        float f = paramInt / 100.0F;
        localLayoutParams.screenBrightness = f;
        localWindow.setAttributes(localLayoutParams);
    }

    /**
     * 开启亮度自动调节
     *
     * @param activity
     */

    public static void startAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }
    /**
     * 关闭亮度自动调节
     *
     * @param activity
     */
    public static void stopAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获得当前屏幕亮度值
     *
     * @param mContext
     * @return 0~100
     */
    public static float getScreenBrightness(Context mContext) {
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenBrightness / 255.0F * 100;
    }
}
