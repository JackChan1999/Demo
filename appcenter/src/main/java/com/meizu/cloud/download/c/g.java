package com.meizu.cloud.download.c;

import android.util.Log;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class g {
    public static final c a = new d();
    private static g d;
    e b = new e(2);
    e c = new e(2);
    private final Executor e = new ThreadPoolExecutor(4, 8, 10, TimeUnit.SECONDS, new LinkedBlockingQueue(), new f("thread-pool", 10));

    public interface b<T> {
        T b(c cVar);
    }

    public interface a {
        void a();
    }

    public interface c {
        void a(a aVar);

        boolean b();
    }

    private static class d implements c {
        private d() {
        }

        public boolean b() {
            return false;
        }

        public void a(a listener) {
        }
    }

    private static class e {
        public int a;

        public e(int v) {
            this.a = v;
        }
    }

    private class f<T> implements c<T>, c, Runnable {
        final /* synthetic */ g a;
        private b<T> b;
        private d<T> c;
        private a d;
        private e e;
        private volatile boolean f;
        private boolean g;
        private T h;
        private int i;

        public f(g gVar, b<T> job, d<T> listener) {
            this.a = gVar;
            this.b = job;
            this.c = listener;
        }

        public void run() {
            Object result = null;
            if (a(1)) {
                try {
                    result = this.b.b(this);
                } catch (Throwable ex) {
                    Log.w("Worker", "Exception in running a job", ex);
                }
            }
            synchronized (this) {
                a(0);
                this.h = result;
                this.g = true;
                notifyAll();
            }
            if (this.c != null) {
                this.c.a(this);
            }
        }

        public synchronized void a() {
            if (!this.f) {
                this.f = true;
                if (this.e != null) {
                    synchronized (this.e) {
                        this.e.notifyAll();
                    }
                }
                if (this.d != null) {
                    this.d.a();
                }
            }
        }

        public boolean b() {
            return this.f;
        }

        public synchronized T c() {
            while (!this.g) {
                try {
                    wait();
                } catch (Exception ex) {
                    Log.w("Worker", "ingore exception", ex);
                }
            }
            return this.h;
        }

        public synchronized void a(a listener) {
            this.d = listener;
            if (this.f && this.d != null) {
                this.d.a();
            }
        }

        public boolean a(int mode) {
            e rc = b(this.i);
            if (rc != null) {
                b(rc);
            }
            this.i = 0;
            rc = b(mode);
            if (rc != null) {
                if (!a(rc)) {
                    return false;
                }
                this.i = mode;
            }
            return true;
        }

        private e b(int mode) {
            if (mode == 1) {
                return this.a.b;
            }
            if (mode == 2) {
                return this.a.c;
            }
            return null;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean a(com.meizu.cloud.download.c.g.e r2) {
            /*
            r1 = this;
        L_0x0000:
            monitor-enter(r1);
            r0 = r1.f;	 Catch:{ all -> 0x0021 }
            if (r0 == 0) goto L_0x000b;
        L_0x0005:
            r0 = 0;
            r1.e = r0;	 Catch:{ all -> 0x0021 }
            r0 = 0;
            monitor-exit(r1);	 Catch:{ all -> 0x0021 }
        L_0x000a:
            return r0;
        L_0x000b:
            r1.e = r2;	 Catch:{ all -> 0x0021 }
            monitor-exit(r1);	 Catch:{ all -> 0x0021 }
            monitor-enter(r2);
            r0 = r2.a;	 Catch:{ all -> 0x0029 }
            if (r0 <= 0) goto L_0x0024;
        L_0x0013:
            r0 = r2.a;	 Catch:{ all -> 0x0029 }
            r0 = r0 + -1;
            r2.a = r0;	 Catch:{ all -> 0x0029 }
            monitor-exit(r2);	 Catch:{ all -> 0x0029 }
            monitor-enter(r1);
            r0 = 0;
            r1.e = r0;	 Catch:{ all -> 0x002c }
            monitor-exit(r1);	 Catch:{ all -> 0x002c }
            r0 = 1;
            goto L_0x000a;
        L_0x0021:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0021 }
            throw r0;
        L_0x0024:
            r2.wait();	 Catch:{ InterruptedException -> 0x002f }
        L_0x0027:
            monitor-exit(r2);	 Catch:{ all -> 0x0029 }
            goto L_0x0000;
        L_0x0029:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0029 }
            throw r0;
        L_0x002c:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x002c }
            throw r0;
        L_0x002f:
            r0 = move-exception;
            goto L_0x0027;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.download.c.g.f.a(com.meizu.cloud.download.c.g$e):boolean");
        }

        private void b(e counter) {
            synchronized (counter) {
                counter.a++;
                counter.notifyAll();
            }
        }
    }

    public static synchronized g a() {
        g gVar;
        synchronized (g.class) {
            if (d == null) {
                d = new g();
            }
            gVar = d;
        }
        return gVar;
    }

    public <T> c<T> a(b<T> job, d<T> listener) {
        f<T> w = new f(this, job, listener);
        this.e.execute(w);
        return w;
    }

    public <T> c<T> a(b<T> job) {
        return a(job, null);
    }
}
