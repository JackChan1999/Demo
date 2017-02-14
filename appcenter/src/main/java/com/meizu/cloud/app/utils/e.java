package com.meizu.cloud.app.utils;

import android.content.Context;
import android.os.Parcelable;
import android.os.storage.StorageVolume;
import java.lang.reflect.Method;

public class e {
    public static StorageVolume[] a() {
        Parcelable[] list = r.b();
        if (list == null) {
            return new StorageVolume[0];
        }
        int length = list.length;
        StorageVolume[] result = new StorageVolume[length];
        for (int i = 0; i < length; i++) {
            result[i] = (StorageVolume) list[i];
        }
        return result;
    }

    public static String a(Context context, StorageVolume volume) {
        try {
            Method method = volume.getClass().getMethod("getDescription", new Class[]{Context.class});
            if (method == null) {
                return (String) volume.getClass().getMethod("getDescription", new Class[0]).invoke(volume, new Object[0]);
            }
            return (String) method.invoke(volume, new Object[]{context});
        } catch (Throwable th) {
            return volume.getPath();
        }
    }
}
