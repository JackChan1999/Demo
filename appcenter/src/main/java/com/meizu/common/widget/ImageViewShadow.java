package com.meizu.common.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import com.meizu.common.a.c;
import com.meizu.common.a.j;

public class ImageViewShadow extends ImageView {
    private BlurMaskFilter a;
    private float b;
    private int[] c;
    private int d;
    private int e;

    public ImageViewShadow(Context context) {
        this(context, null);
    }

    public ImageViewShadow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewShadow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, j.ImageViewShadow, defStyle, 0);
        setBlurRadius(a.getFloat(j.ImageViewShadow_mcBlurRadius, 1.0f));
        setOffsetX(a.getInt(j.ImageViewShadow_mcOffsetX, -Math.round(this.b)));
        setOffsetY(a.getInt(j.ImageViewShadow_mcOffsetY, -1));
        a.recycle();
        this.a = new BlurMaskFilter(this.b, Blur.NORMAL);
        setImageShadowDrawable(getDrawable());
    }

    public void setImageShadowResource(int resId) {
        Resources resources = getResources();
        Drawable drawable = resources.getDrawable(resId);
        if (drawable != null) {
            setImageDrawable(a(resources, drawable));
        }
    }

    public void setImageShadowDrawable(Drawable drawable) {
        if (drawable != null) {
            setImageDrawable(a(getResources(), drawable));
        }
    }

    public void setBlurMaskFilter(BlurMaskFilter blurMaskFilter) {
        this.a = blurMaskFilter;
        Drawable drawable = getDrawable();
        if (drawable != null) {
            setImageShadowDrawable(drawable);
        }
    }

    private Drawable a(Resources resources, Drawable originalBitmap) {
        Bitmap mapBitmap = a(originalBitmap);
        if (mapBitmap == null) {
            return null;
        }
        Paint outterShadowPaint = new Paint();
        outterShadowPaint.setColor(resources.getColor(c.mc_image_view_shadow));
        outterShadowPaint.setMaskFilter(this.a);
        this.c = new int[2];
        Bitmap shadowBitmap = mapBitmap.extractAlpha(outterShadowPaint, this.c);
        Bitmap createBitmap = Bitmap.createBitmap(originalBitmap.getIntrinsicWidth(), originalBitmap.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint bitmapPaint = new Paint();
        bitmapPaint.setAlpha(52);
        canvas.drawBitmap(shadowBitmap, (float) this.d, (float) this.e, bitmapPaint);
        canvas.drawBitmap(mapBitmap, 0.0f, 0.0f, null);
        return new BitmapDrawable(resources, createBitmap);
    }

    private Bitmap a(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if (!(drawable instanceof NinePatchDrawable)) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public float getBlurRadius() {
        return this.b;
    }

    public void setBlurRadius(float mBlurRadius) {
        this.b = mBlurRadius;
    }

    public int[] getOffsetXY() {
        return this.c;
    }

    public int getOffsetX() {
        return this.d;
    }

    public void setOffsetX(int offsetX) {
        this.d = offsetX;
    }

    public int getOffsetY() {
        return this.e;
    }

    public void setOffsetY(int offsetY) {
        this.e = offsetY;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ImageViewShadow.class.getName());
    }
}
