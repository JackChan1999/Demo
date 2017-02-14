package com.meizu.common.renderer.effect.texture;

import android.graphics.Bitmap;
import com.meizu.common.renderer.Utils;

public class BitmapTexture extends UploadedTexture {
    protected Bitmap mContentBitmap;
    protected int mGenerationID = -1;

    public BitmapTexture(Bitmap bitmap) {
        boolean z = (bitmap == null || bitmap.isRecycled()) ? false : true;
        Utils.assertTrue(z);
        this.mContentBitmap = bitmap;
        setSize(bitmap.getWidth(), bitmap.getHeight());
    }

    protected void onFreeBitmap(Bitmap bitmap) {
        this.mGenerationID = this.mContentBitmap.getGenerationId();
        this.mContentBitmap = null;
    }

    public int getGenerationId() {
        return this.mGenerationID;
    }

    public void setBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        if (bitmap != this.mContentBitmap || bitmap.getGenerationId() != this.mGenerationID) {
            this.mContentBitmap = bitmap;
            this.mContentChanged = true;
        }
    }

    protected Bitmap onGetBitmap() {
        return this.mContentBitmap;
    }

    public Bitmap getBitmap() {
        return this.mContentBitmap;
    }
}
