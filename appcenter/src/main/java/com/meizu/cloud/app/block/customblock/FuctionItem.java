package com.meizu.cloud.app.block.customblock;

import com.meizu.cloud.app.block.Blockable;

public class FuctionItem implements Blockable {
    public String fuctionText;
    public int icon;
    public int id;

    public FuctionItem(int id, int iconResId, String fuctionText) {
        this.id = id;
        this.icon = iconResId;
        this.fuctionText = fuctionText;
    }

    public Class getBlockClass() {
        return getClass();
    }
}
