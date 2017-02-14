package com.meizu.update.d.c;

public class c {
    private final boolean a;
    private final String b;

    private c(boolean isMatch, String errorMsg) {
        this.a = isMatch;
        this.b = errorMsg;
    }

    protected static c a() {
        return new c(true, null);
    }

    protected static c a(String msg) {
        return new c(false, msg);
    }

    public boolean b() {
        return this.a;
    }

    public String c() {
        return this.b;
    }
}
