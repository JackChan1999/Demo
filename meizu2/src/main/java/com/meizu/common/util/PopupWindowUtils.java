package com.meizu.common.util;

import android.util.Log;
import android.widget.ListPopupWindow;
import java.lang.reflect.Method;

public class PopupWindowUtils {
    private static final String TAG = "PopupWindowUtils";

    public static boolean setContentHeight(ListPopupWindow listPopupWindow, int i) {
        if (CommonUtils.isFlymeOS()) {
            try {
                Method declaredMethod = ListPopupWindow.class.getDeclaredMethod("setContentHeight", new Class[]{Integer.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(listPopupWindow, new Object[]{Integer.valueOf(i)});
                return true;
            } catch (Exception e) {
                Log.e(TAG, "setContentHeight fail to be invoked.Caused by:" + e.getMessage());
                return false;
            }
        }
        listPopupWindow.setHeight(i);
        return true;
    }

    public static boolean adjustWindowPositionForMz(Object obj, boolean z) {
        if (CommonUtils.isFlymeOS()) {
            try {
                Method declaredMethod = obj.getClass().getDeclaredMethod("adjustWindowPositionForMz", new Class[]{Boolean.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(obj, new Object[]{Boolean.valueOf(z)});
                return true;
            } catch (Exception e) {
                Log.e(TAG, obj.getClass().getName() + "#adjustWindowPositionForMz fail to be invoked.Caused by:" + e.getMessage());
            }
        }
        return false;
    }

    public static boolean setLayoutMode(Object obj, int i) {
        if (CommonUtils.isFlymeOS()) {
            try {
                Method declaredMethod = obj.getClass().getDeclaredMethod("setLayoutMode", new Class[]{Integer.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(obj, new Object[]{Integer.valueOf(i)});
                return true;
            } catch (Exception e) {
                Log.e(TAG, obj.getClass().getName() + "#setLayoutMode fail to be invoked.Caused by:" + e.getMessage());
            }
        }
        return false;
    }

    public static boolean setPopupLayoutMode(Object obj, int i) {
        if (CommonUtils.isFlymeOS()) {
            try {
                Method declaredMethod = obj.getClass().getDeclaredMethod("setPopupLayoutMode", new Class[]{Integer.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(obj, new Object[]{Integer.valueOf(i)});
                return true;
            } catch (Exception e) {
                Log.e(TAG, "popupObject#setPopupLayoutMode fail to be invoked.Caused by:" + e.getMessage());
            }
        }
        return false;
    }
}
