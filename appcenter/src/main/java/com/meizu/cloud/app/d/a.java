package com.meizu.cloud.app.d;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.meizu.cloud.b.a.i;
import com.meizu.g.a.b;

public class a {
    private static final String a = a.class.getSimpleName();
    private b b;
    private ServiceConnection c;
    private ProgressDialog d = new ProgressDialog(this.e);
    private Context e;
    private IBinder f;
    private com.meizu.g.a.a g;

    private class a implements ServiceConnection {
        final /* synthetic */ a a;

        private a(a aVar) {
            this.a = aVar;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            this.a.b = com.meizu.g.a.b.a.b(service);
            try {
                this.a.b.a(this.a.e.getPackageName(), 2);
                this.a.b.c(this.a.e.getString(i.voice_help_tips), this.a.e.getPackageName());
                if (this.a.g != null) {
                    this.a.b.a(this.a.e.getPackageName(), this.a.g);
                }
                if (this.a.d.isShowing()) {
                    this.a.d.cancel();
                    this.a.e();
                }
            } catch (RemoteException e) {
                Log.d(a.a, "onServiceConnected | remote exception = " + e);
            } catch (Exception ex) {
                Log.d(a.a, "onServiceConnected | exception = " + ex);
            }
            Log.d(a.a, "onServiceConnected | mService = " + this.a.b + ",mCallback = " + this.a.g);
        }

        public void onServiceDisconnected(ComponentName name) {
            Log.d(a.a, "onServiceDisconnected");
            try {
                this.a.b.a(this.a.f);
                this.a.b.a(this.a.e.getPackageName());
            } catch (RemoteException e) {
                Log.d(a.a, "onServiceDisconnected | remote exception = " + e);
            } catch (Exception ex) {
                Log.d(a.a, "onServiceDisconnected | exception = " + ex);
            } finally {
                this.a.b = null;
            }
        }
    }

    public a(Context context, com.meizu.g.a.a callback) {
        this.e = context;
        this.g = callback;
        this.d.setMessage(context.getString(i.please_wait));
        this.d.setCancelable(false);
        this.f = new Binder();
    }

    public boolean a() {
        if (this.b != null) {
            try {
                return this.b.a();
            } catch (RemoteException e) {
                Log.d(a, "doDisplay | ex = " + e);
            }
        }
        return false;
    }

    public void b() {
        Log.d(a, "doBind");
        this.c = new a();
        Log.d(a, "doBind | mConn = " + this.c);
        Intent intent = new Intent("com.meizu.voiceassistant.support.IVoiceAssistantService");
        intent.setPackage("com.meizu.voiceassistant");
        this.e.bindService(intent, this.c, 1);
    }

    public void c() {
        this.d.show();
        b();
    }

    public void d() {
        if (this.c != null) {
            if (this.b != null) {
                try {
                    this.b.a(this.f);
                    this.b.a(this.e.getPackageName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.e.unbindService(this.c);
            this.c = null;
            this.b = null;
            this.g = null;
        }
    }

    public void e() {
        Log.d(a, "doDisplay | mService = " + this.b);
        if (this.b != null) {
            try {
                if (!this.b.a()) {
                    this.b.a(this.e.getPackageName(), this.f);
                    com.meizu.cloud.statistics.b.a().a("Search_voice", "", null);
                    return;
                }
                return;
            } catch (Exception ex) {
                Log.d(a, "doDisplay | ex = " + ex);
                return;
            }
        }
        Log.w(a, "mService = null, rebind");
        c();
    }

    public void f() {
        Log.d(a, "doHide | mService = " + this.b);
        if (this.b != null) {
            try {
                if (this.b.a()) {
                    this.b.a(this.f);
                }
            } catch (Exception e) {
                Log.d(a, "doHide | exception = " + e);
            }
        }
    }
}
