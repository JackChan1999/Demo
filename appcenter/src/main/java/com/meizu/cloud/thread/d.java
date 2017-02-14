package com.meizu.cloud.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class d implements c, Callable<Boolean> {
    private final FutureTask<Boolean> a = new FutureTask(this);
    private final Runnable b;
    private final e c;
    private final a d;

    interface a {
        void a(d dVar);
    }

    public /* synthetic */ Object call() throws Exception {
        return c();
    }

    protected d(Runnable runnable, e observer, a cleaner) {
        this.b = runnable;
        this.c = observer;
        this.d = cleaner;
    }

    public Boolean c() throws Exception {
        this.b.run();
        e();
        f();
        return Boolean.valueOf(true);
    }

    public FutureTask<Boolean> d() {
        return this.a;
    }

    private void e() {
        if (this.d != null) {
            this.d.a(this);
        }
    }

    private void f() {
        if (this.c != null) {
            this.c.a();
        }
    }

    public boolean a() {
        return this.a.isDone();
    }

    public boolean b() {
        boolean res = this.a.cancel(true);
        e();
        return res;
    }
}
