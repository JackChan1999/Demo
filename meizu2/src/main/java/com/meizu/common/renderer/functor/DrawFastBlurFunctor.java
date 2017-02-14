package com.meizu.common.renderer.functor;

import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLCanvasImpl;
import com.meizu.common.renderer.effect.cache.BasicTextureCache;
import com.meizu.common.renderer.effect.render.FastBlurRender;
import com.meizu.common.renderer.effect.render.Render;
import com.meizu.common.renderer.effect.texture.BasicTexture;
import com.meizu.common.renderer.functor.DrawGLFunctor.GLInfo;

public class DrawFastBlurFunctor extends AbstractBlurFunctor {
    protected BasicTexture mOutputTexture;

    public DrawFastBlurFunctor() {
        this.mEffectKey = FastBlurRender.FAST_BLUR;
    }

    public FastBlurRender getRender(GLCanvas gLCanvas) {
        Render render = gLCanvas.getRender(FastBlurRender.FAST_BLUR);
        if (render == null) {
            render = new FastBlurRender(gLCanvas);
            gLCanvas.addRender(render);
        }
        return (FastBlurRender) render;
    }

    public void setEffect(String str) {
        this.mEffectKey = FastBlurRender.FAST_BLUR;
    }

    protected void drawTexture(GLCanvasImpl gLCanvasImpl, GLInfo gLInfo) {
        this.mTextureElement.init(this.mInputTexture, this.mSourceBounds.left, this.mSourceBounds.top, this.mSourceBounds.width(), this.mSourceBounds.height());
        this.mRenderInfo.flipTextureV = true;
        this.mRenderInfo.flipProjV = true;
        this.mRenderInfo.blend = isBlend(this.mInputTexture);
        this.mRenderInfo.alpha = this.mAlpha;
        this.mRenderInfo.viewportWidth = gLInfo.viewportWidth;
        this.mRenderInfo.viewportHeight = gLInfo.viewportHeight;
        this.mRenderInfo.element = this.mTextureElement;
        this.mRenderInfo.effectKey = this.mEffectKey;
        FastBlurRender render = getRender((GLCanvas) gLCanvasImpl);
        render.setParameters(this.mParameters);
        if ((isStaticMode() && (this.mRequestCopyTexture || this.mOutputTexture == null)) || !isStaticMode()) {
            BasicTextureCache.getInstance().put(this.mOutputTexture);
            this.mOutputTexture = render.blur(this.mRenderInfo);
        }
        if (this.mOutputTexture != null) {
            this.mTextureElement.mTexture = this.mOutputTexture;
        }
        render.drawBlur(this.mRenderInfo);
        if (!isStaticMode()) {
            BasicTextureCache.getInstance().put(this.mOutputTexture);
            this.mOutputTexture = null;
        }
        if (this.mShareTexture && isContinuousMode()) {
            BasicTextureCache.getInstance().put(this.mInputTexture);
            this.mInputTexture = null;
        }
        this.mRenderInfo.reset();
        this.mRequestCopyTexture = false;
    }

    protected void recycle() {
        super.recycle();
        if (this.mOutputTexture != null) {
            BasicTextureCache.getInstance().put(this.mOutputTexture);
            this.mOutputTexture = null;
        }
    }
}
