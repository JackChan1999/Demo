package com.meizu.cloud.app.block.structitem;

import com.meizu.cloud.app.block.Blockable;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem.NotJsonColumn;

public abstract class AbsBlockItem implements Blockable {
    public boolean isExposured = false;
    public boolean mIsLastItemInAppBlock = false;
    public boolean mIsLastItemInGameBlock = false;
    public boolean needExtraMarginTop = false;
    public boolean showDivider = true;
    @NotJsonColumn
    public int style = -1;

    public Class getBlockClass() {
        return getClass();
    }
}
