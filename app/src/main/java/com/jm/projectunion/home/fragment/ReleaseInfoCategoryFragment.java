package com.jm.projectunion.home.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.component.banner.BannerView;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseFragment;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.home.activity.InfoReleaseActivity;
import com.jm.projectunion.home.activity.ReleaseProjectActivity;
import com.jm.projectunion.home.activity.ReleaseRPActivity;
import com.jm.projectunion.home.adapter.InfoCategoryAdapter;
import com.jm.projectunion.mine.entity.UserInfoResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import butterknife.BindView;

/**
 * Created by Young on 2017/10/26.
 */

public class ReleaseInfoCategoryFragment extends BaseFragment {

    /**
     * 包含图片
     */
    public static final String ITEM_IMG_ABS = "1";//广告
    public static final String ITEM_IMG_LAW = "2";//法律
    public static final String ITEM_IMG_HOUSE = "3";//房屋租售
    public static final String ITEM_TITLE_RECRUIT = "4";//招聘
    public static final String ITEM_IMG_BUSINESS_SERVICE = "5";//商业服务
    public static final String ITEM_IMG_BUILDING_USED = "6";//二手建材
    public static final String ITEM_IMG_INSURANCE = "7";//保险服务
    public static final String ITEM_IMG_EQUIPMENT_USED = "8";//二手设备
    public static final String ITEM_IMG_ODDJOB = "9";//零活发布
    public static final String ITEM_IMG_ARCHITECT = "10";//建筑证件
    public static final String ITEM_TITLE_MIGRANT_WORKER = "11";//农民工之家
    public static final String ITEM_IMG_CONVENIENCE = "12";//便民商家


    @BindView(R.id.top_title)
    RelativeLayout top_title;
    @BindView(R.id.banner)
    BannerView bannerView;
    @BindView(R.id.category)
    GridView category;
    private UserInfoResult.UserInfoBean bean;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        top_title.setVisibility(View.GONE);
        bannerView.setVisibility(View.GONE);
//        ViewPager banner = new ViewPager(getActivity().getApplicationContext());
//        banner.setAdapter(new BannerAdapter(getActivity()));
//        bannerView.setBanner(banner, R.mipmap.banner_dot_nor, R.mipmap.banner_dot_sel, 3);

        category.setNumColumns(3);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) category.getLayoutParams();
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp.setMargins(80, 50, 80, 0);
        category.setAdapter(new InfoCategoryAdapter(getActivity().getApplicationContext()));

        category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (null == bean || TextUtils.isEmpty(bean.getRealname())) {
                    ToastUtils.showShort(getActivity(), "请先完善个人信息");
                    return;
                }
                switch (position) {
                    case 0://置顶广告
                        if (Integer.valueOf(PrefUtils.getInstance(getActivity()).getUserInfo().getVipType()) >= 1) {
                            UiGoto.startAtyWithType(getActivity(), InfoReleaseActivity.class, ITEM_IMG_ABS);
                        } else {
                            ToastUtils.showShort(getActivity(), "会员等级不够，请升级高级会员");
                        }
                        break;
                    case 1://法律服务
                        UiGoto.startAtyWithType(getActivity(), InfoReleaseActivity.class, ITEM_IMG_LAW);
                        break;
                    case 2://房租租售
                        UiGoto.startAtyWithType(getActivity(), InfoReleaseActivity.class, ITEM_IMG_HOUSE);
                        break;
                    case 3://招聘
                        UiGoto.startAtyWithType(getActivity(), InfoReleaseActivity.class, ITEM_TITLE_RECRUIT);
                        break;
                    case 4://商业服务
                        UiGoto.startAtyWithType(getActivity(), InfoReleaseActivity.class, ITEM_IMG_BUSINESS_SERVICE);
                        break;
                    case 5://二手建材
                        UiGoto.startAtyWithType(getActivity(), InfoReleaseActivity.class, ITEM_IMG_BUILDING_USED);
                        break;
                    case 6://保险服务
                        UiGoto.startAtyWithType(getActivity(), InfoReleaseActivity.class, ITEM_IMG_INSURANCE);
                        break;
                    case 7://二手设备
                        UiGoto.startAtyWithType(getActivity(), InfoReleaseActivity.class, ITEM_IMG_EQUIPMENT_USED);
                        break;
                    case 8://零活发布
                        UiGoto.startAtyWithType(getActivity(), InfoReleaseActivity.class, ITEM_IMG_ODDJOB);
                        break;
                    case 9://建筑证件
                        UiGoto.startAtyWithType(getActivity(), InfoReleaseActivity.class, ITEM_IMG_ARCHITECT);
                        break;
                    case 10://图文设计
                        UiGoto.startAtyWithType(getActivity(), InfoReleaseActivity.class, ITEM_TITLE_MIGRANT_WORKER);
                        break;
                    case 11://便民商家
                        UiGoto.startAtyWithType(getActivity(), InfoReleaseActivity.class, ITEM_IMG_CONVENIENCE);
                        break;
                    case 12://发布项目
                        UiGoto.startAty(getActivity(), ReleaseProjectActivity.class);
                        break;
                    case 13://发布红包
                        UiGoto.startAty(getActivity(), ReleaseRPActivity.class);
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        getData();
    }

    /**
     * 获取信息
     */
    private void getData() {
        ApiClient.getUserInfo(getActivity(), new ResultCallback<UserInfoResult>() {
            @Override
            public void onSuccess(UserInfoResult response) {
                System.out.println("result-user=" + response.toString());
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        bean = response.getData();
                    } else {
                        ToastUtils.showShort(getActivity(), response.getMsg());
                    }
                }
            }

            @Override
            public void onError(String msg) {
                ToastUtils.showShort(getActivity(), msg);
            }
        });
    }
}
