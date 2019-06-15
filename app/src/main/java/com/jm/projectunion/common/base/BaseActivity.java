package com.jm.projectunion.common.base;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.jm.projectunion.R;
import com.jm.projectunion.common.eventbus.ErrorEvent;
import com.jm.projectunion.common.eventbus.ExitAppEvent;
import com.jm.projectunion.common.interf.IBaseActivity;
import com.jm.projectunion.common.manager.AppManager;
import com.jm.projectunion.common.manager.Config;
import com.jm.projectunion.common.utils.DialogUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.common.widget.SystemBarTintManager;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 基类
 *
 * @author Young
 * @date 2015-5-5 上午10:06:19
 */
public abstract class BaseActivity extends AutoLayoutActivity implements
        OnClickListener, IBaseActivity {
    private static final String NO_LOGIN = "109";
    private Dialog mLoadingDialog;
    protected SystemBarTintManager tintManager;
    public boolean mStatusBarHeight = true;
    public Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFullScreen()) {
            Window window = getWindow();
            //隐藏标题栏
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            //隐藏状态栏
            //定义全屏参数
            int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
            //设置当前窗体为全屏显示
            window.setFlags(flag, flag);
        }
        setStatusBarColor();
        onBeforeSetContentLayout();
        if (getLayoutResId() != 0) {
            setContentView(getLayoutResId());
        }
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        onAfterSetContentLayout();
        ButterKnife.bind(this);
        mIntent = getIntent();
        initView();
        initData();
        registerEventBus();
        AppManager.getInstance().addActivity(this);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            if (isFullScreen()) {
                tintManager.setStatusBarTintColor(getResources().getColor(R.color.translation));
            } else {
                tintManager.setStatusBarTintColor(getResources().getColor(R.color.black));
            }
        }
    }

    /**
     * 注册EventBus通信组件
     */
    protected void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    /**
     * 取消注册EventBus通信组件
     */
    protected void unRegisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    /**
     * 设置布局前要进行的操作
     */
    protected void onBeforeSetContentLayout() {
    }

    /**
     * 设置布局后要进行的操作
     */
    protected void onAfterSetContentLayout() {

    }

    /**
     * 资源文件Layout ID
     */
    protected int getLayoutResId() {
        return 0;
    }

    /**
     * 是否全屏
     *
     * @return
     */
    protected boolean isFullScreen() {
        return false;
    }

    /**
     * 加载中效果
     */
    public void showDialogLoading() {
        mLoadingDialog = DialogUtils.showLoading(this);
    }

    /**
     * 加载中效果
     */
    public void showDialogLoading(boolean hasbg) {
//        mLoadingDialog = DialogUtils.showLoading(this,hasbg);
    }

    /**
     * 加载中效果
     *
     * @param msg 提示信息
     */
    public void showDialogLoading(String msg) {
        mLoadingDialog = DialogUtils.showLoading(this, msg);
    }

    /**
     * 隐藏遮罩的dialog
     */
    public void hideDialogLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * 退出应用
     */
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onMessageEvent(ExitAppEvent exitApp) {
        if (exitApp.isExit()) {
            finish();
        }
    }

    /**
     * 退出应用
     */
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onMessageEvent(ErrorEvent event) {
        if (event.getContext().equals(this)) {
            onFailure(event.getStatus(), event.getMsg());
        }
    }

    /**
     * 网络请求或请求数据失败的信息提示
     *
     * @param status
     * @param message
     */
    protected void onFailure(String status, String message) {
        String errorMsgServer = Config.HTTP_ERROR_LIST.get(status);
        errorMsgServer = TextUtils.isEmpty(message) ? errorMsgServer : message;
        ToastUtils.showShort(this, errorMsgServer);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onDestroy() {
        AppManager.getInstance().finishActivity(this);
        super.onDestroy();
    }

    /**
     * 程序是否在前台运行
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * Toast提示信息
     *
     * @param message
     */
    public void showMsg(String message) {
        ToastUtils.showShort(this, message);
    }

    /**
     * Toast提示信息
     *
     * @param resId
     */
    public void showMsg(int resId) {
        ToastUtils.showShort(this, resId);
    }

    /**
     * 隐藏键盘
     */
    private InputMethodManager imm;

    protected void hintKb() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 点击空白区域隐藏键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
}