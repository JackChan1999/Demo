package com.meizu.cloud.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.support.v4.app.FragmentActivity;
import android.support.v7.c.d;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.meizu.cloud.app.request.structitem.SpecialConfig.Colors;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.c.b.a.b;
import com.meizu.common.a.a;

public class u {
    public static void a(FragmentActivity activity, boolean on) {
        try {
            activity.getClass().getMethod("setStatusBarDarkIcon", new Class[]{Boolean.TYPE}).invoke(activity, new Object[]{Boolean.valueOf(on)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void a(FragmentActivity activity, int color) {
        try {
            ((Toolbar) activity.findViewById(f.action_bar)).setTitleTextColor(color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int a(FragmentActivity activity) {
        try {
            return ((TextView) b.a((Toolbar) activity.findViewById(f.action_bar), "mTitleTextView")).getCurrentTextColor();
        } catch (Exception e) {
            e.printStackTrace();
            return -16777216;
        }
    }

    public static Drawable b(FragmentActivity activity) {
        try {
            return ((ImageButton) b.a((Toolbar) activity.findViewById(f.action_bar), "mNavButtonView")).getDrawable();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Drawable c(FragmentActivity activity) {
        try {
            return (Drawable) b.a(activity.findViewById(f.action_bar_container), "mBackground");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Drawable a(Context context, int color) {
        try {
            return new ColorDrawable(color);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Drawable a(int color, int alphaRadius) {
        try {
            a blurDrawable = new a();
            blurDrawable.setColorFilter(Color.argb((Color.alpha(color) * alphaRadius) / 100, Color.red(color), Color.green(color), Color.blue(color)), a.a);
            return blurDrawable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap a(Bitmap bitmap) {
        return a(bitmap, b(bitmap));
    }

    public static Bitmap a(Bitmap bitmap, int coverColor) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        ColorFilter colorFilter = new PorterDuffColorFilter(-1593835521 & coverColor, Mode.SRC_OVER);
        Bitmap blurredBitmap = new com.meizu.cloud.app.jniutils.a(bitmap).a(120);
        Canvas canvas = new Canvas(blurredBitmap);
        canvas.save();
        paint.setColorFilter(colorFilter);
        canvas.drawBitmap(blurredBitmap, 0.0f, 0.0f, paint);
        canvas.restore();
        return blurredBitmap;
    }

    public static int b(Bitmap bitmap) {
        d palette = d.a(bitmap);
        d.a swatch = null;
        if (null == null) {
            swatch = palette.e();
        }
        if (swatch == null) {
            swatch = palette.g();
        }
        if (swatch == null) {
            swatch = palette.f();
        }
        if (swatch == null) {
            swatch = palette.d();
        }
        if (swatch == null) {
            swatch = palette.b();
        }
        if (swatch == null) {
            swatch = palette.c();
        }
        if (swatch != null) {
            return swatch.a();
        }
        return 0;
    }

    public static void a(Bitmap bm, Rect rect, int[] colors, Orientation orientation) {
        GradientDrawable gradientDrawable = new GradientDrawable(orientation, colors);
        gradientDrawable.setGradientType(0);
        gradientDrawable.setBounds(rect);
        gradientDrawable.setLevel(8);
        gradientDrawable.draw(new Canvas(bm));
    }

    public static Colors a(Context context, JSONObject colors) {
        try {
            Colors colorsConfig = new Colors();
            if (colors.containsKey("bg_color")) {
                colorsConfig.bg_color = Color.parseColor(colors.getString("bg_color"));
            } else {
                colorsConfig.bg_color = context.getResources().getColor(c.activity_background_color);
            }
            if (colors.containsKey("btn_color")) {
                colorsConfig.btn_color = Color.parseColor(colors.getString("btn_color"));
            } else {
                colorsConfig.btn_color = context.getResources().getColor(c.theme_color);
            }
            if (colors.containsKey("line_color")) {
                colorsConfig.line_color = Color.parseColor(colors.getString("line_color"));
            } else {
                colorsConfig.line_color = Color.parseColor("#4D000000");
            }
            if (colors.containsKey("sb_color")) {
                colorsConfig.sb_color = Color.parseColor(colors.getString("sb_color"));
            } else {
                colorsConfig.sb_color = context.getResources().getColor(c.activity_background_color);
            }
            if (colors.containsKey("sb_iconcolor")) {
                colorsConfig.sb_iconcolor = Color.parseColor(colors.getString("sb_iconcolor"));
            } else {
                colorsConfig.sb_iconcolor = context.getResources().getColor(c.theme_color);
            }
            if (colors.containsKey("actionbar_color")) {
                colorsConfig.actionbar_color = Color.parseColor(colors.getString("actionbar_color"));
            } else {
                colorsConfig.actionbar_color = colorsConfig.bg_color;
            }
            if (colors.containsKey("text_color")) {
                colorsConfig.text_color = Color.parseColor(colors.getString("text_color"));
            } else {
                colorsConfig.text_color = context.getResources().getColor(c.title_color);
            }
            if (colors.containsKey("sb_unselectedcolor")) {
                colorsConfig.sb_unselectedcolor = Color.parseColor(colors.getString("sb_unselectedcolor"));
            }
            if (colors.containsKey("statusicon_color")) {
                colorsConfig.statusicon_color = Color.parseColor(colors.getString("statusicon_color"));
            }
            if (colors.containsKey("title_color")) {
                colorsConfig.title_color = Color.parseColor(colors.getString("title_color"));
            } else {
                colorsConfig.title_color = colorsConfig.text_color;
            }
            if (colors.containsKey("des_text_color")) {
                colorsConfig.des_text_color = Color.parseColor(colors.getString("des_text_color"));
            } else {
                colorsConfig.des_text_color = colorsConfig.text_color;
            }
            if (colors.containsKey("btn_text_color")) {
                colorsConfig.btn_text_color = Color.parseColor(colors.getString("btn_text_color"));
                return colorsConfig;
            }
            colorsConfig.btn_text_color = colorsConfig.text_color;
            return colorsConfig;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
