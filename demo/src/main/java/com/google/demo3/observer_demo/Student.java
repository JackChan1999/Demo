package com.google.demo3.observer_demo;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/1 20:04
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class Student implements Teacher.MyObserver {
    @Override
    public void update(String message) {
        System.out.println(getClass().getSimpleName()+"收到"+message);
    }
}
