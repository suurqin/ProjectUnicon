package com.jm.projectunion.friends.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseExtraFragment;
import com.jm.projectunion.friends.activity.FriendInfoActivity;
import com.jm.projectunion.friends.adapter.FriendsAdapter;
import com.jm.projectunion.friends.bean.FriendBean;
import com.jm.projectunion.friends.bean.FriendRect;
import com.jm.projectunion.friends.bean.FriendsResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Young on 2017/11/29.
 */

public class FriendGroupFragment extends BaseExtraFragment {


    @BindView(R.id.friend_group)
    ExpandableListView friend_group;

    private List<FriendRect> recList;
    private FriendsAdapter adapter;

    @Override
    public void initView(View view) {
        friend_group.setGroupIndicator(null);
        adapter = new FriendsAdapter(getActivity());
        friend_group.setAdapter(adapter);
        friend_group.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                FriendBean bean = recList.get(groupPosition).getRecList().get(childPosition);
                Bundle bundle = new Bundle();
                bundle.putString("name", bean.getRealname());
                bundle.putString("id", bean.getUserId());
                UiGoto.startAtyWithBundle(getActivity(), FriendInfoActivity.class, bundle);
                return true;
            }
        });
    }

    @Override
    public void initData() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_friend_group;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        ApiClient.getFriends(getActivity(), "1", new ResultCallback<FriendsResult>() {
            @Override
            public void onSuccess(FriendsResult response) {
                System.out.println("result--friends==" + response.toString());
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        recList = response.getData().getRecList();
                        adapter.setData(recList);
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
        List<FriendRect> filterResult = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            filterResult = recList;
        } else {
            if (null != recList && recList.size() > 0) {
                filterResult.clear();
                for (FriendRect friendBean : recList) {
                    String name = friendBean.getRealname();
                    if (filterStr.equals(name)) {
                        filterResult.add(friendBean);
                    }
                    //刷二级列表
                    if (null != friendBean.getRecList()) {
                        for (FriendBean bean : friendBean.getRecList()) {
                            if (filterStr.equals(bean.getRealname())) {
                                FriendRect rect = new FriendRect();
                                rect.setAvatar(bean.getAvatar());
                                rect.setFriendNum(bean.getFriendNum());
                                rect.setLevel(bean.getLevel());
                                rect.setPhone(bean.getPhone());
                                rect.setRealname(bean.getRealname());
                                rect.setUserId(bean.getUserId());
                                rect.setRecNum1(bean.getRecNum1());
                                rect.setRecNum2(bean.getRecNum2());
                                filterResult.add(rect);
                            }
                        }
                    }
                }
            }
        }
        if (null != adapter) {
            adapter.setData(filterResult);
        }
    }
}
