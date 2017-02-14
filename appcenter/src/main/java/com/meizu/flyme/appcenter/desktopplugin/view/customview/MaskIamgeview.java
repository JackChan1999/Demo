package com.meizu.flyme.appcenter.desktopplugin.view.customview;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.meizu.flyme.appcenter.desktopplugin.view.PluginActivity;
import com.meizu.mstore.R;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class MaskIamgeview extends ImageView implements Target {
    private static final Mode b = Mode.DARKEN;
    private static final Mode c = Mode.DST_IN;
    private int a;
    private PorterDuffXfermode d;
    private PorterDuffXfermode e;
    private float f;
    private float g;
    private Drawable h;
    private Paint i;
    private ValueAnimator j;
    private Boolean k;

    public MaskIamgeview(Context context) {
        super(context);
        this.a = 0;
        this.f = 0.0f;
        this.g = 0.0f;
        this.k = Boolean.valueOf(false);
    }

    public MaskIamgeview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.a = 0;
        this.f = 0.0f;
        this.g = 0.0f;
        this.k = Boolean.valueOf(false);
        this.d = new PorterDuffXfermode(b);
        this.e = new PorterDuffXfermode(c);
        this.h = getDrawable();
        this.i = new Paint();
        this.j = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.j.setDuration(500);
        this.j.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ MaskIamgeview a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                this.a.f = ((this.a.g - this.a.f) * ((Float) animation.getAnimatedValue()).floatValue()) + this.a.f;
                this.a.invalidate();
            }
        });
    }

    public MaskIamgeview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.a = 0;
        this.f = 0.0f;
        this.g = 0.0f;
        this.k = Boolean.valueOf(false);
    }

    protected void onDraw(Canvas canvas) {
        this.h = getDrawable();
        if (this.a == 1 || this.a == 2) {
            BitmapDrawable noMaskBitmapDrawabel = this.h;
            if (noMaskBitmapDrawabel != null) {
                Bitmap noMaskBitmap = noMaskBitmapDrawabel.getBitmap();
                BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.drawable.mask_big, null);
                if (bd != null) {
                    int iconWidthOrHeight;
                    Bitmap cropBitmap = a(bd.getBitmap(), this.f);
                    this.i.setFilterBitmap(false);
                    this.i.setStyle(Style.FILL);
                    int sc = canvas.saveLayer(0.0f, 0.0f, (float) noMaskBitmap.getWidth(), (float) noMaskBitmap.getHeight(), null, 31);
                    if (PluginActivity.k) {
                        iconWidthOrHeight = (int) getResources().getDimension(R.dimen.plugin_item_icon_widthorheight);
                    } else {
                        iconWidthOrHeight = (int) getResources().getDimension(R.dimen.plugin_item_maskimageview_height_noflyme);
                    }
                    canvas.drawBitmap(noMaskBitmap, null, new Rect(0, 0, iconWidthOrHeight, iconWidthOrHeight), this.i);
                    this.i.setXfermode(this.d);
                    canvas.drawBitmap(cropBitmap, 0.0f, 0.0f, this.i);
                    this.i.setXfermode(this.e);
                    canvas.drawBitmap(noMaskBitmap, null, new Rect(0, 0, iconWidthOrHeight, iconWidthOrHeight), this.i);
                    this.i.setXfermode(null);
                    canvas.restoreToCount(sc);
                    return;
                }
                return;
            }
            return;
        }
        super.onDraw(canvas);
    }

    protected void onDetachedFromWindow() {
        this.j.cancel();
        this.j.removeAllUpdateListeners();
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }

    public Bitmap a(Bitmap bitmap, float progress) {
        int w = bitmap.getWidth();
        int wh = (int) ((((float) bitmap.getHeight()) * (100.0f - progress)) / 100.0f);
        if (wh == 0) {
            wh = 1;
        }
        return Bitmap.createBitmap(bitmap, 0, 0, w, wh, null, false);
    }

    public float getmProgress() {
        return this.f;
    }

    public void setmProgress(float progress) {
        this.g = progress;
        if (this.a != 1) {
            this.f = progress;
        } else if (this.f != this.g) {
            this.j.start();
        }
    }

    public Drawable getNoMask() {
        return this.h;
    }

    public int getState() {
        return this.a;
    }

    public void setState(int state) {
        if (state != this.a) {
            this.a = state;
            invalidate();
        }
    }

    public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
        setImageBitmap(bitmap);
    }

    public void onBitmapFailed(Drawable errorDrawable) {
        setImageDrawable(errorDrawable);
    }

    public void onPrepareLoad(Drawable placeHolderDrawable) {
        setImageDrawable(placeHolderDrawable);
    }
}
