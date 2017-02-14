package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.meizu.cloud.app.block.structitem.CategoryGridItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.request.structitem.CategoryStructItem;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class CategoryGridLayout extends AbsBlockLayout<CategoryGridItem> {
    private GridLayout mGridLayout;
    private ViewGroup mParent;

    public CategoryGridLayout(ViewGroup parent) {
        this.mParent = parent;
    }

    public View createView(Context context, CategoryGridItem item) {
        View rootView = LayoutInflater.from(context).inflate(g.block_grid_layout, this.mParent, false);
        this.mGridLayout = (GridLayout) rootView.findViewById(f.gridlayout);
        return rootView;
    }

    public void updateView(Context context, CategoryGridItem item, t viewController, int position) {
        this.mGridLayout.removeAllViews();
        int marginLeft = context.getResources().getDimensionPixelSize(d.category_card_padding_left);
        int cardWidth = (((this.mParent.getWidth() - marginLeft) - context.getResources().getDimensionPixelSize(d.category_card_padding_right)) - (context.getResources().getDimensionPixelSize(d.category_card_margin) * 4)) / 2;
        for (int i = 0; i < item.structItemList.size(); i++) {
            final int horPosition = i;
            final CategoryStructItem structItem = (CategoryStructItem) item.structItemList.get(i);
            CardView cardLayout = (CardView) LayoutInflater.from(context).inflate(g.block_grid_item, this.mGridLayout, false);
            ImageView imgBg = (ImageView) cardLayout.findViewById(f.image);
            LayoutParams layoutParams = (LayoutParams) imgBg.getLayoutParams();
            layoutParams.width = cardWidth;
            imgBg.setLayoutParams(layoutParams);
            if (!TextUtils.isEmpty(structItem.bg)) {
                h.c(context, structItem.bg, imgBg);
            }
            if (!TextUtils.isEmpty(structItem.title)) {
                TextView title = (TextView) cardLayout.findViewById(f.text);
                title.setText(structItem.title);
                title.setTextColor(-1);
                title.setMaxWidth(cardWidth);
                if (structItem.title_color != 0) {
                    imgBg.setImageDrawable(new ColorDrawable(structItem.title_color));
                }
            }
            final int i2 = position;
            cardLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (CategoryGridLayout.this.mOnChildClickListener != null) {
                        CategoryGridLayout.this.mOnChildClickListener.onClickConts(structItem, null, i2, horPosition);
                    }
                }
            });
            this.mGridLayout.addView(cardLayout);
        }
    }

    protected void updateLayoutMargins(Context context, CategoryGridItem item) {
    }
}
