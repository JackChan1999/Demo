package com.meizu.cloud.pushsdk.util;

import android.content.Context;
import android.util.Log;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.statsapp.UsageStatsProxy;
import java.util.HashMap;
import java.util.Map;

public class UxIPUtils {
    private static final String TAG = "UxIPUtils";

    public static void init(Context context) {
        UsageStatsProxy.a(context, true);
    }

    public static void onClickPushMessageEvent(Context context, String str, String str2, String str3) {
        Log.i(TAG, "onClickPushMessageLog packageName " + str + " deviceId= " + str2);
        Map hashMap = new HashMap();
        hashMap.put(PushConstants.TASK_ID, str3);
        hashMap.put(RequestManager.IMEI, str2);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("package_name", str);
        UsageStatsProxy.a(context, true).a("click_push_message", hashMap);
    }

    public static void onReceivePushMessageEvent(Context context, String str, String str2, String str3) {
        Log.i(TAG, "onReceivePushMessageLog packageName " + str + " deviceId= " + str2);
        Map hashMap = new HashMap();
        hashMap.put(PushConstants.TASK_ID, str3);
        hashMap.put(RequestManager.IMEI, str2);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("package_name", str);
        UsageStatsProxy.a(context, true).a("receive_push_event", hashMap);
    }
}
