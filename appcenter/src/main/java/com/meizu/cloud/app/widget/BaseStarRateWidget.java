package com.meizu.cloud.app.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class BaseStarRateWidget extends LinearLayout {
    protected ImageView[] a;
    protected int b;
    protected int c;
    protected TextView d;

    public BaseStarRateWidget(Context context) {
        super(context);
        this.a = new ImageView[5];
        this.b = e.ic_star_nm;
        this.c = e.ic_star_on;
        a(context);
    }

    public BaseStarRateWidget(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = new ImageView[5];
        this.b = e.ic_star_nm;
        this.c = e.ic_star_on;
        a(context);
    }

    public BaseStarRateWidget(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        this.a = new ImageView[5];
        this.b = e.ic_star_nm;
        this.c = e.ic_star_on;
        a(context);
    }

    private void a(Context context) {
        View v = LayoutInflater.from(context).inflate(g.start_rate_layout, this);
        ImageView imageView2 = (ImageView) v.findViewById(f.ItemStar1);
        ImageView imageView3 = (ImageView) v.findViewById(f.ItemStar2);
        ImageView imageView4 = (ImageView) v.findViewById(f.ItemStar3);
        ImageView imageView5 = (ImageView) v.findViewById(f.ItemStar4);
        this.a[0] = (ImageView) v.findViewById(f.ItemStar0);
        this.a[1] = imageView2;
        this.a[2] = imageView3;
        this.a[3] = imageView4;
        this.a[4] = imageView5;
        this.d = (TextView) v.findViewById(f.num);
    }

    public void setCommentNum(int num) {
        this.d.setText(String.format("(%d)", new Object[]{Integer.valueOf(num)}));
    }

    public TextView getCommentNumTextView() {
        return this.d;
    }

    public void setValue(double v) {
        long n = Math.round(v);
        if (n > 5) {
            n = 5;
        }
        for (int i = 0; i < 5; i++) {
            if (((long) i) < n) {
                this.a[i].setImageDrawable(getHighLightStarDrawable());
            } else {
                this.a[i].setImageDrawable(getNormalStarDrawable());
            }
        }
    }

    public Drawable getNormalStarDrawable() {
        return getResources().getDrawable(this.b);
    }

    public Drawable getHighLightStarDrawable() {
        return getResources().getDrawable(this.c);
    }

    public void setStarRes(int normal, int highlight) {
        this.b = normal;
        this.c = highlight;
    }
}
