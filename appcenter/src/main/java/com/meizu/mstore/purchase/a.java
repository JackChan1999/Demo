package com.meizu.mstore.purchase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import com.meizu.mstore.R;

@Deprecated
public abstract class a {
    protected ProgressDialog a;
    protected Messenger b;
    protected Activity c;
    protected Messenger d;
    protected Handler e;
    protected int f;

    public abstract void a(Activity activity, Bundle bundle);

    public void a(Messenger service) {
        this.b = service;
    }

    public Messenger a() {
        return this.d;
    }

    protected void b() {
        this.a = new ProgressDialog(this.c);
        this.a.getWindow().setType(2003);
        this.a.setMessage(this.c.getString(R.string.please_wait));
        this.a.setCancelable(false);
    }
}
