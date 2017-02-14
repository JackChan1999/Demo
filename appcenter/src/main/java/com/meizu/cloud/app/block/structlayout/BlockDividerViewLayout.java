package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.view.View;
import com.meizu.cloud.app.block.structitem.BlockDividerViewItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class BlockDividerViewLayout extends AbsBlockLayout<BlockDividerViewItem> {
    private View mDividerView;

    public BlockDividerViewLayout(Context context, BlockDividerViewItem blockDividerViewItem) {
        super(context, blockDividerViewItem);
    }

    public View createView(Context context, BlockDividerViewItem item) {
        View v = inflate(context, g.block_divider_view_layout);
        this.mDividerView = v.findViewById(f.list_last_bg_divider_view);
        return v;
    }

    public void updateView(Context context, BlockDividerViewItem item, t viewController, int position) {
        if (x.a(context)) {
            this.mDividerView.setVisibility(0);
        } else {
            this.mDividerView.setVisibility(8);
        }
    }

    protected void updateLayoutMargins(Context context, BlockDividerViewItem item) {
    }
}
