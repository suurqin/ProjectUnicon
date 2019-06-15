package com.jm.projectunion.common.web;

import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;

/**
 * 描述：Navtie与JS交互的接口
 */
public class JSNativeInterface {

    private Activity mContext;

    public JSNativeInterface(Activity context) {
        mContext = context;
    }

    @JavascriptInterface
    public void getCategoryIds(String ids) {
        System.out.println("ids===" + ids);
        Intent mIntent = new Intent();
        mIntent.putExtra("categoryIds", ids);
        // 设置结果，并进行传送
        mContext.setResult(200, mIntent);
        mContext.finish();
    }
}
