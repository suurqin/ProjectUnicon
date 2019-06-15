package com.jm.projectunion.friends.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.common.widget.CircleImageView;
import com.jm.projectunion.common.widget.NoScrollGridView;
import com.jm.projectunion.common.widget.NoScrollListView;
import com.jm.projectunion.dao.AreaDaoUtil;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.friends.adapter.TextViewAdapter;
import com.jm.projectunion.home.adapter.ImageAdapter;
import com.jm.projectunion.information.activity.UserInfoEditActivity;
import com.jm.projectunion.information.adapter.PicAdapter;
import com.jm.projectunion.information.entity.UserInfoDetailResult;
import com.jm.projectunion.message.chat.ChatActivity;
import com.jm.projectunion.message.chat.helper.Constant;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Young on 2017/11/13.
 */

public class FriendInfoActivity extends BaseTitleActivity {

    @BindView(R.id.header_img)
    CircleImageView header_img;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.upgrade)
    LinearLayout upVip;
    @BindView(R.id.telphone)
    TextView telphone;
    @BindView(R.id.friend_add)
    ImageView friend_add;
    @BindView(R.id.friend_im)
    ImageView friend_im;
    @BindView(R.id.friend_tel)
    ImageView friend_tel;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.self_intro)
    TextView self_intro;
    @BindView(R.id.friend_mark)
    NoScrollGridView friend_mark;
    @BindView(R.id.building_type)
    TextView building_type;
    @BindView(R.id.company)
    NoScrollListView company;

    @BindView(R.id.complain)
    TextView complain;

    private AreaDaoUtil daoUtil;
    private UserInfoDetailResult.UserInfoDetailBean bean;

    @Override
    public void initView() {
        daoUtil = new AreaDaoUtil(this);
        String username = getIntent().getBundleExtra(UiGoto.BUNDLE).getString("name");
        String userId = getIntent().getBundleExtra(UiGoto.BUNDLE).getString("id");
        setTitleText(null == username ? "好友" : username);
        upVip.setOnClickListener(this);
        friend_add.setOnClickListener(this);
        friend_im.setOnClickListener(this);
        friend_tel.setOnClickListener(this);
        complain.setOnClickListener(this);
        getData(userId);
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_friend_info;
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.friend_tel:
                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel:" + bean.getPhone()));
                startActivity(intent1);
                break;
            case R.id.friend_im:
                startActivity(new Intent(this, ChatActivity.class).putExtra(Constant.EXTRA_USER_ID, bean.getUsername()).putExtra(Constant.EXTRA_USER_NICKNAME, bean.getRealname()));
                break;
            case R.id.friend_add:
                ApiClient.addFriend(this, bean.getPhone(), new ResultCallback<ResultData>() {
                    @Override
                    public void onSuccess(ResultData response) {
                        ToastUtils.showShort(FriendInfoActivity.this, response.getMsg());
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.showShort(FriendInfoActivity.this, msg);
                    }
                });
                break;
            case R.id.complain:
                showToast("已投诉");
                break;
        }
    }

    /**
     * 获取信息
     */
    private void getData(String userId) {
        showDialogLoading();
        ApiClient.getUserInfoDetail(this, userId,
                new ResultCallback<UserInfoDetailResult>() {
                    @Override
                    public void onSuccess(UserInfoDetailResult response) {
                        hideDialogLoading();
                        if (null != response) {
                            System.out.println("result-user=" + response.toString());
                            if (null != response.getData()) {
                                bean = response.getData();
                                Glide.with(FriendInfoActivity.this).load(bean.getAvatar()).asBitmap().error(R.mipmap.app_logo).into(header_img);
                                name.setText(null == bean.getRealname() ? "" : bean.getRealname());
                                sex.setText(null == bean.getSex() ? "" : ("1".equals(bean.getSex()) ? "男" : "女"));
                                age.setText(null == bean.getAge() ? "" : bean.getAge());
                                telphone.setText(null == bean.getPhone() ? "" : bean.getPhone());
                                address.setText(null == bean.getAddress() ? "" : bean.getAddress());
                                self_intro.setText(null == bean.getDesc() ? "" : bean.getDesc());

                                if (null != bean.getService()) {
                                    String[] ids = bean.getService().split(",");
                                    List<String> names = new ArrayList<>();
                                    for (String id : ids) {
                                        if (!TextUtils.isEmpty(id)) {
                                            if (null != daoUtil.queryById(id)) {
                                                names.add(daoUtil.queryById(id).getName());
                                            }
                                        }
                                    }
                                    friend_mark.setAdapter(new TextViewAdapter(FriendInfoActivity.this, names));
                                }
                                building_type.setText(null == bean.getIndustryName() ? "" : bean.getIndustryName());
                                company.setAdapter(new ImageAdapter(FriendInfoActivity.this, bean.getImgs()));
                            }
                            showToast(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        hideDialogLoading();
                        showToast(msg);
                    }
                });
    }

    private void showToast(String msg) {
        ToastUtils.showShort(this, msg);
    }
}
