package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.app.block.requestitem.SubpagePageConfigsInfo;
import com.meizu.cloud.app.block.structitem.Row1Col3AppVerItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.t.a;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.b.a.b;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;

public class Row1Col3VerBlockLayout extends AbsBlockLayout<Row1Col3AppVerItem> {
    TextView mAppSizeTv1;
    TextView mAppSizeTv2;
    TextView mAppSizeTv3;
    private View mDividerBgView;
    private View mDividerLine;
    ImageView mIconIv1;
    ImageView mIconIv2;
    ImageView mIconIv3;
    CirProButton mInstallBtn1;
    CirProButton mInstallBtn2;
    CirProButton mInstallBtn3;
    LinearLayout mLayoutView;
    TextView mNameTv1;
    TextView mNameTv2;
    TextView mNameTv3;
    LinearLayout mVerItem1;
    LinearLayout mVerItem2;
    LinearLayout mVerItem3;

    public Row1Col3VerBlockLayout(Context context, Row1Col3AppVerItem row1Col3AppVerItem) {
        super(context, row1Col3AppVerItem);
    }

    public View createView(Context context, Row1Col3AppVerItem item) {
        View v = inflate(context, g.block_row1_col3_ver_layout);
        this.mLayoutView = (LinearLayout) v.findViewById(f.row1col3_layout_view);
        this.mVerItem1 = (LinearLayout) v.findViewById(f.block_row1col3_veritem1);
        this.mVerItem2 = (LinearLayout) v.findViewById(f.block_row1col3_veritem2);
        this.mVerItem3 = (LinearLayout) v.findViewById(f.block_row1col3_veritem3);
        this.mIconIv1 = (ImageView) this.mVerItem1.findViewById(f.row1_col3_veritem_appicon);
        this.mIconIv2 = (ImageView) this.mVerItem2.findViewById(f.row1_col3_veritem_appicon);
        this.mIconIv3 = (ImageView) this.mVerItem3.findViewById(f.row1_col3_veritem_appicon);
        this.mNameTv1 = (TextView) this.mVerItem1.findViewById(f.row1_col3_veritem_appname);
        this.mNameTv2 = (TextView) this.mVerItem2.findViewById(f.row1_col3_veritem_appname);
        this.mNameTv3 = (TextView) this.mVerItem3.findViewById(f.row1_col3_veritem_appname);
        this.mAppSizeTv1 = (TextView) this.mVerItem1.findViewById(f.row1_col3_veritem_appsize);
        this.mAppSizeTv2 = (TextView) this.mVerItem2.findViewById(f.row1_col3_veritem_appsize);
        this.mAppSizeTv3 = (TextView) this.mVerItem3.findViewById(f.row1_col3_veritem_appsize);
        this.mInstallBtn1 = (CirProButton) this.mVerItem1.findViewById(f.btnInstall);
        this.mInstallBtn2 = (CirProButton) this.mVerItem2.findViewById(f.btnInstall);
        this.mInstallBtn3 = (CirProButton) this.mVerItem3.findViewById(f.btnInstall);
        this.mDividerLine = v.findViewById(f.block_divider);
        this.mDividerBgView = v.findViewById(f.list_last_bg_divider_view);
        return v;
    }

    public void updateView(Context context, Row1Col3AppVerItem item, t viewController, int position) {
        if (item != null) {
            if (item.mAppStructItem1 != null) {
                updateItemView(context, this.mVerItem1, this.mIconIv1, this.mNameTv1, this.mAppSizeTv1, this.mInstallBtn1, item.mAppStructItem1, viewController, position, 0);
            } else {
                this.mVerItem1.setVisibility(4);
            }
            if (item.mAppStructItem2 != null) {
                updateItemView(context, this.mVerItem2, this.mIconIv2, this.mNameTv2, this.mAppSizeTv2, this.mInstallBtn2, item.mAppStructItem2, viewController, position, 1);
            } else {
                this.mVerItem2.setVisibility(4);
            }
            if (item.mAppStructItem3 != null) {
                updateItemView(context, this.mVerItem3, this.mIconIv3, this.mNameTv3, this.mAppSizeTv3, this.mInstallBtn3, item.mAppStructItem3, viewController, position, 2);
            } else {
                this.mVerItem3.setVisibility(4);
            }
            if (item.mIsLastItemInAppBlock || item.mIsLastItemInGameBlock) {
                this.mDividerLine.setVisibility(8);
                if (item.mIsLastItemInAppBlock) {
                    this.mDividerBgView.setVisibility(0);
                    return;
                }
                return;
            }
            this.mDividerLine.setVisibility(0);
            this.mDividerBgView.setVisibility(8);
        }
    }

    private void updateItemView(Context context, LinearLayout verItem, ImageView iconIv, TextView nameTv, TextView appsize, CirProButton installBtn, AppStructItem appStructItem, t viewController, int position, int horPos) {
        verItem.setVisibility(0);
        h.c(context, appStructItem.icon, iconIv);
        nameTv.setText(appStructItem.name);
        if (x.b(context)) {
            appsize.setText(com.meizu.cloud.app.utils.g.a((double) appStructItem.size, context.getResources().getStringArray(b.sizeUnit)));
            this.mLayoutView.setPadding(context.getResources().getDimensionPixelSize(d.block_layout_paddingleft), 0, context.getResources().getDimensionPixelSize(d.block_layout_paddingright), 0);
        } else {
            TextView textView = appsize;
            textView.setText(String.format(context.getString(i.install_counts_only), new Object[]{com.meizu.cloud.app.utils.g.a(context, appStructItem.download_count)}));
        }
        viewController.a((a) appStructItem, null, true, installBtn);
        installBtn.setTag(appStructItem.package_name);
        final AppStructItem appStructItem2 = appStructItem;
        final CirProButton cirProButton = installBtn;
        final int i = position;
        final int i2 = horPos;
        installBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Row1Col3VerBlockLayout.this.mOnChildClickListener.onDownload(appStructItem2, cirProButton, i, i2);
            }
        });
        final AppStructItem appStructItem3 = appStructItem;
        final int i3 = position;
        final int i4 = horPos;
        verItem.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Row1Col3VerBlockLayout.this.mOnChildClickListener.onClickApp(appStructItem3, i3, i4);
            }
        });
        SubpagePageConfigsInfo configsInfo = viewController.d();
        if (configsInfo != null) {
            nameTv.setTextColor(configsInfo.des_color);
            appsize.setTextColor(configsInfo.recom_des_common);
            this.mDividerLine.setBackgroundColor(configsInfo.divider_line_color);
        }
    }

    protected void updateLayoutMargins(Context context, Row1Col3AppVerItem item) {
    }
}
