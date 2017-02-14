package com.meizu.flyme.appcenter.desktopplugin.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import java.lang.ref.WeakReference;

abstract class e implements Runnable {
    private final WeakReference<Context> a;
    private final WeakReference<d> b;
    public boolean c = true;

    abstract void a();

    public e(Context context, d wallpaperObserver) {
        this.a = new WeakReference(context);
        this.b = new WeakReference(wallpaperObserver);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r18 = this;
        r0 = r18;
        r13 = r0.c;
        if (r13 == 0) goto L_0x0010;
    L_0x0006:
        r0 = r18;
        r13 = r0.a;
        r13 = r13.get();
        if (r13 != 0) goto L_0x0011;
    L_0x0010:
        return;
    L_0x0011:
        r0 = r18;
        r13 = r0.a;	 Catch:{ all -> 0x011b }
        r13 = r13.get();	 Catch:{ all -> 0x011b }
        r13 = (android.content.Context) r13;	 Catch:{ all -> 0x011b }
        r14 = "window";
        r12 = r13.getSystemService(r14);	 Catch:{ all -> 0x011b }
        r12 = (android.view.WindowManager) r12;	 Catch:{ all -> 0x011b }
        r5 = r12.getDefaultDisplay();	 Catch:{ all -> 0x011b }
        r0 = r18;
        r13 = r0.a;	 Catch:{ all -> 0x011b }
        r13 = r13.get();	 Catch:{ all -> 0x011b }
        r13 = (android.content.Context) r13;	 Catch:{ all -> 0x011b }
        r13 = android.app.WallpaperManager.getInstance(r13);	 Catch:{ all -> 0x011b }
        r13 = r13.getDrawable();	 Catch:{ all -> 0x011b }
        r13 = (android.graphics.drawable.BitmapDrawable) r13;	 Catch:{ all -> 0x011b }
        r13 = (android.graphics.drawable.BitmapDrawable) r13;	 Catch:{ all -> 0x011b }
        r1 = r13.getBitmap();	 Catch:{ all -> 0x011b }
        r15 = 0;
        r16 = 0;
        r13 = r5.getWidth();	 Catch:{ all -> 0x011b }
        r14 = r1.getWidth();	 Catch:{ all -> 0x011b }
        if (r13 > r14) goto L_0x0081;
    L_0x004e:
        r13 = r5.getWidth();	 Catch:{ all -> 0x011b }
        r14 = r13;
    L_0x0053:
        r13 = r5.getHeight();	 Catch:{ all -> 0x011b }
        r17 = r1.getHeight();	 Catch:{ all -> 0x011b }
        r0 = r17;
        if (r13 > r0) goto L_0x0087;
    L_0x005f:
        r13 = r5.getHeight();	 Catch:{ all -> 0x011b }
    L_0x0063:
        r0 = r16;
        r1 = android.graphics.Bitmap.createBitmap(r1, r15, r0, r14, r13);	 Catch:{ all -> 0x011b }
        r13 = 12;
        r6 = android.support.v7.c.d.a(r1, r13);	 Catch:{ all -> 0x011b }
        r0 = r18;
        r13 = r0.cancel;	 Catch:{ all -> 0x011b }
        r13 = r13.get();	 Catch:{ all -> 0x011b }
        if (r13 != 0) goto L_0x008c;
    L_0x0079:
        r13 = java.lang.Runtime.getRuntime();
        r13.gc();
        goto L_0x0010;
    L_0x0081:
        r13 = r1.getWidth();	 Catch:{ all -> 0x011b }
        r14 = r13;
        goto L_0x0053;
    L_0x0087:
        r13 = r1.getHeight();	 Catch:{ all -> 0x011b }
        goto L_0x0063;
    L_0x008c:
        r0 = r18;
        r13 = r0.cancel;	 Catch:{ all -> 0x011b }
        r13 = r13.get();	 Catch:{ all -> 0x011b }
        r13 = (com.meizu.flyme.appcenter.desktopplugin.presenter.d) r13;	 Catch:{ all -> 0x011b }
        monitor-enter(r13);	 Catch:{ all -> 0x011b }
        r0 = r18;
        r14 = r0.c;	 Catch:{ all -> 0x0118 }
        if (r14 == 0) goto L_0x00a7;
    L_0x009d:
        r0 = r18;
        r14 = r0.a;	 Catch:{ all -> 0x0118 }
        r14 = r14.get();	 Catch:{ all -> 0x0118 }
        if (r14 != 0) goto L_0x00b1;
    L_0x00a7:
        monitor-exit(r13);	 Catch:{ all -> 0x0118 }
        r13 = java.lang.Runtime.getRuntime();
        r13.gc();
        goto L_0x0010;
    L_0x00b1:
        r0 = r18;
        r14 = r0.cancel;	 Catch:{ all -> 0x0118 }
        r14 = r14.get();	 Catch:{ all -> 0x0118 }
        r14 = (com.meizu.flyme.appcenter.desktopplugin.presenter.d) r14;	 Catch:{ all -> 0x0118 }
        r2 = a(r1, r14);	 Catch:{ all -> 0x0118 }
        monitor-exit(r13);	 Catch:{ all -> 0x0118 }
        r13 = 170; // 0xaa float:2.38E-43 double:8.4E-322;
        if (r2 <= r13) goto L_0x019b;
    L_0x00c4:
        r4 = 1;
        r8 = r6.cancel();	 Catch:{ all -> 0x011b }
        r9 = r6.e();	 Catch:{ all -> 0x011b }
        if (r8 == 0) goto L_0x0124;
    L_0x00cf:
        if (r9 == 0) goto L_0x0124;
    L_0x00d1:
        r13 = 1055286886; // 0x3ee66666 float:0.45 double:5.21380997E-315;
        r14 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r15 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r16 = r8.a();	 Catch:{ all -> 0x011b }
        r17 = r9.a();	 Catch:{ all -> 0x011b }
        r15 = a(r15, r16, r17);	 Catch:{ all -> 0x011b }
        r3 = a(r13, r14, r15);	 Catch:{ all -> 0x011b }
    L_0x00e8:
        r0 = r18;
        r13 = r0.a;	 Catch:{ all -> 0x011b }
        r13 = r13.get();	 Catch:{ all -> 0x011b }
        r13 = (android.content.Context) r13;	 Catch:{ all -> 0x011b }
        r13 = r13.getApplicationContext();	 Catch:{ all -> 0x011b }
        com.meizu.cloud.app.utils.s.a(r13, r3);	 Catch:{ all -> 0x011b }
        r7 = new com.meizu.flyme.appcenter.desktopplugin.cancel.c;	 Catch:{ all -> 0x011b }
        r7.<init>();	 Catch:{ all -> 0x011b }
        r0 = r18;
        r13 = r0.a;	 Catch:{ all -> 0x011b }
        r13 = r13.get();	 Catch:{ all -> 0x011b }
        r13 = (android.content.Context) r13;	 Catch:{ all -> 0x011b }
        r14 = 0;
        r7.a(r13, r14);	 Catch:{ all -> 0x011b }
        r18.a();	 Catch:{ all -> 0x011b }
        r13 = java.lang.Runtime.getRuntime();
        r13.gc();
        goto L_0x0010;
    L_0x0118:
        r14 = move-exception;
        monitor-exit(r13);	 Catch:{ all -> 0x0118 }
        throw r14;	 Catch:{ all -> 0x011b }
    L_0x011b:
        r13 = move-exception;
        r14 = java.lang.Runtime.getRuntime();
        r14.gc();
        throw r13;
    L_0x0124:
        if (r8 != 0) goto L_0x017b;
    L_0x0126:
        if (r9 != 0) goto L_0x017b;
    L_0x0128:
        r10 = r6.c();	 Catch:{ all -> 0x011b }
        r11 = r6.f();	 Catch:{ all -> 0x011b }
        if (r10 == 0) goto L_0x014c;
    L_0x0132:
        if (r11 == 0) goto L_0x014c;
    L_0x0134:
        r13 = 1055286886; // 0x3ee66666 float:0.45 double:5.21380997E-315;
        r14 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r15 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r16 = r10.a();	 Catch:{ all -> 0x011b }
        r17 = r11.a();	 Catch:{ all -> 0x011b }
        r15 = a(r15, r16, r17);	 Catch:{ all -> 0x011b }
        r3 = a(r13, r14, r15);	 Catch:{ all -> 0x011b }
        goto L_0x00e8;
    L_0x014c:
        if (r10 != 0) goto L_0x015b;
    L_0x014e:
        if (r11 != 0) goto L_0x015b;
    L_0x0150:
        r13 = 230; // 0xe6 float:3.22E-43 double:1.136E-321;
        r14 = 0;
        r15 = 0;
        r16 = 0;
        r3 = android.graphics.Color.argb(r13, r14, r15, r16);	 Catch:{ all -> 0x011b }
        goto L_0x00e8;
    L_0x015b:
        if (r10 == 0) goto L_0x016c;
    L_0x015d:
        r13 = 1055286886; // 0x3ee66666 float:0.45 double:5.21380997E-315;
        r14 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r15 = r10.a();	 Catch:{ all -> 0x011b }
        r3 = a(r13, r14, r15);	 Catch:{ all -> 0x011b }
        goto L_0x00e8;
    L_0x016c:
        r13 = 1055286886; // 0x3ee66666 float:0.45 double:5.21380997E-315;
        r14 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r15 = r11.a();	 Catch:{ all -> 0x011b }
        r3 = a(r13, r14, r15);	 Catch:{ all -> 0x011b }
        goto L_0x00e8;
    L_0x017b:
        if (r8 == 0) goto L_0x018c;
    L_0x017d:
        r13 = 1055286886; // 0x3ee66666 float:0.45 double:5.21380997E-315;
        r14 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r15 = r8.a();	 Catch:{ all -> 0x011b }
        r3 = a(r13, r14, r15);	 Catch:{ all -> 0x011b }
        goto L_0x00e8;
    L_0x018c:
        r13 = 1055286886; // 0x3ee66666 float:0.45 double:5.21380997E-315;
        r14 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r15 = r9.a();	 Catch:{ all -> 0x011b }
        r3 = a(r13, r14, r15);	 Catch:{ all -> 0x011b }
        goto L_0x00e8;
    L_0x019b:
        r4 = 0;
        r13 = 230; // 0xe6 float:3.22E-43 double:1.136E-321;
        r14 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r15 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r16 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r3 = android.graphics.Color.argb(r13, r14, r15, r16);	 Catch:{ all -> 0x011b }
        goto L_0x00e8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.flyme.appcenter.desktopplugin.presenter.e.run():void");
    }

    private static int a(float bit, int base, int newColor) {
        return Color.rgb((int) ((((float) Color.red(base)) * (1.0f - bit)) + (((float) Color.red(newColor)) * bit)), (int) ((((float) Color.red(base)) * (1.0f - bit)) + (((float) Color.green(newColor)) * bit)), (int) ((((float) Color.red(base)) * (1.0f - bit)) + (((float) Color.blue(newColor)) * bit)));
    }

    private static int a(Bitmap bitmap, d wallpaperObserver) {
        int i;
        int localWidth = bitmap.getWidth();
        int localHeight = bitmap.getHeight();
        int numX = wallpaperObserver.a.length;
        int numY = wallpaperObserver.a[0].length;
        int[] x = new int[numX];
        int[] y = new int[numY];
        for (i = 0; i < x.length; i++) {
            x[i] = (localWidth * i) / numX;
        }
        for (i = 0; i < y.length; i++) {
            y[i] = (localHeight * i) / numY;
        }
        int number = 0;
        double bright = 0.0d;
        for (i = 0; i < x.length; i++) {
            for (int j = 0; j < y.length; j++) {
                number++;
                double localTemp = a(bitmap.getPixel(x[i], y[j]));
                wallpaperObserver.a[i][j] = (int) localTemp;
                bright += localTemp;
            }
        }
        return (int) (bright / ((double) number));
    }

    private static double a(int color) {
        return ((0.299d * ((double) Color.red(color))) + (0.587d * ((double) Color.green(color)))) + (0.114d * ((double) Color.blue(color)));
    }
}
