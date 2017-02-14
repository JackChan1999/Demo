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
import com.meizu.common.R;

public class AuraSeekBar extends SkposSeekBar {
    private static final int mMaxHeight = 48;
    private static final int mMinHeight = 7;
    private int mAuraRadis;
    private float mInitialTouchX;
    private boolean mIsDrag;
    private int mScaledTouchSlop;
    private Drawable mThumb;

    public AuraSeekBar(Context context) {
        this(context, null);
    }

    public AuraSeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_AuraSeekBarStyle);
    }

    public AuraSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsDrag = false;
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AuraSeekBar, i, 0);
        this.mThumb = obtainStyledAttributes.getDrawable(R.styleable.AuraSeekBar_mcAuraThumbDrawble);
        this.mAuraRadis = (int) obtainStyledAttributes.getDimension(R.styleable.AuraSeekBar_mcAuraDistance, 9.0f);
        obtainStyledAttributes.recycle();
    }

    protected synchronized void onMeasure(int i, int i2) {
        int i3 = 0;
        synchronized (this) {
            int size;
            Drawable progressDrawable = getProgressDrawable();
            int intrinsicHeight = this.mThumb == null ? 0 : this.mThumb.getIntrinsicHeight();
            if (progressDrawable != null) {
                size = MeasureSpec.getSize(i);
                i3 = Math.max(intrinsicHeight, Math.max(7, Math.min(48, progressDrawable.getIntrinsicHeight())));
            } else {
                size = 0;
            }
            setMeasuredDimension(resolveSizeAndState(size + (getPaddingLeft() + getPaddingRight()), i, 0), resolveSizeAndState(i3 + (getPaddingTop() + getPaddingBottom()), i2, 0));
        }
    }

    @TargetApi(16)
    protected synchronized void onDraw(Canvas canvas) {
        if (!(!this.mIsDrag || getThumb() == null || this.mThumb == null)) {
            Drawable thumb = getThumb();
            int intrinsicHeight = thumb.getIntrinsicHeight();
            int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
            Rect bounds = thumb.getBounds();
            int i = 0;
            if (height > intrinsicHeight) {
                i = ((height - intrinsicHeight) / 2) - bounds.top;
            }
            canvas.save();
            canvas.translate((float) (getPaddingLeft() - (getThumb().getIntrinsicWidth() / 2)), (float) (i + getPaddingTop()));
            this.mThumb.setBounds(bounds.left - this.mAuraRadis, bounds.top - this.mAuraRadis, bounds.right + this.mAuraRadis, bounds.bottom + this.mAuraRadis);
            this.mThumb.setAlpha(204);
            this.mThumb.draw(canvas);
            canvas.restore();
        }
        super.onDraw(canvas);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        if (!isEnabled()) {
            return false;
        }
        float x = motionEvent.getX();
        switch (motionEvent.getAction()) {
            case 0:
                this.mInitialTouchX = x;
                return true;
            case 1:
                if (!this.mIsDrag) {
                    return true;
                }
                onStopTrackTouch();
                return true;
            case 2:
                if (Math.abs(motionEvent.getX() - this.mInitialTouchX) <= ((float) this.mScaledTouchSlop)) {
                    return true;
                }
                this.mIsDrag = true;
                onProgressChanged();
                return true;
            case 3:
                if (!this.mIsDrag) {
                    return true;
                }
                onStopTrackTouch();
                return true;
            default:
                return true;
        }
    }

    private void onProgressChanged() {
        this.mIsDrag = true;
        if (this.mThumb != null) {
            invalidate(this.mThumb.getBounds());
        }
    }

    private void onStopTrackTouch() {
        this.mIsDrag = false;
        if (this.mThumb != null) {
            invalidate(this.mThumb.getBounds());
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(AuraSeekBar.class.getName());
    }
}
