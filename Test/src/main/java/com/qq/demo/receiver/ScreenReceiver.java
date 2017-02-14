package com.qq.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/4 21:50
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class ScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)){
            //...
        }else if (Intent.ACTION_SCREEN_OFF.equals(action)){
            //...
        }
    }
}
