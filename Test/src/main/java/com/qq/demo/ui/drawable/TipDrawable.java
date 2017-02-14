package com.qq.demo.ui.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import com.qq.demo.R;

import java.io.InputStream;

public class TipDrawable extends BitmapDrawable {
    Context mContext;
    int mRadius;
    Paint mTiPaint;
    int mTipColor;

    public TipDrawable(Context context, Resources resources, Bitmap bitmap) {
        super(resources, bitmap);
        init(context);
    }

    public TipDrawable(Context context, Resources resources, String str) {
        super(resources, str);
        init(context);
    }

    public TipDrawable(Context context, Resources resources, InputStream inputStream) {
        super(resources, inputStream);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.TipDrawable,
                R.attr.MeizuCommon_TipDrawableStyle, 0);
        this.mTipColor = obtainStyledAttributes.getColor(R.styleable.TipDrawable_mcTipColor,
                this.mContext.getResources().getColor(R.color.tip_color));
        this.mRadius = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TipDrawable_mcTipRadius,
                this.mContext.getResources().getDimensionPixelSize(R.dimen.mc_drawable_tip_radius));
        obtainStyledAttributes.recycle();
        this.mTiPaint = new Paint();
        this.mTiPaint.setAntiAlias(true);
        this.mTiPaint.setColor(this.mTipColor);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawCircle((float) (getBounds().right + ((this.mRadius * 3) / 5)), (float) (getBounds().top + this.mRadius), (float) this.mRadius, this.mTiPaint);
    }

    public void setTipColor(int i) {
        if (this.mTipColor != i) {
            this.mTipColor = i;
            this.mTiPaint.setColor(this.mTipColor);
            invalidateSelf();
        }
    }

    public void setTipRadius(int i) {
        if (this.mRadius != i) {
            this.mRadius = i;
            invalidateSelf();
        }
    }
}
