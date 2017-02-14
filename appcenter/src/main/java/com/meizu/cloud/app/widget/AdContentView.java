package com.meizu.cloud.app.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.app.request.structitem.PropertyTag;
import com.meizu.cloud.app.widget.FlowLayout.LayoutParams;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class AdContentView extends CommonListItemView {
    private FlowLayout b;
    private a c;

    public interface a {
        void a(PropertyTag propertyTag, int i, int i2);
    }

    public AdContentView(Context context) {
        super(context);
        a(context);
    }

    public AdContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public AdContentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        a(context);
    }

    private void a(Context context) {
        this.b = (FlowLayout) LayoutInflater.from(context).inflate(g.ad_content_tag_layout, this).findViewById(f.flow_layout);
    }

    public void setOnTagClickedListner(a listner) {
        this.c = listner;
    }

    public void a(AppUpdateStructItem appStructItem, int index) {
        if (appStructItem.adContent != null) {
            this.b.removeAllViews();
            if (appStructItem.adContent.data != null) {
                LayoutParams layoutParams = new LayoutParams(-2, getResources().getDimensionPixelSize(d.ad_content_tag_height));
                layoutParams.rightMargin = getResources().getDimensionPixelSize(d.ad_content_tag_margin_right);
                layoutParams.bottomMargin = getResources().getDimensionPixelSize(d.ad_content_tag_margin_bottom);
                for (int i = 0; i < appStructItem.adContent.data.size(); i++) {
                    this.b.addView(a((PropertyTag) appStructItem.adContent.data.get(i), index, i), layoutParams);
                }
            }
        }
    }

    private TextView a(final PropertyTag tag, final int position, final int horPosition) {
        TextView btn = new TextView(getContext());
        btn.setText(tag.name);
        btn.setGravity(17);
        int innerSpace = getResources().getDimensionPixelSize(d.ad_content_tag_inner_padding);
        btn.setPadding(innerSpace, 0, innerSpace, 0);
        btn.setTextSize(((float) getResources().getDimensionPixelSize(d.ad_content_tag_text_size)) / getResources().getDisplayMetrics().density);
        btn.setSingleLine();
        btn.setBackgroundResource(e.label_bg_selector);
        btn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ AdContentView d;

            public void onClick(View v) {
                if (this.d.c != null) {
                    this.d.c.a(tag, position, horPosition);
                }
            }
        });
        setSelectorText(btn, getResources().getColor(c.desc_color), getResources().getColor(c.white));
        return btn;
    }

    public void setSelectorText(TextView textView, int normalColor, int pressedColor) {
        int[] colors = new int[]{pressedColor, pressedColor, normalColor};
        states = new int[3][];
        states[0] = new int[]{16842919};
        states[1] = new int[]{16842908};
        states[2] = new int[0];
        textView.setTextColor(new ColorStateList(states, colors));
    }
}
