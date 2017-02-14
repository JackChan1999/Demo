package com.meizu.common.renderer.functor;

import android.graphics.Bitmap;
import com.meizu.common.renderer.effect.EGLBitmap;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLCanvasImpl;
import com.meizu.common.renderer.effect.render.RCEffectRender;
import com.meizu.common.renderer.effect.render.Render;

public class DrawRCBitmapFunctor extends DrawBitmapFunctor {
    private float mRadius;

    public DrawRCBitmapFunctor(int i) {
        super(i);
    }

    public DrawRCBitmapFunctor(Bitmap bitmap) {
        super(bitmap);
    }

    public DrawRCBitmapFunctor(EGLBitmap eGLBitmap) {
        super(eGLBitmap);
    }

    public void setRadius(float f) {
        this.mRadius = f;
    }

    public RCEffectRender getRender(GLCanvas gLCanvas) {
        RCEffectRender rCEffectRender = (RCEffectRender) gLCanvas.getRender(RCEffectRender.ROUND_CORNER);
        if (rCEffectRender != null) {
            return rCEffectRender;
        }
        Render rCEffectRender2 = new RCEffectRender(gLCanvas);
        gLCanvas.addRender(rCEffectRender2);
        return rCEffectRender2;
    }

    protected void onPreDraw(GLCanvasImpl gLCanvasImpl) {
        super.onPreDraw(gLCanvasImpl);
        getRender((GLCanvas) gLCanvasImpl).setRadius(this.mRadius);
    }
}
