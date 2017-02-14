package com.meizu.cloud.pushsdk.notification;

public class PushNotificationBuilder {
    protected int mLargIcon;
    protected int mNotificationDefaults;
    protected int mNotificationFlags;
    protected int mStatusbarIcon;

    public int getmLargIcon() {
        return this.mLargIcon;
    }

    public int getmNotificationDefaults() {
        return this.mNotificationDefaults;
    }

    public int getmNotificationFlags() {
        return this.mNotificationFlags;
    }

    public int getmStatusbarIcon() {
        return this.mStatusbarIcon;
    }

    public void setmLargIcon(int i) {
        this.mLargIcon = i;
    }

    public void setmNotificationDefaults(int i) {
        this.mNotificationDefaults = i;
    }

    public void setmNotificationFlags(int i) {
        this.mNotificationFlags = i;
    }

    public void setmStatusbarIcon(int i) {
        this.mStatusbarIcon = i;
    }
}
