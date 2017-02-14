package com.meizu.statsapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings.System;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.gslb.b.d;
import com.meizu.statsapp.util.Utils;

final class c {
    private Context a;
    private boolean b;
    private boolean c;
    private HandlerThread d = new HandlerThread("UsageStatsManagerThread");
    private a e;
    private b f;
    private volatile boolean g = true;
    private c h;
    private volatile a i;

    private class a extends Handler {
        final /* synthetic */ c a;

        public a(c cVar, Looper looper) {
            this.a = cVar;
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        if (this.a.i != null) {
                            this.a.i.onEvent((Event) msg.obj);
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                case 2:
                    try {
                        if (this.a.i != null) {
                            this.a.i.onEventRealtime((Event) msg.obj);
                            return;
                        }
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                case 3:
                    try {
                        Bundle data = msg.getData();
                        if (this.a.i != null) {
                            this.a.i.a(data.getString("pckageName"), data.getBoolean("start"), data.getString("name"), data.getLong(RequestManager.TIME));
                            return;
                        }
                        return;
                    } catch (Exception e22) {
                        e22.printStackTrace();
                        return;
                    }
                case 4:
                    boolean upload = msg.arg1 != 0;
                    if (this.a.c != upload) {
                        this.a.c = upload;
                        try {
                            if (this.a.i != null) {
                                this.a.i.a(this.a.c);
                                return;
                            }
                            return;
                        } catch (Exception e222) {
                            e222.printStackTrace();
                            return;
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private class b extends ContentObserver {
        final /* synthetic */ c a;

        public b(c cVar, Handler handler) {
            this.a = cVar;
            super(handler);
        }

        public void onChange(boolean selfChange, Uri uri) {
            boolean recording = false;
            if (System.getInt(this.a.a.getContentResolver(), "meizu_data_collection", 0) != 0) {
                recording = true;
            }
            UsageStatusLog.d("UsageStatsManager", "usage stats switch changed : " + recording);
            if (this.a.g != recording) {
                this.a.g = recording;
            }
        }
    }

    private class c implements ServiceConnection {
        final /* synthetic */ c a;

        private c(c cVar) {
            this.a = cVar;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            synchronized (this.a.d) {
                try {
                    UsageStatusLog.d("UsageStatsManager", "onServiceConnected, " + this.a.h);
                    this.a.i = com.meizu.statsapp.a.a.a(service);
                    UsageStatusLog.d("UsageStatsManager", "unbindService, " + this.a.h);
                    this.a.a.unbindService(this.a.h);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.a.d.notifyAll();
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            UsageStatusLog.d("UsageStatsManager", "onServiceDisconnected, " + this.a.h);
        }
    }

    c(Context context, boolean online, boolean upload) {
        this.a = context;
        this.b = online;
        this.c = upload;
        if (this.b) {
            d.a(context, new com.meizu.statsapp.a.a());
        }
        this.d.start();
        this.e = new a(this, this.d.getLooper());
        this.e.post(new Runnable(this) {
            final /* synthetic */ c a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.b();
            }
        });
    }

    private void b() {
        boolean z = false;
        UsageStatusLog.initLog();
        if (a(this.a)) {
            synchronized (this.d) {
                this.h = new c();
                Intent intent = new Intent(this.a, UsageStatsManagerService.class);
                intent.putExtra("online", this.b);
                intent.putExtra("upload", this.c);
                UsageStatusLog.d("UsageStatsManager", "bindService, " + this.h);
                this.a.bindService(intent, this.h, 1);
                try {
                    this.d.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            this.i = UsageStatsManagerServer.a(this.a, this.b, this.c);
        }
        if (!this.b) {
            if (System.getInt(this.a.getContentResolver(), "meizu_data_collection", 0) != 0) {
                z = true;
            }
            this.g = z;
            this.f = new b(this, this.e);
            this.a.getContentResolver().registerContentObserver(System.getUriFor("meizu_data_collection"), true, this.f);
        }
    }

    private static boolean a(Context context) {
        PackageManager pm = context.getPackageManager();
        if (pm == null) {
            return false;
        }
        try {
            if (pm.getServiceInfo(new ComponentName(context.getPackageName(), UsageStatsManagerService.class.getName()), 0) != null) {
                return true;
            }
            return false;
        } catch (NameNotFoundException e) {
            UsageStatusLog.d("UsageStatsManager", e.getMessage());
            return false;
        }
    }

    public void a(String packageName, boolean start, String name, long time) {
        if (!Utils.isEmpty(name)) {
            Message msg = this.e.obtainMessage(3);
            Bundle data = new Bundle();
            data.putString("pckageName", packageName);
            data.putBoolean("start", start);
            data.putString("name", name);
            data.putLong(RequestManager.TIME, time);
            msg.setData(data);
            this.e.sendMessage(msg);
        }
    }

    public void onEvent(Event event) {
        Message msg = this.e.obtainMessage(1);
        msg.obj = event;
        this.e.sendMessage(msg);
    }

    public void onEventRealtime(Event event) {
        Message msg = this.e.obtainMessage(2);
        msg.obj = event;
        this.e.sendMessage(msg);
    }

    public boolean a() {
        return this.g;
    }
}
