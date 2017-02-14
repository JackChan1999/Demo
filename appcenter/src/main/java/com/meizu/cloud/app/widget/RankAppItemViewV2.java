package com.meizu.cloud.app.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.meizu.cloud.app.core.l;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.t.a;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class RankAppItemViewV2 extends CommonListItemView {
    public RelativeLayout b;
    public ImageView c;
    public MiddleView d;
    public CirProButton e;
    public t f;
    public View g;

    public RankAppItemViewV2(Context context, t viewController) {
        super(context);
        this.f = viewController;
        a(context);
    }

    private View a(Context context, int layoutId) {
        return LayoutInflater.from(context).inflate(layoutId, this);
    }

    private MiddleView a(Context context, ViewGroup parent) {
        return new Row2MiddleView(context, parent);
    }

    public void a(Context context) {
        View rootView = a(context, g.common_appitem_view_v2);
        this.b = (RelativeLayout) rootView.findViewById(f.relativeLayout);
        this.c = (ImageView) rootView.findViewById(f.icon);
        this.e = (CirProButton) rootView.findViewById(f.btnInstall);
        setClickable(true);
        if (this.f != null && this.f.c() == null) {
            this.f.a(new l());
        }
        ViewGroup middleLayout = (FrameLayout) rootView.findViewById(f.middle_layout);
        this.d = a(context, middleLayout);
        if (this.d != null) {
            middleLayout.addView(this.d);
        }
        this.g = rootView.findViewById(f.divider);
    }

    public void a(final AppUpdateStructItem appStructItem, int position) {
        h.a(getContext(), appStructItem.icon, this.c);
        this.d.a(appStructItem, appStructItem.index, this.f);
        if (this.f != null) {
            this.f.a((a) appStructItem, null, true, this.e);
            this.e.setTag(appStructItem.package_name);
            this.e.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ RankAppItemViewV2 b;

                public void onClick(View v) {
                    appStructItem.install_page = this.b.f.b();
                    if (this.b.a != null) {
                        this.b.a.a(appStructItem, v);
                    }
                }
            });
        }
        this.g.setVisibility(appStructItem.hideDivider ? 4 : 0);
    }
}
