package com.meizu.commonwidget;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.style.ReplacementSpan;
import android.util.Log;
import java.lang.ref.WeakReference;

public class ParcelableImageSpan extends ReplacementSpan implements ParcelableSpan {
    private FontMetricsInt a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    private final int f;
    private final int g;
    private WeakReference<Drawable> h;

    public Drawable a() {
        Drawable drawable;
        Drawable drawable2 = null;
        try {
            int intrinsicWidth;
            int i;
            drawable2 = Resources.getSystem().getDrawable(this.b);
            try {
                intrinsicWidth = drawable2.getIntrinsicWidth();
                int intrinsicHeight = drawable2.getIntrinsicHeight();
                if (this.d == 0 && this.e == 0 && this.f == 0 && this.g == 0) {
                    int i2 = intrinsicHeight;
                    drawable = drawable2;
                    i = i2;
                } else {
                    int i3 = intrinsicWidth + (this.d + this.f);
                    int i4 = intrinsicHeight + (this.e + this.g);
                    drawable = new InsetDrawable(drawable2, this.d, this.e, this.f, this.g);
                    i = i4;
                    intrinsicWidth = i3;
                }
            } catch (Exception e) {
                drawable = drawable2;
                Log.e("ParcelableImageSpan", "Unable to find resource: " + this.b);
                return drawable;
            }
            try {
                drawable.setBounds(0, 0, intrinsicWidth, i);
            } catch (Exception e2) {
                Log.e("ParcelableImageSpan", "Unable to find resource: " + this.b);
                return drawable;
            }
        } catch (Exception e3) {
            drawable = drawable2;
            Log.e("ParcelableImageSpan", "Unable to find resource: " + this.b);
            return drawable;
        }
        return drawable;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(b());
        parcel.writeInt(this.b);
        parcel.writeInt(this.d);
        parcel.writeInt(this.e);
        parcel.writeInt(this.f);
        parcel.writeInt(this.g);
    }

    public int getSpanTypeId() {
        return 25;
    }

    public int b() {
        return this.c;
    }

    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, FontMetricsInt fontMetricsInt) {
        return c().getBounds().right;
    }

    private FontMetricsInt a(Paint paint) {
        if (this.a == null) {
            this.a = paint.getFontMetricsInt();
        } else {
            paint.getFontMetricsInt(this.a);
        }
        return this.a;
    }

    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        Drawable c = c();
        canvas.save();
        int i6 = i5 - c.getBounds().bottom;
        if (this.c == 2) {
            FontMetricsInt a = a(paint);
            i6 -= ((a.descent - a.ascent) - c.getBounds().bottom) >> 1;
        } else if (this.c == 1) {
            i6 -= a(paint).descent;
        }
        canvas.translate(f, (float) i6);
        c.draw(canvas);
        canvas.restore();
    }

    private Drawable c() {
        WeakReference weakReference = this.h;
        Drawable drawable = null;
        if (weakReference != null) {
            drawable = (Drawable) weakReference.get();
        }
        if (drawable != null) {
            return drawable;
        }
        drawable = a();
        this.h = new WeakReference(drawable);
        return drawable;
    }
}
