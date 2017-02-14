package com.meizu.cloud.pushsdk;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.Method;
import java.util.List;

public class NotificationService extends IntentService {
    private static final String TAG = "NotificationService";

    public NotificationService() {
        super(TAG);
    }

    public NotificationService(String str) {
        super(str);
    }

    public String getReceiver(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Intent intent = new Intent(str2);
        intent.setPackage(str);
        List queryBroadcastReceivers = getPackageManager().queryBroadcastReceivers(intent, 0);
        return (queryBroadcastReceivers == null || queryBroadcastReceivers.size() <= 0) ? null : ((ResolveInfo) queryBroadcastReceivers.get(0)).activityInfo.name;
    }

    public void onDestroy() {
        Log.i(TAG, "NotificationService destroy");
        super.onDestroy();
    }

    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.i(TAG, "onHandleIntentaction " + intent.getAction());
            Object stringExtra = intent.getStringExtra("command_type");
            Log.d(TAG, "-- command_type -- " + stringExtra);
            if (!TextUtils.isEmpty(stringExtra) && stringExtra.equals("reflect_receiver")) {
                reflectReceiver(intent);
            }
        }
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void reflectReceiver(Intent intent) {
        String receiver = getReceiver(getPackageName(), intent.getAction());
        if (TextUtils.isEmpty(receiver)) {
            Log.i(TAG, " reflectReceiver error: receiver for: " + intent.getAction() + " not found, package: " + getPackageName());
            intent.setPackage(getPackageName());
            sendBroadcast(intent);
            return;
        }
        try {
            Class cls = Class.forName(receiver);
            Object newInstance = cls.getConstructor((Class[]) null).newInstance((Object[]) null);
            Method method = cls.getMethod("onReceive", new Class[]{Context.class, Intent.class});
            intent.setClassName(getPackageName(), receiver);
            method.invoke(newInstance, new Object[]{getApplicationContext(), intent});
        } catch (Exception e) {
            Log.i(TAG, "reflect e: " + e);
        }
    }
}
