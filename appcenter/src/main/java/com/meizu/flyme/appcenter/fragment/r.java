package com.meizu.flyme.appcenter.fragment;

import android.support.v4.app.Fragment;
import com.meizu.cloud.app.request.RequestConstants;

public class r extends com.meizu.cloud.app.fragment.r {
    protected String a() {
        String url = "http://api-app.meizu.com/apps/public/special/layout";
        if (getArguments() == null) {
            return url;
        }
        url = getArguments().getString("url", url);
        if (url.startsWith(RequestConstants.APP_CENTER_HOST)) {
            return url;
        }
        return RequestConstants.APP_CENTER_HOST + url;
    }

    public Fragment b() {
        return new q();
    }

    public Fragment c() {
        return new AppEventWebviewFragment();
    }
}
