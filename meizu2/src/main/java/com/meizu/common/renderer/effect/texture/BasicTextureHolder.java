package com.meizu.common.renderer.effect.texture;

import android.graphics.Bitmap;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLResource;
import com.meizu.common.renderer.effect.MemoryTrimmer;

public abstract class BasicTextureHolder implements GLResource, MemoryTrimmer {
    protected static final String TAG = "glrenderer";
    public static final int TYPE_BITMAP = 1;
    public static final int TYPE_EGLBITMAP = 2;
    public static final int TYPE_NONE = -1;
    public static final int TYPE_RESOURCE = 0;
    protected boolean mContentChanged;
    protected int mContentType = -1;
    protected int mHeight = -1;
    protected int mResId;
    protected BasicTexture mTexture;
    protected int mWidth = -1;

    public abstract boolean isValid();

    protected abstract void newTextureIfNeeded();

    public abstract boolean updateTexture(GLCanvas gLCanvas);

    public boolean isContentChanged() {
        return this.mContentChanged;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public BasicTexture getTexture() {
        return this.mTexture;
    }

    public void setImageResource(int i) {
    }

    public void setBitmap(Bitmap bitmap) {
    }
}
