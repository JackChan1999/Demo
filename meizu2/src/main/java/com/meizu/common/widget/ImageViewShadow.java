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
import com.meizu.common.R;

public class ImageViewShadow extends ImageView {
    private static final int DEFAULT_OFFSETY = -1;
    private static final int DEFAULT_RADIUS = 1;
    private BlurMaskFilter mBlurMaskFilter;
    private float mBlurRadius;
    private int mOffsetX;
    private int mOffsetY;
    private int[] offsetXY;

    public ImageViewShadow(Context context) {
        this(context, null);
    }

    public ImageViewShadow(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ImageViewShadow(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ImageViewShadow, i, 0);
        setBlurRadius(obtainStyledAttributes.getFloat(R.styleable.ImageViewShadow_mcBlurRadius, 1.0f));
        setOffsetX(obtainStyledAttributes.getInt(R.styleable.ImageViewShadow_mcOffsetX, -Math.round(this.mBlurRadius)));
        setOffsetY(obtainStyledAttributes.getInt(R.styleable.ImageViewShadow_mcOffsetY, -1));
        obtainStyledAttributes.recycle();
        this.mBlurMaskFilter = new BlurMaskFilter(this.mBlurRadius, Blur.NORMAL);
        setImageShadowDrawable(getDrawable());
    }

    public void setImageShadowResource(int i) {
        Resources resources = getResources();
        Drawable drawable = resources.getDrawable(i);
        if (drawable != null) {
            setImageDrawable(convertDrawableShadow(resources, drawable));
        }
    }

    public void setImageShadowDrawable(Drawable drawable) {
        if (drawable != null) {
            setImageDrawable(convertDrawableShadow(getResources(), drawable));
        }
    }

    public void setBlurMaskFilter(BlurMaskFilter blurMaskFilter) {
        this.mBlurMaskFilter = blurMaskFilter;
        Drawable drawable = getDrawable();
        if (drawable != null) {
            setImageShadowDrawable(drawable);
        }
    }

    public void invalidateImageShadow() {
        setImageDrawable(convertDrawableShadow(getResources(), getDrawable()));
    }

    private Drawable convertDrawableShadow(Resources resources, Drawable drawable) {
        Bitmap drawable2Bitmap = drawable2Bitmap(drawable);
        if (drawable2Bitmap == null) {
            return null;
        }
        Paint paint = new Paint();
        paint.setColor(resources.getColor(R.color.mc_image_view_shadow));
        paint.setMaskFilter(this.mBlurMaskFilter);
        this.offsetXY = new int[2];
        Bitmap extractAlpha = drawable2Bitmap.extractAlpha(paint, this.offsetXY);
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint2 = new Paint();
        paint2.setAlpha(52);
        canvas.drawBitmap(extractAlpha, (float) this.mOffsetX, (float) this.mOffsetY, paint2);
        canvas.drawBitmap(drawable2Bitmap, 0.0f, 0.0f, null);
        return new BitmapDrawable(resources, createBitmap);
    }

    private Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if (!(drawable instanceof NinePatchDrawable)) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public float getBlurRadius() {
        return this.mBlurRadius;
    }

    public void setBlurRadius(float f) {
        this.mBlurRadius = f;
    }

    public int[] getOffsetXY() {
        return this.offsetXY;
    }

    public int getOffsetX() {
        return this.mOffsetX;
    }

    public void setOffsetX(int i) {
        this.mOffsetX = i;
    }

    public int getOffsetY() {
        return this.mOffsetY;
    }

    public void setOffsetY(int i) {
        this.mOffsetY = i;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(ImageViewShadow.class.getName());
    }
}
