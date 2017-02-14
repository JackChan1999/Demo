package com.meizu.cloud.app.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class GradientButtom extends LinearLayout {
    private TextView a;
    private TextView b;

    public GradientButtom(Context context) {
        super(context);
        a(context);
    }

    public GradientButtom(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public GradientButtom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        a(context);
    }

    private void a(Context context) {
        inflate(context, g.gradient_buttom, this);
        this.a = (TextView) findViewById(f.text);
        this.b = (TextView) findViewById(f.tag1);
    }

    public void setBackgroundSelecorDrawable(Drawable normalDrawable, Drawable pressedDrawable) {
        StateListDrawable seletor = new StateListDrawable();
        seletor.addState(new int[]{16842908}, pressedDrawable);
        seletor.addState(new int[]{16842919}, pressedDrawable);
        seletor.addState(new int[0], normalDrawable);
        setBackground(seletor);
    }

    public void setText(String text) {
        this.a.setText(text);
    }

    public void setSelecorText(int normalColor, int pressedColor) {
        int[] colors = new int[]{pressedColor, pressedColor, normalColor};
        states = new int[3][];
        states[0] = new int[]{16842919};
        states[1] = new int[]{16842908};
        states[2] = new int[0];
        this.a.setTextColor(new ColorStateList(states, colors));
    }

    public void setTag(String name, String strColor) {
        this.b.setVisibility(0);
        TagView.a(this.b, name, strColor);
    }
}
