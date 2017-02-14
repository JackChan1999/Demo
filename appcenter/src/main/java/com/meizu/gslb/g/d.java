package com.meizu.gslb.g;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

public class d {
    public static String a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }
}
