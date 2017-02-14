package com.meizu.common.drawble;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import com.meizu.common.R;
import com.meizu.common.util.ResourceUtils;

public class DrawerScaleDrawable extends Drawable {
    private int mDefaultColor;
    private int mHeight;
    private final Paint mPaint = new Paint();
    private final Paint mPaintFill = new Paint(1);
    private final Path mPath = new Path();
    private int mPathDefaultLength;
    private int mPathDistance;
    private int mPathMinLength;
    private int mPathThickness;
    private float mProgress;
    private int mThemeColor;
    private int mWidth;

    public DrawerScaleDrawable(Context context) {
        Resources resources = context.getResources();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.styleable.MZTheme);
        this.mThemeColor = obtainStyledAttributes.getColor(R.styleable.MZTheme_mzThemeColor, resources.getColor(R.color.mc_default_theme_color));
        obtainStyledAttributes.recycle();
        this.mDefaultColor = resources.getColor(R.color.mc_drawerscaledrawable_default_color);
        this.mPathThickness = resources.getDimensionPixelSize(R.dimen.mc_drawerscaledrawable_path_thickness);
        this.mPathDistance = resources.getDimensionPixelSize(R.dimen.mc_drawerscaledrawable_path_distance);
        this.mPathDefaultLength = resources.getDimensionPixelSize(R.dimen.mc_drawerscaledrawable_path_length);
        this.mPathMinLength = resources.getDimensionPixelSize(R.dimen.mc_drawerscaledrawable_path_min_length);
        if (this.mPathThickness % 2 != 0) {
            this.mPathThickness++;
        }
        this.mHeight = (this.mPathThickness * 3) + (this.mPathDistance * 2);
        this.mWidth = this.mPathDefaultLength;
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(this.mDefaultColor);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeJoin(Join.ROUND);
        this.mPaint.setStrokeCap(Cap.SQUARE);
        this.mPaint.setStrokeWidth((float) this.mPathThickness);
        this.mPaintFill.setStyle(Style.FILL);
        this.mPaintFill.setColor(this.mDefaultColor);
    }

    public void draw(Canvas canvas) {
        int i;
        this.mPath.rewind();
        int i2 = this.mPathThickness / 2;
        float lerp = lerp((float) this.mPathDefaultLength, (float) this.mPathMinLength, this.mProgress) - ((float) this.mPathThickness);
        int gradualColor = ResourceUtils.getGradualColor(this.mDefaultColor, this.mThemeColor, this.mProgress, 1);
        this.mPaintFill.setColor(gradualColor);
        int i3 = i2;
        for (i = 0; i < 3; i++) {
            canvas.drawCircle((float) null, (float) i3, (float) i2, this.mPaintFill);
            canvas.drawCircle((float) ((int) (((float) null) + (((float) this.mPathThickness) + lerp))), (float) i3, (float) i2, this.mPaintFill);
            i3 += this.mPathDistance + this.mPathThickness;
        }
        this.mPaint.setColor(gradualColor);
        this.mPath.moveTo((float) i2, (float) i2);
        this.mPath.rLineTo(lerp, 0.0f);
        i = (this.mPathDistance + this.mPathThickness) + i2;
        this.mPath.moveTo((float) i2, (float) i);
        this.mPath.rLineTo(lerp, 0.0f);
        this.mPath.moveTo((float) i2, (float) (i + (this.mPathDistance + this.mPathThickness)));
        this.mPath.rLineTo(lerp, 0.0f);
        this.mPath.moveTo(0.0f, 0.0f);
        this.mPath.close();
        canvas.drawPath(this.mPath, this.mPaint);
    }

    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
        this.mPaintFill.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        this.mPaintFill.setColorFilter(colorFilter);
    }

    public int getOpacity() {
        return -3;
    }

    public void setProgress(float f) {
        this.mProgress = f;
        invalidateSelf();
    }

    public void setProgress(int i, int i2, int i3) {
        this.mProgress = ((float) i) / ((float) (i3 - i2));
        invalidateSelf();
    }

    private static float lerp(float f, float f2, float f3) {
        return ((f2 - f) * f3) + f;
    }

    public int getIntrinsicHeight() {
        return this.mHeight;
    }

    public int getIntrinsicWidth() {
        return this.mWidth;
    }
}
