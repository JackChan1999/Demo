package com.meizu.flyme.appcenter.fragment;

import android.support.v4.app.Fragment;
import com.meizu.cloud.app.request.RequestConstants;

public class q extends com.meizu.cloud.app.fragment.q {
    protected String a() {
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

    public Fragment b() {
        return new d();
    }
}
