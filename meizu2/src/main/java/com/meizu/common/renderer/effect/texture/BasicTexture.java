package com.meizu.common.renderer.effect.texture;

import android.graphics.RectF;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLResource;

public abstract class BasicTexture implements GLResource, Texture {
    private static final int MAX_TEXTURE_SIZE = 3096;
    protected static final int STATE_ERROR = -1;
    protected static final int STATE_LOADED = 1;
    protected static final int STATE_UNLOADED = 0;
    protected static final String TAG = "glrenderer";
    protected static int[] sTextureId = new int[1];
    protected RectF mBounds = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
    protected boolean mBoundsChanged = false;
    protected GLCanvas mGLCanvas = null;
    protected int mHeight = -1;
    protected int mId = 0;
    protected boolean mOpaque = false;
    protected int mState = 0;
    protected int mWidth = -1;

    protected void bindCanvas(GLCanvas gLCanvas) {
        this.mGLCanvas = gLCanvas;
    }

    public GLCanvas getBindCanvas() {
        return this.mGLCanvas;
    }

    public void setSize(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
        checkSize();
    }

    public int getBytes() {
        return (this.mWidth * this.mHeight) * 4;
    }

    public void bindSelf(int i) {
        GLES20.glActiveTexture(i);
        GLES20.glBindTexture(getTarget(), getId());
    }

    public int getId() {
        return this.mId;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void resetBounds() {
        this.mBounds.set(0.0f, 0.0f, 1.0f, 1.0f);
        this.mBoundsChanged = false;
    }

    public RectF getBounds() {
        return this.mBounds;
    }

    public void setBounds(float f, float f2, float f3, float f4) {
        this.mBounds.set(f, f2, f + f3, f2 + f4);
        this.mBoundsChanged = true;
    }

    public void updateTransformMatrix(GLCanvas gLCanvas, boolean z, boolean z2) {
        int i = (this.mBoundsChanged || z || z2) ? 1 : 0;
        if (i != 0) {
            float[] texMaxtrix = gLCanvas.getState().getTexMaxtrix();
            float f = z ? this.mBounds.right : this.mBounds.left;
            float f2 = z2 ? this.mBounds.bottom : this.mBounds.top;
            float width = z ? -this.mBounds.width() : this.mBounds.width();
            float height = z2 ? -this.mBounds.height() : this.mBounds.height();
            Matrix.translateM(texMaxtrix, 0, f, f2, 0.0f);
            Matrix.scaleM(texMaxtrix, 0, width, height, 1.0f);
        }
    }

    public int getFormat() {
        return 6408;
    }

    public int getTarget() {
        return 3553;
    }

    public boolean isLoaded() {
        return this.mState == 1;
    }

    public void freeGLResource() {
        GLCanvas gLCanvas = this.mGLCanvas;
        if (gLCanvas != null && isLoaded()) {
            gLCanvas.deleteTexture(getId());
        }
        this.mState = 0;
        bindCanvas(null);
    }

    public void setOpaque(boolean z) {
        this.mOpaque = z;
    }

    public boolean isOpaque() {
        return this.mOpaque;
    }

    public static void initTexParameter(int i, int i2) {
        GLES20.glBindTexture(i, i2);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10240, 9729);
    }

    private void checkSize() {
        if (this.mWidth > MAX_TEXTURE_SIZE || this.mHeight > MAX_TEXTURE_SIZE) {
            Log.w("glrenderer", String.format("texture is too large: %d x %d", new Object[]{Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight)}), new Exception());
        }
    }

    protected void finalize() {
        try {
            freeGLResource();
        } finally {
            super.finalize();
        }
    }
}
