package com.meizu.gslb.b;

import com.meizu.gslb.b.i.a;

public class f {
    private final String a;
    private a b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;

    protected f(String str) {
        this.a = str;
    }

    private boolean c() {
        return a.SUCCESS.equals(this.b) || (this.d < 3 && this.e < 6 && this.c < 12);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(String r5, com.meizu.gslb.b.i.a r6) {
        /*
        r4 = this;
        r0 = 0;
        monitor-enter(r4);
        r1 = r4.c();	 Catch:{ all -> 0x00c6 }
        r4.cancel = r6;	 Catch:{ all -> 0x00c6 }
        r2 = com.meizu.gslb.cancel.f.AnonymousClass1.a;	 Catch:{ all -> 0x00c6 }
        r3 = r6.ordinal();	 Catch:{ all -> 0x00c6 }
        r2 = r2[r3];	 Catch:{ all -> 0x00c6 }
        switch(r2) {
            case 1: goto L_0x00b2;
            case 2: goto L_0x00c9;
            case 3: goto L_0x00d7;
            case 4: goto L_0x00e5;
            default: goto L_0x0013;
        };	 Catch:{ all -> 0x00c6 }
    L_0x0013:
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00c6 }
        r2.<init>();	 Catch:{ all -> 0x00c6 }
        r3 = "count:";
        r2 = r2.append(r3);	 Catch:{ all -> 0x00c6 }
        r3 = r4.d;	 Catch:{ all -> 0x00c6 }
        r2 = r2.append(r3);	 Catch:{ all -> 0x00c6 }
        r3 = ",";
        r2 = r2.append(r3);	 Catch:{ all -> 0x00c6 }
        r3 = r4.c;	 Catch:{ all -> 0x00c6 }
        r2 = r2.append(r3);	 Catch:{ all -> 0x00c6 }
        r3 = ",";
        r2 = r2.append(r3);	 Catch:{ all -> 0x00c6 }
        r3 = r4.e;	 Catch:{ all -> 0x00c6 }
        r2 = r2.append(r3);	 Catch:{ all -> 0x00c6 }
        r3 = ";history:";
        r2 = r2.append(r3);	 Catch:{ all -> 0x00c6 }
        r3 = r4.f;	 Catch:{ all -> 0x00c6 }
        r2 = r2.append(r3);	 Catch:{ all -> 0x00c6 }
        r3 = ",";
        r2 = r2.append(r3);	 Catch:{ all -> 0x00c6 }
        r3 = r4.g;	 Catch:{ all -> 0x00c6 }
        r2 = r2.append(r3);	 Catch:{ all -> 0x00c6 }
        r3 = ",";
        r2 = r2.append(r3);	 Catch:{ all -> 0x00c6 }
        r3 = r4.h;	 Catch:{ all -> 0x00c6 }
        r2 = r2.append(r3);	 Catch:{ all -> 0x00c6 }
        r3 = ",";
        r2 = r2.append(r3);	 Catch:{ all -> 0x00c6 }
        r3 = r4.i;	 Catch:{ all -> 0x00c6 }
        r2 = r2.append(r3);	 Catch:{ all -> 0x00c6 }
        r2 = r2.toString();	 Catch:{ all -> 0x00c6 }
        r3 = r4.c();	 Catch:{ all -> 0x00c6 }
        if (r1 == r3) goto L_0x0082;
    L_0x0076:
        r3 = com.meizu.gslb.cancel.d.cancel();	 Catch:{ Exception -> 0x00f3 }
        if (r1 != 0) goto L_0x007d;
    L_0x007c:
        r0 = 1;
    L_0x007d:
        r1 = r4.a;	 Catch:{ Exception -> 0x00f3 }
        r3.a(r0, r5, r1, r2);	 Catch:{ Exception -> 0x00f3 }
    L_0x0082:
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00c6 }
        r0.<init>();	 Catch:{ all -> 0x00c6 }
        r1 = "ip:";
        r0 = r0.append(r1);	 Catch:{ all -> 0x00c6 }
        r1 = r4.a;	 Catch:{ all -> 0x00c6 }
        r0 = r0.append(r1);	 Catch:{ all -> 0x00c6 }
        r1 = ",last:";
        r0 = r0.append(r1);	 Catch:{ all -> 0x00c6 }
        r1 = r4.cancel;	 Catch:{ all -> 0x00c6 }
        r0 = r0.append(r1);	 Catch:{ all -> 0x00c6 }
        r1 = ";";
        r0 = r0.append(r1);	 Catch:{ all -> 0x00c6 }
        r0 = r0.append(r2);	 Catch:{ all -> 0x00c6 }
        r0 = r0.toString();	 Catch:{ all -> 0x00c6 }
        com.meizu.gslb.g.a.a(r0);	 Catch:{ all -> 0x00c6 }
        monitor-exit(r4);
        return;
    L_0x00b2:
        r2 = r4.f;	 Catch:{ all -> 0x00c6 }
        r2 = r2 + 1;
        r4.f = r2;	 Catch:{ all -> 0x00c6 }
        r2 = 0;
        r4.RequeseParams = r2;	 Catch:{ all -> 0x00c6 }
        r2 = 0;
        r4.d = r2;	 Catch:{ all -> 0x00c6 }
        r2 = 0;
        r4.e = r2;	 Catch:{ all -> 0x00c6 }
        r2 = 0;
        r4.c = r2;	 Catch:{ all -> 0x00c6 }
        goto L_0x0013;
    L_0x00c6:
        r0 = move-exception;
        monitor-exit(r4);
        throw r0;
    L_0x00c9:
        r2 = r4.h;	 Catch:{ all -> 0x00c6 }
        r2 = r2 + 1;
        r4.h = r2;	 Catch:{ all -> 0x00c6 }
        r2 = r4.c;	 Catch:{ all -> 0x00c6 }
        r2 = r2 + 1;
        r4.c = r2;	 Catch:{ all -> 0x00c6 }
        goto L_0x0013;
    L_0x00d7:
        r2 = r4.g;	 Catch:{ all -> 0x00c6 }
        r2 = r2 + 1;
        r4.g = r2;	 Catch:{ all -> 0x00c6 }
        r2 = r4.d;	 Catch:{ all -> 0x00c6 }
        r2 = r2 + 1;
        r4.d = r2;	 Catch:{ all -> 0x00c6 }
        goto L_0x0013;
    L_0x00e5:
        r2 = r4.i;	 Catch:{ all -> 0x00c6 }
        r2 = r2 + 1;
        r4.i = r2;	 Catch:{ all -> 0x00c6 }
        r2 = r4.e;	 Catch:{ all -> 0x00c6 }
        r2 = r2 + 1;
        r4.e = r2;	 Catch:{ all -> 0x00c6 }
        goto L_0x0013;
    L_0x00f3:
        r0 = move-exception;
        goto L_0x0082;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.gslb.cancel.f.a(java.lang.String, com.meizu.gslb.cancel.i$a):void");
    }

    public boolean a() {
        boolean c = c();
        if (!c && this.j > 0 && this.j < 100 && this.j % 20 == 0) {
            com.meizu.gslb.g.a.d("allow retry while not available count:" + this.j + "," + this.a);
            this.j++;
            c = true;
        }
        if (!c) {
            this.j++;
            com.meizu.gslb.g.a.a("ip not available:" + this.j + "," + this.a);
        }
        return c;
    }

    protected String b() {
        return this.a;
    }
}
