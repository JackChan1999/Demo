package com.meizu.cloud.app.utils;

import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.meizu.cloud.c.b.a.b;
import java.lang.reflect.Field;

public class i {
    public static void a(FragmentActivity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService("input_method");
            if (imm != null) {
                try {
                    b.a(imm, "windowDismissed", new Class[]{IBinder.class}, new Object[]{activity.getWindow().getDecorView().getWindowToken()});
                    b.a(imm, "startGettingWindowFocus", new Class[]{View.class}, new View[]{null});
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
                for (String param : arr) {
                    try {
                        Field f = imm.getClass().getDeclaredField(param);
                        if (!f.isAccessible()) {
                            f.setAccessible(true);
                        }
                        Object obj_get = f.get(imm);
                        if (obj_get != null && (obj_get instanceof View)) {
                            if (((View) obj_get).getContext() == activity) {
                                f.set(imm, null);
                            } else {
                                return;
                            }
                        }
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        }
    }
}
