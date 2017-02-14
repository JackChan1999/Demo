package com.qq.demo.ui.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.qq.demo.ui.widget.CircleProgressBar;

public class CircularProgressDrawable extends Drawable {
    private Drawable mCenterIcon;
    private Paint mIconPaint;
    private Paint mPaint;
    private Path mPath;
    private RectF mRectF;
    private boolean mShouldIcon = false;
    private int mSize;
    private float mStartAngle;
    private int mStrokeColor;
    private int mStrokeWidth;
    private float mSweepAngle;

    public CircularProgressDrawable(int i, int i2, int i3) {
        this.mSize = i;
        this.mStrokeWidth = i2;
        this.mStrokeColor = i3;
        this.mStartAngle = -90.0f;
        this.mSweepAngle = 0.0f;
    }

    public void setSweepAngle(float f) {
        this.mSweepAngle = f;
    }

    public int getSize() {
        return this.mSize;
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        if (this.mPath == null) {
            this.mPath = new Path();
        }
        this.mPath.reset();
        this.mPath.addArc(getRect(), this.mStartAngle, this.mSweepAngle);
        this.mPath.offset((float) bounds.left, (float) bounds.top);
        canvas.drawPath(this.mPath, createPaint());
        if (this.mCenterIcon != null) {
            canvas.save();
            this.mCenterIcon.setBounds(0, 0, this.mCenterIcon.getIntrinsicWidth(), this.mCenterIcon.getIntrinsicHeight());
            canvas.translate((float) ((bounds.left + (getSize() / 2)) - (this.mCenterIcon.getIntrinsicWidth() / 2)), (float) ((bounds.top + (getSize() / 2)) - (this.mCenterIcon.getIntrinsicHeight() / 2)));
            this.mCenterIcon.draw(canvas);
            canvas.restore();
        } else if (this.mShouldIcon) {
            if (this.mIconPaint == null) {
                this.mIconPaint = new Paint();
                this.mIconPaint.setStrokeCap(Cap.ROUND);
                this.mIconPaint.setColor(this.mStrokeColor);
            }
            int size = getSize();
            int size2 = getSize();
            int i = this.mStrokeWidth;
            int i2 = (int) ((((float) size) / 5.0f) * CircleProgressBar.BAR_WIDTH_DEF_DIP);
            int i3 = (int) (0.15d * ((double) size2));
            this.mIconPaint.setStrokeWidth((float) i);
            canvas.drawLine((float) (((bounds.left + (size2 / 2)) - (i3 / 2)) - (i / 2)), (float) ((bounds.top + (size / 2)) - (i2 / 2)), (float) (((bounds.left + (size2 / 2)) - (i3 / 2)) - (i / 2)), (float) ((bounds.top + (size / 2)) + (i2 / 2)), this.mIconPaint);
            canvas.drawLine((float) (((bounds.left + (size2 / 2)) + (i3 / 2)) + (i / 2)), (float) ((bounds.top + (size / 2)) - (i2 / 2)), (float) (((bounds.left + (size2 / 2)) + (i3 / 2)) + (i / 2)), (float) ((bounds.top + (size / 2)) + (i2 / 2)), this.mIconPaint);
        }
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return 1;
    }

    private RectF getRect() {
        if (this.mRectF == null) {
            int i = this.mStrokeWidth / 2;
            this.mRectF = new RectF((float) i, (float) i, (float) (getSize() - i), (float) (getSize() - i));
        }
        return this.mRectF;
    }

    private Paint createPaint() {
        if (this.mPaint == null) {
            this.mPaint = new Paint();
            this.mPaint.setAntiAlias(true);
            this.mPaint.setStyle(Style.STROKE);
            this.mPaint.setStrokeWidth((float) this.mStrokeWidth);
            this.mPaint.setColor(this.mStrokeColor);
        }
        return this.mPaint;
    }

    public void setCenterIcon(Drawable drawable) {
        this.mCenterIcon = drawable;
    }

    public void setShowCenterIcon(boolean z) {
        this.mShouldIcon = z;
    }
}
