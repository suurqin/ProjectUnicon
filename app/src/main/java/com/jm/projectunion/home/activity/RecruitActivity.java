package com.jm.projectunion.home.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.home.HomeFragment;
import com.jm.projectunion.home.fragment.RecruitFragment;
import com.jm.projectunion.utils.UiGoto;

/**
 * Created by Young on 2017/10/30.
 */

public class RecruitActivity extends SingleFragmentActivity {

    private String fileName = "category_";
    private int articleType;
    private AreaDaoUtil daoUtil;

    private RecruitFragment recruitFragment;

    @Override
    public void initView() {
        daoUtil = new AreaDaoUtil(this);
        String type = getIntent().getStringExtra(UiGoto.HOME_TYPE);
        fileName = fileName + type + ".txt";
        switch (type) {
            case HomeFragment.ITEM_TITLE_RECRUIT:
                setTitleText("招工");
                articleType = 4;
                break;
            case HomeFragment.ITEM_TITLE_MIGRANT_WORKER:
                setTitleText("农民工之家");
                articleType = 11;
                break;
            case HomeFragment.ITEM_TITLE_WORKER:
                setTitleText("找队伍");
                articleType = 21;
                break;
            case HomeFragment.ITEM_TITLE_PERSONNEL:
                setTitleText("找人才");
                articleType = 22;
                break;
            case HomeFragment.ITEM_TITLE_RESOURCE:
                setTitleText("找材料");
                articleType = 23;
                break;
            case HomeFragment.ITEM_TITLE_MACHINE:
                setTitleText("找机械");
                articleType = 24;
                break;
            case HomeFragment.ITEM_TITLE_ENTERPRISE:
                setTitleText("企业专区");
                articleType = 31;
                break;
        }
    }

    @Override
    public Fragment createFragment() {
        recruitFragment = RecruitFragment.newInstance(fileName, articleType);
        return recruitFragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == resultCode && null != data) {
            String city = data.getStringExtra("city");//province
//            String cityId = data.getStringExtra("cityIds");
            String cityId = String.valueOf(daoUtil.queryByName(city).getAreaId());
            recruitFragment.setArea(city, cityId);
        }
    }
}
