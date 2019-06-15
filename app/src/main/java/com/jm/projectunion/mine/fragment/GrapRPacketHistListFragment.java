package com.jm.projectunion.mine.fragment;

import android.os.Bundle;
import android.view.View;

import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseListFragment;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.Result;
import com.jm.projectunion.home.activity.RedPDetailActivity;
import com.jm.projectunion.home.entiy.InfoListResult;
import com.jm.projectunion.mine.adapter.IncomeDetailsAdapter;
import com.jm.projectunion.mine.adapter.RPHisAdapter;
import com.jm.projectunion.mine.entity.RPHisResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.List;

/**
 * Created by Young on 2017/11/8.
 */

public class GrapRPacketHistListFragment extends BaseListFragment {

    private List<RPHisResult.RPHisBean> data;
    private RPHisAdapter rpHisAdapter;

    @Override
    public void initData() {
        super.initData();
        getData();
    }

    @Override
    protected void onLoadMoreDate() {

    }

    @Override
    protected void onRefreshDate() {

    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        RPHisResult.RPHisBean bean = (RPHisResult.RPHisBean) data;
        Bundle bundle = new Bundle();
        bundle.putString("id", bean.getRedPacketId());
        bundle.putString("receiveStatu", "1");
        bundle.putString("title", bean.getTitle());
        UiGoto.startAtyWithBundle(getActivity(), RedPDetailActivity.class, bundle);
    }

    @Override
    public BaseAdapter createAdapter() {
        rpHisAdapter = new RPHisAdapter();
        rpHisAdapter.setOnItemDelListener(new RPHisAdapter.OnItemDelClickListener() {
            @Override
            public void onDel(int position) {
                delRpHis(position, data.get(position).getRedLogId());
            }
        });
        return rpHisAdapter;
    }

    /**
     * 获取数据
     */
    private void getData() {
        showDialogLoading();
        ApiClient.getRPHis(getActivity(), "1", new ResultCallback<RPHisResult>() {
            @Override
            public void onSuccess(RPHisResult response) {
                System.out.println("result--rphist=" + response.toString());
                hideDialogLoading();
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        data = response.getData();
                        setDataResult(data);
                    }
                }
                ToastUtils.showShort(getActivity(), response.getMsg());
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(getActivity(), msg);
            }
        });
    }

    private void delRpHis(final int position, String rpid) {
        ApiClient.delRPHis(getActivity(), rpid, new ResultCallback<Result>() {
            @Override
            public void onSuccess(Result response) {
                if ("0".equals(response.getCode())) {
                    data.remove(position);
                    rpHisAdapter.remove(position);
                }
                ToastUtils.showShort(getActivity(), response.getMsg());
            }

            @Override
            public void onError(String msg) {
                ToastUtils.showShort(getActivity(), msg);
            }
        });
    }
}
