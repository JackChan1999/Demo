package com.meizu.cloud.app.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

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

    public CircularProgressDrawable(int size, int strokeWidth, int strokeColor) {
        this.mSize = size;
        this.mStrokeWidth = strokeWidth;
        this.mStrokeColor = strokeColor;
        this.mStartAngle = -90.0f;
        this.mSweepAngle = 0.0f;
    }

    public void setSweepAngle(float sweepAngle) {
        this.mSweepAngle = sweepAngle;
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
            int height = getSize();
            int width = getSize();
            int lineWidth = this.mStrokeWidth;
            int lineHeight = (int) ((((float) height) / 5.0f) * 2.0f);
            int lineGap = (int) (0.15d * ((double) width));
            this.mIconPaint.setStrokeWidth((float) lineWidth);
            canvas.drawLine((float) (((bounds.left + (width / 2)) - (lineGap / 2)) - (lineWidth / 2)), (float) ((bounds.top + (height / 2)) - (lineHeight / 2)), (float) (((bounds.left + (width / 2)) - (lineGap / 2)) - (lineWidth / 2)), (float) ((bounds.top + (height / 2)) + (lineHeight / 2)), this.mIconPaint);
            canvas.drawLine((float) (((bounds.left + (width / 2)) + (lineGap / 2)) + (lineWidth / 2)), (float) ((bounds.top + (height / 2)) - (lineHeight / 2)), (float) (((bounds.left + (width / 2)) + (lineGap / 2)) + (lineWidth / 2)), (float) ((bounds.top + (height / 2)) + (lineHeight / 2)), this.mIconPaint);
        }
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return 1;
    }

    private RectF getRect() {
        if (this.mRectF == null) {
            int index = this.mStrokeWidth / 2;
            this.mRectF = new RectF((float) index, (float) index, (float) (getSize() - index), (float) (getSize() - index));
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

    public void setCenterIcon(Drawable centerIcon) {
        this.mCenterIcon = centerIcon;
    }

    public void setShowCenterIcon(boolean showCenterIcon) {
        this.mShouldIcon = showCenterIcon;
    }

    public void setIndicatorColor(int color) {
        createPaint();
        this.mStrokeColor = color;
        this.mPaint.setColor(this.mStrokeColor);
    }

    public void setStrokeWidth(int width) {
        if (width > 0 && this.mStrokeWidth != width) {
            this.mStrokeWidth = width;
            if (this.mRectF != null) {
                int index = this.mStrokeWidth / 2;
                this.mRectF.set((float) index, (float) index, (float) (getSize() - index), (float) (getSize() - index));
            }
            if (this.mPaint != null) {
                this.mPaint.setStrokeWidth((float) this.mStrokeWidth);
            }
        }
    }
}
