package com.meizu.common.renderer.effect.texture;

import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.cache.BitmapTextureCache;

public class ETC1BitmapTextureHolder extends BasicTextureHolder {
    private int mAlphaResId;
    private ETC1BitmapTexture mAlphaTexture;

    public boolean updateTexture(GLCanvas gLCanvas) {
        boolean z = false;
        synchronized (this) {
            newTextureIfNeeded();
            if (this.mTexture == null) {
            } else {
                if (this.mAlphaTexture != null) {
                    this.mAlphaTexture.onBind(gLCanvas);
                }
                this.mTexture.onBind(gLCanvas);
                this.mContentChanged = false;
                z = true;
            }
        }
        return z;
    }

    public void setImageResource(int i) {
        setImageResource(i, 0);
    }

    public void setImageResource(int i, int i2) {
        synchronized (this) {
            if (this.mResId != i) {
                this.mResId = i;
                freeDataTexture();
            }
            if (this.mAlphaResId != i2) {
                this.mAlphaResId = i2;
                freeAlphaTexture();
            }
            newTextureIfNeeded();
            this.mContentType = 0;
        }
    }

    public boolean isValid() {
        boolean z;
        synchronized (this) {
            z = this.mResId != 0;
        }
        return z;
    }

    public ETC1BitmapTexture getAlphaTexture() {
        return this.mAlphaTexture;
    }

    public void onTrimMemory(int i) {
        synchronized (this) {
            if (i >= 19) {
                freeGLResource();
            }
        }
    }

    public void freeGLResource() {
        freeDataTexture();
        freeAlphaTexture();
        this.mContentChanged = true;
    }

    private void freeDataTexture() {
        this.mTexture = null;
    }

    private void freeAlphaTexture() {
        this.mAlphaTexture = null;
    }

    protected void newTextureIfNeeded() {
        if (this.mTexture == null) {
            this.mTexture = BitmapTextureCache.getETC1(this.mResId);
            this.mWidth = this.mTexture.getWidth();
            this.mHeight = this.mTexture.getHeight();
            this.mContentChanged = true;
        }
        if (this.mAlphaTexture == null) {
            this.mAlphaTexture = BitmapTextureCache.getETC1(this.mAlphaResId);
            if (this.mAlphaTexture != null) {
                this.mContentChanged = true;
            }
        }
    }
}
