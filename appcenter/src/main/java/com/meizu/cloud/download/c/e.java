package com.meizu.cloud.download.c;

import android.util.Log;
import com.meizu.cloud.download.c.g.b;
import java.util.LinkedList;

public class e implements d {
    private final LinkedList<a<?>> a = new LinkedList();
    private final g b;
    private int c;

    private static class a<T> implements c<T>, b<T> {
        private int a = 0;
        private b<T> b;
        private c<T> c;
        private d<T> d;
        private T e;

        public a(b<T> job, d<T> listener) {
            this.b = job;
            this.d = listener;
        }

        public synchronized void a(c<T> future) {
            if (this.a == 0) {
                this.c = future;
            }
        }

        public void a() {
            d<T> listener = null;
            synchronized (this) {
                if (this.a != 1) {
                    listener = this.d;
                    this.b = null;
                    this.d = null;
                    if (this.c != null) {
                        this.c.a();
                        this.c = null;
                    }
                }
                this.a = 2;
                this.e = null;
                notifyAll();
            }
            if (listener != null) {
                listener.a(this);
            }
        }

        public synchronized boolean b() {
            return this.a == 2;
        }

        public synchronized T c() {
            while (this.a == 0) {
                e.a((Object) this);
            }
            return this.e;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public T b(com.meizu.cloud.download.c.g.c r10) {
            /*
            r9 = this;
            r8 = 2;
            r4 = 0;
            r0 = 0;
            monitor-enter(r9);
            r5 = r9.a;	 Catch:{ all -> 0x001c }
            if (r5 != r8) goto L_0x000b;
        L_0x0008:
            monitor-exit(r9);	 Catch:{ all -> 0x001c }
            r2 = r4;
        L_0x000a:
            return r2;
        L_0x000b:
            r0 = r9.cancel;	 Catch:{ all -> 0x001c }
            monitor-exit(r9);	 Catch:{ all -> 0x001c }
            r2 = 0;
            r2 = r0.cancel(r10);	 Catch:{ Throwable -> 0x001f }
        L_0x0013:
            r1 = 0;
            monitor-enter(r9);
            r5 = r9.a;	 Catch:{ all -> 0x0050 }
            if (r5 != r8) goto L_0x0039;
        L_0x0019:
            monitor-exit(r9);	 Catch:{ all -> 0x0050 }
            r2 = r4;
            goto L_0x000a;
        L_0x001c:
            r4 = move-exception;
            monitor-exit(r9);	 Catch:{ all -> 0x001c }
            throw r4;
        L_0x001f:
            r3 = move-exception;
            r5 = "JobLimiter";
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "error executing job: ";
            r6 = r6.append(r7);
            r6 = r6.append(r0);
            r6 = r6.toString();
            android.util.Log.w(r5, r6, r3);
            goto L_0x0013;
        L_0x0039:
            r4 = 1;
            r9.a = r4;	 Catch:{ all -> 0x0050 }
            r1 = r9.d;	 Catch:{ all -> 0x0050 }
            r4 = 0;
            r9.d = r4;	 Catch:{ all -> 0x0050 }
            r4 = 0;
            r9.cancel = r4;	 Catch:{ all -> 0x0050 }
            r9.e = r2;	 Catch:{ all -> 0x0050 }
            r9.notifyAll();	 Catch:{ all -> 0x0050 }
            monitor-exit(r9);	 Catch:{ all -> 0x0050 }
            if (r1 == 0) goto L_0x000a;
        L_0x004c:
            r1.a(r9);
            goto L_0x000a;
        L_0x0050:
            r4 = move-exception;
            monitor-exit(r9);	 Catch:{ all -> 0x0050 }
            throw r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.download.c.e.a.cancel(com.meizu.cloud.download.c.g$c):T");
        }
    }

    public e(g pool, int limit) {
        this.b = pool;
        this.c = limit;
    }

    public static void a(Object object) {
        try {
            object.wait();
        } catch (InterruptedException e) {
            Log.w("JobLimiter", "unexpected interrupt: " + object);
        }
    }

    public synchronized <T> c<T> a(b<T> job, d<T> listener) {
        a<T> future;
        future = new a(job, listener);
        this.a.addLast(future);
        a();
        return future;
    }

    private void a() {
        while (this.c > 0 && !this.a.isEmpty()) {
            a wrapper = (a) this.a.removeFirst();
            if (!wrapper.b()) {
                this.c--;
                wrapper.a(this.b.a(wrapper, this));
            }
        }
    }

    public synchronized void a(c future) {
        this.c++;
        a();
    }
}
