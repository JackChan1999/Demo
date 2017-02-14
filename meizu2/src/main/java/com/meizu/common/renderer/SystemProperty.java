package com.meizu.common.renderer;

import android.util.Log;
import com.meizu.cloud.pushsdk.constants.MeizuConstants;
import com.meizu.common.renderer.effect.GLRenderManager;
import java.lang.reflect.Method;

public class SystemProperty {
    private static Method sGetBooleanMethod = null;
    private static Method sGetMethod = null;

    static {
        init();
    }

    public static String get(String str, String str2) {
        try {
            return (String) sGetMethod.invoke(null, new Object[]{str});
        } catch (Exception e) {
            Log.e(GLRenderManager.TAG, "SystemProperty get " + e.toString());
            return str2;
        }
    }

    public static String get(String str) {
        try {
            return (String) sGetMethod.invoke(null, new Object[]{str});
        } catch (Exception e) {
            Log.e(GLRenderManager.TAG, "SystemProperty get " + e.toString());
            return null;
        }
    }

    public static boolean getBoolean(String str, boolean z) {
        Boolean bool;
        Boolean.valueOf(z);
        try {
            bool = (Boolean) sGetBooleanMethod.invoke(null, new Object[]{str, Boolean.valueOf(z)});
        } catch (Exception e) {
            Exception exception = e;
            bool = Boolean.valueOf(z);
            Log.e(GLRenderManager.TAG, "SystemProperty getBoolean " + exception.toString());
        }
        return bool.booleanValue();
    }

    private static void init() {
        try {
            Class cls = Class.forName(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES);
            sGetMethod = cls.getDeclaredMethod("get", new Class[]{String.class});
            sGetBooleanMethod = cls.getDeclaredMethod("getBoolean", new Class[]{String.class, Boolean.TYPE});
        } catch (Exception e) {
            Log.e(GLRenderManager.TAG, "SystemProperty init " + e.toString());
        }
    }
}
