package com.meizu.account.pay;

import android.app.Activity;

public final class l {
    protected static int a = 0;
    protected static int b = 0;

    public static final void a(int i, int i2) {
        a = i;
        b = i2;
    }

    public static final void a(Activity activity, OutTradeOrderInfo outTradeOrderInfo, PayListener payListener) {
        new m(activity, outTradeOrderInfo, payListener, null, null).c();
    }

    public static final void a(Activity activity, OutTradeOrderInfo outTradeOrderInfo, PayListener payListener, String str, String str2) {
        new m(activity, outTradeOrderInfo, payListener, str, str2).c();
    }

    public static final void a(Activity activity, String str, double d, String str2, ICustomBusinessHandler iCustomBusinessHandler, PayListener payListener) {
        new j(activity, str, d, str2, iCustomBusinessHandler, payListener).c();
    }
}
