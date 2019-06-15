package com.jm.projectunion.common.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import com.jm.projectunion.R;
import com.jm.projectunion.common.utils.ImageUtils;
import com.jm.projectunion.utils.GlobalVar;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by Young on 2017/3/31.
 */

public class ShareManager {
    public static final String WXAPPID = GlobalVar.WX_APPID;
    private static final int THUMB_SIZE = 60;
    private static int DEF_IMG = R.mipmap.share_img;

    private static final int WECHAT_SHARE_WAY_TEXT = 1;   //文字
    private static final int WECHAT_SHARE_WAY_PICTURE = 2; //图片
    private static final int WECHAT_SHARE_WAY_WEBPAGE = 3;  //链接
    private static final int WECHAT_SHARE_WAY_VIDEO = 4; //视频
    public static final int WECHAT_SHARE_TYPE_TALK = SendMessageToWX.Req.WXSceneSession;  //会话
    public static final int WECHAT_SHARE_TYPE_FRENDS = SendMessageToWX.Req.WXSceneTimeline; //朋友圈
    private ShareContent mShareContentText, mShareContentPicture, mShareContentWebpag, mShareContentVideo;
    private Context context;

    private static ShareManager instance;

    public IWXAPI getmWXApi() {
        return mWXApi;
    }

    private IWXAPI mWXApi;

    private ShareManager(Context context) {
        this.context = context;
        if (mWXApi == null) {
            mWXApi = WXAPIFactory.createWXAPI(context, WXAPPID);
        }
        mWXApi.registerApp(WXAPPID);
    }

    public static ShareManager getInstance(Context context) {    //对获取实例的方法进行同步
        if (instance == null) {
            synchronized (ShareManager.class) {
                if (instance == null)
                    instance = new ShareManager(context);
            }
        }
        return instance;
    }


    /**
     * 通过微信分享
     *
     * @param shareContent 分享的方式（文本、图片、链接）
     * @param shareType    分享的类型（朋友圈，会话）
     */
    public void shareByWebchat(ShareContent shareContent, int shareType) {
        switch (shareContent.getShareWay()) {
            case WECHAT_SHARE_WAY_TEXT:
                shareText(shareContent, shareType);
                break;
            case WECHAT_SHARE_WAY_PICTURE:
                sharePicture(shareContent, shareType);
                break;
            case WECHAT_SHARE_WAY_WEBPAGE:
                shareWebPage(shareContent, shareType);
                break;
            case WECHAT_SHARE_WAY_VIDEO:
                shareVideo(shareContent, shareType);
                break;
        }
    }

    public abstract class ShareContent {
        protected abstract int getShareWay();

        protected abstract String getContent();

        protected abstract String getTitle();

        protected abstract String getURL();

        protected abstract String getPicUrl();
    }

    /**
     * 设置分享文字的内容
     *
     * @author chengcj1
     */
    public class ShareContentText extends ShareContent {
        private String content;

        /**
         * 构造分享文字类
         *
         * @param content 分享的文字内容
         */
        public ShareContentText(String content) {
            this.content = content;
        }

        @Override
        protected int getShareWay() {
            return WECHAT_SHARE_WAY_TEXT;
        }

        @Override
        protected String getContent() {
            return content;
        }

        @Override
        protected String getTitle() {
            return null;
        }

        @Override
        protected String getURL() {
            return null;
        }

        @Override
        protected String getPicUrl() {
            return null;
        }
    }

    /*
     * 获取文本分享对象
     */
    public ShareContent getShareContentText(String content) {
        mShareContentText = new ShareContentText(content);
        return (ShareContentText) mShareContentText;
    }

    /**
     * 设置分享图片的内容
     *
     * @author chengcj1
     */
    public class ShareContentPicture extends ShareContent {
        private String pictureResource;

        public ShareContentPicture(String pictureResource) {
            this.pictureResource = pictureResource;
        }

        @Override
        protected int getShareWay() {
            return WECHAT_SHARE_WAY_PICTURE;
        }

        @Override
        protected String getPicUrl() {
            return pictureResource;
        }

        @Override
        protected String getContent() {
            return null;
        }

        @Override
        protected String getTitle() {
            return null;
        }

        @Override
        protected String getURL() {
            return null;
        }
    }

    /*
     * 获取图片分享对象
     */
    public ShareContent getShareContentPicture(String pictureResource) {
        mShareContentPicture = new ShareContentPicture(pictureResource);
        return (ShareContentPicture) mShareContentPicture;
    }

    /**
     * 设置分享链接的内容
     *
     * @author chengcj1
     */
    public class ShareContentWebpage extends ShareContent {
        private String title;
        private String content;
        private String url;
        private String pictureResource;

        public ShareContentWebpage(String title, String content, String url, String pictureResource) {
            this.title = title;
            this.content = content;
            this.url = url;
            this.pictureResource = pictureResource;
        }

        @Override
        protected int getShareWay() {
            return WECHAT_SHARE_WAY_WEBPAGE;
        }

        @Override
        protected String getContent() {
            return content;
        }

        @Override
        protected String getTitle() {
            return title;
        }

        @Override
        protected String getURL() {
            return url;
        }

        @Override
        protected String getPicUrl() {
            return pictureResource;
        }
    }

    /*
     * 获取网页分享对象
     */
    public ShareContent getShareContentWebpag(String title, String content, String url, String pictureResource) {
        mShareContentWebpag = new ShareContentWebpage(title, content, url, pictureResource);
        return mShareContentWebpag;
    }

    /**
     * 设置分享视频的内容
     *
     * @author chengcj1
     */
    public class ShareContentVideo extends ShareContent {
        private String url;

        public ShareContentVideo(String url) {
            this.url = url;
        }

        @Override
        protected int getShareWay() {
            return WECHAT_SHARE_WAY_VIDEO;
        }

        @Override
        protected String getContent() {
            return null;
        }

        @Override
        protected String getTitle() {
            return null;
        }

        @Override
        protected String getURL() {
            return url;
        }

        @Override
        protected String getPicUrl() {
            return null;
        }
    }

    /*
     * 获取视频分享内容
     */
    public ShareContent getShareContentVideo(String url) {
        mShareContentVideo = new ShareContentVideo(url);
        return (ShareContentVideo) mShareContentVideo;
    }

    /*
     * 分享文字
     */
    private void shareText(ShareContent shareContent, int shareType) {
        String text = shareContent.getContent();
        //初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;
        //用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        //transaction字段用于唯一标识一个请求
        req.transaction = buildTransaction("textshare");
        req.message = msg;
        //发送的目标场景， 可以选择发送到会话 WXSceneSession 或者朋友圈 WXSceneTimeline。 默认发送到会话。
        req.scene = shareType;
        mWXApi.sendReq(req);
    }

    /*
     * 分享图片
     */
    private void sharePicture(ShareContent shareContent, int shareType) {
        WXImageObject imgObj = new WXImageObject();

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap thumb = Bitmap.createScaledBitmap(getLocalOrNetBitmap(shareContent.getPicUrl()), THUMB_SIZE, THUMB_SIZE, true);//压缩Bitmap
        if (thumb == null) {
            Toast.makeText(context, "图片不能为空", Toast.LENGTH_SHORT).show();
        } else {
            msg.thumbData = ImageUtils.bmpToByteArray(thumb, true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("imgshareappdata");
        req.message = msg;
        req.scene = shareType;
        mWXApi.sendReq(req);
    }

    /*
     * 分享链接
     */
    private void shareWebPage(final ShareContent shareContent, final int shareType) {

        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                return getLocalOrNetBitmap(shareContent.getPicUrl());
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                WXWebpageObject webpageShare = new WXWebpageObject();
                webpageShare.webpageUrl = shareContent.getURL();
                WXMediaMessage msg = new WXMediaMessage(webpageShare);
                msg.title = shareContent.getTitle();
                msg.description = shareContent.getContent();
                Bitmap thumb = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);//压缩Bitmap
                if (thumb == null) {
                    Toast.makeText(context, "图片不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    msg.thumbData = ImageUtils.bmpToByteArray(thumb, true);
                }
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                req.scene = shareType;
                mWXApi.sendReq(req);
            }
        }.execute();

    }

    /*
     * 分享视频
     */
    private void shareVideo(ShareContent shareContent, int shareType) {
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = shareContent.getURL();

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = shareContent.getTitle();
        msg.description = shareContent.getContent();
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.mipmap.share_img);
//      BitmapFactory.decodeStream(new URL(video.videoUrl).openStream());
        /**
         * 测试过程中会出现这种情况，会有个别手机会出现调不起微信客户端的情况。造成这种情况的原因是微信对缩略图的大小、title、description等参数的大小做了限制，所以有可能是大小超过了默认的范围。
         * 一般情况下缩略图超出比较常见。Title、description都是文本，一般不会超过。
         */
        Bitmap thumbBitmap = Bitmap.createScaledBitmap(thumb, THUMB_SIZE, THUMB_SIZE, true);
        thumb.recycle();
        msg.thumbData = ImageUtils.bmpToByteArray(thumbBitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = shareType;
        mWXApi.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 把网络资源图片转化成bitmap
     *
     * @param url 网络资源图片
     * @return Bitmap
     */
    public Bitmap getLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.share_img);
            return bitmap;
        }
    }

    private void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    /**
     * 压缩图片
     *
     * @param image
     * @param max
     * @return
     */
    public static byte[] compressMax(Bitmap image, int max) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        System.out.println("===share=====init==" + baos.toByteArray().length);
        while (baos.toByteArray().length / 1024 > max) {
            // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            // 这里压缩options%，把压缩后的数据存放到baos中
            if (options > 10) {
                options -= 10;// 每次都减少10
            } else {
                options -= 1;
            }
            if (options <= 0) {
                break;
            }
            System.out.println("===share=====" + options);
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            System.out.println("===share=====percent==" + baos.toByteArray().length / 1024);
        }
//		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
//		// 把压缩后的数据baos存放到ByteArrayInputStream中
//		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        // 把ByteArrayInputStream数据生成图片
        return baos.toByteArray();
    }

    public static class ShareFinishEvent {
        private boolean isSuccess;

        public ShareFinishEvent(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }
    }
}
