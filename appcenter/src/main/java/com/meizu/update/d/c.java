package com.meizu.update.d;

public class c extends Exception {
    private int a;

    public c(int responseCode, String illegalMsg) {
        super(illegalMsg);
        this.a = responseCode;
    }

    public int a() {
        return this.a;
    }
}
