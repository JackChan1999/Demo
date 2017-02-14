package com.meizu.common.widget;

import android.content.Context;
import android.util.FloatMath;
import android.util.Log;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import cn.com.xy.sms.sdk.ui.popu.util.XySdkUtil;

public class OverScroller {
    private static final int DEFAULT_DURATION = 250;
    private static final int FLING_MODE = 1;
    private static final int SCROLL_MODE = 0;
    private final boolean mFlywheel;
    private Interpolator mInterpolator;
    private int mMode;
    private final SplineOverScroller mScrollerX;
    private final SplineOverScroller mScrollerY;

    static class SplineOverScroller {
        private static final int BALLISTIC = 2;
        private static final int CUBIC = 1;
        private static final int DECELERATIONSPEED_FAST = 50;
        private static final int DECELERATIONSPEED_SLOW = 25;
        private static float DECELERATION_RATE = ((float) (Math.log(0.78d) / Math.log(0.9d)));
        private static final float END_TENSION = 1.0f;
        private static final float GRAVITY = 2000.0f;
        private static final float INFLEXION = 0.35f;
        private static final int MAXFLINGTESTCOUNT = 4;
        private static final int MAXUPDATECOUNT = 5;
        private static final int NB_SAMPLES = 100;
        private static final int OVERSCROLL_SPRINGBACK_DURATION = 618;
        private static final float P1 = 0.175f;
        private static final float P2 = 0.35000002f;
        private static final int SPLINE = 0;
        private static final float[] SPLINE_POSITION = new float[101];
        private static final float[] SPLINE_TIME = new float[101];
        private static final float START_TENSION = 0.5f;
        private static final String tag = "OverScroller";
        private long mAverageTime = 0;
        private float mCoeffDeceleration = 0.0f;
        private float mCurrVelocity;
        private int mCurrentPosition;
        private float mDeceleration;
        private int mDecelerationSpeed = 50;
        private float mDelta = 0.0f;
        private int mDuration;
        private boolean mEnableOverScrollForMz = false;
        private int mFinal;
        private boolean mFinished = true;
        private float mFlingFriction = ViewConfiguration.getScrollFriction();
        private int mFlingTestCount = 1;
        private int mIterateCount = 0;
        private float mLastDistance = 0.0f;
        private int mOver;
        private float mPhysicalCoeff;
        private boolean mSmoothFling = false;
        private int mSplineDistance;
        private int mSplineDuration;
        private int mSpringDistance = 0;
        private int mSpringbackEnd = 0;
        private int mStart;
        private long mStartTime;
        private int mState = 0;
        private int mUpdateCount = 0;
        private int mVelocity;

        static {
            float f = 0.0f;
            int i = 0;
            float f2 = 0.0f;
            while (i < 100) {
                float f3;
                float f4 = ((float) i) / 100.0f;
                float f5 = 1.0f;
                float f6 = f2;
                while (true) {
                    f2 = ((f5 - f6) / CircleProgressBar.BAR_WIDTH_DEF_DIP) + f6;
                    f3 = (3.0f * f2) * (1.0f - f2);
                    float f7 = ((((1.0f - f2) * P1) + (P2 * f2)) * f3) + ((f2 * f2) * f2);
                    if (((double) Math.abs(f7 - f4)) < 1.0E-5d) {
                        break;
                    } else if (f7 > f4) {
                        f5 = f2;
                    } else {
                        f6 = f2;
                    }
                }
                SPLINE_POSITION[i] = (f2 * (f2 * f2)) + (f3 * (((1.0f - f2) * 0.5f) + f2));
                f5 = 1.0f;
                while (true) {
                    f2 = ((f5 - f) / CircleProgressBar.BAR_WIDTH_DEF_DIP) + f;
                    f3 = (3.0f * f2) * (1.0f - f2);
                    f7 = ((((1.0f - f2) * 0.5f) + f2) * f3) + ((f2 * f2) * f2);
                    if (((double) Math.abs(f7 - f4)) < 1.0E-5d) {
                        break;
                    } else if (f7 > f4) {
                        f5 = f2;
                    } else {
                        f = f2;
                    }
                }
                SPLINE_TIME[i] = (f2 * (f2 * f2)) + ((((1.0f - f2) * P1) + (P2 * f2)) * f3);
                i++;
                f2 = f6;
            }
            float[] fArr = SPLINE_POSITION;
            SPLINE_TIME[100] = 1.0f;
            fArr[100] = 1.0f;
        }

        void setFriction(float f) {
            this.mFlingFriction = f;
        }

        SplineOverScroller(Context context) {
            this.mPhysicalCoeff = ((context.getResources().getDisplayMetrics().density * 160.0f) * 386.0878f) * 0.84f;
            this.mFlingTestCount = 0;
            this.mAverageTime = 0;
            this.mSmoothFling = false;
        }

        void updateScroll(float f) {
            this.mCurrentPosition = this.mStart + Math.round(((float) (this.mFinal - this.mStart)) * f);
        }

        private static float getDeceleration(int i) {
            return i > 0 ? -2000.0f : GRAVITY;
        }

        private void adjustDuration(int i, int i2, int i3) {
            float abs = Math.abs(((float) (i3 - i)) / ((float) (i2 - i)));
            int i4 = (int) (100.0f * abs);
            if (i4 < 100) {
                float f = ((float) i4) / 100.0f;
                float f2 = ((float) (i4 + 1)) / 100.0f;
                float f3 = SPLINE_TIME[i4];
                this.mDuration = (int) (((((abs - f) / (f2 - f)) * (SPLINE_TIME[i4 + 1] - f3)) + f3) * ((float) this.mDuration));
            }
        }

        void startScroll(int i, int i2, int i3) {
            this.mFinished = false;
            this.mStart = i;
            this.mFinal = i + i2;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mDuration = i3;
            this.mDeceleration = 0.0f;
            this.mVelocity = 0;
        }

        void finish() {
            this.mCurrentPosition = this.mFinal;
            this.mFinished = true;
        }

        void setFinalPosition(int i) {
            this.mFinal = i;
            this.mFinished = false;
        }

        void extendDuration(int i) {
            this.mDuration = ((int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime)) + i;
            this.mFinished = false;
        }

        boolean springback(int i, int i2, int i3) {
            this.mFinished = true;
            this.mFinal = i;
            this.mStart = i;
            this.mVelocity = 0;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mDuration = 0;
            if (i < i2) {
                startSpringback(i, i2, 0);
            } else if (i > i3) {
                startSpringback(i, i3, 0);
            }
            if (this.mFinished) {
                return false;
            }
            return true;
        }

        private void startSpringback(int i, int i2, int i3) {
            this.mFinished = false;
            this.mState = 1;
            this.mStart = i;
            this.mFinal = i2;
            int i4 = i - i2;
            this.mDeceleration = getDeceleration(i4);
            this.mVelocity = -i4;
            this.mOver = Math.abs(i4);
            if (this.mEnableOverScrollForMz) {
                this.mDuration = OVERSCROLL_SPRINGBACK_DURATION;
            } else {
                this.mDuration = (int) (Math.sqrt((((double) i4) * -2.0d) / ((double) this.mDeceleration)) * 1000.0d);
            }
        }

        void fling(int i, int i2, int i3, int i4, int i5) {
            this.mOver = i5;
            this.mFinished = false;
            this.mVelocity = i2;
            this.mCurrVelocity = (float) i2;
            this.mSplineDuration = 0;
            this.mDuration = 0;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mStart = i;
            this.mCurrentPosition = i;
            if (i > i4 || i < i3) {
                startAfterEdge(i, i3, i4, i2);
                return;
            }
            this.mState = 0;
            double d = 0.0d;
            if (i2 != 0) {
                int splineFlingDuration = getSplineFlingDuration(i2);
                this.mSplineDuration = splineFlingDuration;
                this.mDuration = splineFlingDuration;
                d = getSplineFlingDistance(i2);
            }
            if (this.mEnableOverScrollForMz && this.mFlingTestCount >= 4 && this.mAverageTime > 40) {
                this.mSmoothFling = false;
            }
            this.mFlingTestCount++;
            this.mUpdateCount = 0;
            if (this.mSmoothFling) {
                if (this.mAverageTime < 20) {
                    this.mDecelerationSpeed = 50;
                } else if (this.mAverageTime < 40) {
                    this.mDecelerationSpeed = 25;
                }
                int abs = Math.abs(this.mVelocity);
                this.mIterateCount = 0;
                this.mLastDistance = 0.0f;
                this.mCoeffDeceleration = 0.97f;
                splineFlingDuration = 0;
                this.mDelta = (1.0f * ((float) this.mVelocity)) / ((float) this.mDecelerationSpeed);
                while (((int) (((double) this.mDelta) * Math.pow((double) this.mCoeffDeceleration, (double) splineFlingDuration))) != 0) {
                    splineFlingDuration++;
                }
                this.mIterateCount = splineFlingDuration;
                double pow = (((double) this.mDelta) * (1.0d - Math.pow((double) this.mCoeffDeceleration, (double) splineFlingDuration))) / ((double) (1.0f - this.mCoeffDeceleration));
                if (abs > 2000) {
                    this.mDuration = 5000;
                } else if (abs < XySdkUtil.DUOQU_BUBBLE_DATA_CACHE_SIZE) {
                    this.mDuration = 0;
                } else {
                    this.mDuration = 3000;
                }
                this.mFinal = this.mStart + ((int) pow);
                return;
            }
            this.mSplineDistance = (int) (d * ((double) Math.signum((float) i2)));
            this.mFinal = this.mSplineDistance + i;
            if (this.mFinal < i3) {
                adjustDuration(this.mStart, this.mFinal, i3);
                this.mFinal = i3;
            }
            if (this.mFinal > i4) {
                adjustDuration(this.mStart, this.mFinal, i4);
                this.mFinal = i4;
            }
        }

        private double getSplineDeceleration(int i) {
            return Math.log((double) ((0.35f * ((float) Math.abs(i))) / (this.mFlingFriction * this.mPhysicalCoeff)));
        }

        private double getSplineFlingDistance(int i) {
            return Math.exp(getSplineDeceleration(i) * (((double) DECELERATION_RATE) / (((double) DECELERATION_RATE) - 1.0d))) * ((double) (this.mFlingFriction * this.mPhysicalCoeff));
        }

        private int getSplineFlingDuration(int i) {
            return (int) (Math.exp(getSplineDeceleration(i) / (((double) DECELERATION_RATE) - 1.0d)) * 1000.0d);
        }

        private void fitOnBounceCurve(int i, int i2, int i3) {
            float sqrt = (float) Math.sqrt((((double) (((((float) (i3 * i3)) / CircleProgressBar.BAR_WIDTH_DEF_DIP) / Math.abs(this.mDeceleration)) + ((float) Math.abs(i2 - i)))) * 2.0d) / ((double) Math.abs(this.mDeceleration)));
            this.mStartTime -= (long) ((int) ((sqrt - (((float) (-i3)) / this.mDeceleration)) * 1000.0f));
            this.mStart = i2;
            this.mVelocity = (int) ((-this.mDeceleration) * sqrt);
        }

        private void startBounceAfterEdge(int i, int i2, int i3) {
            int i4;
            if (i3 == 0) {
                i4 = i - i2;
            } else {
                i4 = i3;
            }
            this.mDeceleration = getDeceleration(i4);
            fitOnBounceCurve(i, i2, i3);
            onEdgeReached();
        }

        private void startAfterEdge(int i, int i2, int i3, int i4) {
            boolean z = true;
            if (i <= i2 || i >= i3) {
                int i5;
                boolean z2 = i > i3;
                if (z2) {
                    i5 = i3;
                } else {
                    i5 = i2;
                }
                int i6 = i - i5;
                if (i6 * i4 < 0) {
                    z = false;
                }
                if (z) {
                    startBounceAfterEdge(i, i5, i4);
                    return;
                } else if (getSplineFlingDistance(i4) > ((double) Math.abs(i6))) {
                    fling(i, i4, z2 ? i2 : i, z2 ? i : i3, this.mOver);
                    return;
                } else {
                    startSpringback(i, i5, i4);
                    return;
                }
            }
            Log.e(tag, "startAfterEdge called from a valid position");
            this.mFinished = true;
        }

        void notifyEdgeReached(int i, int i2, int i3) {
            if (this.mState == 0) {
                this.mOver = i3;
                this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                startAfterEdge(i, i2, i2, (int) this.mCurrVelocity);
            }
        }

        private void onEdgeReached() {
            float abs = ((float) (this.mVelocity * this.mVelocity)) / (Math.abs(this.mDeceleration) * CircleProgressBar.BAR_WIDTH_DEF_DIP);
            if (this.mEnableOverScrollForMz) {
                this.mCoeffDeceleration = 0.5f;
                this.mLastDistance = 0.0f;
                this.mFinished = false;
                this.mDuration = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                this.mDelta = this.mCurrVelocity / 150.0f;
                int i = 0;
                while (((int) (((double) this.mDelta) * Math.pow((double) this.mCoeffDeceleration, (double) i))) != 0) {
                    i++;
                }
                this.mIterateCount = i;
                abs = (float) (((1.0d - Math.pow((double) this.mCoeffDeceleration, (double) i)) * ((double) this.mDelta)) / ((double) (1.0f - this.mCoeffDeceleration)));
                this.mFinal = (int) (((float) this.mStart) + abs);
            } else {
                float signum = Math.signum((float) this.mVelocity);
                if (abs > ((float) this.mOver)) {
                    this.mDeceleration = (((-signum) * ((float) this.mVelocity)) * ((float) this.mVelocity)) / (((float) this.mOver) * CircleProgressBar.BAR_WIDTH_DEF_DIP);
                    abs = (float) this.mOver;
                }
                this.mFinal = ((int) (this.mVelocity > 0 ? abs : -abs)) + this.mStart;
                this.mDuration = -((int) ((1000.0f * ((float) this.mVelocity)) / this.mDeceleration));
            }
            this.mOver = (int) abs;
            this.mState = 2;
        }

        boolean continueWhenFinished() {
            switch (this.mState) {
                case 0:
                    if (this.mDuration < this.mSplineDuration) {
                        this.mStart = this.mFinal;
                        this.mVelocity = (int) this.mCurrVelocity;
                        this.mDeceleration = getDeceleration(this.mVelocity);
                        this.mStartTime += (long) this.mDuration;
                        onEdgeReached();
                        break;
                    }
                    return false;
                case 1:
                    return false;
                case 2:
                    if (this.mEnableOverScrollForMz) {
                        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                    } else {
                        this.mStartTime += (long) this.mDuration;
                    }
                    startSpringback(this.mFinal, this.mStart, 0);
                    break;
            }
            update();
            return true;
        }

        boolean update() {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis() - this.mStartTime;
            if (currentAnimationTimeMillis > ((long) this.mDuration)) {
                if (this.mEnableOverScrollForMz) {
                    if (this.mSmoothFling) {
                        if (this.mIterateCount != 0) {
                            this.mFinal = this.mCurrentPosition;
                        }
                    } else if (this.mDuration < this.mSplineDuration && this.mFinal != this.mCurrentPosition) {
                        this.mCurrentPosition = this.mFinal;
                        return true;
                    }
                    this.mFinished = true;
                }
                return false;
            }
            double d = 0.0d;
            float f;
            float f2;
            switch (this.mState) {
                case 0:
                    if (!this.mSmoothFling) {
                        float f3 = ((float) currentAnimationTimeMillis) / ((float) this.mSplineDuration);
                        int i = (int) (100.0f * f3);
                        float f4 = 1.0f;
                        f = 0.0f;
                        if (i < 100) {
                            f4 = ((float) i) / 100.0f;
                            f = ((float) (i + 1)) / 100.0f;
                            f2 = SPLINE_POSITION[i];
                            f = (SPLINE_POSITION[i + 1] - f2) / (f - f4);
                            f4 = ((f3 - f4) * f) + f2;
                        }
                        double d2 = (double) (f4 * ((float) this.mSplineDistance));
                        this.mCurrVelocity = ((f * ((float) this.mSplineDistance)) / ((float) this.mSplineDuration)) * 1000.0f;
                        d = d2;
                        break;
                    }
                    this.mUpdateCount++;
                    if (this.mEnableOverScrollForMz && this.mUpdateCount == 5) {
                        this.mAverageTime = (this.mAverageTime + (currentAnimationTimeMillis / ((long) this.mUpdateCount))) / 2;
                    }
                    this.mCurrVelocity *= this.mCoeffDeceleration;
                    d = (double) (this.mLastDistance + this.mDelta);
                    this.mDelta *= this.mCoeffDeceleration;
                    this.mLastDistance = (float) d;
                    break;
                case 1:
                    f2 = ((float) currentAnimationTimeMillis) / ((float) this.mDuration);
                    float f5 = f2 * f2;
                    float signum = Math.signum((float) this.mVelocity);
                    if (!this.mEnableOverScrollForMz) {
                        d = (double) ((((float) this.mOver) * signum) * ((3.0f * f5) - ((CircleProgressBar.BAR_WIDTH_DEF_DIP * f2) * f5)));
                        this.mCurrVelocity = ((((float) this.mOver) * signum) * 6.0f) * ((-f2) + f5);
                        break;
                    }
                    d = (double) (((float) quintic(currentAnimationTimeMillis, this.mStart, this.mOver, (long) this.mDuration)) * signum);
                    break;
                case 2:
                    if (!this.mEnableOverScrollForMz) {
                        f = ((float) currentAnimationTimeMillis) / 1000.0f;
                        this.mCurrVelocity = ((float) this.mVelocity) + (this.mDeceleration * f);
                        d = (double) (((f * (this.mDeceleration * f)) / CircleProgressBar.BAR_WIDTH_DEF_DIP) + (((float) this.mVelocity) * f));
                        break;
                    }
                    this.mCurrVelocity *= this.mCoeffDeceleration;
                    d = (double) (this.mLastDistance + this.mDelta);
                    this.mDelta *= this.mCoeffDeceleration;
                    this.mLastDistance = (float) d;
                    break;
            }
            if (!this.mEnableOverScrollForMz) {
                this.mCurrentPosition = ((int) Math.round(d)) + this.mStart;
            } else if (this.mState != 0 || this.mSmoothFling) {
                this.mCurrentPosition = ((int) d) + this.mStart;
                if (this.mCurrentPosition == this.mFinal) {
                    return false;
                }
            } else {
                this.mCurrentPosition = ((int) Math.round(d)) + this.mStart;
                return true;
            }
            return true;
        }

        private int quintic(long j, int i, int i2, long j2) {
            return (int) Math.round((Math.pow((double) (((((float) j) * 1.0f) / ((float) j2)) - 1.0f), 5.0d) + 1.0d) * ((double) i2));
        }

        public void setEnableMZOverScroll(boolean z, boolean z2) {
            this.mEnableOverScrollForMz = z;
            this.mSmoothFling = z2;
        }
    }

    public OverScroller(Context context) {
        this(context, null);
    }

    public OverScroller(Context context, Interpolator interpolator) {
        this(context, interpolator, true);
    }

    public OverScroller(Context context, Interpolator interpolator, boolean z) {
        this.mInterpolator = interpolator;
        this.mFlywheel = z;
        this.mScrollerX = new SplineOverScroller(context);
        this.mScrollerY = new SplineOverScroller(context);
    }

    public OverScroller(Context context, Interpolator interpolator, float f, float f2) {
        this(context, interpolator, true);
    }

    public OverScroller(Context context, Interpolator interpolator, float f, float f2, boolean z) {
        this(context, interpolator, z);
    }

    void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public final void setFriction(float f) {
        this.mScrollerX.setFriction(f);
        this.mScrollerY.setFriction(f);
    }

    public final boolean isFinished() {
        return this.mScrollerX.mFinished && this.mScrollerY.mFinished;
    }

    public final void forceFinished(boolean z) {
        this.mScrollerX.mFinished = this.mScrollerY.mFinished = z;
    }

    public final int getCurrX() {
        return this.mScrollerX.mCurrentPosition;
    }

    public final int getCurrY() {
        return this.mScrollerY.mCurrentPosition;
    }

    public float getCurrVelocity() {
        return FloatMath.sqrt((this.mScrollerX.mCurrVelocity * this.mScrollerX.mCurrVelocity) + (this.mScrollerY.mCurrVelocity * this.mScrollerY.mCurrVelocity));
    }

    public final int getStartX() {
        return this.mScrollerX.mStart;
    }

    public final int getStartY() {
        return this.mScrollerY.mStart;
    }

    public final int getFinalX() {
        return this.mScrollerX.mFinal;
    }

    public final int getFinalY() {
        return this.mScrollerY.mFinal;
    }

    @Deprecated
    public final int getDuration() {
        return Math.max(this.mScrollerX.mDuration, this.mScrollerY.mDuration);
    }

    @Deprecated
    public void extendDuration(int i) {
        this.mScrollerX.extendDuration(i);
        this.mScrollerY.extendDuration(i);
    }

    @Deprecated
    public void setFinalX(int i) {
        this.mScrollerX.setFinalPosition(i);
    }

    @Deprecated
    public void setFinalY(int i) {
        this.mScrollerY.setFinalPosition(i);
    }

    public boolean computeScrollOffset() {
        if (isFinished()) {
            return false;
        }
        switch (this.mMode) {
            case 0:
                long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis() - this.mScrollerX.mStartTime;
                int access$500 = this.mScrollerX.mDuration;
                if (currentAnimationTimeMillis >= ((long) access$500)) {
                    abortAnimation();
                    break;
                }
                float f = ((float) currentAnimationTimeMillis) / ((float) access$500);
                if (this.mInterpolator == null) {
                    f = Scroller.viscousFluid(f);
                } else {
                    f = this.mInterpolator.getInterpolation(f);
                }
                this.mScrollerX.updateScroll(f);
                this.mScrollerY.updateScroll(f);
                break;
            case 1:
                if (!(this.mScrollerX.mFinished || this.mScrollerX.update() || this.mScrollerX.continueWhenFinished())) {
                    this.mScrollerX.finish();
                }
                if (!(this.mScrollerY.mFinished || this.mScrollerY.update() || this.mScrollerY.continueWhenFinished())) {
                    this.mScrollerY.finish();
                    break;
                }
        }
        return true;
    }

    public void startScroll(int i, int i2, int i3, int i4) {
        startScroll(i, i2, i3, i4, DEFAULT_DURATION);
    }

    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        this.mMode = 0;
        this.mScrollerX.startScroll(i, i3, i5);
        this.mScrollerY.startScroll(i2, i4, i5);
    }

    public boolean springBack(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mMode = 1;
        boolean springback = this.mScrollerX.springback(i, i3, i4);
        boolean springback2 = this.mScrollerY.springback(i2, i5, i6);
        if (springback || springback2) {
            return true;
        }
        return false;
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        fling(i, i2, i3, i4, i5, i6, i7, i8, 0, 0);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        int i11;
        if (this.mFlywheel && !isFinished()) {
            float access$200 = this.mScrollerX.mCurrVelocity;
            float access$2002 = this.mScrollerY.mCurrVelocity;
            if (Math.signum((float) i3) == Math.signum(access$200) && Math.signum((float) i4) == Math.signum(access$2002)) {
                i4 = (int) (((float) i4) + access$2002);
                i11 = (int) (access$200 + ((float) i3));
                this.mMode = 1;
                this.mScrollerX.fling(i, i11, i5, i6, i9);
                this.mScrollerY.fling(i2, i4, i7, i8, i10);
            }
        }
        i11 = i3;
        this.mMode = 1;
        this.mScrollerX.fling(i, i11, i5, i6, i9);
        this.mScrollerY.fling(i2, i4, i7, i8, i10);
    }

    public void notifyHorizontalEdgeReached(int i, int i2, int i3) {
        this.mScrollerX.notifyEdgeReached(i, i2, i3);
    }

    public void notifyVerticalEdgeReached(int i, int i2, int i3) {
        this.mScrollerY.notifyEdgeReached(i, i2, i3);
    }

    public boolean isOverScrolled() {
        return ((this.mScrollerX.mFinished || this.mScrollerX.mState == 0) && (this.mScrollerY.mFinished || this.mScrollerY.mState == 0)) ? false : true;
    }

    public void abortAnimation() {
        this.mScrollerX.finish();
        this.mScrollerY.finish();
    }

    public int timePassed() {
        return (int) (AnimationUtils.currentAnimationTimeMillis() - Math.min(this.mScrollerX.mStartTime, this.mScrollerY.mStartTime));
    }

    public boolean isScrollingInDirection(float f, float f2) {
        return !isFinished() && Math.signum(f) == Math.signum((float) (this.mScrollerX.mFinal - this.mScrollerX.mStart)) && Math.signum(f2) == Math.signum((float) (this.mScrollerY.mFinal - this.mScrollerY.mStart));
    }

    public void setEnableMZOverScroll(boolean z, boolean z2) {
        this.mScrollerX.setEnableMZOverScroll(z, z2);
        this.mScrollerY.setEnableMZOverScroll(z, z2);
    }
}
