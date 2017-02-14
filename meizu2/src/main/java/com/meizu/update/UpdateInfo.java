package com.meizu.update;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import defpackage.apa;

public class UpdateInfo implements Parcelable {
    public static final Creator<UpdateInfo> CREATOR = new apa();
    public String mDigest;
    public boolean mExistsUpdate;
    public boolean mNeedUpdate;
    public boolean mNoteNetWork;
    public String mSize;
    public long mSizeByte;
    public String mUpdateUrl;
    public String mUpdateUrl2;
    public int mVerifyMode;
    public String mVersionDate;
    public String mVersionDesc;
    public String mVersionName;

    public static UpdateInfo generateNoUpdateInfo() {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.mNeedUpdate = false;
        updateInfo.mExistsUpdate = false;
        return updateInfo;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(boolean2int(this.mNeedUpdate));
        parcel.writeInt(boolean2int(this.mExistsUpdate));
        parcel.writeString(this.mUpdateUrl);
        parcel.writeString(this.mVersionDesc);
        parcel.writeString(this.mVersionName);
        parcel.writeString(this.mSize);
        parcel.writeString(this.mVersionDate);
        parcel.writeString(this.mDigest);
        parcel.writeInt(this.mVerifyMode);
        parcel.writeLong(this.mSizeByte);
        parcel.writeString(this.mUpdateUrl2);
        parcel.writeInt(boolean2int(this.mNoteNetWork));
    }

    private void readFromParcel(Parcel parcel) {
        this.mNeedUpdate = int2boolean(parcel.readInt());
        this.mExistsUpdate = int2boolean(parcel.readInt());
        this.mUpdateUrl = parcel.readString();
        this.mVersionDesc = parcel.readString();
        this.mVersionName = parcel.readString();
        this.mSize = parcel.readString();
        this.mVersionDate = parcel.readString();
        this.mDigest = parcel.readString();
        this.mVerifyMode = parcel.readInt();
        this.mSizeByte = parcel.readLong();
        this.mUpdateUrl2 = parcel.readString();
        this.mNoteNetWork = int2boolean(parcel.readInt());
    }

    private UpdateInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    private int boolean2int(boolean z) {
        return z ? 1 : 0;
    }

    private boolean int2boolean(int i) {
        return i != 0;
    }
}
