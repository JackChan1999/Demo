package com.meizu.update.g;

import android.content.Context;
import com.meizu.statsapp.UsageStatsProxy;
import java.util.HashMap;
import java.util.Map;

public class a {
    private static a a;
    private UsageStatsProxy b;
    private Context c;
    private final String d;

    public enum a {
        PushMessageReceived("PushMessageReceived"),
        UpdateDisplay_Alert("UpdateDisplay_Alert"),
        UpdateDisplay_Notification("UpdateDisplay_Notification"),
        UpdateAlert_Yes("UpdateAlert_Yes"),
        UpdateAlert_Ignore("UpdateAlert_Ignore"),
        UpdateAlert_No("UpdateAlert_No"),
        Download_Del("Download_Del"),
        Download_Failure("Download_Failure"),
        Download_Done("Download_Done"),
        Install_Yes("Install_Yes"),
        Install_No("Install_No"),
        Install_Complete("Install_Complete"),
        Install_Failure("Install_Failure");
        
        private String n;

        private a(String name) {
            this.n = name;
        }

        public String a() {
            return this.n;
        }
    }

    public a(Context context) {
        this.c = context.getApplicationContext();
        this.d = context.getPackageName();
        this.b = UsageStatsProxy.a(context, true);
    }

    public static final synchronized a a(Context context) {
        a aVar;
        synchronized (a.class) {
            if (a == null) {
                a = new a(context);
            }
            aVar = a;
        }
        return aVar;
    }

    public void a(a action, String newVersion) {
        a(action, newVersion, null);
    }

    public void a(a action, String newVersion, String oldVersion) {
        a(action, newVersion, oldVersion, false);
    }

    public void a(a action, String newVersion, String oldVersion, boolean isManual) {
        Map actionProperties = new HashMap();
        actionProperties.put("update_action", action.a());
        actionProperties.put("package_name", this.d);
        if (newVersion != null) {
            actionProperties.put("new_version", newVersion);
        }
        if (oldVersion != null) {
            actionProperties.put("old_version", oldVersion);
        }
        if (isManual) {
            actionProperties.put("update_manual", "manual");
        }
        actionProperties.put("up_sdk_version", "2.0.0");
        this.b.a("update.component.app", actionProperties);
    }
}
