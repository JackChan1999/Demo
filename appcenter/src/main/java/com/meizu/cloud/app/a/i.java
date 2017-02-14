package com.meizu.cloud.app.a;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.app.core.k;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.w;
import com.meizu.cloud.app.request.structitem.RecommendAppStructItem;
import com.meizu.cloud.app.request.structitem.SpecialConfig;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.widget.RankAppItemView;
import com.meizu.cloud.app.widget.c;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class i extends a<RecommendAppStructItem> {
    private SpecialConfig e;
    private Callback j;
    private Drawable k;

    public class a extends com.meizu.cloud.base.a.d.a {
        public FrameLayout a;
        public ImageView b;
        public TextView c;
        final /* synthetic */ i d;

        public a(i iVar, View itemView) {
            this.d = iVar;
            super(iVar, itemView);
        }
    }

    public void a(Callback callback) {
        this.j = callback;
    }

    public void a(ImageView imageView) {
        if (imageView != null) {
            Picasso.with(this.d.getApplicationContext()).cancelRequest(imageView);
            this.j = null;
        }
    }

    public i(FragmentActivity activity) {
        super(activity);
        this.h = true;
    }

    public i(FragmentActivity activity, t viewController) {
        this(activity);
        this.b = viewController;
        this.c = viewController.a();
    }

    public void a(SpecialConfig config) {
        this.e = config;
        if (config != null && config.colors != null) {
            w displayConfig = new w();
            displayConfig.a = this.e.colors.btn_color;
            displayConfig.b = -1;
            displayConfig.c = this.e.colors.btn_text_color;
            if (this.e.colors.bg_color == 0) {
                displayConfig.d = this.e.colors.btn_color;
            } else {
                displayConfig.d = this.e.colors.text_color;
            }
            displayConfig.e = this.e.colors.btn_color;
            displayConfig.f = this.e.colors.btn_color;
            displayConfig.a(true);
            this.b.a(displayConfig);
        }
    }

    public com.meizu.cloud.base.a.d.a a(ViewGroup parent) {
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(this.d).inflate(g.special_header_layout, parent, false);
        a holder = new a(this, rootView);
        holder.b = (ImageView) rootView.findViewById(f.image);
        holder.c = (TextView) rootView.findViewById(f.desc);
        holder.a = (FrameLayout) rootView.findViewById(f.image_layout);
        return holder;
    }

    public void a(com.meizu.cloud.base.a.d.a holder) {
        if ((holder instanceof a) && this.e != null) {
            a headerHolder = (a) holder;
            if (!TextUtils.isEmpty(this.e.banner)) {
                if (this.e.colors != null) {
                    Picasso.with(this.d.getApplicationContext()).load(this.e.banner).transform(new c(this.d.getResources().getDimensionPixelSize(d.special_list_item_corner_radius), 0)).placeholder(new ColorDrawable(this.e.colors.bg_color)).error(h.d).resizeDimen(d.special_header_image_width, d.special_header_image_height).centerCrop().into(headerHolder.b, this.j);
                    if (this.k != null) {
                        headerHolder.a.setBackground(this.k);
                    }
                } else {
                    h.c(this.d, this.e.banner, headerHolder.b);
                }
            }
            if (this.e.description != null) {
                headerHolder.c.setVisibility(0);
                headerHolder.c.setText(this.e.description);
            } else {
                headerHolder.c.setVisibility(8);
            }
            if (this.e.colors != null) {
                headerHolder.c.setTextColor(this.e.colors.des_text_color);
            }
            headerHolder.a.setTag("header_layout");
            headerHolder.b.setTag("header_image");
        }
    }

    public void a(Drawable drawable) {
        this.k = drawable;
    }

    public com.meizu.cloud.base.a.d.a a(ViewGroup parent, int viewType) {
        return new com.meizu.cloud.base.a.d.a(this, new RankAppItemView(this.d, g.special_item_view));
    }

    public void a(com.meizu.cloud.base.a.d.a holder, final int position) {
        RankAppItemView itemView = holder.itemView;
        final com.meizu.cloud.app.core.t.a dataItem = (RecommendAppStructItem) c(position);
        if (dataItem != null && !TextUtils.isEmpty(dataItem.icon) && !TextUtils.isEmpty(dataItem.name)) {
            itemView.setIconUrl(dataItem.icon);
            itemView.f.setText(dataItem.name);
            itemView.j.setText(dataItem.recommend_desc == null ? "" : dataItem.recommend_desc);
            this.b.a(dataItem, null, true, itemView.h);
            itemView.h.setTag(dataItem.package_name);
            itemView.h.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ i c;

                public void onClick(View v) {
                    dataItem.page_info = this.c.c;
                    dataItem.install_page = this.c.b.b();
                    dataItem.click_pos = position + 1;
                    this.c.b.a(new k(dataItem));
                }
            });
            if (this.e != null && this.e.colors != null) {
                int textColor = this.e.colors.text_color;
                itemView.f.setTextColor(textColor);
                int r = Color.red(textColor);
                int g = Color.green(textColor);
                int b = Color.blue(textColor);
                int descColor = Color.argb(127, r, g, b);
                int dividerColor = Color.argb(51, r, g, b);
                itemView.j.setTextColor(descColor);
                itemView.getDefaultDivider().setBackgroundColor(dividerColor);
            }
        }
    }
}
