package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.meizu.common.R;

public class CircleProgressBar extends View {
    public static final int BAR_COLOR_DEF = -436207617;
    public static final float BAR_WIDTH_DEF_DIP = 2.0f;
    public static final int CENTER_TEXT_COLOR_DEF = -1;
    public static final int CENTER_TEXT_SIZE_DEF = 14;
    public static final int RIM_COLOR_DEF = 889192447;
    private int mBarColor;
    private Paint mBarPaint;
    private int mBarPostition;
    private float mBarWidth;
    private RectF mCircleBound;
    private boolean mIsShowProgress;
    private int mMax;
    private int mPercentage;
    private int mProgress;
    private int mRimColor;
    private Paint mRimPaint;
    private boolean mShouldUpdateBound;
    private String mText;
    private int mTextColor;
    private Paint mTextPaint;
    private int mTextSize;

    public CircleProgressBar(Context context) {
        super(context, null);
        this.mBarPostition = 0;
        this.mBarPaint = new Paint();
        this.mRimPaint = new Paint();
        this.mTextPaint = new Paint();
        this.mCircleBound = new RectF();
        this.mTextSize = 0;
        this.mText = "0%";
        this.mPercentage = 0;
        this.mShouldUpdateBound = false;
        this.mIsShowProgress = true;
    }

    public CircleProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBarPostition = 0;
        this.mBarPaint = new Paint();
        this.mRimPaint = new Paint();
        this.mTextPaint = new Paint();
        this.mCircleBound = new RectF();
        this.mTextSize = 0;
        this.mText = "0%";
        this.mPercentage = 0;
        this.mShouldUpdateBound = false;
        this.mIsShowProgress = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CircleProgressBar, i, 0);
        this.mBarColor = obtainStyledAttributes.getColor(R.styleable.CircleProgressBar_mcCircleBarColor, BAR_COLOR_DEF);
        this.mRimColor = obtainStyledAttributes.getColor(R.styleable.CircleProgressBar_mcCircleBarRimColor, RIM_COLOR_DEF);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = getResources().getDisplayMetrics();
        this.mBarWidth = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.CircleProgressBar_mcCircleBarWidth, (int) (displayMetrics.density * BAR_WIDTH_DEF_DIP));
        this.mTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CircleProgressBar_mcCenterTextSize, (int) (displayMetrics.density * 14.0f));
        this.mTextColor = obtainStyledAttributes.getColor(R.styleable.CircleProgressBar_mcCenterTextColor, -1);
        setMax(obtainStyledAttributes.getInt(R.styleable.CircleProgressBar_mcCircleBarMax, 0));
        setProgress(obtainStyledAttributes.getInt(R.styleable.CircleProgressBar_mcCircleBarProgress, 0));
        this.mIsShowProgress = obtainStyledAttributes.getBoolean(R.styleable.CircleProgressBar_mcCircleIsShowProgress, this.mIsShowProgress);
        obtainStyledAttributes.recycle();
        init();
    }

    private void init() {
        setBound();
        setPaint();
        this.mBarPostition = getPosByProgress(this.mProgress, true);
        this.mPercentage = getPosByProgress(this.mProgress, false);
        this.mText = String.valueOf(this.mPercentage) + "%";
    }

    protected void onDraw(Canvas canvas) {
        if (this.mShouldUpdateBound) {
            setBound();
            this.mShouldUpdateBound = false;
        }
        canvas.drawArc(this.mCircleBound, 360.0f, 360.0f, false, this.mRimPaint);
        canvas.drawArc(this.mCircleBound, -90.0f, (float) this.mBarPostition, false, this.mBarPaint);
        float descent = ((this.mTextPaint.descent() - this.mTextPaint.ascent()) / BAR_WIDTH_DEF_DIP) - this.mTextPaint.descent();
        float measureText = this.mTextPaint.measureText(this.mText) / BAR_WIDTH_DEF_DIP;
        if (this.mIsShowProgress) {
            canvas.drawText(this.mText, ((float) (getWidth() / 2)) - measureText, descent + ((float) (getHeight() / 2)), this.mTextPaint);
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mShouldUpdateBound = true;
    }

    private void setBound() {
        if (this.mCircleBound == null) {
            this.mCircleBound = new RectF();
        }
        this.mCircleBound.left = ((float) getPaddingLeft()) + this.mBarWidth;
        this.mCircleBound.top = ((float) getPaddingTop()) + this.mBarWidth;
        this.mCircleBound.right = ((float) (getWidth() - getPaddingRight())) - this.mBarWidth;
        this.mCircleBound.bottom = ((float) (getHeight() - getPaddingBottom())) - this.mBarWidth;
    }

    private void setPaint() {
        if (this.mBarPaint == null) {
            this.mBarPaint = new Paint();
        }
        this.mBarPaint.setColor(this.mBarColor);
        this.mBarPaint.setAntiAlias(true);
        this.mBarPaint.setStyle(Style.STROKE);
        this.mBarPaint.setStrokeWidth(this.mBarWidth);
        this.mBarPaint.setStrokeJoin(Join.ROUND);
        if (this.mRimPaint == null) {
            this.mRimPaint = new Paint();
        }
        this.mRimPaint.setColor(this.mRimColor);
        this.mRimPaint.setAntiAlias(true);
        this.mRimPaint.setStyle(Style.STROKE);
        this.mRimPaint.setStrokeWidth(this.mBarWidth);
        if (this.mTextPaint == null) {
            this.mTextPaint = new Paint();
        }
        this.mTextPaint.setTextSize((float) this.mTextSize);
        this.mTextPaint.setColor(this.mTextColor);
        this.mTextPaint.setAntiAlias(true);
    }

    private int getPosByProgress(int i, boolean z) {
        int i2 = z ? 360 : 100;
        if (this.mMax <= 0) {
            return 0;
        }
        if (i >= this.mMax) {
            return i2;
        }
        return (int) (((float) i2) * (((float) i) / ((float) this.mMax)));
    }

    public void setProgressStatus(boolean z) {
        this.mIsShowProgress = z;
    }

    public void setMax(int i) {
        if (i < 0) {
            i = 0;
        }
        if (i != this.mMax) {
            this.mMax = i;
            if (this.mProgress > i) {
                this.mProgress = i;
            }
            postInvalidate();
        }
    }

    public void setProgress(int i) {
        if (i < 0) {
            i = 0;
        }
        if (i > this.mMax) {
            i = this.mMax;
        }
        if (i != this.mProgress) {
            this.mProgress = i;
            this.mBarPostition = getPosByProgress(this.mProgress, true);
            this.mPercentage = getPosByProgress(this.mProgress, false);
            this.mText = String.valueOf(this.mPercentage) + "%";
            postInvalidate();
        }
    }

    public void setCircleBarColor(int i) {
        if (this.mBarColor != i) {
            this.mBarColor = i;
            this.mBarPaint.setColor(this.mBarColor);
            postInvalidate();
        }
    }

    public void setCircleRimColor(int i) {
        if (this.mRimColor != i) {
            this.mRimColor = i;
            this.mRimPaint.setColor(this.mRimColor);
            postInvalidate();
        }
    }

    public void setCircleBarWidth(float f) {
        if (((double) Math.abs(this.mBarWidth - f)) >= 1.0E-6d) {
            if (f < 0.0f) {
                this.mBarWidth = 0.0f;
            } else {
                this.mBarWidth = f;
            }
            this.mBarPaint.setStrokeWidth(this.mBarWidth);
            this.mRimPaint.setStrokeWidth(this.mBarWidth);
            this.mShouldUpdateBound = true;
            postInvalidate();
        }
    }

    public int getMax() {
        return this.mMax < 0 ? 0 : this.mMax;
    }

    public int getProgress() {
        return this.mProgress < 0 ? 0 : this.mProgress;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(CircleProgressBar.class.getName());
    }
}
