package com.meizu.flyme.appcenter.fragment;

import android.support.v4.app.Fragment;
import com.meizu.cloud.app.fragment.g;
import com.meizu.cloud.app.request.RequestConstants;

public class c extends g {
    public String e() {
        return RequestConstants.APP_CENTER_HOST;
    }

    public Fragment b(int position) {
        return new p();
    }
}
