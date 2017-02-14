package com.meizu.a.a;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.a.a.a.a;
import com.meizu.a.a.a.b;

public class g extends a {
    private c b;
    private String c;
    private String d;
    private d e;

    public g(Activity activity, c order, d listener, String account, String pwd) {
        super(activity);
        this.b = order;
        this.e = listener;
        this.c = account;
        this.d = pwd;
        if (this.b == null || this.e == null) {
            throw new IllegalArgumentException("Params cant be null!");
        } else if ((!TextUtils.isEmpty(account) && TextUtils.isEmpty(pwd)) || (TextUtils.isEmpty(account) && !TextUtils.isEmpty(pwd))) {
            throw new IllegalArgumentException("account/pwd illegal.");
        }
    }

    protected void a(Bundle value) {
        Log.e("SystemPayController", "service pay success !");
        if (this.e != null) {
            this.e.a(0, this.b, null);
        } else {
            Log.e("SystemPayController", "onServiceResult while no listener!");
        }
    }

    protected void a(int errorCode, String errorMsg) {
        Log.e("SystemPayController", "service error : " + errorMsg + " , " + errorCode);
        if (this.e != null) {
            this.e.a(f.a(errorCode), this.b, errorMsg);
        } else {
            Log.e("SystemPayController", "onServiceError while no listener!");
        }
    }

    protected void a() {
        Log.e("SystemPayController", "service exception.");
        if (this.e != null) {
            this.e.a(100, this.b, "pay service exception.");
        } else {
            Log.e("SystemPayController", "onServiceException while no listener!");
        }
    }

    protected void a(b service, a response) throws RemoteException {
        String packageName = a.getPackageName();
        Bundle extras = this.b.m();
        extras.putString("package", packageName);
        if (!TextUtils.isEmpty(this.c)) {
            extras.putString("account", this.c);
            extras.putString("pwd", this.d);
        }
        service.a(extras, response);
    }

    protected void c() {
        super.c();
        this.e = null;
    }
}
