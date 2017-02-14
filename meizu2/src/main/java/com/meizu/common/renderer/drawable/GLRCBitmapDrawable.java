package com.meizu.common.renderer.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.meizu.common.renderer.drawable.GLBitmapDrawable.BitmapState;
import com.meizu.common.renderer.effect.EGLBitmap;
import com.meizu.common.renderer.functor.DrawRCBitmapFunctor;

public class GLRCBitmapDrawable extends GLBitmapDrawable {
    private RCBitmapState mMyState;

    static final class RCBitmapState extends BitmapState {
        DrawRCBitmapFunctor mMyDrawGLFunctor;
        float mRadius = 10.0f;

        RCBitmapState(EGLBitmap eGLBitmap) {
            super(eGLBitmap);
        }

        RCBitmapState(Bitmap bitmap) {
            super(bitmap);
        }

        RCBitmapState(int i) {
            super(i);
        }

        RCBitmapState(RCBitmapState rCBitmapState) {
            super((BitmapState) rCBitmapState);
            this.mRadius = rCBitmapState.mRadius;
        }

        protected void newGLFunctor() {
            if (this.mContentType == 0) {
                this.mMyDrawGLFunctor = new DrawRCBitmapFunctor(this.mResId);
            } else if (this.mContentType == 1) {
                this.mMyDrawGLFunctor = new DrawRCBitmapFunctor(this.mBitmap);
            } else if (this.mContentType == 2) {
                this.mMyDrawGLFunctor = new DrawRCBitmapFunctor(this.mEGLBitmap);
            }
            this.mDrawGLFunctor = this.mMyDrawGLFunctor;
        }

        public GLRCBitmapDrawable newDrawable() {
            return new GLRCBitmapDrawable(new RCBitmapState(this));
        }
    }

    public GLRCBitmapDrawable(EGLBitmap eGLBitmap) {
        this(new RCBitmapState(eGLBitmap));
    }

    public GLRCBitmapDrawable(Bitmap bitmap) {
        this(new RCBitmapState(bitmap));
    }

    public GLRCBitmapDrawable(int i) {
        this(new RCBitmapState(i));
    }

    protected GLRCBitmapDrawable(RCBitmapState rCBitmapState) {
        super((BitmapState) rCBitmapState);
        this.mMyState = (RCBitmapState) this.mState;
    }

    public void setRadius(float f) {
        if (this.mMyState.mRadius != f) {
            this.mMyState.mRadius = f;
            invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        this.mMyState.mMyDrawGLFunctor.setRadius(this.mMyState.mRadius);
        super.draw(canvas);
    }

    public void recycle() {
        this.mMyState.mDrawGLFunctor.onTrimMemory(39);
    }
}
