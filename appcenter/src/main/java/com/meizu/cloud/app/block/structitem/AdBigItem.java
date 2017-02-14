package com.meizu.cloud.app.block.structitem;

import com.meizu.cloud.app.block.requestitem.AdBigStructItem;

public class AdBigItem extends AbsBlockItem {
    public AdBigStructItem mAdBigStructItem;
    public boolean mIsNeedMarginBottom;

    public AdBigItem() {
        this.mIsNeedMarginBottom = false;
        this.style = 9;
        this.needExtraMarginTop = true;
    }
}
