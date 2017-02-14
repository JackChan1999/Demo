package com.meizu.a.a;

import android.os.Bundle;

public class c {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;

    public c a(String partner) {
        this.a = partner;
        return this;
    }

    public c b(String subject) {
        this.b = subject;
        return this;
    }

    public c c(String totalFee) {
        this.c = totalFee;
        return this;
    }

    public c d(String outTrade) {
        this.d = outTrade;
        return this;
    }

    public c e(String notifyUrl) {
        this.e = notifyUrl;
        return this;
    }

    public c f(String sign) {
        this.h = sign;
        return this;
    }

    public c g(String signType) {
        this.i = signType;
        return this;
    }

    public c h(String payAccounts) {
        this.j = payAccounts;
        return this;
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public String c() {
        return this.c;
    }

    public String d() {
        return this.d;
    }

    public String e() {
        return this.e;
    }

    public String f() {
        return this.f;
    }

    public String g() {
        return this.g;
    }

    public String h() {
        return this.h;
    }

    public String i() {
        return this.i;
    }

    public String j() {
        return this.j;
    }

    public String k() {
        return this.k;
    }

    public String l() {
        return this.l;
    }

    public Bundle m() {
        Bundle bundle = new Bundle();
        bundle.putString("extContent", g());
        bundle.putString("label1", k());
        bundle.putString("label2", l());
        bundle.putString("body", f());
        bundle.putString("notifyUrl", e());
        bundle.putString("outTrade", d());
        bundle.putString("partner", a());
        bundle.putString("payAccounts", j());
        bundle.putString("sign", h());
        bundle.putString("signType", i());
        bundle.putString("subject", b());
        bundle.putString("totalFee", c());
        return bundle;
    }
}
