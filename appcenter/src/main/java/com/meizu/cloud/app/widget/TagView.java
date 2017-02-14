package com.meizu.cloud.app.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.meizu.cloud.app.request.structitem.Tags;
import com.meizu.cloud.app.request.structitem.Tags.Name;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import java.io.UnsupportedEncodingException;

public class TagView extends RelativeLayout {
    protected TextView a;
    protected TextView b;

    public TagView(Context context) {
        super(context);
        a(context);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a(context);
    }

    protected void a(Context context) {
        View v = LayoutInflater.from(context).inflate(g.tag_layout, this);
        this.a = (TextView) v.findViewById(f.tag1);
        this.b = (TextView) v.findViewById(f.tag2);
    }

    public void setTags(String appName, Tags tags) {
        if (tags == null) {
            this.a.setVisibility(8);
            this.b.setVisibility(8);
        } else if (tags.names == null || tags.names.size() <= 0) {
            this.a.setVisibility(8);
            this.b.setVisibility(8);
        } else {
            try {
                int charCount = appName.getBytes("GB18030").length;
                Name name;
                if (charCount <= 10) {
                    int size = tags.names.size();
                    if (size > 0) {
                        name = (Name) tags.names.get(0);
                        this.a.setVisibility(0);
                        a(this.a, name.text, name.bg_color);
                    } else {
                        this.a.setVisibility(8);
                    }
                    if (size > 1) {
                        name = (Name) tags.names.get(1);
                        this.b.setVisibility(0);
                        a(this.b, name.text, name.bg_color);
                        return;
                    }
                    this.b.setVisibility(8);
                } else if (charCount <= 14) {
                    name = (Name) tags.names.get(0);
                    this.a.setVisibility(0);
                    a(this.a, name.text, name.bg_color);
                    this.b.setVisibility(8);
                } else {
                    this.a.setVisibility(8);
                    this.b.setVisibility(8);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void a(TextView view, String name, String strColor) {
        Resources resources = view.getContext().getResources();
        int color = resources.getColor(c.theme_color);
        if (!TextUtils.isEmpty(strColor)) {
            while (strColor.length() < 7) {
                strColor = strColor + "0";
            }
            try {
                color = Color.parseColor(strColor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke((int) resources.getDimension(d.tag_stroke_width), color);
        drawable.setColor(color);
        drawable.setShape(0);
        drawable.setCornerRadius((float) resources.getDimensionPixelSize(d.tag_stroke_corner_radius));
        view.setText(name);
        view.setTextColor(-1);
        view.setBackground(drawable);
    }
}
