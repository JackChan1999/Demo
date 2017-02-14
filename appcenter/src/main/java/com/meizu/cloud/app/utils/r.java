package com.meizu.cloud.app.utils;

import android.app.AlertDialog;
import android.app.Notification.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import com.meizu.cloud.b.a;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.c.b.a.c;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class r {
    public static void a(Context context, Builder builder) {
        try {
            Object flymeNotificationBuilder = c.a().a(Builder.class, "mFlymeNotificationBuilder").get(builder);
            c.a().a(flymeNotificationBuilder.getClass(), "setCircleProgressBar", Boolean.TYPE).invoke(flymeNotificationBuilder, new Object[]{Boolean.valueOf(true)});
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
        }
    }

    public static void a(AlertDialog dialog) {
        try {
            c.a().a(AlertDialog.class, "setButtonTextColor", Integer.TYPE, Integer.TYPE).invoke(dialog, new Object[]{Integer.valueOf(-1), Integer.valueOf(a.c.theme_color)});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
        }
    }

    public static Boolean a() {
        try {
            return (Boolean) c.a().a("android.os.BuildExt").getDeclaredField("IS_SHOPDEMO").get(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (NoSuchFieldException e3) {
            e3.printStackTrace();
        }
        return Boolean.valueOf(false);
    }

    public static final Boolean a(Context context, String packageName) {
        try {
            return (Boolean) com.meizu.f.a.a(context.getSystemService((String) com.meizu.f.a.a("android.content.ContextExt", "ACCESS_CONTROL_SERVICE")), "checkAccessControl", new String[]{packageName});
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.valueOf(false);
        }
    }

    public static void a(Fragment fragment, int requestCode) {
        if (fragment != null) {
            Intent intent = new Intent();
            intent.setAction("meizu.intent.action.CONFIRM_PASSWORD");
            intent.putExtra("password_from", 2);
            try {
                fragment.startActivityForResult(intent, requestCode);
            } catch (ActivityNotFoundException e) {
                t.a(fragment.getActivity().getBaseContext(), fragment.getString(i.not_support_operation), 0, 0);
            }
        }
    }

    public static Parcelable[] b() {
        Parcelable[] parcelableArr = null;
        IBinder iBinder = com.meizu.cloud.c.b.a.a("mount");
        try {
            Object o = c.a().a(c.a().a("android.os.storage.IMountService$Stub"), "asInterface", IBinder.class).invoke(null, new Object[]{iBinder});
            Method b = o.getClass().getDeclaredMethod("getVolumeList", new Class[0]);
            b.setAccessible(true);
            return (Parcelable[]) b.invoke(o, new Object[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return parcelableArr;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return parcelableArr;
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
            return parcelableArr;
        } catch (IllegalAccessException e4) {
            e4.printStackTrace();
            return parcelableArr;
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
            return parcelableArr;
        } catch (Exception e6) {
            e6.printStackTrace();
            return parcelableArr;
        }
    }

    public static int a(String permission, int uid, int owningUid, boolean exported) {
        int result = 0;
        try {
            result = ((Integer) c.a().a("android.app.ActivityManager").getMethod("checkComponentPermission", new Class[]{String.class, Integer.TYPE, Integer.TYPE, Boolean.TYPE}).invoke(null, new Object[]{permission, Integer.valueOf(uid), Integer.valueOf(owningUid), Boolean.valueOf(exported)})).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
