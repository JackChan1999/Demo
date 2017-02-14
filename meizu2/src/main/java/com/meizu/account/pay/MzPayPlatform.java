package com.meizu.account.pay;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;

public class MzPayPlatform {
    private static final int getServiceVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.meizu.account", 0);
            return packageInfo != null ? packageInfo.versionCode : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static final boolean inputerAvailable(Context context) {
        return getServiceVersion(context) >= 122;
    }

    public static final void pay(Activity activity, OutTradeOrderInfo outTradeOrderInfo, PayListener payListener) {
        l.a(activity, outTradeOrderInfo, payListener);
    }

    public static final void pay(Activity activity, OutTradeOrderInfo outTradeOrderInfo, PayListener payListener, String str, String str2) {
        l.a(activity, outTradeOrderInfo, payListener, str, str2);
    }

    public static final void payCustom(Activity activity, String str, double d, String str2, ICustomBusinessHandler iCustomBusinessHandler, PayListener payListener) {
        l.a(activity, str, d, str2, iCustomBusinessHandler, payListener);
    }

    public static final boolean serviceAvailable(Context context) {
        return getServiceVersion(context) >= 100;
    }

    public static final void setCustomTheme(int i, int i2) {
        l.a(i, i2);
    }
}
