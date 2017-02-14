package com.meizu.cloud.app.block.customblock;

import com.meizu.cloud.app.block.Blockable;

public class AccountSubItem implements Blockable {
    public String fuctionText;
    public int icon;
    public int id;
    public String subInfo;

    public AccountSubItem(int id, int iconResId, String subInfo, String fuctionText) {
        this.id = id;
        this.icon = iconResId;
        this.subInfo = subInfo;
        this.fuctionText = fuctionText;
    }

    public Class getBlockClass() {
        return getClass();
    }
}
