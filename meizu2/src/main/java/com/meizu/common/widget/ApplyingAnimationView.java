package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.PathInterpolator;
import com.meizu.common.R;
import com.meizu.common.renderer.effect.parameters.BlurParameters;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;

public class ApplyingAnimationView extends View {
    private final String[] alphaProperty;
    private AnimatorSet animator;
    private int bAlpha;
    private boolean bDraw;
    private float baseX;
    private float baseY;
    private float cbPosition;
    private float cbRadius;
    private float cgPosition;
    private float cgRadius;
    private float coPosition;
    private float coRadius;
    private float crPosition;
    private float crRadius;
    private int gAlpha;
    private boolean gDraw;
    private float halfMaxRadius;
    private float mAnimScale;
    private Paint mBluePaint;
    private Paint mGreenPaint;
    private boolean mIsAnimRun;
    private Paint mOrangePaint;
    private Paint mRedPaint;
    private boolean mStopFromUser;
    private float maxRadius;
    private int oAlpha;
    private boolean oDraw;
    private float po1;
    private float po2;
    private float po3;
    private float po4;
    private final String[] positionProperty;
    private int rAlpha;
    private boolean rDraw;
    private final String[] radiusProperty;

    public ApplyingAnimationView(Context context) {
        this(context, null, 0);
    }

    public ApplyingAnimationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ApplyingAnimationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.rAlpha = 255;
        this.bAlpha = 255;
        this.gAlpha = 255;
        this.oAlpha = 255;
        this.positionProperty = new String[]{"crPosition", "cbPosition", "cgPosition", "coPosition"};
        this.alphaProperty = new String[]{"rAlpha", "bAlpha", "gAlpha", "oAlpha"};
        this.radiusProperty = new String[]{"crRadius", "cbRadius", "cgRadius", "coRadius"};
        this.rDraw = false;
        this.bDraw = false;
        this.gDraw = false;
        this.oDraw = false;
        this.mStopFromUser = false;
        this.mIsAnimRun = false;
        this.mAnimScale = 1.0f;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ApplyingAnimationView);
        this.mAnimScale = obtainStyledAttributes.getFloat(R.styleable.ApplyingAnimationView_mcApplyingAnimationScale, this.mAnimScale);
        obtainStyledAttributes.recycle();
        init(context);
    }

    private void init(Context context) {
        initData(context);
        this.mRedPaint = createCommonPaint();
        this.mRedPaint.setColor(-1357238);
        this.mBluePaint = createCommonPaint();
        this.mBluePaint.setColor(-16737828);
        this.mGreenPaint = createCommonPaint();
        this.mGreenPaint.setColor(110475);
        this.mOrangePaint = createCommonPaint();
        this.mOrangePaint.setColor(-620493);
    }

    private void initData(Context context) {
        float density = getDensity(context) * this.mAnimScale;
        this.maxRadius = 6.0f * density;
        this.halfMaxRadius = this.maxRadius * FastBlurParameters.DEFAULT_LEVEL;
        this.po1 = 0.0f;
        this.po2 = 12.3f * density;
        this.po3 = 24.0f * density;
        this.po4 = density * 11.0f;
        this.baseX = (getX() + this.halfMaxRadius) + (this.mAnimScale * CircleProgressBar.BAR_WIDTH_DEF_DIP);
        this.baseY = getY();
    }

    protected void onDraw(Canvas canvas) {
        if (this.crRadius < this.halfMaxRadius) {
            canvas.drawCircle(this.baseX + this.crPosition, this.baseY + this.maxRadius, this.crRadius, this.mRedPaint);
            this.rDraw = true;
        }
        if (this.cbRadius < this.halfMaxRadius) {
            canvas.drawCircle(this.baseX + this.cbPosition, this.baseY + this.maxRadius, this.cbRadius, this.mBluePaint);
            this.bDraw = true;
        }
        if (this.cgRadius < this.halfMaxRadius) {
            canvas.drawCircle(this.baseX + this.cgPosition, this.baseY + this.maxRadius, this.cgRadius, this.mGreenPaint);
            this.gDraw = true;
        }
        if (this.coRadius < this.halfMaxRadius) {
            canvas.drawCircle(this.baseX + this.coPosition, this.baseY + this.maxRadius, this.coRadius, this.mOrangePaint);
            this.oDraw = true;
        }
        if (!this.rDraw) {
            canvas.drawCircle(this.baseX + this.crPosition, this.baseY + this.maxRadius, this.crRadius, this.mRedPaint);
        }
        if (!this.bDraw) {
            canvas.drawCircle(this.baseX + this.cbPosition, this.baseY + this.maxRadius, this.cbRadius, this.mBluePaint);
        }
        if (!this.gDraw) {
            canvas.drawCircle(this.baseX + this.cgPosition, this.baseY + this.maxRadius, this.cgRadius, this.mGreenPaint);
        }
        if (!this.oDraw) {
            canvas.drawCircle(this.baseX + this.coPosition, this.baseY + this.maxRadius, this.coRadius, this.mOrangePaint);
        }
        this.rDraw = false;
        this.bDraw = false;
        this.gDraw = false;
        this.oDraw = false;
    }

    private Paint createCommonPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        return paint;
    }

    private void startAnimator() {
        if (!this.mIsAnimRun) {
            Animator createPositionAnimator = createPositionAnimator(0);
            Animator createPositionAnimator2 = createPositionAnimator(1);
            Animator createPositionAnimator3 = createPositionAnimator(2);
            Animator createPositionAnimator4 = createPositionAnimator(3);
            new AnimatorSet().playTogether(new Animator[]{createPositionAnimator, createPositionAnimator2, createPositionAnimator3, createPositionAnimator4});
            createPositionAnimator = createRadiusAnimator(0);
            createPositionAnimator2 = createRadiusAnimator(1);
            createPositionAnimator3 = createRadiusAnimator(2);
            createPositionAnimator4 = createRadiusAnimator(3);
            new AnimatorSet().playTogether(new Animator[]{createPositionAnimator, createPositionAnimator2, createPositionAnimator3, createPositionAnimator4});
            createPositionAnimator = createAlphaAnimator(0);
            createPositionAnimator2 = createAlphaAnimator(1);
            createPositionAnimator3 = createAlphaAnimator(2);
            createPositionAnimator4 = createAlphaAnimator(3);
            new AnimatorSet().playTogether(new Animator[]{createPositionAnimator, createPositionAnimator2, createPositionAnimator3, createPositionAnimator4});
            this.animator = new AnimatorSet();
            this.animator.playTogether(new Animator[]{r4, r5, r6});
            this.animator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    if (!ApplyingAnimationView.this.mStopFromUser && ApplyingAnimationView.this.animator != null) {
                        ApplyingAnimationView.this.animator.start();
                    } else if (ApplyingAnimationView.this.animator != null) {
                        ApplyingAnimationView.this.mStopFromUser = false;
                        ApplyingAnimationView.this.mIsAnimRun = false;
                    }
                }
            });
            this.mIsAnimRun = true;
            this.animator.start();
        }
    }

    private Animator createPositionAnimator(int i) {
        r1 = new ObjectAnimator[4];
        r1[0] = ObjectAnimator.ofFloat(this, this.positionProperty[i], new float[]{0.0f, this.po2});
        r1[0].setDuration(704);
        r1[0].setInterpolator(new PathInterpolator(0.21f, 0.0f, BlurParameters.DEFAULT_PROGRESS_BLUR_SCALE, 0.471f));
        r1[1] = ObjectAnimator.ofFloat(this, this.positionProperty[i], new float[]{this.po2, this.po3});
        r1[1].setDuration(704);
        r1[1].setInterpolator(new PathInterpolator(0.24f, 0.341f, 0.41f, 1.0f));
        r1[2] = ObjectAnimator.ofFloat(this, this.positionProperty[i], new float[]{this.po3, this.po4});
        r1[2].setDuration(672);
        r1[2].setInterpolator(new PathInterpolator(0.26f, 0.0f, 0.87f, 0.758f));
        r1[3] = ObjectAnimator.ofFloat(this, this.positionProperty[i], new float[]{this.po4, this.po1});
        r1[3].setDuration(736);
        r1[3].setInterpolator(new PathInterpolator(0.18f, 0.434f, 0.59f, 1.0f));
        Animator animatorSet = new AnimatorSet();
        animatorSet.playSequentially(new Animator[]{r1[i % 4], r1[(i + 1) % 4], r1[(i + 2) % 4], r1[(i + 3) % 4]});
        return animatorSet;
    }

    private Animator createRadiusAnimator(int i) {
        r1 = new ObjectAnimator[4];
        r1[0] = ObjectAnimator.ofFloat(this, this.radiusProperty[i], new float[]{this.halfMaxRadius, this.maxRadius});
        r1[0].setInterpolator(new PathInterpolator(0.24f, 0.209f, 0.25f, 1.0f));
        r1[0].setDuration(720);
        r1[1] = ObjectAnimator.ofFloat(this, this.radiusProperty[i], new float[]{this.maxRadius, this.halfMaxRadius});
        r1[1].setInterpolator(new PathInterpolator(0.29f, 0.0f, 0.32f, 0.631f));
        r1[1].setDuration(704);
        r1[2] = ObjectAnimator.ofFloat(this, this.radiusProperty[i], new float[]{this.halfMaxRadius, this.maxRadius * 0.25f});
        r1[2].setInterpolator(new PathInterpolator(0.2f, 0.337f, 0.17f, 1.0f));
        r1[2].setDuration(704);
        r1[3] = ObjectAnimator.ofFloat(this, this.radiusProperty[i], new float[]{this.maxRadius * 0.25f, this.halfMaxRadius});
        r1[3].setInterpolator(new PathInterpolator(0.19f, 0.0f, 0.37f, 0.31f));
        r1[3].setDuration(688);
        Animator animatorSet = new AnimatorSet();
        animatorSet.playSequentially(new Animator[]{r1[i % 4], r1[(i + 1) % 4], r1[(i + 2) % 4], r1[(i + 3) % 4]});
        return animatorSet;
    }

    private Animator createAlphaAnimator(int i) {
        ObjectAnimator[] objectAnimatorArr = new ObjectAnimator[4];
        objectAnimatorArr[0] = ObjectAnimator.ofInt(this, this.alphaProperty[i], new int[]{255, 255});
        objectAnimatorArr[0].setDuration(720);
        objectAnimatorArr[1] = ObjectAnimator.ofInt(this, this.alphaProperty[i], new int[]{255, 255});
        objectAnimatorArr[1].setDuration(704);
        objectAnimatorArr[2] = ObjectAnimator.ofInt(this, this.alphaProperty[i], new int[]{255, 0, 0, 0});
        objectAnimatorArr[2].setInterpolator(new PathInterpolator(0.33f, 0.0f, FastBlurParameters.DEFAULT_SCALE, 1.0f));
        objectAnimatorArr[2].setDuration(704);
        objectAnimatorArr[3] = ObjectAnimator.ofInt(this, this.alphaProperty[i], new int[]{0, 255, 255});
        objectAnimatorArr[3].setInterpolator(new PathInterpolator(0.33f, 0.0f, FastBlurParameters.DEFAULT_SCALE, 1.0f));
        objectAnimatorArr[3].setDuration(688);
        Animator animatorSet = new AnimatorSet();
        animatorSet.playSequentially(new Animator[]{objectAnimatorArr[i % 4], objectAnimatorArr[(i + 1) % 4], objectAnimatorArr[(i + 2) % 4], objectAnimatorArr[(i + 3) % 4]});
        return animatorSet;
    }

    private void setRAlpha(int i) {
        this.rAlpha = Math.round((float) i);
        this.mRedPaint.setAlpha(this.rAlpha);
    }

    private void setBAlpha(int i) {
        this.bAlpha = Math.round((float) i);
        this.mBluePaint.setAlpha(this.bAlpha);
    }

    private void setGAlpha(int i) {
        this.gAlpha = Math.round((float) i);
        this.mGreenPaint.setAlpha(this.gAlpha);
    }

    private void setOAlpha(int i) {
        this.oAlpha = Math.round((float) i);
        this.mOrangePaint.setAlpha(this.oAlpha);
    }

    private void setCrRadius(float f) {
        this.crRadius = f;
    }

    private void setCbRadius(float f) {
        this.cbRadius = f;
    }

    private void setCgRadius(float f) {
        this.cgRadius = f;
    }

    private void setCoRadius(float f) {
        this.coRadius = f;
    }

    private void setCrPosition(float f) {
        this.crPosition = f;
    }

    private void setCbPosition(float f) {
        this.cbPosition = f;
    }

    private void setCgPosition(float f) {
        this.cgPosition = f;
    }

    private void setCoPosition(float f) {
        this.coPosition = f;
        invalidate();
    }

    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i != 0) {
            stopRunAnimator();
        } else if (isShown()) {
            startAnimator();
            this.mStopFromUser = false;
        }
    }

    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i != 0) {
            stopRunAnimator();
        } else if (isShown()) {
            startAnimator();
            this.mStopFromUser = false;
        }
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0) {
            startAnimator();
            this.mStopFromUser = false;
        } else if (i == 4 || i == 8) {
            stopRunAnimator();
        }
    }

    private void stopRunAnimator() {
        if (this.animator != null) {
            this.animator.cancel();
            this.mStopFromUser = true;
            this.mIsAnimRun = false;
            this.animator = null;
        }
    }

    protected void onMeasure(int i, int i2) {
        int round = Math.round((((this.po3 - this.po1) + this.maxRadius) + (this.mAnimScale * 4.0f)) + FastBlurParameters.DEFAULT_LEVEL);
        setMeasuredDimension(resolveSizeAndState(round + (getPaddingLeft() + getPaddingRight()), i, 0), resolveSizeAndState(Math.round((this.maxRadius * CircleProgressBar.BAR_WIDTH_DEF_DIP) + FastBlurParameters.DEFAULT_LEVEL) + (getPaddingTop() + getPaddingBottom()), i2, 0));
    }

    public float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(ApplyingAnimationView.class.getName());
    }
}
