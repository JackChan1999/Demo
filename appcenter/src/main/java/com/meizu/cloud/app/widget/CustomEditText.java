package com.meizu.cloud.app.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

public class CustomEditText extends EditText {
    private String a;

    public void setTail(String tail) {
        this.a = tail;
    }

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (!TextUtils.isEmpty(this.a) && selStart == selEnd) {
            String str = getText().toString();
            if (str.contains(this.a)) {
                int lastIndex = str.indexOf(this.a);
                if (selEnd > lastIndex) {
                    setSelection(lastIndex);
                }
            }
        }
    }
}
