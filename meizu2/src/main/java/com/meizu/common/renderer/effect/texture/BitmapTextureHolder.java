package com.meizu.common.renderer.effect.texture;

import android.graphics.Bitmap;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.cache.BitmapTextureCache;

public class BitmapTextureHolder extends BasicTextureHolder {
    private Bitmap mBitmap;

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
                this.mBitmap = null;
                this.mTexture = null;
                this.mContentType = 0;
                newTextureIfNeeded();
            }
        }
    }

    public void setBitmap(Bitmap bitmap) {
        synchronized (this) {
            if (!(this.mBitmap != null && this.mBitmap == bitmap && this.mBitmap.getGenerationId() == bitmap.getGenerationId())) {
                this.mResId = 0;
                this.mBitmap = bitmap;
                this.mTexture = null;
                this.mContentType = 1;
                newTextureIfNeeded();
            }
        }
    }

    protected void newTextureIfNeeded() {
        if (this.mTexture == null) {
            if (this.mContentType == 1) {
                this.mTexture = BitmapTextureCache.get(this.mBitmap);
            } else {
                this.mTexture = BitmapTextureCache.get(this.mResId);
            }
            this.mContentChanged = true;
            this.mWidth = this.mTexture.getWidth();
            this.mHeight = this.mTexture.getHeight();
        }
    }

    public void onTrimMemory(int i) {
        synchronized (this) {
            if (i >= 19) {
                freeGLResource();
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
                if (!(this.mBitmap == null || this.mBitmap.isRecycled())) {
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
