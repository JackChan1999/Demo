package com.meizu.cloud.push;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.base.app.BaseApplication;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.update.c.c;
import com.meizu.update.h.b;

public class AppPushReceiver extends MzPushMessageReceiver {
    public void onMessage(Context context, String data) {
        BaseApplication.a(context);
        if (!c.a(context, data) && a.a(context, data)) {
        }
    }

    public void onRegister(Context context, String pushId) {
        b.c("onRegister : " + pushId);
        if (!TextUtils.isEmpty(pushId)) {
            com.meizu.update.b.a(context);
            a.b(context);
        }
    }

    public void onUnRegister(Context context, boolean success) {
        b.d("onUnRegister");
    }

    public void onUpdateNotificationBuilder(PushNotificationBuilder pushNotificationBuilder) {
        super.onUpdateNotificationBuilder(pushNotificationBuilder);
        pushNotificationBuilder.setmStatusbarIcon(e.mz_stat_sys_appcenter);
    }
}
