package com.meizu.account.pay;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.account.pay.a.d;
import com.meizu.account.pay.a.g;
import sdk.meizu.traffic.auth.MzAccountManager;

public final class m extends a {
    private OutTradeOrderInfo b;
    private String c;
    private String d;
    private PayListener e;

    public m(Activity activity, OutTradeOrderInfo outTradeOrderInfo, PayListener payListener, String str, String str2) {
        super(activity);
        this.b = outTradeOrderInfo;
        this.e = payListener;
        this.c = str;
        this.d = str2;
        if (this.b == null || this.e == null) {
            throw new IllegalArgumentException("Params cant be null!");
        } else if ((!TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) || (TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2))) {
            throw new IllegalArgumentException("account/pwd illegal.");
        }
    }

    protected final void a() {
        this.e.onPayResult(0, this.b, null);
    }

    protected final void a(int i, String str) {
        Log.e("SystemPayController", "service error : " + str + " , " + i);
        this.e.onPayResult(PayResultCode.fixCode(i), this.b, str);
    }

    protected final void a(g gVar, d dVar) {
        String packageName = this.a.getPackageName();
        Bundle toBundle = this.b.toBundle();
        toBundle.putString("package", packageName);
        if (!TextUtils.isEmpty(this.c)) {
            toBundle.putString(MzAccountManager.PATH_ACCOUNT, this.c);
            toBundle.putString("pwd", this.d);
        }
        toBundle.putInt("btn_color_id", l.a);
        toBundle.putInt("edittext_id", l.b);
        gVar.a(toBundle, dVar);
    }

    protected final void b() {
        Log.e("SystemPayController", "service exception.");
        this.e.onPayResult(100, this.b, "pay service exception.");
    }
}
