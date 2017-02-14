package com.meizu.cloud.app.core;

import android.content.Context;
import com.meizu.cloud.app.downlad.a;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;

public class b {
    public static void a(Context context, AppUpdateStructItem structItem) {
        if (structItem != null) {
            for (ServerUpdateAppInfo appInfo : r.a(context).b(context)) {
                if (appInfo != null && appInfo.package_name != null && appInfo.package_name.equals(structItem.package_name)) {
                    if (appInfo.existDeltaUpdate()) {
                        structItem.patchSize = appInfo.version_patch_size;
                    }
                    structItem.isDownload = a.b(structItem.package_name, structItem.version_code);
                    return;
                }
            }
        }
    }
}
