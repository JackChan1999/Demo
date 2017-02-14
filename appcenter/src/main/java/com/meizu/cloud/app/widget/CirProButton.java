package com.meizu.cloud.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import com.meizu.cloud.b.a.g;

public class CirProButton extends ProgressTextButtonView {
    private boolean a = false;
    private boolean b = false;

    public CirProButton(Context context) {
        super(context);
        a(context);
    }

    public CirProButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    private void a(Context context) {
        inflate(context, g.progress_text, this);
        inflate(context, g.progress_text_btn, this);
    }

    public void setChargeAnim(boolean bAnim) {
        this.a = bAnim;
    }

    public boolean a() {
        return this.a;
    }

    public void a(boolean show, boolean useAnim) {
        this.b = show;
        super.a(show, useAnim);
    }

    public boolean b() {
        return this.b;
    }

    public void setOnClickListener(OnClickListener l) {
        getButton().setOnClickListener(l);
        getTextView().setOnClickListener(l);
    }
}
