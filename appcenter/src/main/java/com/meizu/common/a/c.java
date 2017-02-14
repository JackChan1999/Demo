package com.meizu.common.a;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class c extends Drawable {
    private float a;
    private float b;
    private int c;
    private int d;
    private int e;
    private Drawable f;
    private Paint g;
    private boolean h = false;
    private RectF i;
    private Paint j;
    private Path k;

    public c(int size, int strokeWidth, int strokeColor) {
        this.c = size;
        this.d = strokeWidth;
        this.e = strokeColor;
        this.b = -90.0f;
        this.a = 0.0f;
    }

    public void a(float sweepAngle) {
        this.a = sweepAngle;
    }

    public int a() {
        return this.c;
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        if (this.k == null) {
            this.k = new Path();
        }
        this.k.reset();
        this.k.addArc(b(), this.b, this.a);
        this.k.offset((float) bounds.left, (float) bounds.top);
        canvas.drawPath(this.k, c());
        if (this.f != null) {
            canvas.save();
            this.f.setBounds(0, 0, this.f.getIntrinsicWidth(), this.f.getIntrinsicHeight());
            canvas.translate((float) ((bounds.left + (a() / 2)) - (this.f.getIntrinsicWidth() / 2)), (float) ((bounds.top + (a() / 2)) - (this.f.getIntrinsicHeight() / 2)));
            this.f.draw(canvas);
            canvas.restore();
        } else if (this.h) {
            if (this.g == null) {
                this.g = new Paint();
                this.g.setStrokeCap(Cap.ROUND);
                this.g.setColor(this.e);
            }
            int height = a();
            int width = a();
            int lineWidth = this.d;
            int lineHeight = (int) ((((float) height) / 5.0f) * 2.0f);
            int lineGap = (int) (0.15d * ((double) width));
            this.g.setStrokeWidth((float) lineWidth);
            canvas.drawLine((float) (((bounds.left + (width / 2)) - (lineGap / 2)) - (lineWidth / 2)), (float) ((bounds.top + (height / 2)) - (lineHeight / 2)), (float) (((bounds.left + (width / 2)) - (lineGap / 2)) - (lineWidth / 2)), (float) ((bounds.top + (height / 2)) + (lineHeight / 2)), this.g);
            canvas.drawLine((float) (((bounds.left + (width / 2)) + (lineGap / 2)) + (lineWidth / 2)), (float) ((bounds.top + (height / 2)) - (lineHeight / 2)), (float) (((bounds.left + (width / 2)) + (lineGap / 2)) + (lineWidth / 2)), (float) ((bounds.top + (height / 2)) + (lineHeight / 2)), this.g);
        }
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return 1;
    }

    private RectF b() {
        if (this.i == null) {
            int index = this.d / 2;
            this.i = new RectF((float) index, (float) index, (float) (a() - index), (float) (a() - index));
        }
        return this.i;
    }

    private Paint c() {
        if (this.j == null) {
            this.j = new Paint();
            this.j.setAntiAlias(true);
            this.j.setStyle(Style.STROKE);
            this.j.setStrokeWidth((float) this.d);
            this.j.setColor(this.e);
        }
        return this.j;
    }

    public void a(Drawable centerIcon) {
        this.f = centerIcon;
    }

    public void a(boolean showCenterIcon) {
        this.h = showCenterIcon;
    }
}
