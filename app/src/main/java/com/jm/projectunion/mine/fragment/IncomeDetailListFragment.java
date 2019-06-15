package com.jm.projectunion.mine.fragment;

import android.view.View;

import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseListFragment;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.mine.adapter.IncomeDetailsAdapter;
import com.jm.projectunion.mine.entity.IncomeListResult;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.List;

/**
 * Created by Young on 2017/11/8.
 */

public class IncomeDetailListFragment extends BaseListFragment {

    private IncomeDetailsAdapter adapter;
    private List<IncomeListResult.IncomeBean> data;

    @Override
    public void initData() {
        super.initData();
        getData("0");
    }

    @Override
    protected void onLoadMoreDate() {

    }

    @Override
    protected void onRefreshDate() {

    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {

    }

    @Override
    protected boolean isLoadMore() {
        return false;
    }

    @Override
    protected boolean isRefresh() {
        return false;
    }

    @Override
    public BaseAdapter createAdapter() {
        adapter = new IncomeDetailsAdapter();
        adapter.setOnItemDelListener(new IncomeDetailsAdapter.OnItemDelClickListener() {

            @Override
            public void onDel(IncomeListResult.IncomeBean data, int position) {
                delIncome(position, data.getAccountLogId());
            }
        });
        return adapter;
    }

    /**
     * 设置数据
     */
    public void setData(int type) {
        switch (type) {
            case 0:
                getData("0");
                break;
            case 1:
                getData("1");
                break;
            case 2:
                getData("2");
                break;
        }
    }

    private void getData(String type) {
        showDialogLoading();
        mCurrentPage = 1;
        ApiClient.getIncomeDetail(getActivity(), type, new ResultCallback<IncomeListResult>() {
            @Override
            public void onSuccess(IncomeListResult response) {
                System.out.println("result-income=" + response.toString());
                hideDialogLoading();
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

    /**
     * 删除收支
     *
     * @param id
     */
    private void delIncome(final int position, String id) {
        showDialogLoading();
        ApiClient.delIncomeItem(getActivity(), id, new ResultCallback<ResultData>() {
            @Override
            public void onSuccess(ResultData response) {
                hideDialogLoading();
                ToastUtils.showShort(getActivity(), response.getMsg());
                data.remove(position);
                adapter.replace(data);
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(getActivity(), msg);
            }
        });
    }
}
