package com.jm.projectunion.home.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.jm.projectunion.R;
import com.jm.projectunion.common.base.BaseTitleActivity;
import com.jm.projectunion.common.share.ShareManager;
import com.jm.projectunion.common.utils.BitmapUtils;
import com.jm.projectunion.common.utils.FileUtils;
import com.jm.projectunion.common.utils.TextViewUtils;
import com.jm.projectunion.common.utils.ToastUtils;
import com.jm.projectunion.entity.ResultData;
import com.jm.projectunion.home.adapter.ShareAdapter;
import com.jm.projectunion.utils.api.ApiClient;
import com.jm.projectunion.utils.api.ResultCallback;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;

/**
 * Created by YangPan on 2017/11/10.
 */

public class ShareActivity extends BaseTitleActivity {

    private static final String IMG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jm/qercode.png";

    @BindView(R.id.qercode)
    ImageView qercode;
    @BindView(R.id.share_controller)
    GridView share_controller;

    private ShareManager shareManager;
    private String url;

    @Override
    public void initView() {
        setTitleText("分享");
        //分型内容
        final String title = "工程联盟app马上下载";
        final String des = "智慧媒体 个人自媒体广告宣传平台\r\n互联建筑 工程行业信息资源数据库";
        shareManager = ShareManager.getInstance(this);

        share_controller.setAdapter(new ShareAdapter(this));
        share_controller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (TextUtils.isEmpty(url)) {
                    ToastUtils.showShort(ShareActivity.this, "数据解析错误，请稍后重试");
                    return;
                }
                final UMWeb web = new UMWeb(url);
                UMImage image = new UMImage(ShareActivity.this, R.mipmap.share_img);//网络图片
                web.setTitle(title);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(des);//描述

                switch (i) {
                    case 0:
                        if (shareManager.getmWXApi().isWXAppInstalled()) {
//                            ToastUtils.showShort(ShareActivity.this, "分享-微信");
                            ShareManager.ShareContent shareContent = shareManager.getShareContentWebpag(title, des, url, "");
                            shareManager.shareByWebchat(shareContent, ShareManager.WECHAT_SHARE_TYPE_TALK);
//                            new ShareAction(ShareActivity.this).setPlatform(SHARE_MEDIA.WEIXIN).withMedia(web).share();
                        } else {
                            ToastUtils.showShort(ShareActivity.this, "微信未安装");
                        }
                        break;
                    case 1:
                        if (shareManager.getmWXApi().isWXAppInstalled()) {
//                            ToastUtils.showShort(ShareActivity.this, "分享-朋友圈");
                            ShareManager.ShareContent shareContent = shareManager.getShareContentWebpag(des, des, url, "");
                            shareManager.shareByWebchat(shareContent, ShareManager.WECHAT_SHARE_TYPE_FRENDS);
//                            new ShareAction(ShareActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).withMedia(web).share();
                        } else {
                            ToastUtils.showShort(ShareActivity.this, "微信未安装");
                        }
                        break;
                    case 2:
                        ToastUtils.showShort(ShareActivity.this, "分享-QQ空间");
                        new ShareAction(ShareActivity.this).setPlatform(SHARE_MEDIA.QZONE).withMedia(web).setCallback(shareListener).share();
                        break;
                    case 3:
                        ToastUtils.showShort(ShareActivity.this, "分享-QQ好友");
                        new ShareAction(ShareActivity.this).setPlatform(SHARE_MEDIA.QQ).withMedia(web).setCallback(shareListener).share();
                        break;
                    case 4:
                        ToastUtils.showShort(ShareActivity.this, "分享-新浪微博");
//                        new ShareAction(ShareActivity.this).setPlatform(SHARE_MEDIA.SINA).withMedia(web).setCallback(shareListener).share();
                        break;
                    case 5:
                        ToastUtils.showShort(ShareActivity.this, "分享-复制链接");
                        new ShareAction(ShareActivity.this).setPlatform(SHARE_MEDIA.SMS).withMedia(web).setCallback(shareListener).share();
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        getShareLink();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_share;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void getShareLink() {
        showDialogLoading();
        ApiClient.getShare(this, new ResultCallback<ResultData>() {
            @Override
            public void onSuccess(ResultData response) {
                hideDialogLoading();
                System.out.println("result-share=" + response.toString());
                if (null != response) {
                    if ("0".equals(response.getCode())) {
                        url = response.getData();
                        int size = TextViewUtils.getRealTextSize(ShareActivity.this, 380);
                        Bitmap bitmap = BitmapUtils.generateBitmap(response.getData(), size, size);
                        qercode.setImageBitmap(bitmap);
                        FileUtils.bitmapToFile(bitmap, IMG_PATH);
                    }
                    ToastUtils.showShort(ShareActivity.this, response.getMsg());
                }
            }

            @Override
            public void onError(String msg) {
                hideDialogLoading();
                ToastUtils.showShort(ShareActivity.this, msg);
            }
        });
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(ShareActivity.this, "成功了", Toast.LENGTH_SHORT).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ShareActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ShareActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };
}
