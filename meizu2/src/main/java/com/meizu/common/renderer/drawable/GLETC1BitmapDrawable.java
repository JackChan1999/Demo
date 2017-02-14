package com.meizu.common.renderer.drawable;

import com.meizu.common.renderer.drawable.GLBitmapDrawable.BitmapState;
import com.meizu.common.renderer.functor.DrawETC1BitmapFunctor;

public class GLETC1BitmapDrawable extends GLBitmapDrawable {
    private ETC1BitmapState mMyState;

    static class ETC1BitmapState extends BitmapState {
        int mAlphaResId;
        DrawETC1BitmapFunctor mMyDrawGLFunctor;

        ETC1BitmapState(int i, int i2) {
            super(i);
            this.mAlphaResId = i2;
        }

        ETC1BitmapState(ETC1BitmapState eTC1BitmapState) {
            super((BitmapState) eTC1BitmapState);
            this.mAlphaResId = eTC1BitmapState.mAlphaResId;
        }

        protected void newGLFunctor() {
            this.mMyDrawGLFunctor = new DrawETC1BitmapFunctor(this.mResId, this.mAlphaResId);
            this.mDrawGLFunctor = this.mMyDrawGLFunctor;
        }

        public GLETC1BitmapDrawable newDrawable() {
            return new GLETC1BitmapDrawable(this.mResId, this.mAlphaResId);
        }
    }

    public GLETC1BitmapDrawable(int i, int i2) {
        this(new ETC1BitmapState(i, i2));
    }

    public GLETC1BitmapDrawable(int i) {
        this(i, 0);
    }

    protected GLETC1BitmapDrawable(ETC1BitmapState eTC1BitmapState) {
        super((BitmapState) eTC1BitmapState);
        this.mMyState = (ETC1BitmapState) this.mState;
    }

    public int getAlphaResId() {
        return this.mMyState.mAlphaResId;
    }
}
