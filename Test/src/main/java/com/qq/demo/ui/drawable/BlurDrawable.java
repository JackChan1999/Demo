package com.qq.demo.ui.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.view.ViewDebug.ExportedProperty;

import java.lang.reflect.Method;

public class BlurDrawable extends Drawable {
    public static final int DEFAULT_BLUR_COLOR = Color.RED;
    public static final Mode DEFAULT_BLUR_COLOR_MODE = Mode.SRC_OVER;
    public static final float DEFAULT_BLUR_LEVEL = 0.9f;
    public static final Method sDrawBlurRectMethod = getDrawBlurRectMethod();
    private boolean mMutated;
    @ExportedProperty(deepExport = true, prefix = "state_")
    private BlurState mState;

    static final class BlurState extends ConstantState {
        int mAlpha = 255;
        int mBaseColor = BlurDrawable.DEFAULT_BLUR_COLOR;
        @ExportedProperty
        int mChangingConfigurations;
        float mLevel = BlurDrawable.DEFAULT_BLUR_LEVEL;
        Paint mPaint = new Paint();
        int mUseColor = BlurDrawable.getUseColor(BlurDrawable.DEFAULT_BLUR_COLOR, 255, BlurDrawable.DEFAULT_BLUR_LEVEL);

        BlurState(BlurState blurState) {
            if (blurState != null) {
                this.mLevel = blurState.mLevel;
                this.mPaint = new Paint(blurState.mPaint);
                this.mChangingConfigurations = blurState.mChangingConfigurations;
            } else if (BlurDrawable.sDrawBlurRectMethod == null) {
                this.mPaint.setColor(this.mUseColor);
            }
        }

        public Drawable newDrawable() {
            return new BlurDrawable();
        }

        public Drawable newDrawable(Resources resources) {
            return new BlurDrawable();
        }

        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }
    }

    public BlurDrawable() {
        this(null);
    }

    public BlurDrawable(float f) {
        this(null);
        setBlurLevel(f);
    }

    private BlurDrawable(BlurState blurState) {
        this.mState = new BlurState(blurState);
        if (blurState == null) {
            setColorFilter(DEFAULT_BLUR_COLOR, DEFAULT_BLUR_COLOR_MODE);
        }
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mState.mChangingConfigurations;
    }

    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = new BlurState(this.mState);
            this.mMutated = true;
        }
        return this;
    }

    public void draw(Canvas canvas) {
        if (sDrawBlurRectMethod != null) {
            try {
                sDrawBlurRectMethod.invoke(canvas, new Object[]{getBounds(), Float.valueOf(this.mState.mLevel), this.mState.mPaint});
                return;
            } catch (Exception e) {
                canvas.drawRect(getBounds(), this.mState.mPaint);
                return;
            }
        }
        canvas.drawRect(getBounds(), this.mState.mPaint);
    }

    public float getBlurLevel() {
        return this.mState.mLevel;
    }

    public void setBlurLevel(float f) {
        if (this.mState.mLevel != f) {
            this.mState.mLevel = f;
            if (updateUseColor()) {
                invalidateSelf();
            }
        }
    }

    public int getAlpha() {
        return this.mState.mPaint.getAlpha();
    }

    public void setAlpha(int i) {
        if (this.mState.mAlpha != i) {
            this.mState.mAlpha = i;
            if (updateUseColor()) {
                if (sDrawBlurRectMethod != null) {
                    this.mState.mPaint.setAlpha(i);
                }
                invalidateSelf();
            }
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mState.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setColorFilter(int i, Mode mode) {
        if (sDrawBlurRectMethod != null) {
            super.setColorFilter(i, mode);
        } else if (this.mState.mBaseColor != i) {
            this.mState.mBaseColor = i;
            if (updateUseColor()) {
                invalidateSelf();
            }
        }
    }

    public void setXfermode(Xfermode xfermode) {
        this.mState.mPaint.setXfermode(xfermode);
        invalidateSelf();
    }

    public int getOpacity() {
        if (sDrawBlurRectMethod == null) {
            switch (this.mState.mPaint.getAlpha()) {
                case 0:
                    return -2;
                case 255:
                    return -1;
            }
        }
        return -3;
    }

    public ConstantState getConstantState() {
        this.mState.mChangingConfigurations = getChangingConfigurations();
        return this.mState;
    }

    private static Method getDrawBlurRectMethod() {
        try {
            return Canvas.class.getMethod("drawBlurRect", new Class[]{Rect.class, Float.TYPE, Paint.class});
        } catch (Exception e) {
            return null;
        }
    }

    private boolean updateUseColor() {
        if (sDrawBlurRectMethod != null) {
            return true;
        }
        int useColor = getUseColor(this.mState.mBaseColor, this.mState.mAlpha, this.mState.mLevel);
        if (this.mState.mUseColor == useColor) {
            return false;
        }
        this.mState.mUseColor = useColor;
        this.mState.mPaint.setColor(useColor);
        return true;
    }

    private static int getUseColor(int i, int i2, float f) {
        float f2 = (float) ((ViewCompat.MEASURED_STATE_MASK & i) >>> 24);
        return ((((int) (((f2 + ((255.0f - f2) * f)) * ((float) i2)) / 255.0f)) & 255) << 24) | (ViewCompat.MEASURED_SIZE_MASK & i);
    }
}
