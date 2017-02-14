package com.meizu.statsapp.a;

import android.content.Context;
import com.meizu.gslb.f.d;
import com.meizu.statsapp.UsageStatsProxy;
import java.util.Map;

public class a implements d {
    private UsageStatsProxy a;

    public void a(Context context) {
        this.a = UsageStatsProxy.a(context, true);
    }

    public void a(String logName, Map<String, String> properties) {
        this.a.a(logName, (Map) properties);
    }
}
