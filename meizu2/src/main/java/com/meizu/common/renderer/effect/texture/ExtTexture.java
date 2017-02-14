package com.meizu.common.renderer.effect.texture;

import android.opengl.GLES20;
import com.meizu.common.renderer.effect.GLCanvas;

public class ExtTexture extends BasicTexture {
    private int mTarget = 36197;

    private void uploadToCanvas(GLCanvas gLCanvas) {
        if (!isLoaded()) {
            GLES20.glGenTextures(1, sTextureId, 0);
            BasicTexture.initTexParameter(getTarget(), sTextureId[0]);
            this.mId = sTextureId[0];
            bindCanvas(gLCanvas);
            this.mState = 1;
        }
    }

    public int getFormat() {
        return 36197;
    }

    public boolean onBind(GLCanvas gLCanvas) {
        if (!isLoaded()) {
            uploadToCanvas(gLCanvas);
        }
        return true;
    }

    public int getTarget() {
        return this.mTarget;
    }

    public boolean isOpaque() {
        return true;
    }
}
