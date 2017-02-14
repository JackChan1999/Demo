package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.meizu.cloud.app.block.structitem.Row1Col2AppItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.t.a;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.widget.BaseStarRateWidget;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class Row1Col2BlockLayout extends AbsBlockLayout<Row1Col2AppItem> {
    View mAppLayout1;
    View mAppLayout2;
    CirProButton mBtnInstall1;
    CirProButton mBtnInstall2;
    ImageView mImageView1;
    ImageView mImageView2;
    BaseStarRateWidget mStar1;
    BaseStarRateWidget mStar2;
    TextView mTextView1;
    TextView mTextView2;

    public Row1Col2BlockLayout(Context context, Row1Col2AppItem item) {
        super(context, item);
    }

    public View createView(Context context, Row1Col2AppItem item) {
        View v = inflate(context, g.block_row1_col2_layout_v2);
        this.mDivider = v.findViewById(f.divider);
        View appLayout1 = v.findViewById(f.app1);
        View appLayout2 = v.findViewById(f.app2);
        int padidngitem = (int) context.getResources().getDimension(d.block_inner_item_padding);
        appLayout1.setPadding(appLayout1.getPaddingLeft(), appLayout1.getPaddingTop(), appLayout1.getPaddingRight(), appLayout1.getPaddingBottom());
        int paddingLeft2 = appLayout2.getPaddingLeft();
        int i = paddingLeft2 + padidngitem;
        appLayout2.setPadding(i, appLayout2.getPaddingTop(), appLayout2.getPaddingRight(), appLayout2.getPaddingBottom());
        ImageView icon1 = (ImageView) appLayout1.findViewById(f.icon);
        ImageView icon2 = (ImageView) appLayout2.findViewById(f.icon);
        TextView textView1 = (TextView) appLayout1.findViewById(f.text);
        TextView textView2 = (TextView) appLayout2.findViewById(f.text);
        this.mBtnInstall1 = (CirProButton) appLayout1.findViewById(f.btnInstall);
        this.mBtnInstall2 = (CirProButton) appLayout2.findViewById(f.btnInstall);
        this.mStar1 = (BaseStarRateWidget) appLayout1.findViewById(f.star);
        this.mStar2 = (BaseStarRateWidget) appLayout2.findViewById(f.star);
        this.mAppLayout1 = appLayout1;
        this.mAppLayout2 = appLayout2;
        this.mImageView1 = icon1;
        this.mImageView2 = icon2;
        this.mTextView1 = textView1;
        this.mTextView2 = textView2;
        this.mBtnInstall1.post(new Runnable() {
            public void run() {
                int offsetY = Row1Col2BlockLayout.this.mBtnInstall1.getHeight() / 2;
                int offsetX = Row1Col2BlockLayout.this.mBtnInstall1.getWidth() / 4;
                Rect rect = new Rect();
                Row1Col2BlockLayout.this.mBtnInstall1.getHitRect(rect);
                rect.top -= offsetY;
                rect.bottom += offsetY;
                rect.left -= offsetX;
                rect.right += offsetX;
            }
        });
        this.mBtnInstall2.post(new Runnable() {
            public void run() {
                int offsetY = Row1Col2BlockLayout.this.mBtnInstall2.getHeight() / 2;
                int offsetX = Row1Col2BlockLayout.this.mBtnInstall2.getWidth() / 4;
                Rect rect = new Rect();
                Row1Col2BlockLayout.this.mBtnInstall2.getHitRect(rect);
                rect.top -= offsetY;
                rect.bottom += offsetY;
                rect.left -= offsetX;
                rect.right += offsetX;
            }
        });
        return v;
    }

    public void updateView(Context context, Row1Col2AppItem item, t viewController, final int position) {
        AbsBlockLayout.checkShowDivider(this, item);
        if (item.app1 != null) {
            item.app1.click_pos = position;
        }
        if (item.app2 != null) {
            item.app2.click_pos = position;
        }
        final a item1 = item.app1;
        final a item2 = item.app2;
        if (item1 != null) {
            this.mAppLayout1.setVisibility(0);
            this.mAppLayout1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (Row1Col2BlockLayout.this.mOnChildClickListener != null) {
                        Row1Col2BlockLayout.this.mOnChildClickListener.onClickApp(item1, position, 0);
                    }
                }
            });
            h.a(context, item1.icon, this.mImageView1);
            this.mTextView1.setText(item1.name);
            this.mStar1.setValue((double) (((float) item1.star) / 10.0f));
            this.mStar1.setCommentNum(item1.evaluate_count);
            this.mBtnInstall1.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (Row1Col2BlockLayout.this.mOnChildClickListener != null) {
                        Row1Col2BlockLayout.this.mOnChildClickListener.onDownload(item1, view, position, 0);
                    }
                }
            });
            this.mBtnInstall1.setTag(item1.package_name);
            viewController.a(item1, null, true, this.mBtnInstall1);
        } else {
            this.mAppLayout1.setVisibility(4);
        }
        if (item2 != null) {
            this.mAppLayout2.setVisibility(0);
            this.mAppLayout2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (Row1Col2BlockLayout.this.mOnChildClickListener != null) {
                        Row1Col2BlockLayout.this.mOnChildClickListener.onClickApp(item2, position, 1);
                    }
                }
            });
            h.a(context, item2.icon, this.mImageView2);
            this.mTextView2.setText(item2.name);
            this.mStar2.setValue((double) (((float) item2.star) / 10.0f));
            this.mStar2.setCommentNum(item2.evaluate_count);
            this.mBtnInstall2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (Row1Col2BlockLayout.this.mOnChildClickListener != null) {
                        Row1Col2BlockLayout.this.mOnChildClickListener.onDownload(item2, view, position, 1);
                    }
                }
            });
            this.mBtnInstall2.setTag(item2.package_name);
            viewController.a(item2, null, true, this.mBtnInstall2);
            return;
        }
        this.mAppLayout2.setVisibility(4);
    }

    protected void updateLayoutMargins(Context context, Row1Col2AppItem item) {
    }
}
