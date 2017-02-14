package com.meizu.common.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.meizu.common.R;

public class PagerIndicator extends View {
    private static final String TAG = "PagerIndicator";
    private int mCurPosition;
    private float mDistance;
    private float mEnlargeRadius;
    private int mFillColor;
    private int mGravity;
    private int mHighlightColor;
    private float mPageOffset;
    private int mPagerCount;
    private Paint mPaintFill;
    private Paint mPaintStroke;
    private float mRadius;
    private boolean mSnap;
    private int mSnapPage;
    private int mStrokeColor;

    public PagerIndicator(Context context) {
        this(context, null);
    }

    public PagerIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_PagerIndicator);
    }

    public PagerIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PagerIndicator, i, 0);
        Resources resources = getResources();
        float dimension = resources.getDimension(R.dimen.mc_pager_indicator_radius);
        float dimension2 = resources.getDimension(R.dimen.mc_pager_indicator_enlarge_radius);
        float dimension3 = resources.getDimension(R.dimen.mc_pager_indicator_distance);
        int color = resources.getColor(R.color.mc_pager_indicator_fill_color);
        int color2 = resources.getColor(R.color.mc_pager_indicator_highlight_color);
        this.mRadius = obtainStyledAttributes.getDimension(R.styleable.PagerIndicator_mcRadius, dimension);
        this.mEnlargeRadius = obtainStyledAttributes.getDimension(R.styleable.PagerIndicator_mcEnlargeRadius, dimension2);
        this.mDistance = obtainStyledAttributes.getDimension(R.styleable.PagerIndicator_mcDistance, dimension3);
        this.mFillColor = obtainStyledAttributes.getColor(R.styleable.PagerIndicator_mcFillColor, color);
        this.mHighlightColor = obtainStyledAttributes.getColor(R.styleable.PagerIndicator_mcHighlightColor, color2);
        this.mStrokeColor = obtainStyledAttributes.getColor(R.styleable.PagerIndicator_mcStrokeColor, color2);
        this.mGravity = obtainStyledAttributes.getInteger(R.styleable.PagerIndicator_mcGravity, 17);
        obtainStyledAttributes.recycle();
        this.mPaintFill = new Paint(1);
        this.mPaintFill.setStyle(Style.FILL);
        this.mPaintFill.setColor(this.mFillColor);
        this.mPaintStroke = new Paint(1);
        this.mPaintStroke.setStyle(Style.STROKE);
        this.mPaintStroke.setColor(this.mStrokeColor);
    }

    public void setSnap(boolean z) {
        this.mSnap = z;
    }

    public void setCirclePosition(int i) {
        this.mCurPosition = i;
        this.mSnapPage = i;
        invalidate();
    }

    public void setCirclePosOffset(float f, int i) {
        this.mCurPosition = i;
        this.mPageOffset = f;
        invalidate();
    }

    public void setPagerCount(int i) {
        if (this.mPagerCount != i) {
            this.mPagerCount = i;
            requestLayout();
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPagerCount != 0) {
            int i = this.mPagerCount;
            int width = getWidth();
            int i2 = this.mCurPosition;
            float f = this.mDistance;
            float f2 = this.mEnlargeRadius;
            float f3 = (((float) width) / CircleProgressBar.BAR_WIDTH_DEF_DIP) - ((((float) (i - 1)) * f) / CircleProgressBar.BAR_WIDTH_DEF_DIP);
            float f4 = ((((float) (i - 1)) * f) + f3) + (this.mRadius * CircleProgressBar.BAR_WIDTH_DEF_DIP);
            this.mPaintFill.setColor(this.mFillColor);
            int i3 = 0;
            while (i3 < i) {
                if (!(i2 == i3 || ((i2 == i - 1 && i3 == 0) || i3 == i2 + 1))) {
                    canvas.drawCircle((((float) i3) * f) + f3, f2, this.mRadius, this.mPaintFill);
                }
                i3++;
            }
            f4 = (((float) (this.mSnap ? this.mSnapPage : i2)) * f) + f3;
            if (i2 != i - 1) {
                f3 = f4 + f;
            }
            i2 = getGradualColor(this.mFillColor, this.mHighlightColor, this.mPageOffset, -1);
            float gradualRadius = getGradualRadius(this.mRadius, this.mEnlargeRadius, this.mPageOffset, -1);
            this.mPaintFill.setColor(i2);
            canvas.drawCircle(f4, f2, gradualRadius, this.mPaintFill);
            i3 = getGradualColor(this.mFillColor, this.mHighlightColor, this.mPageOffset, 1);
            float gradualRadius2 = getGradualRadius(this.mRadius, this.mEnlargeRadius, this.mPageOffset, 1);
            this.mPaintFill.setColor(i3);
            canvas.drawCircle(f3, f2, gradualRadius2, this.mPaintFill);
        }
    }

    private int getGradualColor(int i, int i2, float f, int i3) {
        int round;
        int red = Color.red(i);
        int green = Color.green(i);
        int blue = Color.blue(i);
        int alpha = Color.alpha(i);
        int red2 = Color.red(i2);
        int green2 = Color.green(i2);
        int blue2 = Color.blue(i2);
        int alpha2 = Color.alpha(i2);
        if (i3 < 0) {
            round = Math.round(((float) red2) - (((float) (red2 - red)) * f));
            red2 = Math.round(((float) green2) - (((float) (green2 - green)) * f));
            green = Math.round(((float) blue2) - (((float) (blue2 - blue)) * f));
            red = Math.round(((float) alpha2) - (((float) (alpha2 - alpha)) * f));
        } else {
            round = Math.round((((float) (red2 - red)) * f) + ((float) red));
            red2 = Math.round(((float) green) + (((float) (green2 - green)) * f));
            green = Math.round(((float) blue) + (((float) (blue2 - blue)) * f));
            red = Math.round(((float) alpha) + (((float) (alpha2 - alpha)) * f));
        }
        return Color.argb(red, round, red2, green);
    }

    private float getGradualRadius(float f, float f2, float f3, int i) {
        if (i < 0) {
            return f2 - ((f2 - f) * f3);
        }
        return ((f2 - f) * f3) + f;
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(resolveSizeAndState(((int) ((((float) this.mPagerCount) * this.mDistance) + (this.mRadius * CircleProgressBar.BAR_WIDTH_DEF_DIP))) + (getPaddingLeft() + getPaddingRight()), i, 0), resolveSizeAndState(((int) Math.max(this.mRadius * CircleProgressBar.BAR_WIDTH_DEF_DIP, this.mEnlargeRadius * CircleProgressBar.BAR_WIDTH_DEF_DIP)) + (getPaddingTop() + getPaddingBottom()), i2, 0));
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(PagerIndicator.class.getName());
    }
}
