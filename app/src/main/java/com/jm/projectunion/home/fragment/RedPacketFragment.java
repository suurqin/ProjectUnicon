package com.jm.projectunion.home.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseExtraFragment;

import butterknife.BindView;

/**
 * Created by YangPan on 2017/11/5.
 */

public class RedPacketFragment extends BaseExtraFragment {

    @BindView(R.id.rp_scope)
    LinearLayout rp_scope;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.country)
    TextView country;

    private RedPacketListFragment redPacketListFragment;

    @Override
    public void initView(View view) {
        redPacketListFragment = new RedPacketListFragment();
        getChildFragmentManager().beginTransaction()
                .add(R.id.content, redPacketListFragment).commit();
        city.setOnClickListener(this);
        country.setOnClickListener(this);
    }

    @Override
    public void initData() {
        city.setTextColor(getActivity().getResources().getColor(R.color.text_selected));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_redpacket;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        city.setTextColor(getActivity().getResources().getColor(R.color.text_normal));
        country.setTextColor(getActivity().getResources().getColor(R.color.text_normal));
        switch (v.getId()) {
            case R.id.city:
                city.setTextColor(getActivity().getResources().getColor(R.color.text_selected));
                redPacketListFragment.getDataFromOut("5");
                break;
            case R.id.country:
                country.setTextColor(getActivity().getResources().getColor(R.color.text_selected));
                redPacketListFragment.getDataFromOut("6");
                break;
        }
    }
}
