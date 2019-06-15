package com.jm.projectunion.mine.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseExtraFragment;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.LoginOrRegistResult;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.mine.dto.OrderDto;
import com.jm.projectunion.mine.entity.VipTypeResult;
import com.jm.projectunion.pay.PayResult;
import com.jm.projectunion.utils.GlobalVar;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Young on 2017/11/8.
 */

public class LeaguerFragment extends BaseExtraFragment {

    private static final String VIP_TIME = "vtime";

    @BindView(R.id.level)
    TextView tv_level;
    @BindView(R.id.bydate)
    TextView bydate;
    @BindView(R.id.level_1)
    LinearLayout level_1;
    @BindView(R.id.btn_level1)
    TextView btn_level1;
    @BindView(R.id.level_2)
    LinearLayout level_2;
    @BindView(R.id.btn_level2)
    TextView btn_level2;
    @BindView(R.id.mid_money)
    TextView mid_money;
    @BindView(R.id.level_3)
    LinearLayout level_3;
    @BindView(R.id.btn_level3)
    TextView btn_level3;
    @BindView(R.id.heig_money)
    TextView heig_money;
    @BindView(R.id.leaguer_rules)
    TextView leaguer_rules;
    @BindView(R.id.vip)
    RadioGroup vip;
    @BindView(R.id.pay_wx)
    RadioButton pay_wx;
    @BindView(R.id.pay_alipay)
    RadioButton pay_alipay;
    @BindView(R.id.pay_ye)
    RadioButton pay_ye;
    @BindView(R.id.buy)
    Button buy;

    private String vipType = "1";
    private List<VipTypeResult.VipTypeBean> vipTypeBeans = new ArrayList<>();
    private IWXAPI api;
    private String orderJson;

    public static LeaguerFragment newInstance(String vipTime) {
        LeaguerFragment fragment = new LeaguerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIP_TIME, vipTime);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView(View view) {
        api = WXAPIFactory.createWXAPI(getActivity(), GlobalVar.WX_APPID, true);
        api.registerApp(GlobalVar.WX_APPID);

        String vtime = "";
        Bundle bundle = getArguments();
        if (bundle != null) {
            vtime = bundle.getString(VIP_TIME, "");
        }

        String level = "用户等级：";
        int iLevel = Integer.valueOf(PrefUtils.getInstance(getActivity()).getUserInfo().getVipType());
        switch (iLevel) {
            case 0:
                level += ("普通用户");
                btn_level1.setBackgroundResource(R.mipmap.bg_highlight);
                leaguer_rules.setText(GlobalVar.LEVEL_1);
                break;
            case 1:
                level += ("高级用户");
                btn_level2.setBackgroundResource(R.mipmap.bg_highlight);
                leaguer_rules.setText(GlobalVar.LEVEL_2);
                break;
            case 2:
                level += ("企业用户");
                btn_level3.setBackgroundResource(R.mipmap.bg_highlight);
                leaguer_rules.setText(GlobalVar.LEVEL_3);

                break;
        }
        tv_level.setText(level);
        if (0 == iLevel) {
            bydate.setText("到期时间:永久");
        } else {
            bydate.setText("到期时间:" + vtime);
        }

        level_1.setOnClickListener(this);
        level_2.setOnClickListener(this);
        level_3.setOnClickListener(this);
        buy.setOnClickListener(this);
        vip.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.senior_leaguers:
                        vipType = "1";
                        break;
                    case R.id.enterprise_leaguers:
                        vipType = "2";
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        getData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_leaguer;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.level_1:
                if (vipTypeBeans.size() > 2) {
                    leaguer_rules.setText(GlobalVar.LEVEL_1);
                }
                initBtn();
                btn_level1.setBackgroundResource(R.mipmap.bg_highlight);
                break;
            case R.id.level_2:
                if (vipTypeBeans.size() > 0) {
                    leaguer_rules.setText(GlobalVar.LEVEL_2);
                }
                initBtn();
                btn_level2.setBackgroundResource(R.mipmap.bg_highlight);
                break;
            case R.id.level_3:
//                if (vipTypeBeans.size() > 1) {
//                    leaguer_rules.setText(GlobalVar.LEVEL_3);
//                }
//                initBtn();
//                btn_level3.setBackgroundResource(R.mipmap.bg_highlight);
                //TODO wfx修改 关闭开通企业会员 开始
                showToast("暂无权限");
                break;
            case R.id.buy:
                int index = Integer.valueOf(vipType);
                String vipName = "普通用户";
                if (1 == index) {
                    vipName = "高级用户";
                } else if (2 == index) {
                    vipName = "企业用户";
                    //TODO wfx修改 关闭开通企业会员 开始
                    showToast("暂无权限");
                    return;
                    //TODO wfx修改 关闭开通企业会员 结束
                }
                final String money = vipTypeBeans.get(index - 1).getMoney();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("确定需要花费 [￥" + money + "]开通[" + vipName + "]");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        buyVip(vipType, money);
                        String type = "0";
                        if (pay_wx.isChecked()) {
                            type = "2";
                        } else if (pay_alipay.isChecked()) {
                            type = "1";
                        } else if (pay_ye.isChecked()) {
                            type = "3";
                            showToast("余额支付");
                        }
                        createPay(money, type, vipType);
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
        }
    }

    private void initBtn() {
        btn_level1.setBackgroundResource(R.mipmap.bg_grey_dark);
        btn_level2.setBackgroundResource(R.mipmap.bg_grey_dark);
        btn_level3.setBackgroundResource(R.mipmap.bg_grey_dark);
    }

    private void getData() {
        showDialogLoading();
        ApiClient.getVipType(getActivity(), new ResultCallback<VipTypeResult>() {
            @Override
            public void onSuccess(VipTypeResult response) {
                System.out.println("result--vip=" + response.toString());
                hideDialogLoading();
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        vipTypeBeans = response.getData();
                        if (vipTypeBeans.size() > 2) {
                            mid_money.setText("￥" + vipTypeBeans.get(0).getMoney() + "/年");
                            //TODO wfx修改 企业用户 2800/年  改成  0 元
//                            heig_money.setText("￥" + vipTypeBeans.get(1).getMoney() + "/年");
                            heig_money.setText("￥" + 0 + "/年");
                        }
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

    private void buyVip(final String vipType, String money) {
        showDialogLoading();
        ApiClient.buyVip(getActivity(), vipType, money, new ResultCallback<ResultData>() {
            @Override
            public void onSuccess(ResultData response) {
                hideDialogLoading();
                System.out.println("reslut-vip==" + response.toString());
                if (null != response) {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                    if ("0".equals(response.getCode())) {
                        bydate.setText("到期时间:" + response.getData());
                        String level = "用户等级：";
                        int iLevel = Integer.valueOf(vipType);
                        switch (iLevel) {
                            case 0:
                                level += ("普通用户");
                                break;
                            case 1:
                                level += ("高级用户");
                                break;
                            case 2:
                                level += ("企业用户");
                                break;
                        }
                        tv_level.setText(level);

                        LoginOrRegistResult.RegistBean bean = PrefUtils.getInstance(getActivity()).getUserInfo();
                        bean.setVipType(vipType);
                        PrefUtils.getInstance(getActivity()).setUserinfo(bean);
                    }
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
     * 支付
     *
     * @param money
     * @param payType: string支付类型：1支付宝，2微信，3银联
     * @param orderType: string商品类型:3高级会员，4企业会员
     */
    private void createPay(String money, String payType, String orderType) {
        showDialogLoading();
      int i_orderType = Integer.valueOf(orderType)+2;
        OrderDto dto = new OrderDto();
        dto.setAmount(money);
//        dto.setAmount("0.01");
        dto.setOrderType(String.valueOf(i_orderType));
        dto.setPayType(payType);
        dto.setUserId(PrefUtils.getInstance(getActivity()).getUserInfo().getUserId());
        ApiClient.createPay(getActivity(), dto, new ResultCallback<ResultData>() {
            @Override
            public void onSuccess(ResultData response) {
                hideDialogLoading();
                System.out.println("result--pay=" + response.toString());
                if (null != response) {
                    showToast(response.getMsg());
                    if ("0".equals(response.getCode())) {
                        orderJson = response.getData();
                        if (pay_wx.isChecked()) {
                            payByWX(orderJson);
                        } else if (pay_alipay.isChecked()) {
                            payByAlipay(orderJson);
                        } else if (pay_ye.isChecked()) {
                            getActivity().finish();
                            showToast("余额支付返回！");
                        }
                    }
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                showToast(msg);
            }
        });
    }

    private void payByWX(String payJson) {
        JSONObject json = null;
        try {
            json = new JSONObject(payJson);
            if (null != json && !json.has("retcode")) {
                PayReq req = new PayReq();
                req.appId = json.getString("appid");
                req.partnerId = json.getString("partnerid");
                req.prepayId = json.getString("prepayid");
                req.nonceStr = json.getString("noncestr");
                req.timeStamp = json.getString("timestamp");
                req.packageValue = json.getString("package");
                req.sign = json.getString("sign");
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                api.sendReq(req);
            } else {
                showToast("返回错误" + json.getString("retmsg"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showToast("服务器请求错误");
        }
    }

    private void payByAlipay(final String payJson) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(payJson, true);
                Message msg = new Message();
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            //同步获取结果
            String resultInfo = payResult.getResult();
            String tradeStatus = payResult.getResultStatus();

            // 支付宝返回成功，交给服务器验签
            if (tradeStatus.equals("9000")) {
                showToast("成功支付");
                getActivity().finish();
            } else if (tradeStatus.equals("4000")) {
                showToast("系统异常，请稍候重试");
            } else if (tradeStatus.equals("4001")) {
                showToast("系统异常，请稍候重试");
            } else if (tradeStatus.equals("4003")) {
                showToast("您的支付宝账户被冻结或不允许支付，请更换账户重试");
            } else if (tradeStatus.equals("4004")) {
                showToast("支付宝账户异常，请更换账户支付宝账户异常，请更换账户");
            } else if (tradeStatus.equals("4005")) {
                showToast("支付宝账户异常，请更换账户");
            } else if (tradeStatus.equals("4006")) {
                showToast("支付失败");
            } else if (tradeStatus.equals("4010")) {
                showToast("支付宝账户异常，请更换账户");
            } else if (tradeStatus.equals("6000")) {
                showToast("支付宝服务正在升级");
            } else if (tradeStatus.equals("6001")) {
                showToast("交易已取消");
            } else if (tradeStatus.equals("6002")) {
                showToast("网络连接异常");
            } else {
                showToast("系统错误，请稍候重试 ");
            }

        }
    };

    private void showToast(String msg) {
        ToastUtils.showShort(getActivity(), msg);
    }
}
