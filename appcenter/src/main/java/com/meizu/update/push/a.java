package com.meizu.update.push;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.meizu.statsapp.UsageStatsProxy;
import com.meizu.update.d.d.b;
import com.meizu.update.h.g;
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

    private void a(Map<String, String> actionProperties, String requestUrl) {
        try {
            actionProperties.put("uuid", this.a);
            actionProperties.put("clientip", b.a());
            if (!TextUtils.isEmpty(requestUrl)) {
                String host = Uri.parse(requestUrl).getHost();
                if (!TextUtils.isEmpty(host)) {
                    actionProperties.put("serverip", b.a(host));
                }
            }
            actionProperties.put("product", this.c.getPackageName());
            com.meizu.update.h.b.a("Write push usage log:");
            for (String key : actionProperties.keySet()) {
                com.meizu.update.h.b.a(key + "=" + ((String) actionProperties.get(key)));
            }
            this.b.a("update.push.system.app", (Map) actionProperties);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    private void a(final int resultMark, final int responseCode, final String msg) {
        new AsyncTask<Void, Void, Void>(this) {
            final /* synthetic */ a d;

            protected /* synthetic */ Object doInBackground(Object[] x0) {
                return a((Void[]) x0);
            }

            protected Void a(Void... params) {
                Map actionProperties = new HashMap();
                actionProperties.put("result_mark", String.valueOf(resultMark));
                actionProperties.put("rescode", String.valueOf(responseCode));
                if (msg != null) {
                    actionProperties.put("msg", msg);
                }
                String model = g.d(this.d.c);
                String androidVersion = g.c(this.d.c);
                String flymeVersion = g.b(this.d.c);
                String appVersion = g.a(this.d.c);
                if (model != null) {
                    actionProperties.put("local_model", model);
                }
                if (androidVersion != null) {
                    actionProperties.put("android_version", androidVersion);
                }
                if (flymeVersion != null) {
                    actionProperties.put("flyme_version", flymeVersion);
                }
                if (appVersion != null) {
                    actionProperties.put("app_version", appVersion);
                }
                this.d.a(actionProperties, "http://u.meizu.com/appupgrade/check");
                return null;
            }
        }.execute(new Void[0]);
    }

    public void a(String msg) {
        a(1, 200, msg);
    }

    public void b(String msg) {
        a(2, 200, msg);
    }

    public void c(String msg) {
        a(3, 200, msg);
    }

    public void a(int responseCode, String msg) {
        a(4, responseCode, msg);
    }
}
