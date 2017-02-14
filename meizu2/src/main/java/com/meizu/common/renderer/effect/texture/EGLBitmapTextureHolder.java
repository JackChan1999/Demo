package com.meizu.common.renderer.effect.texture;

import android.graphics.Bitmap;
import com.meizu.common.renderer.effect.EGLBitmap;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.cache.BitmapTextureCache;
import com.meizu.common.renderer.effect.cache.EGLBitmapCache;

public class EGLBitmapTextureHolder extends BasicTextureHolder {
    private EGLBitmap mEGLBitmap;

    public boolean updateTexture(GLCanvas gLCanvas) {
        boolean z = false;
        synchronized (this) {
            newTextureIfNeeded();
            if (this.mTexture == null) {
            } else {
                this.mTexture.onBind(gLCanvas);
                this.mContentChanged = false;
                z = true;
            }
        }
        return z;
    }

    public void setImageResource(int i) {
        synchronized (this) {
            if (this.mResId != i) {
                this.mResId = i;
                this.mTexture = null;
                this.mEGLBitmap = EGLBitmapCache.get(i);
                this.mContentType = 0;
                newTextureIfNeeded();
            }
        }
    }

    public void setBitmap(Bitmap bitmap) {
        setEGLBitmap(EGLBitmapCache.get(bitmap));
    }

    public void setEGLBitmap(EGLBitmap eGLBitmap) {
        synchronized (this) {
            if (this.mEGLBitmap == null || this.mEGLBitmap.getGenerationId() != eGLBitmap.getGenerationId()) {
                this.mResId = 0;
                this.mTexture = null;
                this.mEGLBitmap = eGLBitmap;
                this.mContentType = 1;
                newTextureIfNeeded();
            }
        }
    }

    protected void newTextureIfNeeded() {
        if (this.mTexture == null) {
            if (this.mEGLBitmap == null && this.mContentType == 0) {
                this.mEGLBitmap = EGLBitmapCache.get(this.mResId);
            }
            if (this.mEGLBitmap != null && this.mEGLBitmap.isValid()) {
                this.mTexture = BitmapTextureCache.get(this.mEGLBitmap);
                this.mWidth = this.mTexture.getWidth();
                this.mHeight = this.mTexture.getHeight();
                this.mContentChanged = true;
            }
        }
    }

    public void onTrimMemory(int i) {
        synchronized (this) {
            if (i >= 19) {
                freeGLResource();
                if (this.mContentType == 0) {
                    this.mEGLBitmap = null;
                    this.mContentChanged = true;
                }
            }
        }
    }

    public void freeGLResource() {
        this.mTexture = null;
        this.mContentChanged = true;
    }

    public boolean isValid() {
        boolean z = false;
        synchronized (this) {
            if (this.mContentType == 1) {
                if (this.mEGLBitmap != null && this.mEGLBitmap.isValid()) {
                    z = true;
                }
            } else if (this.mContentType == 0) {
                if (this.mResId != 0) {
                    z = true;
                }
            }
        }
        return z;
    }
}
