package com.meizu.cloud.compaign.award;

import android.content.Intent;
import com.alibaba.fastjson.TypeReference;
import com.meizu.cloud.app.request.JSONUtils;
import meizu.sdk.compaign.a;

public class GameAward extends a {
    private GameGiftInfo mGiftInfo = ((GameGiftInfo) JSONUtils.parseJSONObject(getAwardData(), new TypeReference<GameGiftInfo>() {
    }));

    public GameAward(Intent intent) {
        super(intent);
    }

    public String getGiftUrl() {
        return this.mGiftInfo == null ? "" : this.mGiftInfo.url;
    }
}
