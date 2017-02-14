package com.meizu.cloud.app.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.widget.TextView;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.d;

public class b {
    public static void a(TextView textView, int colorId, boolean bSolid) {
        a(textView, colorId, (float) (textView.getResources().getDimensionPixelOffset(d.rank_button_height) / 2), bSolid);
    }

    public static void a(TextView textView, int colorId, float radius, boolean bSolid) {
        textView.setBackground(a(textView.getContext(), colorId, radius, bSolid));
        textView.setTextColor(a(textView.getContext(), colorId, bSolid));
    }

    public static Drawable a(Context context, int colorId, float radius, boolean bSolid) {
        return b(context, context.getResources().getColor(colorId), radius, bSolid);
    }

    public static Drawable b(Context context, int color, float radius, boolean bSolid) {
        GradientDrawable normalDrawable = new GradientDrawable();
        GradientDrawable pressedDrawable = new GradientDrawable();
        GradientDrawable unableDrawable = new GradientDrawable();
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] = hsv[2] - (hsv[2] * 0.15f);
        if (bSolid) {
            normalDrawable.setColor(color);
            pressedDrawable.setColor(Color.HSVToColor(hsv));
        } else {
            normalDrawable.setStroke(2, color);
            pressedDrawable.setStroke(2, Color.HSVToColor(hsv));
        }
        unableDrawable.setColor(context.getResources().getColor(17170445));
        normalDrawable.setCornerRadius(radius);
        pressedDrawable.setCornerRadius(radius);
        StateListDrawable seletor = new StateListDrawable();
        seletor.addState(new int[]{16842908}, pressedDrawable);
        seletor.addState(new int[]{16842919}, pressedDrawable);
        seletor.addState(new int[]{-16842910}, unableDrawable);
        seletor.addState(new int[0], normalDrawable);
        return seletor;
    }

    public static ColorStateList a(Context context, int colorId, boolean bSolid) {
        return a(context, context.getResources().getColor(colorId), bSolid, context.getResources().getColor(17170443));
    }

    public static ColorStateList a(Context context, int color, boolean bSolid, int solidColor) {
        int normalColor;
        int pressedColor;
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] = hsv[2] - (hsv[2] * 0.15f);
        int unableColor = context.getResources().getColor(c.btn_unable);
        if (bSolid) {
            normalColor = solidColor;
            pressedColor = normalColor;
        } else {
            normalColor = color;
            pressedColor = Color.HSVToColor(hsv);
        }
        int[] colors = new int[]{pressedColor, pressedColor, unableColor, normalColor, normalColor};
        states = new int[5][];
        states[0] = new int[]{16842919};
        states[1] = new int[]{16842908};
        states[2] = new int[]{-16842910};
        states[3] = new int[]{16842910};
        states[4] = new int[0];
        return new ColorStateList(states, colors);
    }

    public static ColorStateList a(Context context, int colorId) {
        return b(context, context.getResources().getColor(colorId));
    }

    public static ColorStateList b(Context context, int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] = hsv[2] - (hsv[2] * 0.15f);
        int unableColor = context.getResources().getColor(c.btn_unable);
        int normalColor = color;
        int pressedColor = Color.HSVToColor(hsv);
        int[] colors = new int[]{pressedColor, pressedColor, unableColor, normalColor, normalColor};
        states = new int[5][];
        states[0] = new int[]{16842919};
        states[1] = new int[]{16842908};
        states[2] = new int[]{-16842910};
        states[3] = new int[]{16842910};
        states[4] = new int[0];
        return new ColorStateList(states, colors);
    }

    public static ColorStateList c(Context context, int colorId) {
        return d(context, context.getResources().getColor(colorId));
    }

    public static ColorStateList d(Context context, int color) {
        return new ColorStateList(new int[][]{new int[0]}, new int[]{color});
    }
}
