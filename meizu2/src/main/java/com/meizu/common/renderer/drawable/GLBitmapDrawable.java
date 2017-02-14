package com.meizu.common.renderer.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import com.meizu.common.renderer.effect.EGLBitmap;
import com.meizu.common.renderer.functor.DrawBitmapFunctor;

public class GLBitmapDrawable extends Drawable {
    protected BitmapState mState;

    public static class BitmapState extends ConstantState {
        Bitmap mBitmap;
        int mChangingConfigurations;
        int mContentType = -1;
        DrawBitmapFunctor mDrawGLFunctor;
        EGLBitmap mEGLBitmap;
        int mResId;

        BitmapState(EGLBitmap eGLBitmap) {
            setEGLBitmap(eGLBitmap);
            newGLFunctor();
        }

        BitmapState(Bitmap bitmap) {
            setBitmap(bitmap);
            newGLFunctor();
        }

        BitmapState(int i) {
            setResId(i);
            newGLFunctor();
        }

        BitmapState(BitmapState bitmapState) {
            setEGLBitmap(bitmapState.mEGLBitmap);
            setBitmap(bitmapState.mBitmap);
            setResId(bitmapState.mResId);
            newGLFunctor();
            this.mDrawGLFunctor.setEffect(bitmapState.mDrawGLFunctor.getEffect());
            this.mDrawGLFunctor.setAlpha(bitmapState.mDrawGLFunctor.getAlpha());
            this.mChangingConfigurations = bitmapState.mChangingConfigurations;
        }

        private void setEGLBitmap(EGLBitmap eGLBitmap) {
            if (eGLBitmap != null) {
                this.mEGLBitmap = eGLBitmap;
                this.mBitmap = null;
                this.mResId = 0;
                this.mContentType = 2;
            }
        }

        private void setBitmap(Bitmap bitmap) {
            if (bitmap != null) {
                this.mEGLBitmap = null;
                this.mBitmap = bitmap;
                this.mResId = 0;
                this.mContentType = 1;
            }
        }

        private void setResId(int i) {
            if (i != 0) {
                this.mEGLBitmap = null;
                this.mBitmap = null;
                this.mResId = i;
                this.mContentType = 0;
            }
        }

        protected void newGLFunctor() {
            if (this.mContentType == 0) {
                this.mDrawGLFunctor = new DrawBitmapFunctor(this.mResId);
            } else if (this.mContentType == 1) {
                this.mDrawGLFunctor = new DrawBitmapFunctor(this.mBitmap);
            } else if (this.mContentType == 2) {
                this.mDrawGLFunctor = new DrawBitmapFunctor(this.mEGLBitmap);
            }
        }

        public GLBitmapDrawable newDrawable() {
            return new GLBitmapDrawable(new BitmapState(this));
        }

        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }
    }

    public GLBitmapDrawable(EGLBitmap eGLBitmap) {
        this(new BitmapState(eGLBitmap));
    }

    public GLBitmapDrawable(Bitmap bitmap) {
        this(new BitmapState(bitmap));
    }

    public GLBitmapDrawable(int i) {
        this(new BitmapState(i));
    }

    protected GLBitmapDrawable(BitmapState bitmapState) {
        this.mState = bitmapState;
    }

    public int getIntrinsicWidth() {
        return this.mState.mDrawGLFunctor.getWidth();
    }

    public int getIntrinsicHeight() {
        return this.mState.mDrawGLFunctor.getHeight();
    }

    public void setEffect(String str) {
        if (str != null && !getEffect().equals(str)) {
            this.mState.mDrawGLFunctor.setEffect(str);
            invalidateSelf();
        }
    }

    public String getEffect() {
        return this.mState.mDrawGLFunctor.getEffect();
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

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return getAlpha() == 255 ? -1 : -3;
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

    public ConstantState getConstantState() {
        this.mState.mChangingConfigurations = getChangingConfigurations();
        return this.mState;
    }

    public void recycle() {
        this.mState.mDrawGLFunctor.onTrimMemory(39);
    }
}
