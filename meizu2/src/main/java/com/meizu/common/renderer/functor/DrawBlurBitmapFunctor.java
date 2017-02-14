package com.meizu.common.renderer.functor;

import android.graphics.Bitmap;
import com.meizu.common.renderer.effect.EGLBitmap;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLCanvasImpl;
import com.meizu.common.renderer.effect.parameters.BlurParameters;
import com.meizu.common.renderer.effect.render.BlurEffectRender;
import com.meizu.common.renderer.effect.render.Render;

public class DrawBlurBitmapFunctor extends DrawBitmapFunctor {
    private BlurParameters mParameters;

    public void init() {
        this.mEffectKey = Render.BLUR;
        this.mParameters = new BlurParameters();
    }

    public DrawBlurBitmapFunctor(int i) {
        super(i);
        init();
    }

    public DrawBlurBitmapFunctor(Bitmap bitmap) {
        super(bitmap);
        init();
    }

    public DrawBlurBitmapFunctor(EGLBitmap eGLBitmap) {
        super(eGLBitmap);
        init();
    }

    public BlurEffectRender getRender(GLCanvas gLCanvas) {
        return (BlurEffectRender) gLCanvas.getRender(Render.BLUR);
    }

    public void setParameters(BlurParameters blurParameters) {
        this.mParameters.copyFrom(blurParameters);
    }

    public BlurParameters getParameters() {
        return this.mParameters;
    }

    protected void onPreDraw(GLCanvasImpl gLCanvasImpl) {
        super.onPreDraw(gLCanvasImpl);
        getRender((GLCanvas) gLCanvasImpl).setParameters(this.mParameters);
    }
}
