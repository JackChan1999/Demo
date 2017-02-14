package com.meizu.cloud.download.b;

public class b extends Exception {
    private int a;

    public b(int responseCode, String msg) {
        super(msg);
        this.a = responseCode;
    }

    public int a() {
        return this.a;
    }
}
