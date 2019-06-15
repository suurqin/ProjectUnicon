package com.jm.projectunion.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jm.projectunion.R;
import com.jm.projectunion.common.manager.AppManager;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.utils.GlobalVar;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, GlobalVar.WX_APPID, true);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        final Intent intent = new Intent();
        System.out.println("wx==" + resp.errCode + "--------" + resp.errStr);
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
            // 支付成功
            ToastUtils.showShort(this, "成功支付");
            AppManager.getInstance().finishCurrentActiviy();
        } else {
            ToastUtils.showShort(this, "支付失败");
        }
        this.finish();
    }
}