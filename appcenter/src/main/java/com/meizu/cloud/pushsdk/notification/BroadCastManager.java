package com.meizu.cloud.pushsdk.notification;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import java.util.List;

public class BroadCastManager {
    public static String findReceiver(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Intent intent = new Intent(str);
        intent.setPackage(str2);
        List queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
        return (queryBroadcastReceivers == null || queryBroadcastReceivers.size() <= 0) ? null : ((ResolveInfo) queryBroadcastReceivers.get(0)).activityInfo.name;
    }
}
