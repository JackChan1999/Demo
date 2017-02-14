package com.meizu.cloud.app.fragment;

import android.content.ClipboardManager;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.webkit.JavascriptInterface;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.b.a;

public abstract class BaseEventWebviewFragmentV2 extends BaseEventWebviewFragment {
    @JavascriptInterface
    public boolean launchApp(String pkgName) {
        Intent intent = this.mActivity.getPackageManager().getLaunchIntentForPackage(pkgName);
        if (intent == null) {
            return false;
        }
        intent.setFlags(268435456);
        this.mActivity.startActivity(intent);
        return true;
    }

    @JavascriptInterface
    public boolean uninstallApp(String pkgName) {
        d.a(this.mActivity).i(pkgName);
        return false;
    }

    @JavascriptInterface
    public int getVersionCode(int appId, String pkgName) {
        return i.f(this.mActivity, pkgName);
    }

    @JavascriptInterface
    public boolean updateApp(int appId, String pkgName) {
        if (m.b(this.mActivity)) {
            requestDownload(appId, pkgName);
            return true;
        }
        showNoticeOnUi(getString(a.i.nonetwork));
        return false;
    }

    @JavascriptInterface
    public boolean setClipContent(String content) {
        ((ClipboardManager) this.mActivity.getSystemService("clipboard")).setText(content);
        return true;
    }

    @JavascriptInterface
    public String getClipContent() {
        return ((ClipboardManager) this.mActivity.getSystemService("clipboard")).getText().toString();
    }

    @JavascriptInterface
    public String getPhoneNumber() {
        String number = "";
        TelephonyManager telephonyManager = (TelephonyManager) this.mActivity.getSystemService("phone");
        if (telephonyManager != null) {
            return telephonyManager.getLine1Number();
        }
        return number;
    }
}
