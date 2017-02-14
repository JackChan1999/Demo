package com.qq.demo.bean;

import java.io.Serializable;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/3/24 20:55
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class AppInfoBean implements Serializable{
    private static final long serialVersionUID = -2071565876962058344L;

    public String	des;			// 应用的描述
    public String	downloadUrl;	// 应用的下载地址
    public String	iconUrl;		// 应用的图标地址
    public long		id;			    // 应用的id
    public String	name;			// 应用的名字
    public String	packageName;	// 应用的包名
    public long		size;			// 应用的长度
    public float	stars;			// 应用的评分
}
