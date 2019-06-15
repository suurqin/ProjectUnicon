package com.jm.projectunion.common.base;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.RefreshHeaderFactory;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jm.projectunion.R;
import com.jm.projectunion.common.eventbus.ListLoadErrorEvent;
import com.jm.projectunion.common.manager.Config;
import com.jm.projectunion.common.utils.StringUtils;
import com.jm.projectunion.common.widget.SpaceItemDecoration;
import com.zhy.autolayout.AutoFrameLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * ListFragment基类
 *
 * @author Young
 * @date 2015-12-6 下午6:03:37
 */
public abstract class BaseListFragment<T> extends BaseFragment {
    @BindView(R.id.recyclerview)
    public XRecyclerView mRecyclerView;
    @BindView(R.id.pull_refresh_empty_layout)
    AutoFrameLayout mEmptyView;
    @BindView(R.id.pull_refresh_empty_text)
    TextView mEmptyText;
    @BindView(R.id.pull_refresh_empty_image)
    ImageView mEmptyImage;
    /**
     * 数据源
     */
    protected BaseAdapter<T> mAdapter;
    protected int mCurrentPage = 1;
    protected View mHeader;
    protected View mFoot;
    protected int mHeaderHeight;

    /**
     * 分页数量
     */
    public int PAGE_SIZE = Config.PAGE_SIZE;

    @Override
    public void initView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingMoreEnabled(isLoadMore());
        mRecyclerView.setPullRefreshEnabled(isRefresh());
        mRecyclerView.setRefreshStyle(getRefreshStyle());
//        mRecyclerView.setArrowImageView(R.drawable.zre_icon_downgrey);
        int spacingInPixels = setLineSpace();
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        mAdapter = createAdapter();
        mAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                onRecyclerItemClick(view, data);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (mRecyclerView == null) {
                            return;
                        }
                        mCurrentPage = 1;
                        onRefreshDate();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (mRecyclerView == null) {
                            return;
                        }
                        onLoadMoreDate();
                    }
                }, 1000);
            }
        });
        onShowLoading();
    }

    protected abstract void onLoadMoreDate();

    protected abstract void onRefreshDate();

    protected FrameLayout getEmptyView() {
        return mEmptyView;
    }

    protected void setEmptyText(int resId) {
        mEmptyText.setText(resId);
    }

    protected abstract void onRecyclerItemClick(View view, Object data);

    @Override
    public void initData() {
        if (getRecyclerHeader() != null) {
            mRecyclerView.addHeaderView(getRecyclerHeader());
        }
        if (getRecyclerFoot() != null) {
            mRecyclerView.addFootView(getRecyclerFoot());
        }
        Config.NOTICE_CLASS = StringUtils.getClassName(this.getClass());
    }

    protected void setPageSize(int pageSize) {
        PAGE_SIZE = pageSize;
    }

    protected int getPageSize() {
        return PAGE_SIZE;
    }

    /**
     * 设置数据源
     *
     * @param list
     */
    protected void setDataResult(List<T> list) {
        if (mRecyclerView == null) {
            return;
        }
        onShowContent();
        hideDialogLoading();
        if (mCurrentPage == 1) {
            mAdapter.removeAll();
            if (isRefresh()) {
                if (mRecyclerView == null) {
                    return;
                }
                mRecyclerView.refreshComplete();
            }
        }
        if (list != null && list.size() > 0) {
            mAdapter.append(list);
            mCurrentPage += 1;
            if (list.size() < getPageSize()) {
                mRecyclerView.refreshComplete();
            }
        } else {
            mRecyclerView.refreshComplete();
        }
        if (isLoadMore()) {
            mRecyclerView.loadMoreComplete();
        }
        if (mAdapter.getItemCount() == 0 && getRecyclerHeader() == null) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else if (mAdapter.getItemCount() == 0 && getRecyclerHeader() != null) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mEmptyView.getLayoutParams();
            params.setMargins(0, getRecyclerHeaderHeight(), 0, 0);
            mEmptyView.setLayoutParams(params);
            mEmptyView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onMessageEvent(ListLoadErrorEvent event) {
        if (mRecyclerView != null) {
            mRecyclerView.loadMoreErrorComplete();
        }
    }

    /**
     * 获取ListRecycler
     *
     * @return
     */
    public XRecyclerView getListView() {
        return mRecyclerView;
    }

    /**
     * 获取RecyclerHeader
     *
     * @return
     */
    public View getRecyclerHeader() {
        return mHeader;
    }

    /**
     * 获取RecyclerHeader
     *
     * @return
     */
    public void setRecyclerHeader(View view) {
        mHeader = view;
    }

    /**
     * 获取RecyclerHeader
     *
     * @return
     */
    public View getRecyclerFoot() {
        return mFoot;
    }

    /**
     * 获取RecyclerHeader
     *
     * @return
     */
    public void setRecyclerFoot(View view) {
        mFoot = view;
    }

    /**
     * 获取RecyclerHeader高度
     *
     * @return
     */
    public void setRecyclerHeaderHeight(int height) {
        mHeaderHeight = height;
    }


    public int getRecyclerHeaderHeight() {
        return mHeaderHeight;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.base_list_recyclerview;
    }


    @Override
    protected void onForceRefresh() {
        onShowLoading();
        super.onForceRefresh();

    }

    /**
     * 数据适配器
     *
     * @return
     */
    public abstract BaseAdapter<T> createAdapter();

    /**
     * 是否加载更多 默认支持加载更多
     *
     * @return true 是，false 否
     */
    protected boolean isLoadMore() {
        return true;
    }

    /**
     * 是否下拉刷新 默认为不支持
     *
     * @return true 是，false 否
     */
    protected boolean isRefresh() {
        return false;
    }

    protected int getRefreshStyle() {
        return RefreshHeaderFactory.HEADER_NORMAL;
    }

    protected int setLineSpace() {
        return 0;
    }
}
