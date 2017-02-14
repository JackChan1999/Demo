package com.google.demo3.interface_demo;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/1 19:02
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class Me {

    private Timer mTimer;

    public void wantToSleep(){
        System.out.println("想要睡觉");
    }

    public void startSleep(){
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("开始睡觉了，老师来了叫醒我");
            }
        } , 0 , 1000);
    }


    public void stopSlepp(){
        if (mTimer != null){
            mTimer.cancel();
            mTimer = null ;
            System.out.println("停止睡觉");
        }
    }
}
