package com.meizu.statsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings.System;
import com.meizu.statsapp.util.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UsageStatsManagerServer extends com.meizu.statsapp.a.a {
    public static String a = "00000000000000000000000000000000000";
    private static Object b = new Object();
    private static volatile UsageStatsManagerServer c;
    private Context d;
    private boolean e;
    private boolean f;
    private d g;
    private e h;
    private HandlerThread i = new HandlerThread("RecordEventThread");
    private b j;
    private Map<String, String> k = new HashMap();
    private c l;
    private volatile boolean m = true;
    private LinkedList<a> n = new LinkedList();
    private long o;
    private List<Event> p = new ArrayList();
    private int q = 0;
    private String r = null;
    private String s = null;
    private SharedPreferences t = null;
    private Runnable u = new Runnable(this) {
        final /* synthetic */ UsageStatsManagerServer a;

        {
            this.a = r1;
        }

        public void run() {
            this.a.k.clear();
            UsageStatusLog.d("UsageStatsManagerServer", "cleared packageSessionMap ");
        }
    };

    public class ConnectionReceiver extends BroadcastReceiver {
        final /* synthetic */ UsageStatsManagerServer a;

        public ConnectionReceiver(UsageStatsManagerServer usageStatsManagerServer) {
            this.a = usageStatsManagerServer;
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                this.a.r = Utils.getNetworkType(this.a.d);
            }
        }
    }

    private static class a {
        private String a;
        private String b;
        private long c;

        public a(String packageName, String name, long time) {
            this.a = packageName;
            this.b = name;
            this.c = time;
        }

        public String a() {
            return this.a;
        }

        public String b() {
            return this.b;
        }

        public long c() {
            return this.c;
        }

        public String toString() {
            return "{" + this.b + ", " + this.c + ", " + this.a + "}";
        }
    }

    private class b extends Handler {
        final /* synthetic */ UsageStatsManagerServer a;

        public b(UsageStatsManagerServer usageStatsManagerServer, Looper looper) {
            this.a = usageStatsManagerServer;
            super(looper);
        }

        public void handleMessage(Message msg) {
            try {
                Event event;
                a startPage;
                switch (msg.what) {
                    case 1:
                        UsageStatusLog.d("UsageStatsManagerServer", "ON_EVENT, mRecording=" + this.a.m + ", event=" + msg.obj);
                        if (this.a.m) {
                            event = msg.obj;
                            event.b((long) this.a.q);
                            event.e(this.a.r);
                            if (this.a.e && Utils.isEmpty((String) this.a.k.get(event.e()))) {
                                this.a.h.b();
                            }
                            a(event);
                            b(event);
                            return;
                        }
                        return;
                    case 2:
                        UsageStatusLog.d("UsageStatsManagerServer", "ON_EVENT_REALTIME, mRecording=" + this.a.m + ", event=" + msg.obj);
                        if (this.a.m) {
                            event = (Event) msg.obj;
                            event.b((long) this.a.q);
                            event.e(this.a.r);
                            a(event);
                            try {
                                if (!this.a.h.a(event, Utils.isWiFiWorking(this.a.d))) {
                                    UsageStatusLog.d("UsageStatsManagerServer", "ON_EVENT_REALTIME, uploadEvent unsuccessfully, store event.");
                                    b(event);
                                    return;
                                }
                                return;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        return;
                    case 3:
                        UsageStatusLog.d("UsageStatsManagerServer", "ON_PAGE_START, mRecording=" + this.a.m + ", page=" + msg.obj);
                        if (this.a.m) {
                            startPage = msg.obj;
                            if (this.a.n.size() <= 0) {
                                this.a.j.removeCallbacks(this.a.u);
                                if (Utils.isEmpty((String) this.a.k.get(startPage.a()))) {
                                    UsageStatusLog.d("UsageStatsManagerServer", "ON_PAGE_START, app boot, new session.");
                                    this.a.k.put(startPage.a(), UUID.randomUUID().toString());
                                    if (this.a.e) {
                                        this.a.h.b();
                                    }
                                }
                            }
                            this.a.n.addLast(startPage);
                            return;
                        }
                        return;
                    case 4:
                        UsageStatusLog.d("UsageStatsManagerServer", "ON_PAGE_STOP, mRecording=" + this.a.m + ", page=" + msg.obj);
                        if (this.a.m) {
                            a stopPage = msg.obj;
                            Iterator<a> iterator = this.a.n.iterator();
                            if (iterator != null) {
                                int delCount;
                                int i;
                                long currentTime = System.currentTimeMillis();
                                while (iterator.hasNext()) {
                                    startPage = (a) iterator.next();
                                    if (stopPage.b().equals(startPage.b())) {
                                        iterator.remove();
                                        Map<String, String> properties = new HashMap();
                                        properties.put("start_time", String.valueOf(startPage.c));
                                        properties.put("stop_time", String.valueOf(stopPage.c));
                                        event = new Event(stopPage.b(), 2, stopPage.c(), null, stopPage.a(), null, properties, Build.DISPLAY, Utils.getPackageVersion(stopPage.a(), this.a.d));
                                        event.b((long) this.a.q);
                                        event.e(this.a.r);
                                        a(event);
                                        b(event);
                                        if (this.a.n.size() <= 0) {
                                            this.a.o = stopPage.c;
                                            this.a.j.postDelayed(this.a.u, 30000);
                                            UsageStatusLog.d("UsageStatsManagerServer", "ON_PAGE_STOP, switch to background.");
                                        }
                                        delCount = this.a.n.size() - 100;
                                        if (delCount > 0) {
                                            UsageStatusLog.d("UsageStatsManagerServer", "ON_PAGE_STOP, too many pages in stack, delete pages " + delCount);
                                            for (i = 0; i < delCount; i++) {
                                                this.a.n.removeFirst();
                                            }
                                            return;
                                        }
                                        return;
                                    } else if (Math.abs(currentTime - startPage.c) > 43200000) {
                                        UsageStatusLog.d("UsageStatsManagerServer", "ON_PAGE_STOP, page time out :" + startPage);
                                        iterator.remove();
                                    }
                                }
                                if (this.a.n.size() <= 0) {
                                    this.a.o = stopPage.c;
                                    this.a.j.postDelayed(this.a.u, 30000);
                                    UsageStatusLog.d("UsageStatsManagerServer", "ON_PAGE_STOP, switch to background.");
                                }
                                delCount = this.a.n.size() - 100;
                                if (delCount > 0) {
                                    UsageStatusLog.d("UsageStatsManagerServer", "ON_PAGE_STOP, too many pages in stack, delete pages " + delCount);
                                    for (i = 0; i < delCount; i++) {
                                        this.a.n.removeFirst();
                                    }
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    case 5:
                        this.a.n.clear();
                        this.a.o = 0;
                        return;
                    default:
                        return;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            e2.printStackTrace();
        }

        private void a(Event event) {
            try {
                String sessionid = (String) this.a.k.get(event.e());
                if (Utils.isEmpty(sessionid)) {
                    sessionid = UUID.randomUUID().toString();
                    this.a.k.put(event.e(), sessionid);
                }
                event.b(sessionid);
                if (3 == event.b()) {
                    event.c("com.meizu.uxip.log");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void b(Event event) {
            UsageStatusLog.d("UsageStatsManagerServer", "insert Event " + event.toString());
            try {
                this.a.g.a(event);
                if (this.a.e) {
                    this.a.h.a();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class c extends ContentObserver {
        final /* synthetic */ UsageStatsManagerServer a;

        public c(UsageStatsManagerServer usageStatsManagerServer, Handler handler) {
            this.a = usageStatsManagerServer;
            super(handler);
        }

        public void onChange(boolean selfChange, Uri uri) {
            boolean recording = false;
            if (System.getInt(this.a.d.getContentResolver(), "meizu_data_collection", 0) != 0) {
                recording = true;
            }
            UsageStatusLog.d("UsageStatsManagerServer", "usage stats switch changed : " + recording);
            if (this.a.m != recording) {
                this.a.m = recording;
                if (!this.a.m) {
                    this.a.j.sendEmptyMessage(5);
                }
            }
        }
    }

    private UsageStatsManagerServer(Context context, boolean online, boolean upload) {
        this.d = context;
        this.e = online;
        this.f = upload;
        this.i.start();
        this.j = new b(this, this.i.getLooper());
        a();
    }

    static UsageStatsManagerServer a(Context context, boolean online, boolean upload) {
        if (c == null) {
            synchronized (b) {
                if (c == null) {
                    c = new UsageStatsManagerServer(context, online, upload);
                }
            }
        }
        return c;
    }

    private void a() {
        boolean z = false;
        UsageStatusLog.initLog();
        a(this.d);
        this.r = Utils.getNetworkType(this.d);
        this.t = this.d.getSharedPreferences("com.meizu.stats", 0);
        a = this.t.getString(b.g, "00000000000000000000000000000000000");
        ConnectionReceiver connReceiver = new ConnectionReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        try {
            this.d.unregisterReceiver(connReceiver);
        } catch (Exception e) {
        }
        try {
            this.d.registerReceiver(connReceiver, filter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.g = d.a(this.d, this.e);
        if (this.e) {
            this.h = e.a(this.d, this.e, this.f);
        }
        if (!this.e) {
            if (System.getInt(this.d.getContentResolver(), "meizu_data_collection", 0) != 0) {
                z = true;
            }
            this.m = z;
            this.l = new c(this, this.j);
            this.d.getContentResolver().registerContentObserver(System.getUriFor("meizu_data_collection"), true, this.l);
        }
        try {
            if (a == null || "00000000000000000000000000000000000".equals(a)) {
                b();
            } else if (!Utils.checkUmid(a, this.d)) {
                b();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(String packageName, boolean start, String name, long time) throws RemoteException {
        if (!Utils.isEmpty(name)) {
            Message msg;
            a page = new a(packageName, name, time);
            if (start) {
                msg = this.j.obtainMessage(3, page);
            } else {
                msg = this.j.obtainMessage(4, page);
            }
            this.j.sendMessage(msg);
        }
    }

    public void onEvent(Event event) throws RemoteException {
        this.j.sendMessage(this.j.obtainMessage(1, event));
    }

    public void onEventRealtime(Event event) throws RemoteException {
        this.j.sendMessage(this.j.obtainMessage(2, event));
    }

    private void a(Context context) {
        PackageManager pm = context.getPackageManager();
        if (pm != null) {
            try {
                ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), 128);
                if (appInfo != null && appInfo.metaData != null) {
                    this.q = appInfo.metaData.get("uxip_channel_num") == null ? 0 : ((Integer) appInfo.metaData.get("uxip_channel_num")).intValue();
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void b() {
        if (this.e && this.f) {
            this.h.d();
        }
    }

    public void a(boolean upload) throws RemoteException {
        if (this.e && this.f != upload) {
            this.f = upload;
            this.h.b(this.f);
        }
    }
}
