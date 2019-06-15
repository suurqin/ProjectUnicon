package com.jm.projectunion.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jm.projectunion.R;
import com.jm.projectunion.common.cache.HttpCache;
import com.jm.projectunion.common.eventbus.ErrorEvent;
import com.jm.projectunion.common.interf.IBaseFragment;
import com.jm.projectunion.common.manager.Config;
import com.jm.projectunion.common.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Fragment基类
 *
 * @author Young
 * @date 2015年6月8日 下午3:59:05
 */
public abstract class BaseFragment extends Fragment implements
        OnClickListener, IBaseFragment {
    protected boolean isPrepared = false;

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
    public View mLoadingLayout, mLoadingFailedLayout, mBaseContent;

    /**
     * 加载失败后的刷新数据
     */
    private Button mLoadingFailedRefresh;
    /**
     * 遮罩层显示加载中的Dialog
     */
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.base_loading_content, container,
                false);
        LinearLayout llContent = (LinearLayout) mView
                .findViewById(R.id.base_loading_content);
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResId(), null);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        llContent.addView(rootView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        if (!EventBus.getDefault().isRegistered(this)) {
            registerEventBus();
        }
        ButterKnife.bind(getActivity());
        return mView;
    }

    protected void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    protected void unRegisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        unRegisterEventBus();
        super.onDestroy();
    }

    /**
     * 显示加载中的Dialog
     */
    public void showDialogLoading() {
        showDialogLoading(null);
    }

    /**
     * 显示加载中遮罩层Dialog，带消息提示
     *
     * @param msg
     */
    public void showDialogLoading(String msg) {
        FragmentActivity activity = getActivity();
        if (getActivity() != null) {
            if (activity instanceof BaseActivity) {
                if (TextUtils.isEmpty(msg)) {
                    ((BaseActivity) activity).showDialogLoading();
                } else {
                    ((BaseActivity) activity).showDialogLoading(msg);
                }
            } else if (activity instanceof BaseMainActivity) {
                if (TextUtils.isEmpty(msg)) {
                    ((BaseMainActivity) activity).showDialogLoading();
                } else {
                    ((BaseMainActivity) activity).showDialogLoading(msg);
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onMessageEvent(ErrorEvent event) {
        String status = event.getStatus();
        String message = event.getMsg();
        if (event.getContext().equals(getActivity())) {
            onErrorMsg(status, message);
        }
    }


    /**
     * 网络请求或请求数据失败的信息提示
     *
     * @param status
     * @param message
     */
    protected void onErrorMsg(String status, String message) {
        if (status == Config.HTTP_IO_ERROR || status == Config.HTTP_NET_ERROR
                || status == Config.HTTP_PARSER_ERROR) {
            onShowFailed();
        }
    }

    /**
     * 隐藏遮罩层
     */
    public void hideDialogLoading() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            if (activity instanceof BaseActivity) {
                ((BaseActivity) activity).hideDialogLoading();
            } else if (activity instanceof BaseMainActivity) {
                ((BaseMainActivity) activity).hideDialogLoading();
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 布局资源文件
     *
     * @return
     */
    protected abstract int getLayoutResId();


    /**
     * 刷新数据
     */
    protected void onForceRefresh() {
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mLoadingLayout = view
                .findViewById(R.id.base_loading_layout);
        ImageView im = (ImageView) mLoadingLayout.findViewById(R.id.base_loading_im);
//        Glide.with(this).load(R.drawable.loading_red).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(im);
        mLoadingFailedLayout = view
                .findViewById(R.id.base_loading_failed_layout);
        mBaseContent = view
                .findViewById(R.id.base_loading_content);
        mLoadingFailedRefresh = (Button) view
                .findViewById(R.id.base_loading_failed_refresh);
        mLoadingFailedRefresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onForceRefresh();
            }
        });
        isPrepared = true;
        initView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
//        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
    }

    /**
     * Toast提示信息
     *
     * @param message
     */
    public void showMsg(String message) {
        ToastUtils.showShort(getActivity(), message);
    }

    /**
     * Toast提示信息
     *
     * @param resId
     */
    public void showMsg(int resId) {
        ToastUtils.showShort(getActivity(), resId);
    }

    /**
     * 缓存数据
     */
    protected void saveCacheData(String account, String key, String info) {
        if (account != null) {
            HttpCache.getUserCacheClient(account).put(account + key, info);
        }
    }
}
