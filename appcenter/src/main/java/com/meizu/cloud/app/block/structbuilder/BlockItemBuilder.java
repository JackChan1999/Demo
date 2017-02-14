package com.meizu.cloud.app.block.structbuilder;

import android.content.Context;
import android.view.ViewGroup;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.block.structitem.AdAppBigItem;
import com.meizu.cloud.app.block.structitem.AdBigItem;
import com.meizu.cloud.app.block.structitem.AdvertiseItem;
import com.meizu.cloud.app.block.structitem.BlockDividerViewItem;
import com.meizu.cloud.app.block.structitem.ChannelCol5Item;
import com.meizu.cloud.app.block.structitem.ContsRow1Col4Item;
import com.meizu.cloud.app.block.structitem.GameBacktopItem;
import com.meizu.cloud.app.block.structitem.GameQualityItem;
import com.meizu.cloud.app.block.structitem.IconTitleItem;
import com.meizu.cloud.app.block.structitem.RecommendAppItem;
import com.meizu.cloud.app.block.structitem.RollMessageItem;
import com.meizu.cloud.app.block.structitem.RollingPlayItem;
import com.meizu.cloud.app.block.structitem.Row1Col2AppItem;
import com.meizu.cloud.app.block.structitem.Row1Col2AppVerItem;
import com.meizu.cloud.app.block.structitem.Row1Col3AppVerItem;
import com.meizu.cloud.app.block.structitem.Row1Col4AppVerItem;
import com.meizu.cloud.app.block.structitem.Row2Col2AppVerItem;
import com.meizu.cloud.app.block.structitem.SingleRowAppItem;
import com.meizu.cloud.app.block.structitem.SubpageTitleItem;
import com.meizu.cloud.app.block.structitem.TitleItem;
import com.meizu.cloud.app.block.structitem.VideoCol1Item;
import com.meizu.cloud.app.block.structitem.VideoCol2Item;
import com.meizu.cloud.app.block.structlayout.AbsBlockLayout;
import com.meizu.cloud.app.block.structlayout.AdAppBigBlockLayout;
import com.meizu.cloud.app.block.structlayout.AdBigBlockLayout;
import com.meizu.cloud.app.block.structlayout.AdvertiseBlockLayout;
import com.meizu.cloud.app.block.structlayout.BlockDividerViewLayout;
import com.meizu.cloud.app.block.structlayout.CategoryGridLayout;
import com.meizu.cloud.app.block.structlayout.CategoryTag6Layout;
import com.meizu.cloud.app.block.structlayout.ChannelBlockLayout;
import com.meizu.cloud.app.block.structlayout.ContsRow1Col4BLockLayout;
import com.meizu.cloud.app.block.structlayout.GameBacktopLayout;
import com.meizu.cloud.app.block.structlayout.GameQualityBlockLayout;
import com.meizu.cloud.app.block.structlayout.IconTitleBlockLayout;
import com.meizu.cloud.app.block.structlayout.RecommendBlcokLayout;
import com.meizu.cloud.app.block.structlayout.RollMessageBlockLayout;
import com.meizu.cloud.app.block.structlayout.RollingPlayLayout;
import com.meizu.cloud.app.block.structlayout.Row1Col2BlockLayout;
import com.meizu.cloud.app.block.structlayout.Row1Col2VerBlockLayout;
import com.meizu.cloud.app.block.structlayout.Row1Col3VerBlockLayout;
import com.meizu.cloud.app.block.structlayout.Row1Col4VerBlockLayout;
import com.meizu.cloud.app.block.structlayout.Row2Col2VerBlockLayout;
import com.meizu.cloud.app.block.structlayout.SingleRowBlockLayout;
import com.meizu.cloud.app.block.structlayout.SubpageSingleRowLayout;
import com.meizu.cloud.app.block.structlayout.SubpageTitleLayout;
import com.meizu.cloud.app.block.structlayout.TitleBlockLayout;
import com.meizu.cloud.app.block.structlayout.VideoCol1Layout;
import com.meizu.cloud.app.block.structlayout.VideoCol2Layout;

public class BlockItemBuilder {
    public static final int BLOCK_ADVERTISE_STYLE = 2;
    public static final int BLOCK_AD_APP_BIG_STYLE = 10;
    public static final int BLOCK_AD_BIG_STYLE = 9;
    public static final int BLOCK_BACKTOP = 20;
    public static final int BLOCK_BANNER_STYLE = 1;
    public static final int BLOCK_CATEGORY_GRID_STYLE = 7;
    public static final int BLOCK_CATEGORY_TAG6_STYLE = 6;
    public static final int BLOCK_CHANNEL_STYLE = 12;
    public static final int BLOCK_COL1_APP_STYLE = 4;
    public static final int BLOCK_COL2_APP_STYLE = 3;
    public static final int BLOCK_COL2_APP_VER_STYLE = 16;
    public static final int BLOCK_COL3_APP_VER_STYLE = 17;
    public static final int BLOCK_COL4_APP_VER_STYLE = 11;
    public static final int BLOCK_CONTS_COL4_STYLE = 8;
    public static final int BLOCK_DIVIDER_VIEW_STYLE = 21;
    public static final int BLOCK_GAME_QUALITY_STYLE = 14;
    public static final int BLOCK_ICON_TITLE_STYLE = 15;
    public static final int BLOCK_RECCOMEND_APP_STYLE = 5;
    public static final int BLOCK_ROLL_MESSAGE_STYLE = 13;
    public static final int BLOCK_ROW2_COL2_VER_STYLE = 22;
    public static final int BLOCK_SUBPAGE_COL1_APP_STYLE = 24;
    public static final int BLOCK_SUBPAGE_TITLE_STYLE = 23;
    public static final int BLOCK_TITLE_STYLE = 0;
    public static final int BLOCK_VIDEO_COL1_STYLE = 19;
    public static final int BLOCK_VIDEO_COL2_STYLE = 18;
    public static final int VIEW_TYPE_COUNT = 25;

    public static AbsBlockLayout build(Context context, AbsBlockItem absBlockItem) {
        switch (absBlockItem.style) {
            case 0:
                return new TitleBlockLayout(context, (TitleItem) absBlockItem);
            case 1:
                return new RollingPlayLayout(context, (RollingPlayItem) absBlockItem);
            case 2:
                return new AdvertiseBlockLayout(context, (AdvertiseItem) absBlockItem);
            case 3:
                return new Row1Col2BlockLayout(context, (Row1Col2AppItem) absBlockItem);
            case 4:
                return new SingleRowBlockLayout(context, (SingleRowAppItem) absBlockItem);
            case 5:
                return new RecommendBlcokLayout(context, (RecommendAppItem) absBlockItem);
            case 8:
                return new ContsRow1Col4BLockLayout(context, (ContsRow1Col4Item) absBlockItem);
            case 9:
                return new AdBigBlockLayout(context, (AdBigItem) absBlockItem);
            case 10:
                return new AdAppBigBlockLayout(context, (AdAppBigItem) absBlockItem);
            case 11:
                return new Row1Col4VerBlockLayout(context, (Row1Col4AppVerItem) absBlockItem);
            case 12:
                return new ChannelBlockLayout(context, (ChannelCol5Item) absBlockItem);
            case 13:
                return new RollMessageBlockLayout(context, (RollMessageItem) absBlockItem);
            case 14:
                return new GameQualityBlockLayout(context, (GameQualityItem) absBlockItem);
            case 15:
                return new IconTitleBlockLayout(context, (IconTitleItem) absBlockItem);
            case 16:
                return new Row1Col2VerBlockLayout(context, (Row1Col2AppVerItem) absBlockItem);
            case 17:
                return new Row1Col3VerBlockLayout(context, (Row1Col3AppVerItem) absBlockItem);
            case 18:
                return new VideoCol2Layout(context, (VideoCol2Item) absBlockItem);
            case 19:
                return new VideoCol1Layout(context, (VideoCol1Item) absBlockItem);
            case 20:
                return new GameBacktopLayout(context, (GameBacktopItem) absBlockItem);
            case 21:
                return new BlockDividerViewLayout(context, (BlockDividerViewItem) absBlockItem);
            case 22:
                return new Row2Col2VerBlockLayout(context, (Row2Col2AppVerItem) absBlockItem);
            case 23:
                return new SubpageTitleLayout(context, (SubpageTitleItem) absBlockItem);
            case 24:
                return new SubpageSingleRowLayout(context, (SingleRowAppItem) absBlockItem);
            default:
                return null;
        }
    }

    public static AbsBlockLayout build(int style, ViewGroup parent) {
        switch (style) {
            case 0:
                return new TitleBlockLayout();
            case 1:
                return new RollingPlayLayout();
            case 2:
                return new AdvertiseBlockLayout();
            case 3:
                return new Row1Col2BlockLayout();
            case 4:
                return new SingleRowBlockLayout();
            case 5:
                return new RecommendBlcokLayout();
            case 6:
                return new CategoryTag6Layout(parent);
            case 7:
                return new CategoryGridLayout(parent);
            case 8:
                return new ContsRow1Col4BLockLayout();
            case 9:
                return new AdBigBlockLayout();
            case 10:
                return new AdAppBigBlockLayout();
            case 11:
                return new Row1Col4VerBlockLayout();
            case 12:
                return new ChannelBlockLayout();
            case 13:
                return new RollMessageBlockLayout();
            case 14:
                return new GameQualityBlockLayout();
            case 15:
                return new IconTitleBlockLayout();
            case 16:
                return new Row1Col2VerBlockLayout();
            case 17:
                return new Row1Col3VerBlockLayout();
            case 18:
                return new VideoCol2Layout(parent);
            case 19:
                return new VideoCol1Layout(parent);
            case 20:
                return new GameBacktopLayout();
            case 21:
                return new BlockDividerViewLayout();
            case 22:
                return new Row2Col2VerBlockLayout();
            case 23:
                return new SubpageTitleLayout();
            case 24:
                return new SubpageSingleRowLayout();
            default:
                return null;
        }
    }
}
