package com.meizu.flyme.appcenter.fragment;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import com.meizu.cloud.app.fragment.BaseEventWebviewFragmentV3;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.base.b.d;
import com.meizu.cloud.statistics.b;
import com.meizu.cloud.statistics.c;

public class AppEventWebviewFragment extends BaseEventWebviewFragmentV3 {
    public String getRequestUrl() {
        String url = "";
        if (getArguments() == null) {
            return url;
        }
        url = getArguments().getString("url", url);
        if (url.startsWith(RequestConstants.APP_CENTER_HOST)) {
            return url;
        }
        return RequestConstants.APP_CENTER_HOST + url;
    }

    @JavascriptInterface
    public void gotoAppInfoPage(final String pkgName) {
        runOnUi(new Runnable(this) {
            final /* synthetic */ AppEventWebviewFragment b;

            public void run() {
                Bundle bundle = new Bundle();
                bundle.putString("package_name", pkgName);
                bundle.putInt("source_page_id", 15);
                bundle.putString("source_page", this.b.mPageName);
                d appDetailFragment = new d();
                appDetailFragment.setArguments(bundle);
                d.startFragment(this.b.getActivity(), appDetailFragment);
                b.a().a("item", this.b.mPageName, c.a(pkgName));
            }
        });
    }
}
