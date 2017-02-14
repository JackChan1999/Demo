package com.meizu.cloud.pushsdk;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import cn.com.xy.sms.sdk.constant.Constant;
import java.lang.reflect.Method;
import java.util.List;

public class NotificationService extends Service {
    private static final int STOP_SERVICE = 10001;
    private static final String TAG = "NotificationService";
    Handler handler;

    public void onCreate() {
        Log.i(TAG, "NotificationService create");
        super.onCreate();
        this.handler = new Handler(getMainLooper()) {
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 10001:
                        Log.i(NotificationService.TAG, "stop notification service");
                        NotificationService.this.stopSelf();
                        return;
                    default:
                        return;
                }
            }
        };
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        Object stringExtra = intent.getStringExtra("command_type");
        Log.d(TAG, "-- command_type -- " + stringExtra);
        if (!TextUtils.isEmpty(stringExtra) && stringExtra.equals("reflect_receiver")) {
            reflectReceiver(intent);
            stopService();
        }
        return 2;
    }

    private void stopService() {
        this.handler.removeMessages(10001);
        this.handler.sendEmptyMessageDelayed(10001, Constant.MINUTE);
    }

    public void onDestroy() {
        Log.i(TAG, "NotificationService destroy");
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind initSuc: ");
        return null;
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public String getReceiver(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Intent intent = new Intent(str2);
        intent.setPackage(str);
        List queryBroadcastReceivers = getPackageManager().queryBroadcastReceivers(intent, 0);
        if (queryBroadcastReceivers == null || queryBroadcastReceivers.size() <= 0) {
            return null;
        }
        return ((ResolveInfo) queryBroadcastReceivers.get(0)).activityInfo.name;
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
