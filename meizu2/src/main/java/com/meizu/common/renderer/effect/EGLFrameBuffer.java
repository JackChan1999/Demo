package com.meizu.common.renderer.effect;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import com.meizu.common.renderer.effect.texture.EGLBitmapTexture;

public class EGLFrameBuffer extends FrameBuffer {
    public EGLFrameBuffer(GLCanvas gLCanvas, int i, int i2) {
        super(gLCanvas, i, i2);
    }

    public EGLFrameBuffer(GLCanvas gLCanvas, int i, int i2, boolean z) {
        super(gLCanvas, i, i2, z);
    }

    public EGLFrameBuffer(GLCanvas gLCanvas, EGLBitmap eGLBitmap) {
        super(gLCanvas);
        this.mTexture = new EGLBitmapTexture(eGLBitmap);
        this.mTexture.onBind(gLCanvas);
        GLES20.glGenFramebuffers(1, this.mFrameBufferID, 0);
        GLES20.glBindFramebuffer(36160, this.mFrameBufferID[0]);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.mTexture.getId(), 0);
        this.mRenderBufferID[0] = 0;
        GLES20.glBindFramebuffer(36160, gLCanvas.getState().getFrameBufferId());
    }

    public void updateBitmap(GLCanvas gLCanvas, EGLBitmap eGLBitmap) {
        EGLBitmapTexture eGLBitmapTexture = (EGLBitmapTexture) this.mTexture;
        if (eGLBitmap != null && eGLBitmap.isValid() && !eGLBitmap.equals(eGLBitmapTexture.getEGLBitmap())) {
            eGLBitmapTexture.setEGLBitmap(eGLBitmap);
            eGLBitmapTexture.onBind(gLCanvas);
        }
    }

    public boolean isEGL() {
        return true;
    }

    protected EGLBitmapTexture newTexure(int i, int i2) {
        return new EGLBitmapTexture(i, i2);
    }

    public void fillBitmap(Bitmap bitmap) {
        ((EGLBitmapTexture) this.mTexture).getEGLBitmap().fillBitmap(bitmap);
    }
}
