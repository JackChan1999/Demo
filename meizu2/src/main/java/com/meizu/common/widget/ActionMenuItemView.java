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
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.meizu.common.R;

public class ActionMenuItemView extends LinearLayout {
    static final String TAG = "ActionMenuItemView";
    private Context mContext;
    private ImageView mIcon;
    private boolean mIsShowing = false;
    private PopupMenu mPopup;
    private TextView mTitle;
    private ImageView mborderless;

    class MenuDismissListener implements OnDismissListener {
        MenuDismissListener() {
        }

        public void onDismiss(PopupMenu popupMenu) {
            ActionMenuItemView.this.mIsShowing = false;
            ActionMenuItemView.this.mborderless.setRotation(BitmapDescriptorFactory.HUE_CYAN);
        }
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        initView();
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
    }

    public ActionMenuItemView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.mc_action_menu_view, this);
        if (inflate == null) {
            Log.w("CustomItemView", "can not inflate the view");
            return;
        }
        setClickable(true);
        setGravity(17);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.mz_action_button_min_width);
        int dimensionPixelSize2 = getContext().getResources().getDimensionPixelSize(R.dimen.mz_action_button_min_height);
        setMinimumWidth(dimensionPixelSize);
        setMinimumHeight(dimensionPixelSize2);
        this.mTitle = (TextView) inflate.findViewById(R.id.menu_text);
        this.mIcon = (ImageView) inflate.findViewById(R.id.menu_image);
        this.mborderless = (ImageView) inflate.findViewById(R.id.borderless);
        this.mborderless.setRotation(BitmapDescriptorFactory.HUE_CYAN);
        this.mTitle.setActivated(false);
        setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ActionMenuItemView.this.mPopup != null && ActionMenuItemView.this.mPopup.getMenu().hasVisibleItems() && !ActionMenuItemView.this.mIsShowing) {
                    ActionMenuItemView.this.mIsShowing = true;
                    ActionMenuItemView.this.mPopup.show();
                    ActionMenuItemView.this.mborderless.setRotation(0.0f);
                }
            }
        });
        setBackground(getResources().getDrawable(R.drawable.mc_action_menu_view_background));
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
    }

    public void setPressed(boolean z) {
        super.setPressed(z);
    }

    public void setIcon(Drawable drawable) {
        if (this.mIcon != null) {
            this.mIcon.setVisibility(0);
            this.mIcon.setImageDrawable(drawable);
        }
        if (this.mTitle != null) {
            this.mTitle.setVisibility(8);
        }
    }

    public void setTitle(CharSequence charSequence) {
        if (this.mTitle != null) {
            this.mTitle.setVisibility(0);
            this.mTitle.setText(charSequence);
        }
        if (this.mIcon != null) {
            this.mIcon.setVisibility(8);
        }
    }

    public ImageView getIcon() {
        return this.mIcon;
    }

    public TextView getTitle() {
        return this.mTitle;
    }

    public void inflateMenu(int i) {
        this.mPopup = new PopupMenu(this.mContext, this);
        this.mPopup.getMenuInflater().inflate(i, this.mPopup.getMenu());
        this.mPopup.setOnDismissListener(new MenuDismissListener());
    }

    private void dismiss() {
        if (this.mPopup != null) {
            this.mPopup.dismiss();
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        if (this.mPopup != null) {
            this.mPopup.setOnMenuItemClickListener(onMenuItemClickListener);
        }
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        if (this.mPopup != null) {
            this.mPopup.setOnDismissListener(onDismissListener);
        }
    }

    public PopupMenu getPopup() {
        if (this.mPopup == null) {
            this.mPopup = new PopupMenu(this.mContext, this);
        }
        this.mPopup.setOnDismissListener(new MenuDismissListener());
        return this.mPopup;
    }

    @Deprecated
    public void setPopupCenterHorizontal(boolean z) {
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(ActionMenuItemView.class.getName());
    }
}
