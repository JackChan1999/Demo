package com.meizu.update.service;

import android.app.Activity;
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
import com.meizu.update.display.c;
import com.meizu.update.display.d;
import com.meizu.update.h.b;
import com.meizu.update.h.e;
import com.meizu.update.h.g;
import com.meizu.update.iresponse.MzUpdateResponse;
import java.io.File;

public class MzUpdateComponentService extends Service {
    private static long a = 0;
    private com.meizu.update.d.a.a b;
    private volatile Looper c;
    private volatile a d;
    private Handler e;

    private final class a extends Handler {
        final /* synthetic */ MzUpdateComponentService a;

        public a(MzUpdateComponentService mzUpdateComponentService, Looper looper) {
            this.a = mzUpdateComponentService;
            super(looper);
        }

        public void handleMessage(Message msg) {
            Bundle bundle = msg.obj.getExtras();
            MzUpdateResponse response;
            switch (msg.what) {
                case 1:
                    e.a(true);
                    response = null;
                    if (bundle.containsKey("response")) {
                        response = (MzUpdateResponse) bundle.getParcelable("response");
                    }
                    UpdateInfo info = (UpdateInfo) bundle.getParcelable("update_info");
                    if (bundle.containsKey("from_notification")) {
                        com.meizu.update.g.a.a(this.a).a(com.meizu.update.g.a.a.UpdateAlert_Yes, info.mVersionName, g.b(this.a, this.a.getPackageName()));
                    }
                    this.a.a(info, response);
                    e.a(false);
                    break;
                case 2:
                    e.a(true);
                    String path = bundle.getString("apk_path");
                    response = null;
                    if (bundle.containsKey("response")) {
                        response = (MzUpdateResponse) bundle.getParcelable("response");
                    }
                    this.a.b((UpdateInfo) bundle.getParcelable("update_info"), path, response);
                    e.a(false);
                    break;
                case 3:
                    this.a.a();
                    break;
                case 4:
                    this.a.c();
                    break;
                case 5:
                    com.meizu.update.a.a.a(this.a);
                    break;
            }
            this.a.stopSelf(msg.arg1);
        }
    }

    private void a() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0006 in list [B:31:0x00d1]
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
        r15 = this;
        r12 = com.meizu.update.h.g.d();
        if (r12 == 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        r11 = 0;
        r12 = "power";
        r5 = r15.getSystemService(r12);
        r5 = (android.os.PowerManager) r5;
        if (r5 == 0) goto L_0x0019;
    L_0x0012:
        r12 = 1;
        r13 = "MzUpdateComponent[PushCheck]";
        r11 = r5.newWakeLock(r12, r13);
    L_0x0019:
        if (r11 == 0) goto L_0x0023;
    L_0x001b:
        r12 = "acquire wake lock for push check.";
        com.meizu.update.h.cancel.a(r15, r12);
        r11.acquire();
    L_0x0023:
        r12 = "onPushUpdate check...";	 Catch:{ all -> 0x011c }
        com.meizu.update.h.cancel.a(r15, r12);	 Catch:{ all -> 0x011c }
        r3 = 3;	 Catch:{ all -> 0x011c }
        r6 = 60000; // 0xea60 float:8.4078E-41 double:2.9644E-319;	 Catch:{ all -> 0x011c }
        r8 = android.os.SystemClock.elapsedRealtime();	 Catch:{ all -> 0x011c }
        r1 = 0;	 Catch:{ all -> 0x011c }
        r2 = 0;	 Catch:{ all -> 0x011c }
        r4 = r3;	 Catch:{ all -> 0x011c }
    L_0x0033:
        r3 = r4 + -1;	 Catch:{ all -> 0x011c }
        if (r4 <= 0) goto L_0x0060;	 Catch:{ all -> 0x011c }
    L_0x0037:
        r12 = android.os.SystemClock.elapsedRealtime();	 Catch:{ all -> 0x011c }
        r12 = r12 - r8;	 Catch:{ all -> 0x011c }
        r12 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1));	 Catch:{ all -> 0x011c }
        if (r12 > 0) goto L_0x0060;	 Catch:{ all -> 0x011c }
    L_0x0040:
        r12 = new java.lang.StringBuilder;	 Catch:{ all -> 0x011c }
        r12.<init>();	 Catch:{ all -> 0x011c }
        r13 = "start check try:";	 Catch:{ all -> 0x011c }
        r12 = r12.append(r13);	 Catch:{ all -> 0x011c }
        r12 = r12.append(r3);	 Catch:{ all -> 0x011c }
        r12 = r12.toString();	 Catch:{ all -> 0x011c }
        com.meizu.update.h.cancel.a(r15, r12);	 Catch:{ all -> 0x011c }
        r12 = r15.getPackageName();	 Catch:{ a -> 0x00f0 }
        r2 = com.meizu.update.c.cancel(r15, r12);	 Catch:{ a -> 0x00f0 }
    L_0x005e:
        if (r2 == 0) goto L_0x00f4;
    L_0x0060:
        r10 = new com.meizu.update.push.a;	 Catch:{ all -> 0x011c }
        r10.<init>(r15);	 Catch:{ all -> 0x011c }
        if (r2 == 0) goto L_0x014b;	 Catch:{ all -> 0x011c }
    L_0x0067:
        r12 = new java.lang.StringBuilder;	 Catch:{ all -> 0x011c }
        r12.<init>();	 Catch:{ all -> 0x011c }
        r13 = "push update check end:";	 Catch:{ all -> 0x011c }
        r12 = r12.append(r13);	 Catch:{ all -> 0x011c }
        r13 = r2.mExistsUpdate;	 Catch:{ all -> 0x011c }
        r12 = r12.append(r13);	 Catch:{ all -> 0x011c }
        r13 = ",";	 Catch:{ all -> 0x011c }
        r12 = r12.append(r13);	 Catch:{ all -> 0x011c }
        r13 = r2.mNeedUpdate;	 Catch:{ all -> 0x011c }
        r12 = r12.append(r13);	 Catch:{ all -> 0x011c }
        r12 = r12.toString();	 Catch:{ all -> 0x011c }
        com.meizu.update.h.cancel.a(r15, r12);	 Catch:{ all -> 0x011c }
        r12 = r2.mExistsUpdate;	 Catch:{ all -> 0x011c }
        if (r12 != 0) goto L_0x0093;	 Catch:{ all -> 0x011c }
    L_0x008f:
        r12 = r2.mNeedUpdate;	 Catch:{ all -> 0x011c }
        if (r12 == 0) goto L_0x013d;	 Catch:{ all -> 0x011c }
    L_0x0093:
        r12 = r2.mNeedUpdate;	 Catch:{ all -> 0x011c }
        if (r12 != 0) goto L_0x0100;	 Catch:{ all -> 0x011c }
    L_0x0097:
        r12 = r2.mVersionName;	 Catch:{ all -> 0x011c }
        r12 = com.meizu.update.push.cancel.c(r15, r12);	 Catch:{ all -> 0x011c }
        if (r12 == 0) goto L_0x0100;	 Catch:{ all -> 0x011c }
    L_0x009f:
        r12 = new java.lang.StringBuilder;	 Catch:{ all -> 0x011c }
        r12.<init>();	 Catch:{ all -> 0x011c }
        r13 = "on push while version skip: ";	 Catch:{ all -> 0x011c }
        r12 = r12.append(r13);	 Catch:{ all -> 0x011c }
        r13 = r2.mVersionName;	 Catch:{ all -> 0x011c }
        r12 = r12.append(r13);	 Catch:{ all -> 0x011c }
        r12 = r12.toString();	 Catch:{ all -> 0x011c }
        com.meizu.update.h.cancel.c(r12);	 Catch:{ all -> 0x011c }
        r12 = new java.lang.StringBuilder;	 Catch:{ all -> 0x011c }
        r12.<init>();	 Catch:{ all -> 0x011c }
        r13 = "Version skip: ";	 Catch:{ all -> 0x011c }
        r12 = r12.append(r13);	 Catch:{ all -> 0x011c }
        r13 = r2.mVersionName;	 Catch:{ all -> 0x011c }
        r12 = r12.append(r13);	 Catch:{ all -> 0x011c }
        r12 = r12.toString();	 Catch:{ all -> 0x011c }
        r10.c(r12);	 Catch:{ all -> 0x011c }
    L_0x00cf:
        if (r11 == 0) goto L_0x0006;
    L_0x00d1:
        r11.release();
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r13 = "wake lock state after release:";
        r12 = r12.append(r13);
        r13 = r11.isHeld();
        r12 = r12.append(r13);
        r12 = r12.toString();
        com.meizu.update.h.cancel.a(r15, r12);
        goto L_0x0006;
    L_0x00f0:
        r0 = move-exception;
        r1 = r0;
        goto L_0x005e;
    L_0x00f4:
        r12 = 3000; // 0xbb8 float:4.204E-42 double:1.482E-320;
        java.lang.Thread.sleep(r12);	 Catch:{ InterruptedException -> 0x00fc }
        r4 = r3;
        goto L_0x0033;
    L_0x00fc:
        r12 = move-exception;
        r4 = r3;
        goto L_0x0033;
    L_0x0100:
        r15.a(r2);	 Catch:{ all -> 0x011c }
        r12 = new java.lang.StringBuilder;	 Catch:{ all -> 0x011c }
        r12.<init>();	 Catch:{ all -> 0x011c }
        r13 = "New Version: ";	 Catch:{ all -> 0x011c }
        r12 = r12.append(r13);	 Catch:{ all -> 0x011c }
        r13 = r2.mVersionName;	 Catch:{ all -> 0x011c }
        r12 = r12.append(r13);	 Catch:{ all -> 0x011c }
        r12 = r12.toString();	 Catch:{ all -> 0x011c }
        r10.a(r12);	 Catch:{ all -> 0x011c }
        goto L_0x00cf;
    L_0x011c:
        r12 = move-exception;
        if (r11 == 0) goto L_0x013c;
    L_0x011f:
        r11.release();
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "wake lock state after release:";
        r13 = r13.append(r14);
        r14 = r11.isHeld();
        r13 = r13.append(r14);
        r13 = r13.toString();
        com.meizu.update.h.cancel.a(r15, r13);
    L_0x013c:
        throw r12;
    L_0x013d:
        r12 = "on push while no update!";	 Catch:{ all -> 0x011c }
        com.meizu.update.h.cancel.c(r12);	 Catch:{ all -> 0x011c }
        com.meizu.update.a.a.a(r15);	 Catch:{ all -> 0x011c }
        r12 = "No update!";	 Catch:{ all -> 0x011c }
        r10.cancel(r12);	 Catch:{ all -> 0x011c }
        goto L_0x00cf;	 Catch:{ all -> 0x011c }
    L_0x014b:
        if (r1 != 0) goto L_0x0154;	 Catch:{ all -> 0x011c }
    L_0x014d:
        r1 = new com.meizu.update.h.a;	 Catch:{ all -> 0x011c }
        r12 = "Unknown Exception!";	 Catch:{ all -> 0x011c }
        r1.<init>(r12);	 Catch:{ all -> 0x011c }
    L_0x0154:
        r12 = r1.cancel();	 Catch:{ all -> 0x011c }
        if (r12 == 0) goto L_0x016c;	 Catch:{ all -> 0x011c }
    L_0x015a:
        r12 = r1.a();	 Catch:{ all -> 0x011c }
    L_0x015e:
        r13 = r1.getMessage();	 Catch:{ all -> 0x011c }
        r10.a(r12, r13);	 Catch:{ all -> 0x011c }
        r12 = "push update check return null";	 Catch:{ all -> 0x011c }
        com.meizu.update.h.cancel.a(r15, r12);	 Catch:{ all -> 0x011c }
        goto L_0x00cf;
    L_0x016c:
        r12 = 100000; // 0x186a0 float:1.4013E-40 double:4.94066E-319;
        goto L_0x015e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.update.service.MzUpdateComponentService.a():void");
    }

    public void onCreate() {
        super.onCreate();
        b.d("onCreate");
        HandlerThread thread = new HandlerThread("MzUpdateComponentService[InternalTread]");
        thread.start();
        this.c = thread.getLooper();
        this.d = new a(this, this.c);
        this.e = new Handler(getMainLooper());
    }

    public void onDestroy() {
        super.onDestroy();
        b.d("onDestroy");
        this.c.quit();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        a(intent, startId);
        return 2;
    }

    private void a(Intent intent, int startId) {
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            if (bundle.containsKey("action")) {
                int action = bundle.getInt("action");
                b.c("handle command : " + action);
                if (a(action)) {
                    b.d("Request too fast, skip action: " + action);
                    return;
                }
                switch (action) {
                    case 0:
                        a((UpdateInfo) bundle.getParcelable("update_info"));
                        a(0, intent, startId);
                        return;
                    case 1:
                        a(1, intent, startId);
                        return;
                    case 2:
                        a(2, intent, startId);
                        return;
                    case 3:
                        a(3, intent, startId);
                        return;
                    case 4:
                        c((UpdateInfo) bundle.getParcelable("update_info"));
                        a(0, intent, startId);
                        return;
                    case 5:
                        d((UpdateInfo) bundle.getParcelable("update_info"));
                        a(0, intent, startId);
                        return;
                    case 6:
                        e((UpdateInfo) bundle.getParcelable("update_info"));
                        a(0, intent, startId);
                        return;
                    case 7:
                        f((UpdateInfo) bundle.getParcelable("update_info"));
                        a(0, intent, startId);
                        return;
                    case 8:
                        g((UpdateInfo) bundle.getParcelable("update_info"));
                        a(0, intent, startId);
                        return;
                    case 9:
                        b();
                        a(0, intent, startId);
                        return;
                    case 10:
                        a(4, intent, startId);
                        return;
                    case 11:
                        b((UpdateInfo) bundle.getParcelable("update_info"));
                        a(5, intent, startId);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private boolean a(int action) {
        if (action == 7 || action == 4 || action == 8 || action == 5) {
            if (SystemClock.elapsedRealtime() - a < 500) {
                return true;
            }
            a = SystemClock.elapsedRealtime();
        }
        return false;
    }

    private void a(int what, Intent intent, int startId) {
        this.d.sendMessage(this.d.obtainMessage(what, startId, 0, intent));
    }

    public static final void a(Context context) {
        b.b(context, "Handle push msg");
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 3);
        context.startService(intent);
    }

    public static final void b(Context context) {
        b.b(context, "Request push register");
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 10);
        context.startService(intent);
    }

    public static final void a(Context context, UpdateInfo info, MzUpdateResponse response) {
        Intent intent = a(context, info, false);
        if (response != null) {
            intent.putExtra("response", response);
        }
        context.startService(intent);
    }

    private static final Intent a(Context context, UpdateInfo info, boolean fromNotification) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 1);
        intent.putExtra("update_info", info);
        if (fromNotification) {
            intent.putExtra("from_notification", true);
        }
        return intent;
    }

    public static final PendingIntent a(Context context, UpdateInfo info) {
        return a(context, 1, a(context, info, true));
    }

    private static final Intent b(Context context, UpdateInfo info, String path, MzUpdateResponse response) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 2);
        intent.putExtra("update_info", info);
        intent.putExtra("apk_path", path);
        if (response != null) {
            intent.putExtra("response", response);
        }
        return intent;
    }

    public static final void a(Context context, UpdateInfo info, String path, MzUpdateResponse response) {
        context.startService(b(context, info, path, response));
    }

    public static final PendingIntent a(Context context, UpdateInfo info, String path) {
        return a(context, 2, b(context, info, path, null));
    }

    public static final PendingIntent b(Context context, UpdateInfo info) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 4);
        intent.putExtra("update_info", info);
        return a(context, 4, intent);
    }

    public static final PendingIntent c(Context context, UpdateInfo info) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 5);
        intent.putExtra("update_info", info);
        return a(context, 5, intent);
    }

    public static final PendingIntent d(Context context, UpdateInfo info) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 6);
        intent.putExtra("update_info", info);
        return a(context, 6, intent);
    }

    public static final PendingIntent e(Context context, UpdateInfo info) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 7);
        intent.putExtra("update_info", info);
        return a(context, 7, intent);
    }

    public static final PendingIntent f(Context context, UpdateInfo info) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 8);
        intent.putExtra("update_info", info);
        return a(context, 8, intent);
    }

    public static final void c(Context context) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 9);
        context.startService(intent);
    }

    public static final PendingIntent g(Context context, UpdateInfo info) {
        Intent intent = new Intent(context, MzUpdateComponentService.class);
        intent.putExtra("action", 11);
        intent.putExtra("update_info", info);
        return a(context, 11, intent);
    }

    private static final PendingIntent a(Context context, int requestCode, Intent intent) {
        return PendingIntent.getService(context, requestCode, intent, 134217728);
    }

    private void a(UpdateInfo info) {
        if (info != null) {
            new a(this, info).a();
        }
    }

    private void a(UpdateInfo info, MzUpdateResponse response) {
        if (info != null) {
            final a notifyManager = new a(this, info);
            com.meizu.update.a.a.a((Context) this, info.mVersionName);
            String apkFilePath = com.meizu.update.a.a.c(this, info.mVersionName);
            if (g.c(this, apkFilePath)) {
                notifyManager.f();
                a(info, apkFilePath, response);
                return;
            }
            File apkFile = new File(apkFilePath);
            if (apkFile.exists()) {
                apkFile.delete();
            }
            notifyManager.a(0, 0);
            String tempFilePath = com.meizu.update.a.a.b(this, info.mVersionName);
            com.meizu.update.d.b downloaderImpl = new com.meizu.update.d.b(info.mUpdateUrl, tempFilePath, null, null);
            downloaderImpl.a(new com.meizu.update.d.b.a(this) {
                final /* synthetic */ MzUpdateComponentService b;

                public void a(int progress, long speed) {
                    notifyManager.a(progress, speed);
                }
            });
            com.meizu.update.d.c.e retryTracker = new com.meizu.update.d.c.b(5);
            retryTracker.a(info.mUpdateUrl2);
            this.b = new com.meizu.update.d.a.a(this, info.mUpdateUrl, downloaderImpl, retryTracker);
            this.b.a(h(this, info));
            try {
                if (this.b.a((Context) this)) {
                    if (g.c(this, tempFilePath) && com.meizu.update.a.a.a(tempFilePath, apkFilePath)) {
                        notifyManager.f();
                        a(info, apkFilePath, response);
                        return;
                    }
                    b.d("download apk cant parse or rename!");
                    File tempFile = new File(tempFilePath);
                    if (tempFile.exists()) {
                        tempFile.delete();
                    }
                }
            } catch (com.meizu.update.d.a e) {
                notifyManager.f();
                if (response != null) {
                    response.b();
                    return;
                }
                return;
            } catch (com.meizu.update.d.e e2) {
                e2.printStackTrace();
            }
            if (response != null) {
                response.b();
            }
            com.meizu.update.g.a.a(this).a(com.meizu.update.g.a.a.Download_Failure, info.mVersionName);
            notifyManager.c();
        } else if (response != null) {
            response.a();
        }
    }

    protected com.meizu.update.d.c.a h(Context context, UpdateInfo info) {
        return new com.meizu.update.d.c.a(context, info.mVerifyMode, context.getPackageName(), info.mSizeByte, info.mDigest, 0);
    }

    private void a(final UpdateInfo info, final String path, MzUpdateResponse response) {
        if (response != null) {
            response.a(path);
            return;
        }
        final Context latestTracker = com.meizu.update.a.a();
        if (latestTracker != null) {
            b.d("start dialog for tracker : " + latestTracker);
            a(new Runnable(this) {
                final /* synthetic */ MzUpdateComponentService d;

                public void run() {
                    com.meizu.update.display.a displayBase = new d(latestTracker, null, info, path);
                    displayBase.a(!(latestTracker instanceof Activity));
                    displayBase.b();
                }
            });
            return;
        }
        a(info, path, response, false);
    }

    private void b(UpdateInfo info, String path, MzUpdateResponse response) {
        if (info != null && !TextUtils.isEmpty(path)) {
            a(info, path, response, true);
        } else if (response != null) {
            response.d();
        }
    }

    private void a(final UpdateInfo info, String path, MzUpdateResponse response, boolean forceInstall) {
        boolean hasInstall = false;
        if (g.e(this, getPackageName())) {
            hasInstall = true;
            a notifyManager = new a(this, info);
            notifyManager.b();
            com.meizu.update.f.a.a(this, info);
            com.meizu.update.e.a.a status = com.meizu.update.e.a.a(this, path);
            if (com.meizu.update.e.a.a.SUCCESS.equals(status)) {
                com.meizu.update.g.a.a(this).a(com.meizu.update.g.a.a.Install_Complete, info.mVersionName);
                notifyManager.e();
                if (response != null) {
                    response.c();
                }
            } else if (com.meizu.update.e.a.a.FAILED.equals(status)) {
                com.meizu.update.g.a.a(this).a(com.meizu.update.g.a.a.Install_Failure, info.mVersionName, g.b(this, getPackageName()));
                notifyManager.d();
                if (response != null) {
                    response.d();
                }
            } else {
                hasInstall = false;
                notifyManager.f();
            }
        }
        if (!hasInstall) {
            if (response != null) {
                response.e();
                return;
            }
            final Context latestTracker = com.meizu.update.a.a();
            if (latestTracker != null) {
                b.d("start system install for tracker : " + latestTracker);
                final String installPath = path;
                a(new Runnable(this) {
                    final /* synthetic */ MzUpdateComponentService d;

                    public void run() {
                        try {
                            com.meizu.update.e.a.a(latestTracker, installPath, info);
                        } catch (Exception e) {
                            e.printStackTrace();
                            com.meizu.update.g.a.a(this.d).a(com.meizu.update.g.a.a.Install_Failure, info.mVersionName, g.b(this.d, this.d.getPackageName()));
                            new a(this.d, info).d();
                        }
                    }
                });
            } else if (forceInstall) {
                com.meizu.update.f.a.a(this, info);
                Intent intent = com.meizu.update.e.a.a(path);
                intent.addFlags(268435456);
                startActivity(intent);
            } else {
                new a(this, info).a(path);
            }
        }
    }

    private void b(UpdateInfo info) {
        if (info != null) {
            com.meizu.update.g.a.a(this).a(com.meizu.update.g.a.a.Install_Complete, info.mVersionName);
            new a(this, info).e();
            return;
        }
        b.d("notifyUpdateFinish info null");
    }

    private void c(UpdateInfo info) {
        if (info != null) {
            new com.meizu.update.display.b(this, info).b();
        }
    }

    private void d(UpdateInfo info) {
        if (info != null) {
            new com.meizu.update.display.g(this, null, info, true, true).b();
        }
    }

    private void e(UpdateInfo info) {
        if (info != null) {
            new a(this, info).f();
            new com.meizu.update.display.e(this, info, null, true).b();
        }
    }

    private void f(UpdateInfo info) {
        if (info != null) {
            new c(this, info, true).b();
        }
    }

    private void g(UpdateInfo info) {
        if (info != null) {
            new c(this, info, false).b();
        }
    }

    private void b() {
        if (this.b != null) {
            this.b.a();
        }
    }

    private void c() {
        String pushid = PushManager.getPushId(this);
        if (!TextUtils.isEmpty(pushid)) {
            if (com.meizu.update.c.c(this, pushid)) {
                com.meizu.update.push.b.a((Context) this, true);
            } else {
                b.d("register push error.");
            }
        }
    }

    private void a(Runnable r) {
        this.e.post(r);
    }

    public IBinder onBind(Intent paramIntent) {
        return null;
    }
}
