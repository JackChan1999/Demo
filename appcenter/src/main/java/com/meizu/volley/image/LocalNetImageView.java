package com.meizu.volley.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.f;

public class LocalNetImageView extends NetworkImageView {
    protected boolean c = false;

    public LocalNetImageView(Context context) {
        super(context);
    }

    public LocalNetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocalNetImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageDrawableLocal(Drawable drawable) {
        a();
        super.setImageDrawable(drawable);
    }

    public void setImageBitmapLocal(Bitmap bm) {
        a();
        super.setImageBitmap(bm);
    }

    public void setImageResourceLocal(int resId) {
        a();
        super.setImageResource(resId);
    }

    private void a() {
        this.c = true;
        if (this.a != null) {
            this.a.a();
            this.a = null;
        }
    }

    public void setImageUrl(String url, f imageLoader) {
        this.c = false;
        super.setImageUrl(url, imageLoader);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!this.c) {
            super.onLayout(changed, left, top, right, bottom);
        }
    }
}
