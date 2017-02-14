package com.meizu.cloud.app.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;

public class o {
    public static final void a(Context context) throws ActivityNotFoundException {
        Intent intent = new Intent("com.meizu.intent.action.INSUFFICENT_SPACE_DIALOG");
        intent.setFlags(872415232);
        context.startActivity(intent);
    }
}
