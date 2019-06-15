package com.jm.projectunion.mine.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jm.projectunion.MainFragmentAdapter;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseExtraFragment;
import com.jm.projectunion.common.utils.TextViewUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.common.widget.NoSlideViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Young on 2017/11/8.
 */

public class ReleaseHisFragment extends BaseExtraFragment {

    @BindView(R.id.home)
    TextView home;
    @BindView(R.id.redpacket)
    TextView redpacket;
    @BindView(R.id.market)
    TextView market;
    @BindView(R.id.content)
    NoSlideViewPager content;

    private ArrayList<Fragment> fragmentList;

    @Override
    public void initView(View view) {

        home.setOnClickListener(this);
        redpacket.setOnClickListener(this);
        market.setOnClickListener(this);

        fragmentList = new ArrayList<>();
        ReleaseHomeHisListFragment releaseHomeHisListFragment = new ReleaseHomeHisListFragment();
        Bundle bundleHome = new Bundle();
        bundleHome.putString("type", ReleaseHomeHisListFragment.TYPE_HOME);
        releaseHomeHisListFragment.setArguments(bundleHome);

//        ReleaseRPHisListFragment releaseRPHisListFragment = new ReleaseRPHisListFragment();
        ReleaseHomeHisListFragment releaseRPHisListFragment = new ReleaseHomeHisListFragment();
        Bundle bundleRP = new Bundle();
        bundleRP.putString("type", ReleaseHomeHisListFragment.TYPE_RP);
        releaseRPHisListFragment.setArguments(bundleRP);


        ReleaseHomeHisListFragment releaseAllHisListFragment = new ReleaseHomeHisListFragment();
        Bundle bundleAll = new Bundle();
        bundleAll.putString("type", ReleaseHomeHisListFragment.TYPE_ALL);
        releaseAllHisListFragment.setArguments(bundleAll);

        fragmentList.add(releaseHomeHisListFragment);
        fragmentList.add(releaseRPHisListFragment);
        fragmentList.add(releaseAllHisListFragment);

        content.setAdapter(new MainFragmentAdapter(getChildFragmentManager(), fragmentList));
        content.setOffscreenPageLimit(fragmentList.size());
        content.setCurrentItem(0);
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_release_his;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        home.setTextColor(getActivity().getResources().getColor(R.color.text_normal));
        redpacket.setTextColor(getActivity().getResources().getColor(R.color.text_normal));
        market.setTextColor(getActivity().getResources().getColor(R.color.text_normal));
        switch (v.getId()) {
            case R.id.home:
                home.setTextColor(getActivity().getResources().getColor(R.color.text_selected));
                content.setCurrentItem(0);
                break;
            case R.id.redpacket:
                redpacket.setTextColor(getActivity().getResources().getColor(R.color.text_selected));
                content.setCurrentItem(1);
                break;
            case R.id.market:
                market.setTextColor(getActivity().getResources().getColor(R.color.text_selected));
                content.setCurrentItem(2);
                break;
        }
    }
}
