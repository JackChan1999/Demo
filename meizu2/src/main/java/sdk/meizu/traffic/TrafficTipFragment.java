package sdk.meizu.traffic;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.meizu.statsapp.UsageStatsProxy;
import defpackage.ang;
import java.net.URLDecoder;
import sdk.meizu.traffic.hybird.HyBirdView;
import sdk.meizu.traffic.interfaces.ActionBarHandler;
import sdk.meizu.traffic.util.UrlSpanHelper;

public class TrafficTipFragment extends Fragment {
    private static final String TAG = TrafficTipFragment.class.getSimpleName();
    private static final String mPageUrl = "http://magneto.meizu.com/flowrecharge/html/help.html";
    private boolean isLoadFailed = false;
    private Activity mActivity;
    private HyBirdView mContentView;
    private WebView mWebView;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mActivity = getActivity();
        this.mActivity.setTitle(R.string.title_help);
        ((ActionBarHandler) this.mActivity).enableCustomView(false);
        ((ActionBarHandler) this.mActivity).enableHomeBack(true);
        if (this.mContentView == null) {
            this.mContentView = generateContentView();
            loadPage();
        }
        return this.mContentView;
    }

    protected HyBirdView generateContentView() {
        Log.v(TAG, "generateContentView");
        this.mContentView = new HyBirdView(this.mActivity);
        this.mContentView.setNoNetworkClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ang.b(TrafficTipFragment.this.mActivity)) {
                    TrafficTipFragment.this.loadPage();
                    return;
                }
                Intent intent = new Intent("android.settings.SETTINGS");
                intent.setFlags(268435456);
                TrafficTipFragment.this.startActivity(intent);
            }
        });
        this.mWebView = this.mContentView.getWebView();
        this.mContentView.getWebView().setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                if (TrafficTipFragment.this.isLoadFailed) {
                    TrafficTipFragment.this.mWebView.setVisibility(8);
                } else {
                    TrafficTipFragment.this.mContentView.stopLoading();
                }
            }

            public void onReceivedError(WebView webView, int i, String str, String str2) {
                super.onReceivedError(webView, i, str, str2);
                TrafficTipFragment.this.isLoadFailed = true;
                if (!ang.b(TrafficTipFragment.this.mActivity)) {
                    TrafficTipFragment.this.showNoNetworkView();
                }
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (!str.startsWith("tel:")) {
                    return super.shouldOverrideUrlLoading(webView, str);
                }
                try {
                    str = URLDecoder.decode(str, "UTF-8");
                } catch (Exception e) {
                }
                UrlSpanHelper.showDialog(TrafficTipFragment.this.mActivity.getWindow().getDecorView(), str, 4);
                return true;
            }
        });
        return this.mContentView;
    }

    private void loadPage() {
        this.mContentView.postDelayed(new Runnable() {
            public void run() {
                TrafficTipFragment.this.mContentView.getWebView().loadUrl(TrafficTipFragment.mPageUrl);
                TrafficTipFragment.this.mContentView.startLoading();
            }
        }, 100);
    }

    private void showNoNetworkView() {
        this.mContentView.showNoNetwork();
    }

    public void onDestroyView() {
        if (this.mContentView != null) {
            this.mContentView.destroy();
        }
        super.onDestroyView();
    }

    public void onStart() {
        super.onStart();
        UsageStatsProxy.a(this.mActivity, true).a(TAG);
        Log.v("UsageEvent", "onStart-" + TAG);
    }

    public void onStop() {
        super.onStop();
        Log.v("UsageEvent", "onStop-" + TAG);
        UsageStatsProxy.a(this.mActivity, true).b(TAG);
    }
}
