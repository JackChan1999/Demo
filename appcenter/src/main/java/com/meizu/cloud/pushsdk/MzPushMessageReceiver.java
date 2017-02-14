package com.meizu.cloud.pushsdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.MPushMessage;
import com.meizu.cloud.pushsdk.notification.NotificationTools;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;
import com.meizu.cloud.pushsdk.util.UxIPUtils;

public abstract class MzPushMessageReceiver extends BroadcastReceiver {
    public static final String TAG = "MzPushMessageReceiver";
    public static String deviceId;

    private void parseMethodMessage(Context context, Intent intent, String str) {
        if ("message".equals(str)) {
            String stringExtra = intent.getStringExtra("message");
            String stringExtra2 = intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_TASK_ID);
            Log.i(TAG, " packageName " + context.getPackageName() + "receive through message " + stringExtra + " taskId " + stringExtra2);
            UxIPUtils.onReceivePushMessageEvent(context, context.getPackageName(), deviceId, stringExtra2);
            onMessage(context, stringExtra);
        } else if (PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE.equals(str)) {
            r0 = (MPushMessage) intent.getSerializableExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE);
            Log.i(TAG, " packageName " + context.getPackageName() + "push private message " + r0);
            PushPreferencesUtils.putDiscardNotificationIdByPackageName(context, r0.getPackageName(), 0);
            Intent buildIntent = NotificationTools.buildIntent(context, r0);
            if (buildIntent != null) {
                buildIntent.addFlags(268435456);
                context.startActivity(buildIntent);
                UxIPUtils.onClickPushMessageEvent(context, r0.getPackageName(), deviceId, r0.getTaskId());
            }
        } else if (PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_SHOW.equals(str)) {
            r0 = (MPushMessage) intent.getSerializableExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE);
            Log.i(TAG, " packageName " + context.getPackageName() + " push notification message " + r0);
            PushNotificationBuilder pushNotificationBuilder = new PushNotificationBuilder();
            onUpdateNotificationBuilder(pushNotificationBuilder);
            NotificationTools.showPrivateNotification(context, r0, pushNotificationBuilder);
            UxIPUtils.onReceivePushMessageEvent(context, r0.getPackageName(), deviceId, r0.getTaskId());
        } else if (PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_DELETE.equals(str)) {
            Log.i(TAG, " packageName " + context.getPackageName() + " delete notification message " + ((MPushMessage) intent.getSerializableExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE)));
        }
    }

    private void processRegisterCallback(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("registration_id");
        Log.i(TAG, "receive push action " + intent.getAction() + " pushId " + stringExtra);
        context.getSharedPreferences("com.meizu.flyme.push", 0).edit().putString("pushId", stringExtra).commit();
        onRegister(context, stringExtra);
    }

    private void processUnRegisterCallback(Context context, Intent intent) {
        boolean booleanExtra = intent.getBooleanExtra(PushConstants.EXTRA_APP_IS_UNREGISTER_SUCCESS, false);
        CharSequence stringExtra = intent.getStringExtra(PushConstants.EXTRA_REGISTRATION_ERROR);
        CharSequence stringExtra2 = intent.getStringExtra(PushConstants.EXTRA_UNREGISTERED);
        Log.i(TAG, "processUnRegisterCallback 5.0:" + booleanExtra + " 4.0:" + stringExtra + " 3.0:" + stringExtra2);
        if (TextUtils.isEmpty(stringExtra) || booleanExtra || !TextUtils.isEmpty(stringExtra2)) {
            context.getSharedPreferences("com.meizu.flyme.push", 0).edit().putString("pushId", "").commit();
            onUnRegister(context, true);
            return;
        }
        onUnRegister(context, false);
    }

    public void onMessage(Context context, Intent intent) {
    }

    public abstract void onMessage(Context context, String str);

    public final void onReceive(Context context, Intent intent) {
        Log.i(TAG, " receive pushaciton " + intent.getAction());
        if (PushConstants.MZ_PUSH_ON_REGISTER_ACTION.equals(intent.getAction())) {
            processRegisterCallback(context, intent);
        } else if (PushConstants.MZ_PUSH_ON_UNREGISTER_ACTION.equals(intent.getAction())) {
            processUnRegisterCallback(context, intent);
        } else if (PushConstants.REGISTRATION_CALLBACK_INTENT.equals(intent.getAction())) {
            if (TextUtils.isEmpty(intent.getStringExtra("registration_id"))) {
                processUnRegisterCallback(context, intent);
            } else {
                processRegisterCallback(context, intent);
            }
        } else if (PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction())) {
            Object stringExtra = intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD);
            String stringExtra2 = intent.getStringExtra("message");
            Log.i(TAG, "receive Push Message " + stringExtra2 + " method=" + stringExtra);
            if (TextUtils.isEmpty(stringExtra) && !TextUtils.isEmpty(stringExtra2)) {
                onMessage(context, stringExtra2);
            } else if (!TextUtils.isEmpty(stringExtra)) {
                if (!TextUtils.isEmpty(intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY))) {
                    deviceId = intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY);
                }
                Log.i(TAG, "current deviceId=" + deviceId);
                parseMethodMessage(context, intent, stringExtra);
            }
        } else if (PushConstants.C2DM_INTENT.equals(intent.getAction())) {
            Log.i(TAG, "flyme3 Message arrive");
            onMessage(context, intent);
        }
    }

    public abstract void onRegister(Context context, String str);

    public abstract void onUnRegister(Context context, boolean z);

    public void onUpdateNotificationBuilder(PushNotificationBuilder pushNotificationBuilder) {
    }
}
