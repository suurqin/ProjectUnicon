package com.jm.projectunion.friends.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseListFragment;
import com.jm.projectunion.friends.activity.FriendInfoActivity;
import com.jm.projectunion.friends.adapter.FriendChildAdapter;
import com.jm.projectunion.friends.bean.FriendBean;
import com.jm.projectunion.friends.bean.FriendsResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Young on 2017/11/29.
 */

public class FriendItemFragment extends BaseListFragment {

    private List<FriendBean> friendList;

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCurrentPage = 1;
        getData();
    }

    @Override
    protected void onLoadMoreDate() {

    }

    @Override
    protected void onRefreshDate() {

    }

    @Override
    protected boolean isRefresh() {
        return false;
    }

    @Override
    protected boolean isLoadMore() {
        return false;
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        FriendBean bean = (FriendBean) data;
        Bundle bundle = new Bundle();
        bundle.putString("name", bean.getRealname());
        bundle.putString("id", bean.getUserId());
        UiGoto.startAtyWithBundle(getActivity(), FriendInfoActivity.class, bundle);
    }

    @Override
    public BaseAdapter createAdapter() {
        return new FriendChildAdapter();
    }

    private void getData() {
        ApiClient.getFriends(getActivity(), "0", new ResultCallback<FriendsResult>() {
            @Override
            public void onSuccess(FriendsResult response) {
                hideDialogLoading();
                //System.out.println("result--friends==" + response.toString());
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        friendList = response.getData().getFriendList();
                        if (null != friendList) {
                            setDataResult(friendList);
                        }
                    }
                }
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    /**
     * 数据筛选
     *
     * @param filterStr
     */
    public void filtData(String filterStr) {
        List<FriendBean> filterResult = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            filterResult = friendList;
        } else {
            if (null != friendList && friendList.size() > 0) {
                filterResult.clear();
                for (FriendBean friendBean : friendList) {
                    String name = friendBean.getRealname();
                    if (filterStr.equals(name)) {
                        filterResult.add(friendBean);
                    }
                }
            }
        }
        if (null != mAdapter) {
            mAdapter.replace(filterResult);
        }
    }
}
