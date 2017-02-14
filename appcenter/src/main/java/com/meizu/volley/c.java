package com.meizu.volley;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build.VERSION;
import com.android.volley.b;
import com.android.volley.m;
import com.android.volley.toolbox.e;
import com.android.volley.toolbox.h;
import com.meizu.volley.d.a;

public class c {
    public static m a(Context context, int[] unauthorizedCodes) {
        return a(context, new h(), null, unauthorizedCodes);
    }

    public static m a(Context context, b cache, e stack, int[] unauthorizedCodes) {
        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            userAgent = packageName + "/" + context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (NameNotFoundException e) {
        }
        if (stack == null) {
            if (VERSION.SDK_INT >= 9) {
                stack = new a();
            } else {
                stack = new com.android.volley.toolbox.c(AndroidHttpClient.newInstance(userAgent));
            }
        }
        m queue = new m(cache, new com.android.volley.toolbox.a(stack).a(unauthorizedCodes));
        queue.a();
        return queue;
    }
}
