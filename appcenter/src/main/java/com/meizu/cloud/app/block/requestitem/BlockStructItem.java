package com.meizu.cloud.app.block.requestitem;

import android.os.Parcel;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem.NotJsonColumn;
import java.util.ArrayList;

public class BlockStructItem extends AbstractStrcutItem {
    public ArrayList<AbstractStrcutItem> mSubItems = new ArrayList();
    @NotJsonColumn
    public boolean needExtraMarginTop = false;
    @NotJsonColumn
    public int style;

    public BlockStructItem(String name, String type, String url, boolean more) {
        this.name = name;
        this.type = type;
        this.url = url;
        this.more = more;
    }

    public AbstractStrcutItem getItem(int position) {
        if (this.mSubItems == null || position < 0 || position >= this.mSubItems.size()) {
            return null;
        }
        return (AbstractStrcutItem) this.mSubItems.get(position);
    }

    public ArrayList<AbstractStrcutItem> getSubItems() {
        return this.mSubItems;
    }

    public void addItem(AbstractStrcutItem item) {
        if (this.mSubItems == null) {
            this.mSubItems = new ArrayList();
        }
        this.mSubItems.add(item);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }
}
