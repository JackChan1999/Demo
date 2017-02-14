package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import com.meizu.cloud.app.block.requestitem.SubpagePageConfigsInfo;
import com.meizu.cloud.app.block.structitem.SubpageTitleItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class SubpageTitleLayout extends AbsBlockLayout<SubpageTitleItem> {
    View mImgCusor;
    TextView mRecomDes;
    TextView mTitle;
    View mTitleLayout;

    public SubpageTitleLayout(Context context, SubpageTitleItem subpageTitleItem) {
        super(context, subpageTitleItem);
    }

    public View createView(Context context, SubpageTitleItem item) {
        View v = inflate(context, g.block_subpage_title_layout);
        this.mTitleLayout = v.findViewById(f.titleLayout);
        this.mImgCusor = v.findViewById(f.img_cursor);
        this.mTitle = (TextView) v.findViewById(f.subpage_title_name);
        this.mRecomDes = (TextView) v.findViewById(f.subpage_title_recomdes);
        try {
            this.mTitle.setTypeface(Typeface.createFromFile("/system/fonts/SourceHansansCN-Normal.ttf"), 1);
            this.mRecomDes.setTypeface(Typeface.createFromFile("/system/fonts/SourceHansansCN-Light.otf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mDivider = v.findViewById(f.block_title_divider);
        return v;
    }

    public void updateView(Context context, SubpageTitleItem item, t viewController, int position) {
        if (item != null) {
            this.mTitle.setText(item.name);
            this.mRecomDes.setText(item.recommend_desc);
            SubpagePageConfigsInfo configInfo = null;
            if (viewController != null) {
                configInfo = viewController.d();
            }
            if (configInfo != null) {
                this.mImgCusor.setBackgroundColor(configInfo.line_color);
                this.mTitle.setTextColor(configInfo.des_color);
                this.mRecomDes.setTextColor(configInfo.recom_des_common);
            }
        }
    }

    protected void updateLayoutMargins(Context context, SubpageTitleItem item) {
    }
}
