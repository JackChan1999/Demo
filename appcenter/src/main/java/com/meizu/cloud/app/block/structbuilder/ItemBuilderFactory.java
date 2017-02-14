package com.meizu.cloud.app.block.structbuilder;

public class ItemBuilderFactory {
    public static final int BLOCK_ADVERTISE_STYLE = 1;
    public static final int BLOCK_BANNER_STYLE = 0;
    public static final int BLOCK_ROW1_COL2_STYLE = 2;
    public static final int BLOCK_ROW2_COL2_STYLE = 4;
    public static final int BLOCK_ROW3_COL1_STYLE = 5;
    public static final int BLOCK_TYPE_GAME_RECOMMEND = 3;
    public static final int VIEW_TYPE_COUNT = 6;
    private static BlockItemBuilder sBlockItemBuilder = null;

    public static BlockItemBuilder findItemBuilder(int type) {
        switch (type) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                if (sBlockItemBuilder == null) {
                    sBlockItemBuilder = new BlockItemBuilder();
                }
                return sBlockItemBuilder;
            default:
                return null;
        }
    }
}
