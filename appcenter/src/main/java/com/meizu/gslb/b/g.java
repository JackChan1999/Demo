package com.meizu.gslb.b;

import android.content.Context;
import android.os.SystemClock;

class g {
    private String a;
    private long b;
    private h c;

    public g(Context context, String str) {
        this.a = str;
        a(context);
    }

    public void a(Context context) {
        this.b = SystemClock.elapsedRealtime();
        this.c = h.b(context);
    }

    public boolean a(String str) {
        return this.a.equals(str);
    }

    public boolean b(Context context) {
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.b;
        return this.c.a(context) ? elapsedRealtime >= 300000 : elapsedRealtime >= 10000;
    }
}
