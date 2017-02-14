package com.meizu.common.renderer.functor;

import com.meizu.common.renderer.effect.FrameBuffer;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLCanvasImpl;
import com.meizu.common.renderer.effect.cache.BasicTextureCache;
import com.meizu.common.renderer.effect.render.BlurEffectRender;
import com.meizu.common.renderer.effect.render.Render;
import com.meizu.common.renderer.functor.DrawGLFunctor.GLInfo;

public class DrawBlurFunctor extends AbstractBlurFunctor {
    protected FrameBuffer mFrameBuffer;

    public DrawBlurFunctor() {
        this.mEffectKey = Render.BLUR;
    }

    public void setEffect(String str) {
        this.mEffectKey = Render.BLUR;
    }

    public BlurEffectRender getRender(GLCanvas gLCanvas) {
        return (BlurEffectRender) gLCanvas.getRender(Render.BLUR);
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
        BlurEffectRender render = getRender((GLCanvas) gLCanvasImpl);
        render.setParameters(this.mParameters);
        if ((isStaticMode() && (this.mRequestCopyTexture || this.mFrameBuffer == null)) || !isStaticMode()) {
            gLCanvasImpl.getFrameBufferCache().put(this.mFrameBuffer);
            this.mFrameBuffer = render.blur(this.mTextureElement);
        }
        if (this.mFrameBuffer != null) {
            this.mTextureElement.mTexture = this.mFrameBuffer.getTexture();
        }
        render.drawBlur(this.mRenderInfo);
        if (!isStaticMode()) {
            gLCanvasImpl.getFrameBufferCache().put(this.mFrameBuffer);
            this.mFrameBuffer = null;
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
        if (this.mFrameBuffer != null) {
            this.mFrameBuffer.freeGLResource();
            this.mFrameBuffer = null;
        }
    }
}
