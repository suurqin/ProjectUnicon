package com.jm.projectunion.home.fragment;

import android.os.Bundle;
import android.view.View;

import com.jm.projectunion.WebViewActivity;
import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseListFragment;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.home.activity.UserHelpDetailActivity;
import com.jm.projectunion.home.adapter.UseHelpAdapter;
import com.jm.projectunion.home.entiy.FuncListResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.List;

/**
 * Created by YangPan on 2017/10/29.
 */

public class UseHelpFragment extends BaseListFragment {

    private List<FuncListResult.FuncListBean> data;

    @Override
    public void initData() {
        super.initData();
        getData();
    }

    @Override
    protected boolean isLoadMore() {
        return false;
    }

    @Override
    protected void onLoadMoreDate() {

    }

    @Override
    protected void onRefreshDate() {

    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        FuncListResult.FuncListBean funcListBean = (FuncListResult.FuncListBean) data;
//        UiGoto.startAtyWithType(getActivity(), UserHelpDetailActivity.class, funcListBean.getFid());
        Bundle bundle = new Bundle();
        bundle.putString("url", "http://39.106.138.102:8092/h5/html/info.html?id=" + funcListBean.getFid());
        bundle.putString("title", funcListBean.getTitle());
        UiGoto.startAtyWithBundle(getActivity(), WebViewActivity.class, bundle);
    }

    @Override
    public BaseAdapter createAdapter() {
        return new UseHelpAdapter();
    }

    @Override
    protected int setLineSpace() {
        return 1;
    }

    private void getData() {
        showDialogLoading();
        ApiClient.getFuncList(getActivity(), new ResultCallback<FuncListResult>() {
            @Override
            public void onSuccess(FuncListResult response) {
                hideDialogLoading();
                System.out.println("result--help" + response.toString());
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        data = response.getData();
                        setDataResult(data);
                    }
                    ToastUtils.showShort(getActivity(), response.getMsg());
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(getActivity(), msg);
            }
        });
    }
}
