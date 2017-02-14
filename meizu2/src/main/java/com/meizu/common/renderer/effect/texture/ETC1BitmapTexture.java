package com.meizu.common.renderer.effect.texture;

import android.opengl.ETC1Util.ETC1Texture;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.util.Log;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLRenderManager;
import java.nio.Buffer;

public class ETC1BitmapTexture extends BasicTexture {
    private boolean mContentChanged;
    protected ETC1Texture mETC1Texture;

    public ETC1BitmapTexture(ETC1Texture eTC1Texture) {
        setETC1Texture(eTC1Texture);
        setOpaque(true);
    }

    public void setETC1Texture(ETC1Texture eTC1Texture) {
        if (eTC1Texture != null && this.mETC1Texture != eTC1Texture) {
            this.mETC1Texture = eTC1Texture;
            setSize(eTC1Texture.getWidth(), eTC1Texture.getHeight());
            this.mContentChanged = true;
        }
    }

    public void uploadIfNeeded(GLCanvas gLCanvas) {
        if (isLoaded()) {
            if (this.mContentChanged && this.mETC1Texture != null) {
                Buffer data = this.mETC1Texture.getData();
                int i = 3553;
                int i2 = 0;
                int i3 = 0;
                int i4 = 0;
                GLES10.glCompressedTexSubImage2D(i, i2, i3, i4, getWidth(), getHeight(), 36196, data.remaining(), data);
                this.mContentChanged = false;
                this.mETC1Texture = null;
            }
        } else if (this.mETC1Texture != null) {
            GLES20.glGenTextures(1, sTextureId, 0);
            BasicTexture.initTexParameter(getTarget(), sTextureId[0]);
            bindCanvas(gLCanvas);
            this.mId = sTextureId[0];
            Buffer data2 = this.mETC1Texture.getData();
            GLES10.glCompressedTexImage2D(3553, 0, 36196, getWidth(), getHeight(), 0, data2.remaining(), data2);
            this.mState = 1;
            this.mContentChanged = false;
            this.mETC1Texture = null;
        } else {
            Log.e(GLRenderManager.TAG, "Texture load fail, no ETC1Texture.");
            this.mState = -1;
        }
    }

    public int getBytes() {
        return (getWidth() * getHeight()) / 2;
    }

    public boolean onBind(GLCanvas gLCanvas) {
        uploadIfNeeded(gLCanvas);
        return isLoaded();
    }

    public int getFormat() {
        return 36196;
    }
}
