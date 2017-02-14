package com.meizu.common.renderer.effect.texture;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLRenderManager;

public abstract class UploadedTexture extends BasicTexture {
    protected Bitmap mBitmap;
    protected boolean mContentChanged;
    private int mFormat = 6408;

    protected abstract void onFreeBitmap(Bitmap bitmap);

    protected abstract Bitmap onGetBitmap();

    private Bitmap getBitmap() {
        if (this.mBitmap == null) {
            this.mBitmap = onGetBitmap();
            setSize(this.mBitmap.getWidth(), this.mBitmap.getHeight());
        }
        return this.mBitmap;
    }

    private void freeBitmap() {
        onFreeBitmap(this.mBitmap);
        this.mBitmap = null;
    }

    public void uploadIfNeeded(GLCanvas gLCanvas) {
        Bitmap bitmap;
        if (!isLoaded()) {
            bitmap = getBitmap();
            if (bitmap != null) {
                try {
                    GLES20.glGenTextures(1, sTextureId, 0);
                    BasicTexture.initTexParameter(getTarget(), sTextureId[0]);
                    updateFormat(bitmap);
                    GLUtils.texImage2D(3553, 0, bitmap, 0);
                    bindCanvas(gLCanvas);
                    this.mId = sTextureId[0];
                    this.mState = 1;
                    this.mContentChanged = false;
                } finally {
                    freeBitmap();
                }
            } else {
                this.mState = -1;
                Log.e(GLRenderManager.TAG, "Texture load fail, no bitmap");
            }
        } else if (this.mContentChanged) {
            this.mContentChanged = false;
            bitmap = getBitmap();
            if (bitmap != null) {
                GLUtils.texSubImage2D(3553, 0, 0, 0, bitmap);
                freeBitmap();
            }
        }
    }

    public int getBytes() {
        return (this.mFormat == 6408 ? 4 : 2) * (getHeight() * getWidth());
    }

    private void updateFormat(Bitmap bitmap) {
        if (bitmap.getConfig() == Config.RGB_565) {
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

    public boolean isOpaque() {
        return this.mOpaque;
    }

    public void freeGLResource() {
        super.freeGLResource();
        if (this.mBitmap != null) {
            freeBitmap();
        }
    }
}
