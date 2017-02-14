package com.meizu.account.pay.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

public final class j implements ServiceConnection {
    private Context a;
    private IInterface b;
    private String c;
    private String d;
    private k e;

    public j(Context context, k kVar, String str, String str2) {
        this.a = context;
        this.e = kVar;
        this.c = str;
        this.d = str2;
    }

    public final synchronized IInterface a() {
        IInterface iInterface;
        if (this.b == null) {
            Intent intent = new Intent();
            intent.setAction(this.c);
            intent.setPackage(this.d);
            if (this.a.bindService(intent, this, 1)) {
                try {
                    wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceBindHelper", "cant find service for action : " + this.c);
                iInterface = null;
            }
        }
        iInterface = this.b;
        return iInterface;
    }

    public final void b() {
        try {
            if (this.b != null) {
                this.a.unbindService(this);
                this.b = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.d("ServiceBindHelper", "get service.");
        this.b = (IInterface) this.e.a(iBinder);
        synchronized (this) {
            notifyAll();
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        Log.w("ServiceBindHelper", "lost service.");
        this.b = null;
    }
}
