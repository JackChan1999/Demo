package com.meizu.cloud.app.utils.param;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.a.c;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.List;
import java.util.Locale;

public class a extends b {
    private static a h;
    private Context a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private boolean g = false;

    public static a a(Context context) {
        if (h == null) {
            h = new a(context);
        }
        return h;
    }

    protected a(Context context) {
        this.a = context.getApplicationContext();
        this.b = d.b(context);
        this.c = d.a(context);
        this.d = d.e();
        this.e = i.e(context, context.getPackageName());
        this.f = String.valueOf(i.f(context, context.getPackageName()));
        this.g = d.l(context);
    }

    public void a() {
        this.g = d.l(this.a);
    }

    public List<com.meizu.volley.b.a> b() {
        List<com.meizu.volley.b.a> paramPairs = super.b();
        paramPairs.add(new com.meizu.volley.b.a(RequestManager.IMEI, d()));
        paramPairs.add(new com.meizu.volley.b.a(RequestManager.SN, c()));
        paramPairs.add(new com.meizu.volley.b.a(RequestManager.DEVICE_MODEL, e()));
        paramPairs.add(new com.meizu.volley.b.a(RequestManager.V, this.e));
        paramPairs.add(new com.meizu.volley.b.a(RequestManager.VC, this.f));
        paramPairs.add(new com.meizu.volley.b.a(RequestManager.NET, m.c(this.a)));
        String uid = c.c(this.a);
        if (!TextUtils.isEmpty(uid)) {
            paramPairs.add(new com.meizu.volley.b.a(RequestManager.UID, uid));
        }
        paramPairs.add(new com.meizu.volley.b.a(RequestManager.FIRMWARE, d.a()));
        paramPairs.add(new com.meizu.volley.b.a(RequestManager.LOCALE, Locale.getDefault().getCountry()));
        paramPairs.add(new com.meizu.volley.b.a(RequestManager.MPV, f()));
        paramPairs.add(new com.meizu.volley.b.a(RequestManager.CUSTOM_ICON, this.g ? PushConstants.CLICK_TYPE_ACTIVITY : "0"));
        return paramPairs;
    }

    public String c() {
        if (TextUtils.isEmpty(this.b)) {
            this.b = d.b(this.a);
        }
        return this.b;
    }

    public String d() {
        if (TextUtils.isEmpty(this.c)) {
            this.c = d.a(this.a);
        }
        return this.c;
    }

    public String e() {
        if (TextUtils.isEmpty(this.d)) {
            this.d = d.a(this.a);
        }
        return this.d;
    }

    public String f() {
        String mpv = RequestManager.VALUE_APPS_FLYME5;
        if (d.k(this.a)) {
            if (x.b(this.a)) {
                return RequestManager.VALUE_GAMES_ALI;
            }
            return RequestManager.VALUE_APPS_ALI;
        } else if (x.b(this.a)) {
            return RequestManager.VALUE_GAMES_FLYME5;
        } else {
            return mpv;
        }
    }
}
