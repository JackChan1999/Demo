package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.view.View;
import com.meizu.cloud.app.block.requestitem.AppAdStructItem;
import com.meizu.cloud.app.block.structitem.RollingPlayItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.widget.RollingPlayWidgetV2;
import com.meizu.cloud.app.widget.RollingPlayWidgetV2.a;
import com.meizu.cloud.b.a.d;
import java.util.ArrayList;

public class RollingPlayLayout extends AbsBlockLayout<RollingPlayItem> implements IStateableLayout {
    RollingPlayWidgetV2 mRollingPlayWidget;

    public RollingPlayLayout(Context context, RollingPlayItem item) {
        super(context, item);
    }

    public View createView(Context context, RollingPlayItem item) {
        if (item == null) {
            return null;
        }
        ArrayList list = item.rollingPlayItem.getSubItems();
        if (this.mRollingPlayWidget == null) {
            this.mRollingPlayWidget = new RollingPlayWidgetV2(context, list);
            this.mView = this.mRollingPlayWidget;
        }
        return this.mRollingPlayWidget;
    }

    public void updateView(Context context, RollingPlayItem item, t viewController, final int position) {
        if (this.mRollingPlayWidget != null) {
            this.mRollingPlayWidget.setOnAdItemClickListener(new a() {
                public void onClickAd(AppAdStructItem appAdStructItem) {
                    if (RollingPlayLayout.this.mOnChildClickListener != null) {
                        appAdStructItem.ad_wdm_pos = RollingPlayLayout.this.mRollingPlayWidget == null ? 0 : RollingPlayLayout.this.mRollingPlayWidget.getCurrentSimplePos();
                        appAdStructItem.ad_wdm_type = 1;
                        RollingPlayLayout.this.mOnChildClickListener.onClickAd(appAdStructItem, position, RollingPlayLayout.this.mRollingPlayWidget.getCurrentSimplePos());
                    }
                }
            });
            updateLayoutMargins(context, item);
            this.mRollingPlayWidget.a(context, item, item.rollingPlayItem.getSubItems());
        }
    }

    protected void updateLayoutMargins(Context context, RollingPlayItem item) {
        if (item.needExtraMarginTop) {
            this.mView.setPadding(this.mView.getPaddingLeft(), (int) context.getResources().getDimension(d.common_block_list_divider_height), this.mView.getPaddingRight(), this.mView.getPaddingBottom());
        } else {
            this.mView.setPadding(this.mView.getPaddingLeft(), 0, this.mView.getPaddingRight(), this.mView.getPaddingBottom());
        }
    }

    public int getCurrentPosition() {
        if (this.mRollingPlayWidget != null) {
            return this.mRollingPlayWidget.getCurrentPosition();
        }
        return 0;
    }

    public void start(int position) {
        if (this.mRollingPlayWidget != null) {
            this.mRollingPlayWidget.a(0);
        }
    }

    public void pause() {
        if (this.mRollingPlayWidget != null) {
            this.mRollingPlayWidget.a();
        }
    }

    public void resume() {
        if (this.mRollingPlayWidget != null) {
            this.mRollingPlayWidget.b();
        }
    }

    public void onMainPageScrollStateChanged(int arg0) {
        if (this.mRollingPlayWidget != null) {
            this.mRollingPlayWidget.b(arg0);
        }
    }

    public void onMainPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (this.mRollingPlayWidget != null) {
            this.mRollingPlayWidget.a(position, positionOffset, positionOffsetPixels);
        }
    }
}
