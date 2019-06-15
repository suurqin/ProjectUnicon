package com.jm.projectunion.common.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.jm.projectunion.R;
import com.jm.projectunion.common.manager.Config;
import com.jm.projectunion.common.utils.StringUtils;
import com.jm.projectunion.common.web.JSNativeInterface;

import butterknife.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * ListFragment基类
 *
 * @author Young
 * @date 2015-12-6 下午6:03:37
 */
public class BaseWebFragment<T> extends BaseFragment {

    private final static String JSNATIVE_INTERFACE_NAME_NATIVE = "native";
    public static final String WEB_URL = "web_url";
    public onTitleListener listener;
    @BindView(R.id.base_webview)
    WebView mWebView;
    @BindView(R.id.webview_pb)
    ProgressBar mPb;

    protected String strUrl;

    public static BaseWebFragment newInstance(String url) {
        BaseWebFragment fragment = new BaseWebFragment();
        Bundle bundle = new Bundle();
        bundle.putString(WEB_URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.base_fragment_web;
    }

    @Override
    public void initView(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            strUrl = bundle.getString(WEB_URL, "");
        }
    }

    @Override
    public void initData() {
        if (TextUtils.isEmpty(strUrl)) {
            showMsg("连接地址为空");
        }
        Config.NOTICE_CLASS = StringUtils.getClassName(this.getClass());
    }

    @Override
    public void onResume() {
        super.onResume();
        showDialogLoading();
        initWebView(strUrl);
    }

    @Override
    protected void onErrorMsg(String status, String message) {

    }

    @Override
    protected void onForceRefresh() {
//        mWebView.reLoadUrl();
    }

    private void initWebView(String url) {
        WebSettings settings = mWebView.getSettings();
        settings.setBlockNetworkImage(false);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        mWebView.loadUrl(url);
        mWebView.addJavascriptInterface(new JSNativeInterface(getActivity()), JSNATIVE_INTERFACE_NAME_NATIVE);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(android.webkit.WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mPb != null) {
                    if (newProgress == 100) {
                        hideDialogLoading();
                        mPb.setVisibility(View.INVISIBLE);
                    } else {
                        if (mPb.getVisibility() == View.GONE)
                            mPb.setVisibility(View.VISIBLE);
                        mPb.setProgress(newProgress);
                    }
                }
            }
        });

        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    getActivity().startActivity(intent);
                } catch (Exception e) {
                    showMsg("需要跳到第三方浏览器进行下载，请安装第三方浏览器！");
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mPb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                super.onPageFinished(view, url);
                hideDialogLoading();
                mPb.setVisibility(View.GONE);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, WebResourceRequest request) {
                String url = String.valueOf(request.getUrl());
                view.loadUrl(url);
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
    }

    public boolean canGoBack() {
        if (mWebView.canGoBack()) {
            return true;
        } else {
            return false;
        }
    }

    public void goBack() {
        mWebView.goBack();// 返回前一个页面
    }

//    private class MyWebViewClient extends BaseWebViewClient {
//        // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            if (url.startsWith("tel:")) {
//                getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//            } else {
//                view.loadUrl(url);
//            }
//            // 如果不需要其他对点击链接事件的处理返回true，否则返回false
//            return true;
//        }
//
//        @Override
//        public void onReceivedSslError(WebView webView, com.tencent.smtt.export.external.interfaces.SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
//            sslErrorHandler.proceed();  // 接受所有网站的证书,android 默认是拒绝的
//        }
//
//
//    }
//
//    private class MyWebViewDownLoadListener implements DownloadListener {
//
//        @Override
//        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
//                                    long contentLength) {
//            try {//TODO 爱多保应用网页里边的下载都跳到第三方浏览器进行下载
//                Uri uri = Uri.parse(url);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                getContext().startActivity(intent);
//            } catch (Exception e) {
//                showMsg("需要跳到第三方浏览器进行下载，请安装第三方浏览器！");
//            }
//        }
//    }

    public void setOnTitleListener(onTitleListener listener) {
        this.listener = listener;
    }

    public interface onTitleListener {
        public void onReceivedTitle(WebView view, String title);
    }

//    public class MyWebChromeClient extends WebChromeClient {
//
//        @Override
//        public void onProgressChanged(WebView view, int newProgress) {
//            if (newProgress == 100) {
//                mWebView.mProgressBar.setVisibility(GONE);
//                hideDialogLoading();
//                mWebView.isLoadCompleted = true;
//            } else {
//                if (mWebView.mProgressBar.getVisibility() == GONE) {
//                    mWebView.mProgressBar.setVisibility(VISIBLE);
//                    showDialogLoading();
//                    mWebView.isLoadCompleted = false;
//                }
//                mWebView.mProgressBar.setProgress(newProgress);
//            }
//            super.onProgressChanged(view, newProgress);
//        }
//
//        @Override
//        public void onReceivedTitle(WebView view, String title) {
//            if (mWebView.listener != null) {
//                mWebView.listener.onReceivedTitle(view, title);
//            }
//            super.onReceivedTitle(view, title);
//        }
//
//
//        // file upload callback (Android 2.2 (API level 8) -- Android 2.3 (API level 10)) (hidden method)
//        public void openFileChooser(ValueCallback<Uri> filePathCallback) {
//            handle(filePathCallback);
//        }
//
//        // file upload callback (Android 3.0 (API level 11) -- Android 4.0 (API level 15)) (hidden method)
//        public void openFileChooser(ValueCallback filePathCallback, String acceptType) {
//            handle(filePathCallback);
//        }
//
//        // file upload callback (Android 4.1 (API level 16) -- Android 4.3 (API level 18)) (hidden method)
//        public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture) {
//            handle(filePathCallback);
//        }
//
//        @Override
//        public boolean onShowFileChooser(WebView webView, com.tencent.smtt.sdk.ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
//            if (mFilePathCallbackArray != null) {
//                mFilePathCallbackArray.onReceiveValue(null);
//            }
//            mFilePathCallbackArray = valueCallback;
//
//            openImageChooserActivity();
//
//            return true;
//        }
//
//    }

    //---------------------------- 处理Web调用系统图片库START --------------------------------//

    private ValueCallback<Uri> mFilePathCallback;
    private ValueCallback<Uri[]> mFilePathCallbackArray;
    protected final static int REQUEST_CODE_GALLERY = 1008;

    /**
     * 处理5.0以下系统回调
     *
     * @param filePathCallback
     */
    private void handle(ValueCallback<Uri> filePathCallback) {
        if (mFilePathCallback != null) {
            mFilePathCallback.onReceiveValue(null);
        }
        mFilePathCallback = filePathCallback;

        openImageChooserActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (null == mFilePathCallback && null == mFilePathCallbackArray) return;
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
            if (mFilePathCallbackArray != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(result);
                mFilePathCallback = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != REQUEST_CODE_GALLERY || mFilePathCallbackArray == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mFilePathCallbackArray.onReceiveValue(results);
        mFilePathCallbackArray = null;
    }

    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "选择身份证实照"), REQUEST_CODE_GALLERY);
    }
}
