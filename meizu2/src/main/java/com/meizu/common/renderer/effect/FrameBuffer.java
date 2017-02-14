package com.meizu.common.renderer.effect;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import com.meizu.common.renderer.Utils;
import com.meizu.common.renderer.effect.texture.BasicTexture;
import com.meizu.common.renderer.effect.texture.RawTexture;

public class FrameBuffer implements GLResource {
    protected boolean mDepth;
    protected int[] mFrameBufferID;
    protected GLCanvas mGLCanvas;
    protected int[] mRenderBufferID;
    protected BasicTexture mTexture;

    public FrameBuffer(GLCanvas gLCanvas, int i, int i2, boolean z) {
        this.mFrameBufferID = new int[1];
        this.mRenderBufferID = new int[1];
        this.mTexture = newTexure(i, i2);
        this.mTexture.onBind(gLCanvas);
        this.mDepth = z;
        GLES20.glGenFramebuffers(1, this.mFrameBufferID, 0);
        GLES20.glBindFramebuffer(36160, this.mFrameBufferID[0]);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.mTexture.getId(), 0);
        this.mRenderBufferID[0] = 0;
        if (this.mDepth) {
            GLES20.glGenRenderbuffers(1, this.mRenderBufferID, 0);
            GLES20.glBindRenderbuffer(36161, this.mRenderBufferID[0]);
            GLES20.glRenderbufferStorage(36161, 33189, i, i2);
            GLES20.glFramebufferRenderbuffer(36160, 36096, 36161, this.mRenderBufferID[0]);
            GLES20.glBindRenderbuffer(36161, 0);
        }
        GLES20.glBindFramebuffer(36160, gLCanvas.getState().getFrameBufferId());
        this.mGLCanvas = gLCanvas;
    }

    public FrameBuffer(GLCanvas gLCanvas, int i, int i2) {
        this(gLCanvas, i, i2, false);
    }

    public void clear(GLCanvas gLCanvas, float f, float f2, float f3, float f4) {
        GLES20.glBindFramebuffer(36160, this.mFrameBufferID[0]);
        GLES20.glClearColor(f, f2, f3, f4);
        GLES20.glClear(16384);
        GLES20.glBindFramebuffer(36160, gLCanvas.getState().getFrameBufferId());
    }

    public int getSize() {
        if (this.mTexture == null) {
            return 0;
        }
        return (this.mDepth ? 6 : 4) * (this.mTexture.getHeight() * this.mTexture.getWidth());
    }

    protected FrameBuffer(GLCanvas gLCanvas) {
        this.mFrameBufferID = new int[1];
        this.mRenderBufferID = new int[1];
        this.mGLCanvas = gLCanvas;
        this.mDepth = false;
    }

    public boolean getDepth() {
        return this.mDepth;
    }

    public int getWidth() {
        return this.mTexture.getWidth();
    }

    public int getHeight() {
        return this.mTexture.getHeight();
    }

    public BasicTexture getTexture() {
        return this.mTexture;
    }

    public void resetTextureBounds() {
        if (this.mTexture != null) {
            this.mTexture.resetBounds();
        }
    }

    public int getId() {
        return this.mFrameBufferID[0];
    }

    public boolean isEGL() {
        return false;
    }

    public void freeGLResource() {
        if (this.mGLCanvas != null) {
            this.mTexture.freeGLResource();
            this.mGLCanvas.deleteFrameBuffer(getId());
            if (this.mDepth && this.mRenderBufferID[0] != 0) {
                this.mGLCanvas.deleteRenderBuffer(this.mRenderBufferID[0]);
                this.mRenderBufferID[0] = 0;
            }
            this.mFrameBufferID[0] = 0;
            this.mTexture = null;
            this.mGLCanvas = null;
        }
    }

    protected void finalize() {
        try {
            if (this.mGLCanvas != null) {
                freeGLResource();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    protected BasicTexture newTexure(int i, int i2) {
        return new RawTexture(i, i2);
    }

    public void fillBitmap(Bitmap bitmap) {
        Utils.glFillBitmap(bitmap);
    }
}
