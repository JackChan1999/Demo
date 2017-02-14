package com.meizu.cloud.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import com.meizu.cloud.app.a.n;

public class LinearLayoutForListView extends LinearLayout {
    n a;

    public LinearLayoutForListView(Context context) {
        super(context);
    }

    public LinearLayoutForListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayoutForListView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    public void setAdapter(n adapter) {
        this.a = adapter;
    }
}
