package com.meizu.account.pay;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.meizu.account.pay.a.a;
import com.meizu.account.pay.a.d;
import com.meizu.account.pay.a.g;

public final class j extends a {
    private String b;
    private double c;
    private String d;
    private ICustomBusinessHandler e;
    private PayListener f;
    private OutTradeOrderInfo g;
    private a h = new k(this);

    public j(Activity activity, String str, double d, String str2, ICustomBusinessHandler iCustomBusinessHandler, PayListener payListener) {
        super(activity);
        this.b = str;
        this.c = d;
        this.d = str2;
        this.e = iCustomBusinessHandler;
        this.f = payListener;
        if (this.b == null || this.c <= 0.0d || this.e == null || this.f == null) {
            throw new IllegalArgumentException("Params cant be null!");
        }
    }

    protected final void a() {
        this.f.onPayResult(0, this.g, null);
    }

    protected final void a(int i, String str) {
        Log.e("AccountInputController", "service error : " + str + " , " + i);
        this.f.onPayResult(PayResultCode.fixCode(i), this.g, str);
    }

    protected final void a(g gVar, d dVar) {
        String packageName = this.a.getPackageName();
        Bundle bundle = new Bundle();
        bundle.putString("package", packageName);
        bundle.putString("show_order_title", this.b);
        bundle.putDouble("show_order_amount", this.c);
        bundle.putString("token_scope", this.d);
        bundle.putInt("btn_color_id", l.a);
        bundle.putInt("edittext_id", l.b);
        gVar.a(bundle, dVar, this.h);
    }

    protected final void b() {
        Log.e("AccountInputController", "service exception.");
        this.f.onPayResult(100, this.g, "pay service exception.");
    }
}
