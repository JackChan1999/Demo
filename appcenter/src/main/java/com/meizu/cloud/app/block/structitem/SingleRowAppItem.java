package com.meizu.cloud.app.block.structitem;

import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;

public class SingleRowAppItem extends AbsBlockItem {
    public AppUpdateStructItem app;
    public boolean showDivider;

    public SingleRowAppItem() {
        this.showDivider = true;
        this.style = 4;
    }

    public int getId() {
        if (this.app != null) {
            return this.app.id;
        }
        return -1;
    }

    public String getPackgeName() {
        if (this.app != null) {
            return this.app.package_name;
        }
        return "";
    }

    public void setItemStyle(int style) {
        this.style = style;
    }
}
