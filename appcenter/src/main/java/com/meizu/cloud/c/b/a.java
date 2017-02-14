package com.meizu.cloud.c.b;

import android.os.IBinder;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class a {
    private a() {
    }

    public static IBinder a(String name) {
        IBinder binder = null;
        try {
            Class<?> cls = a().loadClass("android.os.ServiceManager");
            Constructor<?> con = cls.getDeclaredConstructor(new Class[0]);
            con.setAccessible(true);
            return (IBinder) cls.getMethod("getService", new Class[]{String.class}).invoke(con.newInstance(new Object[0]), new Object[]{name});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return binder;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return binder;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return binder;
        } catch (InstantiationException e4) {
            e4.printStackTrace();
            return binder;
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
            return binder;
        } catch (Exception e6) {
            e6.printStackTrace();
            return binder;
        }
    }

    private static final ClassLoader a() {
        return a.class.getClassLoader();
    }
}
