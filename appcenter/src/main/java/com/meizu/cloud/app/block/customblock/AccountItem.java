package com.meizu.cloud.app.block.customblock;

import com.meizu.cloud.app.block.Blockable;

public class AccountItem implements Blockable {
    public int id;

    public Class getBlockClass() {
        return getClass();
    }
}
