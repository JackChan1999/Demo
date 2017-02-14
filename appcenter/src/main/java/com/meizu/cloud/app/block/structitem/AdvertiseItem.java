package com.meizu.cloud.app.block.structitem;

import com.meizu.cloud.app.block.requestitem.AppAdStructItem;

public class AdvertiseItem extends AbsBlockItem {
    public AppAdStructItem advertise1;
    public AppAdStructItem advertise2;

    public AdvertiseItem() {
        this.style = 2;
        this.needExtraMarginTop = true;
    }
}
