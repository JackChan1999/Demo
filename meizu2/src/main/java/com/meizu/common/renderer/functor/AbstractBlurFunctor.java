package com.meizu.common.renderer.functor;

import android.graphics.Rect;
import android.opengl.GLES20;
import com.meizu.common.renderer.Utils;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLCanvasImpl;
import com.meizu.common.renderer.effect.GLRenderManager;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.renderer.effect.cache.BasicTextureCache;
import com.meizu.common.renderer.effect.element.TextureElement;
import com.meizu.common.renderer.effect.parameters.BlurParameters;
import com.meizu.common.renderer.effect.render.Render;
import com.meizu.common.renderer.effect.texture.BasicTexture;
import com.meizu.common.renderer.functor.DrawGLFunctor.GLInfo;

public class AbstractBlurFunctor extends DrawGLFunctor {
    protected boolean mAlwaysIngoreLayer;
    protected Rect mClipBounds = new Rect();
    protected BasicTexture mInputTexture;
    protected final BlurParameters mParameters = new BlurParameters();
    protected RenderInfo mRenderInfo = new RenderInfo();
    protected int mRenderMode = 0;
    protected boolean mRequestCopyTexture = true;
    protected boolean mShareTexture = true;
    protected Rect mTargetBounds = new Rect();
    protected TextureElement mTextureElement = new TextureElement();

    public Render getRender(GLCanvas gLCanvas) {
        return null;
    }

    public void setParameters(BlurParameters blurParameters) {
        this.mParameters.copyFrom(blurParameters);
    }

    public BlurParameters getParameters() {
        return this.mParameters;
    }

    protected void onDraw(GLInfo gLInfo) {
        synchronized (this) {
            GLCanvasImpl canvas = GLRenderManager.getInstance().getCanvas();
            canvas.onRenderPreDraw(gLInfo);
            copyTexture(canvas, gLInfo);
            drawTexture(canvas, gLInfo);
            canvas.onRenderPostDraw();
        }
    }

    protected void drawTexture(GLCanvasImpl gLCanvasImpl, GLInfo gLInfo) {
    }

    protected void copyTexture(GLCanvasImpl gLCanvasImpl, GLInfo gLInfo) {
        if (isContinuousMode() || this.mRequestCopyTexture || this.mInputTexture == null) {
            int width = this.mSourceBounds.width();
            int height = this.mSourceBounds.height();
            if (!(this.mInputTexture != null && this.mInputTexture.getWidth() == width && this.mInputTexture.getHeight() == height)) {
                BasicTextureCache.getInstance().put(this.mInputTexture);
                this.mInputTexture = BasicTextureCache.getInstance().get(width, height, true);
            }
            this.mInputTexture.onBind(gLCanvasImpl);
            this.mInputTexture.bindSelf(33984);
            if (gLInfo.isLayer && this.mAlwaysIngoreLayer) {
                GLES20.glBindFramebuffer(36160, 0);
            }
            Utils.window2View(gLInfo.transform, (float) gLInfo.clipLeft, (float) gLInfo.clipTop, (float) gLInfo.clipRight, (float) gLInfo.clipBottom, this.mClipBounds);
            this.mClipBounds.intersect(this.mSourceBounds);
            Utils.view2Window(gLInfo.transform, this.mClipBounds, this.mTargetBounds);
            GLES20.glCopyTexSubImage2D(3553, 0, Math.abs(this.mClipBounds.left - this.mSourceBounds.left), Math.abs(this.mClipBounds.bottom - this.mSourceBounds.bottom), this.mTargetBounds.left, gLInfo.viewportHeight - this.mTargetBounds.bottom, this.mTargetBounds.width(), this.mTargetBounds.height());
            GLES20.glBindFramebuffer(36160, gLCanvasImpl.getTargetFrameBufferId());
        }
    }

    public void setRenderMode(int i) {
        synchronized (this) {
            if (this.mRenderMode != i) {
                this.mRequestCopyTexture = true;
                this.mRenderMode = i;
            }
        }
    }

    public int getRenderMode() {
        return this.mRenderMode;
    }

    public void setAlwaysIngoreLayer(boolean z) {
        synchronized (this) {
            this.mAlwaysIngoreLayer = z;
        }
    }

    public void setShareTexture(boolean z) {
        synchronized (this) {
            this.mShareTexture = z;
        }
    }

    protected boolean isContinuousMode() {
        return this.mRenderMode == 0;
    }

    protected boolean isStaticMode() {
        return this.mRenderMode == 1;
    }

    public void onGone() {
        synchronized (this) {
            recycle();
        }
    }

    public void onTrimMemory(int i) {
        synchronized (this) {
            super.onTrimMemory(i);
            if (i >= 19) {
                recycle();
            }
        }
    }

    protected void recycle() {
        if (this.mInputTexture != null) {
            BasicTextureCache.getInstance().put(this.mInputTexture);
            this.mInputTexture = null;
        }
    }
}
