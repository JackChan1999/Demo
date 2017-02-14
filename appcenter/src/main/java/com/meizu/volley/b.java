package com.meizu.volley;

import android.content.Context;
import com.android.volley.m;

public class b {
    private static b a;
    private static int[] b;
    private m c;

    public static b a(Context context) {
        if (a == null) {
            a = new b(context);
        }
        return a;
    }

    private b(Context context) {
        b(context);
    }

    private void b(Context context) {
        this.c = c.a(context.getApplicationContext(), b);
    }

    public m a() {
        return this.c;
    }
}
