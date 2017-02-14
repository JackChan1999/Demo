package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.meizu.common.R;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;

public class PullRefreshAnimationView {
    private static int DRAW_ARC_VIEW = 1;
    private static int DRAW_LINE_VIEW = 0;
    private static int INVALID = -1;
    public static final String START_ANGLE_PROPERTY = "startAngle";
    public static final String SWEEP_ANGLE_PROPERTY = "sweepAngle";
    private final long BEFORE_LOADING_ANIM_DURATION = 1120;
    private int COST_TIME_ARC = 1000;
    private int COST_TIME_LINE = 1500;
    private final long LOADING_ANIM_DURATION = 1760;
    private String[] colorArray = new String[]{"chocolate", "coral", "dodgerBlue", "fireBrick", "forestGreeen", "LimeGreen", "peru", "tomato"};
    private float mAnimHeight;
    private Animator mAnimSet;
    private int mAnimationDistance;
    private boolean mArcAnimationRun = false;
    private float mCentX;
    private float mCentY;
    private RectF mCircleBounds = null;
    private float mCurrentCircleY = 0.0f;
    private int mCurrentOverScrollDistance = 0;
    private int mDrawType = DRAW_ARC_VIEW;
    private int mEndLine;
    private TypedArray mEndPointArray;
    private int mExtraOffset;
    private float mFontTop = 0.0f;
    private int mForegroundColor = INVALID;
    private float mHalfAnimHeight = 0.0f;
    private Handler mHandler = new Handler();
    private boolean mIsSpringBack = false;
    private boolean mLineAnimationRun = false;
    private RectF mLineRect = null;
    private float mMaxPullHeight;
    private float mMinPullHeight;
    private int mNewForegroundColor;
    private int mNewForegroundColorAlpha;
    private int mNewPaintArcBackColor;
    private int mNewPaintArcBackColorAlpha;
    private float mNewY = 0.0f;
    private int mOverscrollDistance;
    private Paint mPaint = null;
    private Paint mPaintArc = null;
    private Paint mPaintArcBack = null;
    private final int mPaintArcBackDefaultColor = 637534208;
    private Paint mPaintLine = null;
    private Paint mPaintLineBack = null;
    private float mPaintLineWidth = 0.0f;
    private float mPaintOffset = 3.0f;
    private String mPull2Refresh;
    private String mPull2RefreshDefault;
    private String mPullGoRefresh;
    private float mRadius = BitmapDescriptorFactory.HUE_ORANGE;
    private String mRefreshing;
    private float mRingWidth = 5.0f;
    private Runnable mRunnable = new Runnable() {
        public void run() {
            if (PullRefreshAnimationView.this.mArcAnimationRun || PullRefreshAnimationView.this.mLineAnimationRun) {
                if (PullRefreshAnimationView.this.mLineAnimationRun) {
                    PullRefreshAnimationView.this.mView.postInvalidate(0, PullRefreshAnimationView.this.mExtraOffset, PullRefreshAnimationView.this.mView.getWidth(), ((int) PullRefreshAnimationView.this.mPaintLineWidth) + PullRefreshAnimationView.this.mExtraOffset);
                    PullRefreshAnimationView.this.mHandler.postDelayed(this, (long) (PullRefreshAnimationView.this.COST_TIME_LINE / PullRefreshAnimationView.this.mTotalFrame));
                } else if (PullRefreshAnimationView.this.mArcAnimationRun) {
                    PullRefreshAnimationView.this.mView.postInvalidate((int) (((((float) (PullRefreshAnimationView.this.mView.getWidth() / 2)) - PullRefreshAnimationView.this.mRadius) - PullRefreshAnimationView.this.mRingWidth) - 10.0f), (int) (((PullRefreshAnimationView.this.mCircleBounds.top - 5.0f) + ((float) PullRefreshAnimationView.this.mView.getScrollY())) + PullRefreshAnimationView.this.mCurrentCircleY), (int) (((((float) (PullRefreshAnimationView.this.mView.getWidth() / 2)) + PullRefreshAnimationView.this.mRadius) + PullRefreshAnimationView.this.mRingWidth) + 10.0f), (int) (((PullRefreshAnimationView.this.mCircleBounds.bottom + 5.0f) + ((float) PullRefreshAnimationView.this.mView.getScrollY())) + PullRefreshAnimationView.this.mCurrentCircleY));
                    PullRefreshAnimationView.this.mHandler.postDelayed(this, (long) (PullRefreshAnimationView.this.COST_TIME_ARC / PullRefreshAnimationView.this.mTotalFrame));
                }
            }
            if (!PullRefreshAnimationView.this.mArcAnimationRun && PullRefreshAnimationView.this.mAnimSet != null) {
                PullRefreshAnimationView.this.mAnimSet.cancel();
                PullRefreshAnimationView.this.mAnimSet = null;
            }
        }
    };
    private int mShowArcDistance;
    private float mStartAngle;
    private int mStartLine;
    private TypedArray mStartPointArray;
    private long mStartTime;
    private float mSweepAngle;
    private int mTextColor = -1;
    private int mTextColorAlpha;
    private int mTextMarginTop = 30;
    private int mTextSize = 40;
    private int mTotalFrame;
    private View mView = null;

    public PullRefreshAnimationView(Context context, int i, int i2, View view) {
        this.mAnimationDistance = context.getResources().getDimensionPixelOffset(R.dimen.mc_pullRefresh_holdheight);
        this.mOverscrollDistance = context.getResources().getDimensionPixelOffset(R.dimen.mc_pullRefresh_overscrollheight);
        this.mShowArcDistance = context.getResources().getDimensionPixelOffset(R.dimen.mc_pullRefresh_showarcheight);
        this.mRadius = context.getResources().getDimension(R.dimen.mc_pullRefresh_radius);
        this.mRingWidth = context.getResources().getDimension(R.dimen.mc_pullRefresh_ringwidth);
        this.mMaxPullHeight = context.getResources().getDimension(R.dimen.mc_pullRefresh_maxheight);
        this.mMinPullHeight = context.getResources().getDimension(R.dimen.mc_pullRefresh_minheight);
        this.mAnimHeight = context.getResources().getDimension(R.dimen.mc_pullRefresh_animheight);
        this.mTextSize = context.getResources().getDimensionPixelOffset(R.dimen.mc_pullRefresh_textsize);
        this.mTextMarginTop = context.getResources().getDimensionPixelOffset(R.dimen.mc_pullRefresh_textmargintop);
        this.mStartPointArray = context.getResources().obtainTypedArray(R.array.mc_pullline_move_start);
        this.mEndPointArray = context.getResources().obtainTypedArray(R.array.mc_pullline_move_end);
        this.mTotalFrame = this.mEndPointArray.length();
        this.mPaintOffset = context.getResources().getDimension(R.dimen.mc_pullRefresh_paintoffset);
        this.mDrawType = i2;
        this.mForegroundColor = i;
        String string = context.getResources().getString(R.string.mc_pull_refresh);
        this.mPull2Refresh = string;
        this.mPull2RefreshDefault = string;
        this.mRefreshing = context.getResources().getString(R.string.mc_is_Refreshing);
        this.mPullGoRefresh = context.getResources().getString(R.string.mc_go_Refreshing);
        this.mStartLine = 0;
        this.mPaint = new Paint(1);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(this.mTextColor);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextAlign(Align.CENTER);
        this.mPaint.setTextSize((float) this.mTextSize);
        this.mPaintArc = new Paint(1);
        this.mPaintArc.setAntiAlias(true);
        this.mPaintArc.setColor(this.mForegroundColor);
        this.mPaintArc.setStyle(Style.STROKE);
        this.mPaintArc.setStrokeCap(Cap.ROUND);
        this.mPaintArc.setStrokeWidth(this.mRingWidth);
        this.mPaintArcBack = new Paint(1);
        this.mPaintArcBack.setAntiAlias(true);
        this.mPaintArcBack.setColor(637534208);
        this.mPaintArcBack.setStyle(Style.STROKE);
        this.mPaintArcBack.setStrokeWidth(this.mRingWidth);
        this.mPaintLine = new Paint(1);
        this.mPaintLine.setAntiAlias(true);
        this.mPaintLine.setColor(this.mForegroundColor);
        this.mPaintLine.setStyle(Style.FILL);
        this.mPaintLineBack = new Paint(1);
        this.mPaintLineBack.setAntiAlias(true);
        this.mPaintLineBack.setColor(-1);
        this.mPaintLineBack.setStyle(Style.FILL);
        this.mPaintLineBack.setAlpha(102);
        this.mNewForegroundColor = this.mForegroundColor;
        this.mNewForegroundColorAlpha = Color.alpha(this.mNewForegroundColor);
        this.mNewPaintArcBackColor = 637534208;
        this.mNewPaintArcBackColorAlpha = Color.alpha(this.mNewPaintArcBackColor);
        this.mTextColorAlpha = Color.alpha(this.mTextColor);
        this.mFontTop = -this.mPaint.getFontMetrics().ascent;
        this.mHalfAnimHeight = (((float) (this.mShowArcDistance + this.mTextMarginTop)) + this.mPaint.getTextSize()) / CircleProgressBar.BAR_WIDTH_DEF_DIP;
        initView(view);
    }

    public void initView(View view) {
        this.mView = view;
        this.mCircleBounds = new RectF();
        this.mLineRect = new RectF(0.0f, 0.0f, (float) this.mView.getWidth(), 100.0f);
        this.mCentX = this.mView.getX() + ((float) (this.mView.getWidth() / 2));
        this.mCentY = (((this.mView.getY() - this.mRadius) - this.mRingWidth) - ((float) this.mTextMarginTop)) - this.mPaint.getTextSize();
        this.mCircleBounds.left = (this.mCentX - this.mRadius) - (this.mRingWidth / CircleProgressBar.BAR_WIDTH_DEF_DIP);
        this.mCircleBounds.top = (this.mCentY - this.mRadius) - (this.mRingWidth / CircleProgressBar.BAR_WIDTH_DEF_DIP);
        this.mCircleBounds.right = (this.mCentX + this.mRadius) + (this.mRingWidth / CircleProgressBar.BAR_WIDTH_DEF_DIP);
        this.mCircleBounds.bottom = (this.mCentY + this.mRadius) + (this.mRingWidth / CircleProgressBar.BAR_WIDTH_DEF_DIP);
    }

    @SuppressLint({"NewApi"})
    private void drawLineView(Canvas canvas) {
        float f = 0.0f;
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        int save = canvas.save();
        if (((float) this.mCurrentOverScrollDistance) <= this.mMaxPullHeight / 4.0f) {
            this.mPaintLineWidth = (float) this.mCurrentOverScrollDistance;
        } else if (((float) this.mCurrentOverScrollDistance) <= this.mMaxPullHeight / 4.0f || this.mCurrentOverScrollDistance > this.mAnimationDistance) {
            this.mPaintLineWidth = this.mMaxPullHeight;
        } else {
            this.mPaintLineWidth = (this.mMaxPullHeight / 4.0f) + ((((this.mMaxPullHeight * 3.0f) / 4.0f) * (((float) this.mCurrentOverScrollDistance) - (this.mMaxPullHeight / 4.0f))) / (((float) this.mAnimationDistance) - (this.mMaxPullHeight / 4.0f)));
        }
        canvas.translate(0.0f, (float) this.mView.getScrollY());
        Canvas canvas2 = canvas;
        canvas2.drawRect(0.0f, (float) this.mExtraOffset, (float) this.mView.getWidth(), this.mPaintLineWidth + ((float) this.mExtraOffset), this.mPaintLine);
        if (this.mLineAnimationRun) {
            float f2;
            int arrayIndex = getArrayIndex((currentAnimationTimeMillis - this.mStartTime) % ((long) this.COST_TIME_LINE));
            if (arrayIndex >= this.mStartPointArray.length() || arrayIndex >= this.mEndPointArray.length()) {
                f2 = 0.0f;
            } else {
                f2 = ((float) this.mView.getWidth()) * this.mStartPointArray.getFloat(arrayIndex, 0.0f);
                f = ((float) this.mView.getWidth()) * this.mEndPointArray.getFloat(arrayIndex, 0.0f);
            }
            canvas.drawRect(f, (float) this.mExtraOffset, f2, (((float) this.mExtraOffset) + this.mPaintLineWidth) - this.mPaintOffset, this.mPaintLineBack);
        }
        canvas.restoreToCount(save);
    }

    private void drawArcView(Canvas canvas) {
        this.mPaintArc.setAlpha(this.mNewForegroundColorAlpha);
        this.mPaintArcBack.setAlpha(this.mNewPaintArcBackColorAlpha);
        this.mPaint.setAlpha(this.mTextColorAlpha);
        if (this.mCurrentOverScrollDistance < this.mAnimationDistance) {
            this.mCurrentCircleY = ((float) this.mExtraOffset) + (((float) this.mCurrentOverScrollDistance) - this.mHalfAnimHeight);
        } else {
            this.mCurrentCircleY = ((float) this.mExtraOffset) + (((float) this.mAnimationDistance) - this.mHalfAnimHeight);
        }
        int save = canvas.save();
        if (this.mCurrentOverScrollDistance >= this.mShowArcDistance) {
            float f;
            canvas.translate((float) (this.mView.getWidth() / 2), this.mCurrentCircleY + ((float) this.mView.getScrollY()));
            if (this.mCurrentOverScrollDistance <= this.mAnimationDistance && this.mCurrentOverScrollDistance >= this.mShowArcDistance) {
                f = (float) (((this.mCurrentOverScrollDistance - this.mShowArcDistance) * 360) / (this.mAnimationDistance - this.mShowArcDistance));
            } else if (this.mCurrentOverScrollDistance < this.mShowArcDistance) {
                f = 0.0f;
            } else if (this.mCurrentOverScrollDistance > this.mAnimationDistance) {
                f = 360.0f;
            } else {
                f = 0.0f;
            }
            float f2 = this.mCentX;
            this.mNewY = (((this.mCentY + this.mRadius) + this.mRingWidth) + ((float) this.mTextMarginTop)) + this.mFontTop;
            float f3 = f / 360.0f;
            this.mPaintArcBack.setAlpha((int) (((float) this.mNewPaintArcBackColorAlpha) * f3));
            canvas.drawArc(this.mCircleBounds, -90.0f, 360.0f, false, this.mPaintArcBack);
            if (this.mArcAnimationRun) {
                canvas.drawArc(this.mCircleBounds, this.mStartAngle, this.mSweepAngle, false, this.mPaintArc);
                canvas.drawText(this.mRefreshing, f2, this.mNewY, this.mPaint);
            } else if (this.mCurrentOverScrollDistance >= this.mAnimationDistance && !this.mIsSpringBack) {
                canvas.drawText(this.mPullGoRefresh, f2, this.mNewY, this.mPaint);
                canvas.drawArc(this.mCircleBounds, -90.0f, f, false, this.mPaintArc);
            } else if (this.mIsSpringBack) {
                this.mPaintArc.setAlpha((int) (((float) this.mNewForegroundColorAlpha) * f3));
                this.mPaint.setAlpha((int) (((float) this.mTextColorAlpha) * f3));
                canvas.drawArc(this.mCircleBounds, this.mStartAngle, this.mSweepAngle, false, this.mPaintArc);
                canvas.drawText(this.mRefreshing, f2, this.mNewY, this.mPaint);
            } else {
                this.mPaintArc.setAlpha((int) (((float) this.mNewForegroundColorAlpha) * f3));
                this.mPaint.setAlpha((int) (((float) this.mTextColorAlpha) * f3));
                canvas.drawText(this.mPull2Refresh, f2, this.mNewY, this.mPaint);
                canvas.drawArc(this.mCircleBounds, -90.0f, f, false, this.mPaintArc);
            }
            canvas.restoreToCount(save);
        }
    }

    private int getArrayIndex(long j) {
        return (int) ((((long) this.mTotalFrame) * j) / ((long) this.COST_TIME_LINE));
    }

    public void setCurrentOverScrollDistance(int i, Canvas canvas) {
        if (i > 0) {
            this.mCurrentOverScrollDistance = Math.abs(i);
        } else {
            this.mCurrentOverScrollDistance = Math.abs(i);
        }
        if (DRAW_ARC_VIEW == this.mDrawType) {
            if (this.mCurrentOverScrollDistance > this.mShowArcDistance && this.mCurrentOverScrollDistance < this.mAnimationDistance) {
                this.mArcAnimationRun = false;
            } else if (this.mCurrentOverScrollDistance < this.mAnimationDistance) {
                this.mArcAnimationRun = false;
            }
            drawArcView(canvas);
        } else if (DRAW_LINE_VIEW == this.mDrawType) {
            if (((float) this.mCurrentOverScrollDistance) < (this.mMaxPullHeight / 4.0f) - 1.0f) {
                this.mLineAnimationRun = false;
            }
            drawLineView(canvas);
        }
    }

    public void resetSpringFlag(boolean z) {
        this.mIsSpringBack = z;
    }

    public void stopDrawArcAnimation() {
        this.mArcAnimationRun = false;
        this.mIsSpringBack = true;
    }

    public void stopDrawLineAnimation() {
        this.mLineAnimationRun = false;
    }

    public void setStartTime() {
        if (!this.mArcAnimationRun && !this.mLineAnimationRun) {
            if (this.mDrawType == DRAW_LINE_VIEW) {
                this.mLineAnimationRun = true;
                this.mArcAnimationRun = false;
            } else if (this.mDrawType == DRAW_ARC_VIEW) {
                this.mLineAnimationRun = false;
                this.mArcAnimationRun = true;
            }
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            if (this.mArcAnimationRun && this.mAnimSet == null) {
                this.mAnimSet = createRefreshAnimator();
                this.mAnimSet.start();
            }
            if (this.mLineAnimationRun || this.mArcAnimationRun) {
                this.mHandler.postDelayed(this.mRunnable, 1);
            }
        }
    }

    public void setExtraOffset(int i) {
        this.mExtraOffset = i;
    }

    public void setOverScrollDistance(int i) {
        this.mOverscrollDistance = i;
    }

    public void setAnimationDistance(int i) {
        this.mAnimationDistance = i;
    }

    public void setTextColor(int i) {
        this.mTextColor = i;
        this.mTextColorAlpha = Color.alpha(this.mTextColor);
        if (this.mPaint != null) {
            this.mPaint.setColor(this.mTextColor);
        }
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public void setLastRefreshTime(String str) {
        this.mPull2Refresh = str;
    }

    public void setLastRefreshTimeDefault() {
        this.mPull2Refresh = this.mPull2RefreshDefault;
    }

    public void removeCallbacksAndMessages() {
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages(null);
        }
        if (this.mAnimSet != null) {
            this.mAnimSet.end();
            this.mAnimSet = null;
        }
    }

    public void setPaintArcColor(int i) {
        if (this.mPaintArc != null) {
            this.mPaintArc.setColor(i);
            this.mNewForegroundColor = i;
            this.mNewForegroundColorAlpha = Color.alpha(this.mNewForegroundColor);
        }
    }

    public int getPaintArcColor() {
        return this.mNewForegroundColor;
    }

    public void setPaintArcBackColor(int i) {
        if (this.mPaintArcBack != null) {
            this.mPaintArcBack.setColor(i);
            this.mNewPaintArcBackColor = i;
            this.mNewPaintArcBackColorAlpha = Color.alpha(this.mNewPaintArcBackColor);
        }
    }

    public int getPaintArcBackColor() {
        return this.mNewPaintArcBackColor;
    }

    public void resetRingColor() {
        if (this.mPaintArc != null && this.mPaintArcBack != null) {
            this.mPaintArc.setColor(this.mForegroundColor);
            this.mPaintArcBack.setColor(637534208);
            this.mNewForegroundColor = this.mForegroundColor;
            this.mNewPaintArcBackColor = 637534208;
            this.mNewForegroundColorAlpha = Color.alpha(this.mNewForegroundColor);
            this.mNewPaintArcBackColorAlpha = Color.alpha(this.mNewPaintArcBackColor);
        }
    }

    private Animator createRefreshAnimator() {
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("startAngle", new float[]{-90.0f, BitmapDescriptorFactory.HUE_VIOLET});
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat("sweepAngle", new float[]{-360.0f, 0.0f});
        Animator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofFloat, ofFloat2});
        ofPropertyValuesHolder.setDuration(1120);
        ofPropertyValuesHolder.setInterpolator(new LinearInterpolator());
        Animator createLoadingAnimator = createLoadingAnimator();
        Animator animatorSet = new AnimatorSet();
        animatorSet.play(ofPropertyValuesHolder).before(createLoadingAnimator);
        return animatorSet;
    }

    private Animator createLoadingAnimator() {
        Keyframe ofFloat = Keyframe.ofFloat(0.0f, -90.0f);
        Keyframe ofFloat2 = Keyframe.ofFloat(FastBlurParameters.DEFAULT_LEVEL, BitmapDescriptorFactory.HUE_ROSE);
        Keyframe ofFloat3 = Keyframe.ofFloat(1.0f, 630.0f);
        PropertyValuesHolder ofKeyframe = PropertyValuesHolder.ofKeyframe("startAngle", new Keyframe[]{ofFloat, ofFloat2, ofFloat3});
        PropertyValuesHolder ofFloat4 = PropertyValuesHolder.ofFloat("sweepAngle", new float[]{0.2f, -150.0f, 0.0f});
        Animator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofKeyframe, ofFloat4});
        ofPropertyValuesHolder.setDuration(1760);
        ofPropertyValuesHolder.setInterpolator(new LinearInterpolator());
        ofPropertyValuesHolder.setRepeatCount(-1);
        return ofPropertyValuesHolder;
    }

    public float getSweepAngle() {
        return this.mSweepAngle;
    }

    public void setSweepAngle(float f) {
        this.mSweepAngle = f;
        Log.d(null, "anim-sw:" + f);
    }

    public float getStartAngle() {
        return this.mStartAngle;
    }

    public void setStartAngle(float f) {
        this.mStartAngle = f;
    }
}
