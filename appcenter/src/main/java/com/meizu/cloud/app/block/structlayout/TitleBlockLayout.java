package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.meizu.cloud.app.block.structitem.TitleItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class TitleBlockLayout extends AbsBlockLayout<TitleItem> {
    TextView mTitle;
    View mTitleLayout;

    public TitleBlockLayout(Context context, TitleItem item) {
        super(context, item);
    }

    public View createView(Context context, TitleItem item) {
        View v = inflate(context, g.block_item_container_v2);
        this.mTitleLayout = this.mView.findViewById(f.titleLayout);
        this.mTitle = (TextView) this.mView.findViewById(16908308);
        this.mDivider = this.mView.findViewById(f.block_title_divider);
        return v;
    }

    public void updateView(Context context, final TitleItem item, t viewController, int position) {
        updateLayoutMargins(context, item);
        this.mTitle.setText(item.name);
        TextView moreText = (TextView) this.mView.findViewById(16908309);
        moreText.setVisibility(0);
        View arrow = this.mView.findViewById(f.more);
        if (!item.more || TextUtils.isEmpty(item.url)) {
            moreText.setVisibility(8);
            arrow.setVisibility(8);
            this.mTitleLayout.setOnClickListener(null);
        } else {
            moreText.setVisibility(0);
            arrow.setVisibility(8);
            this.mTitleLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (TitleBlockLayout.this.mOnChildClickListener != null) {
                        TitleBlockLayout.this.mOnChildClickListener.onMore(item);
                    }
                }
            });
            moreText.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (TitleBlockLayout.this.mOnChildClickListener != null) {
                        TitleBlockLayout.this.mOnChildClickListener.onMore(item);
                    }
                }
            });
        }
        if (!item.showDivider) {
            this.mDivider.setVisibility(8);
        }
    }

    protected void updateLayoutMargins(Context context, TitleItem item) {
        if (item.needExtraMarginTop) {
            ((LayoutParams) this.mTitleLayout.getLayoutParams()).topMargin = (int) context.getResources().getDimension(d.common_top_extra_margin);
            return;
        }
        ((LayoutParams) this.mTitleLayout.getLayoutParams()).topMargin = 0;
    }
}
