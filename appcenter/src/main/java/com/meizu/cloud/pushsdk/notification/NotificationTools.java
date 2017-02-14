package com.meizu.cloud.pushsdk.notification;

import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Map.Entry;
import org.apache.commons.io.FileUtils;

public class NotificationTools {
    private static final String TAG = "NotificationTools";

    public static Intent buildIntent(Context context, MPushMessage mPushMessage) {
        if ("0".equals(mPushMessage.getClickType())) {
            Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(mPushMessage.getPackageName());
            if (mPushMessage.getParams() == null) {
                return launchIntentForPackage;
            }
            for (Entry entry : mPushMessage.getParams().entrySet()) {
                Log.i(TAG, " launcher activity key " + ((String) entry.getKey()) + " value " + ((String) entry.getValue()));
                if (!(TextUtils.isEmpty((CharSequence) entry.getKey()) || TextUtils.isEmpty((CharSequence) entry.getValue()))) {
                    launchIntentForPackage.putExtra((String) entry.getKey(), urlEncode((String) entry.getValue()));
                }
            }
            return launchIntentForPackage;
        } else if (PushConstants.CLICK_TYPE_ACTIVITY.equals(mPushMessage.getClickType())) {
            String str;
            Object obj = "";
            if (mPushMessage.getParams() != null) {
                for (Entry entry2 : mPushMessage.getParams().entrySet()) {
                    Log.i(TAG, " key " + ((String) entry2.getKey()) + " value " + ((String) entry2.getValue()));
                    str = (TextUtils.isEmpty((CharSequence) entry2.getKey()) || TextUtils.isEmpty((CharSequence) entry2.getValue())) ? r2 : r2 + "S." + ((String) entry2.getKey()) + "=" + urlEncode((String) entry2.getValue()) + ";";
                    Log.i(TAG, "paramValue " + str);
                    String str2 = str;
                }
            }
            str = "intent:#Intent;component=" + mPushMessage.getPackageName() + "/" + ((String) mPushMessage.getExtra().get(PushConstants.INTENT_ACTIVITY_NAME)) + (TextUtils.isEmpty(obj) ? ";" : ";" + obj) + "end";
            Log.i(TAG, "open activity intent uri " + str);
            try {
                return Intent.parseUri(str, 1);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return null;
            }
        } else if (!PushConstants.CLICK_TYPE_WEB.equals(mPushMessage.getClickType())) {
            return null;
        } else {
            return new Intent("android.intent.action.VIEW", Uri.parse((String) mPushMessage.getExtra().get("url")));
        }
    }

    public static Bitmap getAppIcon(Context context, MPushMessage mPushMessage) {
        try {
            return ((BitmapDrawable) context.getPackageManager().getApplicationIcon(mPushMessage.getPackageName())).getBitmap();
        } catch (NameNotFoundException e) {
            Log.i(TAG, "getappicon error " + e.getMessage());
            return ((BitmapDrawable) context.getApplicationInfo().loadIcon(context.getPackageManager())).getBitmap();
        }
    }

    public static Notification onCreateNotification(Context context, MPushMessage mPushMessage, PushNotificationBuilder pushNotificationBuilder) {
        CharSequence content = mPushMessage.getContent();
        CharSequence title = mPushMessage.getTitle();
        CharSequence content2 = mPushMessage.getContent();
        Builder builder = new Builder(context);
        if (pushNotificationBuilder == null || pushNotificationBuilder.getmLargIcon() == 0) {
            builder.setLargeIcon(getAppIcon(context, mPushMessage));
        } else {
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), pushNotificationBuilder.getmLargIcon()));
        }
        if (pushNotificationBuilder == null || pushNotificationBuilder.getmStatusbarIcon() == 0) {
            builder.setSmallIcon(context.getApplicationInfo().icon);
        } else {
            builder.setSmallIcon(pushNotificationBuilder.getmStatusbarIcon());
        }
        builder.setTicker(content);
        builder.setContentTitle(title);
        builder.setContentText(content2);
        builder.setAutoCancel(true);
        builder.setDefaults(1);
        if (MinSdkChecker.isSupportBigTextStyleAndAction()) {
            builder.setStyle(new BigTextStyle().bigText(content2));
        } else {
            builder.setContentText(content2);
        }
        return MinSdkChecker.isSupportNotificationBuild() ? builder.build() : builder.getNotification();
    }

    public static void showPrivateNotification(Context context, MPushMessage mPushMessage, PushNotificationBuilder pushNotificationBuilder) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        Intent intent = new Intent();
        intent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        intent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, mPushMessage);
        intent.putExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD, PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE);
        String findReceiver = BroadCastManager.findReceiver(context, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, context.getPackageName());
        Log.i(TAG, "current notify receiver name " + findReceiver);
        intent.setClassName(context.getPackageName(), findReceiver);
        intent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, FileUtils.ONE_GB);
        Intent intent2 = new Intent();
        intent2.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        intent2.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, mPushMessage);
        intent2.putExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD, PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_DELETE);
        intent2.setClassName(context.getPackageName(), findReceiver);
        intent2.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        PendingIntent broadcast2 = PendingIntent.getBroadcast(context, 0, intent2, FileUtils.ONE_GB);
        Notification onCreateNotification = onCreateNotification(context, mPushMessage, pushNotificationBuilder);
        onCreateNotification.contentIntent = broadcast;
        onCreateNotification.deleteIntent = broadcast2;
        int currentTimeMillis = (int) System.currentTimeMillis();
        if ("true".equals(mPushMessage.getIsDiscard())) {
            if (PushPreferencesUtils.getDiscardNotificationId(context, mPushMessage.getPackageName()) == 0) {
                PushPreferencesUtils.putDiscardNotificationIdByPackageName(context, mPushMessage.getPackageName(), currentTimeMillis);
            }
            if (!TextUtils.isEmpty(mPushMessage.getTaskId())) {
                if (PushPreferencesUtils.getDiscardNotificationTaskId(context, mPushMessage.getPackageName()) == 0) {
                    PushPreferencesUtils.putDiscardNotificationTaskId(context, mPushMessage.getPackageName(), Integer.valueOf(mPushMessage.getTaskId()).intValue());
                } else if (Integer.valueOf(mPushMessage.getTaskId()).intValue() < PushPreferencesUtils.getDiscardNotificationTaskId(context, mPushMessage.getPackageName())) {
                    Log.i(TAG, "current package " + mPushMessage.getPackageName() + " taskid " + mPushMessage.getTaskId() + " dont show notification");
                    return;
                } else {
                    PushPreferencesUtils.putDiscardNotificationTaskId(context, mPushMessage.getPackageName(), Integer.valueOf(mPushMessage.getTaskId()).intValue());
                    currentTimeMillis = PushPreferencesUtils.getDiscardNotificationId(context, mPushMessage.getPackageName());
                }
            }
            Log.i(TAG, "current package " + mPushMessage.getPackageName() + " notificationId=" + currentTimeMillis + " taskId=" + mPushMessage.getTaskId());
        }
        notificationManager.notify(currentTimeMillis, onCreateNotification);
    }

    private static String urlEncode(String str) {
        try {
            str = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.i(TAG, "encode url fail");
        }
        Log.i(TAG, "encode all value is " + str);
        return str;
    }
}
