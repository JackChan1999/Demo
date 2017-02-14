package com.qq.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/4 21:07
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class LeaderService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ZhouMi();
    }

    public class ZhouMi extends Binder implements PublicBusiness{
        public void qianXian(){
            banZheng();
        }

        public void daMaJiang(){
            //...
        }
    }

    public void banZheng(){
        //...
    }
}
