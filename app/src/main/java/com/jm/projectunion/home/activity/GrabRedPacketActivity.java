package com.jm.projectunion.home.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.utils.TextViewUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.common.widget.CircleImageView;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.friends.activity.FriendInfoActivity;
import com.jm.projectunion.home.entiy.RedPacketResult;
import com.jm.projectunion.information.entity.UserInfoDetailResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import butterknife.BindView;

/**
 * Created by YangPan on 2017/11/5.
 */

public class GrabRedPacketActivity extends BaseTitleActivity {

    @BindView(R.id.packet_title)
    TextView packet_title;
    @BindView(R.id.packet)
    ImageView packet;
    @BindView(R.id.money)
    TextView tv_money;
    @BindView(R.id.avator)
    CircleImageView avator;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.phone)
    TextView phone;

    private RedPacketResult.RedPacketBean bean;
    private String rpStatu;

    @Override
    public void initView() {
        setTitleText("抢红包");
        packet.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getBundleExtra(UiGoto.BUNDLE);
        bean = (RedPacketResult.RedPacketBean) bundle.getSerializable("redPackage");
        rpStatu = bundle.getString("rpstatu");
        if (null != bean) {
            phone.setText(bean.getPhone());
            packet_title.setText(bean.getTitle());
            getUserInfo(bean.getUserId());
        }

        if ("1".equals(rpStatu)) {
            packet.setVisibility(View.GONE);
            tv_money.setVisibility(View.VISIBLE);
            tv_money.setText("已领 ￥" + bean.getPrice());
        }
        avator.setOnClickListener(this);
        phone.setOnClickListener(this);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_grab_redpacket;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.packet:
                if (null != bean) {
                    gpRedpack(bean.getRedPacketId(), bean.getTitle(), bean.getPrice());
                } else {
                    ToastUtils.showShort(this, "服务器繁忙，稍后再试");
                }
                break;
            case R.id.avator:
                Bundle bundle = new Bundle();
                bundle.putString("name", username.getText().toString().trim());
                bundle.putString("id", bean.getUserId());
                UiGoto.startAtyWithBundle(this, FriendInfoActivity.class, bundle);
                break;
            case R.id.phone:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + bean.getPhone()));
                startActivity(intent);
                break;
        }
    }

    private void gpRedpack(String redId, String title, final String money) {
        showDialogLoading();
        ApiClient.gpRedPack(this, redId, title, money, new ResultCallback<ResultData>() {
            @Override
            public void onSuccess(ResultData response) {
                hideDialogLoading();
                System.out.println("result-rgp==" + response.toString());
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        packet.setVisibility(View.GONE);
                        tv_money.setVisibility(View.VISIBLE);
                        //1表示成功，0表示未抢到
                        if ("1".equals(response.getData())) {
                            tv_money.setText(money + "元");
                        } else {
                            tv_money.setText("谢谢参与");
                        }
                    }
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(GrabRedPacketActivity.this, msg);
            }
        });
    }

    private void getUserInfo(String userId) {
        ApiClient.getUserInfoDetail(this, userId,
                new ResultCallback<UserInfoDetailResult>() {
                    @Override
                    public void onSuccess(UserInfoDetailResult response) {
                        if (null != response) {
                            if ("0".equals(response.getCode())) {
                                UserInfoDetailResult.UserInfoDetailBean data = response.getData();
                                Glide.with(GrabRedPacketActivity.this).load(data.getAvatar()).asBitmap().error(R.mipmap.app_logo).into(avator);
                                username.setText(data.getRealname());
                            }
                        }
                    }

                    @Override
                    public void onError(String msg) {
                    }
                });
    }
}
