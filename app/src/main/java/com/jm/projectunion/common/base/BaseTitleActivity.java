package com.jm.projectunion.common.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.utils.UiHelpers;


/**
 * 带有标题的的基类
 *
 * @author Young
 * @date 2015-5-5 上午10:30:57
 */
public abstract class BaseTitleActivity extends BaseActivity {
    protected TextView mBaseTitle, mBaseEnsure;
    protected ImageView mBaseBack;
    public View mTitleLayout;
    public boolean mStatusBarHeight = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarSpace();
    }

    private void setStatusBarSpace() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View statusBar = findViewById(R.id.base_titlebar_status_padding);
//            LogUtils.i("tint" + tintManager.getConfig().getStatusBarHeight());
            if (mStatusBarHeight) {
                statusBar.setPadding(0, tintManager.getConfig()
                        .getStatusBarHeight(), 0, 0);
            }
        }
    }

    protected void onAfterSetContentLayout() {
        LinearLayout llContent = (LinearLayout) findViewById(R.id.base_titlebar_content);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(getContentResId(), null);
        llContent.addView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        baseInitView();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.base_titlebar_activity;
    }

    public View getTitleLayout() {
        return mTitleLayout;
    }

    private void baseInitView() {
        mTitleLayout = findViewById(R.id.base_titlebar_layout);
        mBaseTitle = (TextView) findViewById(R.id.base_titlebar_text);
        mBaseEnsure = (TextView) findViewById(R.id.base_titlebar_ensure);
        mBaseEnsure.setOnClickListener(this);
        mBaseBack = (ImageView) findViewById(R.id.base_titlebar_back);
        mBaseBack.setOnClickListener(this);
        mTitleLayout.setVisibility(View.VISIBLE);
        setBackImg();
    }

    /**
     * title返回图片按钮
     */
    protected void setBackImg() {
        mBaseTitle.setTextColor(getResources().getColor(R.color.text_normal));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_titlebar_back:
                baseGoBack();
                break;
            case R.id.base_titlebar_ensure:
                baseGoEnsure();
                break;
            default:
                break;
        }
        super.onClick(v);
    }

    /**
     * 设置标题文字
     *
     * @param title
     */
    public void setTitleText(String title) {
        mBaseTitle.setText(title);
    }

    /**
     * 设置标题文字
     *
     * @param resid
     */
    public void setTitleText(int resid) {
        mBaseTitle.setText(resid);
    }


    /**
     * 获取标题控件
     *
     * @return
     */
    public TextView getTitleTextView() {
        return mBaseTitle;
    }

    /**
     * 获取返回按钮
     *
     * @return
     */
    public ImageView getBackView() {
        return mBaseBack;
    }

    /**
     * 设置右侧文字
     *
     * @param text
     */
    public void setEnsureText(String text) {
        mBaseEnsure.setText(text);
    }

    /**
     * 设置右侧点击按钮图片
     *
     * @param resId
     */
    public void setEnsureDrawable(int resId) {
        UiHelpers.setTextViewIcon(this, mBaseEnsure, resId,
                R.dimen.zre_common_titlebar_right_icon_width,
                R.dimen.zre_common_titlebar_right_icon_height, UiHelpers.DRAWABLE_LEFT);
    }

    public void setEnsureText(int resid) {
        mBaseEnsure.setText(resid);
    }

    public TextView getEnsureView() {
        return mBaseEnsure;
    }

    public void setEnsureEnable(boolean flag) {
        mBaseEnsure.setClickable(flag);
        mBaseEnsure.setEnabled(flag);
    }

    /**
     * 左侧的按钮事件
     */
    protected void baseGoBack() {
        hintKb();
        finish();
    }

    /**
     * 右侧的按钮事件
     */
    protected void baseGoEnsure() {

    }

    /**
     * 手机自带的返回按键
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 布局文件
     *
     * @return
     */
    protected abstract int getContentResId();

    public void setViewVisible(boolean flag) {
        if (flag) {
            mTitleLayout.setVisibility(View.GONE);
        } else {
            mTitleLayout.setVisibility(View.VISIBLE);
        }
    }
}