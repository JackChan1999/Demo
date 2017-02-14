package com.meizu.update.a;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.update.h.g;
import java.io.File;

public class a {
    public static void a(Context context) {
        a(context, null);
    }

    public static void a(Context context, String excludeVersion) {
        String cachePath = c(context.getPackageName());
        String excludeTemp = null;
        String excludeApk = null;
        if (!TextUtils.isEmpty(excludeVersion)) {
            excludeTemp = a(excludeVersion);
            excludeApk = b(excludeVersion);
        }
        File file = new File(cachePath);
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    if (f.isFile() && ((excludeTemp == null || !f.getName().equals(excludeTemp)) && (excludeApk == null || !f.getName().equals(excludeApk)))) {
                        d("delete cache file : " + f.getName());
                        f.delete();
                    }
                }
                return;
            }
            return;
        }
        file.delete();
    }

    public static void b(Context context) {
        String packageName = context.getPackageName();
        String cachePath = c(packageName);
        File file = new File(cachePath + b(g.b(context, packageName)));
        if (file.exists()) {
            d("delete cur cache : " + file.getName());
            file.delete();
        }
    }

    public static final String b(Context context, String versionName) {
        return c(context.getPackageName()) + a(versionName);
    }

    public static final String c(Context context, String versionName) {
        return c(context.getPackageName()) + b(versionName);
    }

    private static final String a(String versionName) {
        return "update_cache_" + versionName + ".temp";
    }

    private static final String b(String versionName) {
        return "update_cache_" + versionName + ".apk";
    }

    private static final String c(String packageName) {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" + packageName + "/InstallCache/";
    }

    public static final boolean a(String srcPath, String destPath) {
        try {
            return new File(srcPath).renameTo(new File(destPath));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void d(String msg) {
        Log.d("FileCacheManager", msg);
    }
}
