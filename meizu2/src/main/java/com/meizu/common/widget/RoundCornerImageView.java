package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import com.meizu.common.R;

public class RoundCornerImageView extends ImageView {
    private Drawable mDstRoundCornerDrawable;
    private float mRadiusX;
    private float mRadiusY;
    private boolean mRecycle;

    public RoundCornerImageView(Context context) {
        this(context, null);
    }

    public RoundCornerImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundCornerImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RoundCornerImageView, i, 0);
        this.mRadiusX = obtainStyledAttributes.getFloat(R.styleable.RoundCornerImageView_mzCornerRadiusX, 0.0f);
        this.mRadiusY = obtainStyledAttributes.getFloat(R.styleable.RoundCornerImageView_mzCornerRadiusY, 0.0f);
        obtainStyledAttributes.recycle();
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        if (this.mDstRoundCornerDrawable != null && this.mDstRoundCornerDrawable != getDrawable() && this.mRecycle) {
            ((BitmapDrawable) this.mDstRoundCornerDrawable).getBitmap().recycle();
            this.mDstRoundCornerDrawable = null;
            this.mRecycle = false;
        }
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (this.mDstRoundCornerDrawable != null && this.mDstRoundCornerDrawable != getDrawable() && this.mRecycle) {
            ((BitmapDrawable) this.mDstRoundCornerDrawable).getBitmap().recycle();
            this.mDstRoundCornerDrawable = null;
            this.mRecycle = false;
        }
    }

    public void setImageResource(int i) {
        super.setImageResource(i);
        if (this.mDstRoundCornerDrawable != null && this.mDstRoundCornerDrawable != getDrawable() && this.mRecycle) {
            ((BitmapDrawable) this.mDstRoundCornerDrawable).getBitmap().recycle();
            this.mDstRoundCornerDrawable = null;
            this.mRecycle = false;
        }
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if (this.mDstRoundCornerDrawable != null && this.mDstRoundCornerDrawable != getDrawable() && this.mRecycle) {
            ((BitmapDrawable) this.mDstRoundCornerDrawable).getBitmap().recycle();
            this.mDstRoundCornerDrawable = null;
            this.mRecycle = false;
        }
    }

    public void setRadius(float f, float f2) {
        if (getDrawable() == null || getDrawable() != this.mDstRoundCornerDrawable) {
            this.mRadiusX = f;
            this.mRadiusY = f2;
            invalidate();
        }
    }

    public float getRadiusX() {
        return this.mRadiusX;
    }

    public float getRadiusY() {
        return this.mRadiusY;
    }

    private void drawRoundCorner() {
        if (getDrawable() != null) {
            Drawable drawable = this.mDstRoundCornerDrawable;
            boolean z = this.mRecycle;
            this.mRecycle = false;
            if (getDrawable() instanceof BitmapDrawable) {
                Bitmap bitmap;
                Bitmap bitmap2 = ((BitmapDrawable) getDrawable()).getBitmap();
                int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
                int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
                if (bitmap2.getWidth() < measuredWidth && bitmap2.getHeight() < measuredHeight) {
                    bitmap = bitmap2;
                } else if (bitmap2.getWidth() < measuredWidth) {
                    bitmap = Bitmap.createBitmap(bitmap2, 0, (bitmap2.getHeight() - measuredHeight) / 2, bitmap2.getWidth(), measuredHeight);
                    this.mRecycle = true;
                } else if (bitmap2.getHeight() < measuredHeight) {
                    bitmap = Bitmap.createBitmap(bitmap2, (bitmap2.getWidth() - measuredWidth) / 2, 0, measuredWidth, bitmap2.getHeight());
                    this.mRecycle = true;
                } else {
                    bitmap = ThumbnailUtils.extractThumbnail(bitmap2, measuredWidth, measuredHeight);
                    if (bitmap != bitmap2) {
                        this.mRecycle = true;
                    }
                }
                if (bitmap != null) {
                    if (this.mRadiusX == 0.0f || this.mRadiusY == 0.0f) {
                        this.mDstRoundCornerDrawable = new BitmapDrawable(getContext().getResources(), bitmap);
                    } else {
                        Bitmap createBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Config.ARGB_8888);
                        Canvas canvas = new Canvas(createBitmap);
                        int width = measuredWidth - bitmap.getWidth();
                        int height = measuredHeight - bitmap.getHeight();
                        Rect rect = new Rect(width / 2, height / 2, measuredWidth - (width / 2), measuredHeight - (height / 2));
                        RectF rectF = new RectF(new Rect(0, 0, measuredWidth, measuredHeight));
                        Paint paint = new Paint();
                        paint.setColor(Color.argb(255, 255, 255, 255));
                        paint.setAntiAlias(true);
                        canvas.drawRoundRect(rectF, this.mRadiusX, this.mRadiusY, paint);
                        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
                        canvas.drawBitmap(bitmap, null, rect, paint);
                        if (bitmap != bitmap2) {
                            bitmap.recycle();
                        }
                        this.mDstRoundCornerDrawable = new BitmapDrawable(getContext().getResources(), createBitmap);
                        this.mRecycle = true;
                    }
                    super.setImageDrawable(this.mDstRoundCornerDrawable);
                }
            }
            if (drawable != null && z) {
                ((BitmapDrawable) drawable).getBitmap().recycle();
            }
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mDstRoundCornerDrawable != getDrawable()) {
            drawRoundCorner();
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(RoundCornerImageView.class.getName());
    }
}
