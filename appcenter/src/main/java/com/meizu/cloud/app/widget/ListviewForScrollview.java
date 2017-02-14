package com.meizu.cloud.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ListView;

public class ListviewForScrollview extends ListView {
    public ListviewForScrollview(Context context) {
        super(context);
    }

    public ListviewForScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListviewForScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
