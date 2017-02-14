package com.meizu.common.renderer.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import com.meizu.common.renderer.effect.GLRenderManager;
import com.meizu.common.renderer.effect.parameters.BlurParameters;
import com.meizu.common.renderer.functor.AbstractBlurFunctor;
import com.meizu.common.renderer.functor.DrawBlurFunctor;
import com.meizu.common.renderer.functor.DrawFastBlurFunctor;

public class GLBlurDrawable extends Drawable {
    protected boolean mMutated;
    private Paint mPaint;
    protected BlurState mState;

    public static class BlurState extends ConstantState {
        int mChangingConfigurations;
        AbstractBlurFunctor mDrawGLFunctor;
        boolean mForce = false;
        boolean mProgress = false;

        BlurState(boolean z) {
            this.mProgress = z;
            newGLFunctor();
        }

        BlurState(BlurState blurState) {
            this.mForce = blurState.mForce;
            this.mProgress = blurState.mProgress;
            newGLFunctor();
            this.mDrawGLFunctor.getParameters().setLevel(blurState.mDrawGLFunctor.getParameters().getLevel());
            this.mDrawGLFunctor.getParameters().setFilterColor(blurState.mDrawGLFunctor.getParameters().getFilterColor());
            this.mDrawGLFunctor.setAlpha(blurState.mDrawGLFunctor.getAlpha());
            this.mChangingConfigurations = blurState.mChangingConfigurations;
        }

        protected void newGLFunctor() {
            if (!this.mProgress || VERSION.SDK_INT < 21) {
                this.mDrawGLFunctor = new DrawBlurFunctor();
                if (this.mProgress) {
                    this.mDrawGLFunctor.getParameters().setProgressBlur(true);
                    return;
                }
                return;
            }
            this.mDrawGLFunctor = new DrawFastBlurFunctor();
        }

        public Drawable newDrawable() {
            return new GLBlurDrawable(new BlurState(this));
        }

        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }
    }

    public GLBlurDrawable(boolean z) {
        this(new BlurState(z));
    }

    public GLBlurDrawable() {
        this(1.0f);
    }

    public GLBlurDrawable(float f) {
        this(new BlurState(false));
        getParameter().setLevel(f);
    }

    protected GLBlurDrawable(BlurState blurState) {
        this.mState = blurState;
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
        Rect bounds = getBounds();
        int filterColor = getParameter().getFilterColor();
        if (!canvas.isHardwareAccelerated() || !isTakeEffect()) {
            if (this.mPaint == null) {
                this.mPaint = new Paint();
            }
            int alpha = (int) ((((float) getAlpha()) * ((float) Color.alpha(filterColor))) / 255.0f);
            this.mPaint.setColor(filterColor | ViewCompat.MEASURED_STATE_MASK);
            this.mPaint.setAlpha(alpha);
            canvas.drawRect(bounds, this.mPaint);
        } else if (bounds.isEmpty()) {
            this.mState.mDrawGLFunctor.draw(canvas);
        } else {
            this.mState.mDrawGLFunctor.draw(canvas, bounds.left, bounds.top, bounds.right, bounds.bottom);
        }
    }

    public boolean isTakeEffect() {
        return !(GLRenderManager.isDisableViewBlur() || GLRenderManager.isDisableBlur()) || this.mState.mForce;
    }

    public void setForce(boolean z) {
        if (this.mState.mForce != z) {
            this.mState.mForce = z;
            invalidateSelf();
        }
    }

    public boolean getForce() {
        return this.mState.mForce;
    }

    public float getBlurLevel() {
        return getParameter().getLevel();
    }

    public void setColorFilter(int i) {
        if (getParameter().getFilterColor() != i) {
            getParameter().setFilterColor(i);
            invalidateSelf();
        }
    }

    public void setShareTexture(boolean z) {
        this.mState.mDrawGLFunctor.setShareTexture(z);
    }

    public void setAlwaysIngoreLayer(boolean z) {
        this.mState.mDrawGLFunctor.setAlwaysIngoreLayer(z);
    }

    public void setRenderMode(int i) {
        this.mState.mDrawGLFunctor.setRenderMode(i);
    }

    public int getRenderMode() {
        return this.mState.mDrawGLFunctor.getRenderMode();
    }

    public void setProgressBlur(boolean z) {
        getParameter().setProgressBlur(z);
    }

    public boolean getProgressBlur() {
        return getParameter().getProgressBlur();
    }

    public void setAlpha(int i) {
        if (getAlpha() != i) {
            this.mState.mDrawGLFunctor.setAlpha(i);
            invalidateSelf();
        }
    }

    public int getAlpha() {
        return this.mState.mDrawGLFunctor.getAlpha();
    }

    public void setBlurLevel(float f) {
        float round = ((float) Math.round(f * 100.0f)) / 100.0f;
        if (getParameter().getLevel() != round) {
            getParameter().setLevel(round);
            invalidateSelf();
        }
    }

    public void setRadius(int i) {
        if (getParameter().getRadius() != i) {
            getParameter().setRadius(i);
            invalidateSelf();
        }
    }

    public void setPassCount(int i) {
        int min = Math.min(10, i);
        if (getParameter().getPassCount() != min) {
            getParameter().setPassCount(min);
            invalidateSelf();
        }
    }

    @Deprecated
    public void setMinScale(float f) {
        setScale(f);
    }

    @Deprecated
    public void setMaxScale(float f) {
    }

    public void setScale(float f) {
        if (getParameter().getScale() != f) {
            getParameter().setScale(f);
            invalidateSelf();
        }
    }

    public void setIntensity(float f) {
        if (getParameter().getIntensity() != f) {
            getParameter().setIntensity(f);
            invalidateSelf();
        }
    }

    public void setColorFilter(int i, Mode mode) {
        setColorFilter(i);
    }

    public BlurParameters getParameter() {
        return this.mState.mDrawGLFunctor.getParameters();
    }

    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (visible && !z) {
            this.mState.mDrawGLFunctor.onGone();
        }
        return visible;
    }

    public int getOpacity() {
        return this.mState.mDrawGLFunctor.getAlpha() == 255 ? -1 : -3;
    }

    public ConstantState getConstantState() {
        this.mState.mChangingConfigurations = getChangingConfigurations();
        return this.mState;
    }

    public void recycle() {
        this.mState.mDrawGLFunctor.onTrimMemory(39);
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }
}
