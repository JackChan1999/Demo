package com.meizu.common.renderer.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import com.meizu.common.renderer.effect.GLRenderManager;
import com.meizu.common.renderer.functor.DrawBlurWindowFunctor;

public class GLBlurWindowDrawable extends Drawable {
    private BlurWindowState mState;

    public static class BlurWindowState extends ConstantState {
        int mChangingConfigurations;
        DrawBlurWindowFunctor mDrawGLFunctor;
        boolean mForce = false;

        BlurWindowState(int i) {
            this.mDrawGLFunctor = new DrawBlurWindowFunctor(i);
        }

        BlurWindowState(BlurWindowState blurWindowState) {
            this.mForce = blurWindowState.mForce;
            this.mChangingConfigurations = blurWindowState.mChangingConfigurations;
            this.mDrawGLFunctor = new DrawBlurWindowFunctor(blurWindowState.mDrawGLFunctor.getFormat());
            this.mDrawGLFunctor.setAlpha(blurWindowState.mDrawGLFunctor.getAlpha());
        }

        public Drawable newDrawable() {
            return new GLBlurWindowDrawable(new BlurWindowState(this));
        }

        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }
    }

    public GLBlurWindowDrawable() {
        this(new BlurWindowState(4));
    }

    public GLBlurWindowDrawable(int i) {
        this(new BlurWindowState(i));
    }

    private GLBlurWindowDrawable(BlurWindowState blurWindowState) {
        this.mState = blurWindowState;
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mState.mChangingConfigurations;
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        if (bounds.isEmpty()) {
            this.mState.mDrawGLFunctor.draw(canvas);
            return;
        }
        this.mState.mDrawGLFunctor.draw(canvas, bounds.left, bounds.top, bounds.right, bounds.bottom);
    }

    public boolean isTakeEffect() {
        return !GLRenderManager.isDisableBlur() || this.mState.mForce;
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

    public void setIntensity(float f) {
        if (this.mState.mDrawGLFunctor.getIntensity() != f) {
            this.mState.mDrawGLFunctor.setIntensity(f);
            invalidateSelf();
        }
    }

    public void setAlpha(int i) {
        if (this.mState.mDrawGLFunctor.getAlpha() != i) {
            this.mState.mDrawGLFunctor.setAlpha(i);
            invalidateSelf();
        }
    }

    public void setVisible(boolean z) {
        this.mState.mDrawGLFunctor.setVisible(z);
    }

    public int getAlpha() {
        return 255;
    }

    public int getOpacity() {
        return -1;
    }

    public ConstantState getConstantState() {
        this.mState.mChangingConfigurations = getChangingConfigurations();
        return this.mState;
    }

    public void reclaimMemory() {
        this.mState.mDrawGLFunctor.reclaimMemory();
    }

    public void update() {
        this.mState.mDrawGLFunctor.screenshot(0, -1);
    }

    public void update(int i, int i2) {
        this.mState.mDrawGLFunctor.screenshot(i, i2);
    }

    public void update(int i, int i2, int i3, int i4) {
        this.mState.mDrawGLFunctor.screenshot(i, i2, i3, i4);
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }
}
