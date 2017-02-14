package com.meizu.cloud.app.block.structitem;

import com.meizu.cloud.app.request.structitem.RecommendAppStructItem;

public class RecommendAppItem extends AbsBlockItem {
    public RecommendAppStructItem app;

    public RecommendAppItem(RecommendAppStructItem app) {
        this.style = 5;
        this.app = app;
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
}
