package com.jm.projectunion.common.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jm.projectunion.R;
import com.jm.projectunion.common.utils.UiHelpers;


/**
 * 带有标题和加载的的基类
 *
 * @author Young
 * @date 2015-5-5 上午10:30:57
 */
public abstract class BaseTitleLoadActivity extends BaseActivity {

    /**
     * 加载中
     */
    public static final int LOADING = 1;
    /**
     * 加载失败
     */
    public static final int LOADING_FAILED = 2;
    /**
     * 加载成功
     */
    public static final int LOADING_SUCCESS = 3;
    /**
     * 加载成功,无数据
     */
    public static final int LOADING_SUCCESS_NULL = 4;

    /**
     * 加载中布局，加载失败布局，加载成功显示数据布局
     */
    private View mLoadingLayout, mLoadingFailedLayout, mBaseContent;

    /**
     * 加载失败后的刷新数据
     */
    private Button mLoadingFailedRefresh;

    protected TextView mBaseTitle, mBaseEnsure, mBaseBack;
    public View mTitleLayout;
    public boolean mStatusBarHeight = true;
    public ImageView mCollection, mZan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarSpace();
    }

    private void setStatusBarSpace() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View statusBar = findViewById(R.id.base_titlebar_status_padding);
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
        return R.layout.base_titlebar_load_activity;
    }

    public View getTitleLayout() {
        return mTitleLayout;
    }

    private void baseInitView() {
        mTitleLayout = findViewById(R.id.base_titlebar_layout);
        mBaseTitle = (TextView) findViewById(R.id.base_titlebar_text);
        mBaseEnsure = (TextView) findViewById(R.id.base_titlebar_ensure);
        mBaseBack = (TextView) findViewById(R.id.base_titlebar_back);
        mCollection = (ImageView) findViewById(R.id.base_titlebar_collection);
        mZan = (ImageView) findViewById(R.id.base_titlebar_zan);
        mBaseEnsure.setOnClickListener(this);
        mBaseBack.setOnClickListener(this);
        mCollection.setOnClickListener(this);
        mZan.setOnClickListener(this);

        mLoadingLayout = findViewById(R.id.base_loading_layout);
        ImageView im = (ImageView) mLoadingLayout.findViewById(R.id.base_loading_im);
//        Glide.with(this).load(R.drawable.loading_red).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(im);
        mLoadingFailedLayout = findViewById(R.id.base_loading_failed_layout);
        mBaseContent = findViewById(R.id.base_titlebar_content);
        mLoadingFailedRefresh = (Button) findViewById(R.id.base_loading_failed_refresh);
        mLoadingFailedRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onForceRefresh();
            }
        });

    }

    /**
     * title返回图片按钮
     */
    protected void setBackImg() {
        // 初始化返回按钮图片大小
        mBaseTitle.setTextColor(getResources().getColor(R.color.black));
        mTitleLayout.setBackgroundColor(getResources().getColor(R.color.white));
//        UiHelpers.setTextViewIcon(this, mBaseBack, R.drawable.zre_ic_back,
//                R.dimen.zre_common_titlebar_icon_width,
//                R.dimen.zre_common_titlebar_icon_height, UiHelpers.DRAWABLE_LEFT);

    }

    /**
     * 显示加载失败布局
     */
    public void onShowFailed() {
        showView(LOADING_FAILED);
    }

    /**
     * 显示加载成功布局
     */
    public void onShowContent() {
        showView(LOADING_SUCCESS);
    }

    /**
     * 显示加载中布局
     */
    public void onShowLoading() {
        showView(LOADING);
    }


    /**
     * 判断显示的View
     *
     * @param index
     */
    private void showView(int index) {
        mLoadingLayout.setVisibility(index == LOADING ? View.VISIBLE
                : View.GONE);
        mLoadingFailedLayout
                .setVisibility(index == LOADING_FAILED ? View.VISIBLE
                        : View.GONE);
        mBaseContent.setVisibility(index == LOADING_SUCCESS ? View.VISIBLE
                : View.GONE);
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
            case R.id.base_titlebar_collection:
                baseGoCollection();
                break;
            case R.id.base_titlebar_zan:
                baseGoZan();
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

    /**
     * 左侧的按钮事件
     */
    protected void baseGoBack() {
        finish();
    }

    /**
     * 右侧的按钮事件
     */
    protected void baseGoEnsure() {

    }

    /**
     * 右侧的收藏事件
     */
    protected void baseGoCollection() {

    }

    /**
     * 右侧的点赞事件
     */
    protected void baseGoZan() {

    }

    /**
     * 布局文件
     *
     * @return
     */
    protected abstract int getContentResId();

    /**
     * 重新加载数据
     */
    protected void onForceRefresh() {
    }

    public void setViewVisible(boolean flag) {
        if (flag) {
            mTitleLayout.setVisibility(View.GONE);
        } else {
            mTitleLayout.setVisibility(View.VISIBLE);
        }
    }

    public void setEnsureVisible(boolean isShow) {
        if (isShow) {
            mBaseEnsure.setVisibility(View.VISIBLE);
        } else {
            mBaseEnsure.setVisibility(View.GONE);
        }
    }

    public void setCollectAndZanVisible(boolean isShow) {
        if (isShow) {
            mCollection.setVisibility(View.VISIBLE);
            mZan.setVisibility(View.VISIBLE);
            setEnsureVisible(false);
        } else {
            mCollection.setVisibility(View.GONE);
            mZan.setVisibility(View.GONE);
        }
    }

}