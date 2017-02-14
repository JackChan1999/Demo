package com.meizu.common.renderer;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.util.TypedValue;
import com.meizu.common.drawble.BlurDrawable;
import com.meizu.common.renderer.drawable.GLBlurDrawable;
import com.meizu.common.renderer.effect.GLRenderManager;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;

public class RendererUtils {
    public static Drawable getTitleBarWithTabBackground(Context context, int i, int i2) {
        return getTitleBarWithTabBackground(context, i, i2, BlurDrawable.DEFAULT_BLUR_LEVEL);
    }

    public static Drawable getTitleBarWithTabBackground(Context context, int i, int i2, float f) {
        if (context == null) {
            return null;
        }
        Rect rect = new Rect();
        rect.set(0, ((getActionBarHeight(context) + getStatusBarHeight(context)) + dip2px(context, 38.0f)) - dip2px(context, 0.3f), 0, 0);
        return createDrawable(i, i2, f, rect);
    }

    public static Drawable getActionBarBackground(Context context, int i, int i2) {
        return getActionBarBackground(context, i, i2, 1.0f);
    }

    public static Drawable getActionBarBackground(Context context, int i, int i2, float f) {
        if (context == null) {
            return null;
        }
        int dip2px = dip2px(context, 0.3f);
        int dip2px2 = dip2px(context, 52.0f);
        Rect rect = new Rect();
        rect.set(0, 0, 0, dip2px2 - dip2px);
        return createDrawable(i, i2, f, rect);
    }

    public static Drawable getTitleBarBackground(Context context, int i, int i2) {
        return getTitleBarBackground(context, i, i2, 1.0f);
    }

    public static Drawable getTitleBarBackground(Context context, int i, int i2, float f) {
        if (context == null) {
            return null;
        }
        Rect rect = new Rect();
        rect.set(0, (getActionBarHeight(context) + getStatusBarHeight(context)) - dip2px(context, 0.3f), 0, 0);
        return createDrawable(i, i2, f, rect);
    }

    private static Drawable createDrawable(int i, int i2, float f, Rect rect) {
        ColorDrawable colorDrawable = new ColorDrawable(i2);
        GLBlurDrawable gLBlurDrawable = new GLBlurDrawable();
        gLBlurDrawable.setColorFilter(i);
        gLBlurDrawable.setBlurLevel(f);
        gLBlurDrawable.setForce(true);
        Drawable layerDrawable = new LayerDrawable(new Drawable[]{gLBlurDrawable, colorDrawable});
        layerDrawable.setLayerInset(1, rect.left, rect.top, rect.right, rect.bottom);
        return layerDrawable;
    }

    private static int getActionBarHeight(Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(16843499, typedValue, true)) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
        }
        return 144;
    }

    private static int getStatusBarHeight(Context context) {
        try {
            Class cls = Class.forName("com.android.internal.R$dimen");
            return context.getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(cls.newInstance()).toString()));
        } catch (Throwable e) {
            Log.e(GLRenderManager.TAG, "get status bar height fail", e);
            return 75;
        }
    }

    private static int dip2px(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + FastBlurParameters.DEFAULT_LEVEL);
    }
}
