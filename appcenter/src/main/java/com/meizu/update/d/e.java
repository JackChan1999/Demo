package com.meizu.update.d;

public class e extends Exception {
    private static final long serialVersionUID = 1;
    private int a;

    public e(int responseCode, String msg) {
        super(msg);
        this.a = responseCode;
    }

    public int a() {
        return this.a;
    }
}
