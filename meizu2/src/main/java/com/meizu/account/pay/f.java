package com.meizu.account.pay;

final class f implements Runnable {
    private /* synthetic */ d a;
    private final /* synthetic */ int b;
    private final /* synthetic */ String c;

    f(d dVar, int i, String str) {
        this.a = dVar;
        this.b = i;
        this.c = str;
    }

    public final void run() {
        this.a.a.a.a(this.b, this.c);
    }
}
