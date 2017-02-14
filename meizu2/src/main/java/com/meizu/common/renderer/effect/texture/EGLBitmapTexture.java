package com.meizu.common.renderer.effect.texture;

import android.opengl.GLES20;
import android.util.Log;
import com.meizu.common.renderer.effect.EGLBitmap;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLRenderManager;

public class EGLBitmapTexture extends BasicTexture {
    protected EGLBitmap mEGLBitmap;
    private boolean mEGLBitmapChanged;
    private boolean mEGLBitmapOwner;
    private int mFormat;
    private int mGenerationID;

    public EGLBitmapTexture(EGLBitmap eGLBitmap) {
        this(eGLBitmap, false);
    }

    public EGLBitmapTexture(EGLBitmap eGLBitmap, boolean z) {
        this.mFormat = 6408;
        setEGLBitmap(eGLBitmap, z);
        setSize(eGLBitmap.getWidth(), eGLBitmap.getHeight());
    }

    public EGLBitmapTexture(int i, int i2) {
        this.mFormat = 6408;
        setEGLBitmap(new EGLBitmap(i, i2), true);
        setSize(i, i2);
    }

    public int getGenerationId() {
        return this.mGenerationID;
    }

    public EGLBitmap getEGLBitmap() {
        return this.mEGLBitmap;
    }

    public void setEGLBitmap(EGLBitmap eGLBitmap) {
        setEGLBitmap(eGLBitmap, false);
    }

    public void setEGLBitmap(EGLBitmap eGLBitmap, boolean z) {
        if (eGLBitmap == null || !eGLBitmap.isValid()) {
            Log.e(GLRenderManager.TAG, "Can't setEGLBitmap " + eGLBitmap);
        }
        if (this.mEGLBitmap == null || !this.mEGLBitmap.equals(eGLBitmap)) {
            if (this.mEGLBitmap != eGLBitmap) {
                freeEGLBitmap();
            }
            this.mEGLBitmapOwner = z;
            this.mEGLBitmap = eGLBitmap;
            this.mGenerationID = this.mEGLBitmap.getGenerationId();
            this.mEGLBitmapChanged = true;
        }
    }

    public void freeEGLBitmap() {
        if (this.mEGLBitmap != null && this.mEGLBitmapOwner) {
            this.mEGLBitmap.freeGLResource();
            this.mEGLBitmap = null;
        }
    }

    public void freeGLResource() {
        super.freeGLResource();
        freeEGLBitmap();
    }

    public void uploadIfNeeded(GLCanvas gLCanvas) {
        if (isLoaded()) {
            if (this.mEGLBitmapChanged) {
                updateFormat(this.mEGLBitmap);
                this.mEGLBitmap.bindTexture(this.mId);
                this.mEGLBitmapChanged = false;
            }
        } else if (this.mEGLBitmap == null || !this.mEGLBitmap.isValid()) {
            this.mState = -1;
            Log.e(GLRenderManager.TAG, "Texture load fail, no eglbitmap");
        } else {
            GLES20.glGenTextures(1, sTextureId, 0);
            BasicTexture.initTexParameter(getTarget(), sTextureId[0]);
            bindCanvas(gLCanvas);
            this.mId = sTextureId[0];
            updateFormat(this.mEGLBitmap);
            this.mEGLBitmap.bindTexture(this.mId);
            this.mState = 1;
            this.mEGLBitmapChanged = false;
        }
    }

    public int getBytes() {
        return (this.mFormat == 6408 ? 4 : 2) * (this.mHeight * this.mWidth);
    }

    private void updateFormat(EGLBitmap eGLBitmap) {
        if (eGLBitmap.getFormat() == 4) {
            this.mFormat = 36194;
        } else {
            this.mFormat = 6408;
        }
    }

    public int getFormat() {
        return this.mFormat;
    }

    public boolean onBind(GLCanvas gLCanvas) {
        uploadIfNeeded(gLCanvas);
        return isLoaded();
    }
}
