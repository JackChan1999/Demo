package com.meizu.update;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UpdateInfo implements Parcelable {
    public static final Creator<UpdateInfo> CREATOR = new Creator<UpdateInfo>() {
        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return a(x0);
        }

        public /* synthetic */ Object[] newArray(int x0) {
            return a(x0);
        }

        public UpdateInfo a(Parcel source) {
            return new UpdateInfo(source);
        }

        public UpdateInfo[] a(int size) {
            return new UpdateInfo[size];
        }
    };
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

    protected UpdateInfo() {
    }

    public static UpdateInfo generateNoUpdateInfo() {
        UpdateInfo info = new UpdateInfo();
        info.mNeedUpdate = false;
        info.mExistsUpdate = false;
        return info;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(boolean2int(this.mNeedUpdate));
        dest.writeInt(boolean2int(this.mExistsUpdate));
        dest.writeString(this.mUpdateUrl);
        dest.writeString(this.mVersionDesc);
        dest.writeString(this.mVersionName);
        dest.writeString(this.mSize);
        dest.writeString(this.mVersionDate);
        dest.writeString(this.mDigest);
        dest.writeInt(this.mVerifyMode);
        dest.writeLong(this.mSizeByte);
        dest.writeString(this.mUpdateUrl2);
        dest.writeInt(boolean2int(this.mNoteNetWork));
    }

    private void readFromParcel(Parcel in) {
        this.mNeedUpdate = int2boolean(in.readInt());
        this.mExistsUpdate = int2boolean(in.readInt());
        this.mUpdateUrl = in.readString();
        this.mVersionDesc = in.readString();
        this.mVersionName = in.readString();
        this.mSize = in.readString();
        this.mVersionDate = in.readString();
        this.mDigest = in.readString();
        this.mVerifyMode = in.readInt();
        this.mSizeByte = in.readLong();
        this.mUpdateUrl2 = in.readString();
        this.mNoteNetWork = int2boolean(in.readInt());
    }

    private UpdateInfo(Parcel in) {
        readFromParcel(in);
    }

    private int boolean2int(boolean value) {
        return value ? 1 : 0;
    }

    private boolean int2boolean(int value) {
        return value != 0;
    }
}
