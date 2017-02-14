package com.meizu.cloud.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public abstract class BaseCheckableAppItem extends FrameLayout {
    protected ImageView a;
    protected TextView b;
    protected TextView c;
    protected CheckBox d;

    public BaseCheckableAppItem(Context context) {
        super(context);
        a(context);
    }

    public BaseCheckableAppItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public BaseCheckableAppItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a(context);
    }

    protected void a(Context context) {
        View v = LayoutInflater.from(context).inflate(g.checkable_grid_item, this);
        TextView titleTextView = (TextView) v.findViewById(f.title);
        TextView descTextView = (TextView) v.findViewById(f.desc);
        this.a = (ImageView) v.findViewById(f.icon);
        this.b = titleTextView;
        this.c = descTextView;
        this.d = (CheckBox) v.findViewById(f.checkbox);
    }

    public void setCheck(boolean v) {
        if (this.d != null) {
            this.d.setChecked(v);
        }
    }

    public void setIconUrl(String url) {
        h.a(getContext(), url, this.a);
    }

    public void setDesc(String desc) {
        this.c.setText(desc);
    }

    public void setTitle(String title) {
        this.b.setText(title);
    }
}
