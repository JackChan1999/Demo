package com.meizu.cloud.app.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.meizu.cloud.app.core.l;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.t.a;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.b.a.b;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;

public class RankAppItemView extends CommonListItemView {
    public RelativeLayout b;
    public ImageView c;
    public TextView d;
    public LinearLayout e;
    public TextView f;
    public TagView g;
    public CirProButton h;
    public BaseStarRateWidget i;
    public TextView j;
    public TextView k;
    public View l;
    public View m;
    public LinearLayout n;
    public t o;
    private boolean p;
    private boolean q;

    public RankAppItemView(Context context, t viewController) {
        super(context);
        b(context);
        a(viewController);
    }

    public RankAppItemView(Context context) {
        super(context);
        b(context);
    }

    public RankAppItemView(Context context, int layoutId) {
        super(context);
        b(context, layoutId);
    }

    public RankAppItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        b(context);
    }

    public RankAppItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        b(context);
    }

    public View a(Context context) {
        return a(context, g.common_appitem_view);
    }

    public View a(Context context, int layoutId) {
        return LayoutInflater.from(context).inflate(layoutId, this);
    }

    public void b(Context context) {
        b(context, -1);
    }

    public void b(Context context, int layoutId) {
        View rootView;
        if (layoutId == -1) {
            rootView = a(context);
        } else {
            rootView = a(context, layoutId);
        }
        this.b = (RelativeLayout) rootView.findViewById(f.relativeLayout);
        this.c = (ImageView) rootView.findViewById(f.icon);
        this.e = (LinearLayout) rootView.findViewById(f.layout_title);
        this.f = (TextView) rootView.findViewById(f.txt_title);
        this.g = (TagView) rootView.findViewById(f.tagView);
        this.j = (TextView) rootView.findViewById(f.txt_desc);
        this.i = (BaseStarRateWidget) rootView.findViewById(f.starRate);
        this.k = (TextView) rootView.findViewById(f.txt_bottom);
        this.d = (TextView) rootView.findViewById(f.txt_index);
        this.h = (CirProButton) rootView.findViewById(f.btnInstall);
        this.l = rootView.findViewById(f.divider);
        this.m = rootView.findViewById(f.list_last_bg_divider_view);
        this.n = (LinearLayout) rootView.findViewById(f.common_titlestar_container);
        a();
        setClickable(true);
    }

    public void setIconUrl(String url) {
        Object tag = this.c.getTag();
        if (url != null && !url.equals(tag)) {
            this.c.setTag(url);
            h.a(getContext(), url, this.c);
        }
    }

    public void a(int index, t viewController) {
        Typeface tf1 = null;
        Typeface tf2 = null;
        if (!(viewController == null || viewController.c() == null)) {
            tf1 = viewController.c().d;
            tf2 = viewController.c().e;
        }
        if (index <= 3) {
            switch (index) {
                case 1:
                    this.d.setTextColor(getResources().getColor(c.rank_index_first));
                    this.d.getPaint().setFakeBoldText(true);
                    if (tf1 != null) {
                        this.d.setTypeface(tf1);
                        break;
                    }
                    break;
                case 2:
                    this.d.setTextColor(getResources().getColor(c.rank_index_second));
                    this.d.getPaint().setFakeBoldText(true);
                    if (tf1 != null) {
                        this.d.setTypeface(tf1);
                        break;
                    }
                    break;
                case 3:
                    this.d.setTextColor(getResources().getColor(c.rank_index_third));
                    this.d.getPaint().setFakeBoldText(true);
                    if (tf1 != null) {
                        this.d.setTypeface(tf1);
                        break;
                    }
                    break;
            }
        }
        this.d.setTextColor(getResources().getColor(c.app_info_app_name_color));
        this.d.getPaint().setFakeBoldText(false);
        this.d.setVisibility(0);
        this.d.setText(String.format("%d. ", new Object[]{Integer.valueOf(index)}));
        if (tf2 != null) {
            this.d.setTypeface(tf2);
        }
    }

    public void a(final AppUpdateStructItem appStructItem, t viewController) {
        a(viewController);
        if (this.c != null) {
            h.a(getContext(), appStructItem.icon, this.c);
        }
        this.f.setText(appStructItem.name);
        this.g.setTags(appStructItem.name, appStructItem.tags);
        String size = com.meizu.cloud.app.utils.g.a((double) appStructItem.size, getResources().getStringArray(b.sizeUnit));
        String install = String.format("%s%s", new Object[]{com.meizu.cloud.app.utils.g.a(getContext(), appStructItem.download_count), getResources().getString(i.user_downloaded)});
        if (this.q) {
            this.i.setValue((double) (((float) appStructItem.star) / 10.0f));
            this.i.setVisibility(0);
            if (appStructItem.evaluate_count > 0) {
                this.i.setCommentNum(appStructItem.evaluate_count);
            }
            this.j.setVisibility(8);
            if (getStyle() == 4) {
                a(this.k, appStructItem, size);
            } else if (getStyle() == 3) {
                a(this.k, appStructItem, size, install);
            }
        } else {
            this.j.setVisibility(0);
            this.i.setVisibility(8);
            if (getStyle() != 2 || TextUtils.isEmpty(appStructItem.recommend_desc)) {
                this.j.setText(appStructItem.category_name);
            } else {
                this.j.setText(appStructItem.recommend_desc);
            }
            a(this.k, appStructItem, size, install);
        }
        if (this.o != null) {
            viewController.a((a) appStructItem, null, true, this.h);
            this.h.setTag(appStructItem.package_name);
            this.h.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ RankAppItemView b;

                public void onClick(View v) {
                    appStructItem.install_page = this.b.o.b();
                    if (this.b.a != null) {
                        this.b.a.a(appStructItem, v);
                    }
                }
            });
        }
    }

    public void a(AppUpdateStructItem appStructItem, int index) {
        if (this.p) {
            a(index, this.o);
        }
        a(appStructItem, this.o);
    }

    public View getDefaultDivider() {
        return this.l;
    }

    private void a(TextView textView, AppUpdateStructItem appStructItem, String size) {
        SpannableString spanSize = null;
        CharSequence text;
        if (appStructItem.isDownload) {
            text = String.format("%s\t\t%s\t\t%s", new Object[]{size, appStructItem.category_name, getContext().getText(i.update_downloaded)});
            spanSize = a(text, text.indexOf(size), text.indexOf(size) + size.length());
        } else if (appStructItem.patchSize > 0) {
            String patchSize = com.meizu.cloud.app.utils.g.a((double) appStructItem.patchSize, getResources().getStringArray(b.sizeUnit));
            text = String.format("%s\t\t%s\t\t%s", new Object[]{size, appStructItem.category_name, patchSize});
            spanSize = a(text, text.indexOf(size), text.indexOf(size) + size.length());
        }
        if (spanSize == null) {
            textView.setText(String.format("%s\t%s", new Object[]{size, appStructItem.category_name}));
            return;
        }
        textView.setText(spanSize);
    }

    private void a(TextView textView, AppUpdateStructItem appStructItem, String size, String install) {
        SpannableString spanSize = null;
        if (appStructItem.isDownload) {
            spanSize = a(String.format("%s\t\t%s\t\t%s", new Object[]{size, getContext().getText(i.update_downloaded), install}), 0, size.length());
        } else if (appStructItem.patchSize > 0) {
            String patchSize = com.meizu.cloud.app.utils.g.a((double) appStructItem.patchSize, getResources().getStringArray(b.sizeUnit));
            spanSize = a(String.format("%s\t\t%s\t\t%s", new Object[]{size, patchSize, install}), 0, size.length());
        }
        if (spanSize == null) {
            textView.setText(String.format("%s\t\t%s", new Object[]{size, install}));
            return;
        }
        textView.setText(spanSize);
    }

    private SpannableString a(CharSequence rawString, int start, int end) {
        SpannableString spanString = new SpannableString(rawString);
        if (!TextUtils.isEmpty(rawString)) {
            spanString.setSpan(new StrikethroughSpan(), start, end, 33);
        }
        return spanString;
    }

    public void a(t viewController) {
        if (this.o == null) {
            this.o = viewController;
            if (this.o.c() == null) {
                this.o.a(new l());
            }
            this.p = b();
            this.q = c();
            a();
        }
    }

    private void a() {
        if (this.p) {
            LayoutParams layoutParams = (LayoutParams) this.n.getLayoutParams();
            layoutParams.bottomMargin += getResources().getDimensionPixelSize(d.rank_item_container);
            this.n.setLayoutParams(layoutParams);
        }
    }

    private boolean b() {
        if (this.o == null || this.o.c() == null) {
            return false;
        }
        return this.o.c().b;
    }

    private boolean c() {
        return getStyle() == 3 || getStyle() == 4;
    }

    private int getStyle() {
        if (this.o == null || this.o.c() == null || this.o.c().c == null) {
            return -1;
        }
        return this.o.c().c.getStyle();
    }
}
