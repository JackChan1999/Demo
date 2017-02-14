package com.meizu.cloud.c.b;

import android.content.Context;
import com.meizu.cloud.c.b.a.c;
import com.meizu.cloud.pushsdk.constants.MeizuConstants;

public class b {
    public static String a(Context context, String key) throws IllegalArgumentException {
        String ret = "";
        try {
            Class systemProperties = context.getClassLoader().loadClass(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES);
            return (String) c.a().a(systemProperties, "get", String.class).invoke(systemProperties, new Object[]{new String(key)});
        } catch (IllegalArgumentException iAE) {
            throw iAE;
        } catch (Exception e) {
            return "";
        }
    }

    public static String a(Context context, String key, String def) throws IllegalArgumentException {
        String ret = def;
        try {
            Class systemProperties = context.getClassLoader().loadClass(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES);
            return (String) c.a().a(systemProperties, "get", String.class, String.class).invoke(systemProperties, new Object[]{new String(key), new String(def)});
        } catch (IllegalArgumentException iAE) {
            throw iAE;
        } catch (Exception e) {
            return def;
        }
    }
}
