package com.jm.projectunion.home.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.home.HomeFragment;
import com.jm.projectunion.home.fragment.LawFragment;
import com.jm.projectunion.utils.UiGoto;

/**
 * Created by Young on 2017/10/27.
 */

public class LawActivity extends SingleFragmentActivity {

    private String fileName = "category_";
    private int atype;
    private LawFragment lawFragment;
    private AreaDaoUtil daoUtil;

    @Override
    public void initView() {
        daoUtil = new AreaDaoUtil(this);
        String type = getIntent().getStringExtra(UiGoto.HOME_TYPE);
        fileName = fileName + type + ".txt";
        switch (type) {
            case HomeFragment.ITEM_IMG_LAW:
                setTitleText("法律服务");
                atype = 2;
                break;
            case HomeFragment.ITEM_IMG_HOUSE:
                setTitleText("房屋租售");
                atype = 3;
                break;
            case HomeFragment.ITEM_IMG_BUSINESS_SERVICE:
                setTitleText("商业服务");
                atype = 5;
                break;
            case HomeFragment.ITEM_IMG_INSURANCE:
                setTitleText("保险服务");
                atype = 7;
                break;
            case HomeFragment.ITEM_IMG_BUILDING_USED:
                setTitleText("二手建材");
                atype = 6;
                break;
            case HomeFragment.ITEM_IMG_EQUIPMENT_USED:
                setTitleText("二手车设备");
                atype = 8;
                break;
            case HomeFragment.ITEM_IMG_ODDJOB:
                setTitleText("劳务市场");
                atype = 9;
                break;
            case HomeFragment.ITEM_IMG_ARCHITECT:
                setTitleText("建筑证件");
                atype = 10;
                break;
            case HomeFragment.ITEM_IMG_CONVENIENCE:
                    setTitleText("便民商家");
                atype = 12;
                break;
        }
    }

    @Override
    public Fragment createFragment() {
        lawFragment = LawFragment.newInstance(fileName, atype);
        return lawFragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == resultCode && null != data) {
            String city = data.getStringExtra("city");
//            String cityId = data.getStringExtra("cityIds");
            System.out.println("city==" + city);
            String cityId = String.valueOf(daoUtil.queryByName(city).getAreaId());
            lawFragment.setArea(city, cityId);
        }
    }
}
