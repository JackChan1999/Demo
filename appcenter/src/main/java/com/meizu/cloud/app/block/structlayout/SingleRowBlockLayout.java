package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.meizu.cloud.app.block.structitem.SingleRowAppItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.t.a;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.app.widget.TagView;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class SingleRowBlockLayout extends AbsBlockLayout<SingleRowAppItem> {
    public CirProButton btnInstall;
    public View mDivider;
    public ImageView mIconImageView;
    public View mLastItemBgDivider;
    private RelativeLayout mRootLayout;
    public TagView mTagView;
    public LinearLayout mTitlelayout;
    public TextView mTxtDesc;
    public TextView mTxtInstall;
    public TextView mTxtSize;
    public TextView mTxtTitle;

    public SingleRowBlockLayout(Context context, SingleRowAppItem item) {
        super(context, item);
    }

    public View createView(Context context, SingleRowAppItem item) {
        View v = inflate(context, g.block_single_row_view);
        this.mRootLayout = (RelativeLayout) v.findViewById(f.relativeLayout);
        this.mIconImageView = (ImageView) v.findViewById(f.icon);
        this.mTitlelayout = (LinearLayout) v.findViewById(f.layout_title);
        this.mTxtTitle = (TextView) v.findViewById(f.txt_title);
        this.mTagView = (TagView) v.findViewById(f.tagView);
        this.mTxtDesc = (TextView) v.findViewById(f.txt_desc);
        this.mTxtSize = (TextView) v.findViewById(f.txt_size);
        this.mTxtInstall = (TextView) v.findViewById(f.txt_install);
        this.btnInstall = (CirProButton) v.findViewById(f.btnInstall);
        this.mDivider = v.findViewById(f.divider);
        this.mLastItemBgDivider = v.findViewById(f.list_last_bg_divider_view);
        return v;
    }

    public void updateView(Context context, SingleRowAppItem singleRowAppItem, t viewController, final int position) {
        if (singleRowAppItem != null) {
            singleRowAppItem.app.click_pos = position;
            final a item = singleRowAppItem.app;
            this.mRootLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (SingleRowBlockLayout.this.mOnChildClickListener != null) {
                        SingleRowBlockLayout.this.mOnChildClickListener.onClickApp(item, position, 0);
                    }
                }
            });
            viewController.a(item, null, true, this.btnInstall);
            this.btnInstall.setTag(item.package_name);
            this.btnInstall.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (SingleRowBlockLayout.this.mOnChildClickListener != null) {
                        SingleRowBlockLayout.this.mOnChildClickListener.onDownload(item, SingleRowBlockLayout.this.btnInstall, position, 0);
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
            this.mTxtDesc.setText(appStructItem.category_name);
        } else {
            this.mTxtDesc.setText(appStructItem.recommend_desc);
        }
        com.meizu.cloud.app.utils.g.a(context, appStructItem, this.mTxtSize);
        com.meizu.cloud.app.utils.g.b(context, appStructItem, this.mTxtInstall);
    }

    protected void updateLayoutMargins(Context context, SingleRowAppItem item) {
    }
}
