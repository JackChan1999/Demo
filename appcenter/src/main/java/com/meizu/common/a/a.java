package com.meizu.common.a;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.view.ViewDebug.ExportedProperty;
import java.lang.reflect.Method;

public class a extends Drawable {
    public static final Mode a = Mode.SRC_OVER;
    public static final Method b = a();
    @ExportedProperty(deepExport = true, prefix = "state_")
    private a c;
    private boolean d;

    static final class a extends ConstantState {
        float a = 0.9f;
        Paint b = new Paint();
        int c = 255;
        int d = -637534209;
        int e = a.b(-637534209, 255, 0.9f);
        @ExportedProperty
        int f;

        a(a state) {
            if (state != null) {
                this.a = state.a;
                this.b = new Paint(state.b);
                this.f = state.f;
            } else if (a.b == null) {
                this.b.setColor(this.e);
            }
        }

        public Drawable newDrawable() {
            return new a();
        }

        public Drawable newDrawable(Resources res) {
            return new a();
        }

        public int getChangingConfigurations() {
            return this.f;
        }
    }

    public a() {
        this(null);
    }

    private a(a state) {
        this.c = new a(state);
        if (state == null) {
            setColorFilter(-637534209, a);
        }
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.c.f;
    }

    public Drawable mutate() {
        if (!this.d && super.mutate() == this) {
            this.c = new a(this.c);
            this.d = true;
        }
        return this;
    }

    public void draw(Canvas canvas) {
        if (b != null) {
            try {
                b.invoke(canvas, new Object[]{getBounds(), Float.valueOf(this.c.a), this.c.b});
                return;
            } catch (Exception e) {
                canvas.drawRect(getBounds(), this.c.b);
                return;
            }
        }
        canvas.drawRect(getBounds(), this.c.b);
    }

    public void a(float level) {
        if (this.c.a != level) {
            this.c.a = level;
            if (b()) {
                invalidateSelf();
            }
        }
    }

    public int getAlpha() {
        return this.c.b.getAlpha();
    }

    public void setAlpha(int alpha) {
        if (this.c.c != alpha) {
            this.c.c = alpha;
            if (b()) {
                if (b != null) {
                    this.c.b.setAlpha(alpha);
                }
                invalidateSelf();
            }
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.c.b.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setColorFilter(int color, Mode mode) {
        if (b != null) {
            super.setColorFilter(color, mode);
        } else if (this.c.d != color) {
            this.c.d = color;
            if (b()) {
                invalidateSelf();
            }
        }
    }

    public int getOpacity() {
        if (b == null) {
            switch (this.c.b.getAlpha()) {
                case 0:
                    return -2;
                case 255:
                    return -1;
            }
        }
        return -3;
    }

    public ConstantState getConstantState() {
        this.c.f = getChangingConfigurations();
        return this.c;
    }

    private static Method a() {
        try {
            return Canvas.class.getMethod("drawBlurRect", new Class[]{Rect.class, Float.TYPE, Paint.class});
        } catch (Exception e) {
            return null;
        }
    }

    private boolean b() {
        if (b != null) {
            return true;
        }
        int useColor = b(this.c.d, this.c.c, this.c.a);
        if (this.c.e == useColor) {
            return false;
        }
        this.c.e = useColor;
        this.c.b.setColor(useColor);
        return true;
    }

    private static int b(int color, int alpha, float level) {
        float a = (float) ((-16777216 & color) >>> 24);
        return ((((int) ((((float) alpha) * (a + ((255.0f - a) * level))) / 255.0f)) & 255) << 24) | (16777215 & color);
    }
}
