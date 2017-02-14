package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.meizu.cloud.app.block.structitem.RecommendAppItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.t.a;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.app.widget.TagView;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class RecommendBlcokLayout extends AbsBlockLayout<RecommendAppItem> {
    TextView mAppInstallCount;
    TextView mAppSizeDesc;
    CirProButton mBtnInstall;
    View mDividerBgView;
    View mDividerLine;
    ImageView mIcon;
    TextView mRecommendDesc;
    TagView mTagView;
    TextView mText;

    public RecommendBlcokLayout(Context context, RecommendAppItem item) {
        super(context, item);
    }

    public View createView(Context context, RecommendAppItem item) {
        View v = inflate(context, g.block_row1_col1_layout_v2);
        this.mIcon = (ImageView) v.findViewById(f.icon);
        this.mText = (TextView) v.findViewById(f.text);
        this.mAppSizeDesc = (TextView) v.findViewById(f.app_size);
        this.mAppInstallCount = (TextView) v.findViewById(f.app_install_count);
        this.mRecommendDesc = (TextView) v.findViewById(f.recommend_desc);
        this.mTagView = (TagView) v.findViewById(f.app_tagview);
        this.mBtnInstall = (CirProButton) v.findViewById(f.btnInstall);
        this.mDividerLine = v.findViewById(f.block_divider);
        this.mDividerBgView = v.findViewById(f.list_last_bg_divider_view);
        return v;
    }

    public void updateView(Context context, RecommendAppItem item, t viewController, final int position) {
        item.app.click_pos = position;
        final a recommendStructItem = item.app;
        h.a(context, recommendStructItem.icon, this.mIcon);
        this.mText.setText(recommendStructItem.name);
        com.meizu.cloud.app.utils.g.a(context, (AppUpdateStructItem) recommendStructItem, this.mAppSizeDesc);
        com.meizu.cloud.app.utils.g.b(context, recommendStructItem, this.mAppInstallCount);
        this.mRecommendDesc.setText(recommendStructItem.recommend_desc);
        this.mView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (RecommendBlcokLayout.this.mOnChildClickListener != null && recommendStructItem != null) {
                    RecommendBlcokLayout.this.mOnChildClickListener.onClickApp(recommendStructItem, position, 0);
                }
            }
        });
        this.mBtnInstall.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (RecommendBlcokLayout.this.mOnChildClickListener != null && recommendStructItem != null) {
                    RecommendBlcokLayout.this.mOnChildClickListener.onDownload(recommendStructItem, view, position, 0);
                }
            }
        });
        this.mBtnInstall.setTag(recommendStructItem.package_name);
        viewController.a(recommendStructItem, null, true, this.mBtnInstall);
        this.mTagView.setTags(recommendStructItem.name, recommendStructItem.tags);
        this.mTagView.setVisibility(0);
        if (item.mIsLastItemInAppBlock) {
            this.mDividerLine.setVisibility(8);
            this.mDividerBgView.setVisibility(0);
            return;
        }
        this.mDividerLine.setVisibility(0);
        this.mDividerBgView.setVisibility(8);
    }

    protected void updateLayoutMargins(Context context, RecommendAppItem item) {
    }
}
