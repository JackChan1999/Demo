package com.meizu.common.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.util.TypedValue;
import com.meizu.common.a.a;
import com.meizu.common.a.d;
import java.util.ArrayList;

public class c {
    private static Integer a;

    public static ArrayList<String> a(Context context, TypedArray a, int index) {
        TypedValue outValue = new TypedValue();
        if (!a.getValue(index, outValue)) {
            return null;
        }
        int resourceId = outValue.resourceId;
        if (resourceId == 0) {
            throw new IllegalStateException("can't find the resourceId");
        }
        TypedArray array = context.getResources().obtainTypedArray(resourceId);
        int count = array.length();
        ArrayList<String> strs = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            strs.add(array.getString(i));
        }
        array.recycle();
        return strs;
    }

    public static Drawable a(Context context) {
        if (context == null) {
            return null;
        }
        int divider = context.getResources().getColor(com.meizu.common.a.c.mc_smartbar_divider);
        int background = context.getResources().getColor(com.meizu.common.a.c.mc_smartbar_background);
        int smartbarDividerHeight = context.getResources().getDimensionPixelSize(d.mc_smartbarbar_divider_height);
        int smartbarHeight = context.getResources().getDimensionPixelSize(d.mz_action_bar_default_height_appcompat_split);
        Rect layerInset = new Rect();
        layerInset.set(0, 0, 0, smartbarHeight - smartbarDividerHeight);
        return a(background, divider, layerInset);
    }

    private static Drawable a(int background, int divider, Rect layerInset) {
        ColorDrawable dividerDrawable = new ColorDrawable(divider);
        new a().setColorFilter(background, a.a);
        LayerDrawable ld = new LayerDrawable(new Drawable[]{blurDrawable, dividerDrawable});
        ld.setLayerInset(1, layerInset.left, layerInset.top, layerInset.right, layerInset.bottom);
        return ld;
    }

    public static Drawable a(Drawable background, float level, int color) {
        a blurDrawable = new a();
        if (level >= 0.0f && level <= 1.0f) {
            blurDrawable.a(level);
        }
        return new LayerDrawable(new Drawable[]{blurDrawable, background});
    }

    public static int b(Context context) {
        try {
            if (a == null) {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                a = Integer.valueOf(context.getResources().getDimensionPixelSize(Integer.parseInt(c.getField("status_bar_height").get(c.newInstance()).toString())));
            }
        } catch (Exception e) {
            Log.e("ResurceUtils", "get status bar height fail", e);
            a = Integer.valueOf(context.getResources().getDimensionPixelSize(d.status_bar_height));
        }
        return a.intValue();
    }

    public static Integer c(Context context) {
        if (context.getResources().getIdentifier("mzThemeColor", "attr", context.getPackageName()) <= 0) {
            return null;
        }
        TypedArray array = context.getTheme().obtainStyledAttributes(new int[]{id});
        int color = array.getColor(0, -1);
        array.recycle();
        if (color == -1) {
            return null;
        }
        return Integer.valueOf(color);
    }
}
