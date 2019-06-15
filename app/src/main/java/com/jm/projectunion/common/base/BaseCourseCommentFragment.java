package com.jm.projectunion.common.base;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jm.projectunion.R;
import com.jm.projectunion.common.eventbus.ListLoadErrorEvent;
import com.jm.projectunion.common.manager.Config;
import com.jm.projectunion.common.utils.StringUtils;
import com.jm.projectunion.common.widget.SpaceItemDecoration;

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
public abstract class BaseCourseCommentFragment<T> extends BaseFragment {
    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    @BindView(R.id.pull_refresh_empty_layout)
    View mEmptyView;
    @BindView(R.id.pull_refresh_empty_text)
    TextView mEmptyText;
    @BindView(R.id.pull_refresh_empty_image)
    ImageView mEmptyImage;


    @BindView(R.id.final_exam)
    LinearLayout mFinalExam;
    @BindView(R.id.comment_content)
    Button mCommentContent;
//    @BindView(R.id.send_comment)
//    TextView mSendComment;

    /**
     * 数据源
     */
    protected BaseAdapter<T> mAdapter;
    protected int mCurrentPage = 1;
    protected View mHeader;
    protected int mHeaderHeight;

    /**
     * 分页数量
     */
    public int PAGE_SIZE = Config.PAGE_SIZE;

    /**
     * 设置终极考试的背景色
     *
     * @param isTrue true:颜色可点击   false:灰色不可点击
     */
    public void setFinalExamBankgraoud(boolean isTrue) {
        if (isTrue) {
            mFinalExam.setClickable(true);
            mFinalExam.setBackgroundColor(Color.parseColor("#2EA7E0"));
        } else {
            mFinalExam.setClickable(false);
            mFinalExam.setBackgroundColor(Color.parseColor("#a0a0a0"));
        }
    }

    @Override
    public void initView(View view) {
//        mFinalExam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onFianlExamClick();
//            }
//        });
//        mCommentContent.setInputType(InputType.TYPE_NULL);
        mCommentContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCommentContentClick();
            }
        });

//        mSendComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "发送", Toast.LENGTH_SHORT).show();
//            }
//        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerView.setArrowImageView(R.drawable.zre_icon_downgrey);
        mRecyclerView.setLoadingMoreEnabled(isLoadMore());
        mRecyclerView.setPullRefreshEnabled(isRefresh());
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

    }

    protected abstract void onCommentContentClick();

    protected abstract void onFianlExamClick();

    protected abstract void onLoadMoreDate();

    protected abstract void onRefreshDate();

    protected View getEmptyView() {
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
        Config.NOTICE_CLASS = StringUtils.getClassName(this.getClass());
        onShowLoading();
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
            mEmptyView.setVisibility(View.VISIBLE);
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
        mHeader = view;
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
        return R.layout.base_coursecomment_recyclerview;
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

    protected int setLineSpace() {
        return 1;
    }
}
