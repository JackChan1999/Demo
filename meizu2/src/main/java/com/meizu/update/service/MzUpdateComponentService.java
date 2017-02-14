package com.meizu.update.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.update.UpdateInfo;
import com.meizu.update.iresponse.MzUpdateResponse;
import defpackage.aow;
import defpackage.aoy;
import defpackage.apb;
import defpackage.apt;
import defpackage.apv;
import defpackage.aqf;
import defpackage.aqi;
import defpackage.aqn;
import defpackage.aqo;
import defpackage.aqq;
import defpackage.aqr;
import defpackage.aqx;
import defpackage.arb;
import defpackage.arc;
import defpackage.arf;
import defpackage.arj;
import defpackage.arj$a;
import defpackage.arp;
import defpackage.arq;
import defpackage.arr;
import defpackage.ars;
import defpackage.art;
import defpackage.aru;
import defpackage.arv;
import defpackage.arv$a;
import defpackage.arx;
import defpackage.asb;
import defpackage.asd;
import java.io.File;

public class MzUpdateComponentService extends Service {
    private static long a = 0;
    private aqx b;
    private volatile Looper c;
    private volatile a d;
    private Handler e;

    final class a extends Handler {
        final /* synthetic */ MzUpdateComponentService a;

        public a(MzUpdateComponentService mzUpdateComponentService, Looper looper) {
            this.a = mzUpdateComponentService;
            super(looper);
        }

        public void handleMessage(Message message) {
            MzUpdateResponse mzUpdateResponse = null;
            Bundle extras = ((Intent) message.obj).getExtras();
            switch (message.what) {
                case 1:
                    asb.a(true);
                    if (extras.containsKey("response")) {
                        mzUpdateResponse = (MzUpdateResponse) extras.getParcelable("response");
                    }
                    UpdateInfo updateInfo = (UpdateInfo) extras.getParcelable("update_info");
                    if (extras.containsKey("from_notification")) {
                        arv.a(this.a).a(arv$a.UpdateAlert_Yes, updateInfo.mVersionName, asd.b(this.a, this.a.getPackageName()));
                    }
                    this.a.a(updateInfo, mzUpdateResponse);
                    asb.a(false);
                    break;
                case 2:
                    asb.a(true);
                    String string = extras.getString("apk_path");
                    if (extras.containsKey("response")) {
                        mzUpdateResponse = (MzUpdateResponse) extras.getParcelable("response");
                    }
                    this.a.b((UpdateInfo) extras.getParcelable("update_info"), string, mzUpdateResponse);
                    asb.a(false);
                    break;
                case 3:
                    this.a.a();
                    break;
                case 4:
                    this.a.c();
                    break;
                case 5:
                    apb.a(this.a);
                    break;
            }
            this.a.stopSelf(message.arg1);
        }
    }

    private void a() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0007 in list [B:32:0x00d7]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r13 = this;
        r1 = 0;
        r0 = defpackage.asd.d();
        if (r0 == 0) goto L_0x0008;
    L_0x0007:
        return;
    L_0x0008:
        r0 = "power";
        r0 = r13.getSystemService(r0);
        r0 = (android.os.PowerManager) r0;
        if (r0 == 0) goto L_0x0182;
    L_0x0012:
        r2 = 1;
        r3 = "MzUpdateComponent[PushCheck]";
        r0 = r0.newWakeLock(r2, r3);
        r4 = r0;
    L_0x001a:
        if (r4 == 0) goto L_0x0024;
    L_0x001c:
        r0 = "acquire wake lock for push check.";
        defpackage.arx.a(r13, r0);
        r4.acquire();
    L_0x0024:
        r0 = "onPushUpdate check...";	 Catch:{ all -> 0x012a }
        defpackage.arx.a(r13, r0);	 Catch:{ all -> 0x012a }
        r0 = 3;	 Catch:{ all -> 0x012a }
        r6 = 60000; // 0xea60 float:8.4078E-41 double:2.9644E-319;	 Catch:{ all -> 0x012a }
        r8 = android.os.SystemClock.elapsedRealtime();	 Catch:{ all -> 0x012a }
        r2 = r0;	 Catch:{ all -> 0x012a }
        r0 = r1;	 Catch:{ all -> 0x012a }
    L_0x0033:
        r3 = r2 + -1;	 Catch:{ all -> 0x012a }
        if (r2 <= 0) goto L_0x0066;	 Catch:{ all -> 0x012a }
    L_0x0037:
        r10 = android.os.SystemClock.elapsedRealtime();	 Catch:{ all -> 0x012a }
        r10 = r10 - r8;	 Catch:{ all -> 0x012a }
        r2 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1));	 Catch:{ all -> 0x012a }
        if (r2 > 0) goto L_0x0066;	 Catch:{ all -> 0x012a }
    L_0x0040:
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x012a }
        r2.<init>();	 Catch:{ all -> 0x012a }
        r5 = "start check try:";	 Catch:{ all -> 0x012a }
        r2 = r2.append(r5);	 Catch:{ all -> 0x012a }
        r2 = r2.append(r3);	 Catch:{ all -> 0x012a }
        r2 = r2.toString();	 Catch:{ all -> 0x012a }
        defpackage.arx.a(r13, r2);	 Catch:{ all -> 0x012a }
        r2 = r13.getPackageName();	 Catch:{ arw -> 0x00f6 }
        r1 = defpackage.aoy.b(r13, r2);	 Catch:{ arw -> 0x00f6 }
        r12 = r1;
        r1 = r0;
        r0 = r12;
    L_0x0061:
        if (r0 == 0) goto L_0x00fc;
    L_0x0063:
        r12 = r0;
        r0 = r1;
        r1 = r12;
    L_0x0066:
        r2 = new arn;	 Catch:{ all -> 0x012a }
        r2.<init>(r13);	 Catch:{ all -> 0x012a }
        if (r1 == 0) goto L_0x015a;	 Catch:{ all -> 0x012a }
    L_0x006d:
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x012a }
        r0.<init>();	 Catch:{ all -> 0x012a }
        r3 = "push update check end:";	 Catch:{ all -> 0x012a }
        r0 = r0.append(r3);	 Catch:{ all -> 0x012a }
        r3 = r1.mExistsUpdate;	 Catch:{ all -> 0x012a }
        r0 = r0.append(r3);	 Catch:{ all -> 0x012a }
        r3 = ",";	 Catch:{ all -> 0x012a }
        r0 = r0.append(r3);	 Catch:{ all -> 0x012a }
        r3 = r1.mNeedUpdate;	 Catch:{ all -> 0x012a }
        r0 = r0.append(r3);	 Catch:{ all -> 0x012a }
        r0 = r0.toString();	 Catch:{ all -> 0x012a }
        defpackage.arx.a(r13, r0);	 Catch:{ all -> 0x012a }
        r0 = r1.mExistsUpdate;	 Catch:{ all -> 0x012a }
        if (r0 != 0) goto L_0x0099;	 Catch:{ all -> 0x012a }
    L_0x0095:
        r0 = r1.mNeedUpdate;	 Catch:{ all -> 0x012a }
        if (r0 == 0) goto L_0x014b;	 Catch:{ all -> 0x012a }
    L_0x0099:
        r0 = r1.mNeedUpdate;	 Catch:{ all -> 0x012a }
        if (r0 != 0) goto L_0x010e;	 Catch:{ all -> 0x012a }
    L_0x009d:
        r0 = r1.mVersionName;	 Catch:{ all -> 0x012a }
        r0 = defpackage.arp.c(r13, r0);	 Catch:{ all -> 0x012a }
        if (r0 == 0) goto L_0x010e;	 Catch:{ all -> 0x012a }
    L_0x00a5:
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x012a }
        r0.<init>();	 Catch:{ all -> 0x012a }
        r3 = "on push while version skip: ";	 Catch:{ all -> 0x012a }
        r0 = r0.append(r3);	 Catch:{ all -> 0x012a }
        r3 = r1.mVersionName;	 Catch:{ all -> 0x012a }
        r0 = r0.append(r3);	 Catch:{ all -> 0x012a }
        r0 = r0.toString();	 Catch:{ all -> 0x012a }
        defpackage.arx.c(r0);	 Catch:{ all -> 0x012a }
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x012a }
        r0.<init>();	 Catch:{ all -> 0x012a }
        r3 = "Version skip: ";	 Catch:{ all -> 0x012a }
        r0 = r0.append(r3);	 Catch:{ all -> 0x012a }
        r1 = r1.mVersionName;	 Catch:{ all -> 0x012a }
        r0 = r0.append(r1);	 Catch:{ all -> 0x012a }
        r0 = r0.toString();	 Catch:{ all -> 0x012a }
        r2.c(r0);	 Catch:{ all -> 0x012a }
    L_0x00d5:
        if (r4 == 0) goto L_0x0007;
    L_0x00d7:
        r4.release();
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "wake lock state after release:";
        r0 = r0.append(r1);
        r1 = r4.isHeld();
        r0 = r0.append(r1);
        r0 = r0.toString();
        defpackage.arx.a(r13, r0);
        goto L_0x0007;
    L_0x00f6:
        r0 = move-exception;
        r12 = r1;
        r1 = r0;
        r0 = r12;
        goto L_0x0061;
    L_0x00fc:
        r10 = 3000; // 0xbb8 float:4.204E-42 double:1.482E-320;
        java.lang.Thread.sleep(r10);	 Catch:{ InterruptedException -> 0x0107 }
        r2 = r3;
        r12 = r1;
        r1 = r0;
        r0 = r12;
        goto L_0x0033;
    L_0x0107:
        r2 = move-exception;
        r2 = r3;
        r12 = r1;
        r1 = r0;
        r0 = r12;
        goto L_0x0033;
    L_0x010e:
        r13.a(r1);	 Catch:{ all -> 0x012a }
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x012a }
        r0.<init>();	 Catch:{ all -> 0x012a }
        r3 = "New Version: ";	 Catch:{ all -> 0x012a }
        r0 = r0.append(r3);	 Catch:{ all -> 0x012a }
        r1 = r1.mVersionName;	 Catch:{ all -> 0x012a }
        r0 = r0.append(r1);	 Catch:{ all -> 0x012a }
        r0 = r0.toString();	 Catch:{ all -> 0x012a }
        r2.a(r0);	 Catch:{ all -> 0x012a }
        goto L_0x00d5;
    L_0x012a:
        r0 = move-exception;
        if (r4 == 0) goto L_0x014a;
    L_0x012d:
        r4.release();
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "wake lock state after release:";
        r1 = r1.append(r2);
        r2 = r4.isHeld();
        r1 = r1.append(r2);
        r1 = r1.toString();
        defpackage.arx.a(r13, r1);
    L_0x014a:
        throw r0;
    L_0x014b:
        r0 = "on push while no update!";	 Catch:{ all -> 0x012a }
        defpackage.arx.c(r0);	 Catch:{ all -> 0x012a }
        defpackage.apb.a(r13);	 Catch:{ all -> 0x012a }
        r0 = "No update!";	 Catch:{ all -> 0x012a }
        r2.b(r0);	 Catch:{ all -> 0x012a }
        goto L_0x00d5;	 Catch:{ all -> 0x012a }
    L_0x015a:
        if (r0 != 0) goto L_0x0180;	 Catch:{ all -> 0x012a }
    L_0x015c:
        r0 = new arw;	 Catch:{ all -> 0x012a }
        r1 = "Unknown Exception!";	 Catch:{ all -> 0x012a }
        r0.<init>(r1);	 Catch:{ all -> 0x012a }
        r1 = r0;	 Catch:{ all -> 0x012a }
    L_0x0164:
        r0 = r1.b();	 Catch:{ all -> 0x012a }
        if (r0 == 0) goto L_0x017c;	 Catch:{ all -> 0x012a }
    L_0x016a:
        r0 = r1.a();	 Catch:{ all -> 0x012a }
    L_0x016e:
        r1 = r1.getMessage();	 Catch:{ all -> 0x012a }
        r2.a(r0, r1);	 Catch:{ all -> 0x012a }
        r0 = "push update check return null";	 Catch:{ all -> 0x012a }
        defpackage.arx.a(r13, r0);	 Catch:{ all -> 0x012a }
        goto L_0x00d5;
    L_0x017c:
        r0 = 100000; // 0x186a0 float:1.4013E-40 double:4.94066E-319;
        goto L_0x016e;
    L_0x0180:
        r1 = r0;
        goto L_0x0164;
    L_0x0182:
        r4 = r1;
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.update.service.MzUpdateComponentService.a():void");
    }

    public void onCreate() {
        super.onCreate();
        arx.d("onCreate");
        HandlerThread handlerThread = new HandlerThread("MzUpdateComponentService[InternalTread]");
        handlerThread.start();
        this.c = handlerThread.getLooper();
        this.d = new a(this, this.c);
        this.e = new Handler(getMainLooper());
    }

    public void onDestroy() {
        super.onDestroy();
        arx.d("onDestroy");
        this.c.quit();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        a(intent, i2);
        return 2;
    }

    private void a(Intent intent, int i) {
        if (intent != null && intent.getExtras() != null) {
            Bundle extras = intent.getExtras();
            if (extras.containsKey("action")) {
                int i2 = extras.getInt("action");
                arx.c("handle command : " + i2);
                if (a(i2)) {
                    arx.d("Request too fast, skip action: " + i2);
                    return;
                }
                switch (i2) {
                    case 0:
                        a((UpdateInfo) extras.getParcelable("update_info"));
                        a(0, intent, i);
                        return;
                    case 1:
                        a(1, intent, i);
                        return;
                    case 2:
                        a(2, intent, i);
                        return;
                    case 3:
                        a(3, intent, i);
                        return;
                    case 4:
                        c((UpdateInfo) extras.getParcelable("update_info"));
                        a(0, intent, i);
                        return;
                    case 5:
                        d((UpdateInfo) extras.getParcelable("update_info"));
                        a(0, intent, i);
                        return;
                    case 6:
                        e((UpdateInfo) extras.getParcelable("update_info"));
                        a(0, intent, i);
                        return;
                    case 7:
                        f((UpdateInfo) extras.getParcelable("update_info"));
                        a(0, intent, i);
                        return;
                    case 8:
                        g((UpdateInfo) extras.getParcelable("update_info"));
                        a(0, intent, i);
                        return;
                    case 9:
                        b();
                        a(0, intent, i);
                        return;
                    case 10:
                        a(4, intent, i);
                        return;
                    case 11:
                        b((UpdateInfo) extras.getParcelable("update_info"));
                        a(5, intent, i);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private boolean a(int i) {
        if (i == 7 || i == 4 || i == 8 || i == 5) {
            if (SystemClock.elapsedRealtime() - a < 500) {
                return true;
            }
            a = SystemClock.elapsedRealtime();
        }
        return false;
    }

    private void a(int i, Intent intent, int i2) {
        this.d.sendMessage(this.d.obtainMessage(i, i2, 0, intent));
    }

    public static final void a(Context context) {
        arx.b(context, "Handle push msg");
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 3);
        context.startService(intent);
    }

    public static final void b(Context context) {
        arx.b(context, "Request push register");
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 10);
        context.startService(intent);
    }

    public static final void a(Context context, UpdateInfo updateInfo, MzUpdateResponse mzUpdateResponse) {
        Intent a = a(context, updateInfo, false);
        if (mzUpdateResponse != null) {
            a.putExtra("response", mzUpdateResponse);
        }
        context.startService(a);
    }

    private static final Intent a(Context context, UpdateInfo updateInfo, boolean z) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 1);
        intent.putExtra("update_info", updateInfo);
        if (z) {
            intent.putExtra("from_notification", true);
        }
        return intent;
    }

    public static final PendingIntent a(Context context, UpdateInfo updateInfo) {
        return a(context, 1, a(context, updateInfo, true));
    }

    private static final Intent b(Context context, UpdateInfo updateInfo, String str, MzUpdateResponse mzUpdateResponse) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 2);
        intent.putExtra("update_info", updateInfo);
        intent.putExtra("apk_path", str);
        if (mzUpdateResponse != null) {
            intent.putExtra("response", mzUpdateResponse);
        }
        return intent;
    }

    public static final void a(Context context, UpdateInfo updateInfo, String str, MzUpdateResponse mzUpdateResponse) {
        context.startService(b(context, updateInfo, str, mzUpdateResponse));
    }

    public static final PendingIntent a(Context context, UpdateInfo updateInfo, String str) {
        return a(context, 2, b(context, updateInfo, str, null));
    }

    public static final PendingIntent b(Context context, UpdateInfo updateInfo) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 4);
        intent.putExtra("update_info", updateInfo);
        return a(context, 4, intent);
    }

    public static final PendingIntent c(Context context, UpdateInfo updateInfo) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 5);
        intent.putExtra("update_info", updateInfo);
        return a(context, 5, intent);
    }

    public static final PendingIntent d(Context context, UpdateInfo updateInfo) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 6);
        intent.putExtra("update_info", updateInfo);
        return a(context, 6, intent);
    }

    public static final PendingIntent e(Context context, UpdateInfo updateInfo) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 7);
        intent.putExtra("update_info", updateInfo);
        return a(context, 7, intent);
    }

    public static final PendingIntent f(Context context, UpdateInfo updateInfo) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 8);
        intent.putExtra("update_info", updateInfo);
        return a(context, 8, intent);
    }

    public static final void c(Context context) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 9);
        context.startService(intent);
    }

    public static final PendingIntent g(Context context, UpdateInfo updateInfo) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 11);
        intent.putExtra("update_info", updateInfo);
        return a(context, 11, intent);
    }

    private static final PendingIntent a(Context context, int i, Intent intent) {
        return PendingIntent.getService(context, i, intent, 134217728);
    }

    private void a(UpdateInfo updateInfo) {
        if (updateInfo != null) {
            new aru(this, updateInfo).a();
        }
    }

    private void a(UpdateInfo updateInfo, MzUpdateResponse mzUpdateResponse) {
        if (updateInfo != null) {
            aru aru = new aru(this, updateInfo);
            apb.a((Context) this, updateInfo.mVersionName);
            String c = apb.c(this, updateInfo.mVersionName);
            if (asd.c(this, c)) {
                aru.f();
                a(updateInfo, c, mzUpdateResponse);
                return;
            }
            File file = new File(c);
            if (file.exists()) {
                file.delete();
            }
            aru.a(0, 0);
            String b = apb.b(this, updateInfo.mVersionName);
            aqq aqo = new aqo(updateInfo.mUpdateUrl, b, null, null);
            aqo.a(new arr(this, aru));
            arf arc = new arc(5);
            arc.a(updateInfo.mUpdateUrl2);
            this.b = new aqx(this, updateInfo.mUpdateUrl, aqo, arc);
            this.b.a(h(this, updateInfo));
            try {
                if (this.b.a((Context) this)) {
                    if (asd.c(this, b) && apb.a(b, c)) {
                        aru.f();
                        a(updateInfo, c, mzUpdateResponse);
                        return;
                    }
                    arx.d("download apk cant parse or rename!");
                    File file2 = new File(b);
                    if (file2.exists()) {
                        file2.delete();
                    }
                }
            } catch (aqn e) {
                aru.f();
                if (mzUpdateResponse != null) {
                    mzUpdateResponse.b();
                    return;
                }
                return;
            } catch (aqr e2) {
                e2.printStackTrace();
            }
            if (mzUpdateResponse != null) {
                mzUpdateResponse.b();
            }
            arv.a(this).a(arv$a.Download_Failure, updateInfo.mVersionName);
            aru.c();
        } else if (mzUpdateResponse != null) {
            mzUpdateResponse.a();
        }
    }

    protected arb h(Context context, UpdateInfo updateInfo) {
        return new arb(context, updateInfo.mVerifyMode, context.getPackageName(), updateInfo.mSizeByte, updateInfo.mDigest, 0);
    }

    private void a(UpdateInfo updateInfo, String str, MzUpdateResponse mzUpdateResponse) {
        if (mzUpdateResponse != null) {
            mzUpdateResponse.a(str);
            return;
        }
        Context a = aow.a();
        if (a != null) {
            arx.d("start dialog for tracker : " + a);
            a(new ars(this, a, updateInfo, str));
            return;
        }
        a(updateInfo, str, mzUpdateResponse, false);
    }

    private void b(UpdateInfo updateInfo, String str, MzUpdateResponse mzUpdateResponse) {
        if (updateInfo != null && !TextUtils.isEmpty(str)) {
            a(updateInfo, str, mzUpdateResponse, true);
        } else if (mzUpdateResponse != null) {
            mzUpdateResponse.d();
        }
    }

    private void a(UpdateInfo updateInfo, String str, MzUpdateResponse mzUpdateResponse, boolean z) {
        Object obj;
        Context a;
        if (asd.e(this, getPackageName())) {
            obj = 1;
            aru aru = new aru(this, updateInfo);
            aru.b();
            arq.a(this, updateInfo);
            arj$a a2 = arj.a(this, str);
            if (arj$a.SUCCESS.equals(a2)) {
                arv.a(this).a(arv$a.Install_Complete, updateInfo.mVersionName);
                aru.e();
                if (mzUpdateResponse != null) {
                    mzUpdateResponse.c();
                }
            } else if (arj$a.FAILED.equals(a2)) {
                arv.a(this).a(arv$a.Install_Failure, updateInfo.mVersionName, asd.b(this, getPackageName()));
                aru.d();
                if (mzUpdateResponse != null) {
                    mzUpdateResponse.d();
                }
            } else {
                aru.f();
            }
            if (obj != null) {
            }
            if (mzUpdateResponse == null) {
                mzUpdateResponse.e();
            }
            a = aow.a();
            if (a != null) {
                arx.d("start system install for tracker : " + a);
                a(new art(this, a, str, updateInfo));
                return;
            } else if (z) {
                new aru(this, updateInfo).a(str);
                return;
            } else {
                arq.a(this, updateInfo);
                Intent a3 = arj.a(str);
                a3.addFlags(268435456);
                startActivity(a3);
                return;
            }
        }
        obj = null;
        if (obj != null) {
            if (mzUpdateResponse == null) {
                a = aow.a();
                if (a != null) {
                    arx.d("start system install for tracker : " + a);
                    a(new art(this, a, str, updateInfo));
                    return;
                } else if (z) {
                    arq.a(this, updateInfo);
                    Intent a32 = arj.a(str);
                    a32.addFlags(268435456);
                    startActivity(a32);
                    return;
                } else {
                    new aru(this, updateInfo).a(str);
                    return;
                }
            }
            mzUpdateResponse.e();
        }
    }

    private void b(UpdateInfo updateInfo) {
        if (updateInfo != null) {
            arv.a(this).a(arv$a.Install_Complete, updateInfo.mVersionName);
            new aru(this, updateInfo).e();
            return;
        }
        arx.d("notifyUpdateFinish info null");
    }

    private void c(UpdateInfo updateInfo) {
        if (updateInfo != null) {
            new apt(this, updateInfo).b();
        }
    }

    private void d(UpdateInfo updateInfo) {
        if (updateInfo != null) {
            new aqi(this, null, updateInfo, true, true).b();
        }
    }

    private void e(UpdateInfo updateInfo) {
        if (updateInfo != null) {
            new aru(this, updateInfo).f();
            new aqf(this, updateInfo, null, true).b();
        }
    }

    private void f(UpdateInfo updateInfo) {
        if (updateInfo != null) {
            new apv(this, updateInfo, true).b();
        }
    }

    private void g(UpdateInfo updateInfo) {
        if (updateInfo != null) {
            new apv(this, updateInfo, false).b();
        }
    }

    private void b() {
        if (this.b != null) {
            this.b.a();
        }
    }

    private void c() {
        Object pushId = PushManager.getPushId(this);
        if (!TextUtils.isEmpty(pushId)) {
            if (aoy.c(this, pushId)) {
                arp.a((Context) this, true);
            } else {
                arx.d("register push error.");
            }
        }
    }

    private void a(Runnable runnable) {
        this.e.post(runnable);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
