package com.meizu.cloud.app.core;

public enum c {
    UPGRADE,
    OPEN,
    DOWNGRADE,
    NOT_INSTALL,
    BUILD_IN;

    public static c a(int value) {
        if (Integer.MAX_VALUE == value) {
            return BUILD_IN;
        }
        if (Integer.MIN_VALUE == value) {
            return NOT_INSTALL;
        }
        if (value == 0) {
            return OPEN;
        }
        if (value > 0) {
            return UPGRADE;
        }
        if (value < 0) {
            return DOWNGRADE;
        }
        return NOT_INSTALL;
    }
}
