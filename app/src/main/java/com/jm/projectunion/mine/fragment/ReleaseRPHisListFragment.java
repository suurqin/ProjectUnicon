package com.jm.projectunion.mine.fragment;

import android.view.View;

import com.jm.projectunion.common.base.BaseAdapter;
import com.jm.projectunion.common.base.BaseListFragment;
import com.jm.projectunion.home.adapter.RedPacketAdapter;

/**
 * Created by YangPan on 2017/11/5.
 */

public class ReleaseRPHisListFragment extends BaseListFragment {
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
    public BaseAdapter createAdapter() {
        return new RedPacketAdapter();
    }
}
