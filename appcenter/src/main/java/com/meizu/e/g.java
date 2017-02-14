package com.meizu.e;

public class g {
    public static final g a = new g("10.0.0.172", 80);
    private static g b = null;
    private String c = null;
    private int d = 0;

    public static boolean a() {
        return b != null && b.f();
    }

    public static String b() {
        return a() ? b.d() : null;
    }

    public static int c() {
        return a() ? b.e() : 0;
    }

    private g(String proxyHost, int proxyPort) {
        this.c = proxyHost;
        this.d = proxyPort;
    }

    private String d() {
        return this.c;
    }

    private int e() {
        return this.d;
    }

    private boolean f() {
        return this.c != null && this.c.length() > 0;
    }
}
