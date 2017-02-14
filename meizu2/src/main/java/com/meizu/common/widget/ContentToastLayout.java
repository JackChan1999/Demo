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
import com.meizu.common.R;

public class ContentToastLayout extends FrameLayout {
    public static final int TOAST_TYPE_ERROR = 1;
    public static final int TOAST_TYPE_NORMAL = 0;
    private Drawable mActionIcon;
    private ImageView mActionView;
    private LinearLayout mContainerLayout;
    private Drawable mDefaultActionIcon;
    private Drawable mDefaultWarningIcon;
    private LinearLayout mParentLayout;
    private View mSeparatorView;
    private String mText;
    private TextView mTitleView;
    private Drawable mWarningIcon;
    private ImageView mWarningView;
    private LinearLayout mWidgetLayout;
    private View mWidgetView;

    public ContentToastLayout(Context context) {
        this(context, null);
    }

    public ContentToastLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ContentToastLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.mc_content_toast_layout, this);
        this.mParentLayout = (LinearLayout) findViewById(R.id.mc_content_toast_parent);
        this.mWidgetLayout = (LinearLayout) findViewById(16908312);
        this.mTitleView = (TextView) findViewById(16908310);
        this.mWarningView = (ImageView) findViewById(16908294);
        this.mSeparatorView = findViewById(R.id.mc_toast_separator);
        this.mContainerLayout = (LinearLayout) findViewById(R.id.mc_content_toast_container);
        this.mDefaultWarningIcon = getResources().getDrawable(R.drawable.mz_ic_content_toast_warning);
        setToastType(0);
    }

    public String getText() {
        return this.mText;
    }

    public void setText(String str) {
        this.mText = str;
        this.mTitleView.setText(str);
    }

    public void setWarningIcon(Drawable drawable) {
        this.mWarningIcon = drawable;
        this.mWarningView.setImageDrawable(this.mWarningIcon);
    }

    public void setActionIcon(Drawable drawable) {
        this.mActionIcon = drawable;
        if (this.mActionView == null) {
            this.mActionView = new ImageView(getContext());
            this.mActionView.setScaleType(ScaleType.CENTER_INSIDE);
            setWidget(this.mActionView);
        }
        this.mActionView.setImageDrawable(drawable);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(ContentToastLayout.class.getName());
    }

    public void setBackground(Drawable drawable) {
        this.mParentLayout.setBackgroundDrawable(drawable);
    }

    public void setTextColor(int i) {
        this.mTitleView.setTextColor(i);
    }

    public void setIsShowSeparator(boolean z) {
        if (z) {
            this.mSeparatorView.setVisibility(0);
        } else {
            this.mSeparatorView.setVisibility(8);
        }
    }

    public void setContainerLayoutPadding(int i) {
        this.mContainerLayout.setPadding(i, 0, i, 0);
    }

    public void setParentLayoutPadding(int i) {
        this.mParentLayout.setPadding(i, 0, i, 0);
    }

    private void setWidget(View view) {
        if (this.mWidgetView != null) {
            this.mWidgetLayout.removeView(this.mWidgetView);
        }
        if (view != null) {
            this.mWidgetLayout.addView(view);
        }
    }

    public void setToastType(int i) {
        switch (i) {
            case 1:
                setWarningIcon(this.mDefaultWarningIcon);
                this.mDefaultActionIcon = getResources().getDrawable(R.drawable.mz_arrow_next_right_warning);
                setActionIcon(this.mDefaultActionIcon);
                this.mTitleView.setTextColor(getResources().getColor(R.color.mc_content_toast_text_color_error));
                this.mParentLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.mc_content_toast_bg_error));
                this.mWarningView.setVisibility(0);
                return;
            default:
                setWarningIcon(null);
                this.mDefaultActionIcon = getResources().getDrawable(R.drawable.mz_arrow_next_right_normal);
                setActionIcon(this.mDefaultActionIcon);
                this.mTitleView.setTextColor(getResources().getColor(R.color.mc_content_toast_text_color_normal));
                this.mParentLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.mc_content_toast_bg_normal));
                this.mWarningView.setVisibility(8);
                return;
        }
    }
}
