package com.jcodecraeer.xrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by jianghejie on 15/11/22.
 */
public class BaseRefreshHeader extends LinearLayout implements IBaseRefreshHeader {

    public BaseRefreshHeader(Context context) {
        super(context);
    }

    public BaseRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onMove(float delta) {

    }

    @Override
    public boolean releaseAction() {
        return false;
    }

    @Override
    public void refreshComplate() {

    }

    @Override
    public void setArrowImageView(int resid) {

    }

    @Override
    public void setProgressStyle(int style) {

    }

    public int getVisiableHeight() {
        return 0;
    }

    public void setVisiableHeight(int height) {

    }

    public int getState() {
        return 0;
    }

    public void setState(int state) {
    }

    public int getMeasureHeight() {
        return 0;
    }
}
