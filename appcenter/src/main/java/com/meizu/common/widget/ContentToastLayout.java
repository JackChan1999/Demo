package com.meizu.common.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.common.a.c;
import com.meizu.common.a.e;
import com.meizu.common.a.f;
import com.meizu.common.a.g;

public class ContentToastLayout extends FrameLayout {
    private String a;
    private Drawable b;
    private Drawable c;
    private Drawable d;
    private Drawable e;
    private TextView f;
    private ImageView g;
    private ImageView h;
    private LinearLayout i;
    private View j;
    private LinearLayout k;
    private View l;
    private LinearLayout m;

    public ContentToastLayout(Context context) {
        this(context, null);
    }

    public ContentToastLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContentToastLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(g.mc_content_toast_layout, this);
        this.k = (LinearLayout) findViewById(f.mc_content_toast_parent);
        this.i = (LinearLayout) findViewById(16908312);
        this.f = (TextView) findViewById(16908310);
        this.g = (ImageView) findViewById(16908294);
        this.l = findViewById(f.mc_toast_separator);
        this.m = (LinearLayout) findViewById(f.mc_content_toast_container);
        this.d = getResources().getDrawable(e.mz_ic_content_toast_warning);
        setToastType(0);
    }

    public String getText() {
        return this.a;
    }

    public void setText(String text) {
        this.a = text;
        this.f.setText(text);
    }

    public void setWarningIcon(Drawable warningIcon) {
        this.b = warningIcon;
        this.g.setImageDrawable(this.b);
    }

    public void setActionIcon(Drawable actionIcon) {
        this.c = actionIcon;
        if (this.h == null) {
            this.h = new ImageView(getContext());
            this.h.setScaleType(ScaleType.CENTER_INSIDE);
            setWidget(this.h);
        }
        this.h.setImageDrawable(actionIcon);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ContentToastLayout.class.getName());
    }

    public void setBackground(Drawable background) {
        this.k.setBackgroundDrawable(background);
    }

    public void setTextColor(int color) {
        this.f.setTextColor(color);
    }

    public void setIsShowSeparator(boolean isShowSeparator) {
        if (isShowSeparator) {
            this.l.setVisibility(0);
        } else {
            this.l.setVisibility(8);
        }
    }

    public void setContainerLayoutPadding(int padding) {
        this.m.setPadding(padding, 0, padding, 0);
    }

    public void setParentLayoutPadding(int padding) {
        this.k.setPadding(padding, 0, padding, 0);
    }

    private void setWidget(View widget) {
        if (this.j != null) {
            this.i.removeView(this.j);
        }
        if (widget != null) {
            this.i.addView(widget);
        }
    }

    public void setToastType(int type) {
        switch (type) {
            case 1:
                setWarningIcon(this.d);
                this.e = getResources().getDrawable(e.mz_arrow_next_right_warning);
                setActionIcon(this.e);
                this.f.setTextColor(getResources().getColor(c.mc_content_toast_text_color_error));
                this.k.setBackgroundDrawable(getResources().getDrawable(e.mc_content_toast_bg_error));
                this.g.setVisibility(0);
                return;
            default:
                setWarningIcon(null);
                this.e = getResources().getDrawable(e.mz_arrow_next_right_normal);
                setActionIcon(this.e);
                this.f.setTextColor(getResources().getColor(c.mc_content_toast_text_color_normal));
                this.k.setBackgroundDrawable(getResources().getDrawable(e.mc_content_toast_bg_normal));
                this.g.setVisibility(8);
                return;
        }
    }
}
