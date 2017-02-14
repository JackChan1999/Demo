package com.meizu.common.util;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.meizu.common.drawble.BlurDrawable;
import java.lang.reflect.InvocationTargetException;

public class BlurActivityUtils {
    public static void enableBlurActivity(Activity activity) {
        BlurDrawable blurDrawable = new BlurDrawable();
        ActionBar actionBar = activity.getActionBar();
        actionBar.setDisplayOptions(0);
        setActionBarViewCollapsable(actionBar, true);
        ColorDrawable colorDrawable = new ColorDrawable(-695533);
        actionBar.setBackgroundDrawable(new LayerDrawable(new Drawable[]{new BlurDrawable(), colorDrawable}));
        actionBar.setSplitBackgroundDrawable(new BlurDrawable());
        Window window = activity.getWindow();
        LayoutParams attributes = window.getAttributes();
        attributes.flags |= 67108864;
        window.setAttributes(attributes);
    }

    private static void setActionBarViewCollapsable(ActionBar actionBar, boolean z) {
        try {
            try {
                Class.forName("android.app.ActionBar").getMethod("setActionBarViewCollapsable", new Class[]{Boolean.TYPE}).invoke(actionBar, new Object[]{Boolean.valueOf(z)});
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            }
        } catch (SecurityException e4) {
            e4.printStackTrace();
        } catch (NoSuchMethodException e5) {
            e5.printStackTrace();
        } catch (ClassNotFoundException e6) {
            e6.printStackTrace();
        }
    }
}
