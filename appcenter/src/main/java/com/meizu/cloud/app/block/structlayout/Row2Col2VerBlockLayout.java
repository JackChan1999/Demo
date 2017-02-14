package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.app.block.structitem.Row2Col2AppVerItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.t.a;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.b.a.b;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class Row2Col2VerBlockLayout extends AbsBlockLayout<Row2Col2AppVerItem> {
    TextView mAppSizeTv1;
    TextView mAppSizeTv2;
    TextView mAppSizeTv3;
    TextView mAppSizeTv4;
    private View mDividerLine;
    ImageView mIconIv1;
    ImageView mIconIv2;
    ImageView mIconIv3;
    ImageView mIconIv4;
    CirProButton mInstallBtn1;
    CirProButton mInstallBtn2;
    CirProButton mInstallBtn3;
    CirProButton mInstallBtn4;
    TextView mNameTv1;
    TextView mNameTv2;
    TextView mNameTv3;
    TextView mNameTv4;
    LinearLayout mVerItem1;
    LinearLayout mVerItem2;
    LinearLayout mVerItem3;
    LinearLayout mVerItem4;

    public Row2Col2VerBlockLayout(Context context, Row2Col2AppVerItem row2Col2AppVerItem) {
        super(context, row2Col2AppVerItem);
    }

    public View createView(Context context, Row2Col2AppVerItem item) {
        View v = inflate(context, g.block_row2_col2_ver_layout);
        this.mVerItem1 = (LinearLayout) v.findViewById(f.block_row1col2_veritem1);
        this.mVerItem2 = (LinearLayout) v.findViewById(f.block_row1col2_veritem2);
        this.mVerItem3 = (LinearLayout) v.findViewById(f.block_row1col2_veritem3);
        this.mVerItem4 = (LinearLayout) v.findViewById(f.block_row1col2_veritem4);
        this.mIconIv1 = (ImageView) this.mVerItem1.findViewById(f.row1_col2_veritem_appicon);
        this.mIconIv2 = (ImageView) this.mVerItem2.findViewById(f.row1_col2_veritem_appicon);
        this.mIconIv3 = (ImageView) this.mVerItem3.findViewById(f.row1_col2_veritem_appicon);
        this.mIconIv4 = (ImageView) this.mVerItem4.findViewById(f.row1_col2_veritem_appicon);
        this.mNameTv1 = (TextView) this.mVerItem1.findViewById(f.row1_col2_veritem_appname);
        this.mNameTv2 = (TextView) this.mVerItem2.findViewById(f.row1_col2_veritem_appname);
        this.mNameTv3 = (TextView) this.mVerItem3.findViewById(f.row1_col2_veritem_appname);
        this.mNameTv4 = (TextView) this.mVerItem4.findViewById(f.row1_col2_veritem_appname);
        this.mAppSizeTv1 = (TextView) this.mVerItem1.findViewById(f.row1_col2_veritem_appsize);
        this.mAppSizeTv2 = (TextView) this.mVerItem2.findViewById(f.row1_col2_veritem_appsize);
        this.mAppSizeTv3 = (TextView) this.mVerItem3.findViewById(f.row1_col2_veritem_appsize);
        this.mAppSizeTv4 = (TextView) this.mVerItem4.findViewById(f.row1_col2_veritem_appsize);
        this.mInstallBtn1 = (CirProButton) this.mVerItem1.findViewById(f.btnInstall);
        this.mInstallBtn2 = (CirProButton) this.mVerItem2.findViewById(f.btnInstall);
        this.mInstallBtn3 = (CirProButton) this.mVerItem3.findViewById(f.btnInstall);
        this.mInstallBtn4 = (CirProButton) this.mVerItem4.findViewById(f.btnInstall);
        this.mDividerLine = v.findViewById(f.block_divider);
        return v;
    }

    public void updateView(Context context, Row2Col2AppVerItem item, t viewController, int position) {
        if (item != null) {
            if (item.mAppStructItem1 != null) {
                updateItemView(context, this.mVerItem1, this.mIconIv1, this.mNameTv1, this.mAppSizeTv1, this.mInstallBtn1, item.mAppStructItem1, viewController, position, 0);
            } else {
                this.mVerItem1.setVisibility(8);
            }
            if (item.mAppStructItem2 != null) {
                updateItemView(context, this.mVerItem2, this.mIconIv2, this.mNameTv2, this.mAppSizeTv2, this.mInstallBtn2, item.mAppStructItem2, viewController, position, 1);
            } else {
                this.mVerItem2.setVisibility(8);
            }
            if (item.mAppStructItem3 != null) {
                updateItemView(context, this.mVerItem3, this.mIconIv3, this.mNameTv3, this.mAppSizeTv3, this.mInstallBtn3, item.mAppStructItem3, viewController, position, 2);
            } else {
                this.mVerItem3.setVisibility(8);
            }
            if (item.mAppStructItem4 != null) {
                updateItemView(context, this.mVerItem4, this.mIconIv4, this.mNameTv4, this.mAppSizeTv4, this.mInstallBtn4, item.mAppStructItem4, viewController, position, 3);
            } else {
                this.mVerItem4.setVisibility(8);
            }
            if (item.mIsLastItemInGameBlock) {
                this.mDividerLine.setVisibility(8);
            } else {
                this.mDividerLine.setVisibility(0);
            }
        }
    }

    private void updateItemView(Context context, LinearLayout verItem, ImageView iconIv, TextView nameTv, TextView appsize, CirProButton installBtn, final AppStructItem appStructItem, t viewController, int position, int horPos) {
        verItem.setVisibility(0);
        h.c(context, appStructItem.icon, iconIv);
        nameTv.setText(appStructItem.name);
        appsize.setText(com.meizu.cloud.app.utils.g.a((double) appStructItem.size, context.getResources().getStringArray(b.sizeUnit)));
        viewController.a((a) appStructItem, null, true, installBtn);
        installBtn.setTag(appStructItem.package_name);
        final AppStructItem appStructItem2 = appStructItem;
        final CirProButton cirProButton = installBtn;
        final int i = position;
        final int i2 = horPos;
        installBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Row2Col2VerBlockLayout.this.mOnChildClickListener.onDownload(appStructItem2, cirProButton, i, i2);
            }
        });
        final int i3 = position;
        final int i4 = horPos;
        verItem.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Row2Col2VerBlockLayout.this.mOnChildClickListener.onClickApp(appStructItem, i3, i4);
            }
        });
    }

    protected void updateLayoutMargins(Context context, Row2Col2AppVerItem item) {
    }
}
