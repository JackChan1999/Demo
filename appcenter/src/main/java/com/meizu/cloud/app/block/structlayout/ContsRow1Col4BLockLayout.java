package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.meizu.cloud.app.block.requestitem.ContsRow1Col4StructItem;
import com.meizu.cloud.app.block.structitem.ContsRow1Col4Item;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class ContsRow1Col4BLockLayout extends AbsBlockLayout<ContsRow1Col4Item> {
    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private ImageView mImageView4;

    public ContsRow1Col4BLockLayout(Context context, ContsRow1Col4Item item) {
        super(context, item);
    }

    public View createView(Context context, ContsRow1Col4Item item) {
        View view = inflate(context, g.block_conts_row1col4_layout);
        this.mImageView1 = (ImageView) view.findViewById(f.conts_row1col4_image1);
        this.mImageView2 = (ImageView) view.findViewById(f.conts_row1col4_image2);
        this.mImageView3 = (ImageView) view.findViewById(f.conts_row1col4_image3);
        this.mImageView4 = (ImageView) view.findViewById(f.conts_row1col4_image4);
        return view;
    }

    public void updateView(Context context, ContsRow1Col4Item item, t viewController, int position) {
        if (item != null) {
            updateLayoutMargins(context, item);
            if (item.item1 != null) {
                updateItemView(context, this.mImageView1, item.item1, position, 0);
            } else {
                this.mImageView1.setVisibility(8);
            }
            if (item.item2 != null) {
                updateItemView(context, this.mImageView2, item.item2, position, 1);
            } else {
                this.mImageView2.setVisibility(8);
            }
            if (item.item3 != null) {
                updateItemView(context, this.mImageView3, item.item3, position, 2);
            } else {
                this.mImageView3.setVisibility(8);
            }
            if (item.item4 != null) {
                updateItemView(context, this.mImageView4, item.item4, position, 3);
                return;
            }
            this.mImageView4.setVisibility(8);
        }
    }

    private void updateItemView(Context context, ImageView imageView, final ContsRow1Col4StructItem contsItem, final int position, final int horPos) {
        imageView.setVisibility(0);
        h.a(context, contsItem.icon, imageView, context.getResources().getDimensionPixelSize(d.block_rolling_play_item_image_radius), 0);
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ContsRow1Col4BLockLayout.this.mOnChildClickListener.onClickConts(contsItem, null, position, horPos);
            }
        });
    }

    protected void updateLayoutMargins(Context context, ContsRow1Col4Item item) {
        if (item.needExtraMarginTop) {
            this.mView.setPadding(this.mView.getPaddingLeft(), (int) context.getResources().getDimension(d.common_block_list_divider_height), this.mView.getPaddingRight(), this.mView.getPaddingBottom());
        } else {
            this.mView.setPadding(this.mView.getPaddingLeft(), 0, this.mView.getPaddingRight(), this.mView.getPaddingBottom());
        }
    }
}
