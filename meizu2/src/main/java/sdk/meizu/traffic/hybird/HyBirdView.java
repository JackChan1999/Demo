package sdk.meizu.traffic.hybird;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.meizu.common.widget.EmptyView;
import com.meizu.common.widget.LoadingView;
import sdk.meizu.traffic.R;
import sdk.meizu.traffic.util.ThemeUtil;

public class HyBirdView extends FrameLayout {
    private View mLoadingLayout;
    private TextView mLoadingText;
    private LoadingView mLoadingView;
    private EmptyView mNoNetworkView;
    private WebView mWebView;

    public HyBirdView(Context context) {
        super(context);
        setLayoutParams(new LayoutParams(-1, -1));
        this.mWebView = new WebView(context);
        this.mWebView.setLayoutParams(new LayoutParams(-1, -1));
        this.mWebView.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View view) {
                return true;
            }
        });
        WebSettings settings = this.mWebView.getSettings();
        if (VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(2);
        }
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setDomStorageEnabled(true);
        this.mLoadingLayout = inflate(getContext(), R.layout.loading_progress_container, null);
        this.mLoadingView = (LoadingView) this.mLoadingLayout.findViewById(R.id.progress_bar);
        this.mLoadingText = (TextView) this.mLoadingLayout.findViewById(R.id.progress_text);
        this.mLoadingView.setBarBackgroundColor(ThemeUtil.getCurrentThemeColor(getContext()));
        this.mLoadingView.setBarColor(ThemeUtil.getCurrentThemeColor(getContext()));
        this.mLoadingLayout.setLayoutParams(new LayoutParams(-1, -1));
        this.mNoNetworkView = (EmptyView) LayoutInflater.from(context).inflate(R.layout.no_network_view, null);
        this.mNoNetworkView.setTitleColor(ThemeUtil.getCurrentThemeColor(context));
        addView(this.mNoNetworkView);
        addView(this.mLoadingLayout);
        addView(this.mWebView);
    }

    public WebView getWebView() {
        return this.mWebView;
    }

    public void destroy() {
        if (this.mWebView != null) {
            this.mWebView.setOnLongClickListener(null);
            removeView(this.mWebView);
            this.mWebView.removeAllViews();
            this.mWebView.destroy();
            this.mWebView.setWebViewClient(null);
        }
        removeAllViews();
    }

    public void startLoading(String str) {
        this.mLoadingText.setText(str);
        this.mLoadingView.startAnimator();
        this.mLoadingView.setVisibility(0);
        this.mWebView.setVisibility(8);
        this.mNoNetworkView.setVisibility(8);
        this.mLoadingLayout.setVisibility(0);
    }

    public void showNoNetwork() {
        this.mLoadingView.stopAnimator();
        this.mLoadingView.setVisibility(8);
        this.mLoadingLayout.setVisibility(8);
        this.mWebView.setVisibility(8);
        this.mNoNetworkView.setVisibility(0);
    }

    public void startLoading() {
        startLoading(getResources().getString(R.string.msg_loading));
    }

    public void stopLoading() {
        this.mLoadingView.stopAnimator();
        this.mLoadingView.setVisibility(8);
        this.mNoNetworkView.setVisibility(8);
        this.mWebView.setVisibility(0);
        this.mLoadingLayout.setVisibility(8);
    }

    public void setNoNetworkClickListener(OnClickListener onClickListener) {
        this.mNoNetworkView.setOnClickListener(onClickListener);
    }
}
