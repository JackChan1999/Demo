package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.meizu.cloud.app.block.requestitem.AppAdStructItem;
import com.meizu.cloud.app.block.structitem.AdvertiseItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class AdvertiseBlockLayout extends AbsBlockLayout<AdvertiseItem> {
    ImageView mImageView1;
    ImageView mImageView2;
    private TextView mTagView1;
    private TextView mTagView2;

    public AdvertiseBlockLayout(Context context, AdvertiseItem item) {
        super(context, item);
    }

    public View createView(Context context, AdvertiseItem item) {
        View v = inflate(context, g.advertise_layout_v2);
        this.mImageView1 = (ImageView) v.findViewById(f.image1);
        this.mImageView2 = (ImageView) v.findViewById(f.image2);
        this.mTagView1 = (TextView) v.findViewById(f.image_tag1);
        this.mTagView2 = (TextView) v.findViewById(f.image_tag2);
        return v;
    }

    public void updateView(Context context, AdvertiseItem item, t viewController, final int position) {
        if (item != null) {
            final AppAdStructItem appAdStructItem;
            int color;
            updateLayoutMargins(context, item);
            if (item.advertise1 != null) {
                this.mImageView1.setVisibility(0);
                AbstractStrcutItem item1 = item.advertise1;
                if (item1 != null && (item1 instanceof AppAdStructItem)) {
                    appAdStructItem = (AppAdStructItem) item1;
                    h.a(context, appAdStructItem.img_url, this.mImageView1, context.getResources().getDimensionPixelSize(d.block_rolling_play_item_image_radius), 0);
                    if (TextUtils.isEmpty(appAdStructItem.tag) || TextUtils.isEmpty(appAdStructItem.tag_color)) {
                        this.mTagView1.setVisibility(8);
                    } else {
                        this.mTagView1.setText(appAdStructItem.tag);
                        color = context.getResources().getColor(c.theme_color);
                        try {
                            color = Color.parseColor(appAdStructItem.tag_color);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        this.mTagView1.setBackgroundColor(color);
                        this.mTagView1.setVisibility(0);
                    }
                    this.mImageView1.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            if (AdvertiseBlockLayout.this.mOnChildClickListener != null) {
                                appAdStructItem.ad_wdm_pos = 1;
                                appAdStructItem.ad_wdm_type = 2;
                                AdvertiseBlockLayout.this.mOnChildClickListener.onClickAd(appAdStructItem, position, 0);
                            }
                        }
                    });
                }
            } else {
                this.mImageView1.setVisibility(4);
            }
            if (item.advertise2 != null) {
                this.mImageView2.setVisibility(0);
                AbstractStrcutItem item2 = item.advertise2;
                if (item2 != null && (item2 instanceof AppAdStructItem)) {
                    appAdStructItem = (AppAdStructItem) item2;
                    h.a(context, appAdStructItem.img_url, this.mImageView2, context.getResources().getDimensionPixelSize(d.block_rolling_play_item_image_radius), 0);
                    if (TextUtils.isEmpty(appAdStructItem.tag) || TextUtils.isEmpty(appAdStructItem.tag_color)) {
                        this.mTagView2.setVisibility(8);
                    } else {
                        this.mTagView2.setText(appAdStructItem.tag);
                        color = context.getResources().getColor(c.theme_color);
                        try {
                            color = Color.parseColor(appAdStructItem.tag_color);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        this.mTagView2.setBackgroundColor(color);
                        this.mTagView2.setVisibility(0);
                    }
                    this.mImageView2.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            if (AdvertiseBlockLayout.this.mOnChildClickListener != null) {
                                appAdStructItem.ad_wdm_pos = 2;
                                appAdStructItem.ad_wdm_type = 2;
                                AdvertiseBlockLayout.this.mOnChildClickListener.onClickAd(appAdStructItem, position, 1);
                            }
                        }
                    });
                    return;
                }
                return;
            }
            this.mImageView2.setVisibility(4);
        }
    }

    protected void updateLayoutMargins(Context context, AdvertiseItem item) {
        if (item.needExtraMarginTop) {
            this.mView.setPadding(this.mView.getPaddingLeft(), (int) context.getResources().getDimension(d.common_block_list_divider_height), this.mView.getPaddingRight(), this.mView.getPaddingBottom());
        } else {
            this.mView.setPadding(this.mView.getPaddingLeft(), 0, this.mView.getPaddingRight(), this.mView.getPaddingBottom());
        }
    }
}
