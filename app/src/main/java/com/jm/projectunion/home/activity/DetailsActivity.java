package com.jm.projectunion.home.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.home.fragment.DetailsFragment;
import com.jm.projectunion.utils.GlobalVar;
import com.jm.projectunion.utils.UiGoto;

/**
 * Created by Young on 2017/10/31.
 */

public class DetailsActivity extends SingleFragmentActivity {

    public static final String TYPE_BANNER = "type_banner";
    public static final String TYPE_INFO = "type_info";

    private String type;
    private String infoId;
    private String atype;
    private String bannerType;

    @Override
    public void initView() {
        Bundle bundle = getIntent().getBundleExtra(UiGoto.BUNDLE);
        type = bundle.getString("type");
        infoId = bundle.getString("id");
        atype = bundle.getString("atype");
        bannerType = bundle.getString("bannerType");
        if (TYPE_BANNER.equals(type)) {
            setTitleText("首页置顶");
        } else if (TYPE_INFO.equals(type)) {
            setTitleText(GlobalVar.getCategoryName(atype));
        }
    }

    @Override
    public Fragment createFragment() {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("id", infoId);
        bundle.putString("atype", atype);
        bundle.putString("bannerType", bannerType);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }
}
