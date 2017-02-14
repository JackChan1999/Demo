package com.meizu.common.renderer.effect.texture;

import android.opengl.GLES20;
import com.meizu.common.renderer.effect.GLCanvas;

public class RawTexture extends BasicTexture {
    private int mFormat;

    public RawTexture(int i, int i2, boolean z) {
        setSize(i, i2);
        setOpaque(z);
        this.mFormat = z ? 6407 : 6408;
    }

    public RawTexture(int i, int i2) {
        this(i, i2, false);
    }

    private void prepare(GLCanvas gLCanvas) {
        if (!isLoaded()) {
            GLES20.glGenTextures(1, sTextureId, 0);
            BasicTexture.initTexParameter(getTarget(), sTextureId[0]);
            GLES20.glTexImage2D(3553, 0, this.mFormat, getWidth(), getHeight(), 0, this.mFormat, 5121, null);
            this.mId = sTextureId[0];
            this.mState = 1;
            bindCanvas(gLCanvas);
        }
    }

    public int getFormat() {
        return this.mFormat;
    }

    public void setSize(int i, int i2) {
        if (!(this.mWidth == i && this.mHeight == i2)) {
            freeGLResource();
        }
        super.setSize(i, i2);
    }

    public int getBytes() {
        return (this.mFormat == 6408 ? 4 : 3) * (getHeight() * getWidth());
    }

    public boolean onBind(GLCanvas gLCanvas) {
        prepare(gLCanvas);
        return isLoaded();
    }
}
