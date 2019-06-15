package com.jm.projectunion.common.base;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jm.projectunion.R;
import com.jm.projectunion.common.eventbus.ListLoadErrorEvent;
import com.jm.projectunion.common.manager.Config;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * GridFragment基类
 *
 * @author Young
 * @date 2015-12-6 下午6:03:37
 */
public abstract class BaseGridFragment<T> extends BaseFragment{
    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    @BindView(R.id.pull_refresh_empty_layout)
    View mEmptyView;
    @BindView(R.id.pull_refresh_empty_text)
    TextView mEmptyText;
    @BindView(R.id.pull_refresh_empty_image)
    ImageView mEmptyImage;
    /**
     * 数据源
     */
    protected BaseAdapter<T> mAdapter;
    protected int mCurrentPage = 1;
    protected int mColumnNum = 2;
    protected int mPattern = 0;
    protected View mHeader;
    /**
     * 分页数量
     */
    public int PAGE_SIZE = Config.PAGE_SIZE;


    @Override
    public void initView(View view) {
        if(getGridPattern()==1){
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager( getColumnNum(), StaggeredGridLayoutManager.VERTICAL);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);
        }else{
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),getColumnNum());
            mRecyclerView.setLayoutManager(layoutManager);
        }
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerView.setArrowImageView(R.drawable.zre_icon_downgrey);

        mRecyclerView.setLoadingMoreEnabled(isLoadMore());
        mRecyclerView.setPullRefreshEnabled(isRefresh());

        if (getRecyclerHeader() != null) {
            mRecyclerView.addHeaderView(getRecyclerHeader());
        }
        mAdapter = createAdapter();
            mAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                onRecyclerItemClick(view,data);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        onRefreshDate();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            onLoadMoreDate();
                        }
                    }, 1000);
            }
        });

    }
    protected abstract void onLoadMoreDate();
    protected abstract void onRefreshDate();

    protected View getEmptyView() {
        return mEmptyView;
    }
    protected void setEmptyText(int resId){
        mEmptyText.setText(resId);
    }
    protected abstract void onRecyclerItemClick(View view, Object data);

    @Override
    public void initData() {
        onShowLoading();
    }

    protected void setPageSize(int pageSize) {
        PAGE_SIZE = pageSize;
    }

    protected int getPageSize() {
        return PAGE_SIZE;
    }
    public int getGridPattern() {
        return mPattern;
    }
    public void setGridPattern(int pattern) {
        mPattern=pattern;
    }

    public int getColumnNum() {
        return mColumnNum;
    }
    public void setColumnNum(int num) {
        mColumnNum=num;
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
    public RecyclerView getListView() {
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
        mHeader=view;
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.base_list_recyclerview;
    }


    @Override
    protected void onForceRefresh() {
        super.onForceRefresh();
        onShowLoading();
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
}
