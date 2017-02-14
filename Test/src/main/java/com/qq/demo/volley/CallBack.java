package com.qq.demo.volley;

import com.android.volley.VolleyError;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/6/30 09:16
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public interface CallBack<T> {
    void onResponse(T t);
    void onErrorResponse(VolleyError volleyError);
}
