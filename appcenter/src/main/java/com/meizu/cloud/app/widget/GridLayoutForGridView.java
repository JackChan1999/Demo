package com.meizu.cloud.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.GridLayout;
import com.meizu.cloud.app.fragment.v.b;

public class GridLayoutForGridView extends GridLayout {
    private b a;

    public GridLayoutForGridView(Context context) {
        super(context);
    }

    public GridLayoutForGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridLayoutForGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void a() {
        int count = this.a.getCount();
        removeAllViews();
        for (int i = 0; i < count; i++) {
            addView(this.a.getView(i, null, null), i);
        }
        Log.v("countTAG", "" + count);
    }

    public void setAdapter(b adapter) {
        this.a = adapter;
    }
}
