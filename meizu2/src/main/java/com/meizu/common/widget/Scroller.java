package com.meizu.common.widget;

import android.content.Context;
import android.util.FloatMath;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

public class Scroller {
    private static float DECELERATION_RATE = ((float) (Math.log(0.78d) / Math.log(0.9d)));
    private static final int DEFAULT_DURATION = 250;
    private static final float END_TENSION = 1.0f;
    private static final int FLING_MODE = 1;
    private static final float INFLEXION = 0.35f;
    private static final int NB_SAMPLES = 100;
    private static final float P1 = 0.175f;
    private static final float P2 = 0.35000002f;
    private static final int SCROLL_MODE = 0;
    private static final float[] SPLINE_POSITION = new float[101];
    private static final float[] SPLINE_TIME = new float[101];
    private static final float START_TENSION = 0.5f;
    private static float sViscousFluidNormalize;
    private static float sViscousFluidScale = 8.0f;
    private float mCurrVelocity;
    private int mCurrX;
    private int mCurrY;
    private float mDeceleration;
    private float mDeltaX;
    private float mDeltaY;
    private int mDistance;
    private int mDuration;
    private float mDurationReciprocal;
    private int mFinalX;
    private int mFinalY;
    private boolean mFinished;
    private float mFlingFriction;
    private boolean mFlywheel;
    private Interpolator mInterpolator;
    private int mMaxX;
    private int mMaxY;
    private int mMinX;
    private int mMinY;
    private int mMode;
    private float mPhysicalCoeff;
    private final float mPpi;
    private long mStartTime;
    private int mStartX;
    private int mStartY;
    private float mVelocity;

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
        sViscousFluidNormalize = 1.0f;
        sViscousFluidNormalize = 1.0f / viscousFluid(1.0f);
    }

    public Scroller(Context context) {
        this(context, null);
    }

    public Scroller(Context context, Interpolator interpolator) {
        this(context, interpolator, context.getApplicationInfo().targetSdkVersion >= 11);
    }

    public Scroller(Context context, Interpolator interpolator, boolean z) {
        this.mFlingFriction = ViewConfiguration.getScrollFriction();
        this.mFinished = true;
        this.mInterpolator = interpolator;
        this.mPpi = context.getResources().getDisplayMetrics().density * 160.0f;
        this.mDeceleration = computeDeceleration(ViewConfiguration.getScrollFriction());
        this.mFlywheel = z;
        this.mPhysicalCoeff = computeDeceleration(0.84f);
    }

    public final void setFriction(float f) {
        this.mDeceleration = computeDeceleration(f);
        this.mFlingFriction = f;
    }

    private float computeDeceleration(float f) {
        return (386.0878f * this.mPpi) * f;
    }

    public final boolean isFinished() {
        return this.mFinished;
    }

    public final void forceFinished(boolean z) {
        this.mFinished = z;
    }

    public final int getDuration() {
        return this.mDuration;
    }

    public final int getCurrX() {
        return this.mCurrX;
    }

    public final int getCurrY() {
        return this.mCurrY;
    }

    public float getCurrVelocity() {
        return this.mMode == 1 ? this.mCurrVelocity : this.mVelocity - ((this.mDeceleration * ((float) timePassed())) / 2000.0f);
    }

    public final int getStartX() {
        return this.mStartX;
    }

    public final int getStartY() {
        return this.mStartY;
    }

    public final int getFinalX() {
        return this.mFinalX;
    }

    public final int getFinalY() {
        return this.mFinalY;
    }

    public boolean computeScrollOffset() {
        if (this.mFinished) {
            return false;
        }
        int currentAnimationTimeMillis = (int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
        if (currentAnimationTimeMillis < this.mDuration) {
            float f;
            switch (this.mMode) {
                case 0:
                    f = ((float) currentAnimationTimeMillis) * this.mDurationReciprocal;
                    if (this.mInterpolator == null) {
                        f = viscousFluid(f);
                    } else {
                        f = this.mInterpolator.getInterpolation(f);
                    }
                    this.mCurrX = this.mStartX + Math.round(this.mDeltaX * f);
                    this.mCurrY = Math.round(f * this.mDeltaY) + this.mStartY;
                    break;
                case 1:
                    float f2 = ((float) currentAnimationTimeMillis) / ((float) this.mDuration);
                    int i = (int) (100.0f * f2);
                    float f3 = 1.0f;
                    f = 0.0f;
                    if (i < 100) {
                        f3 = ((float) i) / 100.0f;
                        f = ((float) (i + 1)) / 100.0f;
                        float f4 = SPLINE_POSITION[i];
                        f = (SPLINE_POSITION[i + 1] - f4) / (f - f3);
                        f3 = ((f2 - f3) * f) + f4;
                    }
                    this.mCurrVelocity = ((f * ((float) this.mDistance)) / ((float) this.mDuration)) * 1000.0f;
                    this.mCurrX = this.mStartX + Math.round(((float) (this.mFinalX - this.mStartX)) * f3);
                    this.mCurrX = Math.min(this.mCurrX, this.mMaxX);
                    this.mCurrX = Math.max(this.mCurrX, this.mMinX);
                    this.mCurrY = this.mStartY + Math.round(f3 * ((float) (this.mFinalY - this.mStartY)));
                    this.mCurrY = Math.min(this.mCurrY, this.mMaxY);
                    this.mCurrY = Math.max(this.mCurrY, this.mMinY);
                    if (this.mCurrX == this.mFinalX && this.mCurrY == this.mFinalY) {
                        this.mFinished = true;
                        break;
                    }
            }
        }
        this.mCurrX = this.mFinalX;
        this.mCurrY = this.mFinalY;
        this.mFinished = true;
        return true;
    }

    public void startScroll(int i, int i2, int i3, int i4) {
        startScroll(i, i2, i3, i4, DEFAULT_DURATION);
    }

    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        this.mMode = 0;
        this.mFinished = false;
        this.mDuration = i5;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mStartX = i;
        this.mStartY = i2;
        this.mFinalX = i + i3;
        this.mFinalY = i2 + i4;
        this.mDeltaX = (float) i3;
        this.mDeltaY = (float) i4;
        this.mDurationReciprocal = 1.0f / ((float) this.mDuration);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        float currVelocity;
        float f;
        float f2 = 1.0f;
        if (this.mFlywheel && !this.mFinished) {
            currVelocity = getCurrVelocity();
            f = (float) (this.mFinalX - this.mStartX);
            float f3 = (float) (this.mFinalY - this.mStartY);
            float sqrt = FloatMath.sqrt((f * f) + (f3 * f3));
            f = (f / sqrt) * currVelocity;
            currVelocity *= f3 / sqrt;
            if (Math.signum((float) i3) == Math.signum(f) && Math.signum((float) i4) == Math.signum(currVelocity)) {
                i3 = (int) (f + ((float) i3));
                i4 = (int) (currVelocity + ((float) i4));
            }
        }
        this.mMode = 1;
        this.mFinished = false;
        f = FloatMath.sqrt((float) ((i3 * i3) + (i4 * i4)));
        this.mVelocity = f;
        this.mDuration = getSplineFlingDuration(f);
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mStartX = i;
        this.mStartY = i2;
        currVelocity = f == 0.0f ? 1.0f : ((float) i3) / f;
        if (f != 0.0f) {
            f2 = ((float) i4) / f;
        }
        double splineFlingDistance = getSplineFlingDistance(f);
        this.mDistance = (int) (((double) Math.signum(f)) * splineFlingDistance);
        this.mMinX = i5;
        this.mMaxX = i6;
        this.mMinY = i7;
        this.mMaxY = i8;
        this.mFinalX = ((int) Math.round(((double) currVelocity) * splineFlingDistance)) + i;
        this.mFinalX = Math.min(this.mFinalX, this.mMaxX);
        this.mFinalX = Math.max(this.mFinalX, this.mMinX);
        this.mFinalY = ((int) Math.round(((double) f2) * splineFlingDistance)) + i2;
        this.mFinalY = Math.min(this.mFinalY, this.mMaxY);
        this.mFinalY = Math.max(this.mFinalY, this.mMinY);
    }

    private double getSplineDeceleration(float f) {
        return Math.log((double) ((0.35f * Math.abs(f)) / (this.mFlingFriction * this.mPhysicalCoeff)));
    }

    private int getSplineFlingDuration(float f) {
        return (int) (Math.exp(getSplineDeceleration(f) / (((double) DECELERATION_RATE) - 1.0d)) * 1000.0d);
    }

    private double getSplineFlingDistance(float f) {
        return Math.exp(getSplineDeceleration(f) * (((double) DECELERATION_RATE) / (((double) DECELERATION_RATE) - 1.0d))) * ((double) (this.mFlingFriction * this.mPhysicalCoeff));
    }

    static float viscousFluid(float f) {
        float f2 = sViscousFluidScale * f;
        if (f2 < 1.0f) {
            f2 -= 1.0f - ((float) Math.exp((double) (-f2)));
        } else {
            f2 = ((1.0f - ((float) Math.exp((double) (1.0f - f2)))) * (1.0f - 0.36787945f)) + 0.36787945f;
        }
        return f2 * sViscousFluidNormalize;
    }

    public void abortAnimation() {
        this.mCurrX = this.mFinalX;
        this.mCurrY = this.mFinalY;
        this.mFinished = true;
    }

    public void extendDuration(int i) {
        this.mDuration = timePassed() + i;
        this.mDurationReciprocal = 1.0f / ((float) this.mDuration);
        this.mFinished = false;
    }

    public int timePassed() {
        return (int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
    }

    public void setFinalX(int i) {
        this.mFinalX = i;
        this.mDeltaX = (float) (this.mFinalX - this.mStartX);
        this.mFinished = false;
    }

    public void setFinalY(int i) {
        this.mFinalY = i;
        this.mDeltaY = (float) (this.mFinalY - this.mStartY);
        this.mFinished = false;
    }

    public boolean isScrollingInDirection(float f, float f2) {
        return !this.mFinished && Math.signum(f) == Math.signum((float) (this.mFinalX - this.mStartX)) && Math.signum(f2) == Math.signum((float) (this.mFinalY - this.mStartY));
    }
}
