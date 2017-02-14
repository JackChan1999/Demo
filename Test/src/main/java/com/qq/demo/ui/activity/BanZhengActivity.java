package com.qq.demo.ui.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.qq.demo.service.LeaderService;
import com.qq.demo.service.PublicBusiness;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/4 21:10
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class BanZhengActivity extends Activity {

    private PublicBusiness mZhouMi;
    private MyServiceConnection mConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, LeaderService.class);
        mConn = new MyServiceConnection();
        bindService(intent, mConn,BIND_AUTO_CREATE);
        mZhouMi.qianXian();
    }

    class MyServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mZhouMi = (PublicBusiness) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
