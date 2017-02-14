package com.meizu.update.d.a;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.update.d.c.d;
import com.meizu.update.d.c.e;
import com.meizu.update.h.b;

public class a {
    private e a;
    private d b;
    private com.meizu.update.d.d c;
    private String d;
    private boolean e;
    private com.meizu.update.d.d.a f;

    public a(Context context, String originalUrl, com.meizu.update.d.d downloader, e retryTracker) {
        if (TextUtils.isEmpty(originalUrl) || downloader == null || retryTracker == null) {
            throw new IllegalArgumentException("Params cant be null!");
        }
        this.d = originalUrl;
        this.c = downloader;
        this.e = false;
        this.a = retryTracker;
        this.f = new com.meizu.update.d.d.a(context);
    }

    public void a(d checker) {
        this.b = checker;
        this.c.a(checker);
    }

    public void a() {
        this.e = true;
        this.c.a();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(Context r32) throws com.meizu.update.d.a, com.meizu.update.d.e {
        /*
        r31 = this;
        r0 = r31;
        r0 = r0.a;
        r25 = r0;
        r0 = r31;
        r8 = r0.d;
        r30 = 0;
        r26 = 0;
        r10 = 0;
        r0 = r31;
        r4 = r0.cancel;
        if (r4 == 0) goto L_0x001d;
    L_0x0015:
        r0 = r31;
        r4 = r0.cancel;
        r10 = r4.d();
    L_0x001d:
        r31.cancel();	 Catch:{ a -> 0x0089 }
        if (r25 == 0) goto L_0x0025;
    L_0x0022:
        r25.a();	 Catch:{ a -> 0x0089 }
    L_0x0025:
        r27 = 0;
        r21 = 0;
        r23 = 0;
        r29 = 0;
        r0 = r31;
        r4 = r0.c;	 Catch:{ f -> 0x006f, e -> 0x00a1, h -> 0x0133, c -> 0x0184 }
        r4.a(r8);	 Catch:{ f -> 0x006f, e -> 0x00a1, h -> 0x0133, c -> 0x0184 }
        r0 = r31;
        r4 = r0.c;	 Catch:{ f -> 0x006f, e -> 0x00a1, h -> 0x0133, c -> 0x0184 }
        r5 = 1;
        r27 = r4.a(r5);	 Catch:{ f -> 0x006f, e -> 0x00a1, h -> 0x0133, c -> 0x0184 }
        if (r27 != 0) goto L_0x0059;
    L_0x003f:
        r26 = 1;
        r0 = r31;
        r4 = r0.f;	 Catch:{ f -> 0x006f, e -> 0x00a1, h -> 0x0133, c -> 0x0184 }
        r5 = r32.getPackageName();	 Catch:{ f -> 0x006f, e -> 0x00a1, h -> 0x0133, c -> 0x0184 }
        r0 = r31;
        r6 = r0.d;	 Catch:{ f -> 0x006f, e -> 0x00a1, h -> 0x0133, c -> 0x0184 }
        r7 = 100000; // 0x186a0 float:1.4013E-40 double:4.94066E-319;
        r9 = "Uncaugth http exception.";
        r4.cancel(r5, r6, r7, r8, r9, r10);	 Catch:{ f -> 0x006f, e -> 0x00a1, h -> 0x0133, c -> 0x0184 }
    L_0x0055:
        if (r27 == 0) goto L_0x023e;
    L_0x0057:
        r4 = 1;
    L_0x0058:
        return r4;
    L_0x0059:
        if (r30 != 0) goto L_0x005d;
    L_0x005b:
        if (r26 == 0) goto L_0x0055;
    L_0x005d:
        r0 = r31;
        r5 = r0.f;	 Catch:{ f -> 0x006f, e -> 0x00a1, h -> 0x0133, c -> 0x0184 }
        r6 = r32.getPackageName();	 Catch:{ f -> 0x006f, e -> 0x00a1, h -> 0x0133, c -> 0x0184 }
        r0 = r31;
        r7 = r0.d;	 Catch:{ f -> 0x006f, e -> 0x00a1, h -> 0x0133, c -> 0x0184 }
        r9 = "Download success";
        r5.a(r6, r7, r8, r9, r10);	 Catch:{ f -> 0x006f, e -> 0x00a1, h -> 0x0133, c -> 0x0184 }
        goto L_0x0055;
    L_0x006f:
        r19 = move-exception;
        r26 = 1;
        r0 = r31;
        r4 = r0.f;	 Catch:{ a -> 0x0089 }
        r5 = r32.getPackageName();	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r6 = r0.d;	 Catch:{ a -> 0x0089 }
        r7 = 100000; // 0x186a0 float:1.4013E-40 double:4.94066E-319;
        r9 = r19.getMessage();	 Catch:{ a -> 0x0089 }
        r4.cancel(r5, r6, r7, r8, r9, r10);	 Catch:{ a -> 0x0089 }
        goto L_0x0055;
    L_0x0089:
        r19 = move-exception;
        if (r26 == 0) goto L_0x00a0;
    L_0x008c:
        r0 = r31;
        r11 = r0.f;
        r12 = r32.getPackageName();
        r0 = r31;
        r13 = r0.d;
        r15 = "User Canceled";
        r14 = r8;
        r16 = r10;
        r11.cancel(r12, r13, r14, r15, r16);
    L_0x00a0:
        throw r19;
    L_0x00a1:
        r19 = move-exception;
        r7 = r19.a();	 Catch:{ a -> 0x0089 }
        r4 = new java.lang.StringBuilder;	 Catch:{ a -> 0x0089 }
        r4.<init>();	 Catch:{ a -> 0x0089 }
        r5 = "LoadException: ";
        r4 = r4.append(r5);	 Catch:{ a -> 0x0089 }
        r4 = r4.append(r7);	 Catch:{ a -> 0x0089 }
        r4 = r4.toString();	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r0.a(r4);	 Catch:{ a -> 0x0089 }
        r21 = r19;
        r26 = 1;
        r0 = r31;
        r4 = r0.f;	 Catch:{ a -> 0x0089 }
        r5 = r32.getPackageName();	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r6 = r0.d;	 Catch:{ a -> 0x0089 }
        r9 = "Http response code error";
        r4.cancel(r5, r6, r7, r8, r9, r10);	 Catch:{ a -> 0x0089 }
        if (r30 == 0) goto L_0x0055;
    L_0x00d5:
        r4 = 401; // 0x191 float:5.62E-43 double:1.98E-321;
        if (r7 != r4) goto L_0x0055;
    L_0x00d9:
        r4 = new java.lang.StringBuilder;	 Catch:{ a -> 0x0089 }
        r4.<init>();	 Catch:{ a -> 0x0089 }
        r5 = "Proxy auth exception:";
        r4 = r4.append(r5);	 Catch:{ a -> 0x0089 }
        r4 = r4.append(r7);	 Catch:{ a -> 0x0089 }
        r4 = r4.toString();	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r0.a(r4);	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r4 = r0.a;	 Catch:{ a -> 0x0089 }
        r4.d();	 Catch:{ a -> 0x0089 }
        r31.cancel();	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r4 = r0.d;	 Catch:{ a -> 0x0089 }
        r0 = r25;
        r1 = r32;
        r28 = r0.a(r1, r4);	 Catch:{ a -> 0x0089 }
        r31.cancel();	 Catch:{ a -> 0x0089 }
        if (r28 == 0) goto L_0x0055;
    L_0x010c:
        r0 = r31;
        r4 = r0.cancel;	 Catch:{ a -> 0x0089 }
        r0 = r28;
        r0.a(r4);	 Catch:{ a -> 0x0089 }
        r0 = r28;
        r8 = r0.a;	 Catch:{ a -> 0x0089 }
        r0 = r28;
        r4 = r0.cancel;	 Catch:{ a -> 0x0089 }
        if (r4 == 0) goto L_0x012a;
    L_0x011f:
        r0 = r31;
        r4 = r0.c;	 Catch:{ a -> 0x0089 }
        r0 = r28;
        r5 = r0.cancel;	 Catch:{ a -> 0x0089 }
        r4.a(r5);	 Catch:{ a -> 0x0089 }
    L_0x012a:
        r4 = "Re proxy success";
        r0 = r31;
        r0.a(r4);	 Catch:{ a -> 0x0089 }
        goto L_0x0055;
    L_0x0133:
        r19 = move-exception;
        r24 = r19.getMessage();	 Catch:{ a -> 0x0089 }
        r4 = new java.lang.StringBuilder;	 Catch:{ a -> 0x0089 }
        r4.<init>();	 Catch:{ a -> 0x0089 }
        r5 = "Relocate to: ";
        r4 = r4.append(r5);	 Catch:{ a -> 0x0089 }
        r0 = r24;
        r4 = r4.append(r0);	 Catch:{ a -> 0x0089 }
        r4 = r4.toString();	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r0.a(r4);	 Catch:{ a -> 0x0089 }
        r8 = r24;
        r23 = 1;
        if (r25 == 0) goto L_0x0055;
    L_0x0158:
        if (r30 == 0) goto L_0x0055;
    L_0x015a:
        r0 = r25;
        r1 = r32;
        r2 = r24;
        r28 = r0.cancel(r1, r2);	 Catch:{ a -> 0x0089 }
        if (r28 == 0) goto L_0x0055;
    L_0x0166:
        r4 = "Relocate and re proxy success";
        r0 = r31;
        r0.a(r4);	 Catch:{ a -> 0x0089 }
        r0 = r28;
        r8 = r0.a;	 Catch:{ a -> 0x0089 }
        r0 = r28;
        r4 = r0.cancel;	 Catch:{ a -> 0x0089 }
        if (r4 == 0) goto L_0x0055;
    L_0x0177:
        r0 = r31;
        r4 = r0.c;	 Catch:{ a -> 0x0089 }
        r0 = r28;
        r5 = r0.cancel;	 Catch:{ a -> 0x0089 }
        r4.a(r5);	 Catch:{ a -> 0x0089 }
        goto L_0x0055;
    L_0x0184:
        r19 = move-exception;
        r26 = 1;
        r0 = r31;
        r11 = r0.f;	 Catch:{ a -> 0x0089 }
        r12 = r32.getPackageName();	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r13 = r0.d;	 Catch:{ a -> 0x0089 }
        r14 = r19.a();	 Catch:{ a -> 0x0089 }
        r16 = r19.getMessage();	 Catch:{ a -> 0x0089 }
        r15 = r8;
        r17 = r10;
        r11.a(r12, r13, r14, r15, r16, r17);	 Catch:{ a -> 0x0089 }
        r4 = "Handle FileIllegalException!";
        r0 = r31;
        r0.a(r4);	 Catch:{ a -> 0x0089 }
        if (r25 == 0) goto L_0x0226;
    L_0x01aa:
        r18 = r25.c();	 Catch:{ a -> 0x0089 }
        r4 = android.text.TextUtils.isEmpty(r18);	 Catch:{ a -> 0x0089 }
        if (r4 != 0) goto L_0x01ba;
    L_0x01b4:
        r29 = 1;
        r8 = r18;
        goto L_0x0055;
    L_0x01ba:
        r31.cancel();	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r4 = r0.d;	 Catch:{ a -> 0x0089 }
        r0 = r25;
        r1 = r32;
        r28 = r0.a(r1, r4);	 Catch:{ a -> 0x0089 }
        r31.cancel();	 Catch:{ a -> 0x0089 }
        if (r28 == 0) goto L_0x020c;
    L_0x01ce:
        r0 = r31;
        r4 = r0.cancel;	 Catch:{ a -> 0x0089 }
        r0 = r28;
        r0.a(r4);	 Catch:{ a -> 0x0089 }
        r30 = 1;
        r0 = r28;
        r8 = r0.a;	 Catch:{ a -> 0x0089 }
        r0 = r28;
        r4 = r0.cancel;	 Catch:{ a -> 0x0089 }
        if (r4 == 0) goto L_0x01ee;
    L_0x01e3:
        r0 = r31;
        r4 = r0.c;	 Catch:{ a -> 0x0089 }
        r0 = r28;
        r5 = r0.cancel;	 Catch:{ a -> 0x0089 }
        r4.a(r5);	 Catch:{ a -> 0x0089 }
    L_0x01ee:
        r4 = "Trans to proxy server request";
        r0 = r31;
        r0.a(r4);	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r4 = r0.cancel;	 Catch:{ a -> 0x0089 }
        if (r4 == 0) goto L_0x0055;
    L_0x01fb:
        r4 = "Disable file checker!";
        r0 = r31;
        r0.a(r4);	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r4 = r0.cancel;	 Catch:{ a -> 0x0089 }
        r5 = 0;
        r4.a(r5);	 Catch:{ a -> 0x0089 }
        goto L_0x0055;
    L_0x020c:
        r0 = r31;
        r11 = r0.f;	 Catch:{ a -> 0x0089 }
        r12 = r32.getPackageName();	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r13 = r0.d;	 Catch:{ a -> 0x0089 }
        r14 = 100001; // 0x186a1 float:1.40131E-40 double:4.9407E-319;
        r16 = "Cant trans to proxy server.";
        r15 = r8;
        r17 = r10;
        r11.cancel(r12, r13, r14, r15, r16, r17);	 Catch:{ a -> 0x0089 }
    L_0x0223:
        r4 = 0;
        goto L_0x0058;
    L_0x0226:
        r0 = r31;
        r11 = r0.f;	 Catch:{ a -> 0x0089 }
        r12 = r32.getPackageName();	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r13 = r0.d;	 Catch:{ a -> 0x0089 }
        r14 = 100001; // 0x186a1 float:1.40131E-40 double:4.9407E-319;
        r16 = "Cant trans to proxy server.";
        r15 = r8;
        r17 = r10;
        r11.cancel(r12, r13, r14, r15, r16, r17);	 Catch:{ a -> 0x0089 }
        goto L_0x0223;
    L_0x023e:
        if (r25 == 0) goto L_0x02f4;
    L_0x0240:
        r4 = r25.cancel();	 Catch:{ a -> 0x0089 }
        if (r4 != 0) goto L_0x0267;
    L_0x0246:
        r0 = r31;
        r11 = r0.f;	 Catch:{ a -> 0x0089 }
        r12 = r32.getPackageName();	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r13 = r0.d;	 Catch:{ a -> 0x0089 }
        r14 = 100000; // 0x186a0 float:1.4013E-40 double:4.94066E-319;
        r16 = "Over max retry count, error end!";
        r15 = r8;
        r17 = r10;
        r11.cancel(r12, r13, r14, r15, r16, r17);	 Catch:{ a -> 0x0089 }
        r4 = "Over max retry count, error end!";
        r0 = r31;
        r0.a(r4);	 Catch:{ a -> 0x0089 }
        if (r21 == 0) goto L_0x0223;
    L_0x0266:
        throw r21;	 Catch:{ a -> 0x0089 }
    L_0x0267:
        if (r23 != 0) goto L_0x02ef;
    L_0x0269:
        if (r29 != 0) goto L_0x0277;
    L_0x026b:
        r18 = r25.c();	 Catch:{ a -> 0x0089 }
        r4 = android.text.TextUtils.isEmpty(r18);	 Catch:{ a -> 0x0089 }
        if (r4 != 0) goto L_0x0277;
    L_0x0275:
        r8 = r18;
    L_0x0277:
        r4 = com.meizu.update.h.g.i(r32);	 Catch:{ a -> 0x0089 }
        if (r4 != 0) goto L_0x02dd;
    L_0x027d:
        r22 = 0;
        r20 = 0;
    L_0x0281:
        r4 = 10;
        r0 = r20;
        if (r0 >= r4) goto L_0x02af;
    L_0x0287:
        r4 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        java.lang.Thread.sleep(r4);	 Catch:{ InterruptedException -> 0x02f7 }
    L_0x028c:
        r31.cancel();	 Catch:{ a -> 0x0089 }
        r22 = com.meizu.update.h.g.i(r32);	 Catch:{ a -> 0x0089 }
        r4 = new java.lang.StringBuilder;	 Catch:{ a -> 0x0089 }
        r4.<init>();	 Catch:{ a -> 0x0089 }
        r5 = "Wait network count: ";
        r4 = r4.append(r5);	 Catch:{ a -> 0x0089 }
        r5 = r20 + 1;
        r4 = r4.append(r5);	 Catch:{ a -> 0x0089 }
        r4 = r4.toString();	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r0.cancel(r4);	 Catch:{ a -> 0x0089 }
        if (r22 == 0) goto L_0x02d1;
    L_0x02af:
        if (r22 != 0) goto L_0x02d4;
    L_0x02b1:
        r0 = r31;
        r11 = r0.f;	 Catch:{ a -> 0x0089 }
        r12 = r32.getPackageName();	 Catch:{ a -> 0x0089 }
        r0 = r31;
        r13 = r0.d;	 Catch:{ a -> 0x0089 }
        r14 = 100000; // 0x186a0 float:1.4013E-40 double:4.94066E-319;
        r16 = "No network, error end!";
        r15 = r8;
        r17 = r10;
        r11.cancel(r12, r13, r14, r15, r16, r17);	 Catch:{ a -> 0x0089 }
        r4 = "Wait network failed.";
        r0 = r31;
        r0.a(r4);	 Catch:{ a -> 0x0089 }
        goto L_0x0223;
    L_0x02d1:
        r20 = r20 + 1;
        goto L_0x0281;
    L_0x02d4:
        r4 = "Wait network success, go on.";
        r0 = r31;
        r0.cancel(r4);	 Catch:{ a -> 0x0089 }
        goto L_0x001d;
    L_0x02dd:
        r20 = 0;
    L_0x02df:
        r4 = 3;
        r0 = r20;
        if (r0 >= r4) goto L_0x001d;
    L_0x02e4:
        r4 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        java.lang.Thread.sleep(r4);	 Catch:{ InterruptedException -> 0x02f9 }
    L_0x02e9:
        r31.cancel();	 Catch:{ a -> 0x0089 }
        r20 = r20 + 1;
        goto L_0x02df;
    L_0x02ef:
        r25.e();	 Catch:{ a -> 0x0089 }
        goto L_0x001d;
    L_0x02f4:
        if (r21 == 0) goto L_0x0223;
    L_0x02f6:
        throw r21;	 Catch:{ a -> 0x0089 }
    L_0x02f7:
        r4 = move-exception;
        goto L_0x028c;
    L_0x02f9:
        r4 = move-exception;
        goto L_0x02e9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.update.d.a.a.a(android.content.Context):boolean");
    }

    private void b() throws com.meizu.update.d.a {
        if (this.e) {
            throw new com.meizu.update.d.a();
        }
    }

    private void a(String msg) {
        b.d(msg);
    }

    private void b(String msg) {
        b.c(msg);
    }
}
