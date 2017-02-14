package com.google.demo3.observer_demo;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/1 20:05
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class Test {

    public static void main(String[] args){
        Teacher teacher = new Teacher();
        Student student = new Student();
        teacher.addObserver(student);
        teacher.publishMessage("同学们，今天没有作业");
    }
}
