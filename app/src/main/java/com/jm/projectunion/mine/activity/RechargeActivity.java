package com.jm.projectunion.mine.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alipay.sdk.app.PayTask;
import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.base.SingleFragmentActivity;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.mine.dto.OrderDto;
import com.jm.projectunion.mine.fragment.RechargeFragment;
import com.jm.projectunion.pay.PayResult;
import com.jm.projectunion.utils.GlobalVar;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by Young on 2017/11/7.
 */

public class RechargeActivity extends BaseTitleActivity {

    @BindView(R.id.money)
    EditText money;
    @BindView(R.id.pay_mode)
    RadioGroup pay_mode;
    @BindView(R.id.pay_wx)
    RadioButton pay_wx;
    @BindView(R.id.pay_alipay)
    RadioButton pay_alipay;
    @BindView(R.id.submit)
    Button submit;

    private String orderJson;

    private IWXAPI api;

    @Override
    public void initView() {
        setTitleText("充值");
        api = WXAPIFactory.createWXAPI(this, GlobalVar.WX_APPID, true);
        api.registerApp(GlobalVar.WX_APPID);
        submit.setOnClickListener(this);
    }

    @Override
    public void initData() {
    }

    @Override
    protected int getContentResId() {
        return R.layout.fragment_recharge;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.submit:
                if (TextUtils.isEmpty(money.getText().toString().trim())) {
                    ToastUtils.showShort(this, "金额不能为空");
                    return;
                }
                String type = "0";
                if (pay_wx.isChecked()) {
                    type = "2";
                } else if (pay_alipay.isChecked()) {
                    type = "1";
                }
                createPay(money.getText().toString().trim(), type);
                break;
        }
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

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            //同步获取结果
            String resultInfo = payResult.getResult();
            String tradeStatus = payResult.getResultStatus();

            // 支付宝返回成功，交给服务器验签
            if (tradeStatus.equals("9000")) {
                showToast("成功支付");
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

    private void payByAlipay(final String payJson) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeActivity.this);
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

    /**
     * 支付
     *
     * @param money
     * @param payType: string支付类型：1支付宝，2微信，3银联
     */
    private void createPay(String money, String payType) {
        showDialogLoading();
        OrderDto dto = new OrderDto();
        dto.setAmount(money);
        dto.setOrderType("1");
        dto.setPayType(payType);
//        dto.setProType("1");
        dto.setUserId(PrefUtils.getInstance(this).getUserInfo().getUserId());
        ApiClient.createPay(this, dto, new ResultCallback<ResultData>() {
            @Override
            public void onSuccess(ResultData response) {
                hideDialogLoading();
                System.out.println("result--pay=" + response.toString());
                if (null != response) {
                    ToastUtils.showShort(RechargeActivity.this, response.getMsg());
                    if ("0".equals(response.getCode())) {
                        orderJson = response.getData();
                        if (pay_wx.isChecked()) {
                            payByWX(orderJson);
                        } else if (pay_alipay.isChecked()) {
                            payByAlipay(orderJson);
                        }
                    }
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(RechargeActivity.this, msg);
            }
        });
    }

    private void showToast(String msg) {
        ToastUtils.showShort(this, msg);
    }
}
