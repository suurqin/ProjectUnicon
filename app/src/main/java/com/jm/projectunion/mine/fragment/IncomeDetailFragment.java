package com.jm.projectunion.mine.fragment;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseExtraFragment;
import com.jm.projectunion.common.utils.TextViewUtils;
import com.jm.projectunion.common.utils.ToastUtils;

import butterknife.BindView;

/**
 * Created by Young on 2017/11/8.
 */

public class IncomeDetailFragment extends BaseExtraFragment {

    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.income)
    TextView income;
    @BindView(R.id.pay)
    TextView pay;

    private IncomeDetailListFragment incomeDetailListFragment;

    @Override
    public void initView(View view) {

        all.setOnClickListener(this);
        income.setOnClickListener(this);
        pay.setOnClickListener(this);

        incomeDetailListFragment = new IncomeDetailListFragment();
        getChildFragmentManager().beginTransaction()
                .add(R.id.content, incomeDetailListFragment).commit();
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_income;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        all.setTextColor(getActivity().getResources().getColor(R.color.text_normal));
        income.setTextColor(getActivity().getResources().getColor(R.color.text_normal));
        pay.setTextColor(getActivity().getResources().getColor(R.color.text_normal));
        switch (v.getId()) {
            case R.id.all:
                all.setTextColor(getActivity().getResources().getColor(R.color.text_selected));
                incomeDetailListFragment.setData(0);
                break;
            case R.id.income:
                income.setTextColor(getActivity().getResources().getColor(R.color.text_selected));
                incomeDetailListFragment.setData(1);
                break;
            case R.id.pay:
                pay.setTextColor(getActivity().getResources().getColor(R.color.text_selected));
                incomeDetailListFragment.setData(2);
                break;
        }
    }
}
