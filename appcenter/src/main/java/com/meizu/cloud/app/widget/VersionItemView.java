package com.meizu.cloud.app.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.common.widget.FoldableTextView;
import com.meizu.common.widget.FoldableTextView.a;

public class VersionItemView extends CommonListItemView {
    protected TextView b;
    protected TextView c;
    protected TextView d;
    protected TextView e;
    protected FrameLayout f;
    protected CirProButton g;
    protected View h;
    protected FoldableTextView i;

    public VersionItemView(Context context) {
        super(context);
        a(context);
    }

    public VersionItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public VersionItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        a(context);
    }

    protected void a(Context context) {
        View rootView = LayoutInflater.from(context).inflate(g.version_item_view, this);
        this.b = (TextView) rootView.findViewById(f.txtVersion);
        this.c = (TextView) rootView.findViewById(f.txtTag);
        this.d = (TextView) rootView.findViewById(f.txtSize);
        this.e = (TextView) rootView.findViewById(f.txtRelease);
        this.f = (FrameLayout) rootView.findViewById(f.btn_layout);
        this.g = (CirProButton) rootView.findViewById(f.btnInstall);
        this.i = (FoldableTextView) rootView.findViewById(f.foldableTxt);
        this.h = rootView.findViewById(f.divider);
    }

    public void setVersionName(String versionName) {
        this.b.setText(versionName);
    }

    public void setSizeText(String size) {
        this.d.setText(size);
    }

    public void setReleaseText(String releaseText) {
        this.e.setText(releaseText);
    }

    public void setVersionDesc(boolean isFold, int foldingCount, String text, a listener) {
        this.i.setFoldText(null, null, true);
        this.i.setFoldStatus(isFold);
        this.i.setFolding(foldingCount, listener);
        this.i.setLinkColor(getResources().getColor(c.theme_color));
        this.i.setText(text);
    }

    public void setTags(String tag) {
        if (TextUtils.isEmpty(tag)) {
            this.c.setVisibility(8);
            return;
        }
        this.c.setVisibility(0);
        a(this.c, tag);
    }

    private void a(TextView view, String name) {
        Resources resources = view.getContext().getResources();
        int color = resources.getColor(c.theme_color);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke((int) resources.getDimension(d.common_flag_stroke_width), color);
        view.setText(name);
        view.setTextColor(color);
        view.setBackground(drawable);
    }

    public CirProButton getInstallBtn() {
        return this.g;
    }

    public void setInstallBtnClickListener(OnClickListener onClickListener) {
        if (this.g != null) {
            this.g.setOnClickListener(onClickListener);
        }
    }

    public void setDividerVisible(boolean visible) {
        this.h.setVisibility(visible ? 0 : 8);
    }

    public void a(AppUpdateStructItem appStructItem, int index) {
    }
}
