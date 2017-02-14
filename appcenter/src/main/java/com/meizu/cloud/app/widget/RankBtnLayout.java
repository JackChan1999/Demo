package com.meizu.cloud.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public final class RankBtnLayout extends FrameLayout {
    public CirProButton a;

    public RankBtnLayout(Context context) {
        super(context);
        a(context);
    }

    public RankBtnLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public RankBtnLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a(context);
    }

    protected void a(Context context) {
        this.a = (CirProButton) LayoutInflater.from(context).inflate(g.install_btn_layout, this).findViewById(f.btnInstall);
    }
}
