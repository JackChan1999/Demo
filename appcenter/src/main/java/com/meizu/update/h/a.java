package com.meizu.update.h;

public class a extends Exception {
    private int a;
    private boolean b = false;

    public a(int responseCode, String msg) {
        super(msg);
        this.a = responseCode;
        this.b = true;
    }

    public a(String msg) {
        super(msg);
    }

    public int a() {
        return this.a;
    }

    public boolean b() {
        return this.b;
    }
}
