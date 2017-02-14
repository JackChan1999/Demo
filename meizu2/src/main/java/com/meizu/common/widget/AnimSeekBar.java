package com.meizu.common.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.meizu.common.R;

public class AnimSeekBar extends SkposSeekBar implements OnGestureListener {
    private static final int DEFAULT_ANIMSEEKBAE_HEIGHT = 65;
    private static final int DEFAULT_DISTANCE_BEW = 40;
    private static final int DEFAULT_EXPANDED_PIN_RADIUS_DP = 15;
    private static final int DEFAULT_LAGRECIRCLE_MOVEUP_VALUE = 24;
    private static final int DEFAULT_TEXT_COLOR = -1;
    private static final int DEFAULT_TEXT_SIZE = 15;
    private static final float DEFAULT_THUMB_RADIUS_DP = 0.0f;
    private static final boolean Debug = false;
    private static final String TAG = "AnimSeekBar";
    private int mAinmSeekBarHeight;
    private Rect mBounds;
    private boolean mCheckRadisChanged;
    private Drawable mCircleAnimDrawble;
    private int mCircleRadius;
    private int mDefaultHeight;
    private int mDefaultWidth;
    private int mDistanceBwCircle;
    private ValueAnimator mFadeAnim;
    private Interpolator mFadeInterpolator;
    private int mFadeValue;
    private GestureDetector mGesture;
    private boolean mIsLongOrScroll;
    private boolean mIsStartAnim;
    private boolean mIsTapPressed;
    private ValueAnimator mMoveAnim;
    private Interpolator mMoveDownInterpolator;
    private Interpolator mMoveUpInterpolator;
    private int mMoveUpValue;
    private int mMoveUpValueDp;
    private float mPinRadisMax;
    private float mPinRadisMin;
    private float mPinRadiusPx;
    private Drawable mProgressDrawble;
    private Resources mRes;
    private Interpolator mScaleInterpolator;
    private ValueAnimator mScaleanim;
    private int mSdkApi;
    private String mTextNumber;
    private int mTextNumberColor;
    private int mTextNumberSize;
    private Paint mTextPaint;
    private float mThumbHeight;
    private int mThumbRadis;
    private float mX;
    private float mY;

    public AnimSeekBar(Context context) {
        this(context, null);
    }

    public AnimSeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_AnimSeekBarStyle);
    }

    public AnimSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPinRadiusPx = 0.0f;
        this.mFadeValue = 0;
        this.mPinRadisMax = 0.0f;
        this.mPinRadisMin = 0.0f;
        this.mIsStartAnim = false;
        this.mIsTapPressed = false;
        this.mIsLongOrScroll = false;
        this.mCheckRadisChanged = false;
        this.mDefaultWidth = 500;
        this.mBounds = new Rect();
        this.mGesture = null;
        seekBarAnimationInit(context, attributeSet, i);
    }

    private void seekBarAnimationInit(Context context, AttributeSet attributeSet, int i) {
        this.mRes = context.getResources();
        this.mGesture = new GestureDetector(context, this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AnimSeekBar, i, 0);
        this.mCircleAnimDrawble = obtainStyledAttributes.getDrawable(R.styleable.AnimSeekBar_mcLargeCircleDrawble);
        this.mCircleRadius = (int) obtainStyledAttributes.getDimension(R.styleable.AnimSeekBar_mcLargeCircleRadis, 15.0f);
        this.mTextNumberColor = obtainStyledAttributes.getColor(R.styleable.AnimSeekBar_mcTextNumberColor, -1);
        this.mDistanceBwCircle = (int) obtainStyledAttributes.getDimension(R.styleable.AnimSeekBar_mcDistanceToCircle, 40.0f);
        this.mTextNumberSize = (int) obtainStyledAttributes.getDimension(R.styleable.AnimSeekBar_mcTextNumberSize, 15.0f);
        this.mMoveUpValueDp = (int) TypedValue.applyDimension(1, 24.0f, this.mRes.getDisplayMetrics());
        this.mDefaultHeight = (int) TypedValue.applyDimension(1, 65.0f, this.mRes.getDisplayMetrics());
        this.mTextPaint = new Paint();
        this.mTextPaint.setColor(this.mTextNumberColor);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setTextSize((float) this.mTextNumberSize);
        obtainStyledAttributes.recycle();
        this.mSdkApi = VERSION.SDK_INT;
        if (this.mSdkApi >= 21) {
            this.mScaleInterpolator = new PathInterpolator(0.0f, 0.0f, 0.1f, 1.0f);
            this.mFadeInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
            this.mMoveUpInterpolator = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
            this.mMoveDownInterpolator = new PathInterpolator(0.66f, 0.0f, 0.67f, 1.0f);
            return;
        }
        this.mScaleInterpolator = new AccelerateInterpolator();
        this.mFadeInterpolator = new AccelerateInterpolator();
        this.mMoveUpInterpolator = new AccelerateInterpolator();
        this.mMoveDownInterpolator = new AccelerateInterpolator();
    }

    protected synchronized void onMeasure(int i, int i2) {
        this.mAinmSeekBarHeight = (int) (((float) ((((this.mCircleRadius + this.mDistanceBwCircle) + this.mMoveUpValueDp) + getPaddingTop()) + getPaddingBottom())) + this.mThumbHeight);
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        if (mode == Integer.MIN_VALUE) {
            mode = size;
        } else if (mode == 1073741824) {
            mode = size;
        } else {
            mode = this.mDefaultWidth;
        }
        if (mode2 == Integer.MIN_VALUE) {
            size = this.mAinmSeekBarHeight;
        } else if (mode2 != 1073741824) {
            size = this.mAinmSeekBarHeight;
        } else if (size2 < this.mDefaultHeight) {
            size = this.mDefaultHeight;
            this.mCircleRadius = (int) TypedValue.applyDimension(1, 15.0f, this.mRes.getDisplayMetrics());
            this.mDistanceBwCircle = (int) TypedValue.applyDimension(1, 10.0f, this.mRes.getDisplayMetrics());
        } else {
            size = this.mAinmSeekBarHeight;
        }
        setMeasuredDimension(mode, size);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mProgressDrawble = getProgressDrawable();
        Drawable thumb = getThumb();
        if (thumb != null && this.mProgressDrawble != null) {
            this.mThumbRadis = thumb.getBounds().width() / 2;
            this.mPinRadisMax = (float) this.mThumbRadis;
            this.mPinRadisMin = (float) (this.mThumbRadis / 2);
            this.mThumbHeight = (float) thumb.getBounds().height();
            this.mPinRadiusPx = (float) this.mThumbRadis;
            setmY((float) this.mProgressDrawble.getBounds().centerY());
            if (this.mSdkApi <= 21) {
                getThumb().setBounds(thumb.getBounds().centerX() - this.mThumbRadis, this.mProgressDrawble.getBounds().centerY() - this.mThumbRadis, thumb.getBounds().centerX() + this.mThumbRadis, this.mProgressDrawble.getBounds().centerY() + this.mThumbRadis);
            }
        }
    }

    protected synchronized void onDraw(Canvas canvas) {
        canvas.translate(0.0f, (((float) (this.mAinmSeekBarHeight / 2)) - this.mThumbHeight) - ((float) getPaddingBottom()));
        canvas.save();
        if (!(getThumb() == null || this.mCircleAnimDrawble == null)) {
            this.mX = (float) ((getThumb().getBounds().centerX() + getPaddingLeft()) - (getThumb().getIntrinsicWidth() / 2));
            float exactCenterY = (float) ((int) getThumb().getBounds().exactCenterY());
            getThumb().setBounds((int) (((float) getThumb().getBounds().centerX()) - this.mPinRadiusPx), (int) (exactCenterY - this.mPinRadiusPx), (int) (((float) getThumb().getBounds().centerX()) + this.mPinRadiusPx), (int) (((float) ((int) getThumb().getBounds().exactCenterY())) + this.mPinRadiusPx));
            this.mBounds.set((int) (this.mX - ((float) this.mCircleRadius)), (int) (((this.mY - ((float) this.mCircleRadius)) - ((float) this.mMoveUpValue)) - ((float) this.mDistanceBwCircle)), (int) (this.mX + ((float) this.mCircleRadius)), (int) (((this.mY + ((float) this.mCircleRadius)) - ((float) this.mMoveUpValue)) - ((float) this.mDistanceBwCircle)));
            this.mCircleAnimDrawble.setBounds(this.mBounds);
            this.mCircleAnimDrawble.setAlpha(this.mFadeValue);
            this.mCircleAnimDrawble.draw(canvas);
            if (this.mFadeValue > 100) {
                this.mTextNumber = Integer.toString(getProgress());
            } else {
                this.mTextNumber = "";
            }
            if (this.mTextNumber.length() > 4) {
                this.mTextNumber = this.mTextNumber.substring(0, 4);
            }
            this.mTextPaint.getTextBounds(this.mTextNumber, 0, this.mTextNumber.length(), this.mBounds);
            this.mTextPaint.setTextAlign(Align.CENTER);
            FontMetricsInt fontMetricsInt = this.mTextPaint.getFontMetricsInt();
            canvas.drawText(this.mTextNumber, this.mX, (float) ((int) ((((this.mY - ((float) this.mMoveUpValue)) - ((float) this.mDistanceBwCircle)) - ((float) ((fontMetricsInt.bottom - fontMetricsInt.top) / 2))) - ((float) fontMetricsInt.top))), this.mTextPaint);
        }
        super.onDraw(canvas);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        if (!isEnabled()) {
            return false;
        }
        this.mGesture.onTouchEvent(motionEvent);
        switch (motionEvent.getAction()) {
            case 1:
            case 3:
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                if (!this.mIsTapPressed) {
                    onActionUp(motionEvent.getX(), motionEvent.getY());
                    break;
                }
                break;
            case 2:
                if (this.mIsLongOrScroll && getThumb() != null) {
                    float centerX = (float) getThumb().getBounds().centerX();
                    float centerY = (float) getThumb().getBounds().centerY();
                    getThumb().setBounds((int) (centerX - ((float) (this.mThumbRadis / 2))), (int) (centerY - ((float) (this.mThumbRadis / 2))), (int) (centerX + ((float) (this.mThumbRadis / 2))), (int) (centerY + ((float) (this.mThumbRadis / 2))));
                }
                invalidate();
                break;
        }
        return true;
    }

    private void onActionUp(float f, float f2) {
        if (this.mIsStartAnim) {
            this.mIsStartAnim = false;
        }
        releasePin();
        this.mIsLongOrScroll = false;
    }

    public boolean onDown(MotionEvent motionEvent) {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return false;
    }

    public void onShowPress(MotionEvent motionEvent) {
        if (!this.mIsStartAnim) {
            pressPin();
            this.mIsStartAnim = true;
        }
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        if (this.mPinRadiusPx != this.mPinRadisMax || this.mIsStartAnim) {
            this.mIsTapPressed = false;
        } else {
            this.mIsTapPressed = true;
        }
        return true;
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        this.mIsTapPressed = false;
        this.mIsLongOrScroll = true;
        if (getThumb() != null) {
            float centerX = (float) getThumb().getBounds().centerX();
            float centerY = (float) getThumb().getBounds().centerY();
            getThumb().setBounds((int) (centerX - ((float) (this.mThumbRadis / 2))), (int) (centerY - ((float) (this.mThumbRadis / 2))), (int) (centerX + ((float) (this.mThumbRadis / 2))), (int) (centerY + ((float) (this.mThumbRadis / 2))));
        }
        if (this.mIsStartAnim) {
            this.mScaleanim.end();
            this.mPinRadiusPx = this.mPinRadisMin;
        } else {
            this.mIsStartAnim = true;
            pressPin();
        }
        invalidate();
        return true;
    }

    public void onLongPress(MotionEvent motionEvent) {
        this.mIsTapPressed = false;
        this.mIsLongOrScroll = true;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (getThumb() != null) {
            releasePin();
            invalidate();
        }
        this.mIsLongOrScroll = false;
        return true;
    }

    private void pressPin() {
        this.mScaleanim = ValueAnimator.ofFloat(new float[]{(float) this.mThumbRadis, (float) (this.mThumbRadis / 2)});
        this.mScaleanim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimSeekBar.this.setSize(((Float) valueAnimator.getAnimatedValue()).floatValue());
                AnimSeekBar.this.invalidate();
            }
        });
        this.mScaleanim.setInterpolator(this.mScaleInterpolator);
        this.mScaleanim.setDuration(166);
        this.mScaleanim.start();
        this.mFadeAnim = ValueAnimator.ofInt(new int[]{0, 255});
        this.mFadeAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimSeekBar.this.setFadeValue(((Integer) valueAnimator.getAnimatedValue()).intValue());
                AnimSeekBar.this.invalidate();
            }
        });
        this.mFadeAnim.setInterpolator(this.mFadeInterpolator);
        this.mFadeAnim.setDuration(166);
        this.mFadeAnim.start();
        this.mMoveAnim = ValueAnimator.ofInt(new int[]{0, this.mMoveUpValueDp});
        this.mMoveAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimSeekBar.this.setMoveValue(((Integer) valueAnimator.getAnimatedValue()).intValue());
                AnimSeekBar.this.invalidate();
            }
        });
        this.mMoveAnim.setDuration(166);
        this.mMoveAnim.setInterpolator(this.mMoveUpInterpolator);
        this.mMoveAnim.start();
    }

    private void releasePin() {
        this.mScaleanim = ValueAnimator.ofFloat(new float[]{(float) (this.mThumbRadis / 2), (float) this.mThumbRadis});
        this.mScaleanim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimSeekBar.this.setSize(((Float) valueAnimator.getAnimatedValue()).floatValue());
                AnimSeekBar.this.invalidate();
            }
        });
        this.mScaleanim.setInterpolator(this.mScaleInterpolator);
        this.mScaleanim.setDuration(166);
        this.mScaleanim.start();
        this.mFadeAnim = ValueAnimator.ofInt(new int[]{255, 0});
        this.mFadeAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimSeekBar.this.setFadeValue(((Integer) valueAnimator.getAnimatedValue()).intValue());
                AnimSeekBar.this.invalidate();
            }
        });
        this.mFadeAnim.setInterpolator(this.mFadeInterpolator);
        this.mFadeAnim.setDuration(166);
        this.mFadeAnim.start();
        this.mMoveAnim = ValueAnimator.ofInt(new int[]{this.mMoveUpValueDp, 0});
        this.mMoveAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimSeekBar.this.setMoveValue(((Integer) valueAnimator.getAnimatedValue()).intValue());
                AnimSeekBar.this.invalidate();
            }
        });
        this.mMoveAnim.setDuration(166);
        this.mMoveAnim.setInterpolator(this.mMoveDownInterpolator);
        this.mMoveAnim.start();
    }

    public void setTextNumberColor(int i) {
        if (this.mTextNumberColor != i) {
            this.mTextNumberColor = i;
            this.mTextPaint.setColor(this.mTextNumberColor);
            postInvalidate();
        }
    }

    public void setTextNumberSize(int i) {
        Context context = getContext();
        if (this.mRes == null) {
            this.mRes = Resources.getSystem();
        } else {
            this.mRes = context.getResources();
        }
        if (i != this.mTextNumberSize) {
            this.mTextNumberSize = (int) TypedValue.applyDimension(2, (float) i, this.mRes.getDisplayMetrics());
            this.mTextPaint.setTextSize((float) this.mTextNumberSize);
            requestLayout();
        }
        invalidate();
    }

    public void setLargeCircleDrawble(Drawable drawable) {
        int i = -1;
        if (this.mCircleAnimDrawble != drawable) {
            int width;
            int width2 = this.mCircleAnimDrawble.getBounds().width();
            int height = this.mCircleAnimDrawble.getBounds().height();
            this.mCircleAnimDrawble = drawable;
            if (drawable != null) {
                width = drawable.getBounds().width();
                i = drawable.getBounds().height();
            } else {
                width = -1;
            }
            if (!(width2 == width && height == r0)) {
                requestLayout();
            }
            invalidate();
        }
    }

    public void setLargeCircleRadis(int i) {
        Context context = getContext();
        if (this.mRes == null) {
            this.mRes = Resources.getSystem();
        } else {
            this.mRes = context.getResources();
        }
        int applyDimension = (int) TypedValue.applyDimension(1, (float) i, this.mRes.getDisplayMetrics());
        if (this.mCircleRadius != i) {
            this.mCircleRadius = applyDimension;
            requestLayout();
        }
        invalidate();
    }

    public void setDistanceToCircle(int i) {
        Context context = getContext();
        if (this.mRes == null) {
            this.mRes = Resources.getSystem();
        } else {
            this.mRes = context.getResources();
        }
        int applyDimension = (int) TypedValue.applyDimension(1, (float) i, this.mRes.getDisplayMetrics());
        if (i != this.mDistanceBwCircle) {
            this.mDistanceBwCircle = applyDimension;
            this.mCheckRadisChanged = true;
            requestLayout();
        }
        invalidate();
    }

    public Drawable getLargeCircleDrawble() {
        if (this.mCircleAnimDrawble != null) {
            return this.mCircleAnimDrawble;
        }
        return null;
    }

    public int getTextNumberColor() {
        return this.mTextNumberColor;
    }

    public int getTextNumberSize() {
        return this.mTextNumberSize;
    }

    public int getDistanceToCircle() {
        return this.mDistanceBwCircle;
    }

    public int getLargeCircleRadius() {
        return this.mCircleRadius;
    }

    private void setmY(float f) {
        this.mY = f;
    }

    private void setFadeValue(int i) {
        this.mFadeValue = i;
    }

    private void setMoveValue(int i) {
        this.mMoveUpValue = i;
    }

    private void setSize(float f) {
        this.mPinRadiusPx = (float) ((int) f);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(AnimSeekBar.class.getName());
    }
}
