package com.meizu.cloud.app.core;

import android.util.Pair;

public class SubmitAppInfo {
    public int operate;
    public String package_name;
    public int version_code;

    public SubmitAppInfo(String packageName, int versionCode, int operate) {
        this.package_name = packageName;
        this.version_code = versionCode;
        this.operate = operate;
    }

    public SubmitAppInfo(Pair<String, Integer> info, int operate) {
        this.package_name = (String) info.first;
        this.version_code = ((Integer) info.second).intValue();
        this.operate = operate;
    }

    public SubmitAppInfo(SubmitAppInfo info, int operate) {
        this.package_name = info.package_name;
        this.version_code = info.version_code;
        this.operate = operate;
    }

    public boolean isSame(Pair<String, Integer> info) {
        return this.package_name.equals(info.first) && this.version_code == ((Integer) info.second).intValue();
    }
}
