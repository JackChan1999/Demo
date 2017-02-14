package com.meizu.cloud.app.block.structitem;

import com.meizu.cloud.app.request.structitem.AppStructItem;

public class Row1Col4AppVerItem extends AbsBlockItem {
    public AppStructItem mAppStructItem1;
    public AppStructItem mAppStructItem2;
    public AppStructItem mAppStructItem3;
    public AppStructItem mAppStructItem4;
    public boolean mIsHideDividerView;

    public Row1Col4AppVerItem() {
        this.mIsHideDividerView = false;
        this.style = 11;
    }

    public Row1Col4AppVerItem(boolean isHideDividerView) {
        this.mIsHideDividerView = false;
        this.style = 11;
        this.mIsHideDividerView = isHideDividerView;
    }
}
