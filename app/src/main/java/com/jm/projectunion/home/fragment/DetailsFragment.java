package com.jm.projectunion.home.fragment;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jm.projectunion.ImageViewActivity;
import com.jm.projectunion.MyApplication;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseExtraFragment;
import com.jm.projectunion.common.manager.Config;
import com.jm.projectunion.common.utils.DateUtils;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.common.widget.NoScrollListView;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.friends.activity.FriendInfoActivity;
import com.jm.projectunion.friends.adapter.TextViewAdapter;
import com.jm.projectunion.home.activity.DetailsActivity;
import com.jm.projectunion.home.adapter.ImageAdapter;
import com.jm.projectunion.home.dto.ReleaseInfoDto;
import com.jm.projectunion.home.entiy.InfoDetailsResult;
import com.jm.projectunion.information.entity.UserInfoDetailResult;
import com.jm.projectunion.message.chat.ChatActivity;
import com.jm.projectunion.message.chat.helper.Constant;
import com.jm.projectunion.mine.entity.CollectResult;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Young on 2017/10/31.
 */

public class DetailsFragment extends BaseExtraFragment {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.title_enclosure)
    LinearLayout title_enclosure;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.collect)
    TextView collect;
    @BindView(R.id.add_friend)
    TextView add_friend;
    @BindView(R.id.desp)
    TextView desp;
    @BindView(R.id.contact_enclosure)
    LinearLayout contact_enclosure;
    @BindView(R.id.im)
    TextView im;
    @BindView(R.id.tel)
    TextView tel;
    @BindView(R.id.call)
    LinearLayout call;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.pics)
    NoScrollListView pics;
    @BindView(R.id.complain)
    TextView complain;


    private ImageAdapter imageAdapter;
    private String imgUrl;
    private ReleaseInfoDto infoBean;
    private UserInfoDetailResult.UserInfoDetailBean userBean;

    @Override
    public void initView(View view) {
        if (DetailsActivity.TYPE_BANNER.equals(getArguments().getString("type"))) {
            title_enclosure.setVisibility(View.GONE);
            contact_enclosure.setVisibility(View.GONE);
            call.setVisibility(View.GONE);
        } else if (DetailsActivity.TYPE_INFO.equals(getArguments().getString("type"))) {
            title_enclosure.setVisibility(View.VISIBLE);
            contact_enclosure.setVisibility(View.VISIBLE);
            call.setVisibility(View.VISIBLE);
        }
        imageAdapter = new ImageAdapter(getActivity());
        pics.setAdapter(imageAdapter);

        img.setOnClickListener(this);
        collect.setOnClickListener(this);
        add_friend.setOnClickListener(this);
        im.setOnClickListener(this);
        tel.setOnClickListener(this);
        complain.setOnClickListener(this);
    }

    @Override
    public void initData() {
        String infoId = getArguments().getString("id");
        String bannerType = getArguments().getString("bannerType");
//        if (TextUtils.isEmpty(bannerType)) {
//            bannerType = "0";
//        }
        getData(infoId, bannerType);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_details;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img:
                if (!TextUtils.isEmpty(imgUrl)) {
                    UiGoto.startAtyWithType(getActivity(), ImageViewActivity.class, imgUrl);
                }
                break;
            case R.id.collect:
                CollectResult.CollectBean bean = new CollectResult.CollectBean();
                bean.setUserId(PrefUtils.getInstance(getActivity()).getUserInfo().getUserId());
                bean.setType(getArguments().getString("atype"));
                bean.setName(title.getText().toString());
                bean.setContent(getArguments().getString("id"));
                bean.setCtime(DateUtils.getStringByFormat(System.currentTimeMillis(), DateUtils.YYYY_MM_DD_HH_MM_SS));
                collect(bean);
                break;
            case R.id.add_friend:
                addFriend(phone.getText().toString().trim());
                break;
            case R.id.im:
                startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(Constant.EXTRA_USER_ID, userBean.getUsername()).putExtra(Constant.EXTRA_USER_NICKNAME, userBean.getRealname()));
                break;
            case R.id.tel:
                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel:" + phone.getText().toString().trim()));
                startActivity(intent1);
                break;
            case R.id.complain:
                showToast("已投诉");
                break;
        }
    }

    /**
     * 获取发布信息
     *
     * @param infoId
     * @param bannerType
     */
    private void getData(final String infoId, String bannerType) {
        showDialogLoading();
        ApiClient.getInfoDetails(getActivity(), infoId, bannerType, new ResultCallback<InfoDetailsResult>() {
            @Override
            public void onSuccess(InfoDetailsResult response) {
                System.out.println("result-infoDetail=" + response.toString());
                hideDialogLoading();
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        infoBean = response.getData();
                        if (null != infoBean) {
                            title.setText(infoBean.getTitle());
                            String house = "";
                            String price = "";
                            if ("1".equals(infoBean.getType())) {
                                house = "出租\t\t\t";
                                price = infoBean.getPrice();
                            } else if ("2".equals(infoBean.getType())) {
                                house = "出售\t\t\t";
                                price = infoBean.getPrice();
                            }
                            String str_desp = house + price;
                            if (TextUtils.isEmpty(str_desp)) {
                                str_desp = infoBean.getDesp();
                            } else {
                                str_desp += ("\r\n" + infoBean.getDesp());
                            }
                            desp.setText(str_desp);
                            name.setText(infoBean.getAuthor());
                            phone.setText(infoBean.getPhone());
                            address.setText(infoBean.getAddrDetails());
                            imgUrl = infoBean.getImage();
                            Glide.with(MyApplication.getContext()).load(imgUrl + "?imageslim").asBitmap().error(R.mipmap.img_def).into(img);
                            imageAdapter.setData(infoBean.getImgs());

                            /**
                             * 获取用户信息
                             */
                            getUserData(infoBean.getUserId());
                        }
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

    /**
     * 收藏信息
     *
     * @param bean
     */
    private void collect(CollectResult.CollectBean bean) {
        showDialogLoading();
        ApiClient.collectInfo(getActivity(), bean, new ResultCallback<InfoDetailsResult>() {
            @Override
            public void onSuccess(InfoDetailsResult response) {
                hideDialogLoading();
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
    }

    /**
     * 添加好友
     *
     * @param phone
     */
    private void addFriend(final String phone) {
        showDialogLoading();
        ApiClient.addFriend(getActivity(), phone, new ResultCallback<ResultData>() {
            @Override
            public void onSuccess(ResultData response) {
                hideDialogLoading();
                System.out.println("result--addfriend=" + response.toString());
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
    }

    /**
     * 获取信息
     */
    private void getUserData(String userId) {
        showDialogLoading();
        ApiClient.getUserInfoDetail(getActivity(), userId,
                new ResultCallback<UserInfoDetailResult>() {
                    @Override
                    public void onSuccess(UserInfoDetailResult response) {
                        hideDialogLoading();
                        if (null != response) {
                            System.out.println("result-user=" + response.toString());
                            if (null != response.getData()) {
                                userBean = response.getData();
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
        ToastUtils.showShort(getActivity(), msg);
    }
}
