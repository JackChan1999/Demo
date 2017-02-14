package com.meizu.common.renderer.functor;

import com.meizu.common.renderer.effect.GLCanvasImpl;
import com.meizu.common.renderer.effect.render.ETC1Render;
import com.meizu.common.renderer.effect.texture.ETC1BitmapTextureHolder;

public class DrawETC1BitmapFunctor extends DrawBitmapFunctor {
    private ETC1BitmapTextureHolder mETC1TextureHolder = new ETC1BitmapTextureHolder();
    private boolean mTexHasAlpha;

    public DrawETC1BitmapFunctor(int i, int i2) {
        this.mETC1TextureHolder.setImageResource(i, i2);
        this.mTexHasAlpha = i2 != 0;
        if (this.mTexHasAlpha) {
            this.mEffectKey = ETC1Render.ETC1;
        }
        this.mTextureHolder = this.mETC1TextureHolder;
    }

    public void setEffect(String str) {
        if (!this.mTexHasAlpha) {
            super.setEffect(str);
        }
    }

    protected void onPreDraw(GLCanvasImpl gLCanvasImpl) {
        super.onPreDraw(gLCanvasImpl);
        if (this.mTexHasAlpha) {
            ETC1Render eTC1Render = (ETC1Render) gLCanvasImpl.getRender(ETC1Render.ETC1);
            if (eTC1Render == null) {
                eTC1Render = new ETC1Render(gLCanvasImpl);
                gLCanvasImpl.addRender(eTC1Render);
            }
            eTC1Render.setAlphaTexture(this.mETC1TextureHolder.getAlphaTexture());
        }
    }

    protected void onPostDraw(GLCanvasImpl gLCanvasImpl) {
        super.onPostDraw(gLCanvasImpl);
        if (this.mTexHasAlpha) {
            ((ETC1Render) gLCanvasImpl.getRender(ETC1Render.ETC1)).setAlphaTexture(null);
        }
    }
}
