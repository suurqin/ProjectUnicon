package com.jm.projectunion.friends;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.jm.projectunion.MainFragmentAdapter;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseFragment;
import com.jm.projectunion.common.widget.NoSlideViewPager;
import com.jm.projectunion.friends.activity.FriendInfoActivity;
import com.jm.projectunion.friends.adapter.FriendsAdapter;
import com.jm.projectunion.friends.fragment.FriendGroupFragment;
import com.jm.projectunion.friends.fragment.FriendItemFragment;
import com.jm.projectunion.utils.GlobalVar;
import com.jm.projectunion.utils.UiGoto;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Young on 2017/10/26.
 */

public class FriendFragment extends BaseFragment {

    @BindView(R.id.friend_search)
    ImageView friend_search;
    @BindView(R.id.friend_name)
    EditText friend_name;
    @BindView(R.id.friend_item)
    Button friend_item;
    @BindView(R.id.group_item)
    Button group_item;
    @BindView(R.id.friends_group)
    NoSlideViewPager friends_group;

    private FriendItemFragment friendItemFragment;
    private FriendGroupFragment friendGroupFragment;

    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private MainFragmentAdapter adapter;

    @Override
    public void initView(View view) {
        friend_item.setOnClickListener(this);
        group_item.setOnClickListener(this);
        friend_search.setOnClickListener(this);
        friendItemFragment = new FriendItemFragment();
        friendGroupFragment = new FriendGroupFragment();

        mFragmentList.add(friendItemFragment);
        mFragmentList.add(friendGroupFragment);
        adapter = new MainFragmentAdapter(getChildFragmentManager(), mFragmentList);
        friends_group.setAdapter(adapter);
        friends_group.setCurrentItem(0);
        friend_item.setTextColor(getActivity().getResources().getColor(R.color.text_selected));
        friends_group.setOffscreenPageLimit(mFragmentList.size());

        friend_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (0 == friends_group.getCurrentItem()) {
                    friendItemFragment.filtData(s.toString());
                } else {
                    friendGroupFragment.filtData(s.toString());
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_friends;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        friend_item.setTextColor(getActivity().getResources().getColor(R.color.text_normal));
        group_item.setTextColor(getActivity().getResources().getColor(R.color.text_normal));
        switch (v.getId()) {
            case R.id.friend_search:
                break;
            case R.id.friend_item:
                friend_item.setTextColor(getActivity().getResources().getColor(R.color.text_selected));
                friends_group.setCurrentItem(0);
                break;
            case R.id.group_item:
                group_item.setTextColor(getActivity().getResources().getColor(R.color.text_selected));
                friends_group.setCurrentItem(1);
                break;
        }
    }
}
