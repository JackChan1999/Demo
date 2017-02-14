package com.meizu.cloud.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.meizu.cloud.app.core.f;
import com.meizu.cloud.app.core.m;
import com.meizu.cloud.app.core.q;
import com.meizu.cloud.app.core.r;
import com.meizu.cloud.app.core.s;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.g;
import com.meizu.cloud.app.request.model.GameEntryInfo;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo;
import com.meizu.cloud.app.settings.a;
import com.meizu.cloud.app.utils.k;
import java.util.List;

public class UpdateCheckReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        k.b(context, "UpdateCheckReceiver", "onReceive:" + intent.getAction());
        if ("com.meizu.cloud.center.check.update".equals(intent.getAction())) {
            q.a(context).a(new s().a(true).b(a.a(context).a()));
        } else if ("com.meizu.cloud.center.execute.update".equals(intent.getAction())) {
            if (intent.getBooleanExtra("free_flow", false)) {
                List<ServerUpdateAppInfo<GameEntryInfo>> updateAppInfos = r.a(context).c(context);
                for (int i = 0; i < updateAppInfos.size(); i++) {
                    ServerUpdateAppInfo updateAppInfo = (ServerUpdateAppInfo) updateAppInfos.get(i);
                    if (com.meizu.cloud.app.downlad.a.b(updateAppInfo.package_name, updateAppInfo.version_code)) {
                        if (updateAppInfo.getAppStructItem() != null) {
                            updateAppInfo.getAppStructItem().page_info = new int[]{0, 21, 0};
                        }
                        d.a(context.getApplicationContext()).c(updateAppInfo.getAppStructItem(), new g(8, 1));
                    }
                }
                f.a(context).a(5000);
                return;
            }
            q.a(context).a(new s().a(true).b(a.a(context).a()).c(intent.getBooleanExtra("initiative", false)));
        } else if ("com.meizu.cloud.center.ignore.update".equals(intent.getAction())) {
            String jsonIgnore = intent.getStringExtra("package_version_data");
            if (!TextUtils.isEmpty(jsonIgnore)) {
                m.a.a(context, JSON.parseArray(jsonIgnore), "ignore_notify_apps");
            }
        }
    }
}
