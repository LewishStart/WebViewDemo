package com.lewish.start.webviewdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class NormalUseActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "NormalUseActivity";
    private Button mBtnBack;
    private Button mBtnRefresh;
    private TextView tvTitle;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_use);

        mBtnBack = (Button) findViewById(R.id.mBtnBack);
        mBtnRefresh = (Button) findViewById(R.id.mBtnRefresh);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        webView = (WebView) findViewById(R.id.webView);

        mBtnBack.setOnClickListener(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://weibo.com/");
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie("http://weibo.com/");
        Log.d(TAG, "CookieStr= "+CookieStr);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return super.shouldOverrideUrlLoading(webView, url);
            }

            /**
             * 错误码处理
             */
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                CookieManager cookieManager = CookieManager.getInstance();
//                String cookie = cookieManager.getCookie(url);
//                synCookies(NormalUseActivity.this,url,cookie);
                super.onPageFinished(view, url);
            }


        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                tvTitle.setText(title);
                super.onReceivedTitle(view, title);
            }
        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if(url.contains(".apk")) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mBtnBack:
                webView.goBack();
                break;
            case R.id.mBtnRefresh:
                webView.reload();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) (webView.getParent())).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
    /**
     * 将cookie同步到WebView
     * @param url WebView要加载的url
     * @param cookie 要同步的cookie
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public static boolean syncCookie(Context context,String url,String cookie) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(context);
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, cookie);//如果没有特殊需求，这里只需要将session id以"key=value"形式作为cookie即可
        String newCookie = cookieManager.getCookie(url);
        return TextUtils.isEmpty(newCookie)?false:true;
    }
}
