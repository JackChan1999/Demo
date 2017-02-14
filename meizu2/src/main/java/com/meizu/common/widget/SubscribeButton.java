package com.meizu.common.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import com.meizu.common.R;

public class SubscribeButton extends Button {
    private static final int DEFAULT_TEXT_SIZE = 15;
    private static float MAX_ALPHASIGN = 1.0f;
    private static float MIN_ALPHASIGN = 0.0f;
    private static final String Tag = "SubButton";
    private ValueAnimator mAlphaAnim;
    private float mAlphaSign;
    private int mAnimDuration;
    private Rect mBackgroundBound;
    private int mBaseline;
    private String mBtnBeAddedText;
    private int mBtnBeAddedTextColor;
    private String mBtnNormalText;
    private int mBtnNormalTextColor;
    private float mBtnSubTextSize;
    private Interpolator mInterpolator;
    boolean mIsSelected;
    private boolean mManuaStartAnim;
    private Drawable mResBeAddedDrawble;
    private Drawable mResNormalDrawble;
    private Paint mTextPaintA;
    private Paint mTextPaintB;

    public SubscribeButton(Context context) {
        this(context, null);
    }

    public SubscribeButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_SubscribeButtonStyle);
    }

    public SubscribeButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsSelected = false;
        this.mAlphaSign = 0.0f;
        this.mAlphaAnim = null;
        this.mBtnSubTextSize = 15.0f;
        this.mManuaStartAnim = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SubscribeButton, i, 0);
        this.mResNormalDrawble = obtainStyledAttributes.getDrawable(R.styleable.SubscribeButton_mcBtnNormalBg);
        this.mResBeAddedDrawble = obtainStyledAttributes.getDrawable(R.styleable.SubscribeButton_mcBtnBeAddedBg);
        this.mBtnSubTextSize = obtainStyledAttributes.getDimension(R.styleable.SubscribeButton_mcBtnSubTextSize, 15.0f);
        this.mBtnBeAddedText = obtainStyledAttributes.getString(R.styleable.SubscribeButton_mcBtnBeAddedText);
        this.mBtnNormalText = obtainStyledAttributes.getString(R.styleable.SubscribeButton_mcBtnNormalText);
        this.mBtnBeAddedTextColor = obtainStyledAttributes.getColor(R.styleable.SubscribeButton_mcBtnBeAddedTextColor, ViewCompat.MEASURED_STATE_MASK);
        this.mBtnNormalTextColor = obtainStyledAttributes.getColor(R.styleable.SubscribeButton_mcBtnNormalTextColor, -1);
        this.mAnimDuration = obtainStyledAttributes.getInteger(R.styleable.SubscribeButton_mcBtnAnimDuration, 80);
        if (this.mResNormalDrawble == null) {
            this.mResNormalDrawble = getResources().getDrawable(R.drawable.mc_btn_list_default_alpha_normal);
        }
        if (this.mResBeAddedDrawble == null) {
            this.mResBeAddedDrawble = getResources().getDrawable(R.drawable.mc_btn_list_default_pressed);
        }
        obtainStyledAttributes.recycle();
        init();
    }

    @TargetApi(21)
    private void init() {
        Paint paint = new Paint();
        paint.setTextAlign(Align.CENTER);
        paint.setAntiAlias(true);
        paint.setTextSize(this.mBtnSubTextSize);
        this.mTextPaintA = new Paint(paint);
        this.mTextPaintA.setColor(this.mBtnNormalTextColor);
        this.mTextPaintB = new Paint(paint);
        this.mTextPaintB.setColor(this.mBtnBeAddedTextColor);
        if (VERSION.SDK_INT >= 21) {
            this.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        } else {
            this.mInterpolator = new AccelerateInterpolator();
        }
    }

    private void setAlphaSign(float f) {
        this.mAlphaSign = f;
    }

    private float getAlphaSign() {
        return this.mAlphaSign;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && this.mAlphaAnim != null) {
            this.mAlphaAnim.end();
        }
        return super.onTouchEvent(motionEvent);
    }

    protected void onMeasure(int i, int i2) {
        FontMetrics fontMetrics = this.mTextPaintA.getFontMetrics();
        int paddingBottom = (int) (((fontMetrics.bottom - fontMetrics.top) + ((float) getPaddingBottom())) + ((float) getPaddingTop()));
        int max = (int) (Math.max(this.mTextPaintA.measureText(this.mBtnNormalText), this.mTextPaintB.measureText(this.mBtnBeAddedText)) + 20.0f);
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        if (mode != Integer.MIN_VALUE) {
            if (mode == 1073741824) {
                max = size;
            } else {
                max = size;
            }
        }
        if (mode2 != Integer.MIN_VALUE) {
            if (mode2 == 1073741824) {
                paddingBottom = size2;
            } else if (mode == 0) {
                paddingBottom = size2;
            } else {
                paddingBottom = 0;
            }
        }
        setMeasuredDimension(max, paddingBottom);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mBackgroundBound = new Rect(0, 0, i, i2);
        FontMetricsInt fontMetricsInt = this.mTextPaintA.getFontMetricsInt();
        this.mBaseline = (this.mBackgroundBound.centerY() - ((fontMetricsInt.bottom - fontMetricsInt.top) / 2)) - fontMetricsInt.top;
        this.mResBeAddedDrawble.setBounds(this.mBackgroundBound);
        this.mResNormalDrawble.setBounds(this.mBackgroundBound);
    }

    protected void onDraw(Canvas canvas) {
        float f = MAX_ALPHASIGN - this.mAlphaSign;
        this.mTextPaintA.setAlpha((int) (f * 255.0f));
        this.mTextPaintB.setAlpha((int) (this.mAlphaSign * 77.0f));
        if (this.mIsSelected) {
            this.mResBeAddedDrawble.setAlpha((int) (this.mAlphaSign * 255.0f));
            this.mResBeAddedDrawble.draw(canvas);
            this.mResNormalDrawble.setAlpha((int) (f * 255.0f));
            this.mResNormalDrawble.draw(canvas);
        } else {
            this.mResNormalDrawble.setAlpha((int) (f * 255.0f));
            this.mResNormalDrawble.draw(canvas);
            this.mResBeAddedDrawble.setAlpha((int) (this.mAlphaSign * 255.0f));
            this.mResBeAddedDrawble.draw(canvas);
        }
        canvas.drawText(this.mBtnNormalText, (float) this.mBackgroundBound.centerX(), (float) this.mBaseline, this.mTextPaintA);
        canvas.drawText(this.mBtnBeAddedText, (float) this.mBackgroundBound.centerX(), (float) this.mBaseline, this.mTextPaintB);
        super.onDraw(canvas);
    }

    public boolean performClick() {
        if (!this.mManuaStartAnim) {
            onStartAnimation();
        }
        return super.performClick();
    }

    private void startAnimator(float f, float f2, int i) {
        this.mAlphaAnim = ValueAnimator.ofFloat(new float[]{f, f2});
        this.mAlphaAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                SubscribeButton.this.setAlphaSign(((Float) valueAnimator.getAnimatedValue()).floatValue());
                SubscribeButton.this.invalidate();
            }
        });
        this.mAlphaAnim.setDuration((long) i);
        this.mAlphaAnim.setInterpolator(this.mInterpolator);
        this.mAlphaAnim.start();
    }

    public void onStartAnimation() {
        this.mIsSelected = !this.mIsSelected;
        if (this.mAlphaSign > 0.0f) {
            startAnimator(this.mAlphaSign, MIN_ALPHASIGN, this.mAnimDuration);
        } else {
            startAnimator(this.mAlphaSign, MAX_ALPHASIGN, this.mAnimDuration);
        }
    }

    public void setBtnBeAddedText(String str) {
        String str2 = this.mBtnBeAddedText;
        this.mBtnBeAddedText = str;
        if (this.mTextPaintB.measureText(str2) != this.mTextPaintB.measureText(this.mBtnBeAddedText)) {
            requestLayout();
        }
        invalidate();
    }

    public String getBtnBeAddedText() {
        return this.mBtnBeAddedText;
    }

    public void setBtnNormalText(String str) {
        String str2 = this.mBtnNormalText;
        this.mBtnNormalText = str;
        if (this.mTextPaintB.measureText(str2) != this.mTextPaintB.measureText(this.mBtnNormalText)) {
            requestLayout();
        }
        invalidate();
    }

    public String getBtnNormalText() {
        return this.mBtnNormalText;
    }

    public void setBtnBeAddedTextColor(int i) {
        this.mTextPaintB.setColor(i);
        invalidate();
    }

    public int getBtnBeAddedTextColor() {
        return this.mTextPaintB.getColor();
    }

    public void setBtnNormalTextColor(int i) {
        this.mTextPaintA.setColor(i);
        invalidate();
    }

    public int getBtnNormalTextColor() {
        return this.mTextPaintA.getColor();
    }

    public void setBtnSubTextSize(int i) {
        this.mTextPaintA.setTextSize((float) i);
        this.mTextPaintB.setTextSize((float) i);
        if (this.mBackgroundBound != null) {
            FontMetricsInt fontMetricsInt = this.mTextPaintA.getFontMetricsInt();
            this.mBaseline = (this.mBackgroundBound.centerY() - ((fontMetricsInt.bottom - fontMetricsInt.top) / 2)) - fontMetricsInt.top;
        }
        invalidate();
    }

    public void setAnimDuration(int i) {
        this.mAnimDuration = i;
    }

    public void setSelectedable(boolean z) {
        if (this.mIsSelected != z) {
            this.mIsSelected = z;
            if (this.mIsSelected) {
                setAlphaSign(1.0f);
                startAnimator(MIN_ALPHASIGN, this.mAlphaSign, 0);
                return;
            }
            setAlphaSign(0.0f);
            startAnimator(MAX_ALPHASIGN, this.mAlphaSign, 0);
        }
    }

    public boolean getSelectedState() {
        return this.mIsSelected;
    }

    public boolean isManuaStartAnim() {
        return this.mManuaStartAnim;
    }

    public void setManuaStartAnim(boolean z) {
        this.mManuaStartAnim = z;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(SubscribeButton.class.getName());
    }
}
