package com.meizu.cloud.app.block.structitem;

import com.meizu.cloud.app.request.structitem.CategoryStructItem;
import java.util.List;

public class CategoryGridItem extends AbsBlockItem {
    public List<CategoryStructItem> structItemList;

    public CategoryGridItem() {
        this.style = 7;
    }
}
