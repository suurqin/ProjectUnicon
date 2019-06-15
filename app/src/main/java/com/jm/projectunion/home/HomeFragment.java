package com.jm.projectunion.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.component.banner.BannerView;
import com.jm.projectunion.HCLocationActivity;
import com.jm.projectunion.MainActivity;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseFragment;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.home.activity.EnterpriseActivity;
import com.jm.projectunion.home.activity.LawActivity;
import com.jm.projectunion.home.activity.ProjectActivity;
import com.jm.projectunion.home.activity.RecruitActivity;
import com.jm.projectunion.home.activity.RedPacketActivity;
import com.jm.projectunion.home.activity.ReleaseInfoCategoryActivity;
import com.jm.projectunion.home.activity.ShareActivity;
import com.jm.projectunion.home.activity.UseHelpActivity;
import com.jm.projectunion.home.adapter.BannerAdapter;
import com.jm.projectunion.home.adapter.CategoryAdapter;
import com.jm.projectunion.home.entiy.BannerResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Young on 2017/10/26.
 */

public class HomeFragment extends BaseFragment {

    /**
     * 包含图片
     */
    public static final String ITEM_IMG_LAW = "law";//法律
    public static final String ITEM_IMG_HOUSE = "house";//房屋租售
    public static final String ITEM_IMG_BUSINESS_SERVICE = "business_service";//商业服务
    public static final String ITEM_IMG_INSURANCE = "insurance";//保险服务
    public static final String ITEM_IMG_BUILDING_USED = "building_used";//二手建材
    public static final String ITEM_IMG_EQUIPMENT_USED = "equipment_used";//二手设备
    public static final String ITEM_IMG_ODDJOB = "oddjob";//零活发布
    public static final String ITEM_IMG_ARCHITECT = "architect";//建筑证件
    public static final String ITEM_IMG_CONVENIENCE = "convenience";//便民商家

    /**
     * 不包含图片
     */
    public static final String ITEM_TITLE_RECRUIT = "recruit";//招聘
    public static final String ITEM_TITLE_MIGRANT_WORKER = "migrant_worker";//农民工之家
    public static final String ITEM_TITLE_WORKER = "worker";//找工人
    public static final String ITEM_TITLE_PERSONNEL = "personnel";//找人才
    public static final String ITEM_TITLE_RESOURCE = "resouce";//找材料
    public static final String ITEM_TITLE_MACHINE = "machine";//找机械
    public static final String ITEM_TITLE_ENTERPRISE = "enterprise";//企业专区
    public static final String ITEM_TITLE_PROJECT = "project";//项目专区

    @BindView(R.id.loc_parent)
    LinearLayout loc_parent;
    @BindView(R.id.top_title)
    RelativeLayout top_title;
    @BindView(R.id.cur_loc)
    TextView cur_location;
    @BindView(R.id.share)
    TextView share;
    @BindView(R.id.banner)
    BannerView bannerView;
    @BindView(R.id.category)
    GridView category;

    private PrefUtils prefUtils;
    private boolean isCanClick = true;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        prefUtils = PrefUtils.getInstance(getActivity());
        loc_parent.setOnClickListener(this);
        share.setOnClickListener(this);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) category.getLayoutParams();
        lp.setMargins(30, 0, 30, 0);
        category.setAdapter(new CategoryAdapter(getActivity()));
        category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                ToastUtils.showShort(getActivity().getApplicationContext(), position + "");
//                if (!isCanClick) {
//                    return;
//                }
//                isCanClick = false;
                switch (position) {
                    case 0://法律服务
                        UiGoto.startAtyWithType(getActivity(), LawActivity.class, ITEM_IMG_LAW);
                        break;
                    case 1://房租租售
                        UiGoto.startAtyWithType(getActivity(), LawActivity.class, ITEM_IMG_HOUSE);
                        break;
                    case 2://招聘
                        UiGoto.startAtyWithType(getActivity(), RecruitActivity.class, ITEM_TITLE_RECRUIT);
                        break;
                    case 3://商业服务
                        UiGoto.startAtyWithType(getActivity(), LawActivity.class, ITEM_IMG_BUSINESS_SERVICE);
                        break;
                    case 4://保险服务
                        UiGoto.startAtyWithType(getActivity(), LawActivity.class, ITEM_IMG_INSURANCE);
                        break;
                    case 5://二手建材
                        UiGoto.startAtyWithType(getActivity(), LawActivity.class, ITEM_IMG_ARCHITECT);
                        break;
                    case 6://二手设备
                        UiGoto.startAtyWithType(getActivity(), LawActivity.class, ITEM_IMG_EQUIPMENT_USED);
                        break;
                    case 7://零活发布
                        UiGoto.startAtyWithType(getActivity(), LawActivity.class, ITEM_IMG_ODDJOB);
                        break;
                    case 8://建筑证件
                        UiGoto.startAtyWithType(getActivity(), LawActivity.class, ITEM_IMG_BUILDING_USED);
                        break;
                    case 9://农民工之家
                        UiGoto.startAtyWithType(getActivity(), RecruitActivity.class, ITEM_TITLE_MIGRANT_WORKER);
                        break;
                    case 10://便民商家
                        UiGoto.startAtyWithType(getActivity(), LawActivity.class, ITEM_IMG_CONVENIENCE);

                        break;
                    case 11://找队伍
//                        if (Integer.valueOf(prefUtils.getUserInfo().getVipType()) >= 1) {
                        UiGoto.startAtyWithType(getActivity(), RecruitActivity.class, ITEM_TITLE_WORKER);
//                        } else {
//                            showToast("会员等级不够，请升级高级会员");
//                        }
                        break;

                    case 12://找人才
//                        if (Integer.valueOf(prefUtils.getUserInfo().getVipType()) >= 1) {
                        UiGoto.startAtyWithType(getActivity(), RecruitActivity.class, ITEM_TITLE_PERSONNEL);
//                        } else {
//                            showToast("会员等级不够，请升级高级会员");
//                        }
                        break;

                    case 13://企业专区

//                        showToast("暂无权限");
                        if (Integer.valueOf(prefUtils.getUserInfo().getVipType()) >= 1) {

                        //TODO wfx修改 临时关闭
                            UiGoto.startAty(getActivity(), EnterpriseActivity.class);
                            //UiGoto.startAtyWithType(getActivity(), RecruitActivity.class, ITEM_TITLE_ENTERPRISE);

                        } else {
                            showToast("会员等级不够，请升级企业会员");
                        }
                        break;

                    case 14://抢红包
                        UiGoto.startAty(getActivity(), RedPacketActivity.class);
                        break;
                    case 15://功能介绍
                        UiGoto.startAty(getActivity(), UseHelpActivity.class);
                        break;
                    case 16: //找机械
//                        if (Integer.valueOf(prefUtils.getUserInfo().getVipType()) >= 1) {
                        UiGoto.startAtyWithType(getActivity(), RecruitActivity.class, ITEM_TITLE_MACHINE);
//                        } else {
//                            showToast("会员等级不够，请升级高级会员");
//                        }

                        break;
                    case 17://找材料
//                        if (Integer.valueOf(prefUtils.getUserInfo().getVipType()) >= 1) {
                        UiGoto.startAtyWithType(getActivity(), RecruitActivity.class, ITEM_TITLE_RESOURCE);
//                        } else {
//                            showToast("会员等级不够，请升级高级会员");
//                        }
                        break;
                    case 18://项目专区

//                        showToast("暂无权限");
                        if (Integer.valueOf(prefUtils.getUserInfo().getVipType()) >= 1) {

                        //TODO wfx修改 临时关闭
                        UiGoto.startAty(getActivity(), ProjectActivity.class);


                        } else {
                            showToast("会员等级不够，请升级企业会员");
                        }
                        break;
                    case 19://发布
                        UiGoto.startAty(getActivity(), ReleaseInfoCategoryActivity.class);

                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        getBanner();

        //===============wfx 修改定位 start====================
        final String currentCity = prefUtils.getKeyLocationCity();
        if (!TextUtils.isEmpty(currentCity)) {
            setLocation(currentCity);
//            Toast.makeText(getContext(), "设置:"+currentCity, Toast.LENGTH_LONG).show();
        }
        //===============wfx 修改 end====================
    }

    @Override
    public void onResume() {
        super.onResume();
        isCanClick = true;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.loc_parent:
                Intent intent = new Intent(getActivity(), HCLocationActivity.class);
                getActivity().startActivityForResult(intent, 100);
                break;
            case R.id.share:
                UiGoto.startAty(getActivity(), ShareActivity.class);
                break;
        }
    }




    /**
     * 获取banner列表
     */
    private void getBanner() {
        ApiClient.getBanners(getActivity(), new ResultCallback<BannerResult>() {
            @Override
            public void onSuccess(BannerResult response) {
                System.out.println("result-banner==" + response.toString());
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        ViewPager banner = new ViewPager(getActivity().getApplicationContext());
                        banner.setAdapter(new BannerAdapter(getActivity(), response.getData(), true));
                        int size = (null == response.getData() ? 0 : response.getData().size());
                        bannerView.setBanner(banner, R.mipmap.banner_dot_nor, R.mipmap.banner_dot_sel, size);
                    }
                    ToastUtils.showShort(getActivity(), response.getMsg());
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(getActivity(), msg);
            }
        });
    }

    /**
     * 设置当前位置信息
     *
     * @param location
     */
    public void setLocation(String location) {
        if (null != cur_location) {
            cur_location.setText(location);
        }
    }

    private void showToast(String msg) {
        ToastUtils.showShort(getActivity(), msg);
    }
}
