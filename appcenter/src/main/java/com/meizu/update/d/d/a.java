package com.meizu.update.d.d;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.meizu.statsapp.UsageStatsProxy;
import com.meizu.update.h.b;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class a {
    final String a = UUID.randomUUID().toString();
    private UsageStatsProxy b;
    private Context c;

    public a(Context context) {
        this.b = UsageStatsProxy.a(context, true);
        this.c = context;
    }

    private void a(Map<String, String> actionProperties) {
        try {
            actionProperties.put("uuid", this.a);
            actionProperties.put("clientip", b.a());
            String requestServerUrl = null;
            if (actionProperties.containsKey("redirect_url")) {
                requestServerUrl = (String) actionProperties.get("redirect_url");
            } else if (actionProperties.containsKey("requrl")) {
                requestServerUrl = (String) actionProperties.get("requrl");
            }
            if (!TextUtils.isEmpty(requestServerUrl)) {
                String host = Uri.parse(requestServerUrl).getHost();
                if (!TextUtils.isEmpty(host)) {
                    actionProperties.put("serverip", b.a(host));
                }
            }
            actionProperties.put("product", this.c.getPackageName());
            actionProperties.put("up_sdk_version", "2.0.0");
            b.a("Write usage log:");
            for (String key : actionProperties.keySet()) {
                b.a(key + "=" + ((String) actionProperties.get(key)));
            }
            this.b.a("dns.download.app", (Map) actionProperties);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    private void a(String fileTag, int resultMark, String requestUrl, int responseCode, String redirectUrl, String msg, String version) {
        final int i = resultMark;
        final String str = fileTag;
        final String str2 = requestUrl;
        final int i2 = responseCode;
        final String str3 = redirectUrl;
        final String str4 = msg;
        final String str5 = version;
        new AsyncTask<Void, Void, Void>(this) {
            final /* synthetic */ a h;

            protected /* synthetic */ Object doInBackground(Object[] x0) {
                return a((Void[]) x0);
            }

            protected Void a(Void... params) {
                Map<String, String> actionProperties = new HashMap();
                actionProperties.put("result_mark", String.valueOf(i));
                if (str != null) {
                    actionProperties.put("package_name", str);
                }
                if (str2 != null) {
                    actionProperties.put("requrl", str2);
                }
                actionProperties.put("rescode", String.valueOf(i2));
                if (!(str3 == null || str3.equalsIgnoreCase(str2))) {
                    actionProperties.put("redirect_url", str3);
                }
                if (str4 != null) {
                    actionProperties.put("msg", str4);
                }
                if (str5 != null) {
                    actionProperties.put("version_log", str5);
                }
                this.h.a(actionProperties);
                return null;
            }
        }.execute(new Void[0]);
    }

    public void a(String fileTag, String requestUrl, int responseCode, String redirectUrl, String msg, String version) {
        a(fileTag, 3, requestUrl, responseCode, redirectUrl, msg, version);
    }

    public void b(String fileTag, String requestUrl, int responseCode, String redirectUrl, String msg, String version) {
        a(fileTag, 2, requestUrl, responseCode, redirectUrl, msg, version);
    }

    public void a(String fileTag, String requestUrl, String redirectUrl, String msg, String version) {
        a(fileTag, 1, requestUrl, 200, redirectUrl, msg, version);
    }

    public void b(String fileTag, String requestUrl, String redirectUrl, String msg, String version) {
        a(fileTag, 4, requestUrl, 100002, redirectUrl, msg, version);
    }
}
