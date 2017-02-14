package com.meizu.update.push;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.update.c.c;
import com.meizu.update.h.b;

public class PushReceiver extends MzPushMessageReceiver {
    public void onMessage(Context context, String data) {
        if (!c.a(context, data)) {
        }
    }

    public void onMessage(Context context, Intent intent) {
        if (!c.a(context, intent)) {
        }
    }

    public void onRegister(Context context, String pushid) {
        b.c("onRegister : " + pushid);
        if (!TextUtils.isEmpty(pushid)) {
            com.meizu.update.b.a(context);
        }
    }

    public void onUnRegister(Context context, boolean b) {
        b.d("onUnRegister");
    }
}
