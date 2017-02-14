package com.meizu.cloud.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.b.a.c;

public class TabScrollLayout extends LinearLayout {
    public static int a = -12303292;

    public TabScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabScrollLayout(Context context) {
        super(context);
    }

    private void setIndicatorViewSelected(int pos) {
        for (int i = 0; i < super.getChildCount(); i++) {
            if (i == pos) {
                ((TextView) super.getChildAt(i)).setTextColor(getResources().getColor(c.theme_color));
            } else {
                ((TextView) super.getChildAt(i)).setTextColor(a);
            }
        }
    }

    public int getInterMargin() {
        return (int) (4.0f * getResources().getDisplayMetrics().density);
    }
}
