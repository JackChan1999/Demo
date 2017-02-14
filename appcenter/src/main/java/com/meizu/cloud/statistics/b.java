package com.meizu.cloud.statistics;

import android.content.Context;
import android.util.Log;
import com.meizu.cloud.app.utils.j;
import com.meizu.cloud.base.app.BaseApplication;
import com.meizu.statsapp.UsageStatsProxy;
import java.util.HashMap;
import java.util.Map;

public class b {
    private static b a;
    private Context b = BaseApplication.a();
    private UsageStatsProxy c;

    private b() {
        b();
    }

    public static synchronized b a() {
        b bVar;
        synchronized (b.class) {
            if (a == null) {
                a = new b();
            }
            bVar = a;
        }
        return bVar;
    }

    public void b() {
        c();
    }

    protected void c() {
        this.c = UsageStatsProxy.a(this.b, true);
    }

    public void a(String uxipPage) {
        this.c.a(uxipPage);
        Log.i("statsdk", "UXIP 2.0 pageStart: " + uxipPage);
    }

    public void a(String uxipPage, Map<String, String> mParams) {
        if (mParams != null && mParams.size() > 0) {
            Map<String, String> dataMap = new HashMap();
            dataMap.putAll(mParams);
            this.c.a("update_page_params", uxipPage, dataMap);
        }
        this.c.b(uxipPage);
        Log.i("statsdk", "uxip 2.0 onPageStop:" + uxipPage);
    }

    public void a(String actionName, String page, Map<String, String> actionProperties) {
        Map<String, String> dataMap = null;
        if (actionProperties != null && actionProperties.size() > 0) {
            dataMap = new HashMap();
            dataMap.putAll(actionProperties);
        }
        this.c.a(actionName, page, dataMap);
        j.a("statsdk", "onUXIP 2.0 Event: " + actionName);
    }

    public void b(String actionName, String page, Map<String, String> actionProperties) {
        a(actionName, page, actionProperties);
    }
}
