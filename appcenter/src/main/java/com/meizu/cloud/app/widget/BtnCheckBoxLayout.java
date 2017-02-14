package com.meizu.cloud.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class BtnCheckBoxLayout extends FrameLayout {
    private CheckBox a;

    public BtnCheckBoxLayout(Context context) {
        super(context);
        a(context);
    }

    public BtnCheckBoxLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public BtnCheckBoxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a(context);
    }

    protected void a(Context context) {
        this.a = (CheckBox) LayoutInflater.from(context).inflate(g.install_btn_layout, this).findViewById(f.checkbox);
    }
}
