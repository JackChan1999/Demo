package com.meizu.cloud.app.utils;

import android.content.Context;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import com.meizu.cloud.c.b.a.c;
import java.lang.reflect.InvocationTargetException;

public class p {
    private String a = "PackageManagerUtils";
    private PackageManager b = null;

    public p(Context context) {
        this.b = context.getPackageManager();
    }

    public boolean a(Uri packageURI, IPackageInstallObserver observer, int flags, String installerPackageName) {
        try {
            c.a().a(this.b.getClass(), "installPackage", Uri.class, IPackageInstallObserver.class, Integer.TYPE, String.class).invoke(this.b, new Object[]{packageURI, observer, Integer.valueOf(flags), installerPackageName});
            return true;
        } catch (SecurityException e) {
            Log.e(this.a, "No permission to invoke PackageManager.installPackage", e);
        } catch (NoSuchMethodException e2) {
            Log.e(this.a, "No such method: PackageManager.installPackage", e2);
        } catch (IllegalArgumentException e3) {
            Log.e(this.a, "Illegal argument to invoke PackageManager.installPackage", e3);
        } catch (IllegalAccessException e4) {
            Log.e(this.a, "Illegal access to invoke PackageManager.installPackage", e4);
        } catch (InvocationTargetException e5) {
            Log.e(this.a, "Failed to invoke PackageManager.installPackage", e5);
        }
        return false;
    }

    public boolean a(String packageName, IPackageDeleteObserver observer, int flags) {
        try {
            c.a().a(this.b.getClass(), "deletePackage", String.class, IPackageDeleteObserver.class, Integer.TYPE).invoke(this.b, new Object[]{packageName, observer, Integer.valueOf(flags)});
            return true;
        } catch (SecurityException e) {
            Log.e(this.a, "No permission to invoke PackageManager.deletePackage", e);
        } catch (NoSuchMethodException e2) {
            Log.e(this.a, "No such method: PackageManager.deletePackage", e2);
        } catch (IllegalArgumentException e3) {
            Log.e(this.a, "Illegal argument to invoke PackageManager.deletePackage", e3);
        } catch (IllegalAccessException e4) {
            Log.e(this.a, "Illegal access to invoke PackageManager.deletePackage", e4);
        } catch (InvocationTargetException e5) {
            Log.e(this.a, "Failed to invoke PackageManager.deletePackage", e5);
        }
        return false;
    }
}
