package com.meizu.cloud.app.a;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.meizu.cloud.app.request.structitem.SpecialStructItem;
import com.meizu.cloud.app.widget.c;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.squareup.picasso.Picasso;

public class j extends f<SpecialStructItem> {

    public class a extends com.meizu.cloud.base.a.d.a {
        public ImageView a;
        final /* synthetic */ j b;

        public a(j jVar, View itemView) {
            this.b = jVar;
            super(jVar, itemView);
        }
    }

    public j(Context context) {
        super(context);
    }

    public com.meizu.cloud.base.a.d.a a(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.d).inflate(g.special_list_item_layout, parent, false);
        a holder = new a(this, view);
        holder.a = (ImageView) view.findViewById(f.image);
        return holder;
    }

    public void a(com.meizu.cloud.base.a.d.a holder, int position) {
        a specialHolder = (a) holder;
        SpecialStructItem data = (SpecialStructItem) c(position);
        String url = null;
        if ("special".equals(data.type) || "specials".equals(data.type)) {
            url = data.logo;
        } else if (PushConstants.INTENT_ACTIVITY_NAME.equals(data.type) || "activities".equals(data.type)) {
            url = data.publicity_img;
        }
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(this.d.getApplicationContext()).load(url).transform(new c(this.d.getResources().getDimensionPixelSize(d.special_list_item_corner_radius), 0)).placeholder(e.image_background).error(e.image_background).resizeDimen(d.special_list_item_image_width, d.special_header_image_height).centerCrop().into(specialHolder.a);
        }
    }
}
