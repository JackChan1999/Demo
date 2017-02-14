package com.google.demo3.interface_demo;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/1 19:16
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class Test {

    public  static void main(String[] args){

        final Me me = new Me();
        ClassMate classMate = new ClassMate() ;
        me.wantToSleep();
        classMate.serOnTeacherComeListener(new ClassMate.OnTeacherComeListener() {
            @Override
            public void onTeacherCome(String name) {
                if (name.equals("班主任")){
                    System.out.println("班主任来了，快醒醒");
                    me.stopSlepp();
                }
            }

            @Override
            public void onTeacherCome() {

            }
        });

        me.startSleep();
        classMate.doTeacherCome("班主任");

    }
}
