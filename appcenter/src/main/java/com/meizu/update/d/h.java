package com.meizu.update.d;

public class h extends Exception {
    private int a;

    public h(int responseCode, String relocateUrl) {
        super(relocateUrl);
        this.a = responseCode;
    }
}
