package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import com.meizu.common.R;

public class VerticalSeekBar extends AbsSeekBar {
    private OnVerSeekBarChangeListener mOnSeekBarChangeListener;

    public interface OnVerSeekBarChangeListener {
        void onProgressChanged(VerticalSeekBar verticalSeekBar, int i, boolean z);

        void onStartTrackingTouch(VerticalSeekBar verticalSeekBar);

        void onStopTrackingTouch(VerticalSeekBar verticalSeekBar);
    }

    public VerticalSeekBar(Context context) {
        this(context, null);
    }

    public VerticalSeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_VerticalSeekBarStyle);
    }

    public VerticalSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SeekBar, i, 0);
        setBreakPoint(obtainStyledAttributes.getInt(R.styleable.SeekBar_mcBreakPoint, 0));
        obtainStyledAttributes.recycle();
        setIsVertical(true);
        setTouchMode(1);
    }

    void onProgressRefresh(float f, boolean z) {
        super.onProgressRefresh(f, z);
        if (this.mOnSeekBarChangeListener != null) {
            this.mOnSeekBarChangeListener.onProgressChanged(this, getProgress(), z);
        }
    }

    public void setOnSeekBarChangeListener(OnVerSeekBarChangeListener onVerSeekBarChangeListener) {
        this.mOnSeekBarChangeListener = onVerSeekBarChangeListener;
    }

    void onStartTrackingTouch() {
        super.onStartTrackingTouch();
        if (this.mOnSeekBarChangeListener != null) {
            this.mOnSeekBarChangeListener.onStartTrackingTouch(this);
        }
    }

    void onStopTrackingTouch() {
        super.onStopTrackingTouch();
        if (this.mOnSeekBarChangeListener != null) {
            this.mOnSeekBarChangeListener.onStopTrackingTouch(this);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(VerticalSeekBar.class.getName());
    }
}
