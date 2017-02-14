package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.app.block.requestitem.ChannelStructItem;
import com.meizu.cloud.app.block.structitem.ChannelCol5Item;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import java.util.ArrayList;
import java.util.List;

public class ChannelBlockLayout extends AbsBlockLayout<ChannelCol5Item> {
    public List<ImageView> mIconIvs;
    public List<LinearLayout> mItems;
    public List<TextView> mNameTvs;
    public List<ImageView> mRedTagIvs;

    public ChannelBlockLayout(Context context, ChannelCol5Item channelCol5Item) {
        super(context, channelCol5Item);
    }

    public View createView(Context context, ChannelCol5Item item) {
        View view = inflate(context, g.block_channel_col5_layout);
        this.mItems = new ArrayList();
        this.mItems.add((LinearLayout) view.findViewById(f.channel_item1));
        this.mItems.add((LinearLayout) view.findViewById(f.channel_item2));
        this.mItems.add((LinearLayout) view.findViewById(f.channel_item3));
        this.mItems.add((LinearLayout) view.findViewById(f.channel_item4));
        this.mItems.add((LinearLayout) view.findViewById(f.channel_item5));
        this.mIconIvs = new ArrayList();
        this.mNameTvs = new ArrayList();
        this.mRedTagIvs = new ArrayList();
        for (int i = 0; i < this.mItems.size(); i++) {
            this.mIconIvs.add((ImageView) ((LinearLayout) this.mItems.get(i)).findViewById(f.channel_item_icon));
            this.mNameTvs.add((TextView) ((LinearLayout) this.mItems.get(i)).findViewById(f.channel_item_name));
            this.mRedTagIvs.add((ImageView) ((LinearLayout) this.mItems.get(i)).findViewById(f.channel_item_red_tag));
        }
        return view;
    }

    public void updateView(Context context, ChannelCol5Item item, t viewController, int position) {
        if (item != null) {
            if (item.mChannelStructItem1 != null) {
                updateChannelItemView(context, (LinearLayout) this.mItems.get(0), (ImageView) this.mIconIvs.get(0), (TextView) this.mNameTvs.get(0), (ImageView) this.mRedTagIvs.get(0), item.mChannelStructItem1, position, 0);
            }
            if (item.mChannelStructItem2 != null) {
                updateChannelItemView(context, (LinearLayout) this.mItems.get(1), (ImageView) this.mIconIvs.get(1), (TextView) this.mNameTvs.get(1), (ImageView) this.mRedTagIvs.get(1), item.mChannelStructItem2, position, 1);
            }
            if (item.mChannelStructItem3 != null) {
                updateChannelItemView(context, (LinearLayout) this.mItems.get(2), (ImageView) this.mIconIvs.get(2), (TextView) this.mNameTvs.get(2), (ImageView) this.mRedTagIvs.get(2), item.mChannelStructItem3, position, 2);
            }
            if (item.mChannelStructItem4 != null) {
                updateChannelItemView(context, (LinearLayout) this.mItems.get(3), (ImageView) this.mIconIvs.get(3), (TextView) this.mNameTvs.get(3), (ImageView) this.mRedTagIvs.get(3), item.mChannelStructItem4, position, 3);
            }
            if (item.mChannelStructItem5 != null) {
                updateChannelItemView(context, (LinearLayout) this.mItems.get(4), (ImageView) this.mIconIvs.get(4), (TextView) this.mNameTvs.get(4), (ImageView) this.mRedTagIvs.get(4), item.mChannelStructItem5, position, 4);
            }
        }
    }

    private void updateChannelItemView(Context context, LinearLayout mItem, ImageView mIconIv, TextView mNameTv, ImageView mRedTagIv, final ChannelStructItem channelStructItem, final int position, final int horPos) {
        mItem.setVisibility(0);
        h.a(context, channelStructItem.logo, mIconIv);
        mNameTv.setText(channelStructItem.name);
        if (System.currentTimeMillis() - channelStructItem.last_time < 86400000) {
            mRedTagIv.setVisibility(0);
        }
        mItem.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChannelBlockLayout.this.mOnChildClickListener.onClickConts(channelStructItem, null, position, horPos);
            }
        });
    }

    protected void updateLayoutMargins(Context context, ChannelCol5Item item) {
    }
}
