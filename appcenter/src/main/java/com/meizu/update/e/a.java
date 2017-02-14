package com.meizu.update.e;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageInstallObserver;
import android.net.Uri;
import android.os.RemoteException;
import com.meizu.update.UpdateInfo;
import com.meizu.update.h.b;
import java.io.File;

public class a {

    public enum a {
        NOT_SUPPORT,
        SUCCESS,
        FAILED;
        
        private int d;

        protected void a(int errorCode) {
            this.d = errorCode;
        }

        public int a() {
            return this.d;
        }
    }

    public static a a(Context context, String apkPath) {
        final Object LOCK = new Object();
        final a result = a.SUCCESS;
        try {
            int INSTALL_REPLACE_EXISTING = ((Integer) com.meizu.f.a.a("android.content.pm.PackageManager", "INSTALL_REPLACE_EXISTING")).intValue();
            final int INSTALL_SUCCEEDED = ((Integer) com.meizu.f.a.a("android.content.pm.PackageManager", "INSTALL_SUCCEEDED")).intValue();
            com.meizu.f.a.a(context.getPackageManager(), "installPackage", new Class[]{Uri.class, IPackageInstallObserver.class, Integer.TYPE, String.class}, new Object[]{Uri.parse("file://" + apkPath), new android.content.pm.IPackageInstallObserver.a() {
                public void packageInstalled(String packageName, int returnCode) throws RemoteException {
                    if (returnCode != INSTALL_SUCCEEDED) {
                        b.c("install return code : " + returnCode);
                    }
                    result.a(returnCode);
                    synchronized (LOCK) {
                        LOCK.notify();
                    }
                }
            }, Integer.valueOf(INSTALL_REPLACE_EXISTING), null});
            synchronized (LOCK) {
                try {
                    LOCK.wait(120000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return a.FAILED;
                }
            }
            if (result.a() == INSTALL_SUCCEEDED) {
                return result;
            }
            a error = a.FAILED;
            error.a(result.a());
            return error;
        } catch (Exception e2) {
            b.a(context, "background install error :" + e2.getMessage());
            e2.printStackTrace();
            return a.NOT_SUPPORT;
        }
    }

    public static final Intent a(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        return intent;
    }

    public static final void a(Context context, String path, UpdateInfo info) {
        com.meizu.update.f.a.a(context, info);
        Intent intent = a(path);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }
}
