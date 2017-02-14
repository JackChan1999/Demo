package com.meizu.gslb.f;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.meizu.gslb.b.i;
import com.meizu.gslb.g.d;
import java.util.HashMap;
import java.util.Map;

public class b implements a {
    private static long a = -1;
    private Context b;
    private d c;
    private boolean d;
    private String e;
    private String f;
    private c g = new c();

    public enum a {
        EventResponseCodeError("response_err"),
        EventResponseException("response_exception"),
        EventResponseTimeout("response_timeout"),
        EventDataError("data_err"),
        EventResponseSuccess("response_success"),
        EventIpInvalid("gslb_ip_invalid");
        
        private String g;

        private a(String str) {
            this.g = str;
        }

        public String a() {
            return this.g;
        }
    }

    public b(Context context, d dVar) {
        this.b = context;
        this.c = dVar;
    }

    private String a(String str) {
        try {
            Object query = Uri.parse(str).getQuery();
            if (!TextUtils.isEmpty(query)) {
                str = str.subSequence(0, str.length() - query.length()).toString();
            }
        } catch (Exception e) {
        }
        return str;
    }

    private Map<String, String> a(String str, String str2, String str3, String str4) {
        Map<String, String> hashMap = new HashMap();
        if (!TextUtils.isEmpty(str2)) {
            hashMap.put("original_url", a(str2));
        } else if (!TextUtils.isEmpty(str3)) {
            hashMap.put("original_domain", str3);
        }
        if (!TextUtils.isEmpty(str4)) {
            hashMap.put("convert_ip", str4);
        }
        if (!TextUtils.isEmpty(str)) {
            hashMap.put("uuid", str);
        }
        return hashMap;
    }

    public static final void a(long j) {
        a = j;
    }

    private void a(a aVar, Map<String, String> map) {
        if (a()) {
            try {
                if (!this.d) {
                    synchronized (this.c) {
                        if (!this.d) {
                            this.d = true;
                            this.e = this.b.getPackageName();
                            this.f = d.a(this.b);
                            this.c.a(this.b);
                        }
                    }
                }
                map.put("gslb_event", aVar.a());
                map.put("app_v", this.f);
                map.put("package", this.e);
                this.c.a("gslb.component.app", map);
                return;
            } catch (Exception e) {
                return;
            }
        }
        com.meizu.gslb.g.a.c("IGslbUsageProxy not set!");
    }

    public static boolean b(long j) {
        return a > 0 ? j > a : j > 20000;
    }

    public void a(String str, String str2, String str3, long j) {
        if (b(j)) {
            com.meizu.gslb.g.a.c("request time out:" + j);
            Map a = a(str, str2, null, !TextUtils.isEmpty(str3) ? Uri.parse(str3).getHost() : null);
            a.put("request_time", String.valueOf(j));
            a(a.EventResponseTimeout, a);
        }
    }

    public void a(boolean z, String str, i iVar, int i) {
        if (z || !iVar.a()) {
            com.meizu.gslb.g.a.a("write response code usage:" + i);
            Map a = a(str, iVar.d(), iVar.b(), iVar.c());
            a.put("response_code", String.valueOf(i));
            a(iVar.a() ? a.EventResponseSuccess : a.EventResponseCodeError, a);
        }
    }

    public void a(boolean z, String str, i iVar, Exception exception) {
        if (this.g.a(iVar, exception)) {
            Object message = exception != null ? exception.getMessage() : null;
            com.meizu.gslb.g.a.a("write response exception usage:" + message);
            Map a = a(str, iVar.d(), iVar.b(), iVar.c());
            String str2 = "error_msg";
            if (message == null) {
                message = "";
            }
            a.put(str2, message);
            a(a.EventResponseException, a);
        }
    }

    public void a(boolean z, String str, String str2, String str3) {
        com.meizu.gslb.g.a.a("write ip invalid usage:" + str + "," + str2 + ",restore:" + z);
        Map hashMap = new HashMap();
        hashMap.put("original_domain", str);
        hashMap.put("convert_ip", str2);
        hashMap.put("ip_invalid_msg", str3);
        hashMap.put("ip_invalid_restore", String.valueOf(z));
        a(a.EventIpInvalid, hashMap);
    }

    public boolean a() {
        return this.c != null;
    }
}
