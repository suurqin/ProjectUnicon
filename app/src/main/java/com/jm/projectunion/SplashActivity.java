package com.jm.projectunion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Base64;

import com.jm.projectunion.common.base.BaseActivity;
import com.jm.projectunion.common.utils.AppInfoUtils;
import com.jm.projectunion.common.utils.FileUtils;
import com.jm.projectunion.common.utils.PrefUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.VersionResult;
import com.jm.projectunion.message.chat.EaseIMHelper;
import com.jm.projectunion.utils.UiGoto;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;

import java.util.List;

/**
 * Created by Young on 2017/10/25.
 */

public class SplashActivity extends BaseActivity {

    private PrefUtils prefUtils;
    private Handler handler = new Handler();

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        EaseIMHelper.getInstance().initHandler(this.getMainLooper());
        prefUtils = PrefUtils.getInstance(this);
        checkVersion();
    }

    @Override
    public void initData() {

    }

    /**
     * 延时启动
     */
    private void countDown() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!prefUtils.isFirst()) {
                    if (null != prefUtils.getUserInfo()) {
                        UiGoto.startAty(SplashActivity.this, MainActivity.class);
                    } else {
                        UiGoto.startAty(SplashActivity.this, LoginActivity.class);
                    }
                } else {
                    UiGoto.startAty(SplashActivity.this, GuideActivity.class);
                }
                finish();
            }
        }, 3000);
    }

    /**
     * 检查版本
     */
    private void checkVersion() {
        ApiClient.getAppVersion(this, new ResultCallback<VersionResult>() {
            @Override
            public void onSuccess(VersionResult response) {
                System.out.println("result-app=" + response.toString());
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        prefUtils.setVersion(response.getData());
                    }
                }
                countDown();
            }

            @Override
            public void onError(String msg) {
                ToastUtils.showShort(SplashActivity.this, msg);
                countDown();
            }
        });
    }

}
