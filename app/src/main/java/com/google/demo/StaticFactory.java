package com.google.demo;

import java.util.HashMap;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/10 11:12
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class StaticFactory {
    public static Boolean valueOf(boolean b){
        return b ? Boolean.TRUE : Boolean.FALSE;
    }

    public static <k,v> HashMap<k,v> newInstance(){
        return new HashMap<k,v>();
    }

}
