package com.meizu.cloud.app.core;

import com.alibaba.fastjson.annotation.JSONField;

public class EventTaskInfo {
    @JSONField
    public String pkgName;
    @JSONField
    public long taskId;
}
