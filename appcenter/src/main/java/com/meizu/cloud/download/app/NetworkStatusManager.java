package com.meizu.cloud.download.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NetworkStatusManager extends BroadcastReceiver implements OnSharedPreferenceChangeListener {
    private static NetworkStatusManager a;
    private static final int g;
    private Context b;
    private String c;
    private String d;
    private int e = -1;
    private boolean f = false;
    private List<a> h;

    public interface a {
        void a(int i);
    }

    static {
        int typePPPOE;
        try {
            typePPPOE = ConnectivityManager.class.getField("TYPE_PPPOE").getInt(null);
        } catch (Exception e) {
            typePPPOE = 14;
        }
        g = typePPPOE;
    }

    public static void a(Context context, String prefrence, String prefrenceKey) {
        if (a == null) {
            a = new NetworkStatusManager(context, prefrence, prefrenceKey);
        }
    }

    public static NetworkStatusManager a() {
        return a;
    }

    private NetworkStatusManager(Context context, String prefrence, String prefrenceKey) {
        this.b = context;
        this.b.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        if (!(prefrence == null || prefrenceKey == null)) {
            this.c = prefrence;
            this.d = prefrenceKey;
            SharedPreferences sp = this.b.getSharedPreferences(prefrence, 0);
            sp.registerOnSharedPreferenceChangeListener(this);
            this.f = sp.getBoolean(this.d, false);
        }
        this.h = new ArrayList();
    }

    public void onReceive(Context context, Intent intent) {
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            a(this.b);
            b();
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (this.d != null && this.d.equals(key)) {
            boolean wifiOnly = sharedPreferences.getBoolean(this.d, false);
            if (wifiOnly != this.f) {
                this.f = wifiOnly;
                b();
            }
        }
    }

    public void a(a listener) {
        if (listener != null) {
            synchronized (this.h) {
                if (!this.h.contains(listener)) {
                    this.h.add(listener);
                }
            }
        }
    }

    public void b(a listener) {
        synchronized (this.h) {
            this.h.remove(listener);
        }
    }

    public boolean a(boolean wifiOnly, boolean canUseGPRS) {
        boolean z = false;
        if (this.e < 0) {
            a(this.b);
        }
        if (!wifiOnly) {
            if ((canUseGPRS && (this.e == 3 || this.e == 2)) || this.e == 1) {
                z = true;
            }
            return z;
        } else if (this.e == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean a(boolean canUseGPRS) {
        return a(this.f, canUseGPRS);
    }

    private final void a(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info == null || !info.isAvailable()) {
                    this.e = 0;
                    return;
                }
                int type = info.getType();
                if (type == 1 || type == g) {
                    this.e = 1;
                    return;
                }
                type = ((TelephonyManager) context.getSystemService("phone")).getNetworkType();
                if (type == 1 || type == 2) {
                    this.e = 2;
                } else {
                    this.e = 3;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.e = 0;
        }
    }

    private void b() {
        ArrayList<a> listeners;
        synchronized (this.h) {
            listeners = new ArrayList(this.h);
        }
        Iterator i$ = listeners.iterator();
        while (i$.hasNext()) {
            ((a) i$.next()).a(this.e);
        }
    }
}
