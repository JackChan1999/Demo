package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.LinearInterpolator;

import com.meizu.common.R;

@SuppressLint({"NewApi"})
public class LoadingView extends View {
    private static final int LOADING_ANIMATION = 1;
    private static final int PROGRESS_ANIMATION = 2;
    public static final String START_ANGLE_PROPERTY = "startAngle";
    public static final String SWEEP_ANGLE_PROPERTY = "sweepAngle";
    private final long LOADING_ANIM_DURATION;
    private int mBackgroundColor;
    private float mCentX;
    private float mCentY;
    private RectF mCircleBounds;
    private Context mContext;
    private int mForegroundColor;
    private Animator mLoadingAnimator;
    private int mLoadingState;
    private Paint mPaint;
    private Paint mPaintArc;
    private Paint mPaintArcBack;
    private int mPaintWidth;
    private float mRadius;
    private float mRingWidth;
    private float mStartAngle;
    private float mSweepAngle;
    private int mThemeColor;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_LoadingViewStyle);
    }

    public LoadingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        mPaint = null;
        mPaintArc = null;
        mPaintArcBack = null;
        mContext = null;
        mLoadingAnimator = null;
        LOADING_ANIM_DURATION = 1760;
        mCircleBounds = null;
        mPaintWidth = 0;
        mLoadingState = 1;
        mContext = context;
        mPaint = new Paint(1);
        mPaint.setAntiAlias(true);
        mPaint.setColor(-1);
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Align.CENTER);
        mPaint.setTextSize(36.0f);
        TypedArray obtainStyledAttributes = mContext.obtainStyledAttributes(R.styleable
                .MZTheme);
        mThemeColor = obtainStyledAttributes.getInt(R.styleable.MZTheme_mzThemeColor,
                Color.GRAY);
        obtainStyledAttributes.recycle();
        obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable
                .LoadingView, i, 0);
        mRadius = obtainStyledAttributes.getDimension(R.styleable
                .LoadingView_mcLoadingRadius, 30.0f);
        mRingWidth = obtainStyledAttributes.getDimension(R.styleable
                .LoadingView_mcRingWidth, 4.5f);
        mBackgroundColor = obtainStyledAttributes.getColor(R.styleable
                .LoadingView_mcLBackground, mThemeColor);
        mForegroundColor = obtainStyledAttributes.getColor(R.styleable
                .LoadingView_mcLForeground, mThemeColor);
        mLoadingState = obtainStyledAttributes.getInt(R.styleable
                .LoadingView_mcLoadingState, 1);
        obtainStyledAttributes.recycle();
        mPaintArc = new Paint(1);
        mPaintArc.setAntiAlias(true);
        mPaintArc.setColor(mForegroundColor);
        mPaintArc.setStyle(Style.STROKE);
        mPaintArc.setStrokeCap(Cap.ROUND);
        mPaintArcBack = new Paint(1);
        mPaintArcBack.setAntiAlias(true);
        mPaintArcBack.setColor(mBackgroundColor);
        mPaintArcBack.setStyle(Style.STROKE);
        mPaintArc.setStrokeWidth(mRingWidth - ((float) mPaintWidth));
        mPaintArcBack.setStrokeWidth(mRingWidth - ((float) mPaintWidth));
        init();
    }

    public LoadingView(Context context, float f, float f2) {
        this(context, null);
        mRadius = f;
        mRingWidth = f2;
        init();
    }

    private void init() {
        mCentX = (((getX() + ((float) getPaddingLeft())) + mRadius) + ((float) (this
                .mPaintWidth * 2))) + mRingWidth;
        mCentY = (((getY() + ((float) getPaddingTop())) + mRadius) + ((float) (this
                .mPaintWidth * 2))) + mRingWidth;
        mCircleBounds = new RectF();
        mCircleBounds.left = ((mCentX - mRadius) - ((float) (mPaintWidth / 2)
        )) - (mRingWidth / CircleProgressBar.BAR_WIDTH_DEF_DIP);
        mCircleBounds.top = ((mCentY - mRadius) - ((float) (mPaintWidth / 2))
        ) - (mRingWidth / CircleProgressBar.BAR_WIDTH_DEF_DIP);
        mCircleBounds.right = ((mCentX + mRadius) + ((float) (mPaintWidth /
                2))) + (mRingWidth / CircleProgressBar.BAR_WIDTH_DEF_DIP);
        mCircleBounds.bottom = ((mCentY + mRadius) + ((float) (mPaintWidth /
                2))) + (mRingWidth / CircleProgressBar.BAR_WIDTH_DEF_DIP);
    }

    protected void onDraw(Canvas canvas) {
        canvas.translate((((float) (getWidth() / 2)) - mRadius) - mRingWidth, (((float)
                (getHeight() / 2)) - mRadius) - mRingWidth);
        if (mForegroundColor == mBackgroundColor) {
            mPaintArcBack.setAlpha(26);
        }
        if (mLoadingState == 1) {
            drawLoadingAnimation(canvas);
        } else {
            super.onDraw(canvas);
        }
    }

    private void drawLoadingAnimation(Canvas canvas) {
        canvas.drawArc(mCircleBounds, -90.0f, 360.0f, false, mPaintArcBack);
        canvas.drawArc(mCircleBounds, mStartAngle, mSweepAngle, false, this
                .mPaintArc);
    }

    @Deprecated
    public void startProgressAnimation() {
        mLoadingState = 2;
    }

    public void startAnimator() {
        startLoadingAnimation();
    }

    public void stopAnimator() {
        if (mLoadingAnimator != null) {
            mLoadingAnimator.cancel();
            mLoadingAnimator = null;
        }
    }

    private void startLoadingAnimation() {
        if (mLoadingAnimator == null || !mLoadingAnimator.isRunning()) {
            mLoadingState = 1;
            mLoadingAnimator = createLoadingAnimator();
            mLoadingAnimator.start();
        }
    }

    private Animator createLoadingAnimator() {
        Keyframe keyframe = Keyframe.ofFloat(0.0f, -90.0f);
        Keyframe keyframe1 = Keyframe.ofFloat(0.5f,30.0f);
        Keyframe keyframe2 = Keyframe.ofFloat(1.0f, 630.0f);

        PropertyValuesHolder startAngle = PropertyValuesHolder.ofKeyframe("startAngle", keyframe, keyframe1, keyframe2);
        PropertyValuesHolder sweepAngle = PropertyValuesHolder.ofFloat("sweepAngle",0.0f, -120.0f, 0.0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this, startAngle, sweepAngle);

        animator.setDuration(1760);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        return animator;
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            startLoadingAnimation();
        } else if ((visibility == View.INVISIBLE || visibility == View.GONE) && mLoadingAnimator != null) {
            mLoadingAnimator.cancel();
            mLoadingAnimator = null;
        }
    }

    protected void onVisibilityChanged(View view, int visibility) {
        super.onVisibilityChanged(view, visibility);
        if (1 == mLoadingState) {
            if (visibility != View.VISIBLE) {
                if (mLoadingAnimator != null) {
                    mLoadingAnimator.cancel();
                    mLoadingAnimator = null;
                }
            } else if (isShown()) {
                startLoadingAnimation();
            }
        }
    }

    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (1 == mLoadingState) {
            if (visibility != View.VISIBLE) {
                if (mLoadingAnimator != null) {
                    mLoadingAnimator.cancel();
                    mLoadingAnimator = null;
                }
            } else if (isShown()) {
                startLoadingAnimation();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (int) (CircleProgressBar.BAR_WIDTH_DEF_DIP * ((mRadius + mRingWidth) + 1.0f));
        setMeasuredDimension(resolveSizeAndState((getPaddingLeft() + getPaddingRight()) + width, widthMeasureSpec,
                0), resolveSizeAndState(width + (getPaddingTop() + getPaddingBottom()), heightMeasureSpec, 0));
    }

    public void setBarColor(int barColor) {
        if (mPaintArc != null && mPaintArc.getColor() != barColor) {
            mPaintArc.setColor(barColor);
            mForegroundColor = barColor;
            postInvalidate();
        }
    }

    public int getBarColor() {
        return mForegroundColor;
    }

    public void setBarBackgroundColor(int barBackgroundColor) {
        if (mPaintArcBack != null && mPaintArcBack.getColor() != barBackgroundColor) {
            mPaintArcBack.setColor(barBackgroundColor);
            mBackgroundColor = barBackgroundColor;
            postInvalidate();
        }
    }

    public int getBarBackgroundColor() {
        return mBackgroundColor;
    }

    public float getSweepAngle() {
        return mSweepAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        mSweepAngle = sweepAngle;
        invalidate();
    }

    public float getStartAngle() {
        return mStartAngle;
    }

    public void setStartAngle(float startAngle) {
        mStartAngle = startAngle;
        invalidate();
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(LoadingView.class.getName());
    }
}
