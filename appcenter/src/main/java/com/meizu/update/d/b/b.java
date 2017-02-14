package com.meizu.update.d.b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import com.meizu.update.h.g;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b {
    private a[] a;
    private a[] b;
    private final long c;
    private final long d;
    private final b e;

    private static class a {
        public final String a;
        public final String b;

        public a(String ip, String authKey) {
            this.a = ip;
            this.b = authKey;
        }
    }

    private static class b {
        private final int a;
        private final String b;

        private b(int type, String key) {
            this.a = type;
            this.b = key;
        }

        public boolean a(Context context) {
            b current = b(context);
            if (current.a != -1) {
                return equals(current);
            }
            com.meizu.update.h.b.c("Check network match while no network");
            return true;
        }

        public boolean equals(Object o) {
            boolean keyMatch = true;
            if (this == o) {
                return true;
            }
            if (o == null || !(o instanceof b)) {
                com.meizu.update.h.b.c("Check network match while object is illegal:" + o);
            } else {
                b pnt = (b) o;
                if (pnt.a == this.a) {
                    if (pnt.b != null) {
                        keyMatch = pnt.b.equals(this.b);
                    } else if (this.b != null) {
                        keyMatch = false;
                    }
                    if (keyMatch) {
                        return keyMatch;
                    }
                    com.meizu.update.h.b.c("Network key change:" + this.b + "->" + pnt.b);
                    return keyMatch;
                }
                com.meizu.update.h.b.c("Network type change:" + this.a + "->" + pnt.a);
            }
            return false;
        }

        public static b b(Context context) {
            try {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
                if (cm != null) {
                    NetworkInfo info = cm.getActiveNetworkInfo();
                    if (info != null && info.isAvailable()) {
                        int type = info.getType();
                        String key = null;
                        if (type == 1) {
                            WifiInfo wifiInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
                            if (wifiInfo != null) {
                                key = wifiInfo.getSSID();
                            }
                        } else if (type == 0) {
                            key = g.k(context);
                        }
                        if (TextUtils.isEmpty(key)) {
                            key = info.getTypeName();
                        }
                        com.meizu.update.h.b.c("Current network type:" + type + "," + key);
                        return new b(type, key);
                    }
                }
            } catch (Exception ignore) {
                com.meizu.update.h.b.c("InstanceCurrent exception!");
                ignore.printStackTrace();
            }
            com.meizu.update.h.b.c("InstanceCurrent no network!");
            return new b(-1, null);
        }
    }

    protected b(String res, Context context) throws JSONException {
        JSONArray array;
        int size;
        int i;
        JSONObject jsonIp;
        String authKey;
        JSONObject object = new JSONObject(res);
        if (object.has("targets")) {
            array = object.getJSONArray("targets");
            size = array.length();
            if (size > 0) {
                this.a = new a[size];
                for (i = 0; i < size; i++) {
                    jsonIp = array.getJSONObject(i);
                    String ip = jsonIp.getString("ip");
                    if (jsonIp.has("authKey")) {
                        authKey = jsonIp.getString("authKey");
                    } else {
                        authKey = null;
                    }
                    this.a[i] = new a(ip, authKey);
                }
            }
        }
        if (object.has("baks")) {
            array = object.getJSONArray("baks");
            size = array.length();
            if (size > 0) {
                this.b = new a[size];
                for (i = 0; i < size; i++) {
                    jsonIp = array.getJSONObject(i);
                    ip = jsonIp.getString("ip");
                    if (jsonIp.has("authKey")) {
                        authKey = jsonIp.getString("authKey");
                    } else {
                        authKey = null;
                    }
                    this.b[i] = new a(ip, authKey);
                }
            }
        }
        if (object.has("expire")) {
            this.c = object.getLong("expire");
        } else {
            this.c = 5;
        }
        this.d = SystemClock.elapsedRealtime();
        this.e = b.b(context);
    }

    public boolean a(Context context) {
        boolean expire;
        if (SystemClock.elapsedRealtime() - this.d > this.c * 60000) {
            expire = true;
        } else {
            expire = false;
        }
        if (expire) {
            com.meizu.update.h.b.c("Proxy info time expire!");
            return expire;
        } else if (this.e.a(context)) {
            return false;
        } else {
            return true;
        }
    }

    public c a(String url) {
        try {
            String host = Uri.parse(url).getAuthority();
            if (!TextUtils.isEmpty(host)) {
                a targetInfo = null;
                if (this.a != null && this.a.length > 0) {
                    targetInfo = this.a[0];
                } else if (this.b != null && this.b.length > 0) {
                    targetInfo = this.b[0];
                }
                if (targetInfo != null) {
                    String targetHost = targetInfo.a;
                    String targetAuthKey = targetInfo.b;
                    String targetUrl = targetHost;
                    int hostIndex = url.indexOf(host);
                    if (hostIndex != -1) {
                        targetUrl = targetUrl + url.substring(host.length() + hostIndex);
                        List<Pair<String, String>> headers = new ArrayList(2);
                        headers.add(new Pair("Mz_Host", host));
                        if (!TextUtils.isEmpty(targetAuthKey)) {
                            headers.add(new Pair("Authorization", "Basic " + Base64.encodeToString(targetAuthKey.getBytes(), 2)));
                        }
                        return new c(targetUrl, headers);
                    }
                    com.meizu.update.h.b.d("cant re construct url:" + url);
                }
            }
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        return null;
    }
}
