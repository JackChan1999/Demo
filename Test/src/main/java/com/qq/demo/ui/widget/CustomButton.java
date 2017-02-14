package com.qq.demo.ui.widget;

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
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.Button;

import com.qq.demo.R;

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

    public CustomButton(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
      mIsPressing = true;
      mIsFirst = true;
      mAlphaSign = 0.0f;
      mAlphaAnim = null;
      mBtnTextSize = 15.0f;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet,
                R.styleable.CustomButton, defStyleAttr, 0);
       mResDefaultDrawble = obtainStyledAttributes.getDrawable(R.styleable.CustomButton_mcBtnFocus);
       mResPressedDrawble = obtainStyledAttributes.getDrawable(R.styleable.CustomButton_mcBtnNormalPress);
       mBtnTextSize = obtainStyledAttributes.getDimension(R.styleable.CustomButton_mcBtnTextSize, 15.0f);
       mBtnAddedText = obtainStyledAttributes.getString(R.styleable.CustomButton_mcBtnPressedText);
       mBtnAddText = obtainStyledAttributes.getString(R.styleable.CustomButton_mcBtnDefaultText);
       mBtnAddedTextColor = obtainStyledAttributes.getColor(R.styleable.CustomButton_mcBtnPressedTextColor,
               ViewCompat.MEASURED_STATE_MASK);
       mBtnAddTextColor = obtainStyledAttributes.getColor(R.styleable.CustomButton_mcBtnDefaultTextColor, -1);
        if (mResDefaultDrawble == null) {
            mResDefaultDrawble = getResources().getDrawable(R.drawable.mc_btn_list_default_alpha_normal);
        }
        if (mResPressedDrawble == null) {
            mResPressedDrawble = getResources().getDrawable(R.drawable.mc_btn_list_default_pressed);
        }
        obtainStyledAttributes.recycle();
        init();
    }

    private void init() {
        Paint paint = new Paint();
        paint.setTextAlign(Align.CENTER);
        paint.setAntiAlias(true);
        paint.setTextSize(mBtnTextSize);
        mTextPaintA = new Paint(paint);
        mTextPaintA.setColor(mBtnAddTextColor);
        mTextPaintB = new Paint(paint);
        mTextPaintB.setColor(mBtnAddedTextColor);
        if (VERSION.SDK_INT >= 21) {
            mAddInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        } else {
            mAddInterpolator = new AccelerateInterpolator();
        }
    }

    private void setAlphaSign(float f) {
        mAlphaSign = f;
    }

    private float getAlphaSign() {
        return mAlphaSign;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && mAlphaAnim != null) {
            mAlphaAnim.end();
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override
    protected void onMeasure(int i, int i2) {
        FontMetrics fontMetrics = mTextPaintA.getFontMetrics();
        int paddingBottom = (int) (((fontMetrics.bottom - fontMetrics.top)
                + ((float) getPaddingBottom())) + ((float) getPaddingTop()));
        int max = (int) (Math.max(mTextPaintA.measureText(mBtnAddText),
                mTextPaintB.measureText(mBtnAddedText)) + 20.0f);
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

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsFirst) {
            if (mBackgroundBound == null) {
                mBackgroundBound = new Rect(0, 0, getWidth(), getHeight());
            }
            FontMetricsInt fontMetricsInt = mTextPaintA.getFontMetricsInt();
            baseline = (mBackgroundBound.centerY() - ((fontMetricsInt.bottom - fontMetricsInt.top) / 2))
                    - fontMetricsInt.top;
            mResPressedDrawble.setBounds(mBackgroundBound);
            mResPressedDrawble.setAlpha((int) ((MAX_ALPHASIGN - mAlphaSign) * 255.0f));
            mResPressedDrawble.draw(canvas);
            mResDefaultDrawble.setAlpha((int) (mAlphaSign * 255.0f));
            mResDefaultDrawble.setBounds(mBackgroundBound);
            mResDefaultDrawble.draw(canvas);
            canvas.drawText(mBtnAddText, (float) mBackgroundBound.centerX(), (float) baseline,
                    mTextPaintA);
            mIsFirst = false;
        } else if (mIsPressing) {
            mResPressedDrawble.setBounds(mBackgroundBound);
            mResPressedDrawble.setAlpha((int) (mAlphaSign * 255.0f));
            mResPressedDrawble.draw(canvas);
            mResDefaultDrawble.setAlpha((int) ((MAX_ALPHASIGN - mAlphaSign) * 255.0f));
            mResDefaultDrawble.setBounds(mBackgroundBound);
            mResDefaultDrawble.draw(canvas);
            mTextPaintA.setAlpha((int) ((MAX_ALPHASIGN - mAlphaSign) * 255.0f));
            mTextPaintB.setAlpha((int) (mAlphaSign * 77.0f));
            canvas.drawText(mBtnAddText, (float) mBackgroundBound.centerX(), (float) baseline,
                    mTextPaintA);
            canvas.drawText(mBtnAddedText, (float) mBackgroundBound.centerX(), (float) baseline,
                    mTextPaintB);
        } else {
            mResDefaultDrawble.setAlpha((int) ((MAX_ALPHASIGN - mAlphaSign) * 255.0f));
            mResDefaultDrawble.setBounds(mBackgroundBound);
            mResDefaultDrawble.draw(canvas);
            mResPressedDrawble.setBounds(mBackgroundBound);
            mResPressedDrawble.setAlpha((int) (mAlphaSign * 255.0f));
            mResPressedDrawble.draw(canvas);
            mTextPaintA.setAlpha((int) ((MAX_ALPHASIGN - mAlphaSign) * 255.0f));
            mTextPaintB.setAlpha((int) (mAlphaSign * 77.0f));
            canvas.drawText(mBtnAddedText, (float) mBackgroundBound.centerX(), (float) baseline,
                    mTextPaintB);
            canvas.drawText(mBtnAddText, (float) mBackgroundBound.centerX(), (float) baseline,
                    mTextPaintA);
        }
        super.onDraw(canvas);
        canvas.restore();
    }

    public boolean performClick() {
        processButtonClick();
        return super.performClick();
    }

    private void startAnimator(float f, float f2, int i) {
        mAlphaAnim = ValueAnimator.ofFloat(new float[]{f, f2});
        mAlphaAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                CustomButton.this.setAlphaSign(((Float) valueAnimator.getAnimatedValue()).floatValue());
                CustomButton.this.invalidate();
            }
        });
        mAlphaAnim.setDuration((long) i);
        mAlphaAnim.setInterpolator(mAddInterpolator);
        mAlphaAnim.start();
    }

    private void processButtonClick() {
        if (mIsPressing) {
            mAlphaSign = 0.0f;
        } else {
            mAlphaSign = 1.0f;
        }
        mIsPressing = !mIsPressing;
        if (mAlphaSign > 0.0f) {
            startAnimator(mAlphaSign, MIN_ALPHASIGN, 80);
        } else {
            startAnimator(mAlphaSign, MAX_ALPHASIGN, 80);
        }
    }

    public void setBtnPressedText(String btnPressedText) {
        String temptext = mBtnAddedText;
        mBtnAddedText = btnPressedText;
        if (mTextPaintB.measureText(temptext) != mTextPaintB.measureText(mBtnAddedText)) {
            requestLayout();
        }
        invalidate();
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(CustomButton.class.getName());
    }

    public String getBtnPressedText() {
        return mBtnAddedText;
    }

    public void setBtnDefaultText(String btnDefaultText) {
        String temptext = mBtnAddText;
        mBtnAddText = btnDefaultText;
        if (mTextPaintB.measureText(temptext) != mTextPaintB.measureText(mBtnAddText)) {
            requestLayout();
        }
        invalidate();
    }

    public String getBtnDefaultText() {
        return mBtnAddText;
    }

    public void setmBtnAddedTextColor(int mBtnAddedTextColor) {
        mTextPaintB.setColor(mBtnAddedTextColor);
        invalidate();
    }

    public void setmBtnAddTextColor(int mBtnAddTextColor) {
        mTextPaintA.setColor(mBtnAddTextColor);
        invalidate();
    }

    public void setmBtnTextSize(int btnTextSize) {
        mTextPaintA.setTextSize((float) btnTextSize);
        mTextPaintB.setTextSize((float) btnTextSize);
        mIsFirst = true;
        requestLayout();
        invalidate();
    }
}
