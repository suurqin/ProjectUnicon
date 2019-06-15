package com.jm.projectunion.home.activity;


import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.home.fragment.EnterpriseFragment;

/**
 * Created by YangPan on 2017/10/29.
 */

public class EnterpriseActivity extends SingleFragmentActivity {

    private EnterpriseFragment enterpriseFragment;
    private AreaDaoUtil daoUtil;

    @Override
    public void initView() {
        setTitleText("企业专区");
        daoUtil = new AreaDaoUtil(this);
    }

    @Override
    public Fragment createFragment() {
        enterpriseFragment = new EnterpriseFragment();
        return enterpriseFragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == resultCode && null != data) {
            String city = data.getStringExtra("city");
//            String cityId = data.getStringExtra("cityIds");
            String cityId = String.valueOf(daoUtil.queryByName(city).getAreaId());
            enterpriseFragment.setArea(city, cityId);
        }

        if (200 == resultCode && null != data) {
            //Todo:: 获取行业类别
            String candos = data.getStringExtra("categoryIds");
            enterpriseFragment.setCategory(candos);
        }
    }
}
