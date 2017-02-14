package com.meizu.cloud.download.b;

public class a {
    private final boolean a;
    private final String b;

    private a(boolean isMatch, String errorMsg) {
        this.a = isMatch;
        this.b = errorMsg;
    }

    public static a a() {
        return new a(true, null);
    }

    public static a a(String msg) {
        return new a(false, msg);
    }

    public boolean b() {
        return this.a;
    }

    public String c() {
        return this.b;
    }
}
