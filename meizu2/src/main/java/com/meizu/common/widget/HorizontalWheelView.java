package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Scroller;
import com.meizu.common.R;
import java.util.List;

public class HorizontalWheelView extends View {
    private static final String INSTANCE_STATE = "instanceState";
    private static final String STATE_SELECTED = "selected";
    private static final String TAG = "HorizontalWheelView";
    private boolean mAllowScroll;
    private boolean mClick;
    private int mClickNumber;
    private float mClickNumberOffset;
    private float mClickOffset;
    private float mDamping;
    private List<String> mData;
    private int mDataSize;
    private float mDensity;
    private int mDownX;
    private int mDrawCount;
    private boolean mIsAccessibilityEnable;
    private boolean mIsFling;
    private boolean mIsSetTotalMove;
    private int mLastX;
    private int mLineColor;
    private float mLineHeight;
    private float mLineMarginBottom;
    private Paint mLinePaint;
    private float mLineStartY;
    private float mLineStopY;
    private float mLineWidth;
    private int mLittleLineColor;
    private float mLittleLineWidth;
    private float mMaxTotalMove;
    private int mMiddle;
    private int mMinVelocity;
    private OnValueChangeListener mOnValueChangeListener;
    private boolean mOnce;
    private boolean mPaintRound;
    private float mScaleDistance;
    private float mScaleTextSize;
    private Scroller mScroller;
    private boolean mScrolling;
    private int mSelected;
    private int mSelectedColor;
    private int mShowNumber;
    private int mTextColor;
    private float mTextMargin;
    private Paint mTextPaint;
    private float mTotalMove;
    private boolean mTouching;
    private Paint mTrianglePaint;
    private Path mTrianglePath;
    private float mTriangleSideLength;
    private VelocityTracker mVelocityTracker;
    private int mWidth;

    public interface OnValueChangeListener {
        void onSelectedChange(float f);
    }

    public HorizontalWheelView(Context context) {
        this(context, null);
    }

    public HorizontalWheelView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HorizontalWheelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLineHeight = 18.0f;
        this.mSelectedColor = SupportMenu.CATEGORY_MASK;
        this.mTextColor = ViewCompat.MEASURED_STATE_MASK;
        this.mScaleTextSize = 18.0f;
        this.mDataSize = 100;
        this.mOnce = true;
        this.mLineWidth = CircleProgressBar.BAR_WIDTH_DEF_DIP;
        this.mLittleLineWidth = 1.0f;
        this.mSelected = 0;
        this.mScaleDistance = 20.0f;
        this.mTextMargin = 10.0f;
        this.mTriangleSideLength = 10.0f;
        this.mLineColor = ViewCompat.MEASURED_STATE_MASK;
        this.mLittleLineColor = ViewCompat.MEASURED_STATE_MASK;
        this.mShowNumber = 5;
        this.mTotalMove = 0.0f;
        this.mLineMarginBottom = 5.0f;
        this.mDamping = 0.0f;
        this.mAllowScroll = true;
        this.mClickNumber = -1;
        this.mIsAccessibilityEnable = false;
        this.mTouching = false;
        this.mIsSetTotalMove = false;
        init();
        getAttrs(context, attributeSet);
        this.mTextPaint = new TextPaint(1);
        this.mTextPaint.setTextSize(this.mScaleTextSize);
        this.mTextPaint.setColor(this.mTextColor);
        Rect rect = new Rect();
        this.mTextPaint.getTextBounds("0", 0, 1, rect);
        this.mLineStartY = ((float) rect.height()) + (((float) getPaddingTop()) + this.mTextMargin);
        this.mLineStopY = this.mLineStartY + this.mLineHeight;
        this.mLinePaint = new Paint(1);
        this.mLinePaint.setColor(this.mLineColor);
        if (this.mPaintRound) {
            this.mLinePaint.setStrokeCap(Cap.ROUND);
        }
        this.mTriangleSideLength *= this.mDensity;
        this.mTrianglePath = new Path();
        this.mTrianglePaint = new Paint(1);
        this.mTrianglePaint.setStyle(Style.FILL);
        this.mTrianglePaint.setColor(this.mSelectedColor);
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager != null) {
            this.mIsAccessibilityEnable = accessibilityManager.isEnabled();
        }
        if (this.mIsAccessibilityEnable) {
            setFocusable(true);
        }
        sendAccessibilityEvent();
    }

    private void init() {
        this.mScroller = new Scroller(getContext());
        this.mDensity = (float) ((int) getContext().getResources().getDisplayMetrics().density);
        this.mScaleTextSize *= this.mDensity;
        this.mScaleDistance *= this.mDensity;
        this.mMinVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
        this.mLineHeight *= this.mDensity;
        this.mLittleLineWidth *= this.mDensity;
        this.mLineWidth *= this.mDensity;
        this.mTextMargin *= this.mDensity;
        this.mLineMarginBottom *= this.mDensity;
        this.mMaxTotalMove = ((float) this.mDataSize) * this.mScaleDistance;
        this.mClickOffset = 3.0f * this.mDensity;
        this.mClickNumberOffset = 15.0f * this.mDensity;
    }

    private void getAttrs(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.HorizontalWheelView, 0, 0);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == R.styleable.HorizontalWheelView_mcScaleDistance) {
                this.mScaleDistance = (float) ((int) obtainStyledAttributes.getDimension(index, this.mScaleDistance));
            } else if (index == R.styleable.HorizontalWheelView_mcTextColor) {
                this.mTextColor = obtainStyledAttributes.getColor(index, ViewCompat.MEASURED_STATE_MASK);
            } else if (index == R.styleable.HorizontalWheelView_mcTextSize) {
                this.mScaleTextSize = (float) ((int) obtainStyledAttributes.getDimension(index, this.mScaleTextSize));
                this.mMaxTotalMove = ((float) this.mDataSize) * this.mScaleDistance;
            } else if (index == R.styleable.HorizontalWheelView_mcSelectedColor) {
                this.mSelectedColor = obtainStyledAttributes.getColor(index, SupportMenu.CATEGORY_MASK);
            } else if (index == R.styleable.HorizontalWheelView_mcLineColor) {
                this.mLineColor = obtainStyledAttributes.getColor(index, ViewCompat.MEASURED_STATE_MASK);
            } else if (index == R.styleable.HorizontalWheelView_mcLineWidth) {
                this.mLineWidth = obtainStyledAttributes.getDimension(index, this.mLineWidth);
            } else if (index == R.styleable.HorizontalWheelView_mcLineHeight) {
                this.mLineHeight = obtainStyledAttributes.getDimension(index, this.mLineHeight);
            } else if (index == R.styleable.HorizontalWheelView_mcLittleLineWidth) {
                this.mLittleLineWidth = obtainStyledAttributes.getDimension(index, this.mLittleLineWidth);
            } else if (index == R.styleable.HorizontalWheelView_mcLittleLineColor) {
                this.mLittleLineColor = obtainStyledAttributes.getColor(index, ViewCompat.MEASURED_STATE_MASK);
            } else if (index == R.styleable.HorizontalWheelView_mcTriangleSideLength) {
                this.mTriangleSideLength = obtainStyledAttributes.getDimension(index, this.mTriangleSideLength);
            } else if (index == R.styleable.HorizontalWheelView_mcShowNumber) {
                this.mShowNumber = obtainStyledAttributes.getInt(index, this.mShowNumber);
            } else if (index == R.styleable.HorizontalWheelView_mcTextMarginBottom) {
                this.mTextMargin = obtainStyledAttributes.getDimension(index, this.mTextMargin);
            } else if (index == R.styleable.HorizontalWheelView_mcLineMarginBottom) {
                this.mLineMarginBottom = obtainStyledAttributes.getDimension(index, this.mLineMarginBottom);
            } else if (index == R.styleable.HorizontalWheelView_mcDamping) {
                this.mDamping = obtainStyledAttributes.getFloat(index, this.mDamping);
                if (this.mDamping > 1.0f) {
                    this.mDamping = 1.0f;
                } else if (this.mDamping < 0.0f) {
                    this.mDamping = 0.0f;
                }
            } else if (index == R.styleable.HorizontalWheelView_mcPaintRound) {
                this.mPaintRound = obtainStyledAttributes.getBoolean(index, false);
            }
        }
        obtainStyledAttributes.recycle();
    }

    public float getSelected() {
        return (float) this.mSelected;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mWidth = getWidth();
        if (this.mWidth != 0 && this.mOnce) {
            this.mTotalMove = ((float) this.mSelected) * this.mScaleDistance;
            initTriangle();
            this.mDrawCount = (((int) (((float) this.mWidth) / this.mScaleDistance)) / 2) + 1;
            this.mOnce = false;
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    private void initTriangle() {
        this.mMiddle = this.mWidth / 2;
        float f = this.mLineStopY + this.mLineMarginBottom;
        float sin = ((float) ((int) (Math.sin(1.0471975511965976d) * ((double) this.mTriangleSideLength)))) + f;
        this.mTrianglePath.moveTo((float) this.mMiddle, f);
        this.mTrianglePath.lineTo(((float) this.mMiddle) - (this.mTriangleSideLength / CircleProgressBar.BAR_WIDTH_DEF_DIP), sin);
        this.mTrianglePath.lineTo(((float) this.mMiddle) + (this.mTriangleSideLength / CircleProgressBar.BAR_WIDTH_DEF_DIP), sin);
        this.mTrianglePath.close();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScaleLine(canvas);
        drawTriangle(canvas);
    }

    private void drawTriangle(Canvas canvas) {
        canvas.drawPath(this.mTrianglePath, this.mTrianglePaint);
    }

    private void drawScaleLine(Canvas canvas) {
        canvas.save();
        float f = this.mTotalMove % this.mScaleDistance;
        int i = (int) (this.mTotalMove / this.mScaleDistance);
        if (i != this.mSelected) {
            this.mSelected = i;
            notifySelectedChange();
            if (this.mClick && this.mSelected == this.mClickNumber) {
                sendAccessibilityEvent();
            }
        }
        float f2 = ((float) (this.mWidth / 2)) - f;
        int i2 = 0;
        while (i2 < this.mDrawCount) {
            float f3 = f2 + (((float) i2) * this.mScaleDistance);
            if (((float) getPaddingRight()) + f3 < ((float) this.mWidth) && i + i2 <= this.mDataSize) {
                if ((i + i2) % this.mShowNumber == 0) {
                    String text = getText(i + i2);
                    PointF textPoint = getTextPoint(text, this.mTextPaint, f3);
                    canvas.drawText(text, textPoint.x, textPoint.y, this.mTextPaint);
                    setLinePaint(this.mLineColor, this.mLineWidth, f3);
                    canvas.drawLine(f3, this.mLineStartY, f3, this.mLineStopY, this.mLinePaint);
                } else {
                    setLinePaint(this.mLittleLineColor, this.mLittleLineWidth, f3);
                    Canvas canvas2 = canvas;
                    float f4 = f3;
                    canvas2.drawLine(f3, (this.mLineHeight / 4.0f) + this.mLineStartY, f4, this.mLineStopY - (this.mLineHeight / 4.0f), this.mLinePaint);
                }
            }
            f3 = f2 - (((float) i2) * this.mScaleDistance);
            if (f3 > ((float) getPaddingLeft()) && i - i2 >= 0) {
                if ((i - i2) % this.mShowNumber == 0) {
                    text = getText(i - i2);
                    textPoint = getTextPoint(text, this.mTextPaint, f3);
                    canvas.drawText(text, textPoint.x, textPoint.y, this.mTextPaint);
                    setLinePaint(this.mLineColor, this.mLineWidth, f3);
                    canvas.drawLine(f3, this.mLineStartY, f3, this.mLineStopY, this.mLinePaint);
                } else {
                    setLinePaint(this.mLittleLineColor, this.mLittleLineWidth, f3);
                    this.mLinePaint.setStrokeWidth(this.mLittleLineWidth);
                    canvas2 = canvas;
                    f4 = f3;
                    canvas2.drawLine(f3, (this.mLineHeight / 4.0f) + this.mLineStartY, f4, this.mLineStopY - (this.mLineHeight / 4.0f), this.mLinePaint);
                }
            }
            i2++;
        }
        canvas.restore();
    }

    private String getText(int i) {
        if (this.mData == null || this.mData.size() <= 0 || i >= this.mData.size() || i < 0) {
            return String.valueOf(i);
        }
        return (String) this.mData.get(i);
    }

    private void setLinePaint(int i, float f, float f2) {
        this.mLinePaint.setStrokeWidth(f);
        if (Math.abs(f2 - ((float) (this.mWidth / 2))) < this.mScaleDistance) {
            computeTextSizeAndColor(i, Math.abs(f2 - ((float) (this.mWidth / 2))) / this.mScaleDistance);
        } else {
            this.mLinePaint.setColor(i);
        }
    }

    private PointF getTextPoint(String str, Paint paint, float f) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        PointF pointF = new PointF();
        pointF.set(f - ((float) (rect.width() / 2)), (float) (rect.height() + getPaddingTop()));
        return pointF;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mAllowScroll || this.mScrolling) {
            return super.onTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        int x = (int) motionEvent.getX();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        this.mIsFling = false;
        float f;
        switch (action) {
            case 0:
                this.mScroller.forceFinished(true);
                this.mLastX = x;
                this.mDownX = x;
                this.mTouching = true;
                this.mClick = false;
                break;
            case 1:
            case 3:
                this.mTouching = false;
                this.mClick = false;
                if (Math.abs(this.mDownX - this.mLastX) < 5) {
                    f = this.mTotalMove + ((float) (this.mDownX - this.mMiddle));
                    if (f >= (-this.mClickOffset) && f <= this.mMaxTotalMove + this.mClickOffset) {
                        int round = Math.round(f / (this.mScaleDistance * ((float) this.mShowNumber)));
                        if (Math.abs(f - ((((float) round) * this.mScaleDistance) * ((float) this.mShowNumber))) < this.mClickNumberOffset && this.mSelected != this.mShowNumber * round) {
                            this.mClick = true;
                            this.mClickNumber = this.mShowNumber * round;
                            smoothScroll(this.mClickNumber, 500);
                            this.mScrolling = true;
                        }
                    }
                }
                if (!this.mClick) {
                    this.mLastX = 0;
                    invalidate();
                    countVelocityTracker();
                    return true;
                }
                break;
            case 2:
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                f = (float) (this.mLastX - x);
                if (f >= 0.0f && this.mTotalMove >= this.mMaxTotalMove) {
                    return true;
                }
                if (f > 0.0f || this.mTotalMove > 0.0f) {
                    this.mTotalMove = getMove(f) + this.mTotalMove;
                    invalidate();
                    break;
                }
                return true;
        }
        this.mLastX = x;
        return true;
    }

    private void countVelocityTracker() {
        this.mVelocityTracker.computeCurrentVelocity(1000);
        float xVelocity = this.mVelocityTracker.getXVelocity();
        if (Math.abs(xVelocity) > ((float) this.mMinVelocity)) {
            float f = (1.0f - this.mDamping) * xVelocity;
            this.mIsFling = true;
            this.mScroller.fling(0, 0, (int) f, 0, Integer.MIN_VALUE, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, 0, 0);
            return;
        }
        fixPos();
    }

    private void notifySelectedChange() {
        if (this.mOnValueChangeListener != null) {
            post(new Runnable() {
                public void run() {
                    HorizontalWheelView.this.mOnValueChangeListener.onSelectedChange((float) HorizontalWheelView.this.mSelected);
                }
            });
        }
    }

    public void computeScroll() {
        super.computeScroll();
        if (this.mScroller.computeScrollOffset()) {
            int currX = this.mScroller.getCurrX();
            if (this.mIsFling) {
                float f = (float) (this.mLastX - currX);
                this.mLastX = currX;
                if ((f < 0.0f || this.mTotalMove < this.mMaxTotalMove) && (f > 0.0f || this.mTotalMove > 0.0f)) {
                    this.mTotalMove = getMove(f) + this.mTotalMove;
                } else {
                    this.mScroller.forceFinished(true);
                    sendAccessibilityEvent();
                    return;
                }
            }
            this.mTotalMove = (float) currX;
            postInvalidate();
        } else if (this.mIsFling) {
            fixPos();
        } else {
            this.mClickNumber = -1;
            this.mScrolling = false;
            if (!(this.mTouching || this.mClick || this.mIsSetTotalMove)) {
                sendAccessibilityEvent();
            }
            if (this.mIsSetTotalMove) {
                this.mIsSetTotalMove = false;
            }
        }
    }

    private float getMove(float f) {
        if (this.mTotalMove + f < 0.0f) {
            return -this.mTotalMove;
        }
        if (this.mTotalMove + f > this.mMaxTotalMove) {
            return this.mMaxTotalMove - this.mTotalMove;
        }
        return f;
    }

    private void fixPos() {
        this.mIsFling = false;
        this.mAllowScroll = true;
        this.mScroller.forceFinished(true);
        this.mTotalMove = (float) Math.round(this.mTotalMove);
        this.mScroller.startScroll((int) this.mTotalMove, 0, (int) ((float) Math.round(computeDistance(this.mTotalMove % this.mScaleDistance))), 0, 1000);
        postInvalidate();
    }

    private float computeDistance(float f) {
        if (f <= this.mScaleDistance / CircleProgressBar.BAR_WIDTH_DEF_DIP) {
            return -f;
        }
        return this.mScaleDistance - f;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
        }
    }

    private void computeTextSizeAndColor(int i, float f) {
        int i2 = this.mSelectedColor;
        int alpha = Color.alpha(i2);
        int red = Color.red(i2);
        int green = Color.green(i2);
        i2 = Color.blue(i2);
        this.mLinePaint.setColor(Color.argb((int) ((((float) alpha) * (1.0f - f)) + (((float) Color.alpha(i)) * f)), (int) ((((float) red) * (1.0f - f)) + (((float) Color.red(i)) * f)), (int) ((((float) green) * (1.0f - f)) + (((float) Color.green(i)) * f)), (int) ((((float) i2) * (1.0f - f)) + (((float) Color.blue(i)) * f))));
    }

    public void smoothScroll(int i) {
        smoothScroll(i, 1000);
    }

    public void smoothScroll(int i, int i2) {
        this.mIsFling = false;
        this.mScroller.forceFinished(true);
        this.mScroller.startScroll((int) this.mTotalMove, 0, (int) ((((float) i) * this.mScaleDistance) - this.mTotalMove), 0, i2);
        invalidate();
    }

    public void scrollBy(float f) {
        this.mTotalMove = getMove(f) + this.mTotalMove;
        invalidate();
    }

    public void setData(List<String> list, int i) {
        this.mScroller.forceFinished(true);
        this.mData = list;
        this.mDataSize = list.size();
        this.mMaxTotalMove = ((float) this.mDataSize) * this.mScaleDistance;
        setSelectNotDraw(i);
        invalidate();
    }

    public void setSelect(int i) {
        this.mScroller.forceFinished(true);
        setSelectNotDraw(i);
        invalidate();
    }

    private void setSelectNotDraw(int i) {
        if (i > this.mDataSize) {
            this.mSelected = this.mDataSize;
        } else if (i < 0) {
            this.mSelected = 0;
        } else {
            this.mSelected = i;
        }
        this.mTotalMove = ((float) this.mSelected) * this.mScaleDistance;
    }

    public void setTotalMove(float f) {
        boolean z = true;
        this.mScroller.forceFinished(true);
        this.mIsSetTotalMove = true;
        this.mClick = false;
        if (f < 0.0f && this.mTotalMove != 0.0f) {
            this.mTotalMove = 0.0f;
        } else if (f > this.mMaxTotalMove && this.mTotalMove != this.mMaxTotalMove) {
            this.mTotalMove = this.mMaxTotalMove;
        } else if (f != this.mTotalMove) {
            this.mTotalMove = f;
        } else {
            z = false;
        }
        if (z) {
            invalidate();
        }
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.mOnValueChangeListener = onValueChangeListener;
    }

    protected Parcelable onSaveInstanceState() {
        Object bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        if (this.mClickNumber != -1) {
            bundle.putInt(STATE_SELECTED, this.mClickNumber);
        } else {
            bundle.putInt(STATE_SELECTED, this.mSelected);
        }
        return bundle;
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            setSelect(bundle.getInt(STATE_SELECTED));
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public boolean isAllowScroll() {
        return this.mAllowScroll;
    }

    public void setAllowScroll(boolean z) {
        this.mAllowScroll = z;
    }

    public void setScaleDistance(float f) {
        this.mScaleDistance = f;
        invalidate();
    }

    public float getScaleDistance() {
        return this.mScaleDistance;
    }

    public void setSelectedColor(int i) {
        this.mSelectedColor = i;
        invalidate();
    }

    public void setTextColor(int i) {
        this.mTextColor = i;
    }

    public float getTotalMove() {
        return this.mTotalMove;
    }

    private void sendAccessibilityEvent() {
        if (this.mIsAccessibilityEnable) {
            setContentDescription(String.valueOf(this.mSelected));
            sendAccessibilityEvent(4);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(HorizontalWheelView.class.getName());
    }
}
