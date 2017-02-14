package com.meizu.common.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
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

public class CustomButton extends Button {
    private static final int DEFAULT_TEXT_SIZE = 15;
    private static float MAX_ALPHASIGN = 1.0f;
    private static float MIN_ALPHASIGN = 0.0f;
    private int baseline;
    private Interpolator mAddInterpolator;
    private ValueAnimator mAlphaAnim;
    private float mAlphaSign;
    private Rect mBackgroundBound;
    private String mBtnAddText;
    private int mBtnAddTextColor;
    private String mBtnAddedText;
    private int mBtnAddedTextColor;
    private float mBtnTextSize;
    boolean mIsFirst;
    boolean mIsPressing;
    private Drawable mResDefaultDrawble;
    private Drawable mResPressedDrawble;
    private Paint mTextPaintA;
    private Paint mTextPaintB;

    public CustomButton(Context context) {
        this(context, null);
    }

    public CustomButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_CustomButtonStyle);
    }

    public CustomButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsPressing = true;
        this.mIsFirst = true;
        this.mAlphaSign = 0.0f;
        this.mAlphaAnim = null;
        this.mBtnTextSize = 15.0f;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CustomButton, i, 0);
        this.mResDefaultDrawble = obtainStyledAttributes.getDrawable(R.styleable.CustomButton_mcBtnFocus);
        this.mResPressedDrawble = obtainStyledAttributes.getDrawable(R.styleable.CustomButton_mcBtnNormalPress);
        this.mBtnTextSize = obtainStyledAttributes.getDimension(R.styleable.CustomButton_mcBtnTextSize, 15.0f);
        this.mBtnAddedText = obtainStyledAttributes.getString(R.styleable.CustomButton_mcBtnPressedText);
        this.mBtnAddText = obtainStyledAttributes.getString(R.styleable.CustomButton_mcBtnDefaultText);
        this.mBtnAddedTextColor = obtainStyledAttributes.getColor(R.styleable.CustomButton_mcBtnPressedTextColor, ViewCompat.MEASURED_STATE_MASK);
        this.mBtnAddTextColor = obtainStyledAttributes.getColor(R.styleable.CustomButton_mcBtnDefaultTextColor, -1);
        if (this.mResDefaultDrawble == null) {
            this.mResDefaultDrawble = getResources().getDrawable(R.drawable.mc_btn_list_default_alpha_normal);
        }
        if (this.mResPressedDrawble == null) {
            this.mResPressedDrawble = getResources().getDrawable(R.drawable.mc_btn_list_default_pressed);
        }
        obtainStyledAttributes.recycle();
        init();
    }

    private void init() {
        Paint paint = new Paint();
        paint.setTextAlign(Align.CENTER);
        paint.setAntiAlias(true);
        paint.setTextSize(this.mBtnTextSize);
        this.mTextPaintA = new Paint(paint);
        this.mTextPaintA.setColor(this.mBtnAddTextColor);
        this.mTextPaintB = new Paint(paint);
        this.mTextPaintB.setColor(this.mBtnAddedTextColor);
        if (VERSION.SDK_INT >= 21) {
            this.mAddInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        } else {
            this.mAddInterpolator = new AccelerateInterpolator();
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
        int max = (int) (Math.max(this.mTextPaintA.measureText(this.mBtnAddText), this.mTextPaintB.measureText(this.mBtnAddedText)) + 20.0f);
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

    protected void onDraw(Canvas canvas) {
        if (this.mIsFirst) {
            if (this.mBackgroundBound == null) {
                this.mBackgroundBound = new Rect(0, 0, getWidth(), getHeight());
            }
            FontMetricsInt fontMetricsInt = this.mTextPaintA.getFontMetricsInt();
            this.baseline = (this.mBackgroundBound.centerY() - ((fontMetricsInt.bottom - fontMetricsInt.top) / 2)) - fontMetricsInt.top;
            this.mResPressedDrawble.setBounds(this.mBackgroundBound);
            this.mResPressedDrawble.setAlpha((int) ((MAX_ALPHASIGN - this.mAlphaSign) * 255.0f));
            this.mResPressedDrawble.draw(canvas);
            this.mResDefaultDrawble.setAlpha((int) (this.mAlphaSign * 255.0f));
            this.mResDefaultDrawble.setBounds(this.mBackgroundBound);
            this.mResDefaultDrawble.draw(canvas);
            canvas.drawText(this.mBtnAddText, (float) this.mBackgroundBound.centerX(), (float) this.baseline, this.mTextPaintA);
            this.mIsFirst = false;
        } else if (this.mIsPressing) {
            this.mResPressedDrawble.setBounds(this.mBackgroundBound);
            this.mResPressedDrawble.setAlpha((int) (this.mAlphaSign * 255.0f));
            this.mResPressedDrawble.draw(canvas);
            this.mResDefaultDrawble.setAlpha((int) ((MAX_ALPHASIGN - this.mAlphaSign) * 255.0f));
            this.mResDefaultDrawble.setBounds(this.mBackgroundBound);
            this.mResDefaultDrawble.draw(canvas);
            this.mTextPaintA.setAlpha((int) ((MAX_ALPHASIGN - this.mAlphaSign) * 255.0f));
            this.mTextPaintB.setAlpha((int) (this.mAlphaSign * 77.0f));
            canvas.drawText(this.mBtnAddText, (float) this.mBackgroundBound.centerX(), (float) this.baseline, this.mTextPaintA);
            canvas.drawText(this.mBtnAddedText, (float) this.mBackgroundBound.centerX(), (float) this.baseline, this.mTextPaintB);
        } else {
            this.mResDefaultDrawble.setAlpha((int) ((MAX_ALPHASIGN - this.mAlphaSign) * 255.0f));
            this.mResDefaultDrawble.setBounds(this.mBackgroundBound);
            this.mResDefaultDrawble.draw(canvas);
            this.mResPressedDrawble.setBounds(this.mBackgroundBound);
            this.mResPressedDrawble.setAlpha((int) (this.mAlphaSign * 255.0f));
            this.mResPressedDrawble.draw(canvas);
            this.mTextPaintA.setAlpha((int) ((MAX_ALPHASIGN - this.mAlphaSign) * 255.0f));
            this.mTextPaintB.setAlpha((int) (this.mAlphaSign * 77.0f));
            canvas.drawText(this.mBtnAddedText, (float) this.mBackgroundBound.centerX(), (float) this.baseline, this.mTextPaintB);
            canvas.drawText(this.mBtnAddText, (float) this.mBackgroundBound.centerX(), (float) this.baseline, this.mTextPaintA);
        }
        super.onDraw(canvas);
        canvas.restore();
    }

    public boolean performClick() {
        processButtonClick();
        return super.performClick();
    }

    private void startAnimator(float f, float f2, int i) {
        this.mAlphaAnim = ValueAnimator.ofFloat(new float[]{f, f2});
        this.mAlphaAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                CustomButton.this.setAlphaSign(((Float) valueAnimator.getAnimatedValue()).floatValue());
                CustomButton.this.invalidate();
            }
        });
        this.mAlphaAnim.setDuration((long) i);
        this.mAlphaAnim.setInterpolator(this.mAddInterpolator);
        this.mAlphaAnim.start();
    }

    private void processButtonClick() {
        if (this.mIsPressing) {
            this.mAlphaSign = 0.0f;
        } else {
            this.mAlphaSign = 1.0f;
        }
        this.mIsPressing = !this.mIsPressing;
        if (this.mAlphaSign > 0.0f) {
            startAnimator(this.mAlphaSign, MIN_ALPHASIGN, 80);
        } else {
            startAnimator(this.mAlphaSign, MAX_ALPHASIGN, 80);
        }
    }

    public void setBtnPressedText(String str) {
        String str2 = this.mBtnAddedText;
        this.mBtnAddedText = str;
        if (this.mTextPaintB.measureText(str2) != this.mTextPaintB.measureText(this.mBtnAddedText)) {
            requestLayout();
        }
        invalidate();
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(CustomButton.class.getName());
    }

    public String getBtnPressedText() {
        return this.mBtnAddedText;
    }

    public void setBtnDefaultText(String str) {
        String str2 = this.mBtnAddText;
        this.mBtnAddText = str;
        if (this.mTextPaintB.measureText(str2) != this.mTextPaintB.measureText(this.mBtnAddText)) {
            requestLayout();
        }
        invalidate();
    }

    public String getBtnDefaultText() {
        return this.mBtnAddText;
    }

    public void setmBtnAddedTextColor(int i) {
        this.mTextPaintB.setColor(i);
        invalidate();
    }

    public void setmBtnAddTextColor(int i) {
        this.mTextPaintA.setColor(i);
        invalidate();
    }

    public void setmBtnTextSize(int i) {
        this.mTextPaintA.setTextSize((float) i);
        this.mTextPaintB.setTextSize((float) i);
        this.mIsFirst = true;
        requestLayout();
        invalidate();
    }
}
