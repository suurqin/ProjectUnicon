package com.jm.projectunion.home.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.component.banner.BannerView;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseExtraFragment;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.common.widget.NoSlideViewPager;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.home.activity.GrabRedPacketActivity;
import com.jm.projectunion.home.adapter.BannerAdapter;
import com.jm.projectunion.home.entiy.BannerResult;
import com.jm.projectunion.home.entiy.RedPacketResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Young on 2017/11/17.
 */

public class RedPDetailFragment extends BaseExtraFragment {

    private static final String RPID = "re_id";
    private static final String RP_STATUS = "re_status";

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.banner)
    BannerView bannerView;
    @BindView(R.id.count_down)
    TextView count_down;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.redpacket_add_friend)
    TextView redpacketAddFriend;
    @BindView(R.id.address)
    TextView address;

    private Handler mHandler;
    private long startTime = 0;
    private long duration;
    private Runnable mTicker;
    private int TEMP_TIME = 6000;
    private String rpId;
    private String rpStatu;
    private RedPacketResult.RedPacketBean bean;

    public static RedPDetailFragment newInstance(String rpId, String receiveStatu) {
        RedPDetailFragment fragment = new RedPDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(RPID, rpId);
        bundle.putString(RP_STATUS, receiveStatu);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            rpId = bundle.getString(RPID, "");
            rpStatu = bundle.getString(RP_STATUS, "");
        }
        count_down.setVisibility(View.INVISIBLE);
        phone.setOnClickListener(this);
        redpacketAddFriend.setOnClickListener(this);
        getData(rpId);
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_redp_detail;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.phone:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + bean.getPhone()));
                startActivity(intent);
                break;
            case R.id.redpacket_add_friend:
                showDialogLoading();
                ApiClient.addFriend(getActivity(),  bean.getPhone(), new ResultCallback<ResultData>() {
                    @Override
                    public void onSuccess(ResultData response) {
                        hideDialogLoading();
//                        System.out.println("result--addfriend=" + response.toString());
                        if (null != response) {
                            showToast(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        hideDialogLoading();
                        showToast(msg);
                    }
                });
                break;

        }
    }

    private void showToast(String msg) {
        ToastUtils.showShort(getActivity(), msg);
    }

    /**
     * 倒计时
     */
    public void countDown() {
        count_down.setVisibility(View.VISIBLE);
        mHandler = new Handler();
        mTicker = new Runnable() {
            public void run() {
                duration = System.currentTimeMillis() - startTime;
                if (duration >= TEMP_TIME) {
                    mHandler.removeCallbacks(mTicker);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("redPackage", bean);
                    bundle.putString("rpstatu", rpStatu);
                    UiGoto.startAtyWithBundle(getActivity(), GrabRedPacketActivity.class, bundle);
                    getActivity().finish();
                } else {
                    count_down.setText((int) ((TEMP_TIME - duration) / 1000f) + "s");
                    mHandler.postDelayed(mTicker, 1000);
                }
            }
        };
        startTime = System.currentTimeMillis();
        mHandler.post(mTicker);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mHandler && null != mTicker) {
            mHandler.removeCallbacks(mTicker);
        }
    }

    private void getData(String id) {
        showDialogLoading();
        ApiClient.getRPDetail(getActivity(), id, new ResultCallback<RedPacketResult>() {
            @Override
            public void onSuccess(RedPacketResult response) {
                hideDialogLoading();
                System.out.println("result--rpD=" + response.toString());
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        if ("0".equals(rpStatu)) {
                            countDown();
                        }
                        bean = response.getData();
                        phone.setText((TextUtils.isEmpty(bean.getPhone()) ? "" : bean.getPhone()));
                        address.setText("地址：" + bean.getAddr());

                        final List<String> desps = new ArrayList<>();
                        desps.add(bean.getImg1Desc());
                        desps.add(bean.getImg2Desc());
                        desps.add(bean.getImg3Desc());
                        desps.add(bean.getImg4Desc());
                        title.setText(desps.get(0));

                        List<BannerResult.Banner> banners = new ArrayList<>();
                        BannerResult.Banner banner1 = new BannerResult.Banner();
                        banner1.setPicId(bean.getRedPacketId());
                        banner1.setUrl(bean.getImg1());

                        BannerResult.Banner banner2 = new BannerResult.Banner();
                        banner2.setPicId(bean.getRedPacketId());
                        banner2.setUrl(bean.getImg2());

                        BannerResult.Banner banner3 = new BannerResult.Banner();
                        banner3.setPicId(bean.getRedPacketId());
                        banner3.setUrl(bean.getImg3());

                        BannerResult.Banner banner4 = new BannerResult.Banner();
                        banner4.setPicId(bean.getRedPacketId());
                        banner4.setUrl(bean.getImg4());

                        banners.add(banner1);
                        banners.add(banner2);
                        banners.add(banner3);
                        banners.add(banner4);

                        ViewPager banner = null;
                        if ("0".equals(rpStatu)) {
                            banner = new NoSlideViewPager(getActivity().getApplicationContext());
                        } else {
                            banner = new ViewPager(getActivity().getApplicationContext());
                        }
                        banner.setAdapter(new BannerAdapter(getActivity(), banners, false));
                        bannerView.setBanner(banner, R.mipmap.banner_dot_nor, R.mipmap.banner_dot_sel, banners.size(), 1500);
                        banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                title.setText(desps.get(position));
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
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
}
