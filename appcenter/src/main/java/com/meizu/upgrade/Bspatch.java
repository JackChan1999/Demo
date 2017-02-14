package com.meizu.upgrade;

import android.util.Log;
import java.io.IOException;

public class Bspatch {
    private static native int applyPatch(String str, String str2, String str3);

    static {
        try {
            System.loadLibrary("bspatch");
        } catch (UnsatisfiedLinkError e) {
            Log.e("Bspatch", "Unsatisfied Link error: " + e.toString());
        }
    }

    public static int a(String oldApkPath, String newApkPath, String patchPath) throws IOException {
        return applyPatch(oldApkPath, newApkPath, patchPath);
    }
}
