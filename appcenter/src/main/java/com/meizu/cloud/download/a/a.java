package com.meizu.cloud.download.a;

import com.meizu.cloud.download.c.c;
import com.meizu.cloud.download.c.d;
import com.meizu.cloud.download.c.g.b;

public abstract class a<T> implements d<T> {
    private int a = 0;
    private c<T> b;
    private final a<T> c;
    private T d;
    private Runnable e = new Runnable(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void run() {
            this.a.a(this.a.a());
        }
    };

    public interface a<T> {
        c<T> a(b<T> bVar, d<T> dVar);

        void a(Runnable runnable);
    }

    public abstract void a(T t);

    protected abstract void b(T t);

    public abstract b<T> e();

    public a(a<T> jobExecutor) {
        this.c = jobExecutor;
    }

    public T a() {
        return this.d;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.meizu.cloud.download.c.c<T> r3) {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = 0;
        r2.cancel = r0;	 Catch:{ all -> 0x0041 }
        r0 = r3.c();	 Catch:{ all -> 0x0041 }
        r2.d = r0;	 Catch:{ all -> 0x0041 }
        r0 = r2.a;	 Catch:{ all -> 0x0041 }
        r1 = 4;
        if (r0 != r1) goto L_0x0024;
    L_0x000f:
        r0 = r2.d;	 Catch:{ all -> 0x0041 }
        if (r0 == 0) goto L_0x001b;
    L_0x0013:
        r0 = r2.d;	 Catch:{ all -> 0x0041 }
        r2.cancel(r0);	 Catch:{ all -> 0x0041 }
        r0 = 0;
        r2.d = r0;	 Catch:{ all -> 0x0041 }
    L_0x001b:
        r0 = r2.c;	 Catch:{ all -> 0x0041 }
        r1 = r2.e;	 Catch:{ all -> 0x0041 }
        r0.a(r1);	 Catch:{ all -> 0x0041 }
        monitor-exit(r2);	 Catch:{ all -> 0x0041 }
    L_0x0023:
        return;
    L_0x0024:
        r0 = r3.cancel();	 Catch:{ all -> 0x0041 }
        if (r0 == 0) goto L_0x004c;
    L_0x002a:
        r0 = r2.d;	 Catch:{ all -> 0x0041 }
        if (r0 != 0) goto L_0x004c;
    L_0x002e:
        r0 = r2.a;	 Catch:{ all -> 0x0041 }
        r1 = 1;
        if (r0 != r1) goto L_0x0044;
    L_0x0033:
        r0 = r2.c;	 Catch:{ all -> 0x0041 }
        r1 = r2.e();	 Catch:{ all -> 0x0041 }
        r0 = r0.a(r1, r2);	 Catch:{ all -> 0x0041 }
        r2.cancel = r0;	 Catch:{ all -> 0x0041 }
    L_0x003f:
        monitor-exit(r2);	 Catch:{ all -> 0x0041 }
        goto L_0x0023;
    L_0x0041:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0041 }
        throw r0;
    L_0x0044:
        r0 = r2.c;	 Catch:{ all -> 0x0041 }
        r1 = r2.e;	 Catch:{ all -> 0x0041 }
        r0.a(r1);	 Catch:{ all -> 0x0041 }
        goto L_0x003f;
    L_0x004c:
        r0 = r2.d;	 Catch:{ all -> 0x0041 }
        if (r0 != 0) goto L_0x005c;
    L_0x0050:
        r0 = 3;
    L_0x0051:
        r2.a = r0;	 Catch:{ all -> 0x0041 }
        monitor-exit(r2);	 Catch:{ all -> 0x0041 }
        r0 = r2.c;
        r1 = r2.e;
        r0.a(r1);
        goto L_0x0023;
    L_0x005c:
        r0 = 2;
        goto L_0x0051;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.download.a.a.a(com.meizu.cloud.download.c.c):void");
    }

    public synchronized void b() {
        if (this.a == 0) {
            this.a = 1;
            if (this.b == null) {
                this.b = this.c.a(e(), this);
            }
        }
    }

    public synchronized void c() {
        if (this.a == 1) {
            this.a = 0;
            if (this.b != null) {
                this.b.a();
            }
        }
    }

    public synchronized void d() {
        this.a = 4;
        if (this.d != null) {
            b(this.d);
            this.d = null;
        }
        if (this.b != null) {
            this.b.a();
        }
    }
}
