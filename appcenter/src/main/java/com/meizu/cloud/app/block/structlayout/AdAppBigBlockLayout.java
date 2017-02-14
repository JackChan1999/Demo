package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.app.block.structitem.AdAppBigItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.t.a;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class AdAppBigBlockLayout extends AbsBlockLayout<AdAppBigItem> {
    private LinearLayout mAdAppBigView;
    private TextView mAppNameTv;
    private TextView mAppRecomTv;
    private ImageView mBigImageView;
    private ImageView mIconImageView;
    private CirProButton mInstallBtn;
    private TextView mTagView;

    public AdAppBigBlockLayout(Context context, AdAppBigItem item) {
        super(context, item);
    }

    public View createView(Context context, AdAppBigItem item) {
        View view = inflate(context, g.block_ad_app_big_layout);
        this.mAdAppBigView = (LinearLayout) view.findViewById(f.block_ad_app_big_view);
        this.mBigImageView = (ImageView) view.findViewById(f.block_ad_app_big_iv);
        this.mIconImageView = (ImageView) view.findViewById(f.block_ad_app_big_icon);
        this.mAppNameTv = (TextView) view.findViewById(f.ad_app_name);
        this.mAppRecomTv = (TextView) view.findViewById(f.ad_app_recom);
        this.mInstallBtn = (CirProButton) view.findViewById(f.btnInstall);
        this.mTagView = (TextView) view.findViewById(f.image_tag);
        return view;
    }

    public void updateView(Context context, AdAppBigItem item, t viewController, final int position) {
        if (item != null && item.mAppAdBigStructItem != null) {
            final a appAdBigStructItem = item.mAppAdBigStructItem;
            if (x.b(context)) {
                h.c(context, appAdBigStructItem.back_image, this.mBigImageView);
            } else {
                h.c(context, appAdBigStructItem.img_url, this.mBigImageView);
            }
            h.a(context, appAdBigStructItem.icon, this.mIconImageView);
            if (TextUtils.isEmpty(appAdBigStructItem.tag) || TextUtils.isEmpty(appAdBigStructItem.tag_color)) {
                this.mTagView.setVisibility(8);
            } else {
                this.mTagView.setText(appAdBigStructItem.tag);
                int color = context.getResources().getColor(c.theme_color);
                try {
                    color = Color.parseColor(appAdBigStructItem.tag_color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.mTagView.setBackgroundColor(color);
                this.mTagView.setVisibility(0);
            }
            this.mAppNameTv.setText(appAdBigStructItem.name);
            this.mAppRecomTv.setText(appAdBigStructItem.recommend_desc);
            viewController.a(appAdBigStructItem, null, true, this.mInstallBtn);
            this.mInstallBtn.setTag(appAdBigStructItem.package_name);
            this.mInstallBtn.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    AdAppBigBlockLayout.this.mOnChildClickListener.onDownload(appAdBigStructItem, AdAppBigBlockLayout.this.mInstallBtn, position, 0);
                }
            });
            this.mAdAppBigView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    AdAppBigBlockLayout.this.mOnChildClickListener.onClickApp(appAdBigStructItem, position, 0);
                }
            });
        }
    }

    protected void updateLayoutMargins(Context context, AdAppBigItem item) {
    }
}
