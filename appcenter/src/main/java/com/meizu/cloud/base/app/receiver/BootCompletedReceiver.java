package com.meizu.cloud.base.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.meizu.cloud.app.core.f;

public class BootCompletedReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (!"android.intent.action.BOOT_COMPLETED".equals(intent.getAction()) && "com.meizu.setup.DOWNLOAD_NOTIFY".equals(intent.getAction())) {
            f.a(context).b();
            Log.i("BootCompletedReceiver", "success receive action!");
        }
    }
}
