package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.meizu.cloud.app.block.requestitem.RollMessageStructItem;
import com.meizu.cloud.app.block.structitem.RollMessageItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.widget.TagView;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class RollMessageBlockLayout extends AbsBlockLayout<RollMessageItem> {
    public TextView mDescTv;
    public LinearLayout mRollingMsgView;
    public TextView mTitleTv;
    public ViewFlipper mViewFlipper;

    public RollMessageBlockLayout(Context context, RollMessageItem rollMessageItem) {
        super(context, rollMessageItem);
    }

    public View createView(Context context, RollMessageItem item) {
        View v = inflate(context, g.block_roll_message_parent_layout);
        this.mViewFlipper = (ViewFlipper) v.findViewById(f.rolling_message_viewswitcher);
        return v;
    }

    public void updateView(Context context, RollMessageItem item, t viewController, int position) {
        if (!(item == null || item.mRollMessageStructItem == null || item.mRollMessageStructItem.size() <= 0 || this.mViewFlipper.getChildCount() == item.mRollMessageStructItem.size())) {
            for (int i = 0; i < item.mRollMessageStructItem.size(); i++) {
                if (item.mRollMessageStructItem.get(i) != null) {
                    View v = LayoutInflater.from(context).inflate(g.block_roll_message_layout, null);
                    Context context2 = context;
                    updateRollingMsgItem(context2, (RollMessageStructItem) item.mRollMessageStructItem.get(i), (LinearLayout) v.findViewById(f.rolling_message_view), (TextView) v.findViewById(f.roll_message_title_name), (TextView) v.findViewById(f.roll_message_desc), position, i);
                    this.mViewFlipper.addView(v, i);
                }
            }
            this.mViewFlipper.setDisplayedChild(0);
            if (this.mViewFlipper.getChildCount() > 1) {
                this.mViewFlipper.setAutoStart(true);
                this.mViewFlipper.setFlipInterval(5000);
                this.mViewFlipper.startFlipping();
            } else {
                this.mViewFlipper.setAutoStart(false);
            }
        }
        if (!this.mViewFlipper.isFlipping()) {
            this.mViewFlipper.startFlipping();
        }
    }

    protected void updateLayoutMargins(Context context, RollMessageItem item) {
    }

    private void updateRollingMsgItem(Context context, final RollMessageStructItem rollMessageStructItem, LinearLayout rollingMsgView, TextView titleTx, TextView desTx, final int position, final int horPos) {
        TagView.a(titleTx, rollMessageStructItem.tag, rollMessageStructItem.tag_color);
        desTx.setText(rollMessageStructItem.message);
        rollingMsgView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RollMessageBlockLayout.this.mOnChildClickListener.onClickConts(rollMessageStructItem, null, position, horPos);
            }
        });
    }
}
