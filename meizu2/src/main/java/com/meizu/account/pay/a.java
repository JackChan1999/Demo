package com.meizu.account.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import com.meizu.account.pay.a.d;
import com.meizu.account.pay.a.g;
import com.meizu.account.pay.a.j;

public abstract class a {
    protected Activity a;
    private j b;
    private Handler c = new Handler(this.a.getMainLooper());

    public a(Activity activity) {
        this.a = activity;
        if (this.a == null) {
            throw new IllegalArgumentException("activity cant be null!");
        }
        this.b = new j(this.a, new b(), "com.meizu.account.pay.SystemPayService", "com.meizu.account");
    }

    static /* synthetic */ void a(a aVar, Intent intent) {
        try {
            aVar.a.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            aVar.a(new i(aVar));
        }
    }

    private void a(Runnable runnable) {
        this.c.post(runnable);
    }

    protected abstract void a();

    protected abstract void a(int i, String str);

    protected abstract void a(g gVar, d dVar);

    protected abstract void b();

    public final void c() {
        new c(this).execute(new Void[0]);
    }
}
