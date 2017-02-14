package com.meizu.update.b;

import android.content.Context;
import com.meizu.update.UpdateInfo;
import com.meizu.update.c;
import com.meizu.update.h.b;
import com.meizu.update.h.g;

public class a {
    private Context a;
    private com.meizu.update.c.a b;
    private long c;

    protected a(Context context, com.meizu.update.c.a listener, long checkInterval) {
        if (context == null || listener == null) {
            throw new IllegalArgumentException("listener and context cant be null");
        }
        this.b = listener;
        this.a = context;
        this.c = checkInterval;
    }

    protected UpdateInfo a(boolean manualCheck) {
        com.meizu.update.a.a.b(this.a);
        com.meizu.update.service.a.a(this.a);
        boolean hasNetwork = g.i(this.a);
        if (g.d() || !b.a(this.a, this.c)) {
            b.d("check interval interrupt");
            return UpdateInfo.generateNoUpdateInfo();
        } else if (hasNetwork) {
            b.a(this.a, "start check update for :" + this.a.getPackageName());
            UpdateInfo info = c.a(this.a);
            if (info != null) {
                int status;
                b.a(this.a, "check update result :" + info.mExistsUpdate + "," + info.mNeedUpdate + "," + info.mVersionName);
                if (!info.mExistsUpdate) {
                    b.b(this.a);
                    status = 3;
                    com.meizu.update.a.a.a(this.a);
                } else if (info.mNeedUpdate) {
                    status = 1;
                } else {
                    status = 2;
                }
                b.a(this.a, status);
                if (!info.mExistsUpdate || info.mNeedUpdate || !com.meizu.update.push.b.c(this.a, info.mVersionName)) {
                    return info;
                }
                if (manualCheck) {
                    b.c("manual check while skip version: " + info.mVersionName);
                    return info;
                }
                b.c("skip version: " + info.mVersionName);
                info.mExistsUpdate = false;
                b.b(this.a);
                return info;
            }
            b.a(this.a, "check update return null");
            return info;
        } else {
            b.c("request check no network : " + this.a.getPackageName());
            return null;
        }
    }

    protected void a() {
        this.b.a(2, null);
    }

    protected void a(UpdateInfo info) {
        this.b.a(0, info);
    }
}
