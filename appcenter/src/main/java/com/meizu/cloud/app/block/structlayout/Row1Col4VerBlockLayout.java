package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.app.block.requestitem.SubpagePageConfigsInfo;
import com.meizu.cloud.app.block.structitem.Row1Col4AppVerItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.t.a;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class Row1Col4VerBlockLayout extends AbsBlockLayout<Row1Col4AppVerItem> {
    private View mDividerBgView;
    private View mDividerLine;
    private LinearLayout mItem1;
    private CirProButton mItem1Btn;
    private ImageView mItem1Iv;
    private TextView mItem1Tv;
    private LinearLayout mItem2;
    private CirProButton mItem2Btn;
    private ImageView mItem2Iv;
    private TextView mItem2Tv;
    private LinearLayout mItem3;
    private CirProButton mItem3Btn;
    private ImageView mItem3Iv;
    private TextView mItem3Tv;
    private LinearLayout mItem4;
    private CirProButton mItem4Btn;
    private ImageView mItem4Iv;
    private TextView mItem4Tv;

    public Row1Col4VerBlockLayout(Context context, Row1Col4AppVerItem item) {
        super(context, item);
    }

    public View createView(Context context, Row1Col4AppVerItem item) {
        View view = inflate(context, g.block_row1_col4_layout);
        this.mItem1 = (LinearLayout) view.findViewById(f.block_row1col4_item1);
        this.mItem2 = (LinearLayout) view.findViewById(f.block_row1col4_item2);
        this.mItem3 = (LinearLayout) view.findViewById(f.block_row1col4_item3);
        this.mItem4 = (LinearLayout) view.findViewById(f.block_row1col4_item4);
        this.mItem1Iv = (ImageView) this.mItem1.findViewById(f.row1_col4_item_icon);
        this.mItem2Iv = (ImageView) this.mItem2.findViewById(f.row1_col4_item_icon);
        this.mItem3Iv = (ImageView) this.mItem3.findViewById(f.row1_col4_item_icon);
        this.mItem4Iv = (ImageView) this.mItem4.findViewById(f.row1_col4_item_icon);
        this.mItem1Tv = (TextView) this.mItem1.findViewById(f.row1col4_item_appname);
        this.mItem2Tv = (TextView) this.mItem2.findViewById(f.row1col4_item_appname);
        this.mItem3Tv = (TextView) this.mItem3.findViewById(f.row1col4_item_appname);
        this.mItem4Tv = (TextView) this.mItem4.findViewById(f.row1col4_item_appname);
        this.mItem1Btn = (CirProButton) this.mItem1.findViewById(f.btnInstall);
        this.mItem2Btn = (CirProButton) this.mItem2.findViewById(f.btnInstall);
        this.mItem3Btn = (CirProButton) this.mItem3.findViewById(f.btnInstall);
        this.mItem4Btn = (CirProButton) this.mItem4.findViewById(f.btnInstall);
        this.mDividerLine = view.findViewById(f.block_divider);
        this.mDividerBgView = view.findViewById(f.list_last_bg_divider_view);
        return view;
    }

    public void updateView(Context context, Row1Col4AppVerItem item, t viewController, int position) {
        if (item != null) {
            if (item.mAppStructItem1 != null) {
                updateItemView(context, item.mAppStructItem1, this.mItem1, this.mItem1Iv, this.mItem1Tv, this.mItem1Btn, viewController, position, 0);
            } else {
                this.mItem1.setVisibility(4);
            }
            if (item.mAppStructItem2 != null) {
                updateItemView(context, item.mAppStructItem2, this.mItem2, this.mItem2Iv, this.mItem2Tv, this.mItem2Btn, viewController, position, 1);
            } else {
                this.mItem2.setVisibility(4);
            }
            if (item.mAppStructItem3 != null) {
                updateItemView(context, item.mAppStructItem3, this.mItem3, this.mItem3Iv, this.mItem3Tv, this.mItem3Btn, viewController, position, 2);
            } else {
                this.mItem3.setVisibility(4);
            }
            if (item.mAppStructItem4 != null) {
                updateItemView(context, item.mAppStructItem4, this.mItem4, this.mItem4Iv, this.mItem4Tv, this.mItem4Btn, viewController, position, 3);
            } else {
                this.mItem4.setVisibility(4);
            }
            if (!item.mIsLastItemInAppBlock) {
                this.mDividerLine.setVisibility(0);
                this.mDividerBgView.setVisibility(8);
            } else if (item.mIsHideDividerView) {
                this.mDividerLine.setVisibility(8);
                this.mDividerBgView.setVisibility(8);
            } else {
                this.mDividerLine.setVisibility(8);
                this.mDividerBgView.setVisibility(0);
            }
        }
    }

    private void updateItemView(Context context, final AppStructItem appStructItem, LinearLayout itemView, ImageView iv, TextView tv, CirProButton btn, t viewController, int position, int horPos) {
        itemView.setVisibility(0);
        h.a(context, appStructItem.icon, iv);
        tv.setText(appStructItem.name);
        final int i = position;
        final int i2 = horPos;
        itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Row1Col4VerBlockLayout.this.mOnChildClickListener.onClickApp(appStructItem, i, i2);
            }
        });
        viewController.a((a) appStructItem, null, true, btn);
        btn.setTag(appStructItem.package_name);
        final AppStructItem appStructItem2 = appStructItem;
        final CirProButton cirProButton = btn;
        final int i3 = position;
        final int i4 = horPos;
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Row1Col4VerBlockLayout.this.mOnChildClickListener.onDownload(appStructItem2, cirProButton, i3, i4);
            }
        });
        SubpagePageConfigsInfo configsInfo = viewController.d();
        if (configsInfo != null) {
            tv.setTextColor(configsInfo.des_color);
            this.mDividerLine.setBackgroundColor(configsInfo.divider_line_color);
        }
    }

    protected void updateLayoutMargins(Context context, Row1Col4AppVerItem item) {
    }
}
