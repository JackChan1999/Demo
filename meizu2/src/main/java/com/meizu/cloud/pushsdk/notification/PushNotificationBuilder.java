package com.meizu.cloud.pushsdk.notification;

public class PushNotificationBuilder {
    protected int mLargIcon;
    protected int mNotificationDefaults;
    protected int mNotificationFlags;
    protected int mStatusbarIcon;

    public int getmNotificationDefaults() {
        return this.mNotificationDefaults;
    }

    public void setmNotificationDefaults(int i) {
        this.mNotificationDefaults = i;
    }

    public int getmNotificationFlags() {
        return this.mNotificationFlags;
    }

    public void setmNotificationFlags(int i) {
        this.mNotificationFlags = i;
    }

    public int getmStatusbarIcon() {
        return this.mStatusbarIcon;
    }

    public void setmStatusbarIcon(int i) {
        this.mStatusbarIcon = i;
    }

    public int getmLargIcon() {
        return this.mLargIcon;
    }

    public void setmLargIcon(int i) {
        this.mLargIcon = i;
    }
}
