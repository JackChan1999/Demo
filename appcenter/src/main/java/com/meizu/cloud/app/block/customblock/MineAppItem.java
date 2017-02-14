package com.meizu.cloud.app.block.customblock;

import android.app.usage.UsageStats;
import com.meizu.cloud.app.block.Blockable;
import com.meizu.cloud.app.request.model.GameEntryInfo;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo;

public class MineAppItem implements Blockable {
    public boolean isChecked;
    public ServerUpdateAppInfo<GameEntryInfo> updateAppInfo;
    public UsageStats usageStats;
    public long useageFlow;

    public MineAppItem(ServerUpdateAppInfo<GameEntryInfo> updateAppInfo, UsageStats usageStats, long useageFlow) {
        this.updateAppInfo = updateAppInfo;
        this.usageStats = usageStats;
        this.useageFlow = useageFlow;
    }

    public Class getBlockClass() {
        return getClass();
    }
}
