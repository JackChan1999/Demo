package com.meizu.gslb.b;

import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.HashSet;
import java.util.Set;

public class i {
    private static Set<Integer> a;
    private String b;
    private String c;
    private String d;
    private a e;

    public enum a {
        SUCCESS,
        SUCCESS_WEAK,
        ERROR,
        ERROR_WEAK
    }

    private i(String str, String str2) {
        this.b = str;
        this.c = new c(str).a();
        if (!TextUtils.isEmpty(str2)) {
            this.d = new c(str2).a();
        }
    }

    private static synchronized a a(int i) {
        a aVar;
        synchronized (i.class) {
            Object obj = (a == null || !a.contains(Integer.valueOf(i))) ? null : 1;
            if (obj != null || i == 200 || i == 304) {
                aVar = a.SUCCESS;
            } else {
                String valueOf = String.valueOf(i);
                aVar = valueOf.startsWith(PushConstants.CLICK_TYPE_ACTIVITY) ? a.ERROR_WEAK : valueOf.startsWith(PushConstants.CLICK_TYPE_WEB) ? a.SUCCESS_WEAK : valueOf.startsWith("3") ? a.SUCCESS_WEAK : valueOf.startsWith("4") ? (i == 401 || i == 407) ? a.SUCCESS_WEAK : a.ERROR : valueOf.startsWith("5") ? a.ERROR : a.ERROR;
            }
        }
        return aVar;
    }

    private static a a(Exception exception) {
        return a.ERROR;
    }

    public static i a(String str, String str2, int i) {
        i iVar = new i(str, str2);
        iVar.e = a(i);
        com.meizu.gslb.g.a.a("handle request response code:" + i);
        return iVar;
    }

    public static i a(String str, String str2, Exception exception) {
        i iVar = new i(str, str2);
        iVar.e = a(exception);
        com.meizu.gslb.g.a.a("handle request response exception:" + exception.getMessage());
        return iVar;
    }

    public static synchronized void a(int[] iArr) {
        synchronized (i.class) {
            if (iArr != null) {
                if (iArr.length > 0) {
                    if (a == null) {
                        a = new HashSet(iArr.length);
                    } else {
                        a.clear();
                    }
                    for (int valueOf : iArr) {
                        a.add(Integer.valueOf(valueOf));
                    }
                }
            }
            a = null;
        }
    }

    public boolean a() {
        return a.SUCCESS.equals(this.e);
    }

    public String b() {
        return this.c;
    }

    public String c() {
        return this.d;
    }

    public String d() {
        return this.b;
    }

    public a e() {
        return this.e;
    }

    public boolean f() {
        return !TextUtils.isEmpty(this.d);
    }

    public boolean g() {
        return (a.SUCCESS.equals(this.e) || a.SUCCESS_WEAK.equals(this.e)) ? false : true;
    }
}
