package com.meizu.cloud.app.share;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.IBinder;
import com.meizu.cloud.c.b.a.c;
import java.lang.reflect.Method;
import java.util.List;

public class a {
    private Object a;

    public a() {
        try {
            Object iPackageManager = c.a().a(c.a().a("android.os.ServiceManager"), "getService", String.class).invoke(null, new Object[]{"package"});
            this.a = c.a().a(c.a().a("android.content.pm.IPackageManager$Stub"), "asInterface", IBinder.class).invoke(null, new Object[]{iPackageManager});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean a() {
        boolean guestMode = false;
        if (this.a != null) {
            try {
                guestMode = ((Boolean) c.a().a(c.a().a("android.content.pm.IPackageManager$Stub$Proxy"), "isGuestMode", new Class[0]).invoke(this.a, new Object[0])).booleanValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return guestMode;
    }

    public boolean a(String PkgName) {
        boolean result = false;
        if (this.a != null) {
            try {
                Method getPackageGuestSetting = c.a().a(c.a().a("android.content.pm.IPackageManager$Stub$Proxy"), "getPackageGuestSetting", String.class, Integer.TYPE);
                Method myUserId = c.a().a(c.a().a("android.os.UserHandle"), "myUserId", new Class[0]);
                result = ((Boolean) getPackageGuestSetting.invoke(this.a, new Object[]{PkgName, myUserId.invoke(null, new Object[0])})).booleanValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean a(Context context, String packageName) {
        List<PackageInfo> pinfo = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (((PackageInfo) pinfo.get(i)).packageName.equalsIgnoreCase(packageName)) {
                return true;
            }
        }
        return false;
    }
}
