package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.meizu.cloud.app.block.requestitem.AdBigStructItem;
import com.meizu.cloud.app.block.structitem.AdBigItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class AdBigBlockLayout extends AbsBlockLayout<AdBigItem> {
    private ImageView mImageView;
    private TextView mTagView;

    public AdBigBlockLayout(Context context, AdBigItem item) {
        super(context, item);
    }

    public View createView(Context context, AdBigItem item) {
        View view = inflate(context, g.block_ad_big_layout);
        this.mImageView = (ImageView) view.findViewById(f.block_ad_big_iv);
        this.mTagView = (TextView) view.findViewById(f.image_tag);
        return view;
    }

    public void updateView(Context context, AdBigItem item, t viewController, final int position) {
        if (item != null && item.mAdBigStructItem != null) {
            updateLayoutMargins(context, item);
            final AdBigStructItem adBigStructItem = item.mAdBigStructItem;
            h.a(context, adBigStructItem.img_url, this.mImageView, context.getResources().getDimensionPixelSize(d.block_rolling_play_item_image_radius), 0);
            if (TextUtils.isEmpty(adBigStructItem.tag) || TextUtils.isEmpty(adBigStructItem.tag_color)) {
                this.mTagView.setVisibility(8);
            } else {
                this.mTagView.setText(adBigStructItem.tag);
                int color = context.getResources().getColor(c.theme_color);
                try {
                    color = Color.parseColor(adBigStructItem.tag_color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.mTagView.setBackgroundColor(color);
                this.mTagView.setVisibility(0);
            }
            this.mImageView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    AdBigBlockLayout.this.mOnChildClickListener.onClickAd(adBigStructItem, position, 0);
                }
            });
            if ("static_pic".equals(adBigStructItem.type)) {
                this.mImageView.setClickable(false);
            }
            if (viewController != null && viewController.d() != null) {
                if (position == 0 && viewController.d().blur_ad_bg != null) {
                    this.mView.setBackground(viewController.d().blur_ad_bg);
                }
                if (position > 0) {
                    this.mView.setBackground(viewController.d().default_ad_bg);
                }
            }
        }
    }

    protected void updateLayoutMargins(Context context, AdBigItem item) {
        if (item.mIsNeedMarginBottom) {
            this.mView.setPadding(this.mView.getPaddingLeft(), this.mView.getPaddingTop(), this.mView.getPaddingRight(), (int) context.getResources().getDimension(d.new_essential_ad_big_margin_bottom));
        } else {
            this.mView.setPadding(this.mView.getPaddingLeft(), this.mView.getPaddingTop(), this.mView.getPaddingRight(), 0);
        }
    }
}
