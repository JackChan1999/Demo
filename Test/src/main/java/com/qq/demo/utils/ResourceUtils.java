package com.qq.demo.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.qq.demo.R;
import com.qq.demo.ui.drawable.BlurDrawable;

import java.util.ArrayList;

public class ResourceUtils {
    private static Integer sStatusBarHeight;

    static final class AnonymousClass1 extends AnimatorListenerAdapter {
        final View view;

        AnonymousClass1(View view) {
            this.view = view;
        }

        public void onAnimationCancel(Animator animator) {
            view.setBackgroundColor(0);
            view.setHasTransientState(false);
        }

        public void onAnimationEnd(Animator animator) {
            this.view.setBackgroundColor(0);
            this.view.setHasTransientState(false);
        }
    }

    static final class AnonymousClass2 implements AnimatorUpdateListener {
        final  ColorMatrix colorMatrix;
        final  Drawable drawable;

        AnonymousClass2(ColorMatrix colorMatrix, Drawable drawable) {
            this.colorMatrix = colorMatrix;
            this.drawable = drawable;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.colorMatrix.set(new float[]{1.0f, 0.0f, 0.0f, 0.0f, floatValue, 0.0f, 1.0f, 0.0f, 0.0f, floatValue, 0.0f, 0.0f, 1.0f, 0.0f, floatValue, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
            this.drawable.setColorFilter(new ColorMatrixColorFilter(this.colorMatrix));
        }
    }

    public static ArrayList<String> getStringArray(Context context, TypedArray typedArray, int i) {
        TypedValue typedValue = new TypedValue();
        if (!typedArray.getValue(i, typedValue)) {
            return null;
        }
        int i2 = typedValue.resourceId;
        if (i2 == 0) {
            throw new IllegalStateException("can't find the resourceId");
        }
        TypedArray obtainTypedArray = context.getResources().obtainTypedArray(i2);
        int length = obtainTypedArray.length();
        ArrayList<String> arrayList = new ArrayList(length);
        for (int i3 = 0; i3 < length; i3++) {
            arrayList.add(obtainTypedArray.getString(i3));
        }
        obtainTypedArray.recycle();
        return arrayList;
    }

    public static Drawable getSmartBarBackground(Context context) {
        if (context == null) {
            return null;
        }
        int color = context.getResources().getColor(R.color.smartbar_divider);
        int color2 = context.getResources().getColor(R.color.smartbar_background);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.mc_smartbarbar_divider_height);
        int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.mz_action_bar_default_height_appcompat_split);
        Rect rect = new Rect();
        rect.set(0, 0, 0, dimensionPixelSize2 - dimensionPixelSize);
        return createDrawable(color2, color, rect);
    }

    public static Drawable getTitleBarBackground(Context context, int i) {
        return getTitleBarBackground(context, i, true);
    }

    public static Drawable getTitleBarBackground(Context context, int i, boolean z) {
        if (context == null) {
            return null;
        }
        int statusBarHeight;
        int color = context.getResources().getColor(R.color.titlebar_background);
        Rect rect = new Rect();
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.mc_titlebar_divider_height);
        int actionBarHeight = getActionBarHeight(context);
        if (z) {
            statusBarHeight = getStatusBarHeight(context);
        } else {
            statusBarHeight = 0;
        }
        rect.set(0, (statusBarHeight + actionBarHeight) - dimensionPixelSize, 0, 0);
        return createDrawable(color, i, rect);
    }

    private static Drawable createDrawable(int filtColor, int color, Rect rect) {
        ColorDrawable colorDrawable = new ColorDrawable(color);
        new BlurDrawable().setColorFilter(filtColor, BlurDrawable.DEFAULT_BLUR_COLOR_MODE);
        /*??????*/
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{new ColorDrawable(filtColor), colorDrawable});
        layerDrawable.setLayerInset(1, rect.left, rect.top, rect.right, rect.bottom);
        return layerDrawable;
    }

    public static Drawable createBlurDrawable(Drawable drawable, float f, int i) {
        BlurDrawable blurDrawable = new BlurDrawable();
        if (f >= 0.0f && f <= 1.0f) {
            blurDrawable.setBlurLevel(f);
        }
        return new LayerDrawable(new Drawable[]{blurDrawable, drawable});
    }

    public static int getActionBarHeight(Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
        }
        return context.getResources().getDimensionPixelSize(R.dimen.mz_action_bar_default_height_appcompat);
    }

    public static int getStatusBarHeight(Context context) {
        try {
            if (sStatusBarHeight == null) {
                Class cls = Class.forName("com.android.internal.R$dimen");
                sStatusBarHeight = Integer.valueOf(context.getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(cls.newInstance()).toString())));
            }
        } catch (Throwable e) {
            Log.e("ResurceUtils", "get status bar height fail", e);
            sStatusBarHeight = Integer.valueOf(context.getResources().getDimensionPixelSize(R.dimen.status_bar_height));
        }
        return sStatusBarHeight.intValue();
    }

    public static int getGradualColor(int startcolor, int endcolor, float f, int i3) {
        int round;
        int red = Color.red(startcolor);
        int green = Color.green(startcolor);
        int blue = Color.blue(startcolor);
        int alpha = Color.alpha(startcolor);
        int red2 = Color.red(endcolor);
        int green2 = Color.green(endcolor);
        int blue2 = Color.blue(endcolor);
        int alpha2 = Color.alpha(endcolor);
        if (i3 < 0) {
            round = Math.round(((float) red2) - (((float) (red2 - red)) * f));
            red2 = Math.round(((float) green2) - (((float) (green2 - green)) * f));
            green = Math.round(((float) blue2) - (((float) (blue2 - blue)) * f));
            red = Math.round(((float) alpha2) - (((float) (alpha2 - alpha)) * f));
        } else {
            round = Math.round((((float) (red2 - red)) * f) + ((float) red));
            red2 = Math.round(((float) green) + (((float) (green2 - green)) * f));
            green = Math.round(((float) blue) + (((float) (blue2 - blue)) * f));
            red = Math.round(((float) alpha) + (((float) (alpha2 - alpha)) * f));
        }
        return Color.argb(red, round, red2, green);
    }

    public static ValueAnimator getBackgroundAnimation(View view, int i, int i2) {
        int alpha = Color.alpha(i);
        if (alpha == 255 || alpha == 0) {
            i = Color.argb(20, Color.red(i), Color.green(i), Color.blue(i));
        }
        view.setHasTransientState(true);
        view.setBackgroundColor(i);
        ValueAnimator ofInt = ObjectAnimator.ofInt(view, "backgroundColor", new int[]{i, i2});
        ofInt.setEvaluator(new ArgbEvaluator());
        ofInt.setDuration(1000);
        ofInt.setStartDelay(700);
        ofInt.addListener(new AnonymousClass1(view));
        return ofInt;
    }

    public static void startBrightnessAnim(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        if (drawable instanceof ColorDrawable) {
            Log.i("error", "fade animation will not be useful to ColorDrawable because ColorDrawable has not implement method setColorFilter");
            return;
        }
        Rect bounds = drawable.getBounds();
        if (bounds == null || (bounds != null && bounds.isEmpty())) {
            Log.i("error", "you should setBounds for drawable before start brightness anim");
            return;
        }
        ColorMatrix colorMatrix = new ColorMatrix();
        Keyframe ofFloat = Keyframe.ofFloat(0.0f, 0.0f);
        Keyframe ofFloat2 = Keyframe.ofFloat(0.15f, 35.0f);
        Keyframe ofFloat3 = Keyframe.ofFloat(1.0f, 0.0f);
        Keyframe[] keyframeArr = new Keyframe[]{ofFloat, ofFloat2, ofFloat3};
        ValueAnimator ofPropertyValuesHolder = ValueAnimator.ofPropertyValuesHolder(
                new PropertyValuesHolder[]{PropertyValuesHolder.ofKeyframe("", keyframeArr)});
        ofPropertyValuesHolder.addUpdateListener(new AnonymousClass2(colorMatrix, drawable));
        ofPropertyValuesHolder.setDuration(550);
        ofPropertyValuesHolder.start();
    }

    @SuppressLint({"NewApi"})
    public static void actionBarHomeAsUpOnScrolling(Activity activity, int i, int i2, boolean z, int i3, int i4, int i5) {
        if (activity != null && VERSION.SDK_INT >= 18) {
            Drawable drawable = activity.getResources().getDrawable(i);
            Drawable drawable2 = activity.getResources().getDrawable(i2);
            if (drawable != null && drawable2 != null) {
                if (i3 == i4) {
                    activity.getActionBar().setHomeAsUpIndicator(i);
                    return;
                }
                Drawable drawable3;
                if (z) {
                    drawable3 = drawable2;
                } else {
                    drawable3 = drawable;
                }
                activity.getActionBar().setHomeAsUpIndicator(drawable3);
                Rect rect = new Rect();
                drawable2.getPadding(rect);
                int intrinsicWidth = drawable2.getIntrinsicWidth() - rect.right;
                int intrinsicWidth2 = drawable.getIntrinsicWidth();
                float f = ((float) i3) / ((float) (i5 - i4));
                intrinsicWidth = (int) ((((float) (intrinsicWidth - intrinsicWidth2)) * f) + ((float) intrinsicWidth2));
                if (z) {
                    intrinsicWidth += rect.right;
                }
                TypedArray obtainStyledAttributes = activity.obtainStyledAttributes(R.styleable.Theme);
                int i6 = obtainStyledAttributes.getInt(R.styleable.Theme_mzThemeColor, 3319271);
                obtainStyledAttributes.recycle();
                intrinsicWidth2 = getGradualColor(11053224, i6, f, 1);
                drawable3.setBounds(0, 0, intrinsicWidth, drawable3.getIntrinsicHeight());
                drawable3.setColorFilter(new LightingColorFilter(0, intrinsicWidth2));
            }
        }
    }

   /* public static Integer getThemeColor(Context context) {
        if (context.getResources().getIdentifier("mzThemeColor", "attr", context.getPackageName()) <= 0) {
            return null;
        }
        *//*????*//*
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{r1});
        int color = obtainStyledAttributes.getColor(0, -1);
        obtainStyledAttributes.recycle();
        if (color == -1) {
            return null;
        }
        return Integer.valueOf(color);
    }*/
}
