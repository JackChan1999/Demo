package com.meizu.gslb.d;

import com.meizu.gslb.b.e;
import com.meizu.gslb.b.i;
import com.meizu.gslb.f.a;
import java.util.UUID;

public abstract class d<R extends g, T extends f> {
    protected e a;
    protected a b;
    protected e c;

    private void a(String str, String str2, String str3, long j) {
        if (this.b != null) {
            this.b.a(str, str2, str3, j);
        }
    }

    private boolean a(boolean z, String str, String str2, String str3, int i) {
        i a = i.a(str2, str3, i);
        if (this.b != null) {
            this.b.a(z, str, a, i);
        }
        return this.a != null ? this.a.a(a) : false;
    }

    private boolean a(boolean z, String str, String str2, String str3, Exception exception) {
        if (this.a == null || !this.a.d()) {
            com.meizu.gslb.g.a.b("exception while no network:" + (exception != null ? exception.getMessage() : null));
        } else {
            i a = i.a(str2, str3, exception);
            if (this.b != null) {
                this.b.a(z, str, a, exception);
            }
            if (this.a != null) {
                return this.a.a(a);
            }
        }
        return false;
    }

    private void b() {
        if (this.a == null) {
            try {
                this.a = com.meizu.gslb.b.d.a();
                this.b = com.meizu.gslb.b.d.b();
            } catch (IllegalArgumentException e) {
            }
        }
    }

    private String c() {
        try {
            return UUID.randomUUID().toString();
        } catch (Exception e) {
            return String.valueOf(System.currentTimeMillis());
        }
    }

    public abstract e<R, T> a(T t);

    public void a() {
        if (this.c != null) {
            this.c.a();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final R b(T r18) throws java.io.IOException, com.meizu.gslb.d.b {
        /*
        r17 = this;
        r14 = r18.i();
        r4 = r18.a();
        r13 = 0;
        r12 = 0;
        r3 = r17.c();
        r17.cancel();
    L_0x0011:
        r5 = 0;
        if (r14 == 0) goto L_0x0082;
    L_0x0014:
        if (r13 != 0) goto L_0x005e;
    L_0x0016:
        r0 = r17;
        r2 = r0.a;
        if (r2 == 0) goto L_0x005e;
    L_0x001c:
        r0 = r17;
        r2 = r0.a;
        r2 = r2.d();
        if (r2 == 0) goto L_0x005e;
    L_0x0026:
        r0 = r17;
        r2 = r0.a;
        r2 = r2.a(r4);
        r6 = r2.a();
        if (r6 == 0) goto L_0x005e;
    L_0x0034:
        r5 = r2.c();
        r0 = r18;
        r0.a(r5);
        r6 = new java.util.ArrayList;
        r6.<init>();
        r7 = new android.util.Pair;
        r8 = "Host";
        r9 = r2.cancel();
        r7.<init>(r8, r9);
        r6.add(r7);
        r0 = r18;
        r0.a(r6);
        r2 = r2.d();
        r0 = r18;
        r0.a(r2);
    L_0x005e:
        if (r5 != 0) goto L_0x0082;
    L_0x0060:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r6 = "Use normal domain request:";
        r2 = r2.append(r6);
        r2 = r2.append(r4);
        r2 = r2.toString();
        com.meizu.gslb.g.a.a(r2);
        r2 = 0;
        r0 = r18;
        r0.a(r2);
        r2 = 0;
        r0 = r18;
        r0.a(r2);
    L_0x0082:
        r15 = r17.a(r18);
        r0 = r17;
        r0.c = r15;
        r6 = android.os.SystemClock.elapsedRealtime();
        r0 = r18;
        r16 = r15.a(r0);	 Catch:{ IOException -> 0x00d9, cancel -> 0x0111, Exception -> 0x0113 }
        r11 = r16.a();	 Catch:{ IOException -> 0x00d9, cancel -> 0x0111, Exception -> 0x0113 }
        r8 = android.os.SystemClock.elapsedRealtime();	 Catch:{ IOException -> 0x00d9, cancel -> 0x0111, Exception -> 0x0113 }
        r6 = r8 - r6;
        r2 = r17;
        r2.a(r3, r4, r5, r6);	 Catch:{ IOException -> 0x00d9, cancel -> 0x0111, Exception -> 0x0113 }
        if (r12 <= 0) goto L_0x00d7;
    L_0x00a5:
        r7 = 1;
    L_0x00a6:
        r6 = r17;
        r8 = r3;
        r9 = r4;
        r10 = r5;
        r2 = r6.a(r7, r8, r9, r10, r11);	 Catch:{ IOException -> 0x00d9, cancel -> 0x0111, Exception -> 0x0113 }
        if (r2 == 0) goto L_0x0162;
    L_0x00b1:
        r2 = r12 + 1;
        r6 = 2;
        if (r2 > r6) goto L_0x0162;
    L_0x00b6:
        r6 = 2;
        if (r2 != r6) goto L_0x016f;
    L_0x00b9:
        r6 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0165, cancel -> 0x0111, Exception -> 0x0163 }
        r6.<init>();	 Catch:{ IOException -> 0x0165, cancel -> 0x0111, Exception -> 0x0163 }
        r7 = "force not convert while retryCount:";
        r6 = r6.append(r7);	 Catch:{ IOException -> 0x0165, cancel -> 0x0111, Exception -> 0x0163 }
        r6 = r6.append(r2);	 Catch:{ IOException -> 0x0165, cancel -> 0x0111, Exception -> 0x0163 }
        r6 = r6.toString();	 Catch:{ IOException -> 0x0165, cancel -> 0x0111, Exception -> 0x0163 }
        com.meizu.gslb.g.a.c(r6);	 Catch:{ IOException -> 0x0165, cancel -> 0x0111, Exception -> 0x0163 }
        r6 = 1;
    L_0x00d0:
        r15.a();	 Catch:{ IOException -> 0x016a, cancel -> 0x0111, Exception -> 0x0163 }
        r12 = r2;
        r13 = r6;
        goto L_0x0011;
    L_0x00d7:
        r7 = 0;
        goto L_0x00a6;
    L_0x00d9:
        r11 = move-exception;
        r2 = r13;
    L_0x00db:
        if (r12 <= 0) goto L_0x010e;
    L_0x00dd:
        r7 = 1;
    L_0x00de:
        r6 = r17;
        r8 = r3;
        r9 = r4;
        r10 = r5;
        r5 = r6.a(r7, r8, r9, r10, r11);
        if (r5 == 0) goto L_0x0110;
    L_0x00e9:
        r12 = r12 + 1;
        r5 = 2;
        if (r12 > r5) goto L_0x0110;
    L_0x00ee:
        r5 = 2;
        if (r12 != r5) goto L_0x0108;
    L_0x00f1:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r5 = "force not convert while retryCount:";
        r2 = r2.append(r5);
        r2 = r2.append(r12);
        r2 = r2.toString();
        com.meizu.gslb.g.a.c(r2);
        r2 = 1;
    L_0x0108:
        r15.a();
        r13 = r2;
        goto L_0x0011;
    L_0x010e:
        r7 = 0;
        goto L_0x00de;
    L_0x0110:
        throw r11;
    L_0x0111:
        r2 = move-exception;
        throw r2;
    L_0x0113:
        r11 = move-exception;
        r2 = r12;
    L_0x0115:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "handle unknown exception:";
        r6 = r6.append(r7);
        r7 = r11.getMessage();
        r6 = r6.append(r7);
        r6 = r6.toString();
        com.meizu.gslb.g.a.d(r6);
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "exception covert info:";
        r6 = r6.append(r7);
        r6 = r6.append(r4);
        r7 = "->";
        r6 = r6.append(r7);
        r6 = r6.append(r5);
        r6 = r6.toString();
        com.meizu.gslb.g.a.d(r6);
        if (r2 <= 0) goto L_0x0160;
    L_0x0151:
        r7 = 1;
    L_0x0152:
        r6 = r17;
        r8 = r3;
        r9 = r4;
        r10 = r5;
        r6.a(r7, r8, r9, r10, r11);
        r2 = new java.io.IOException;
        r2.<init>(r11);
        throw r2;
    L_0x0160:
        r7 = 0;
        goto L_0x0152;
    L_0x0162:
        return r16;
    L_0x0163:
        r11 = move-exception;
        goto L_0x0115;
    L_0x0165:
        r11 = move-exception;
        r12 = r2;
        r2 = r13;
        goto L_0x00db;
    L_0x016a:
        r11 = move-exception;
        r12 = r2;
        r2 = r6;
        goto L_0x00db;
    L_0x016f:
        r6 = r13;
        goto L_0x00d0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.gslb.d.d.cancel(com.meizu.gslb.d.f):R");
    }
}
