package com.meizu.common.renderer.functor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.meizu.common.renderer.effect.EGLBitmap;
import com.meizu.common.renderer.effect.GLCanvasImpl;
import com.meizu.common.renderer.effect.GLRenderManager;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.renderer.effect.element.TextureElement;
import com.meizu.common.renderer.effect.texture.BasicTextureHolder;
import com.meizu.common.renderer.effect.texture.BitmapTextureHolder;
import com.meizu.common.renderer.effect.texture.EGLBitmapTextureHolder;

public class DrawBitmapFunctor extends DrawGLFunctor {
    protected RenderInfo mRenderInfo;
    protected TextureElement mTextureElement;
    protected BasicTextureHolder mTextureHolder;

    protected DrawBitmapFunctor() {
        this.mTextureElement = new TextureElement();
        this.mRenderInfo = new RenderInfo();
    }

    public DrawBitmapFunctor(int i) {
        this.mTextureElement = new TextureElement();
        this.mRenderInfo = new RenderInfo();
        this.mTextureHolder = new BitmapTextureHolder();
        this.mTextureHolder.setImageResource(i);
    }

    public DrawBitmapFunctor(Bitmap bitmap) {
        this.mTextureElement = new TextureElement();
        this.mRenderInfo = new RenderInfo();
        if (GLRenderManager.isSupprotedEGLBitmap()) {
            this.mTextureHolder = new EGLBitmapTextureHolder();
        } else {
            this.mTextureHolder = new BitmapTextureHolder();
        }
        this.mTextureHolder.setBitmap(bitmap);
    }

    public DrawBitmapFunctor(EGLBitmap eGLBitmap) {
        this.mTextureElement = new TextureElement();
        this.mRenderInfo = new RenderInfo();
        BasicTextureHolder eGLBitmapTextureHolder = new EGLBitmapTextureHolder();
        eGLBitmapTextureHolder.setEGLBitmap(eGLBitmap);
        this.mTextureHolder = eGLBitmapTextureHolder;
    }

    public int getWidth() {
        return this.mTextureHolder == null ? 0 : this.mTextureHolder.getWidth();
    }

    public int getHeight() {
        return this.mTextureHolder == null ? 0 : this.mTextureHolder.getHeight();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas, 0, 0, getWidth(), getHeight());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void onDraw(GLInfo r8) {
        /*
        r7 = this;
        monitor-enter(r7);
        r0 = r7.mTextureHolder;	 Catch:{ all -> 0x0021 }
        if (r0 == 0) goto L_0x000d;
    L_0x0005:
        r0 = r7.mTextureHolder;	 Catch:{ all -> 0x0021 }
        r0 = r0.isValid();	 Catch:{ all -> 0x0021 }
        if (r0 != 0) goto L_0x000f;
    L_0x000d:
        monitor-exit(r7);	 Catch:{ all -> 0x0021 }
    L_0x000e:
        return;
    L_0x000f:
        r0 = com.meizu.common.renderer.effect.GLRenderManager.getInstance();	 Catch:{ all -> 0x0021 }
        r6 = r0.getCanvas();	 Catch:{ all -> 0x0021 }
        r0 = r7.mTextureHolder;	 Catch:{ all -> 0x0021 }
        r0 = r0.updateTexture(r6);	 Catch:{ all -> 0x0021 }
        if (r0 != 0) goto L_0x0024;
    L_0x001f:
        monitor-exit(r7);	 Catch:{ all -> 0x0021 }
        goto L_0x000e;
    L_0x0021:
        r0 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x0021 }
        throw r0;
    L_0x0024:
        r6.onRenderPreDraw(r8);	 Catch:{ all -> 0x0021 }
        r7.onPreDraw(r6);	 Catch:{ all -> 0x0021 }
        r0 = r7.mTextureHolder;	 Catch:{ all -> 0x0021 }
        r0 = r0.getTexture();	 Catch:{ all -> 0x0021 }
        if (r0 == 0) goto L_0x0094;
    L_0x0032:
        r0 = r7.mTextureHolder;	 Catch:{ all -> 0x0021 }
        r0 = r0.getTexture();	 Catch:{ all -> 0x0021 }
        r0.resetBounds();	 Catch:{ all -> 0x0021 }
        r0 = r7.mTextureElement;	 Catch:{ all -> 0x0021 }
        r1 = r7.mTextureHolder;	 Catch:{ all -> 0x0021 }
        r1 = r1.getTexture();	 Catch:{ all -> 0x0021 }
        r2 = r7.mSourceBounds;	 Catch:{ all -> 0x0021 }
        r2 = r2.left;	 Catch:{ all -> 0x0021 }
        r3 = r7.mSourceBounds;	 Catch:{ all -> 0x0021 }
        r3 = r3.top;	 Catch:{ all -> 0x0021 }
        r4 = r7.mSourceBounds;	 Catch:{ all -> 0x0021 }
        r4 = r4.width();	 Catch:{ all -> 0x0021 }
        r5 = r7.mSourceBounds;	 Catch:{ all -> 0x0021 }
        r5 = r5.height();	 Catch:{ all -> 0x0021 }
        r0.init(r1, r2, r3, r4, r5);	 Catch:{ all -> 0x0021 }
        r0 = r7.mRenderInfo;	 Catch:{ all -> 0x0021 }
        r1 = 1;
        r0.flipProjV = r1;	 Catch:{ all -> 0x0021 }
        r0 = r7.mRenderInfo;	 Catch:{ all -> 0x0021 }
        r1 = r7.mTextureHolder;	 Catch:{ all -> 0x0021 }
        r1 = r1.getTexture();	 Catch:{ all -> 0x0021 }
        r1 = r7.isBlend(r1);	 Catch:{ all -> 0x0021 }
        r0.blend = r1;	 Catch:{ all -> 0x0021 }
        r0 = r7.mRenderInfo;	 Catch:{ all -> 0x0021 }
        r1 = r7.mAlpha;	 Catch:{ all -> 0x0021 }
        r0.alpha = r1;	 Catch:{ all -> 0x0021 }
        r0 = r7.mRenderInfo;	 Catch:{ all -> 0x0021 }
        r1 = r8.viewportWidth;	 Catch:{ all -> 0x0021 }
        r0.viewportWidth = r1;	 Catch:{ all -> 0x0021 }
        r0 = r7.mRenderInfo;	 Catch:{ all -> 0x0021 }
        r1 = r8.viewportHeight;	 Catch:{ all -> 0x0021 }
        r0.viewportHeight = r1;	 Catch:{ all -> 0x0021 }
        r0 = r7.mRenderInfo;	 Catch:{ all -> 0x0021 }
        r1 = r7.mTextureElement;	 Catch:{ all -> 0x0021 }
        r0.element = r1;	 Catch:{ all -> 0x0021 }
        r0 = r7.mRenderInfo;	 Catch:{ all -> 0x0021 }
        r1 = r7.mEffectKey;	 Catch:{ all -> 0x0021 }
        r0.effectKey = r1;	 Catch:{ all -> 0x0021 }
        r0 = r7.getRender(r6);	 Catch:{ all -> 0x0021 }
        r1 = r7.mRenderInfo;	 Catch:{ all -> 0x0021 }
        r0.draw(r1);	 Catch:{ all -> 0x0021 }
    L_0x0094:
        r7.onPostDraw(r6);	 Catch:{ all -> 0x0021 }
        r6.onRenderPostDraw();	 Catch:{ all -> 0x0021 }
        r0 = r7.mTextureElement;	 Catch:{ all -> 0x0021 }
        r1 = 0;
        r0.mTexture = r1;	 Catch:{ all -> 0x0021 }
        r0 = r7.mRenderInfo;	 Catch:{ all -> 0x0021 }
        r0.reset();	 Catch:{ all -> 0x0021 }
        monitor-exit(r7);	 Catch:{ all -> 0x0021 }
        goto L_0x000e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.common.renderer.functor.DrawBitmapFunctor.onDraw(com.meizu.common.renderer.functor.DrawGLFunctor$GLInfo):void");
    }

    public void onTrimMemory(int i) {
        synchronized (this) {
            super.onTrimMemory(i);
            this.mTextureHolder.onTrimMemory(i);
        }
    }

    protected void onPreDraw(GLCanvasImpl gLCanvasImpl) {
    }

    protected void onPostDraw(GLCanvasImpl gLCanvasImpl) {
    }
}
