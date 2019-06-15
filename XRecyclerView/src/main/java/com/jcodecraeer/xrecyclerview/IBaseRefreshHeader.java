package com.jcodecraeer.xrecyclerview;

/**
 * Created by jianghejie on 15/11/22.
 */
interface IBaseRefreshHeader {
    public void onMove(float delta);

    public boolean releaseAction();

    public void refreshComplate();

    void setArrowImageView(int resid);

    void setProgressStyle(int style);

    public final static int STATE_NORMAL = 0;
    public final static int STATE_RELEASE_TO_REFRESH = 1;
    public final static int STATE_REFRESHING = 2;
    public final static int STATE_DONE = 3;
}
