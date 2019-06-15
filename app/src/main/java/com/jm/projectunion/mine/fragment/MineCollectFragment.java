package com.jm.projectunion.mine.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseListFragment;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.Result;
import com.jm.projectunion.friends.activity.FriendInfoActivity;
import com.jm.projectunion.home.activity.DetailsActivity;
import com.jm.projectunion.mine.adapter.MineCollectAdapter;
import com.jm.projectunion.mine.entity.CollectResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.List;

/**
 * Created by Young on 2017/11/7.
 */

public class MineCollectFragment extends BaseListFragment {
    private List<CollectResult.CollectBean> data;

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
        CollectResult.CollectBean bean = (CollectResult.CollectBean) data;
        Bundle bundle = new Bundle();
        if ("1".equals(bean.getType())) {
            bundle.putString("type", DetailsActivity.TYPE_BANNER);
        } else {
            bundle.putString("type", DetailsActivity.TYPE_INFO);
        }
        bundle.putString("id", bean.getContent());
        bundle.putString("atype", bean.getType());
        if (TextUtils.isEmpty(bean.getType())) {
            ToastUtils.showShort(getActivity(), "数据错误");
            return;
        }
        int atype = Integer.valueOf(bean.getType());
        if (atype >= 21 && atype <= 24) {
            UiGoto.startAtyWithBundle(getActivity(), FriendInfoActivity.class, bundle);
        } else {
            UiGoto.startAtyWithBundle(getActivity(), DetailsActivity.class, bundle);
        }
    }

    @Override
    public BaseAdapter createAdapter() {
        final MineCollectAdapter adapter = new MineCollectAdapter();
        adapter.setDelOnclicklistener(new MineCollectAdapter.OnDelClickListener() {
            @Override
            public void onDel(String id, final int position) {
                ApiClient.delCol(getActivity(), id, new ResultCallback<Result>() {
                    @Override
                    public void onSuccess(Result response) {
                        if ("0".equals(response.getCode())) {
                            data.remove(position);
                            adapter.replace(data);
                        }
                    }

                    @Override
                    public void onError(String msg) {

                    }
                });
            }
        });
        return adapter;
    }

    private void getData() {
        showDialogLoading();
        ApiClient.getCollect(getActivity(), new ResultCallback<CollectResult>() {
            @Override
            public void onSuccess(CollectResult response) {
                System.out.println("result-collect=" + response.toString());
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
}
