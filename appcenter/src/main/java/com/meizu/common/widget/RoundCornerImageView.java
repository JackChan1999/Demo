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
import com.meizu.common.a.j;

public class RoundCornerImageView extends ImageView {
    private Drawable a;
    private boolean b;
    private float c;
    private float d;

    public RoundCornerImageView(Context context) {
        this(context, null);
    }

    public RoundCornerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, j.RoundCornerImageView, defStyle, 0);
        this.c = a.getFloat(j.RoundCornerImageView_mzCornerRadiusX, 0.0f);
        this.d = a.getFloat(j.RoundCornerImageView_mzCornerRadiusY, 0.0f);
        a.recycle();
    }

    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        if (this.a != null && this.a != getDrawable() && this.b) {
            ((BitmapDrawable) this.a).getBitmap().recycle();
            this.a = null;
            this.b = false;
        }
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (this.a != null && this.a != getDrawable() && this.b) {
            ((BitmapDrawable) this.a).getBitmap().recycle();
            this.a = null;
            this.b = false;
        }
    }

    public void setImageResource(int resId) {
        super.setImageResource(resId);
        if (this.a != null && this.a != getDrawable() && this.b) {
            ((BitmapDrawable) this.a).getBitmap().recycle();
            this.a = null;
            this.b = false;
        }
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if (this.a != null && this.a != getDrawable() && this.b) {
            ((BitmapDrawable) this.a).getBitmap().recycle();
            this.a = null;
            this.b = false;
        }
    }

    public void setRadius(float radiusX, float radiusY) {
        if (getDrawable() == null || getDrawable() != this.a) {
            this.c = radiusX;
            this.d = radiusY;
            invalidate();
        }
    }

    public float getRadiusX() {
        return this.c;
    }

    public float getRadiusY() {
        return this.d;
    }

    private void a() {
        if (getDrawable() != null) {
            Drawable oldDstDrawable = this.a;
            boolean oldRecycle = this.b;
            this.b = false;
            if (getDrawable() instanceof BitmapDrawable) {
                Bitmap thumbnailBmp;
                Bitmap drawableBmp = ((BitmapDrawable) getDrawable()).getBitmap();
                int width = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
                int height = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
                if (drawableBmp.getWidth() < width && drawableBmp.getHeight() < height) {
                    thumbnailBmp = drawableBmp;
                } else if (drawableBmp.getWidth() < width) {
                    thumbnailBmp = Bitmap.createBitmap(drawableBmp, 0, (drawableBmp.getHeight() - height) / 2, drawableBmp.getWidth(), height);
                    this.b = true;
                } else if (drawableBmp.getHeight() < height) {
                    thumbnailBmp = Bitmap.createBitmap(drawableBmp, (drawableBmp.getWidth() - width) / 2, 0, width, drawableBmp.getHeight());
                    this.b = true;
                } else {
                    thumbnailBmp = ThumbnailUtils.extractThumbnail(drawableBmp, width, height);
                    if (thumbnailBmp != drawableBmp) {
                        this.b = true;
                    }
                }
                if (thumbnailBmp != null) {
                    if (this.c == 0.0f || this.d == 0.0f) {
                        this.a = new BitmapDrawable(getContext().getResources(), thumbnailBmp);
                    } else {
                        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                        Canvas canvas = new Canvas(output);
                        int dx = width - thumbnailBmp.getWidth();
                        int dy = height - thumbnailBmp.getHeight();
                        Rect dstRect = new Rect(dx / 2, dy / 2, width - (dx / 2), height - (dy / 2));
                        RectF rectF = new RectF(new Rect(0, 0, width, height));
                        Paint paint = new Paint();
                        paint.setColor(Color.argb(255, 255, 255, 255));
                        paint.setAntiAlias(true);
                        canvas.drawRoundRect(rectF, this.c, this.d, paint);
                        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
                        canvas.drawBitmap(thumbnailBmp, null, dstRect, paint);
                        if (thumbnailBmp != drawableBmp) {
                            thumbnailBmp.recycle();
                        }
                        this.a = new BitmapDrawable(getContext().getResources(), output);
                        this.b = true;
                    }
                    super.setImageDrawable(this.a);
                }
            }
            if (oldDstDrawable != null && oldRecycle) {
                ((BitmapDrawable) oldDstDrawable).getBitmap().recycle();
            }
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.a != getDrawable()) {
            a();
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(RoundCornerImageView.class.getName());
    }
}
