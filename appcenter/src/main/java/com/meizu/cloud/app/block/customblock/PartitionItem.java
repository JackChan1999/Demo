package com.meizu.cloud.app.block.customblock;

import com.meizu.cloud.app.block.Blockable;

public class PartitionItem implements Blockable {
    public String btnText;
    public String btnText2;
    public boolean hasBtn;
    public int id;
    public Object tag;
    public String title;

    public PartitionItem(String title, String btnText, boolean hasBtn, int id) {
        this.title = title;
        this.btnText = btnText;
        this.hasBtn = hasBtn;
        this.id = id;
    }

    public PartitionItem(String title, String btnText1, String btnText2, boolean hasBtn, int id) {
        this.title = title;
        this.btnText = btnText1;
        this.btnText2 = btnText2;
        this.hasBtn = hasBtn;
        this.id = id;
    }

    public Class getBlockClass() {
        return getClass();
    }
}
