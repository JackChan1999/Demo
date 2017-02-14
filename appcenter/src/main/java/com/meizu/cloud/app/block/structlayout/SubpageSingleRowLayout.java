package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.app.block.requestitem.SubpagePageConfigsInfo;
import com.meizu.cloud.app.block.structitem.SingleRowAppItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.t.a;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.app.widget.TagView;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class SubpageSingleRowLayout extends AbsBlockLayout<SingleRowAppItem> {
    public CirProButton btnInstall;
    public View mDivider;
    public ImageView mIconImageView;
    private LinearLayout mRootLayout;
    public TagView mTagView;
    public TextView mTxtDesc;
    public TextView mTxtInstall;
    public TextView mTxtSize;
    public TextView mTxtTitle;

    public SubpageSingleRowLayout(Context context, SingleRowAppItem item) {
        super(context, item);
    }

    public View createView(Context context, SingleRowAppItem item) {
        View v = inflate(context, g.subpage_single_item_view);
        this.mRootLayout = (LinearLayout) v.findViewById(f.linearLayout);
        this.mIconImageView = (ImageView) v.findViewById(f.icon);
        this.mTxtTitle = (TextView) v.findViewById(f.txt_title);
        this.mTagView = (TagView) v.findViewById(f.tagView);
        this.mTxtDesc = (TextView) v.findViewById(f.txt_desc);
        this.mTxtSize = (TextView) v.findViewById(f.txt_size);
        this.mTxtInstall = (TextView) v.findViewById(f.txt_install);
        this.btnInstall = (CirProButton) v.findViewById(f.btnInstall);
        this.mDivider = v.findViewById(f.divider);
        return v;
    }

    public void updateView(Context context, SingleRowAppItem singleRowAppItem, t viewController, final int position) {
        if (singleRowAppItem != null) {
            singleRowAppItem.app.click_pos = position;
            final a item = singleRowAppItem.app;
            this.mRootLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (SubpageSingleRowLayout.this.mOnChildClickListener != null) {
                        SubpageSingleRowLayout.this.mOnChildClickListener.onClickApp(item, position, 0);
                    }
                }
            });
            viewController.a(item, null, true, this.btnInstall);
            this.btnInstall.setTag(item.package_name);
            this.btnInstall.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (SubpageSingleRowLayout.this.mOnChildClickListener != null) {
                        SubpageSingleRowLayout.this.mOnChildClickListener.onDownload(item, SubpageSingleRowLayout.this.btnInstall, position, 0);
                    }
                }
            });
            bindView(context, item, viewController);
            if (singleRowAppItem.mIsLastItemInAppBlock) {
                this.mDivider.setVisibility(8);
            } else {
                this.mDivider.setVisibility(0);
            }
        }
    }

    public void bindView(Context context, AppUpdateStructItem appStructItem, t viewController) {
        if (this.mIconImageView != null) {
            h.a(context, appStructItem.icon, this.mIconImageView);
        }
        this.mTxtTitle.setText(appStructItem.name);
        this.mTagView.setTags(appStructItem.name, appStructItem.tags);
        this.mTagView.setVisibility(0);
        this.mTxtDesc.setVisibility(0);
        if (!"recommend".equals(appStructItem.style) || TextUtils.isEmpty(appStructItem.recommend_desc)) {
            com.meizu.cloud.app.utils.g.a(context, appStructItem, this.mTxtSize);
            com.meizu.cloud.app.utils.g.b(context, appStructItem, this.mTxtInstall);
            this.mTxtSize.setVisibility(0);
            this.mTxtInstall.setVisibility(0);
            this.mTxtDesc.setVisibility(8);
        } else {
            this.mTxtDesc.setText(appStructItem.recommend_desc);
            this.mTxtDesc.setVisibility(0);
            this.mTxtSize.setVisibility(8);
            this.mTxtInstall.setVisibility(8);
        }
        SubpagePageConfigsInfo configsInfo = viewController.d();
        if (configsInfo != null) {
            this.mTxtTitle.setTextColor(configsInfo.des_color);
            this.mTxtSize.setTextColor(configsInfo.recom_des_common);
            this.mTxtInstall.setTextColor(configsInfo.recom_des_common);
            this.mDivider.setBackgroundColor(configsInfo.divider_line_color);
        }
    }

    protected void updateLayoutMargins(Context context, SingleRowAppItem item) {
    }
}
