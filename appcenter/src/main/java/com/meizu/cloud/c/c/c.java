package com.meizu.cloud.c.c;

import android.content.Context;
import com.meizu.cloud.pushsdk.constants.MeizuConstants;

public class c {
    public static String a(Context context, String key) throws IllegalArgumentException {
        String ret = "";
        try {
            Class systemProperties = context.getClassLoader().loadClass(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES);
            return (String) systemProperties.getMethod("get", new Class[]{String.class}).invoke(systemProperties, new Object[]{new String(key)});
        } catch (IllegalArgumentException iAE) {
            throw iAE;
        } catch (Exception e) {
            return "";
        }
    }
}
