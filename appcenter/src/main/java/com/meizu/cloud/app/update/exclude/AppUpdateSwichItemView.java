package com.meizu.cloud.app.update.exclude;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.common.widget.Switch;

public class AppUpdateSwichItemView extends LinearLayout {
    protected ImageView a;
    protected TextView b;
    protected Switch c;

    public AppUpdateSwichItemView(Context context) {
        super(context);
        a(context);
    }

    public AppUpdateSwichItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public AppUpdateSwichItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        a(context);
    }

    protected void a(Context context) {
        View v = LayoutInflater.from(context).inflate(g.app_update_exclude_item, this);
        this.a = (ImageView) v.findViewById(f.app_image);
        this.b = (TextView) v.findViewById(f.app_title);
        this.c = (Switch) v.findViewById(f.app_switch);
    }

    public void setIcon(Drawable icon) {
        this.a.setImageDrawable(icon);
    }

    public void setTitleTextView(String title) {
        this.b.setText(title);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener checkedChangeListener) {
        this.c.setOnCheckedChangeListener(checkedChangeListener);
    }

    public void setCheck(boolean bChecked) {
        this.c.setChecked(bChecked);
    }
}
