package com.meizu.common.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.meizu.common.a.d;
import com.meizu.common.a.e;
import com.meizu.common.a.f;
import com.meizu.common.a.g;
import com.meizu.common.a.i;
import com.meizu.common.a.j;
import com.meizu.common.util.c;
import java.util.ArrayList;
import java.util.Iterator;

public class EmptyView extends FrameLayout {
    private ImageView a;
    private LimitedWHLinearLayout b;
    private TextView c;
    private TextView d;
    private LinearLayout e;
    private View f;
    private Context g;
    private int h;
    private boolean i;
    private boolean j;
    private ArrayList<String> k;
    private CharSequence l;
    private CharSequence m;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.g = context;
        View view = LayoutInflater.from(context).inflate(g.mc_empty_view, null);
        this.a = (ImageView) view.findViewById(f.empty_image);
        this.c = (TextView) view.findViewById(f.empty_title);
        this.d = (TextView) view.findViewById(f.empty_summary);
        this.e = (LinearLayout) view.findViewById(f.empty_tips_panel);
        this.f = view.findViewById(f.titleDivider);
        this.b = (LimitedWHLinearLayout) view.findViewById(f.content_panel);
        addView(view);
        LayoutParams imageLP = (LayoutParams) this.a.getLayoutParams();
        LayoutParams contentLP = (LayoutParams) this.b.getLayoutParams();
        TypedArray a = context.obtainStyledAttributes(attrs, j.EmptyView, defStyle, 0);
        imageLP.topMargin = a.getDimensionPixelSize(j.EmptyView_mcTopMarginOfImage, 0);
        contentLP.topMargin = a.getDimensionPixelSize(j.EmptyView_mcTopMarginOfTips, contentLP.topMargin);
        this.b.setMaxWidth(this.g.getResources().getDimensionPixelSize(d.mc_empty_content_panel_max_width));
        Drawable drawable = a.getDrawable(j.EmptyView_mcSrcOfImage);
        if (drawable != null) {
            this.a.setImageDrawable(drawable);
        }
        this.l = a.getString(j.EmptyView_mcTitle);
        this.m = a.getString(j.EmptyView_mcSummary);
        String tip = a.getString(j.EmptyView_mcTextOfTips);
        this.k = c.a(getContext(), a, j.EmptyView_mcTips);
        this.j = a.getBoolean(j.EmptyView_mcIsShowDot, true);
        a.recycle();
        a = this.g.obtainStyledAttributes(j.MZTheme);
        this.h = a.getInt(j.MZTheme_mzThemeColor, -16777216);
        this.c.setTextAppearance(getContext(), a.getResourceId(j.EmptyView_mcTitleTextAppearance, i.TextAppearance_Small_EmptyView_Title));
        setTitle(this.l);
        setSummary(this.m);
        if (tip == null) {
            setTextOfTips(this.k);
        } else {
            setTextOfTips(tip);
        }
        a.recycle();
        a();
    }

    private void a() {
        if (!TextUtils.isEmpty(this.l) && TextUtils.isEmpty(this.m) && (this.k == null || this.k.size() == 0)) {
            this.c.setPadding(this.c.getPaddingLeft(), getResources().getDimensionPixelSize(d.mc_empty_tip_line_space), this.c.getPaddingRight(), this.c.getPaddingBottom());
        } else if (!TextUtils.isEmpty(this.l) && this.k != null && this.k.size() > 0) {
            ((LinearLayout.LayoutParams) this.c.getLayoutParams()).topMargin = getResources().getDimensionPixelSize(d.mc_empty_title_margin_top);
        }
    }

    public void setImageDrawable(Drawable drawable) {
        if (this.a != null) {
            this.a.setImageDrawable(drawable);
        }
    }

    public void setImageResource(int resId) {
        if (this.a != null) {
            this.a.setImageResource(resId);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(EmptyView.class.getName());
    }

    public void setTextOfTips(String text) {
        ArrayList tips = new ArrayList();
        if (!TextUtils.isEmpty(text)) {
            tips.add(text);
        }
        setTextOfTips(tips);
    }

    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void setTextOfTips(ArrayList<String> tips) {
        ArrayList arrayList;
        if (tips == null) {
            arrayList = new ArrayList();
        } else {
            ArrayList<String> arrayList2 = tips;
        }
        this.k = arrayList;
        this.e.removeAllViews();
        if (this.k == null || this.k.size() == 0) {
            this.f.setVisibility(8);
            return;
        }
        LinearLayout.LayoutParams lpTipsContainer = new LinearLayout.LayoutParams(-1, -2);
        LinearLayout.LayoutParams lpDotImage = new LinearLayout.LayoutParams(-2, -2);
        LinearLayout.LayoutParams lpTipText = new LinearLayout.LayoutParams(-2, -2);
        lpTipsContainer.bottomMargin = this.g.getResources().getDimensionPixelSize(d.mc_empty_tip_margin_Bottom);
        lpDotImage.topMargin = this.g.getResources().getDimensionPixelSize(d.mc_empty_dot_margin_top);
        lpDotImage.rightMargin = this.g.getResources().getDimensionPixelSize(d.mc_empty_dot_margin_right);
        float lineSpace = (float) this.g.getResources().getDimensionPixelSize(d.mc_empty_tip_line_space);
        Drawable dot = getContext().getResources().getDrawable(e.mc_default_word_point);
        Iterator i$ = tips.iterator();
        while (i$.hasNext()) {
            String tip = (String) i$.next();
            LinearLayout tipContainer = new LinearLayout(this.g);
            tipContainer.setLayoutParams(lpTipsContainer);
            tipContainer.setOrientation(0);
            tipContainer.setGravity(48);
            if (this.j) {
                ImageView dotView = new ImageView(this.g);
                dotView.setLayoutParams(lpDotImage);
                dotView.setImageDrawable(dot);
                tipContainer.addView(dotView);
            }
            TextView tv = new TextView(this.g);
            tv.setLayoutParams(lpTipText);
            tv.setTextAppearance(getContext(), i.TextAppearance_Small_EmptyView);
            tv.setText(tip);
            tv.setLineSpacing(lineSpace, 1.0f);
            tipContainer.addView(tv);
            this.e.addView(tipContainer);
        }
        if (!TextUtils.isEmpty(this.l) || !TextUtils.isEmpty(this.m)) {
            this.f.setVisibility(0);
        }
    }

    public void setSummary(CharSequence summary) {
        if (TextUtils.isEmpty(summary)) {
            this.d.setVisibility(8);
            return;
        }
        this.m = summary;
        this.d.setText(summary);
        this.d.setAutoLinkMask(15);
        ViewTreeObserver otb = this.d.getViewTreeObserver();
        if (otb != null) {
            otb.addOnPreDrawListener(new OnPreDrawListener(this) {
                final /* synthetic */ EmptyView a;

                {
                    this.a = r1;
                }

                public boolean onPreDraw() {
                    this.a.d.getViewTreeObserver().removeOnPreDrawListener(this);
                    this.a.d.post(new Runnable(this) {
                        final /* synthetic */ AnonymousClass1 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            this.a.a.d.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    });
                    return true;
                }
            });
        } else {
            this.d.setMovementMethod(LinkMovementMethod.getInstance());
        }
        this.d.setTextColor(this.h);
        this.d.setLinkTextColor(this.h);
        this.d.setVisibility(0);
        if (this.k != null && this.k.size() > 0) {
            this.f.setVisibility(0);
        }
    }

    public void setTitle(CharSequence title) {
        if (TextUtils.isEmpty(title)) {
            this.c.setVisibility(8);
            return;
        }
        this.l = title;
        this.c.setText(title);
        this.c.setVisibility(0);
        if (this.k != null && this.k.size() > 0) {
            this.f.setVisibility(0);
        }
    }

    public void setIgnoreSoftInput(boolean ignoreSoftInput) {
        this.i = ignoreSoftInput;
        LayoutParams lp = (LayoutParams) this.b.getLayoutParams();
        if (ignoreSoftInput) {
            lp.bottomMargin = 0;
        } else {
            lp.bottomMargin = this.g.getResources().getDimensionPixelSize(d.mc_keyboard_approximate_height);
        }
    }

    public void setTitleColor(int color) {
        this.c.setTextColor(color);
    }

    public void setTitleTextSize(float size) {
        this.c.setTextSize(size);
    }

    public void setIsShowDot(boolean isShowDot) {
        if (this.j != isShowDot) {
            this.j = isShowDot;
            setTextOfTips(this.k);
        }
    }

    public void setContentPanelMaxWidth(int maxWidth) {
        this.b.setMaxWidth(maxWidth);
    }
}
