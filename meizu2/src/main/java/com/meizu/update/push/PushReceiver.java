package com.meizu.update.push;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import defpackage.aox;
import defpackage.apj;
import defpackage.arx;

public class PushReceiver extends MzPushMessageReceiver {
    public void onMessage(Context context, String str) {
        if (!apj.a(context, str)) {
        }
    }

    public void onMessage(Context context, Intent intent) {
        if (!apj.a(context, intent)) {
        }
    }

    public void onRegister(Context context, String str) {
        arx.c("onRegister : " + str);
        if (!TextUtils.isEmpty(str)) {
            aox.a(context);
        }
    }

    public void onUnRegister(Context context, boolean z) {
        arx.d("onUnRegister");
    }
}
