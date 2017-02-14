package com.meizu.common.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnDismissListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import com.meizu.common.a.d;
import com.meizu.common.a.e;
import com.meizu.common.a.f;
import com.meizu.common.a.g;

public class ActionMenuItemView extends LinearLayout {
    private PopupMenu a;
    private Context b;
    private TextView c;
    private ImageView d;
    private ImageView e;
    private boolean f = false;

    class a implements OnDismissListener {
        final /* synthetic */ ActionMenuItemView a;

        a(ActionMenuItemView actionMenuItemView) {
            this.a = actionMenuItemView;
        }

        public void onDismiss(PopupMenu menu) {
            this.a.f = false;
            this.a.e.setRotation(180.0f);
        }
    }

    public ActionMenuItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.b = context;
        a();
    }

    public ActionMenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.b = context;
        a();
    }

    public ActionMenuItemView(Context context) {
        super(context);
        this.b = context;
        a();
    }

    private void a() {
        View child = LayoutInflater.from(this.b).inflate(g.mc_action_menu_view, this);
        if (child == null) {
            Log.w("CustomItemView", "can not inflate the view");
            return;
        }
        setClickable(true);
        setGravity(17);
        int minWidth = getContext().getResources().getDimensionPixelSize(d.mz_action_button_min_width);
        int minHeight = getContext().getResources().getDimensionPixelSize(d.mz_action_button_min_height);
        setMinimumWidth(minWidth);
        setMinimumHeight(minHeight);
        this.c = (TextView) child.findViewById(f.menu_text);
        this.d = (ImageView) child.findViewById(f.menu_image);
        this.e = (ImageView) child.findViewById(f.borderless);
        this.e.setRotation(180.0f);
        this.c.setActivated(false);
        setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ActionMenuItemView a;

            {
                this.a = r1;
            }

            public void onClick(View arg0) {
                if (this.a.a != null && this.a.a.getMenu().hasVisibleItems() && !this.a.f) {
                    this.a.f = true;
                    this.a.a.show();
                    this.a.e.setRotation(0.0f);
                }
            }
        });
        setBackground(getResources().getDrawable(e.mc_action_menu_view_background));
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
    }

    public void setIcon(Drawable icon) {
        if (this.d != null) {
            this.d.setVisibility(0);
            this.d.setImageDrawable(icon);
        }
        if (this.c != null) {
            this.c.setVisibility(8);
        }
    }

    public void setTitle(CharSequence title) {
        if (this.c != null) {
            this.c.setVisibility(0);
            this.c.setText(title);
        }
        if (this.d != null) {
            this.d.setVisibility(8);
        }
    }

    public ImageView getIcon() {
        return this.d;
    }

    public TextView getTitle() {
        return this.c;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        if (this.a != null) {
            this.a.setOnMenuItemClickListener(listener);
        }
    }

    public void setOnDismissListener(OnDismissListener listener) {
        if (this.a != null) {
            this.a.setOnDismissListener(listener);
        }
    }

    public PopupMenu getPopup() {
        if (this.a == null) {
            this.a = new PopupMenu(this.b, this);
        }
        this.a.setOnDismissListener(new a(this));
        return this.a;
    }

    @Deprecated
    public void setPopupCenterHorizontal(boolean center) {
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ActionMenuItemView.class.getName());
    }
}
