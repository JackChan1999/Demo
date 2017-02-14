package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.meizu.cloud.app.block.structitem.IconTitleItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class IconTitleBlockLayout extends AbsBlockLayout<IconTitleItem> {
    public ImageView mIconIv;

    public IconTitleBlockLayout(Context context, IconTitleItem iconTitleItem) {
        super(context, iconTitleItem);
    }

    public View createView(Context context, IconTitleItem item) {
        View view = inflate(context, g.block_icon_title_layout);
        this.mIconIv = (ImageView) view.findViewById(f.icon_title_imageview);
        return view;
    }

    public void updateView(Context context, IconTitleItem item, t viewController, int position) {
        if (item != null) {
            h.c(context, item.title_img, this.mIconIv);
        }
    }

    protected void updateLayoutMargins(Context context, IconTitleItem item) {
    }
}
