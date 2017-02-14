package com.meizu.common.renderer.effect;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.meizu.common.renderer.Utils;

public class EGLBitmap implements GLResource {
    private int mFormat;
    private int mGenerationID;
    private int mHeight;
    private long mNativeEGLBitmap;
    private boolean mUploadWithGPU;
    private int mWidth;

    private static native boolean native_bindTexture(long j, int i);

    private static native long native_create();

    private static native void native_fillPixels(long j, Object obj);

    private static native long native_getGraphicBuffer(long j);

    private static native void native_release(long j);

    private static native boolean native_resize(long j, int i, int i2, int i3);

    private static native void native_setPixels(long j, Object obj);

    public EGLBitmap(Bitmap bitmap) {
        this(bitmap, GLRenderManager.isUploadEGLBitmapWithGPU());
    }

    public EGLBitmap(Bitmap bitmap, boolean z) {
        this(bitmap.getWidth(), bitmap.getHeight(), bitmapFormat2PixelFormat(bitmap.getConfig()), z);
        setBitmap(bitmap);
    }

    public EGLBitmap(int i, int i2) {
        this(i, i2, 1, GLRenderManager.isUploadEGLBitmapWithGPU());
    }

    public EGLBitmap(int i, int i2, int i3) {
        this(i, i2, i3, GLRenderManager.isUploadEGLBitmapWithGPU());
    }

    public EGLBitmap(int i, int i2, int i3, boolean z) {
        this.mFormat = 0;
        constructor();
        resize(i3, i, i2);
        this.mGenerationID = 0;
        this.mUploadWithGPU = z;
    }

    private EGLBitmap() {
        this.mFormat = 0;
    }

    public Bitmap getBitmap() {
        if (this.mWidth == 0 || this.mHeight == 0 || this.mNativeEGLBitmap == 0) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, pixelFormat2BitmapFormat(this.mFormat));
        fillBitmap(createBitmap);
        return createBitmap;
    }

    public void fillBitmap(Bitmap bitmap) {
        if (bitmap != null && bitmapFormat2PixelFormat(bitmap.getConfig()) == this.mFormat) {
            if (bitmap.getWidth() != this.mWidth || bitmap.getHeight() != this.mHeight) {
                throw new IllegalArgumentException("Input bitmap size is not matching EGLBitmap.");
            } else if (bitmap.isMutable()) {
                native_fillPixels(this.mNativeEGLBitmap, bitmap);
            } else {
                throw new IllegalArgumentException("Bitmap is not mutable.");
            }
        }
    }

    public void setBitmap(Bitmap bitmap) {
        resize(bitmapFormat2PixelFormat(bitmap.getConfig()), bitmap.getWidth(), bitmap.getHeight());
        if (this.mUploadWithGPU) {
            EGLImageHandler.getInstance().setEGLBitmap(this, bitmap);
        } else {
            native_setPixels(this.mNativeEGLBitmap, bitmap);
        }
        this.mGenerationID = bitmap.getGenerationId();
    }

    public boolean bindTexture(int i) {
        native_bindTexture(this.mNativeEGLBitmap, i);
        return true;
    }

    public long getNativeEGLBitmap() {
        return this.mNativeEGLBitmap;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getFormat() {
        return this.mFormat;
    }

    public int getGenerationId() {
        return this.mGenerationID;
    }

    public boolean isValid() {
        return this.mNativeEGLBitmap != 0;
    }

    public boolean equals(Object obj) {
        EGLBitmap eGLBitmap = (EGLBitmap) obj;
        if (obj == null) {
            return false;
        }
        if (!isValid()) {
            return false;
        }
        if (this != eGLBitmap) {
            return false;
        }
        if (this.mNativeEGLBitmap != eGLBitmap.mNativeEGLBitmap) {
            return false;
        }
        if (getGenerationId() != eGLBitmap.getGenerationId()) {
            return false;
        }
        return true;
    }

    public void freeGLResource() {
        synchronized (this) {
            if (this.mNativeEGLBitmap != 0) {
                native_release(this.mNativeEGLBitmap);
                this.mNativeEGLBitmap = 0;
                this.mGenerationID = -1;
            }
        }
    }

    protected void finalize() {
        try {
            freeGLResource();
        } finally {
            super.finalize();
        }
    }

    private void constructor() {
        Utils.assertTrue(GLRenderManager.isSupprotedEGLBitmap());
        GLRenderManager.getInstance().loadLibraryIfNeeded();
        this.mNativeEGLBitmap = native_create();
        if (this.mNativeEGLBitmap == 0) {
            throw new IllegalArgumentException("Create EGLBitmap fail");
        }
    }

    private void resize(int i, int i2, int i3) {
        if (!isFormatValid(i)) {
            throw new IllegalArgumentException("Bitmap pixel format is : " + i);
        } else if (this.mWidth != i2 || this.mHeight != i3 || this.mFormat != i) {
            if (i2 <= 0 || i3 <= 0 || !native_resize(this.mNativeEGLBitmap, i, i2, i3)) {
                throw new IllegalArgumentException("Resize fail");
            }
            this.mWidth = i2;
            this.mHeight = i3;
            this.mFormat = i;
        }
    }

    public int getSize() {
        if (this.mFormat == 1) {
            return (this.mWidth * this.mHeight) * 4;
        }
        if (this.mFormat == 4) {
            return (this.mWidth * this.mHeight) * 2;
        }
        return 0;
    }

    public static int bitmapFormat2PixelFormat(Config config) {
        if (config == Config.ARGB_8888) {
            return 1;
        }
        if (config == Config.RGB_565) {
            return 4;
        }
        return 0;
    }

    public static Config pixelFormat2BitmapFormat(int i) {
        if (i == 1) {
            return Config.ARGB_8888;
        }
        if (i == 4) {
            return Config.RGB_565;
        }
        return null;
    }

    public static boolean isFormatValid(int i) {
        if (i == 1 || i == 4) {
            return true;
        }
        return false;
    }

    long getGraphicBuffer() {
        return native_getGraphicBuffer(this.mNativeEGLBitmap);
    }
}
