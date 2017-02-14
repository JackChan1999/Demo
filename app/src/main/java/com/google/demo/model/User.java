package com.google.demo.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.demo.BR;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/9 10:51
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class User extends BaseObservable {
    public String name;
    public int age;
    public String icon;

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public int getAge() {
        return age;
    }
}
