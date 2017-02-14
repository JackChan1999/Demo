package com.meizu.widget;

import android.content.Context;
import android.text.Spannable;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class SpannableTextView extends TextView {
    public SpannableTextView(Context context) {
        this(context, null);
    }

    public SpannableTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public SpannableTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setFocusableInTouchMode(true);
    }

    protected MovementMethod getDefaultMovementMethod() {
        return ArrowKeyMovementMethod.getInstance();
    }

    public Spannable getText() {
        return (Spannable) super.getText();
    }

    public void setText(CharSequence charSequence, BufferType bufferType) {
        super.setText(charSequence, BufferType.SPANNABLE);
    }

    public boolean startTextSelection() {
        return onTextContextMenuItem(16908328);
    }

    public boolean stopTextSelection() {
        return onTextContextMenuItem(16908329);
    }

    public boolean isTextSelecting() {
        return false;
    }

    protected boolean getDefaultMagnifierVisible() {
        return false;
    }

    protected boolean getDefaultOptionsVisible() {
        return true;
    }
}
