package com.jm.projectunion.mine;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.jm.projectunion.FrogetPasswordActivity;
import com.jm.projectunion.LoginActivity;
import com.jm.projectunion.MyApplication;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseFragment;
import com.jm.projectunion.common.manager.Config;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.common.widget.CircleImageView;
import com.jm.projectunion.entity.LoginOrRegistResult;
import com.jm.projectunion.entity.Result;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.information.activity.UserInfoEditActivity;
import com.jm.projectunion.message.chat.EaseIMHelper;
import com.jm.projectunion.mine.activity.BalanceActivity;
import com.jm.projectunion.mine.activity.CashActivity;
import com.jm.projectunion.mine.activity.GrapRPacketHistActivity;
import com.jm.projectunion.mine.activity.IDentityActivity;
import com.jm.projectunion.mine.activity.IdCardAuthRelultActivity;
import com.jm.projectunion.mine.activity.LeaguerActivity;
import com.jm.projectunion.mine.activity.MineCollectActivity;
import com.jm.projectunion.mine.activity.ModifyPwActivity;
import com.jm.projectunion.mine.activity.RechargeActivity;
import com.jm.projectunion.mine.activity.ReleaseHisActivity;
import com.jm.projectunion.mine.entity.UserInfoResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import butterknife.BindView;

/**
 * Created by Young on 2017/10/26.
 */

public class MineFragment extends BaseFragment {

    @BindView(R.id.header_img)
    CircleImageView header_img;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.balance_num)
    TextView balance_num;
    @BindView(R.id.modify)
    LinearLayout modify;
    @BindView(R.id.upgrade)
    LinearLayout upgrade;
    @BindView(R.id.vip_name)
    TextView vip_name;
    @BindView(R.id.cash)
    Button cash;
    @BindView(R.id.recharge)
    Button recharge;
    @BindView(R.id.mine_collect)
    RelativeLayout mine_collect;
    @BindView(R.id.num_collect)
    TextView num_collect;
    @BindView(R.id.mine_redpacket)
    RelativeLayout mine_redpacket;
    @BindView(R.id.num_redpacket)
    TextView num_redpacket;
    @BindView(R.id.mine_release)
    RelativeLayout mine_release;
    @BindView(R.id.num_release)
    TextView num_release;
    @BindView(R.id.mine_modify_pay)
    RelativeLayout mine_modify_pay;
    @BindView(R.id.mine_modify_login)
    RelativeLayout mine_modify_login;
    @BindView(R.id.mine_bank)
    RelativeLayout mine_bank;
    @BindView(R.id.logout)
    Button logout;
    @BindView(R.id.commend_phone)
    TextView commend_phone;

    private PrefUtils prefUtils;
    private String vipTime;

    @Override
    public void initView(View view) {
        prefUtils = PrefUtils.getInstance(getActivity());
        modify.setOnClickListener(this);
        upgrade.setOnClickListener(this);
        cash.setOnClickListener(this);
        recharge.setOnClickListener(this);
        mine_collect.setOnClickListener(this);
        mine_redpacket.setOnClickListener(this);
        mine_release.setOnClickListener(this);
        mine_modify_pay.setOnClickListener(this);
        mine_modify_login.setOnClickListener(this);
        mine_bank.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.modify:
                UiGoto.startAty(getActivity(), UserInfoEditActivity.class);
                break;
            case R.id.upgrade:
                UiGoto.startAtyWithType(getActivity(), LeaguerActivity.class, vipTime);
                break;
            case R.id.cash://提现按钮
                isTrueCard();
//                UiGoto.startAty(getActivity(), CashActivity.class);

                break;
            case R.id.recharge:
                UiGoto.startAty(getActivity(), RechargeActivity.class);
                break;
            case R.id.mine_collect:
                UiGoto.startAty(getActivity(), MineCollectActivity.class);
                break;
            case R.id.mine_redpacket:
                UiGoto.startAty(getActivity(), GrapRPacketHistActivity.class);
                break;
            case R.id.mine_release:
                UiGoto.startAty(getActivity(), ReleaseHisActivity.class);
                break;
            case R.id.mine_modify_pay:
                UiGoto.startAtyWithType(getActivity(), FrogetPasswordActivity.class, FrogetPasswordActivity.PAY);
                break;
            case R.id.mine_modify_login:
                UiGoto.startAty(getActivity(), ModifyPwActivity.class);
                break;
            case R.id.mine_bank:
                UiGoto.startAty(getActivity(), BalanceActivity.class);
                break;
            case R.id.logout:
                ApiClient.quitlogin(getActivity(), new ResultCallback<Result>() {
                    @Override
                    public void onSuccess(Result response) {
                        UiGoto.startAty(getActivity(), LoginActivity.class);
                        prefUtils.clear();
                        EMClient.getInstance().logout(true);
                        getActivity().finish();
                    }

                    @Override
                    public void onError(String msg) {

                    }
                });
                break;
        }
    }

    private void isTrueCard(){
        ApiClient.isTureCard(getActivity(),new ResultCallback<ResultData>(){

            @Override
            public void onSuccess(ResultData response) {
                hideDialogLoading();
//                ToastUtils.showShort(getContext(), response.getData());
                if("True".equals(response.getData()) || "true".equals(response.getData())){
                    UiGoto.startAty(getActivity(), CashActivity.class);
                }else{
                    UiGoto.startAty(getActivity(), IDentityActivity.class);
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(getContext(), "请稍后再试");
            }
        });
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
                        UserInfoResult.UserInfoBean bean = response.getData();
                        if (null != bean) {

                            LoginOrRegistResult.RegistBean userInfo = prefUtils.getUserInfo();
                            userInfo.setVipType(bean.getVipType());
                            prefUtils.setUserinfo(userInfo);
                            //环信相关
                            prefUtils.setUserPic(bean.getAvatar());
                            prefUtils.setUserNickname(bean.getRealname());
                            EaseIMHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(bean.getRealname());
                            EaseIMHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(bean.getAvatar());
                            EaseIMHelper.getInstance().setCurrentUserName(userInfo.getPhone()); // 环信Id

                            vipTime = bean.getTotime();
                            Glide.with(MyApplication.getContext()).load(bean.getAvatar() + "?imageslim").asBitmap().error(R.mipmap.app_logo).into(header_img);
                            if (!TextUtils.isEmpty(bean.getRealname())) {
                                name.setText("姓名: " + bean.getRealname());
                            }
                            if (!TextUtils.isEmpty(bean.getSex())) {
                                sex.setText("性别: " + ("1".equals(bean.getSex()) ? "男" : "女"));
                            }
                            if (!TextUtils.isEmpty(bean.getAge())) {
                                age.setText("年龄: " + bean.getAge());
                            }
                            balance_num.setText(bean.getAccount());
                            String collectNum = bean.getCollectNum();
                            String packageNum = bean.getPackageNum();
                            String publishNum = bean.getPublishNum();
                            if (!TextUtils.isEmpty(collectNum)) {
                                num_collect.setVisibility(View.VISIBLE);
                                num_collect.setText(collectNum);
                            }
                            if (!TextUtils.isEmpty(packageNum)) {
                                num_redpacket.setVisibility(View.VISIBLE);
                                num_redpacket.setText(packageNum);
                            }
                            if (!TextUtils.isEmpty(publishNum)) {
                                num_release.setVisibility(View.VISIBLE);
                                num_release.setText(publishNum);
                            }

                            if (!TextUtils.isEmpty(bean.getPhone())) {
                                commend_phone.setText("推荐人电话:" + bean.getPhone());
                            }

                            int vip_i = Integer.valueOf(bean.getVipType());
                            if (0 == vip_i) {
                                vip_name.setText("普通用户");
                                upgrade.setBackgroundResource(R.drawable.login_regist_bg);
                            }
                            if (1 == vip_i) {
                                vip_name.setText("高级用户");
                                upgrade.setBackgroundResource(R.mipmap.bg_highlight);
                            }
                            if (2 == vip_i) {
                                vip_name.setText("企业用户");
                                upgrade.setBackgroundResource(R.mipmap.bg_highlight);
                            }

                        }
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
