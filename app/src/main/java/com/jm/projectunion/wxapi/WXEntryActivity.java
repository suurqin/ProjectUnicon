package com.jm.projectunion.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.jm.projectunion.R;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.utils.GlobalVar;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by ntop on 15/9/4.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this,
                GlobalVar.WX_APPID, true);
        api.handleIntent(getIntent(), this);

    }

    @Override
    public void onReq(BaseReq baseReq) {
        ToastUtils.showShort(this, baseReq.openId);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        String result;
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "分享成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "分享取消";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = "不支持错误";
                break;
            default:
                result = "发送返回";
                break;
        }
        ToastUtils.showShort(this, result);
        this.finish();
    }
}
