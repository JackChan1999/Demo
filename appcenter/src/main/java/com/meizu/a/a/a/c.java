package com.meizu.a.a.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

public class c<T extends IInterface> implements ServiceConnection {
    private Context a;
    private T b;
    private String c;
    private String d;
    private a<T> e;

    public interface a<T> {
        T b(IBinder iBinder);
    }

    public c(Context context, a<T> serviceStub, String serviceAction, String servicePackage) {
        this.a = context;
        this.e = serviceStub;
        this.c = serviceAction;
        this.d = servicePackage;
    }

    public synchronized T a() {
        T t;
        if (this.b == null) {
            if (c()) {
                try {
                    wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceBindHelper", "cant find service for action : " + this.c);
                t = null;
            }
        }
        t = this.b;
        return t;
    }

    public void b() {
        try {
            if (this.b != null) {
                this.a.unbindService(this);
                this.b = null;
            }
            this.a = null;
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    private boolean c() {
        Intent intent = new Intent();
        intent.setAction(this.c);
        intent.setPackage(this.d);
        return this.a.bindService(intent, this, 1);
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.d("ServiceBindHelper", "get service.");
        this.b = (IInterface) this.e.b(service);
        synchronized (this) {
            notifyAll();
        }
    }

    public void onServiceDisconnected(ComponentName name) {
        Log.w("ServiceBindHelper", "lost service.");
        this.b = null;
    }
}
