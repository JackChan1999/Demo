package com.meizu.common.renderer.effect.texture;

import android.opengl.GLES20;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLES31Utils;
import com.meizu.common.renderer.effect.render.FastBlurRender;

public class StorageTexture extends BasicTexture {
    private int mFormat;

    public StorageTexture(int i, int i2) {
        this(i, i2, GLES31Utils.GL_RGBA16F);
    }

    public StorageTexture(int i, int i2, int i3) {
        this.mFormat = GLES31Utils.GL_RGBA16F;
        this.mFormat = i3;
        setSize(i, i2);
    }

    private void prepare(GLCanvas gLCanvas) {
        if (!isLoaded()) {
            GLES20.glGenTextures(1, sTextureId, 0);
            BasicTexture.initTexParameter(getTarget(), sTextureId[0]);
            FastBlurRender.glTexStorage2D(3553, 1, this.mFormat, getWidth(), getHeight());
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
        return (this.mWidth * this.mHeight) * 4;
    }

    public boolean onBind(GLCanvas gLCanvas) {
        prepare(gLCanvas);
        return isLoaded();
    }
}
