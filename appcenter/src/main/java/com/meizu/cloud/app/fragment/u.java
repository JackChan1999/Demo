package com.meizu.cloud.app.fragment;

import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.meizu.cloud.app.core.e;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.b.a;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.base.b.g;
import com.meizu.cloud.thread.c;
import java.util.List;

public abstract class u extends g {
    protected FragmentActivity mActivity;
    private boolean mHasFirstLoad = false;
    protected c mPagerTask;
    protected t mViewController;
    private WebView mWebView;

    public abstract List<e> createJavascriptInterfaces();

    public abstract WebChromeClient createWebChromeClient();

    public abstract WebViewClient createWebviewClient();

    public abstract void loadData();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = getRootFragment().getActivity();
        this.mViewController = new t(getActivity(), new com.meizu.cloud.app.core.u());
        this.mPageInfo[1] = 15;
        this.mViewController.a(this.mPageInfo);
        this.mViewController.a(this.mPageName);
    }

    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(a.g.base_webview_fragment, container, false);
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        d.b(getActivity(), (RelativeLayout) rootView.findViewById(f.activity_webView_container));
        this.mWebView = (WebView) rootView.findViewById(f.webView);
        WebSettings settings = this.mWebView.getSettings();
        settings.setCacheMode(2);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setPluginState(PluginState.ON);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        this.mWebView.setWebViewClient(createWebviewClient());
        this.mWebView.setWebChromeClient(createWebChromeClient());
        List<e> bridgeList = createJavascriptInterfaces();
        if (bridgeList != null) {
            for (e jsBridge : bridgeList) {
                this.mWebView.addJavascriptInterface(jsBridge.b(), jsBridge.a());
            }
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!this.mHasFirstLoad) {
            loadData();
            this.mHasFirstLoad = true;
        }
    }

    protected void loadUrlWithParams(final String url) {
        runOnUi(new Runnable(this) {
            final /* synthetic */ u b;

            public void run() {
                if (this.b.getWebView() != null && !TextUtils.isEmpty(url)) {
                    List<com.meizu.volley.b.a> mapList = com.meizu.cloud.app.utils.param.a.a(this.b.mActivity).b();
                    Builder builder = Uri.parse(url).buildUpon();
                    for (com.meizu.volley.b.a paramPair : mapList) {
                        builder.appendQueryParameter(paramPair.getName(), paramPair.getValue());
                    }
                    this.b.mWebView.loadUrl(builder.build().toString());
                }
            }
        });
    }

    protected void loadHtmlPage(final String url) {
        runOnUi(new Runnable(this) {
            final /* synthetic */ u b;

            public void run() {
                if (this.b.getWebView() == null || TextUtils.isEmpty(url)) {
                    this.b.showEmptyView(this.b.getEmptyTextString(), null, null);
                } else {
                    this.b.mWebView.loadUrl(url);
                }
            }
        });
    }

    protected void loadJavaScript(final String url) {
        runOnUi(new Runnable(this) {
            final /* synthetic */ u b;

            public void run() {
                if (this.b.getWebView() != null && !TextUtils.isEmpty(url)) {
                    this.b.mWebView.loadUrl(url);
                }
            }
        });
    }

    public void showContent() {
        if (getView() != null && getWebView() != null) {
            hideProgress();
            hideEmptyView();
            if (this.mWebView != null) {
                this.mWebView.setVisibility(0);
            }
        }
    }

    public void hideContent() {
        if (getView() != null && getWebView() != null && this.mWebView != null) {
            this.mWebView.setVisibility(8);
        }
    }

    public void showEmptyView(String text, boolean showRetry) {
        if (showRetry) {
            showEmptyView(text, null, new OnClickListener(this) {
                final /* synthetic */ u a;

                {
                    this.a = r1;
                }

                public void onClick(View v) {
                    this.a.loadData();
                }
            });
        } else {
            showEmptyView(text, null, null);
        }
    }

    protected void showNoticeOnUi(final String msg) {
        runOnUi(new Runnable(this) {
            final /* synthetic */ u b;

            public void run() {
                Toast.makeText(this.b.getActivity(), msg, 0).show();
            }
        });
    }

    public void onPause() {
        super.onPause();
        if (this.mWebView != null) {
            this.mWebView.onPause();
        }
    }

    public void onResume() {
        if (this.mWebView != null) {
            this.mWebView.onResume();
        }
        super.onResume();
    }

    public void onDestroy() {
        if (this.mWebView != null) {
            this.mWebView.destroy();
        }
        super.onDestroy();
    }

    public WebView getWebView() {
        return this.mWebView;
    }

    protected void webViewGoback() {
        WebView webView = getWebView();
        if (webView != null && webView.canGoBack()) {
            getWebView().goBack();
        }
    }
}
