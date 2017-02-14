package com.meizu.common.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityNodeInfo;
import com.meizu.common.a.b;
import com.meizu.common.a.j;

public class AuraSeekBar extends SkposSeekBar {
    private boolean a;
    private Drawable b;
    private int c;
    private float d;
    private int e;

    public AuraSeekBar(Context context) {
        this(context, null);
    }

    public AuraSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, b.MeizuCommon_AuraSeekBarStyle);
    }

    public AuraSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.a = false;
        this.e = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray a = context.obtainStyledAttributes(attrs, j.AuraSeekBar, defStyleAttr, 0);
        this.b = a.getDrawable(j.AuraSeekBar_mcAuraThumbDrawble);
        this.c = (int) a.getDimension(j.AuraSeekBar_mcAuraDistance, 9.0f);
        a.recycle();
    }

    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int thumbHeight = 0;
        synchronized (this) {
            Drawable drawable = getProgressDrawable();
            if (this.b != null) {
                thumbHeight = this.b.getIntrinsicHeight();
            }
            int dw = 0;
            int dh = 0;
            if (drawable != null) {
                dw = MeasureSpec.getSize(widthMeasureSpec);
                dh = Math.max(thumbHeight, Math.max(7, Math.min(48, drawable.getIntrinsicHeight())));
            }
            setMeasuredDimension(resolveSizeAndState(dw + (getPaddingLeft() + getPaddingRight()), widthMeasureSpec, 0), resolveSizeAndState(dh + (getPaddingTop() + getPaddingBottom()), heightMeasureSpec, 0));
        }
    }

    @TargetApi(16)
    protected synchronized void onDraw(Canvas canvas) {
        if (!(!this.a || getThumb() == null || this.b == null)) {
            Drawable thumb = getThumb();
            int thumbHeight = thumb.getIntrinsicHeight();
            int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
            Rect bounds = thumb.getBounds();
            int delta = 0;
            if (height > thumbHeight) {
                delta = ((height - thumbHeight) / 2) - bounds.top;
            }
            canvas.save();
            canvas.translate((float) (getPaddingLeft() - (getThumb().getIntrinsicWidth() / 2)), (float) (getPaddingTop() + delta));
            this.b.setBounds(bounds.left - this.c, bounds.top - this.c, bounds.right + this.c, bounds.bottom + this.c);
            this.b.setAlpha(204);
            this.b.draw(canvas);
            canvas.restore();
        }
        super.onDraw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (!isEnabled()) {
            return false;
        }
        float x = event.getX();
        switch (event.getAction()) {
            case 0:
                this.d = x;
                return true;
            case 1:
                if (!this.a) {
                    return true;
                }
                b();
                return true;
            case 2:
                if (Math.abs(event.getX() - this.d) <= ((float) this.e)) {
                    return true;
                }
                this.a = true;
                a();
                return true;
            case 3:
                if (!this.a) {
                    return true;
                }
                b();
                return true;
            default:
                return true;
        }
    }

    private void a() {
        this.a = true;
        if (this.b != null) {
            invalidate(this.b.getBounds());
        }
    }

    private void b() {
        this.a = false;
        if (this.b != null) {
            invalidate(this.b.getBounds());
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(AuraSeekBar.class.getName());
    }
}
