package com.meizu.gslb.b;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import com.meizu.gslb.g.a;
import com.meizu.gslb.g.c;
import com.meizu.gslb.g.e;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b {
    private static final String[] a = new String[0];
    private final String b;
    private final List<f> c;
    private final Map<String, String> d;
    private final long e;
    private final long f;
    private final h g;

    public b(Context context, String str, String str2, h hVar, long j, String str3) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        int i = jSONObject.getInt("code");
        if (i != 200 && i != 110006) {
            a.c("Unknown response:" + str);
            throw new JSONException("Unknown response code:" + i);
        } else if (TextUtils.isEmpty(str3)) {
            a.d("No sign key!");
            throw new JSONException("No sign key!");
        } else if (e.a(str.trim(), str3.trim(), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFRh6o89BH2bel0G2Fq0BQXc8+QK9HA1M9fytmpjJKxb3MXpQuSdyWkZPfZJavVMURwY9yvc6WzdHO/5dnmh3zR9LVFaAV+R6i1dGWx4/nm2+qC67rP/cjNZ1oDVvdN4nivFtOdnH5cot7sS7laTz7h7t3dMUUrQ+/v+jQnBG1QwIDAQAB")) {
            if (i == 110006) {
                this.b = str2;
                this.c = new ArrayList();
                if ((!jSONObject.isNull("value") ? 1 : null) == null || !jSONObject.getJSONObject("value").has("expire")) {
                    this.e = 4320;
                } else {
                    this.e = jSONObject.getJSONObject("value").getLong("expire");
                }
                this.d = null;
            } else {
                jSONObject = jSONObject.getJSONObject("value");
                this.b = jSONObject.getString("name");
                if (str2.equalsIgnoreCase(this.b)) {
                    JSONArray jSONArray;
                    int length;
                    this.c = new ArrayList();
                    if (jSONObject.has("targets")) {
                        jSONArray = jSONObject.getJSONArray("targets");
                        length = jSONArray.length();
                        for (i = 0; i < length; i++) {
                            this.c.add(new f(jSONArray.getJSONObject(i).getString("ip")));
                        }
                    }
                    if (jSONObject.has("baks")) {
                        jSONArray = jSONObject.getJSONArray("baks");
                        length = jSONArray.length();
                        for (i = 0; i < length; i++) {
                            this.c.add(new f(jSONArray.getJSONObject(i).getString("ip")));
                        }
                    }
                    if (a.length > 0) {
                        this.d = new HashMap(a.length);
                        for (String str4 : a) {
                            if (jSONObject.has(str4)) {
                                this.d.put(str4, jSONObject.getString(str4));
                            }
                        }
                    } else {
                        this.d = null;
                    }
                    if (jSONObject.has("expire")) {
                        this.e = jSONObject.getLong("expire");
                    } else {
                        this.e = 5;
                    }
                } else {
                    throw new JSONException("Parse data domain not match:" + str2 + "->" + this.b);
                }
            }
            this.f = SystemClock.elapsedRealtime() - j;
            if (hVar != null) {
                this.g = hVar;
            } else {
                this.g = h.b(context);
            }
        } else {
            a.d("sign not match:" + str3);
            throw new JSONException("sign not match:" + str3);
        }
    }

    public b(Context context, String str, String str2, String str3) throws JSONException {
        this(context, str, str2, null, 0, str3);
    }

    public String a() {
        if (this.c.size() > 0) {
            for (f fVar : this.c) {
                if (fVar.a()) {
                    return fVar.b();
                }
            }
        }
        return null;
    }

    public synchronized void a(String str, i.a aVar) {
        if (!TextUtils.isEmpty(str)) {
            for (f fVar : this.c) {
                if (str.equals(fVar.b())) {
                    fVar.a(this.b, aVar);
                }
            }
        }
    }

    public boolean a(Context context) {
        boolean z = SystemClock.elapsedRealtime() - this.f > this.e * 60000;
        if (z) {
            a.c("Ips info time expire!");
        }
        if (!z && c.b(context)) {
            z = !this.g.a(context);
            if (z) {
                a.c("Ips info expire while network change!");
            }
        }
        return z;
    }

    public boolean a(String str) {
        return !TextUtils.isEmpty(str) && str.equalsIgnoreCase(this.b);
    }

    public Map<String, String> b() {
        return this.d;
    }

    public h c() {
        return this.g;
    }
}
