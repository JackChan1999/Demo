package com.meizu.gslb.b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.meizu.gslb.g.a;
import com.meizu.gslb.g.f;

public class h {
    private static h c;
    public final int a;
    public final String b;

    private h(int i, String str) {
        this.a = i;
        this.b = str;
    }

    public static h b(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                    CharSequence ssid;
                    String typeName;
                    int type = activeNetworkInfo.getType();
                    if (type == 1) {
                        WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
                        if (connectionInfo != null) {
                            ssid = connectionInfo.getSSID();
                        }
                        ssid = null;
                    } else {
                        if (type == 0) {
                            ssid = f.a(context);
                        }
                        ssid = null;
                    }
                    if (TextUtils.isEmpty(ssid)) {
                        typeName = activeNetworkInfo.getTypeName();
                    } else {
                        CharSequence charSequence = ssid;
                    }
                    h hVar = new h(type, typeName);
                    if (c != null && c.equals(hVar)) {
                        return hVar;
                    }
                    c = hVar;
                    a.a("Current network type:" + type + "," + typeName);
                    return hVar;
                }
            }
        } catch (Exception e) {
            a.c("InstanceCurrent exception!");
            e.printStackTrace();
        }
        a.c("InstanceCurrent no network!");
        return new h(-1, null);
    }

    public boolean a(Context context) {
        return equals(b(context));
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof h)) {
            a.c("Check network match while object is illegal:" + obj);
        } else {
            h hVar = (h) obj;
            if (hVar.a == this.a) {
                if (hVar.b != null) {
                    z = hVar.b.equals(this.b);
                } else if (this.b != null) {
                    z = false;
                }
                if (z) {
                    return z;
                }
                a.c("Network key change:" + this.b + "->" + hVar.b);
                return z;
            }
            a.c("Network type change:" + this.a + "->" + hVar.a);
        }
        return false;
    }
}
