package com.meizu.cloud.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.meizu.common.widget.LoadingView;

public class FullScreenImageView extends ImageView {
    protected ImageView a;
    protected LoadingView b;
    protected int c;
    protected int d;
    protected int e;
    protected int f;

    public FullScreenImageView(Context context) {
        super(context);
    }

    public FullScreenImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullScreenImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageViewAndLoadingView(ImageView imageView, LoadingView loadingView, int thumbWith, int thumbHeight, int bigWidth, int bigHeight) {
        this.a = imageView;
        this.b = loadingView;
        this.c = thumbWith;
        this.d = thumbHeight;
        this.e = bigWidth;
        this.f = bigHeight;
    }

    public void invalidate() {
        super.invalidate();
    }

    public void setImageDrawable(Drawable drawable) {
        if (drawable != null) {
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();
            super.setImageDrawable(drawable);
            if (this.a != null && this.b != null && w == this.e && h == this.f) {
                this.a.setVisibility(8);
                this.b.setVisibility(8);
                this.b.b();
            }
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
