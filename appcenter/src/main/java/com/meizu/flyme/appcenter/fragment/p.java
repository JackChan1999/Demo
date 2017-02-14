package com.meizu.flyme.appcenter.fragment;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.meizu.cloud.app.a.a;
import com.meizu.cloud.app.fragment.o;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.flyme.appcenter.a.d;

public class p extends o {
    protected String a() {
        if (this.g && !TextUtils.isEmpty(this.h)) {
            return RequestConstants.APP_CENTER_HOST + this.h;
        }
        String url = getArguments().getString("url", "");
        if (url.startsWith(RequestConstants.APP_CENTER_HOST)) {
            return url;
        }
        return RequestConstants.APP_CENTER_HOST + url;
    }

    public Fragment b() {
        return new d();
    }

    public a f() {
        d adapter = new d(getActivity(), this.b);
        if (this.d) {
            adapter.c();
        }
        return adapter;
    }
}
