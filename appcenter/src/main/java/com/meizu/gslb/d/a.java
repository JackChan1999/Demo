package com.meizu.gslb.d;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import java.util.List;
import java.util.Map;

public abstract class a implements f {
    protected String a;
    protected String b;
    protected Map<String, String> c;
    protected List<Pair<String, String>> d;
    protected List<Pair<String, String>> e;
    protected List<Pair<String, String>> f;

    protected a(String str, List<Pair<String, String>> list) {
        this(str, list, null);
    }

    protected a(String str, List<Pair<String, String>> list, List<Pair<String, String>> list2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("url cant be null");
        }
        this.a = str;
        this.d = list;
        this.e = list2;
    }

    public String a() {
        return this.a;
    }

    public void a(String str) {
        this.b = str;
    }

    public void a(List<Pair<String, String>> list) {
        this.f = list;
    }

    public void a(Map<String, String> map) {
        this.c = map;
    }

    public List<Pair<String, String>> b() {
        return this.e;
    }

    public List<Pair<String, String>> c() {
        return this.f;
    }

    public boolean d() {
        return (TextUtils.isEmpty(this.b) || this.b.equals(this.a)) ? false : true;
    }

    public boolean e() {
        return this.a.startsWith("https");
    }

    public String f() {
        return TextUtils.isEmpty(this.b) ? this.a : this.b;
    }

    protected String g() {
        return Uri.parse(this.a).getHost();
    }

    protected c h() {
        return new c(g());
    }
}
