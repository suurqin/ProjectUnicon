package com.jm.projectunion.home.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.home.HomeFragment;
import com.jm.projectunion.home.fragment.ProjectFragment;
import com.jm.projectunion.home.fragment.RecruitFragment;
import com.jm.projectunion.utils.UiGoto;

/**
 * Created by Young on 2017/10/30.
 */

public class ProjectActivity extends SingleFragmentActivity {

    private ProjectFragment recruitFragment;
    private AreaDaoUtil daoUtil;

    @Override
    public void initView() {
        setTitleText("项目");
        daoUtil = new AreaDaoUtil(this);
    }

    @Override
    public Fragment createFragment() {
        recruitFragment = new ProjectFragment();
        return recruitFragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == resultCode && null != data) {
            String city = data.getStringExtra("city");
//            String cityId = data.getStringExtra("cityIds");
            String cityId = String.valueOf(daoUtil.queryByName(city).getAreaId());
            recruitFragment.setArea(city, cityId);
        }
    }
}
