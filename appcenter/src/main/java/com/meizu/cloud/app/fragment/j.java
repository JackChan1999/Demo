package com.meizu.cloud.app.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.meizu.cloud.app.core.e;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.statistics.UxipPageSourceInfo;
import com.meizu.cloud.statistics.b;
import com.meizu.cloud.statistics.c;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class j extends u {
    private String a;
    private UxipPageSourceInfo b;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPageName = "Activity_" + getArguments().getString("title_name", "");
        if (getArguments().containsKey("uxip_page_source_info")) {
            this.b = (UxipPageSourceInfo) getArguments().getParcelable("uxip_page_source_info");
        } else if (getArguments().containsKey("source_page")) {
            this.a = getArguments().getString("source_page", "");
        }
    }

    protected void setupActionBar() {
        super.setupActionBar();
        Bundle bundle = getArguments();
        if (bundle != null) {
            CharSequence title = bundle.getString("title_name");
            if (!TextUtils.isEmpty(title)) {
                getActionBar().a(title);
            }
        }
    }

    public void loadData() {
        if (m.b(this.mActivity)) {
            Bundle bundle = getArguments();
            if (bundle != null) {
                String url = bundle.getString("url");
                if (TextUtils.isEmpty(url)) {
                    showEmptyView(getEmptyTextString(), false);
                    return;
                } else {
                    loadHtmlPage(url);
                    return;
                }
            }
            return;
        }
        showEmptyView(getEmptyTextString(), true);
    }

    public WebChromeClient createWebChromeClient() {
        return new WebChromeClient(this) {
            final /* synthetic */ j a;

            {
                this.a = r1;
            }
        };
    }

    public WebViewClient createWebviewClient() {
        return new WebViewClient(this) {
            final /* synthetic */ j a;
            private boolean b = false;

            {
                this.a = r2;
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                this.b = false;
                this.a.showProgress();
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (this.b) {
                    this.a.hideContent();
                } else {
                    this.a.showContent();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                this.b = true;
                this.a.showEmptyView(this.a.getEmptyTextString(), true);
            }
        };
    }

    public List<e> createJavascriptInterfaces() {
        return null;
    }

    protected void onRealPageStart() {
        super.onRealPageStart();
        b.a().a(this.mPageName);
    }

    protected void onRealPageStop() {
        super.onRealPageStop();
        b.a().a(this.mPageName, a());
    }

    public Map<String, String> a() {
        Map<String, String> wdmParamsMap = new HashMap();
        wdmParamsMap.put("topicid", String.valueOf(c.f(getArguments().getString("url", ""))));
        wdmParamsMap.put("topicname", getArguments().getString("title_name", ""));
        if (this.b != null) {
            wdmParamsMap.put("source_page", this.b.f);
            wdmParamsMap.put("source_block_id", String.valueOf(this.b.b));
            wdmParamsMap.put("source_block_name", this.b.c);
            wdmParamsMap.put("source_block_type", this.b.a);
            if (this.b.g > 0) {
                wdmParamsMap.put("source_block_profile_id", String.valueOf(this.b.g));
            }
            wdmParamsMap.put("source_pos", String.valueOf(this.b.d));
            if (this.b.e > 0) {
                wdmParamsMap.put("source_hor_pos", String.valueOf(this.b.e));
            }
        } else if (!TextUtils.isEmpty(this.a)) {
            wdmParamsMap.put("source_page", this.a);
        }
        long pushId = getArguments().getLong("push_message_id", 0);
        if (pushId > 0) {
            wdmParamsMap.put("push_id", String.valueOf(pushId));
        }
        return wdmParamsMap;
    }
}
