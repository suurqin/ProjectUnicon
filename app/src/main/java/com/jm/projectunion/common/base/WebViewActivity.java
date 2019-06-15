package com.jm.projectunion.common.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.jm.projectunion.R;

public class WebViewActivity extends BaseTitleActivity {

    public static final String WEB_TITLE = "web_title";
    public static final String HIDE_TITLE = "hide_title";
    public static final String WEB_URL = "web_url";

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private BaseWebFragment webFragment;


    protected String strTitle;
    protected String strUrl;
    protected boolean isHideTitle;
    @Override
    protected int getContentResId() {
        return R.layout.commom_activity_webview;
    }

    @Override
    public void initView() {
        mStatusBarHeight=false;
        fragmentManager = getSupportFragmentManager();
        Intent mIntent = getIntent();
        if (mIntent != null) {
            strTitle = mIntent.getStringExtra(WEB_TITLE);
            strUrl = mIntent.getStringExtra(WEB_URL);
            isHideTitle = mIntent.getBooleanExtra(HIDE_TITLE,false);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    @Override
    public void initData() {
        setTitleText("浏览器");
        if(isHideTitle){
            mTitleLayout.setVisibility(View.GONE);
        }
        commitFragment();
        setTitleText(strTitle);
    }

    private void commitFragment() {
        transaction = fragmentManager.beginTransaction();
        webFragment = BaseWebFragment.newInstance(strUrl);
        transaction.replace(R.id.common_webview, webFragment);
        transaction.commit();
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setStatusBarColor() {

    }

    @Override
    protected void baseGoBack() {
        if (webFragment.canGoBack()) {
            webFragment.goBack();// 返回前一个页面
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webFragment.canGoBack()) {
            webFragment.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
