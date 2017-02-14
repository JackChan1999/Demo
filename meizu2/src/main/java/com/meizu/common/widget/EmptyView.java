package com.meizu.common.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.meizu.common.R;
import com.meizu.common.util.ResourceUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class EmptyView extends FrameLayout {
    private boolean ignoreSoftInput;
    private LimitedWHLinearLayout mContentPanel;
    private Context mContext;
    private View mDividerView;
    private ImageView mImageView;
    private boolean mIsShowDot;
    private CharSequence mSummary;
    private TextView mSummaryView;
    private int mThemeColor;
    private ArrayList<String> mTips;
    private LinearLayout mTipsPanle;
    private CharSequence mTitle;
    private TextView mTitleView;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EmptyView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.mc_empty_view, null);
        this.mImageView = (ImageView) inflate.findViewById(R.id.empty_image);
        this.mTitleView = (TextView) inflate.findViewById(R.id.empty_title);
        this.mSummaryView = (TextView) inflate.findViewById(R.id.empty_summary);
        this.mTipsPanle = (LinearLayout) inflate.findViewById(R.id.empty_tips_panel);
        this.mDividerView = inflate.findViewById(R.id.titleDivider);
        this.mContentPanel = (LimitedWHLinearLayout) inflate.findViewById(R.id.content_panel);
        addView(inflate);
        LayoutParams layoutParams = (LayoutParams) this.mImageView.getLayoutParams();
        LayoutParams layoutParams2 = (LayoutParams) this.mContentPanel.getLayoutParams();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EmptyView, i, 0);
        layoutParams.topMargin = obtainStyledAttributes.getDimensionPixelSize(R.styleable.EmptyView_mcTopMarginOfImage, 0);
        layoutParams2.topMargin = obtainStyledAttributes.getDimensionPixelSize(R.styleable.EmptyView_mcTopMarginOfTips, layoutParams2.topMargin);
        this.mContentPanel.setMaxWidth(this.mContext.getResources().getDimensionPixelSize(R.dimen.mc_empty_content_panel_max_width));
        Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.EmptyView_mcSrcOfImage);
        if (drawable != null) {
            this.mImageView.setImageDrawable(drawable);
        }
        this.mTitle = obtainStyledAttributes.getString(R.styleable.EmptyView_mcTitle);
        this.mSummary = obtainStyledAttributes.getString(R.styleable.EmptyView_mcSummary);
        String string = obtainStyledAttributes.getString(R.styleable.EmptyView_mcTextOfTips);
        this.mTips = ResourceUtils.getStringArray(getContext(), obtainStyledAttributes, R.styleable.EmptyView_mcTips);
        this.mIsShowDot = obtainStyledAttributes.getBoolean(R.styleable.EmptyView_mcIsShowDot, true);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = this.mContext.obtainStyledAttributes(R.styleable.MZTheme);
        this.mThemeColor = obtainStyledAttributes2.getInt(R.styleable.MZTheme_mzThemeColor, ViewCompat.MEASURED_STATE_MASK);
        this.mTitleView.setTextAppearance(getContext(), obtainStyledAttributes2.getResourceId(R.styleable.EmptyView_mcTitleTextAppearance, R.style.TextAppearance_Small_EmptyView_Title));
        setTitle(this.mTitle);
        setSummary(this.mSummary);
        if (string == null) {
            setTextOfTips(this.mTips);
        } else {
            setTextOfTips(string);
        }
        obtainStyledAttributes2.recycle();
        viewControl();
    }

    private void viewControl() {
        if (!TextUtils.isEmpty(this.mTitle) && TextUtils.isEmpty(this.mSummary) && (this.mTips == null || this.mTips.size() == 0)) {
            this.mTitleView.setPadding(this.mTitleView.getPaddingLeft(), getResources().getDimensionPixelSize(R.dimen.mc_empty_tip_line_space), this.mTitleView.getPaddingRight(), this.mTitleView.getPaddingBottom());
        } else if (!TextUtils.isEmpty(this.mTitle) && this.mTips != null && this.mTips.size() > 0) {
            ((LinearLayout.LayoutParams) this.mTitleView.getLayoutParams()).topMargin = getResources().getDimensionPixelSize(R.dimen.mc_empty_title_margin_top);
        }
    }

    public void setImageDrawable(Drawable drawable) {
        if (this.mImageView != null) {
            this.mImageView.setImageDrawable(drawable);
        }
    }

    public void setImageResource(int i) {
        if (this.mImageView != null) {
            this.mImageView.setImageResource(i);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(EmptyView.class.getName());
    }

    public void setTextOfTips(String str) {
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str)) {
            arrayList.add(str);
        }
        setTextOfTips(arrayList);
    }

    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void setTextOfTips(ArrayList<String> arrayList) {
        ArrayList arrayList2;
        if (arrayList == null) {
            arrayList2 = new ArrayList();
        } else {
            ArrayList<String> arrayList3 = arrayList;
        }
        this.mTips = arrayList2;
        this.mTipsPanle.removeAllViews();
        if (this.mTips == null || this.mTips.size() == 0) {
            this.mDividerView.setVisibility(8);
            return;
        }
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        ViewGroup.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        ViewGroup.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.mc_empty_tip_margin_Bottom);
        layoutParams2.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.mc_empty_dot_margin_top);
        layoutParams2.rightMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.mc_empty_dot_margin_right);
        float dimensionPixelSize = (float) this.mContext.getResources().getDimensionPixelSize(R.dimen.mc_empty_tip_line_space);
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.mc_default_word_point);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            View imageView;
            String str = (String) it.next();
            View linearLayout = new LinearLayout(this.mContext);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(0);
            linearLayout.setGravity(48);
            if (this.mIsShowDot) {
                imageView = new ImageView(this.mContext);
                imageView.setLayoutParams(layoutParams2);
                imageView.setImageDrawable(drawable);
                linearLayout.addView(imageView);
            }
            imageView = new TextView(this.mContext);
            imageView.setLayoutParams(layoutParams3);
            imageView.setTextAppearance(getContext(), R.style.TextAppearance_Small_EmptyView);
            imageView.setText(str);
            imageView.setLineSpacing(dimensionPixelSize, 1.0f);
            linearLayout.addView(imageView);
            this.mTipsPanle.addView(linearLayout);
        }
        if (!TextUtils.isEmpty(this.mTitle) || !TextUtils.isEmpty(this.mSummary)) {
            this.mDividerView.setVisibility(0);
        }
    }

    public void setSummary(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            this.mSummaryView.setVisibility(8);
            return;
        }
        this.mSummary = charSequence;
        this.mSummaryView.setText(charSequence);
        this.mSummaryView.setAutoLinkMask(15);
        ViewTreeObserver viewTreeObserver = this.mSummaryView.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
                public boolean onPreDraw() {
                    EmptyView.this.mSummaryView.getViewTreeObserver().removeOnPreDrawListener(this);
                    EmptyView.this.mSummaryView.post(new Runnable() {
                        public void run() {
                            EmptyView.this.mSummaryView.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    });
                    return true;
                }
            });
        } else {
            this.mSummaryView.setMovementMethod(LinkMovementMethod.getInstance());
        }
        this.mSummaryView.setTextColor(this.mThemeColor);
        this.mSummaryView.setLinkTextColor(this.mThemeColor);
        this.mSummaryView.setVisibility(0);
        if (this.mTips != null && this.mTips.size() > 0) {
            this.mDividerView.setVisibility(0);
        }
    }

    public void setTitle(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            this.mTitleView.setVisibility(8);
            return;
        }
        this.mTitle = charSequence;
        this.mTitleView.setText(charSequence);
        this.mTitleView.setVisibility(0);
        if (this.mTips != null && this.mTips.size() > 0) {
            this.mDividerView.setVisibility(0);
        }
    }

    public void setIgnoreSoftInput(boolean z) {
        this.ignoreSoftInput = z;
        LayoutParams layoutParams = (LayoutParams) this.mContentPanel.getLayoutParams();
        if (z) {
            layoutParams.bottomMargin = 0;
        } else {
            layoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.mc_keyboard_approximate_height);
        }
    }

    public void setTitleColor(int i) {
        this.mTitleView.setTextColor(i);
    }

    public void setTitleTextSize(float f) {
        this.mTitleView.setTextSize(f);
    }

    public void setIsShowDot(boolean z) {
        if ((this.mIsShowDot == z ? null : 1) != null) {
            this.mIsShowDot = z;
            setTextOfTips(this.mTips);
        }
    }

    public void setContentPanelMaxWidth(int i) {
        this.mContentPanel.setMaxWidth(i);
    }
}
