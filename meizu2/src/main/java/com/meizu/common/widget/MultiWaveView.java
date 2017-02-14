package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.DecelerateInterpolator;
import com.amap.api.maps2d.model.GroundOverlayOptions;
import com.meizu.common.R;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class MultiWaveView extends View {
    private static final float ANGEL_BETWEEN_POINT = 0.3926991f;
    private static final int CIRCLE_ANIMATION_TIME = 1600;
    private static final boolean DEBUG = false;
    private static final int HIDE_ANIMATION_DELAY = 200;
    private static final int HIDE_ANIMATION_DURATION = 330;
    private static final int INVALID_POINTER_ID = -1;
    private static final int MAX_POINT_ALPHA = 255;
    private static final int MIN_POINT_ALPHA = 0;
    private static final int POINT_COUNT = 16;
    private static final int QUICK_CLICK_ANIMATION = 330;
    private static final int RETURN_TO_HOME_DELAY = 1200;
    private static final int RETURN_TO_HOME_DURATION = 330;
    private static final float SNAP_MARGIN_DEFAULT = 20.0f;
    private static final int STATE_FINISH = 5;
    private static final int STATE_FIRST_TOUCH = 2;
    private static final int STATE_IDLE = 0;
    private static final int STATE_SNAP = 4;
    private static final int STATE_START = 1;
    private static final int STATE_TRACKING = 3;
    private static final String TAG = "MultiWaveView";
    private static final float TAP_RADIUS_SCALE_ACCESSIBILITY_ENABLED = 1.3f;
    public static final int TARGET_ANSWER_ID = 0;
    public static final int TARGET_DECLINE_ID = 1;
    private static final TimeInterpolator circleAnimationInterpolator = new DecelerateInterpolator();
    private float MAX_ANGEL;
    private float MIN_ANGEL;
    private AnimatorSet animatorSet;
    private float density;
    private ArgbEvaluator evaluator;
    private float hightForHandler;
    private int mActivePointerId;
    private int mActiveTarget;
    private boolean mAnimatingTargets;
    private int mBallAlpha;
    private Paint mBallCirclePaint;
    private int mBigCircleAlpha;
    private Paint mBigCirclePaint;
    private ObjectAnimator mCircleAnimation;
    private int mColorGreen;
    private int mColorOrange;
    private int mCurAlpha;
    private float mCurAngel;
    private float mCurCircleRadius;
    private float mCurPointRadius;
    private Paint mDefaultBackgroundPaint;
    private int mDefaultBgColor;
    private ArrayList<String> mDirectionDescriptions;
    private int mDirectionDescriptionsResourceId;
    private boolean mDisableQuickClick;
    private boolean mDragging;
    private boolean mDrawHandleCircle;
    private boolean mDrawPointCircle;
    private AnimatorListener mFinishListener;
    private int mGrabbedState;
    private int mGravity;
    private float mHandleAngleStep;
    private AnimationBundle mHandleAnimations;
    private ObjectAnimator mHandleCircleAnimation;
    private int mHandleCircleColor;
    private Paint mHandleCirclePaint;
    private ValueHolder mHandleCircleValueHolder;
    private TargetDrawable mHandleDrawable;
    private boolean mHoldCircleAnimation;
    private int mHorizontalInset;
    private int mIconbgColor;
    private boolean mInitialLayout;
    private boolean mIsBluetoothAns;
    private float mMaxPointCircleRadius;
    private float mMaxPointRadius;
    private int mMaxTargetHeight;
    private int mMaxTargetWidth;
    private float mMinPointCircleRadius;
    private float mMinPointRadius;
    private float mMotionDownX;
    private float mMotionDownY;
    private AnimationBundle mMoveAnimations;
    private MyPoint mMovePoint;
    private MyAnimatorUpdateListener mMoveUpdateListener;
    private int mNewTargetResources;
    private OnTriggerListener mOnTriggerListener;
    private float mOuterRadius;
    private float mOuterRadiusHeight;
    private TargetDrawable mOuterRing;
    private int mPaintColor;
    private Paint mPointCirclePaint;
    private float mPointCircleX;
    private float mPointCircleY;
    private int mPointColor;
    private boolean mQuickClick;
    private AnimatorListener mResetListener;
    private AnimatorListener mResetListenerWithPing;
    private float mShakeAngel;
    private float mStrokeWidth;
    private float mTapRadius;
    private float mTargetBeginScaleDistance;
    private ArrayList<String> mTargetDescriptions;
    private int mTargetDescriptionsResourceId;
    private ArrayList<TargetDrawable> mTargetDrawables;
    private float mTargetMaxRadius;
    private float mTargetMinRadius;
    private float mTargetRadiusFromCenter;
    private int mTargetResourceId;
    private float mTargetScaleStep;
    private int mTouchSlop;
    private float mTranslateCircleX;
    private AnimatorUpdateListener mUpdateListener;
    private ValueHolder mValueHolder;
    private int mVerticalInset;
    private int mVibrationDuration;
    private Vibrator mVibrator;
    private float mWaveCenterX;
    private float mWaveCenterY;
    private float widthForHandler;

    class AnimationBundle extends ArrayList<Tweener> {
        private static final long serialVersionUID = -6319262269245852568L;
        private boolean mSuspended;

        private AnimationBundle() {
        }

        public void start() {
            if (!this.mSuspended) {
                int size = size();
                for (int i = 0; i < size; i++) {
                    ((Tweener) get(i)).animator.start();
                }
            }
        }

        public void cancel() {
            int size = size();
            for (int i = 0; i < size; i++) {
                ((Tweener) get(i)).animator.cancel();
            }
            clear();
        }

        public void stop() {
            int size = size();
            for (int i = 0; i < size; i++) {
                ((Tweener) get(i)).animator.end();
            }
            clear();
        }

        public void setSuspended(boolean z) {
            this.mSuspended = z;
        }
    }

    static class Ease {
        private static final float DOMAIN = 1.0f;
        private static final float DURATION = 1.0f;
        private static final float START = 0.0f;

        static class Cubic {
            public static final TimeInterpolator easeIn = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    float f2 = f / 1.0f;
                    return (f2 * ((1.0f * f2) * f2)) + 0.0f;
                }
            };
            public static final TimeInterpolator easeInOut = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    float f2 = f / FastBlurParameters.DEFAULT_LEVEL;
                    if (f2 < 1.0f) {
                        return (f2 * ((FastBlurParameters.DEFAULT_LEVEL * f2) * f2)) + 0.0f;
                    }
                    f2 -= CircleProgressBar.BAR_WIDTH_DEF_DIP;
                    return (((f2 * (f2 * f2)) + CircleProgressBar.BAR_WIDTH_DEF_DIP) * FastBlurParameters.DEFAULT_LEVEL) + 0.0f;
                }
            };
            public static final TimeInterpolator easeOut = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    float f2 = (f / 1.0f) - 1.0f;
                    return (((f2 * (f2 * f2)) + 1.0f) * 1.0f) + 0.0f;
                }
            };

            Cubic() {
            }
        }

        static class Linear {
            public static final TimeInterpolator easeNone = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    return f;
                }
            };

            Linear() {
            }
        }

        static class Quad {
            public static final TimeInterpolator easeIn = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    float f2 = f / 1.0f;
                    return (f2 * (1.0f * f2)) + 0.0f;
                }
            };
            public static final TimeInterpolator easeInOut = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    float f2 = f / FastBlurParameters.DEFAULT_LEVEL;
                    if (f2 < 1.0f) {
                        return (f2 * (FastBlurParameters.DEFAULT_LEVEL * f2)) + 0.0f;
                    }
                    f2 -= 1.0f;
                    return (((f2 * (f2 - CircleProgressBar.BAR_WIDTH_DEF_DIP)) - 1.0f) * -0.5f) + 0.0f;
                }
            };
            public static final TimeInterpolator easeOut = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    float f2 = f / 1.0f;
                    return ((GroundOverlayOptions.NO_DIMENSION * f2) * (f2 - CircleProgressBar.BAR_WIDTH_DEF_DIP)) + 0.0f;
                }
            };

            Quad() {
            }
        }

        static class Quart {
            public static final TimeInterpolator easeIn = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    float f2 = f / 1.0f;
                    return (f2 * (((1.0f * f2) * f2) * f2)) + 0.0f;
                }
            };
            public static final TimeInterpolator easeInOut = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    float f2 = f / FastBlurParameters.DEFAULT_LEVEL;
                    if (f2 < 1.0f) {
                        return (f2 * (((FastBlurParameters.DEFAULT_LEVEL * f2) * f2) * f2)) + 0.0f;
                    }
                    f2 -= CircleProgressBar.BAR_WIDTH_DEF_DIP;
                    return (((f2 * ((f2 * f2) * f2)) - CircleProgressBar.BAR_WIDTH_DEF_DIP) * -0.5f) + 0.0f;
                }
            };
            public static final TimeInterpolator easeOut = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    float f2 = (f / 1.0f) - 1.0f;
                    return (GroundOverlayOptions.NO_DIMENSION * ((f2 * ((f2 * f2) * f2)) - 1.0f)) + 0.0f;
                }
            };

            Quart() {
            }
        }

        static class Quint {
            public static final TimeInterpolator easeIn = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    float f2 = f / 1.0f;
                    return (f2 * ((((1.0f * f2) * f2) * f2) * f2)) + 0.0f;
                }
            };
            public static final TimeInterpolator easeInOut = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    float f2 = f / FastBlurParameters.DEFAULT_LEVEL;
                    if (f2 < 1.0f) {
                        return (f2 * ((((FastBlurParameters.DEFAULT_LEVEL * f2) * f2) * f2) * f2)) + 0.0f;
                    }
                    f2 -= CircleProgressBar.BAR_WIDTH_DEF_DIP;
                    return (((f2 * (((f2 * f2) * f2) * f2)) + CircleProgressBar.BAR_WIDTH_DEF_DIP) * FastBlurParameters.DEFAULT_LEVEL) + 0.0f;
                }
            };
            public static final TimeInterpolator easeOut = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    float f2 = (f / 1.0f) - 1.0f;
                    return (((f2 * (((f2 * f2) * f2) * f2)) + 1.0f) * 1.0f) + 0.0f;
                }
            };

            Quint() {
            }
        }

        static class Sine {
            public static final TimeInterpolator easeIn = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    return ((GroundOverlayOptions.NO_DIMENSION * ((float) Math.cos(((double) (f / 1.0f)) * 1.5707963267948966d))) + 1.0f) + 0.0f;
                }
            };
            public static final TimeInterpolator easeInOut = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    return (-0.5f * (((float) Math.cos((3.141592653589793d * ((double) f)) / 1.0d)) - 1.0f)) + 0.0f;
                }
            };
            public static final TimeInterpolator easeOut = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    return (((float) Math.sin(((double) (f / 1.0f)) * 1.5707963267948966d)) * 1.0f) + 0.0f;
                }
            };

            Sine() {
            }
        }

        static class customTrack {
            public static final TimeInterpolator easeLadderShape = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    if (((double) f) <= 0.42d) {
                        customTrack.mOutput = 2.3809524f * f;
                    } else if (((double) f) > 0.65d) {
                        customTrack.mOutput = (float) ((-2.857142857142857d * ((double) f)) + 2.857142857142857d);
                    }
                    return customTrack.mOutput;
                }
            };
            public static final TimeInterpolator easeSinShape = new TimeInterpolator() {
                public float getInterpolation(float f) {
                    if (((double) f) <= 0.34d) {
                        return (float) Math.sin((19.634954084936208d * ((double) f)) - 0.39269908169872414d);
                    }
                    if (((double) f) <= 0.62d) {
                        return (float) (0.7d * Math.sin((26.179938779914945d * ((double) f)) - 9.948376736367678d));
                    }
                    if (((double) f) <= 0.87d) {
                        return (float) (0.3d * Math.sin((22.43994752564138d * ((double) f)) - 13.239569040128414d));
                    }
                    if (((double) f) <= 0.9d) {
                        return (float) ((3.3333333333333335d * ((double) f)) - 2.9d);
                    }
                    return (float) (((double) (GroundOverlayOptions.NO_DIMENSION * f)) + 1.0d);
                }
            };
            static float mOutput = 1.0f;

            customTrack() {
            }
        }

        Ease() {
        }
    }

    class MyAnimatorUpdateListener implements AnimatorUpdateListener {
        public boolean ignorSnap;

        private MyAnimatorUpdateListener() {
            this.ignorSnap = true;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            MultiWaveView.this.handleMove(null, this.ignorSnap);
            MultiWaveView.this.invalidate();
        }
    }

    static class MyPoint {
        public float x;
        public float y;

        public MyPoint(float f, float f2) {
            this.x = f;
            this.y = f2;
        }

        public void setX(float f) {
            this.x = f;
        }

        public void setY(float f) {
            this.y = f;
        }

        public float getX() {
            return this.x;
        }

        public float getY() {
            return this.y;
        }
    }

    public interface OnTriggerListener {
        public static final int CENTER_HANDLE = 1;
        public static final int NO_HANDLE = 0;

        void onFinishFinalAnimation();

        void onGrabbed(View view, int i);

        void onGrabbedStateChange(View view, int i);

        void onReleased(View view, int i);

        void onTrigger(View view, int i, boolean z);
    }

    static class TargetDrawable {
        private static final boolean DEBUG = false;
        public static final int[] STATE_ACTIVE = new int[]{16842910, 16842914};
        public static final int[] STATE_FOCUSED = new int[]{16842910, -16842914, 16842908};
        public static final int[] STATE_INACTIVE = new int[]{16842910, -16842914};
        private static final String TAG = "TargetDrawable";
        private static final Xfermode sMode = new PorterDuffXfermode(Mode.XOR);
        private float mAlpha;
        private Bitmap mBitmap;
        private int mCircleColor;
        private float mCircleRadius;
        private Drawable mDrawable;
        private boolean mEnabled;
        private boolean mIsCircle;
        private Paint mPaint;
        private float mPositionX;
        private float mPositionY;
        private final int mResourceId;
        private float mRotate;
        private float mScaleX;
        private float mScaleY;
        private float mTranslationX;
        private float mTranslationY;

        public TargetDrawable(Resources resources, int i) {
            this.mTranslationX = 0.0f;
            this.mTranslationY = 0.0f;
            this.mPositionX = 0.0f;
            this.mPositionY = 0.0f;
            this.mScaleX = 1.0f;
            this.mScaleY = 1.0f;
            this.mAlpha = 1.0f;
            this.mRotate = 0.0f;
            this.mEnabled = true;
            this.mIsCircle = false;
            this.mResourceId = i;
            setDrawable(resources, i);
            this.mPaint = new Paint();
            this.mPaint.setFilterBitmap(true);
            this.mPaint.setAntiAlias(true);
            this.mIsCircle = false;
        }

        public void setDrawable(Resources resources, int i) {
            Drawable drawable = null;
            Drawable drawable2 = i == 0 ? null : resources.getDrawable(i);
            if (drawable2 != null) {
                drawable = drawable2.mutate();
            }
            this.mDrawable = drawable;
            resizeDrawables();
            setState(STATE_INACTIVE);
        }

        public TargetDrawable(TargetDrawable targetDrawable) {
            this.mTranslationX = 0.0f;
            this.mTranslationY = 0.0f;
            this.mPositionX = 0.0f;
            this.mPositionY = 0.0f;
            this.mScaleX = 1.0f;
            this.mScaleY = 1.0f;
            this.mAlpha = 1.0f;
            this.mRotate = 0.0f;
            this.mEnabled = true;
            this.mIsCircle = false;
            this.mResourceId = targetDrawable.mResourceId;
            this.mDrawable = targetDrawable.mDrawable != null ? targetDrawable.mDrawable.mutate() : null;
            resizeDrawables();
            setState(STATE_INACTIVE);
            this.mPaint = new Paint();
            this.mPaint.setFilterBitmap(true);
            this.mPaint.setAntiAlias(true);
            this.mIsCircle = false;
        }

        public TargetDrawable(int i, float f) {
            this.mTranslationX = 0.0f;
            this.mTranslationY = 0.0f;
            this.mPositionX = 0.0f;
            this.mPositionY = 0.0f;
            this.mScaleX = 1.0f;
            this.mScaleY = 1.0f;
            this.mAlpha = 1.0f;
            this.mRotate = 0.0f;
            this.mEnabled = true;
            this.mIsCircle = false;
            this.mResourceId = -1;
            this.mDrawable = null;
            this.mBitmap = null;
            this.mIsCircle = true;
            this.mCircleColor = i;
            this.mCircleRadius = f;
            this.mPaint = new Paint();
            this.mPaint.setFilterBitmap(true);
            this.mPaint.setAntiAlias(true);
        }

        public void setState(int[] iArr) {
            if (this.mDrawable instanceof StateListDrawable) {
                ((StateListDrawable) this.mDrawable).setState(iArr);
            }
        }

        public boolean hasState(int[] iArr) {
            if (!(this.mDrawable instanceof StateListDrawable)) {
                return false;
            }
            if (getStateDrawableIndex((StateListDrawable) this.mDrawable, iArr) != -1) {
                return true;
            }
            return false;
        }

        public boolean isActive() {
            if (!(this.mDrawable instanceof StateListDrawable)) {
                return false;
            }
            int[] state = ((StateListDrawable) this.mDrawable).getState();
            for (int i : state) {
                if (i == 16842908) {
                    return true;
                }
            }
            return false;
        }

        public boolean isEnabled() {
            return this.mEnabled && !(!this.mIsCircle && this.mBitmap == null && this.mDrawable == null);
        }

        private void resizeDrawables() {
            if (this.mDrawable instanceof StateListDrawable) {
                int i;
                StateListDrawable stateListDrawable = (StateListDrawable) this.mDrawable;
                int i2 = 0;
                int i3 = 0;
                for (i = 0; i < getStateCount(stateListDrawable); i++) {
                    Drawable stateDrawable = getStateDrawable(stateListDrawable, i);
                    i3 = Math.max(i3, stateDrawable.getIntrinsicWidth());
                    i2 = Math.max(i2, stateDrawable.getIntrinsicHeight());
                }
                stateListDrawable.setBounds(0, 0, i3, i2);
                for (i = 0; i < getStateCount(stateListDrawable); i++) {
                    getStateDrawable(stateListDrawable, i).setBounds(0, 0, i3, i2);
                }
            } else if (this.mDrawable != null) {
                this.mDrawable.setBounds(0, 0, this.mDrawable.getIntrinsicWidth(), this.mDrawable.getIntrinsicHeight());
            }
            if (this.mDrawable != null) {
                this.mBitmap = drawableToBitmap(this.mDrawable);
            }
        }

        public void setX(float f) {
            this.mTranslationX = f;
        }

        public void setY(float f) {
            this.mTranslationY = f;
        }

        public void setScaleX(float f) {
            this.mScaleX = f;
        }

        public void setScaleY(float f) {
            this.mScaleY = f;
        }

        public void setAlpha(float f) {
            this.mAlpha = f;
        }

        public void setRotation(float f) {
            this.mRotate = f;
        }

        public float getX() {
            return this.mTranslationX;
        }

        public float getY() {
            return this.mTranslationY;
        }

        public float getScaleX() {
            return this.mScaleX;
        }

        public float getScaleY() {
            return this.mScaleY;
        }

        public float getAlpha() {
            return this.mAlpha;
        }

        public void setPositionX(float f) {
            this.mPositionX = f;
        }

        public void setPositionY(float f) {
            this.mPositionY = f;
        }

        public float getPositionX() {
            return this.mPositionX;
        }

        public float getPositionY() {
            return this.mPositionY;
        }

        public float getRotation() {
            return this.mRotate;
        }

        public int getWidth() {
            if (this.mDrawable != null) {
                return this.mDrawable.getIntrinsicWidth();
            }
            if (this.mBitmap != null) {
                return this.mBitmap.getWidth();
            }
            if (this.mIsCircle) {
                return (int) (this.mCircleRadius * CircleProgressBar.BAR_WIDTH_DEF_DIP);
            }
            return 0;
        }

        public int getHeight() {
            if (this.mDrawable != null) {
                return this.mDrawable.getIntrinsicHeight();
            }
            if (this.mBitmap != null) {
                return this.mBitmap.getHeight();
            }
            if (this.mIsCircle) {
                return (int) (this.mCircleRadius * CircleProgressBar.BAR_WIDTH_DEF_DIP);
            }
            return 0;
        }

        public void draw(Canvas canvas) {
            draw(canvas, false);
        }

        public void draw(Canvas canvas, boolean z) {
            if (this.mEnabled) {
                int width = getWidth() / 2;
                int height = getHeight() / 2;
                canvas.save();
                canvas.translate(this.mTranslationX + this.mPositionX, this.mTranslationY + this.mPositionY);
                canvas.translate((float) (-width), (float) (-height));
                Matrix matrix = new Matrix();
                matrix.preScale(this.mScaleX, this.mScaleY, (float) width, (float) height);
                matrix.postRotate(this.mRotate, (float) width, (float) height);
                if (this.mBitmap != null) {
                    canvas.drawBitmap(this.mBitmap, matrix, this.mPaint);
                } else if (this.mIsCircle) {
                    this.mPaint.setColor(this.mCircleColor);
                    canvas.drawCircle((float) width, (float) height, this.mCircleRadius * this.mScaleX, this.mPaint);
                }
                canvas.restore();
            }
        }

        public void setEnabled(boolean z) {
            this.mEnabled = z;
        }

        public int getResourceId() {
            return this.mResourceId;
        }

        private int getStateDrawableIndex(StateListDrawable stateListDrawable, int[] iArr) {
            try {
                return ((Integer) stateListDrawable.getClass().getMethod("getStateDrawableIndex", new Class[]{int[].class}).invoke(stateListDrawable, new Object[]{iArr})).intValue();
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }

        private int getStateCount(StateListDrawable stateListDrawable) {
            try {
                return ((Integer) stateListDrawable.getClass().getMethod("getStateCount", new Class[0]).invoke(stateListDrawable, new Object[0])).intValue();
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        private Drawable getStateDrawable(StateListDrawable stateListDrawable, int i) {
            try {
                return (Drawable) stateListDrawable.getClass().getMethod("getStateDrawable", new Class[]{Integer.TYPE}).invoke(stateListDrawable, new Object[]{Integer.valueOf(i)});
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private Bitmap drawableToBitmap(Drawable drawable) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            try {
                Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
                Canvas canvas = new Canvas(createBitmap);
                drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
                drawable.draw(canvas);
                return createBitmap;
            } catch (OutOfMemoryError e) {
                Log.w(TAG, e.toString() + "");
                return null;
            } catch (IllegalArgumentException e2) {
                Log.w(TAG, e2.toString() + "");
                return null;
            }
        }
    }

    static class Tweener {
        private static final boolean DEBUG = false;
        private static final String TAG = "Tweener";
        private static AnimatorListener mCleanupListener = new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                Tweener.remove(animator);
            }

            public void onAnimationCancel(Animator animator) {
                Tweener.remove(animator);
            }
        };
        private static HashMap<Object, Tweener> sTweens = new HashMap();
        ObjectAnimator animator;

        public Tweener(ObjectAnimator objectAnimator) {
            this.animator = objectAnimator;
        }

        private static void remove(Animator animator) {
            Iterator it = sTweens.entrySet().iterator();
            while (it.hasNext()) {
                if (((Tweener) ((Entry) it.next()).getValue()).animator == animator) {
                    it.remove();
                    return;
                }
            }
        }

        public static Tweener to(Object obj, long j, Object... objArr) {
            ObjectAnimator ofPropertyValuesHolder;
            Tweener tweener;
            long j2 = 0;
            AnimatorUpdateListener animatorUpdateListener = null;
            AnimatorListener animatorListener = null;
            TimeInterpolator timeInterpolator = null;
            ArrayList arrayList = new ArrayList(objArr.length / 2);
            int i = 0;
            while (i < objArr.length) {
                if (objArr[i] instanceof String) {
                    TimeInterpolator timeInterpolator2;
                    AnimatorListener animatorListener2;
                    AnimatorUpdateListener animatorUpdateListener2;
                    long j3;
                    String str = (String) objArr[i];
                    Object obj2 = objArr[i + 1];
                    if ("simultaneousTween".equals(str)) {
                        timeInterpolator2 = timeInterpolator;
                        animatorListener2 = animatorListener;
                        animatorUpdateListener2 = animatorUpdateListener;
                        j3 = j2;
                    } else if ("ease".equals(str)) {
                        timeInterpolator2 = (TimeInterpolator) obj2;
                        animatorListener2 = animatorListener;
                        animatorUpdateListener2 = animatorUpdateListener;
                        j3 = j2;
                    } else if ("onUpdate".equals(str) || "onUpdateListener".equals(str)) {
                        timeInterpolator2 = timeInterpolator;
                        animatorUpdateListener2 = (AnimatorUpdateListener) obj2;
                        animatorListener2 = animatorListener;
                        j3 = j2;
                    } else if ("onComplete".equals(str) || "onCompleteListener".equals(str)) {
                        animatorListener2 = (AnimatorListener) obj2;
                        timeInterpolator2 = timeInterpolator;
                        animatorUpdateListener2 = animatorUpdateListener;
                        j3 = j2;
                    } else if ("delay".equals(str)) {
                        animatorUpdateListener2 = animatorUpdateListener;
                        AnimatorListener animatorListener3 = animatorListener;
                        j3 = ((Number) obj2).longValue();
                        timeInterpolator2 = timeInterpolator;
                        animatorListener2 = animatorListener3;
                    } else if ("syncWith".equals(str)) {
                        timeInterpolator2 = timeInterpolator;
                        animatorListener2 = animatorListener;
                        animatorUpdateListener2 = animatorUpdateListener;
                        j3 = j2;
                    } else if (obj2 instanceof float[]) {
                        arrayList.add(PropertyValuesHolder.ofFloat(str, new float[]{((float[]) obj2)[0], ((float[]) obj2)[1]}));
                        timeInterpolator2 = timeInterpolator;
                        animatorListener2 = animatorListener;
                        animatorUpdateListener2 = animatorUpdateListener;
                        j3 = j2;
                    } else if (obj2 instanceof int[]) {
                        arrayList.add(PropertyValuesHolder.ofInt(str, new int[]{((int[]) obj2)[0], ((int[]) obj2)[1]}));
                        timeInterpolator2 = timeInterpolator;
                        animatorListener2 = animatorListener;
                        animatorUpdateListener2 = animatorUpdateListener;
                        j3 = j2;
                    } else if (obj2 instanceof Number) {
                        arrayList.add(PropertyValuesHolder.ofFloat(str, new float[]{((Number) obj2).floatValue()}));
                        timeInterpolator2 = timeInterpolator;
                        animatorListener2 = animatorListener;
                        animatorUpdateListener2 = animatorUpdateListener;
                        j3 = j2;
                    } else {
                        throw new IllegalArgumentException("Bad argument for key \"" + str + "\" with value " + obj2.getClass());
                    }
                    i += 2;
                    j2 = j3;
                    animatorListener = animatorListener2;
                    animatorUpdateListener = animatorUpdateListener2;
                    timeInterpolator = timeInterpolator2;
                } else {
                    throw new IllegalArgumentException("Key must be a string: " + objArr[i]);
                }
            }
            Tweener tweener2 = (Tweener) sTweens.get(obj);
            if (tweener2 == null) {
                ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(obj, (PropertyValuesHolder[]) arrayList.toArray(new PropertyValuesHolder[arrayList.size()]));
                tweener = new Tweener(ofPropertyValuesHolder);
                sTweens.put(obj, tweener);
            } else {
                ObjectAnimator objectAnimator = ((Tweener) sTweens.get(obj)).animator;
                replace(arrayList, obj);
                ObjectAnimator objectAnimator2 = objectAnimator;
                tweener = tweener2;
                ofPropertyValuesHolder = objectAnimator2;
            }
            if (timeInterpolator != null) {
                ofPropertyValuesHolder.setInterpolator(timeInterpolator);
            }
            ofPropertyValuesHolder.setStartDelay(j2);
            ofPropertyValuesHolder.setDuration(j);
            if (animatorUpdateListener != null) {
                ofPropertyValuesHolder.removeAllUpdateListeners();
                ofPropertyValuesHolder.addUpdateListener(animatorUpdateListener);
            }
            if (animatorListener != null) {
                ofPropertyValuesHolder.removeAllListeners();
                ofPropertyValuesHolder.addListener(animatorListener);
            }
            ofPropertyValuesHolder.addListener(mCleanupListener);
            return tweener;
        }

        Tweener from(Object obj, long j, Object... objArr) {
            return to(obj, j, objArr);
        }

        public static void reset() {
            sTweens.clear();
        }

        private static void replace(ArrayList<PropertyValuesHolder> arrayList, Object... objArr) {
            for (Object obj : objArr) {
                Tweener tweener = (Tweener) sTweens.get(obj);
                if (tweener != null) {
                    tweener.animator.cancel();
                    if (arrayList != null) {
                        tweener.animator.setValues((PropertyValuesHolder[]) arrayList.toArray(new PropertyValuesHolder[arrayList.size()]));
                    } else {
                        sTweens.remove(tweener);
                    }
                }
            }
        }
    }

    class ValueHolder {
        private int mAlpha;
        private float mAngel;
        private float mCircleRadius;
        private float mPointRadius;
        private float mRotion;

        private ValueHolder() {
        }

        public void setRadius(float f) {
            this.mCircleRadius = f;
        }

        public float getRadius() {
            return this.mCircleRadius;
        }

        public void setAlpha(int i) {
            this.mAlpha = i;
        }

        public int getAlpha() {
            return this.mAlpha;
        }

        public void setPointRadius(float f) {
            this.mPointRadius = f;
        }

        public float getPointRadius() {
            return this.mPointRadius;
        }

        public void setAngel(float f) {
            this.mAngel = f;
        }

        public float getAngel() {
            return this.mAngel;
        }

        public float getRotation() {
            return this.mRotion;
        }

        public void setRotation(float f) {
            this.mRotion = f;
        }
    }

    public MultiWaveView(Context context) {
        this(context, null);
    }

    public MultiWaveView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_MultiWaveView);
    }

    public MultiWaveView(Context context, AttributeSet attributeSet, int i) {
        boolean z = true;
        super(context, attributeSet, i);
        this.mTargetDrawables = new ArrayList();
        this.mHandleAnimations = new AnimationBundle();
        this.mMoveAnimations = new AnimationBundle();
        this.mIsBluetoothAns = false;
        this.mMoveUpdateListener = new MyAnimatorUpdateListener();
        this.mVibrationDuration = 0;
        this.mActiveTarget = -1;
        this.mOuterRadius = 0.0f;
        this.mResetListener = new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                MultiWaveView.this.switchToState(0, MultiWaveView.this.mWaveCenterX, MultiWaveView.this.mWaveCenterY);
                MultiWaveView.this.dispatchOnFinishFinalAnimation();
                MultiWaveView.this.mDrawHandleCircle = false;
            }
        };
        this.mResetListenerWithPing = new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                MultiWaveView.this.switchToState(0, MultiWaveView.this.mWaveCenterX, MultiWaveView.this.mWaveCenterY);
                MultiWaveView.this.dispatchOnFinishFinalAnimation();
                MultiWaveView.this.mDrawHandleCircle = false;
            }
        };
        this.mUpdateListener = new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                MultiWaveView.this.mActiveTarget = MultiWaveView.this.findActiveTarget(Math.atan2((double) (-MultiWaveView.this.mHandleDrawable.getY()), (double) MultiWaveView.this.mHandleDrawable.getX()));
                float x = MultiWaveView.this.mHandleDrawable.getX();
                if (x < 0.0f && x > -117.0f * MultiWaveView.this.density) {
                    MultiWaveView.this.mPaintColor = ((Integer) MultiWaveView.this.evaluator.evaluate((-x) / (MultiWaveView.this.density * 117.0f), Integer.valueOf(MultiWaveView.this.mDefaultBgColor), Integer.valueOf(MultiWaveView.this.mColorOrange))).intValue();
                } else if (x > 0.0f && x < MultiWaveView.this.density * 117.0f) {
                    MultiWaveView.this.mPaintColor = ((Integer) MultiWaveView.this.evaluator.evaluate(x / (MultiWaveView.this.density * 117.0f), Integer.valueOf(MultiWaveView.this.mDefaultBgColor), Integer.valueOf(MultiWaveView.this.mColorGreen))).intValue();
                }
                MultiWaveView.this.invalidate();
            }
        };
        this.mGravity = 1;
        this.mInitialLayout = true;
        this.mCurAlpha = 0;
        this.MAX_ANGEL = 1.4765486f;
        this.MIN_ANGEL = 0.0f;
        this.mCurAngel = 0.0f;
        this.mOuterRadiusHeight = 0.0f;
        this.mTargetRadiusFromCenter = 0.0f;
        this.mMotionDownX = 0.0f;
        this.mMotionDownY = 0.0f;
        this.mHoldCircleAnimation = false;
        this.mDrawPointCircle = true;
        this.mQuickClick = false;
        this.mDisableQuickClick = true;
        this.mDrawHandleCircle = false;
        this.mDefaultBgColor = 234881023;
        this.mPaintColor = this.mHandleCircleColor;
        this.mColorGreen = -12339861;
        this.mColorOrange = -626650;
        this.mIconbgColor = this.mDefaultBgColor;
        this.evaluator = new ArgbEvaluator();
        this.mShakeAngel = 0.0f;
        this.mBallAlpha = 0;
        this.mBigCircleAlpha = 0;
        this.density = getResources().getDisplayMetrics().density;
        this.animatorSet = null;
        this.mActivePointerId = -1;
        this.mFinishListener = new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                MultiWaveView.this.mQuickClick = false;
                MultiWaveView.this.switchToState(5, MultiWaveView.this.mWaveCenterX, MultiWaveView.this.mWaveCenterY);
            }
        };
        Resources resources = context.getResources();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MultiWaveView, i, 0);
        this.mTargetMinRadius = obtainStyledAttributes.getDimension(R.styleable.MultiWaveView_mcTargetMinRadius, this.mTargetMinRadius);
        this.mTargetMaxRadius = obtainStyledAttributes.getDimension(R.styleable.MultiWaveView_mcTargetMaxRadius, this.mTargetMaxRadius);
        this.mOuterRadius = obtainStyledAttributes.getDimension(R.styleable.MultiWaveView_mcOuterRadius, this.mOuterRadius);
        this.mVibrationDuration = obtainStyledAttributes.getInt(R.styleable.MultiWaveView_mcVibrationDuration, this.mVibrationDuration);
        this.mHandleDrawable = new TargetDrawable(resources, obtainStyledAttributes.peekValue(R.styleable.MultiWaveView_mcHandleDrawable).resourceId);
        this.mTapRadius = (float) (this.mHandleDrawable.getWidth() / 2);
        this.mOuterRing = new TargetDrawable(resources, obtainStyledAttributes.peekValue(R.styleable.MultiWaveView_mcWaveDrawable).resourceId);
        this.widthForHandler = this.mHandleDrawable.getX();
        this.hightForHandler = this.mHandleDrawable.getY();
        TypedValue typedValue = new TypedValue();
        if (obtainStyledAttributes.getValue(R.styleable.MultiWaveView_mcTargetDrawables, typedValue)) {
            internalSetTargetResources(typedValue.resourceId);
        }
        if (this.mTargetDrawables == null || this.mTargetDrawables.size() == 0) {
            throw new IllegalStateException("Must specify at least one target drawable");
        }
        if (obtainStyledAttributes.getValue(R.styleable.MultiWaveView_mcTargetDescriptions, typedValue)) {
            int i2 = typedValue.resourceId;
            if (i2 == 0) {
                throw new IllegalStateException("Must specify target descriptions");
            }
            setTargetDescriptionsResourceId(i2);
        }
        if (obtainStyledAttributes.getValue(R.styleable.MultiWaveView_mcDirectionDescriptions, typedValue)) {
            int i3 = typedValue.resourceId;
            if (i3 == 0) {
                throw new IllegalStateException("Must specify direction descriptions");
            }
            setDirectionDescriptionsResourceId(i3);
        }
        this.mMaxPointCircleRadius = obtainStyledAttributes.getDimension(R.styleable.MultiWaveView_mcMaxPointCircleRadius, this.mMaxPointCircleRadius);
        this.mMinPointCircleRadius = this.mMaxPointCircleRadius * 0.12f;
        this.mMaxPointRadius = obtainStyledAttributes.getDimension(R.styleable.MultiWaveView_mcMaxPointRadius, this.mMaxPointRadius);
        this.mMinPointRadius = 0.0f;
        this.mOuterRadiusHeight = obtainStyledAttributes.getDimension(R.styleable.MultiWaveView_mcOuterRadiusHeight, this.mOuterRadiusHeight);
        this.mTargetRadiusFromCenter = (obtainStyledAttributes.getDimension(R.styleable.MultiWaveView_mcTargetToHandle, this.mTargetRadiusFromCenter) + this.mTapRadius) + this.mTargetMinRadius;
        this.mTargetBeginScaleDistance = obtainStyledAttributes.getDimension(R.styleable.MultiWaveView_mcTargetSnapRadius, this.mTargetBeginScaleDistance);
        this.mTargetScaleStep = this.mTargetMaxRadius / this.mTargetMinRadius;
        this.mHandleAngleStep = 135.0f / this.mTargetRadiusFromCenter;
        this.mPointColor = obtainStyledAttributes.getColor(R.styleable.MultiWaveView_mcPointColor, ViewCompat.MEASURED_STATE_MASK);
        this.mHandleCircleColor = obtainStyledAttributes.getColor(R.styleable.MultiWaveView_mcHandleCircleColor, 687865855);
        obtainStyledAttributes.recycle();
        this.mPointCirclePaint = new Paint();
        this.mPointCirclePaint.setAntiAlias(true);
        this.mPointCirclePaint.setColor(this.mPointColor);
        this.mDefaultBackgroundPaint = new Paint();
        this.mDefaultBackgroundPaint.setColor(this.mIconbgColor);
        this.mDefaultBackgroundPaint.setStyle(Style.FILL_AND_STROKE);
        this.mDefaultBackgroundPaint.setStrokeWidth(getResources().getDimension(R.dimen.mc_multiwaveview_handle_circle_width));
        this.mBigCirclePaint = new Paint();
        this.mBigCirclePaint.setAntiAlias(true);
        this.mBigCirclePaint.setColor(this.mDefaultBgColor);
        this.mBigCirclePaint.setStyle(Style.STROKE);
        this.mBigCirclePaint.setStrokeWidth(5.0f);
        this.mHandleCirclePaint = new Paint();
        this.mHandleCirclePaint.setAntiAlias(true);
        this.mHandleCirclePaint.setColor(this.mHandleCircleColor);
        this.mHandleCirclePaint.setStyle(Style.FILL_AND_STROKE);
        this.mHandleCirclePaint.setStrokeWidth(getResources().getDimension(R.dimen.mc_multiwaveview_handle_circle_width));
        this.mBallCirclePaint = new Paint();
        this.mBallCirclePaint.setAntiAlias(true);
        this.mBallCirclePaint.setColor(this.mHandleCircleColor);
        this.mBallCirclePaint.setStyle(Style.FILL_AND_STROKE);
        this.mBallCirclePaint.setStrokeWidth(getResources().getDimension(R.dimen.mc_multiwaveview_handle_circle_width));
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mValueHolder = new ValueHolder();
        resetCircleAnimation();
        this.mHandleCircleValueHolder = new ValueHolder();
        if (this.mVibrationDuration <= 0) {
            z = false;
        }
        setVibrateEnabled(z);
        assignDefaultsIfNeeded();
    }

    private void dump() {
        Log.v(TAG, "Outer Radius = " + this.mOuterRadius);
        Log.v(TAG, "VibrationDuration = " + this.mVibrationDuration);
        Log.v(TAG, "TapRadius = " + this.mTapRadius);
        Log.v(TAG, "WaveCenterX = " + this.mWaveCenterX);
        Log.v(TAG, "WaveCenterY = " + this.mWaveCenterY);
        Log.v(TAG, "mTargetRadiusFromCenter = " + this.mTargetRadiusFromCenter);
        Log.v(TAG, "mOuterRadiusHeight = " + this.mOuterRadiusHeight);
    }

    public void suspendAnimations() {
        this.mHandleAnimations.setSuspended(true);
    }

    public void resumeAnimations() {
        this.mHandleAnimations.setSuspended(false);
        this.mHandleAnimations.start();
    }

    protected int getSuggestedMinimumWidth() {
        return (int) ((Math.max((float) this.mOuterRing.getWidth(), this.mOuterRadius * CircleProgressBar.BAR_WIDTH_DEF_DIP) + ((float) (this.mMaxTargetWidth * 2))) + (this.mTapRadius * CircleProgressBar.BAR_WIDTH_DEF_DIP));
    }

    protected int getSuggestedMinimumHeight() {
        return (int) (this.mOuterRadiusHeight + ((float) this.mMaxTargetHeight));
    }

    private int resolveMeasured(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        switch (MeasureSpec.getMode(i)) {
            case Integer.MIN_VALUE:
                return Math.min(size, i2);
            case 0:
                return i2;
            default:
                return size;
        }
    }

    protected void onMeasure(int i, int i2) {
        int suggestedMinimumWidth = getSuggestedMinimumWidth();
        int suggestedMinimumHeight = getSuggestedMinimumHeight();
        int resolveMeasured = resolveMeasured(i, suggestedMinimumWidth);
        int resolveMeasured2 = resolveMeasured(i2, suggestedMinimumHeight);
        computeInsets(resolveMeasured - suggestedMinimumWidth, resolveMeasured2 - suggestedMinimumHeight);
        setMeasuredDimension(resolveMeasured, resolveMeasured2);
    }

    private void switchToState(int i, float f, float f2) {
        switch (i) {
            case 0:
                this.mHandleDrawable.setState(TargetDrawable.STATE_INACTIVE);
                return;
            case 2:
                setGrabbedState(1);
                if (getInstance(getContext()).isEnabled()) {
                    announceTargets();
                    return;
                }
                return;
            case 5:
                doFinish();
                return;
            default:
                return;
        }
    }

    private void triggerHandleAnimation(int i, int i2, TimeInterpolator timeInterpolator, float f, float f2, float f3, float f4, AnimatorListener animatorListener) {
        this.mHandleAnimations.cancel();
        this.mHandleAnimations.add(Tweener.to(this.mHandleDrawable, (long) i, "ease", timeInterpolator, "delay", Integer.valueOf(i2), "alpha", Float.valueOf(f), "x", Float.valueOf(f2), "y", Float.valueOf(f3), "rotation", Float.valueOf(f4), "onUpdate", this.mUpdateListener, "onComplete", animatorListener));
        this.mHandleAnimations.start();
    }

    private void detectHandleAnimation(int i, int i2, TimeInterpolator timeInterpolator, float f, float f2, float f3, float f4, AnimatorListener animatorListener) {
        AnimationBundle animationBundle = new AnimationBundle();
        animationBundle.add(Tweener.to(this.mHandleDrawable, (long) i, "ease", timeInterpolator, "delay", Integer.valueOf(i2), "alpha", Float.valueOf(f), "x", Float.valueOf(f2), "y", Float.valueOf(f3), "rotation", Float.valueOf(f4), "onUpdate", this.mUpdateListener, "onComplete", animatorListener));
        animationBundle.start();
    }

    void invalidateGlobalRegion(TargetDrawable targetDrawable) {
        int width = targetDrawable.getWidth();
        int height = targetDrawable.getHeight();
        RectF rectF = new RectF(0.0f, 0.0f, (float) width, (float) height);
        rectF.offset(targetDrawable.getX() - ((float) (width / 2)), targetDrawable.getY() - ((float) (height / 2)));
        View view;
        while (view.getParent() != null && (view.getParent() instanceof View)) {
            View view2 = (View) view.getParent();
            view2.getMatrix().mapRect(rectF);
            view2.invalidate((int) Math.floor((double) rectF.left), (int) Math.floor((double) rectF.top), (int) Math.ceil((double) rectF.right), (int) Math.ceil((double) rectF.bottom));
            view = view2;
        }
    }

    private void dispatchTriggerEvent(int i) {
        vibrate();
        if (this.mOnTriggerListener != null) {
            Log.i(TAG, "onTrigger whichTarget = " + i + " mIsBluetoothAns = " + this.mIsBluetoothAns);
            this.mOnTriggerListener.onTrigger(this, i, this.mIsBluetoothAns);
            this.mIsBluetoothAns = false;
        }
    }

    private void dispatchOnFinishFinalAnimation() {
        if (this.mOnTriggerListener != null) {
            this.mOnTriggerListener.onFinishFinalAnimation();
        }
    }

    public boolean startMove2TargetAnimation(int i, long j, boolean z, boolean z2) {
        TargetDrawable targetDrawable;
        if (this.mActiveTarget >= 0 || this.mActiveTarget == i || i < 0 || i >= this.mTargetDrawables.size()) {
            targetDrawable = null;
        } else {
            targetDrawable = (TargetDrawable) this.mTargetDrawables.get(i);
            this.mActiveTarget = i;
        }
        if (targetDrawable == null) {
            return false;
        }
        this.mMoveAnimations.cancel();
        float f = i == 0 ? 0.0f : 135.0f;
        float f2 = i == 0 ? this.mTargetRadiusFromCenter : -this.mTargetRadiusFromCenter;
        this.mMoveAnimations.add(Tweener.to(this.mHandleDrawable, j, "ease", circleAnimationInterpolator, "delay", Integer.valueOf(0), "alpha", Float.valueOf(this.mHandleDrawable.getAlpha()), "x", Float.valueOf(f2), "y", Integer.valueOf(0), "rotation", Float.valueOf(f), "onUpdate", this.mUpdateListener, "onComplete", new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                MultiWaveView.this.switchToState(2, 0.0f, 0.0f);
            }

            public void onAnimationEnd(Animator animator) {
                MultiWaveView.this.mQuickClick = false;
                Tweener.reset();
                MultiWaveView.this.doFinish();
                MultiWaveView.this.detectHandleAnimation(330, MultiWaveView.RETURN_TO_HOME_DELAY, Quart.easeOut, 1.0f, 0.0f, 0.0f, 0.0f, MultiWaveView.this.mResetListener);
            }

            public void onAnimationCancel(Animator animator) {
                MultiWaveView.this.mQuickClick = false;
                Tweener.reset();
                MultiWaveView.this.doFinish();
                MultiWaveView.this.mIsBluetoothAns = false;
            }
        }));
        this.mQuickClick = true;
        this.mMoveAnimations.start();
        if (this.mCircleAnimation != null) {
            this.mCircleAnimation.cancel();
            resetCircleAnimation();
            invalidate();
        }
        if (this.mHandleCircleValueHolder != null) {
            this.mDrawHandleCircle = true;
            this.mHandleCircleValueHolder.setRadius(this.mTargetMaxRadius - 5.0f);
        }
        this.mIsBluetoothAns = z2;
        return true;
    }

    public void cancelMove2TargetAnimation() {
        if (this.mMoveAnimations != null) {
            this.mMoveAnimations.cancel();
        }
    }

    private void doFinish() {
        int i = this.mActiveTarget;
        if ((i != -1 ? 1 : null) != null) {
            AnimatorListener anonymousClass5 = new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    Tweener.reset();
                    MultiWaveView.this.detectHandleAnimation(330, MultiWaveView.RETURN_TO_HOME_DELAY, Quart.easeOut, 1.0f, 0.0f, 0.0f, 0.0f, MultiWaveView.this.mResetListener);
                }

                public void onAnimationEnd(Animator animator) {
                    Tweener.reset();
                    MultiWaveView.this.detectHandleAnimation(330, MultiWaveView.RETURN_TO_HOME_DELAY, Quart.easeOut, 1.0f, 0.0f, 0.0f, 0.0f, MultiWaveView.this.mResetListener);
                }
            };
            dispatchTriggerEvent(i);
            if (this.mDragging) {
                switch (i) {
                    case 0:
                        triggerHandleAnimation(100, 0, circleAnimationInterpolator, this.mHandleDrawable.getAlpha(), this.mTargetRadiusFromCenter, 0.0f, 0.0f, anonymousClass5);
                        break;
                    case 1:
                        triggerHandleAnimation(100, 0, circleAnimationInterpolator, this.mHandleDrawable.getAlpha(), -this.mTargetRadiusFromCenter, 0.0f, 135.0f, anonymousClass5);
                        break;
                }
                if (this.mCircleAnimation != null) {
                    this.mCircleAnimation.cancel();
                    resetCircleAnimation();
                    invalidate();
                }
            }
        } else {
            triggerHandleAnimation(330, 200, Quart.easeOut, 1.0f, 0.0f, 0.0f, 0.0f, this.mResetListenerWithPing);
        }
        setGrabbedState(0);
        this.mDragging = false;
    }

    private void vibrate() {
        if (this.mVibrator != null) {
            this.mVibrator.vibrate((long) this.mVibrationDuration);
        }
    }

    private ArrayList<TargetDrawable> loadDrawableArray(int i) {
        Resources resources = getContext().getResources();
        TypedArray obtainTypedArray = resources.obtainTypedArray(i);
        int length = obtainTypedArray.length();
        ArrayList<TargetDrawable> arrayList = new ArrayList(length);
        for (int i2 = 0; i2 < length; i2++) {
            Object targetDrawable;
            TypedValue peekValue = obtainTypedArray.peekValue(i2);
            if (peekValue == null || peekValue.type != 28) {
                TargetDrawable targetDrawable2 = new TargetDrawable(resources, peekValue != null ? peekValue.resourceId : 0);
            } else {
                targetDrawable = new TargetDrawable(resources.getColor(peekValue.resourceId), this.mTargetMinRadius);
            }
            arrayList.add(targetDrawable);
        }
        obtainTypedArray.recycle();
        return arrayList;
    }

    private void internalSetTargetResources(int i) {
        this.mTargetDrawables = loadDrawableArray(i);
        this.mTargetResourceId = i;
        int size = this.mTargetDrawables.size();
        int width = this.mHandleDrawable.getWidth();
        int height = this.mHandleDrawable.getHeight();
        for (int i2 = 0; i2 < size; i2++) {
            TargetDrawable targetDrawable = (TargetDrawable) this.mTargetDrawables.get(i2);
            width = Math.max(width, targetDrawable.getWidth());
            height = Math.max(height, targetDrawable.getHeight());
        }
        if (this.mMaxTargetWidth == width && this.mMaxTargetHeight == height) {
            updateTargetPositions(this.mWaveCenterX, this.mWaveCenterY);
            return;
        }
        this.mMaxTargetWidth = width;
        this.mMaxTargetHeight = height;
        requestLayout();
    }

    public void setTargetResources(int i) {
        if (this.mAnimatingTargets) {
            this.mNewTargetResources = i;
        } else {
            internalSetTargetResources(i);
        }
    }

    public int getTargetResourceId() {
        return this.mTargetResourceId;
    }

    public void setTargetDescriptionsResourceId(int i) {
        this.mTargetDescriptionsResourceId = i;
        if (this.mTargetDescriptions != null) {
            this.mTargetDescriptions.clear();
        }
    }

    public int getTargetDescriptionsResourceId() {
        return this.mTargetDescriptionsResourceId;
    }

    public void setDirectionDescriptionsResourceId(int i) {
        this.mDirectionDescriptionsResourceId = i;
        if (this.mDirectionDescriptions != null) {
            this.mDirectionDescriptions.clear();
        }
    }

    public int getDirectionDescriptionsResourceId() {
        return this.mDirectionDescriptionsResourceId;
    }

    public void setVibrateEnabled(boolean z) {
        if (z && this.mVibrator == null) {
            this.mVibrator = (Vibrator) getContext().getSystemService("vibrator");
        } else if (!z) {
            this.mVibrator = null;
        }
    }

    public void ping() {
        if (!(this.mActiveTarget == -1 || this.mDragging || this.mQuickClick)) {
            triggerHandleAnimation(330, 200, Quart.easeOut, 1.0f, 0.0f, 0.0f, 0.0f, this.mResetListenerWithPing);
            this.mActiveTarget = -1;
        }
        if (this.mCircleAnimation == null || !this.mCircleAnimation.isStarted()) {
            resetCircleAnimation();
            startCircleAnimation(CIRCLE_ANIMATION_TIME, circleAnimationInterpolator, 0.0f);
        }
        this.mDrawHandleCircle = false;
    }

    public void reset(boolean z) {
        this.mHandleAnimations.stop();
        triggerHandleAnimation(0, 0, Quart.easeOut, 1.0f, 0.0f, 0.0f, 0.0f, null);
        Tweener.reset();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.mPaintColor = this.mHandleCircleColor;
                handleDown(motionEvent);
                if (this.mDrawHandleCircle && this.mCircleAnimation != null) {
                    this.mCircleAnimation.cancel();
                    break;
                }
            case 1:
            case 6:
                handleMove(motionEvent);
                if (motionEvent != null) {
                    Log.i(TAG, "Action Up x = " + motionEvent.getX() + " y = " + motionEvent.getY());
                }
                handleUp(motionEvent);
                break;
            case 2:
                handleMove(motionEvent);
                break;
            case 3:
                handleMove(motionEvent);
                handleCancel(motionEvent);
                break;
            default:
                return super.onTouchEvent(motionEvent);
        }
        invalidate();
        return true;
    }

    private void moveHandleTo(float f, float f2, boolean z) {
        this.mHandleDrawable.setX(f);
        this.mHandleDrawable.setY(f2);
    }

    private void handleDown(MotionEvent motionEvent) {
        float x;
        float y;
        if (motionEvent != null) {
            int actionIndex = motionEvent.getActionIndex();
            this.mActivePointerId = motionEvent.getPointerId(actionIndex);
            x = motionEvent.getX(actionIndex);
            y = motionEvent.getY(actionIndex);
            cancelMove2TargetAnimation();
        } else if (this.mActivePointerId == -1) {
            x = this.mMovePoint.x;
            y = this.mMovePoint.y;
        } else {
            return;
        }
        this.mMotionDownX = x;
        this.mMotionDownY = y;
        switchToState(1, x, y);
        if (!trySwitchToFirstTouchState(x, y)) {
            this.mDragging = false;
            this.mQuickClick = false;
            this.mActiveTarget = -1;
            ping();
        }
    }

    private void handleUp(MotionEvent motionEvent) {
        int actionIndex;
        float x;
        float y;
        if (this.mDragging) {
            if (motionEvent == null) {
                actionIndex = motionEvent.getActionIndex();
                if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
                    x = motionEvent.getX(actionIndex);
                    y = motionEvent.getY(actionIndex);
                    this.mActivePointerId = -1;
                } else {
                    return;
                }
            } else if (this.mActivePointerId != -1) {
                x = this.mMovePoint.x;
                y = this.mMovePoint.y;
            } else {
                return;
            }
            if (!this.mQuickClick) {
                switchToState(5, x, y);
            }
            if (this.mDrawHandleCircle) {
                this.mDrawPointCircle = false;
            }
        }
        if (motionEvent == null) {
            actionIndex = motionEvent.getActionIndex();
            if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
                x = motionEvent.getX(actionIndex);
                y = motionEvent.getY(actionIndex);
                this.mActivePointerId = -1;
            } else {
                return;
            }
        } else if (this.mActivePointerId != -1) {
            x = this.mMovePoint.x;
            y = this.mMovePoint.y;
        } else {
            return;
        }
        if (this.mQuickClick) {
            switchToState(5, x, y);
        }
        if (this.mDrawHandleCircle) {
            this.mDrawPointCircle = false;
        }
    }

    private void handleCancel(MotionEvent motionEvent) {
        int actionIndex;
        float x;
        float y;
        if (this.mDragging) {
            if (motionEvent == null) {
                actionIndex = motionEvent.getActionIndex();
                if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
                    x = motionEvent.getX(actionIndex);
                    y = motionEvent.getY(actionIndex);
                    this.mActivePointerId = -1;
                } else {
                    return;
                }
            } else if (this.mActivePointerId != -1) {
                x = this.mMovePoint.x;
                y = this.mMovePoint.y;
            } else {
                return;
            }
            if (!this.mQuickClick) {
                switchToState(5, x, y);
            }
        }
        if (motionEvent == null) {
            actionIndex = motionEvent.getActionIndex();
            if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
                x = motionEvent.getX(actionIndex);
                y = motionEvent.getY(actionIndex);
                this.mActivePointerId = -1;
            } else {
                return;
            }
        } else if (this.mActivePointerId != -1) {
            x = this.mMovePoint.x;
            y = this.mMovePoint.y;
        } else {
            return;
        }
        if (!this.mQuickClick) {
            switchToState(5, x, y);
        }
    }

    private void handleMove(MotionEvent motionEvent) {
        handleMove(motionEvent, false);
    }

    private void handleMove(MotionEvent motionEvent, boolean z) {
        int i;
        if (motionEvent == null) {
            if (this.mActivePointerId == -1) {
                i = 0;
            } else {
                return;
            }
        } else if (this.mActivePointerId != -1) {
            i = motionEvent.findPointerIndex(this.mActivePointerId);
        } else {
            return;
        }
        int historySize = motionEvent == null ? 0 : motionEvent.getHistorySize();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        float f = 0.0f;
        int i2 = -1;
        float f2 = 0.0f;
        int i3 = 0;
        while (i3 < historySize + 1) {
            if (motionEvent == null) {
                x = this.mMovePoint.x;
                y = this.mMovePoint.y;
            } else {
                x = i3 < historySize ? motionEvent.getHistoricalX(i, i3) : motionEvent.getX(i);
                y = i3 < historySize ? motionEvent.getHistoricalY(i, i3) : motionEvent.getY(i);
            }
            float f3 = x - this.mWaveCenterX;
            float f4 = y - this.mWaveCenterY;
            f = (float) Math.sqrt((double) dist2(f3, f4));
            f = f > this.mTargetRadiusFromCenter ? this.mTargetRadiusFromCenter / f : 1.0f;
            float f5 = f3 * f;
            f2 = f4 * f;
            double atan2 = Math.atan2((double) (-f4), (double) f3);
            if (!(this.mDragging || this.mQuickClick)) {
                trySwitchToFirstTouchState(x, y);
            }
            if (!this.mQuickClick) {
                if (this.mDragging) {
                    i2 = findActiveTarget(atan2);
                }
                if (this.mHandleDrawable != null && this.mDragging) {
                    if (f3 < 0.0f && f3 > -117.0f * this.density) {
                        this.mHandleDrawable.setRotation(135.0f * ((-f3) / (117.0f * this.density)));
                        this.mPaintColor = ((Integer) this.evaluator.evaluate((-f3) / (117.0f * this.density), Integer.valueOf(this.mHandleCircleColor), Integer.valueOf(this.mColorOrange))).intValue();
                    } else if (f3 > 0.0f && f3 < 117.0f * this.density) {
                        this.mPaintColor = ((Integer) this.evaluator.evaluate(f3 / (117.0f * this.density), Integer.valueOf(this.mHandleCircleColor), Integer.valueOf(this.mColorGreen))).intValue();
                        this.mHandleDrawable.setRotation(0.0f);
                    }
                }
                i3++;
                f = f2;
                f2 = f5;
            } else {
                return;
            }
        }
        if (this.mDragging) {
            if (i2 != -1) {
                switchToState(4, f2, f);
                moveHandleTo(f2, f, false);
            } else {
                switchToState(3, f2, f);
                moveHandleTo(f2, f, false);
            }
            f = Math.abs(this.mMotionDownX - x);
            float abs = Math.abs(this.mMotionDownY - y);
            if (this.mDrawPointCircle && !((abs <= ((float) this.mTouchSlop) && f <= ((float) this.mTouchSlop)) || this.mHoldCircleAnimation || this.mCircleAnimation == null || this.mCircleAnimation.isStarted())) {
                this.mDrawPointCircle = false;
                invalidate();
            }
            invalidateGlobalRegion(this.mHandleDrawable);
            this.mActiveTarget = i2;
        }
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        if (getInstance(getContext()).isTouchExplorationEnabled()) {
            int action = motionEvent.getAction();
            switch (action) {
                case 7:
                    motionEvent.setAction(2);
                    break;
                case 9:
                    motionEvent.setAction(0);
                    break;
                case 10:
                    motionEvent.setAction(1);
                    break;
            }
            onTouchEvent(motionEvent);
            motionEvent.setAction(action);
        }
        return super.onHoverEvent(motionEvent);
    }

    private void setGrabbedState(int i) {
        if (i != this.mGrabbedState) {
            if (i != 0) {
                vibrate();
            }
            this.mGrabbedState = i;
            if (this.mOnTriggerListener != null) {
                if (i == 0) {
                    this.mOnTriggerListener.onReleased(this, 1);
                } else {
                    this.mOnTriggerListener.onGrabbed(this, 1);
                }
                this.mOnTriggerListener.onGrabbedStateChange(this, i);
            }
        }
    }

    private boolean trySwitchToFirstTouchState(float f, float f2) {
        float f3 = f - this.mWaveCenterX;
        float f4 = f2 - this.mWaveCenterY;
        if (dist2(f3, f4) <= getScaledTapRadiusSquared()) {
            switchToState(2, f, f2);
            moveHandleTo(f3, f4, false);
            setHoldCircleAnimation(f, f2);
            this.mDragging = true;
            return true;
        } else if (this.mDisableQuickClick || Math.sqrt((double) dist2(f3, f4)) < ((double) (this.mTargetRadiusFromCenter - this.mTargetBeginScaleDistance)) || Math.abs(f4) > this.mTapRadius) {
            return false;
        } else {
            this.mActiveTarget = findActiveTarget(Math.atan2((double) (-f4), (double) f3));
            if (this.mHandleDrawable.getX() != this.widthForHandler || this.mHandleDrawable.getY() != this.hightForHandler) {
                return false;
            }
            if (f3 < 0.0f) {
                triggerHandleAnimation(330, 0, circleAnimationInterpolator, this.mHandleDrawable.getAlpha(), -this.mTargetRadiusFromCenter, 0.0f, 135.0f, this.mFinishListener);
            } else if (f3 > 0.0f) {
                triggerHandleAnimation(330, 0, circleAnimationInterpolator, this.mHandleDrawable.getAlpha(), this.mTargetRadiusFromCenter, 0.0f, 0.0f, this.mFinishListener);
            }
            this.mQuickClick = true;
            if (this.mCircleAnimation != null) {
                this.mCircleAnimation.cancel();
                resetCircleAnimation();
                invalidate();
            }
            return true;
        }
    }

    private void assignDefaultsIfNeeded() {
        if (this.mOuterRadius == 0.0f) {
            this.mOuterRadius = ((float) Math.max(this.mOuterRing.getWidth(), this.mOuterRing.getHeight())) / CircleProgressBar.BAR_WIDTH_DEF_DIP;
        }
    }

    private void computeInsets(int i, int i2) {
        int absoluteGravity = Gravity.getAbsoluteGravity(this.mGravity, getLayoutDirection());
        switch (absoluteGravity & 7) {
            case 3:
                this.mHorizontalInset = 0;
                break;
            case 5:
                this.mHorizontalInset = i;
                break;
            default:
                this.mHorizontalInset = i / 2;
                break;
        }
        switch (absoluteGravity & 112) {
            case 48:
                this.mVerticalInset = 0;
                return;
            case 80:
                this.mVerticalInset = i2;
                return;
            default:
                this.mVerticalInset = i2 / 2;
                return;
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int i5 = i4 - i2;
        float f = (float) this.mHorizontalInset;
        float max = (Math.max((float) (i3 - i), (Math.max((float) this.mOuterRing.getWidth(), this.mOuterRadius * CircleProgressBar.BAR_WIDTH_DEF_DIP) + ((float) (this.mMaxTargetWidth * 2))) + (this.mTapRadius * CircleProgressBar.BAR_WIDTH_DEF_DIP)) / CircleProgressBar.BAR_WIDTH_DEF_DIP) + f;
        float max2 = (Math.max((float) i5, ((float) this.mMaxTargetHeight) + this.mOuterRadiusHeight) / CircleProgressBar.BAR_WIDTH_DEF_DIP) + ((float) this.mVerticalInset);
        if (this.mInitialLayout) {
            moveHandleTo(0.0f, 0.0f, false);
            this.mInitialLayout = false;
        }
        this.mOuterRing.setPositionX(max);
        this.mOuterRing.setPositionY(max2);
        this.mHandleDrawable.setPositionX(max);
        this.mHandleDrawable.setPositionY(max2);
        updateTargetPositions(max, max2);
        this.mWaveCenterX = max;
        this.mWaveCenterY = max2;
        this.mPointCircleX = this.mWaveCenterX;
        this.mPointCircleY = this.mWaveCenterY;
    }

    private void updateTargetPositions(float f, float f2) {
        ArrayList arrayList = this.mTargetDrawables;
        int size = arrayList.size();
        float f3 = (float) (-6.283185307179586d / ((double) size));
        for (int i = 0; i < size; i++) {
            TargetDrawable targetDrawable = (TargetDrawable) arrayList.get(i);
            float f4 = ((float) i) * f3;
            targetDrawable.setPositionX(f);
            targetDrawable.setPositionY(f2);
            targetDrawable.setX(this.mTargetRadiusFromCenter * ((float) Math.cos((double) f4)));
            targetDrawable.setY(((float) Math.sin((double) f4)) * this.mTargetRadiusFromCenter);
        }
    }

    protected void onDraw(Canvas canvas) {
        if (this.mDrawPointCircle) {
            canvas.save();
            canvas.translate(this.mPointCircleX, this.mPointCircleY);
            this.mDefaultBackgroundPaint.setColor(this.mIconbgColor);
            canvas.drawCircle(0.0f, 0.0f, 43.0f * this.density, this.mDefaultBackgroundPaint);
            this.mBigCirclePaint.setStrokeWidth(this.mStrokeWidth);
            this.mBigCirclePaint.setAlpha(this.mBigCircleAlpha);
            float f = ((61.0f * this.density) - this.mCurCircleRadius) / (18.0f * this.density);
            if (this.mCurCircleRadius > 43.0f * this.density && this.mCurCircleRadius <= 61.0f * this.density) {
                this.mBallCirclePaint.setAlpha((int) (SNAP_MARGIN_DEFAULT * f));
                this.mBigCirclePaint.setStrokeWidth(f * 5.0f);
            }
            if (this.mCurCircleRadius >= 61.0f * this.density) {
                this.mBigCirclePaint.setAlpha(0);
                this.mBigCirclePaint.setStrokeWidth(0.0f);
            }
            canvas.drawCircle(0.0f, 0.0f, this.mCurCircleRadius, this.mBigCirclePaint);
            this.mBigCirclePaint.setAlpha(this.mBigCircleAlpha);
            this.mBigCirclePaint.setStrokeWidth(this.mStrokeWidth);
            if (this.mCurCircleRadius > 53.0f * this.density) {
                this.mBigCirclePaint.setStrokeWidth((((77.0f * this.density) - this.mCurCircleRadius) / (24.0f * this.density)) * 5.0f);
                canvas.drawCircle(0.0f, 0.0f, this.mCurCircleRadius - (9.0f * this.density), this.mBigCirclePaint);
            }
            this.mBallCirclePaint.setColor(this.mColorGreen);
            this.mBallCirclePaint.setAlpha(this.mBallAlpha);
            f = ((this.density * 133.0f) - this.mTranslateCircleX) / (33.0f * this.density);
            if (this.mTranslateCircleX >= 100.0f * this.density && this.mTranslateCircleX <= this.density * 133.0f) {
                this.mBallCirclePaint.setAlpha((int) (f * 255.0f));
            }
            if (this.mTranslateCircleX >= this.density * 133.0f) {
                this.mBallCirclePaint.setAlpha(0);
            }
            canvas.drawCircle(this.mTranslateCircleX, 0.0f, (5.0f * this.density) + 1.0f, this.mBallCirclePaint);
            this.mBallCirclePaint.setAlpha(this.mBallAlpha);
            if (this.mTranslateCircleX > 75.0f * this.density) {
                f = ((this.density * 155.0f) - this.mTranslateCircleX) / (33.0f * this.density);
                if (this.mTranslateCircleX >= 122.0f * this.density && this.mTranslateCircleX <= this.density * 155.0f) {
                    this.mBallCirclePaint.setAlpha((int) (f * 255.0f));
                }
                if (this.mTranslateCircleX >= this.density * 155.0f) {
                    this.mBallCirclePaint.setAlpha(0);
                }
                canvas.drawCircle(this.mTranslateCircleX - (SNAP_MARGIN_DEFAULT * this.density), 0.0f, 3.0f * this.density, this.mBallCirclePaint);
            }
            this.mBallCirclePaint.setAlpha(this.mBallAlpha);
            if (this.mTranslateCircleX > 91.0f * this.density) {
                if (this.mTranslateCircleX > 171.0f * this.density) {
                    this.mBallCirclePaint.setAlpha(0);
                }
                canvas.drawCircle(this.mTranslateCircleX - (36.0f * this.density), 0.0f, this.density + 1.0f, this.mBallCirclePaint);
            }
            this.mBallCirclePaint.setColor(this.mColorOrange);
            this.mBallCirclePaint.setAlpha(this.mBallAlpha);
            f = ((this.density * 133.0f) - this.mTranslateCircleX) / (33.0f * this.density);
            if (this.mTranslateCircleX >= 100.0f * this.density && this.mTranslateCircleX <= this.density * 133.0f) {
                this.mBallCirclePaint.setAlpha((int) (f * 255.0f));
            }
            if (this.mTranslateCircleX >= this.density * 133.0f) {
                this.mBallCirclePaint.setAlpha(0);
            }
            canvas.drawCircle(-this.mTranslateCircleX, 0.0f, (5.0f * this.density) + 1.0f, this.mBallCirclePaint);
            this.mBallCirclePaint.setAlpha(this.mBallAlpha);
            if (this.mTranslateCircleX > 75.0f * this.density) {
                f = ((this.density * 155.0f) - this.mTranslateCircleX) / (33.0f * this.density);
                if (this.mTranslateCircleX >= 122.0f * this.density && this.mTranslateCircleX <= this.density * 155.0f) {
                    this.mBallCirclePaint.setAlpha((int) (f * 255.0f));
                }
                if (this.mTranslateCircleX >= this.density * 155.0f) {
                    this.mBallCirclePaint.setAlpha(0);
                }
                canvas.drawCircle((-this.mTranslateCircleX) + (SNAP_MARGIN_DEFAULT * this.density), 0.0f, 3.0f * this.density, this.mBallCirclePaint);
            }
            this.mBallCirclePaint.setAlpha(this.mBallAlpha);
            if (this.mTranslateCircleX > 91.0f * this.density) {
                if (this.mTranslateCircleX > 171.0f * this.density) {
                    this.mBallCirclePaint.setAlpha(0);
                }
                canvas.drawCircle((-this.mTranslateCircleX) + (36.0f * this.density), 0.0f, this.density + 1.0f, this.mBallCirclePaint);
            }
            this.mHandleDrawable.setRotation(this.mShakeAngel);
            canvas.restore();
        }
        if (this.mDrawHandleCircle && this.mHandleCircleValueHolder != null) {
            this.mDrawPointCircle = false;
            canvas.save();
            this.mHandleCirclePaint.setColor(this.mPaintColor);
            canvas.drawCircle(this.mHandleDrawable.getX() + this.mHandleDrawable.getPositionX(), this.mHandleDrawable.getY() + this.mHandleDrawable.getPositionY(), this.mHandleCircleValueHolder.getRadius(), this.mHandleCirclePaint);
            canvas.restore();
        }
        int size = this.mTargetDrawables.size();
        if (!this.mDrawPointCircle) {
            for (int i = 0; i < size; i++) {
                TargetDrawable targetDrawable = (TargetDrawable) this.mTargetDrawables.get(i);
                if (targetDrawable != null) {
                    targetDrawable.draw(canvas, false);
                }
            }
        }
        this.mHandleDrawable.draw(canvas, true);
    }

    public void setOnTriggerListener(OnTriggerListener onTriggerListener) {
        this.mOnTriggerListener = onTriggerListener;
    }

    private float square(float f) {
        return f * f;
    }

    private float dist2(float f, float f2) {
        return (f * f) + (f2 * f2);
    }

    private float getScaledTapRadiusSquared() {
        float f;
        if (getInstance(getContext()).isEnabled()) {
            f = (TAP_RADIUS_SCALE_ACCESSIBILITY_ENABLED * this.mTapRadius) * CircleProgressBar.BAR_WIDTH_DEF_DIP;
        } else {
            f = this.mTapRadius * CircleProgressBar.BAR_WIDTH_DEF_DIP;
        }
        return square(f);
    }

    private void announceTargets() {
        StringBuilder stringBuilder = new StringBuilder();
        int size = this.mTargetDrawables.size();
        for (int i = 0; i < size; i++) {
            CharSequence targetDescription = getTargetDescription(i);
            Object directionDescription = getDirectionDescription(i);
            if (!(TextUtils.isEmpty(targetDescription) || TextUtils.isEmpty(directionDescription))) {
                stringBuilder.append(String.format(directionDescription, new Object[]{targetDescription}));
            }
            if (stringBuilder.length() > 0) {
                announceText(stringBuilder.toString());
            }
        }
    }

    private void announceText(String str) {
        setContentDescription(str);
        sendAccessibilityEvent(8);
        setContentDescription(null);
    }

    private String getTargetDescription(int i) {
        if (this.mTargetDescriptions == null || this.mTargetDescriptions.isEmpty()) {
            this.mTargetDescriptions = loadDescriptions(this.mTargetDescriptionsResourceId);
            if (this.mTargetDrawables.size() != this.mTargetDescriptions.size()) {
                Log.w(TAG, "The number of target drawables must be euqal to the number of target descriptions.");
                return null;
            }
        }
        return (String) this.mTargetDescriptions.get(i);
    }

    private String getDirectionDescription(int i) {
        if (this.mDirectionDescriptions == null || this.mDirectionDescriptions.isEmpty()) {
            this.mDirectionDescriptions = loadDescriptions(this.mDirectionDescriptionsResourceId);
            if (this.mTargetDrawables.size() != this.mDirectionDescriptions.size()) {
                Log.w(TAG, "The number of target drawables must be euqal to the number of direction descriptions.");
                return null;
            }
        }
        return (String) this.mDirectionDescriptions.get(i);
    }

    private ArrayList<String> loadDescriptions(int i) {
        TypedArray obtainTypedArray = getContext().getResources().obtainTypedArray(i);
        int length = obtainTypedArray.length();
        ArrayList<String> arrayList = new ArrayList(length);
        for (int i2 = 0; i2 < length; i2++) {
            arrayList.add(obtainTypedArray.getString(i2));
        }
        obtainTypedArray.recycle();
        return arrayList;
    }

    public int getResourceIdForTarget(int i) {
        TargetDrawable targetDrawable = (TargetDrawable) this.mTargetDrawables.get(i);
        return targetDrawable == null ? 0 : targetDrawable.getResourceId();
    }

    public void setEnableTarget(int i, boolean z) {
        for (int i2 = 0; i2 < this.mTargetDrawables.size(); i2++) {
            TargetDrawable targetDrawable = (TargetDrawable) this.mTargetDrawables.get(i2);
            if (targetDrawable.getResourceId() == i) {
                targetDrawable.setEnabled(z);
                return;
            }
        }
    }

    public int getTargetPosition(int i) {
        for (int i2 = 0; i2 < this.mTargetDrawables.size(); i2++) {
            if (((TargetDrawable) this.mTargetDrawables.get(i2)).getResourceId() == i) {
                return i2;
            }
        }
        return -1;
    }

    private boolean replaceTargetDrawables(Resources resources, int i, int i2) {
        boolean z = false;
        if (!(i == 0 || i2 == 0)) {
            ArrayList arrayList = this.mTargetDrawables;
            int size = arrayList.size();
            int i3 = 0;
            while (i3 < size) {
                boolean z2;
                TargetDrawable targetDrawable = (TargetDrawable) arrayList.get(i3);
                if (targetDrawable == null || targetDrawable.getResourceId() != i) {
                    z2 = z;
                } else {
                    targetDrawable.setDrawable(resources, i2);
                    z2 = true;
                }
                i3++;
                z = z2;
            }
            if (z) {
                requestLayout();
            }
        }
        return z;
    }

    public boolean replaceTargetDrawablesIfPresent(ComponentName componentName, String str, int i) {
        boolean z = false;
        if (i != 0) {
            try {
                PackageManager packageManager = getContext().getPackageManager();
                Bundle bundle = packageManager.getActivityInfo(componentName, 128).metaData;
                if (bundle != null) {
                    int i2 = bundle.getInt(str);
                    if (i2 != 0) {
                        z = replaceTargetDrawables(packageManager.getResourcesForActivity(componentName), i, i2);
                    }
                }
            } catch (Throwable e) {
                Log.w(TAG, "Failed to swap drawable; " + componentName.flattenToShortString() + " not found", e);
            } catch (Throwable e2) {
                Log.w(TAG, "Failed to swap drawable from " + componentName.flattenToShortString(), e2);
            }
        }
        return z;
    }

    private AccessibilityManager getInstance(Context context) {
        try {
            return (AccessibilityManager) Class.forName("android.view.accessibility.AccessibilityManager").getMethod("getInstance", new Class[]{Context.class}).invoke(null, new Object[]{context});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setGravity(int i) {
        this.mGravity = i;
    }

    public void resetCircleAnimation() {
        this.mCurCircleRadius = this.mMinPointCircleRadius;
        this.mCurPointRadius = this.mMinPointRadius;
        this.mCurAlpha = 0;
        this.mCurAngel = 0.0f;
        this.mHoldCircleAnimation = false;
        this.mPointCircleX = this.mWaveCenterX;
        this.mPointCircleY = this.mWaveCenterY;
        this.mDrawPointCircle = true;
        this.mBallAlpha = 0;
        this.mIconbgColor = 0;
        this.mShakeAngel = 0.0f;
        this.mBigCircleAlpha = 0;
    }

    private void startCircleAnimation(int i, TimeInterpolator timeInterpolator, float f) {
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("radius", new float[]{45.0f * this.density, 73.0f * this.density});
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat("angel", new float[]{this.MIN_ANGEL, this.MAX_ANGEL});
        PropertyValuesHolder ofFloat3 = PropertyValuesHolder.ofFloat("strokewidth", new float[]{5.0f, 0.0f});
        PropertyValuesHolder ofFloat4 = PropertyValuesHolder.ofFloat("translateX", new float[]{53.0f * this.density, 171.0f * this.density});
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt("bigcirclealpha", new int[]{30, 0});
        Keyframe ofInt2 = Keyframe.ofInt(0.0f, 0);
        Keyframe ofInt3 = Keyframe.ofInt(FastBlurParameters.DEFAULT_SCALE, 255);
        Keyframe ofInt4 = Keyframe.ofInt(0.78f, 255);
        Keyframe ofInt5 = Keyframe.ofInt(1.0f, 0);
        PropertyValuesHolder ofKeyframe = PropertyValuesHolder.ofKeyframe("ballalpha", new Keyframe[]{ofInt2, ofInt3, ofInt4, ofInt5});
        ofInt3 = Keyframe.ofFloat(0.0f, this.mMinPointRadius);
        ofInt4 = Keyframe.ofFloat(FastBlurParameters.DEFAULT_SCALE, this.mMaxPointRadius);
        ofInt5 = Keyframe.ofFloat(0.78f, this.mMaxPointRadius);
        Keyframe ofFloat5 = Keyframe.ofFloat(1.0f, this.mMinPointRadius);
        PropertyValuesHolder ofKeyframe2 = PropertyValuesHolder.ofKeyframe("pointRadius", new Keyframe[]{ofInt3, ofInt4, ofInt5, ofFloat5});
        ofInt4 = Keyframe.ofInt(0.0f, 0);
        ofInt5 = Keyframe.ofInt(FastBlurParameters.DEFAULT_SCALE, 160);
        ofFloat5 = Keyframe.ofInt(0.75f, 160);
        Keyframe ofInt6 = Keyframe.ofInt(1.0f, 0);
        PropertyValuesHolder ofKeyframe3 = PropertyValuesHolder.ofKeyframe("alpha", new Keyframe[]{ofInt4, ofInt5, ofFloat5, ofInt6});
        ofInt5 = Keyframe.ofFloat(0.0f, 0.0f);
        ofFloat5 = Keyframe.ofFloat(0.25f, 9.0f);
        ofInt6 = Keyframe.ofFloat(FastBlurParameters.DEFAULT_LEVEL, -9.0f);
        Keyframe ofFloat6 = Keyframe.ofFloat(0.75f, 9.0f);
        Keyframe ofFloat7 = Keyframe.ofFloat(1.0f, 0.0f);
        PropertyValuesHolder ofKeyframe4 = PropertyValuesHolder.ofKeyframe("rotation", new Keyframe[]{ofInt5, ofFloat5, ofInt6, ofFloat6, ofFloat7});
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this.mValueHolder, new PropertyValuesHolder[]{ofKeyframe4});
        ofPropertyValuesHolder.setDuration(500);
        ofPropertyValuesHolder.setInterpolator(new DecelerateInterpolator());
        ofPropertyValuesHolder.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                MultiWaveView.this.mShakeAngel = ((Float) valueAnimator.getAnimatedValue("rotation")).floatValue();
                MultiWaveView.this.invalidate();
            }
        });
        final ObjectAnimator ofPropertyValuesHolder2 = ObjectAnimator.ofPropertyValuesHolder(this.mValueHolder, new PropertyValuesHolder[]{ofFloat, ofFloat2, ofKeyframe, ofKeyframe2, ofFloat3, ofFloat4, ofKeyframe3, ofInt});
        ofPropertyValuesHolder2.setDuration((long) i);
        ofPropertyValuesHolder2.setInterpolator(timeInterpolator);
        final float f2 = f;
        ofPropertyValuesHolder2.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                MultiWaveView.this.mBallAlpha = ((Integer) valueAnimator.getAnimatedValue("ballalpha")).intValue();
                MultiWaveView.this.mTranslateCircleX = ((Float) valueAnimator.getAnimatedValue("translateX")).floatValue();
                MultiWaveView.this.mStrokeWidth = ((Float) valueAnimator.getAnimatedValue("strokewidth")).floatValue();
                MultiWaveView.this.mCurCircleRadius = ((Float) valueAnimator.getAnimatedValue("radius")).floatValue();
                MultiWaveView.this.mCurAngel = ((Float) valueAnimator.getAnimatedValue("angel")).floatValue();
                MultiWaveView.this.mBigCircleAlpha = ((Integer) valueAnimator.getAnimatedValue("bigcirclealpha")).intValue();
                MultiWaveView.this.mIconbgColor = MultiWaveView.this.mDefaultBgColor;
                MultiWaveView.this.mCurPointRadius = ((Float) valueAnimator.getAnimatedValue("pointRadius")).floatValue();
                if (!MultiWaveView.this.mHoldCircleAnimation || Math.abs(MultiWaveView.this.mCurCircleRadius - f2) >= 10.0f) {
                    MultiWaveView.this.invalidate();
                    return;
                }
                MultiWaveView.this.invalidate();
                MultiWaveView.this.mHoldCircleAnimation = false;
                ofPropertyValuesHolder2.cancel();
            }
        });
        this.animatorSet = new AnimatorSet();
        this.animatorSet.playSequentially(new Animator[]{ofPropertyValuesHolder2, ofPropertyValuesHolder});
        this.animatorSet.addListener(new AnimatorListener() {
            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                if (MultiWaveView.this.mDrawPointCircle) {
                    MultiWaveView.this.animatorSet.start();
                }
            }

            public void onAnimationCancel(Animator animator) {
                MultiWaveView.this.resetCircleAnimation();
            }

            public void onAnimationRepeat(Animator animator) {
            }
        });
        this.animatorSet.start();
        this.mCircleAnimation = ofPropertyValuesHolder2;
    }

    private void setHoldCircleAnimation(float f, float f2) {
        if (this.mCircleAnimation != null) {
            this.mCircleAnimation.cancel();
            resetCircleAnimation();
            invalidate();
        }
        this.mDrawHandleCircle = true;
        startHandleCircleAnimation(180, new DecelerateInterpolator(3.0f), 43.0f * this.density, this.mTargetMaxRadius - 5.0f);
    }

    private void startHandleCircleAnimation(int i, TimeInterpolator timeInterpolator, float f, float f2) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mHandleCircleValueHolder, "radius", new float[]{f, f2});
        ofFloat.setDuration((long) i);
        ofFloat.setInterpolator(timeInterpolator);
        ofFloat.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                MultiWaveView.this.invalidate();
            }
        });
        ofFloat.start();
        this.mHandleCircleAnimation = ofFloat;
    }

    private int findActiveTarget(double d) {
        ArrayList arrayList = this.mTargetDrawables;
        int i = -1;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            int i3;
            TargetDrawable targetDrawable = (TargetDrawable) arrayList.get(i2);
            double d2 = (((((double) i2) - 0.5d) * 2.0d) * 3.141592653589793d) / ((double) size);
            double d3 = (((((double) i2) + 0.5d) * 2.0d) * 3.141592653589793d) / ((double) size);
            float sqrt = (float) Math.sqrt((double) dist2((targetDrawable.getPositionX() + targetDrawable.getX()) - (this.mHandleDrawable.getPositionX() + this.mHandleDrawable.getX()), (targetDrawable.getPositionY() + targetDrawable.getY()) - (this.mHandleDrawable.getPositionY() + this.mHandleDrawable.getY())));
            if (targetDrawable.isEnabled()) {
                Object obj = ((d <= d2 || d > d3) && (6.283185307179586d + d <= d2 || 6.283185307179586d + d > d3)) ? null : 1;
                if (obj != null && sqrt <= this.mTargetBeginScaleDistance) {
                    float f = (((this.mTargetScaleStep - 1.0f) / this.mTargetBeginScaleDistance) * (this.mTargetBeginScaleDistance - sqrt)) + 1.0f;
                    i3 = i2;
                    i2++;
                    i = i3;
                }
            }
            i3 = i;
            i2++;
            i = i3;
        }
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return i;
        }
    }

    public void stop() {
        if (this.mCircleAnimation != null) {
            this.mCircleAnimation.end();
        }
        if (this.mHandleAnimations != null) {
            this.mHandleAnimations.stop();
        }
        if (this.mHandleCircleAnimation != null) {
            this.mHandleCircleAnimation.end();
        }
        if (this.animatorSet != null) {
            this.animatorSet.cancel();
        }
        invalidate();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }
}
