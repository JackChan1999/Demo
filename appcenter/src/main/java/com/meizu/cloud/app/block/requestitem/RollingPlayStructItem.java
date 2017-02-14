package com.meizu.cloud.app.block.requestitem;

import android.os.Parcel;

public class RollingPlayStructItem extends BlockStructItem {
    protected int mPosition;

    public RollingPlayStructItem() {
        this.mPosition = 0;
    }

    public RollingPlayStructItem(String name, String type, String url, boolean more) {
        super(name, type, url, more);
        this.mPosition = 0;
        this.style = 1;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public int getPosition() {
        return this.mPosition;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }
}
