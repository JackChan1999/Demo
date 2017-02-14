package com.meizu.cloud.app.block.structitem;

import com.meizu.cloud.app.block.requestitem.RollingPlayStructItem;

public class RollingPlayItem extends AbsBlockItem {
    public RollingPlayStructItem rollingPlayItem;

    public RollingPlayItem() {
        this.style = 1;
        this.needExtraMarginTop = true;
    }

    public void setPosition(int position) {
        this.rollingPlayItem.setPosition(position);
    }

    public int getPosition() {
        return this.rollingPlayItem.getPosition();
    }
}
