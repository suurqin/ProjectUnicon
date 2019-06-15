package com.jm.projectunion.message.fragment;

import android.os.Bundle;
import android.view.View;

import com.jm.projectunion.MsgActivity;
import com.jm.projectunion.WebViewActivity;
import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseListFragment;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.message.activity.MsgDetailsAcitivy;
import com.jm.projectunion.message.adapter.MsgSystemAdapter;
import com.jm.projectunion.message.entity.MsgSystemResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

/**
 * Created by Young on 2017/11/8.
 */

public class MsgSystemListFragment extends BaseListFragment {

    private static final String MSG_TYPE = "msgType";

    private String msgType;

    public static MsgSystemListFragment newInstance(String msgType) {
        MsgSystemListFragment fragment = new MsgSystemListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MSG_TYPE, msgType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            msgType = bundle.getString(MSG_TYPE);
        }
        getMsg(msgType);
    }

    @Override
    protected void onLoadMoreDate() {

    }

    @Override
    protected void onRefreshDate() {

    }

    @Override
    protected boolean isLoadMore() {
        return false;
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        MsgSystemResult.MsgSystemBean bean = (MsgSystemResult.MsgSystemBean) data;
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("msg", bean);
//        UiGoto.startAtyWithBundle(getActivity(), MsgDetailsAcitivy.class, bundle);
        Bundle bundle = new Bundle();
        bundle.putString("url", "http://39.106.138.102:8092/h5/html/info.html?id=" + bean.getMsgId());
        bundle.putString("title", bean.getName());
        bundle.putString("content", bean.getContent());
        bundle.putString("ctime", bean.getCtime());
        UiGoto.startAtyWithBundle(getActivity(), MsgActivity.class, bundle);
    }

    @Override
    public BaseAdapter createAdapter() {
        return new MsgSystemAdapter();
    }

    @Override
    protected int setLineSpace() {
        return 2;
    }

    private void getMsg(String type) {
        showDialogLoading();
        ApiClient.getMessage(getActivity(), type, new ResultCallback<MsgSystemResult>() {
            @Override
            public void onSuccess(MsgSystemResult response) {
                hideDialogLoading();
                if (null != response) {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                    if ("0".equals(response.getCode())) {
                        setDataResult(response.getData());
                    }
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
