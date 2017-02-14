package com.meizu.gslb.b;

import android.text.TextUtils;
import java.util.Map;

public class a {
    protected String a;
    protected String b;
    protected String c;
    protected Map<String, String> d;

    protected a(String str) {
        this.a = str;
    }

    protected a(String str, String str2, String str3, Map<String, String> map) {
        this.c = str2;
        this.a = str;
        this.b = str3;
        this.d = map;
    }

    public boolean a() {
        return (TextUtils.isEmpty(this.b) || this.b.equals(this.a) || TextUtils.isEmpty(this.c)) ? false : true;
    }

    public String b() {
        return this.c;
    }

    public String c() {
        return this.b;
    }

    public Map<String, String> d() {
        return this.d;
    }
}
