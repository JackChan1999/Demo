package com.meizu.common.renderer.drawable;

import android.graphics.Bitmap;
import android.graphics.PorterDuff.Mode;
import com.meizu.common.renderer.drawable.GLBitmapDrawable.BitmapState;
import com.meizu.common.renderer.effect.EGLBitmap;
import com.meizu.common.renderer.effect.parameters.BlurParameters;
import com.meizu.common.renderer.functor.DrawBlurBitmapFunctor;

public class GLBlurBitmapDrawable extends GLBitmapDrawable {
    private BlurBitmapState mMyState;

    static final class BlurBitmapState extends BitmapState {
        DrawBlurBitmapFunctor mMyDrawGLFunctor;

        BlurBitmapState(EGLBitmap eGLBitmap) {
            super(eGLBitmap);
        }

        BlurBitmapState(Bitmap bitmap) {
            super(bitmap);
        }

        BlurBitmapState(int i) {
            super(i);
        }

        BlurBitmapState(BlurBitmapState blurBitmapState) {
            super((BitmapState) blurBitmapState);
            this.mMyDrawGLFunctor.getParameters().copyFrom(blurBitmapState.mMyDrawGLFunctor.getParameters());
        }

        public GLBlurBitmapDrawable newDrawable() {
            return new GLBlurBitmapDrawable(new BlurBitmapState(this));
        }

        protected void newGLFunctor() {
            if (this.mContentType == 0) {
                this.mMyDrawGLFunctor = new DrawBlurBitmapFunctor(this.mResId);
            } else if (this.mContentType == 1) {
                this.mMyDrawGLFunctor = new DrawBlurBitmapFunctor(this.mBitmap);
            } else if (this.mContentType == 2) {
                this.mMyDrawGLFunctor = new DrawBlurBitmapFunctor(this.mEGLBitmap);
            }
            this.mDrawGLFunctor = this.mMyDrawGLFunctor;
        }
    }

    public GLBlurBitmapDrawable(EGLBitmap eGLBitmap) {
        this(new BlurBitmapState(eGLBitmap));
    }

    public GLBlurBitmapDrawable(Bitmap bitmap) {
        this(new BlurBitmapState(bitmap));
    }

    public GLBlurBitmapDrawable(int i) {
        this(new BlurBitmapState(i));
    }

    protected GLBlurBitmapDrawable(BlurBitmapState blurBitmapState) {
        super((BitmapState) blurBitmapState);
        this.mMyState = blurBitmapState;
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
        if (getParameter().getPassCount() != i) {
            getParameter().setPassCount(i);
            invalidateSelf();
        }
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

    public void setColorFilter(int i) {
        if (getParameter().getFilterColor() != i) {
            getParameter().setFilterColor(i);
            invalidateSelf();
        }
    }

    public void setProgressBlur(boolean z) {
        getParameter().setProgressBlur(z);
    }

    public boolean getProgressBlur() {
        return getParameter().getProgressBlur();
    }

    public void setColorFilter(int i, Mode mode) {
        setColorFilter(i);
    }

    public BlurParameters getParameter() {
        return this.mMyState.mMyDrawGLFunctor.getParameters();
    }

    public void recycle() {
        this.mMyState.mDrawGLFunctor.onTrimMemory(39);
    }
}
